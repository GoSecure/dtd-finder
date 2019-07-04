import java.io.FileInputStream

fun main(args: Array<String>) {


    val currentDir = System.getProperty("user.dir")


    val samplesDtd = arrayOf("cim20.dtd", "yelp-dtd/docbookx.dtd", "jspxml.dtd", "scrollkeeper-omf.dtd", "sip-app_1_0.dtd")

    for(dtd:String in samplesDtd) {
        println()
        println(" == DTD: $dtd ==")
        try {
            val dtdPath = "$currentDir/sample_dtds/$dtd"
            val entityTester = EntityTester()

            val entitiesToTest = entityTester.inspectDtd(FileInputStream(dtdPath))
            entityTester.findInjectableEntity(dtdPath,entitiesToTest)
        }
        catch (e:Exception) {
            println(e.message)
            e.printStackTrace()
        }
    }

}
