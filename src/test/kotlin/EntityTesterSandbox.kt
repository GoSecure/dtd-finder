import java.io.FileInputStream

fun main(args: Array<String>) {


    val currentDir = System.getProperty("user.dir")


    //val samplesDtd = arrayOf("cim20.dtd", "yelp-dtd/docbookx.dtd", "jspxml.dtd",  "mbeans-descriptors.dtd", "scrollkeeper-omf.dtd", "sip-app_1_0.dtd")
    val samplesDtd = arrayOf("mbeans-descriptors.dtd")

    for(dtd:String in samplesDtd) {
        println()
        println(" == DTD: $dtd ==")
        try {
            //val dtdPath = "$currentDir/sample_dtds/$dtd"
            val dtdPath = EntityTesterTest().javaClass.getResource(dtd).path
            val entityTester = EntityTester()

            val entitiesToTest = entityTester.listOverridableEntitiesXerces(FileInputStream(dtdPath))
            entityTester.findInjectableEntity(dtdPath,dtdPath,entitiesToTest,EchoReporter())
        }
        catch (e:Exception) {
            println(e.message)
            e.printStackTrace()
        }
    }

}
