# DTD Finder

Identify DTDs on filesystem snapshot and build XXE payloads using those local DTDs.

## Building the tool

```
$ mvn install
```

## Usage with docker image

1. Start/Build the docker image
```
$ docker run ...
```

2. Export the filesystem
```
$ docker export weblogic12 -o weblogic-12-dev.tar
```

3. Launch dtd-finder
```
$ java -jar dtd-finder-1.0-SNAPSHOT-all.jar weblogic-12-dev.tar

...
 [=] Found a DTD: /u01/oracle/wlserver/server/lib/consoleapp/webapp/WEB-INF/struts-config_1_2.dtd
Testing 9 entities : [%AttributeName, %BeanName, %Boolean, %ClassName, %Integer, %Location, %PropName, %RequestPath, %RequestScope]
 [+] The entity %AttributeName is injectable
 [+] The entity %BeanName is injectable
 [+] The entity %Boolean is injectable
 [+] The entity %ClassName is injectable
 [+] The entity %Integer is injectable
 [+] The entity %Location is injectable
 [+] The entity %PropName is injectable
 [+] The entity %RequestPath is injectable
 [+] The entity %RequestScope is injectable
...

Report written to weblogic-12-dev.tar-dtd-report.md
```

## Demonstration

![dtd-finder demnonstration](demos/dtd-finder-demo-1.gif)

