mcds_cp=./../../dist/gvcl/gvcl.jar:./../../dist/themis/jars/themis.jar:./../../dist/mcds/jars/mcds.jar:./../../extlib/servlet/servlet-2_3.zip:./../../extlib/ejbs/ejb-2_1-api.jar:./../../extlib/weblogic/weblogic.jar:./../../extlib/weblogic/xbean.jar:./../../extlib/commons-codec/commons-code-1.3.jar:./../../extlib/commons-logging/commons-logging-1.1.jar:./../../extlib/commons-HttpClient/commons-httpclient-3.0.1.jar:./../../extlib/commons-codec/commons-codec-1.3.jar

java -debug -classpath $mcds_cp $*
