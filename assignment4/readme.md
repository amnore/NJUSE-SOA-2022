#### Assignment4

##### XSLT:

采用先对课程编号、成绩性质排序  已达到在每个课程分类、成绩类型内对成绩单独排序的效果

需要注意的是xslt对于命名空间的处理    由于原先文档中存在命名空间

 xpath时需要在nodetest时对命名空间进行引用

代码为XSLT_Transform.java      xsl文件为transform.xsl

##### SAX:

主要逻辑在ScoreSAX.java中，ScoreSAX继承自org.xml.sax.helpers.DefaultHandler，覆写startElement, endElement接口实现对XML文档的处理

XML文档3.xml -> List<Score> failList -> XML文档4.xml



