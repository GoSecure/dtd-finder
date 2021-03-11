@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")

import java.io.File

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.nio.file.Paths
import java.nio.file.Files
import java.io.IOException
import java.net.*


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
            val entitiesToTest = entityTester.listOverridableEntities(dtdFile.canonicalPath)
            entityTester.findInjectableEntity(dtdFile.canonicalPath, fileName, entitiesToTest, reporter)
        } catch (e: Exception) {
            println(" [X] Unable to load DTD: $fileName")
            println(" [X] ${e.javaClass.name}: ${e.message}")
        }
    }


    fun scanDirectory(directory: File) {
        println("Scanning directory ${directory.canonicalPath}")

        val currentDir = System.getProperty("user.dir")

        File(directory.canonicalPath).walkBottomUp().filter { it.isFile && (isDtd(it.name) || isJar(it.name)) }.forEach {
            //println(it.canonicalPath)

            val fileName = it.name

            val content = Files.readAllBytes(Paths.get(it.canonicalPath))

            if(isDtd(fileName)) {
                analyzeDtdFile(String(content, Charset.forName("UTF-8")), it.canonicalPath)
            }
            if(isJar(fileName)) {
                try {
                    analyzingJar(content, it.canonicalPath)
                }
                catch(e:Exception) {
                    println(" [!] Error occurs when load the jar $fileName")
                }
            }
        }

    }

    /**
     * Will extract the file from a TAR archive.
     * Path from archive will be considered to be the absolute path from the original filesystem.
     */
    fun scanTarFile(archive:File) {
        println("Scanning TAR file ${archive.canonicalPath}")

        val myTarFile = TarArchiveInputStream(FileInputStream(archive))


        var entry: TarArchiveEntry? = null
        var fileName: String

        entry = myTarFile.nextTarEntry


        while (entry != null) {
            fileName = entry.name

            val content = ByteArray(entry.size.toInt())
            myTarFile.read(content, 0, content.size)

            if((isDtd(fileName) || isJar(fileName))) {

                if(isDtd(fileName)) {
                    analyzeDtdFile(String(content, Charset.forName("UTF-8")), fileName)
                }
                if(isJar(fileName)) {
                    try {
                        analyzingJar(content,entry.name)
                    }
                    catch(e:Exception) {
                        println(" [!] An error occurs when loading the jar ${entry.name} inside $fileName")
                    }
                }
            }

            entry = myTarFile.nextTarEntry
        }

        myTarFile.close()
    }

    /**
     * This scan mode is intended to scan mainly single jar.
     *
     * @param f Zip file to analyze
     */
    fun scanZipFile(f: File) {
        println("Scanning ZIP file ${f.canonicalPath}")
        try {
            analyzingJar(f.readBytes() ,f.name)
        }
        catch(e:Exception) {
            println(" [!] An error occurs when loading the zip/jar file ${f.name}")
        }
    }
}

inline fun isDtd(filename: String): Boolean {
    return filename.endsWith(".dtd")
}

inline fun isJar(filename: String): Boolean {
    return filename.endsWith(".jar") || filename.endsWith(".zip") || filename.endsWith(".war")
}

/**
 * Entry for the CLI
 */
fun main(args: Array<String>) {

    //Enable some limitation on where DTD can connect
    sandboxUrlHandler()

    if(args.isEmpty()) {
        help()
    }
    else {
        val archive:String = args[0]

        val f = File(archive)
        val currentDir = System.getProperty("user.dir")
        val reportName = "${f.name}-dtd-report.md"
        val dtdFinder = DtdFinder(MarkdownReporter(currentDir, reportName))

        if(f.isFile)
            if(f.extension != null && (f.extension == "jar" || f.extension == "zip"))
                dtdFinder.scanZipFile(f)
            else //Assumes it is a tar file by default
                dtdFinder.scanTarFile(f)
        else
            dtdFinder.scanDirectory(f)

        println("")
        println("Report written to $reportName")
    }
}

/**
 * Few DTDs attempt to import external DTDs.
 * Those external DTDs could be a requirements to properly loading some components.
 * However, few are broken URL for which connection will timeout.
 */
fun sandboxUrlHandler() {

    URL.setURLStreamHandlerFactory(URLStreamHandlerFactory { protocol ->
        if (arrayOf("http","https").contains(protocol) ) {
            object:sun.net.www.protocol.http.Handler() {
                @Throws(IOException::class)
                override fun openConnection(url: URL): URLConnection {
                    //println(" [X] Blocking access to $url")
                    return javaClass.getResource("/empty.txt").openConnection()
                }
            }

        }
        else if (arrayOf("file").contains(protocol) ) {
            sun.net.www.protocol.file.Handler()
        }
        else {
            null
        }
    });

}

fun help() {
    println("DTD-Finder")
    println("Usage: java -jar dtd-finder.jar {archive.tar}")
    println(" -or-")
    println("Usage: java -jar dtd-finder.jar {directory}")
    println("")
}
