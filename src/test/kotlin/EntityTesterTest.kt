import org.testng.Assert.assertEquals
import org.testng.Assert.fail
import org.testng.annotations.Test
import kotlin.test.assertTrue

class EntityTesterTest {

    fun assertCountInjectableEntityForDtd(nbEntities:Int, dtdFile:String) {
        val dtdFileURL = javaClass.getResource(dtdFile)
        if(dtdFileURL == null) {
            fail("Unable to find DTD file : $dtdFile")
        }
        val entityTester = EntityTester()

        var entitiesInjectable = HashSet<String>()

        val entitiesToTest = entityTester.listOverridableEntities(dtdFileURL)
        entityTester.findInjectableEntity(dtdFileURL.path,dtdFileURL.path,entitiesToTest, object : XxeReporter {
            override fun newPayload(dtd: String, entityName: String, xmlPayload: String) {
                entitiesInjectable.add(entityName)
            }
        })

        assertEquals(entitiesInjectable.size, nbEntities, "This is not the number of injectable entities for this DTD file. (Greater = Improvement / Lower = Regression) DTD: $dtdFile")
    }

    fun assertPayloadFoundForDtd(payload:String, dtdFile:String) {
        val dtdFileURL = javaClass.getResource(dtdFile)
        if(dtdFileURL == null) {
            fail("Unable to find DTD file : $dtdFile")
        }
        val entityTester = EntityTester()

        var payloadFound = false

        val entitiesToTest = entityTester.listOverridableEntities(dtdFileURL)
        entityTester.findInjectableEntity(dtdFileURL.path,dtdFileURL.path,entitiesToTest, object : XxeReporter {
            override fun newPayload(dtd: String, entityName: String, xmlPayload: String) {
                if(xmlPayload.contains(payload)) {
                    payloadFound = true
                }
            }
        })

        assertTrue(payloadFound, "Payload '$payload' not found in '$dtdFile'")
    }

    @Test
    fun testInlineEntityAndAttributeList() {

        assertCountInjectableEntityForDtd(13, "/jspxml.dtd")

        // #5 ATTLIST
        assertPayloadFoundForDtd("""
            <!ATTLIST attxx aa "bb
            """.trimIndent(),"/jspxml.dtd")

        // #3 Open Parentheses in ELEMENT
        assertPayloadFoundForDtd("""
            (aa)>
            """.trimIndent(),"/jspxml.dtd")
    }

    @Test
    fun testDocbookx() {
        assertCountInjectableEntityForDtd(19, "/yelp-dtd/docbookx.dtd")

        // #1 Inline
        assertPayloadFoundForDtd("""
            <!ENTITY % ISOamsa '
            """.trimIndent(),"/yelp-dtd/docbookx.dtd")

    }

    @Test
    fun testScrollkeeper() {
        assertCountInjectableEntityForDtd(1, "/scrollkeeper-omf.dtd")

        // #2 Simple ELEMENT injection inside tag
        assertPayloadFoundForDtd("""
            <!ENTITY % url.attribute.set '>
            """.trimIndent(),"/scrollkeeper-omf.dtd")
        assertPayloadFoundForDtd("""
            <!ELEMENT aa "bb"'>
            """.trimIndent(),"/scrollkeeper-omf.dtd")
    }

    @Test
    fun testSipApp() {
        assertCountInjectableEntityForDtd(1, "/sip-app_1_0.dtd")

        // #4 Close Parentheses in ELEMENT
        assertPayloadFoundForDtd("""
            aaa)>
            """.trimIndent(),"/sip-app_1_0.dtd")
        assertPayloadFoundForDtd("""
            <!ELEMENT aa (bb'>
            """.trimIndent(),"/sip-app_1_0.dtd")
    }

    @Test
    fun testMbeans() {
        assertCountInjectableEntityForDtd(4, "/mbeans-descriptors.dtd")
    }

    @Test
    fun testCim() {
        assertCountInjectableEntityForDtd(9, "/cim20.dtd")
    }

    @Test
    fun testServletApi() {
        //This DTD is very common in JAR dependencies. It point to a second DTD.
        //It cause failure during the DTD parsing with Xerces.
        //This test validate the fallback where entities are extracted with RegEx.

        assertCountInjectableEntityForDtd(34, "/servlet-api/XMLSchema.dtd")
    }

    @Test
    fun testXmlCore() {
        assertCountInjectableEntityForDtd(27, "/xmlcore/catalog.dtd")

        // #2 Simple ELEMENT injection inside tag
        assertPayloadFoundForDtd("""
            <!ENTITY % partialPublicIdentifier '
            """.trimIndent(), "/xmlcore/catalog.dtd")


    }


    @Test
    fun testWiresharkSmil() {
        //FIXME: Find a pattern of injection for this file
        assertCountInjectableEntityForDtd(0, "/wireshark/smil.dtd")
    }

    @Test
    fun testPerfsuite() {
        assertCountInjectableEntityForDtd(1, "/perfsuite/hwpcreport.dtd")
        assertCountInjectableEntityForDtd(1, "/perfsuite/hwpcreport-0.3.dtd")

        assertPayloadFoundForDtd("""
            <!ENTITY % machineinfo.dtd '
            """.trimIndent(), "/perfsuite/hwpcreport.dtd")

        assertCountInjectableEntityForDtd(1, "/perfsuite/hwpcprofilereport.dtd")
        assertCountInjectableEntityForDtd(1, "/perfsuite/hwpcprofilereport-0.2.dtd")
        assertCountInjectableEntityForDtd(1, "/perfsuite/hwpcprofilereport-0.3.dtd")


        assertPayloadFoundForDtd("""
            <!ENTITY % machineinfo.dtd '
            """.trimIndent(), "/perfsuite/hwpcprofilereport.dtd")

        assertCountInjectableEntityForDtd(1, "/perfsuite/multihwpcreport.dtd")
        assertCountInjectableEntityForDtd(1, "/perfsuite/multihwpcreport-0.3.dtd")

        assertPayloadFoundForDtd("""
            <!ENTITY % hwpcreport.dtd '
            """.trimIndent(), "/perfsuite/multihwpcreport.dtd")
    }
}