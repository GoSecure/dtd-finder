import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.PrintWriter

interface XxeReporter {

    fun newPayload(dtd:String,entityName:String, xmlPayload:String)
}

class EchoReporter: XxeReporter {

    override fun newPayload(dtd: String,entityName:String, xmlPayload: String) {
        println("DTD: $dtd")
        println("ENTITY: $entityName")
        println("PAYLOAD: $xmlPayload")
    }

}

class QuietReporter: XxeReporter {

    override fun newPayload(dtd: String,entityName:String, xmlPayload: String) {
    }

}


class MarkdownReporter(outputDir:String, filename:String): XxeReporter {

    val outputFile:PrintWriter = PrintWriter(FileOutputStream(File(outputDir,filename)))
    init {
        outputFile.println("# DTD founds")
    }

    override fun newPayload(dtd: String, entityName:String, xmlPayload: String) {
        //println("DTD: $dtd")
        //println("PAYLOAD: $xmlPayload")

        outputFile.println("**DTD File:** `$dtd`")
        outputFile.println("")
        outputFile.println("**Injectable entity:** `$entityName`")
        outputFile.println("")
        outputFile.println("**XXE Payload:**")
        outputFile.println("```")
        outputFile.println("$xmlPayload")
        outputFile.println("```")
        outputFile.println("")
        outputFile.println(" --- ")
        outputFile.println("")
        outputFile.flush()
    }

}

class MarkdownCliReporter: XxeReporter {


    override fun newPayload(dtd: String, entityName:String, xmlPayload: String) {
        //println("DTD: $dtd")
        //println("PAYLOAD: $xmlPayload")

        println("**DTD File:** `$dtd`")
        println("")
        println("**Injectable entity:** `$entityName`")
        println("")
        println("**XXE Payload:**")
        println("```")
        println("$xmlPayload")
        println("```")
        println("")
        println(" --- ")
        println("")
    }

}