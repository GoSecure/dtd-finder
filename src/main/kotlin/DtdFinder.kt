
import java.io.File

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.FileInputStream


fun isDtd(filename:String): Boolean {
    return filename.endsWith(".dtd")
}

fun isJar(filename: String) : Boolean {
    return filename.endsWith(".jar")
}

fun analyzingJar(zip:ByteArray,file:String) {
    val zipInputStream = ZipArchiveInputStream(ByteArrayInputStream(zip))

    var zipEntry = zipInputStream.nextZipEntry
    while (zipEntry != null) {

        zipEntry.name

        if(!zipInputStream.canReadEntryData(zipEntry)) {
            println("Unable to read $file")
        }else {

            if(isDtd(zipEntry.name)) {
                println(zipEntry.name)

                zipEntry

                var fileContent = ByteArray(zipEntry.size.toInt())
                IOUtils.readFully(zipInputStream, fileContent)
                println(String(fileContent))
            }
        }
        zipEntry = zipInputStream.nextZipEntry
    }


}

fun main(args: Array<String>) {

    //val archive:String = args[0]
    val archive = "C:\\Users\\parteau\\Desktop\\"+"02_jersey_deserialization_web_1.tar"


    val myTarFile = TarArchiveInputStream(FileInputStream(File(archive)))

    var entry: TarArchiveEntry? = null
    var fileName: String

    entry = myTarFile.nextTarEntry;
    while (entry != null) {
        fileName = entry.name

        val content = ByteArray(entry.size.toInt())
        myTarFile.read(content, 0, content.size)

        if((isDtd(fileName) || isJar(fileName))) {

            println("File Name : $fileName")
            //println("Size of the File is: " + entry.size)
            //println("Byte Array length: " + content.size)

            //println(String(content).substring(0..5)) //Preview content

            if(isJar(fileName)) {
                analyzingJar(content,entry.name)
            }
        }

        entry = myTarFile.nextTarEntry
    }

    myTarFile.close()
}