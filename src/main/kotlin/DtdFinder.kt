
import java.io.File

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset


class DtdFinder(val reporter:XxeReporter) {


    fun analyzingJar(zip: ByteArray, zipFile: String) {
        val zipInputStream = ZipArchiveInputStream(ByteArrayInputStream(zip))

        var zipEntry = zipInputStream.nextZipEntry
        while (zipEntry != null) {

            val fileName = zipEntry.name

            if (!zipInputStream.canReadEntryData(zipEntry)) {
                println("Unable to read $zipFile")
            } else {

                if (isDtd(zipEntry.name)) {


                    val contentSize = if (zipEntry.size.toInt() != -1) zipEntry.size.toInt() else zipInputStream.available()
                    var fileContent = ByteArray(contentSize)

                    IOUtils.readFully(zipInputStream, fileContent)
                    analyzeDtdFile(String(fileContent, Charset.forName("UTF-8")), zipFile + "!/" + fileName)


                }
            }
            zipEntry = zipInputStream.nextZipEntry
        }


    }

    fun analyzeDtdFile(content: String, fileName: String) {
        val entityTester = EntityTester()

        val dtdFile = File.createTempFile("tempDtd", ".dtd")
        dtdFile.deleteOnExit()

        IOUtils.write(content, FileOutputStream(dtdFile), Charset.forName("UTF-8"))
        println("")
        println(" [=] Found a DTD: /$fileName")

        try {
            val entitiesToTest = entityTester.listOverridableEntities(FileInputStream(dtdFile))
            entityTester.findInjectableEntity(dtdFile.canonicalPath, fileName, entitiesToTest, reporter)
        } catch (e: Exception) {
            println(" [X] Unable to load DTD: $fileName")
            println(" [X] ${e.javaClass.name}: ${e.message}")
        }
    }

}

fun isDtd(filename: String): Boolean {
    return filename.endsWith(".dtd")
}

fun isJar(filename: String): Boolean {
    return filename.endsWith(".jar") || filename.endsWith(".zip") || filename.endsWith(".war")
}

fun main(args: Array<String>) {

    if(args.isEmpty()) {
        help()
    }
    else {
        val archive:String = args[0]
        scanTarFile(archive)
    }
}

fun help() {
    println("DTD-Finder")
    println("Usage: java -jar dtd-finder.jar {archive.tar}")
    println("")
}

fun scanTarFile(archive:String) {
    val myTarFile = TarArchiveInputStream(FileInputStream(File(archive)))
    val currentDir = System.getProperty("user.dir")


    var entry: TarArchiveEntry? = null
    var fileName: String

    entry = myTarFile.nextTarEntry

    val reportName = File(archive).name+"-dtd-report.md"
    val dtdFinder = DtdFinder(MarkdownReporter(currentDir, reportName))

    while (entry != null) {
        fileName = entry.name

        val content = ByteArray(entry.size.toInt())
        myTarFile.read(content, 0, content.size)

        if((isDtd(fileName) || isJar(fileName))) {

            if(isDtd(fileName)) {
                dtdFinder.analyzeDtdFile(String(content, Charset.forName("UTF-8")), fileName)
            }
            if(isJar(fileName)) {
                dtdFinder.analyzingJar(content,entry.name)
            }
        }

        entry = myTarFile.nextTarEntry
    }

    myTarFile.close()
    println("")
    println("Report written to $reportName")
}