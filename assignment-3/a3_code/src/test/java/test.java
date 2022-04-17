import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class test {
    static final String JAXP_SCHEMA_LANGUAGE =
            "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    static final String W3C_XML_SCHEMA ="http://www.w3.org/2001/XMLSchema";
    static final  String Schema_Source="http://java.sun.com/xml/jaxp/properties/schemaSource";

    static final  String XsdLocation= "file:C:\\Users\\84158\\Desktop\\OneDrive - 南京大学\\pc\\myfile\\learning_materia\\大三下\\soa\\assignment\\3\\a3_code\\target\\classes\\Student.xsd" ;
    static final  String XsdLocation1= "file:C:\\Users\\84158\\Desktop\\OneDrive - 南京大学\\pc\\myfile\\learning_materia\\大三下\\soa\\assignment\\3\\a3_code\\src\\main\\resources\\Course.xsd" ;
    static final  String XsdLocation2= "file:C:\\Users\\84158\\Desktop\\OneDrive - 南京大学\\pc\\myfile\\learning_materia\\大三下\\soa\\assignment\\3\\a3_code\\src\\main\\resources\\Department.xsd" ;
    static final  String XsdLocation3= "file:C:\\Users\\84158\\Desktop\\OneDrive - 南京大学\\pc\\myfile\\learning_materia\\大三下\\soa\\assignment\\3\\a3_code\\src\\main\\resources\\PersonInfo.xsd" ;
    static final  String XsdLocation4= "file:C:\\Users\\84158\\Desktop\\OneDrive - 南京大学\\pc\\myfile\\learning_materia\\大三下\\soa\\assignment\\3\\a3_code\\src\\main\\resources\\ScoreList.xsd" ;
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String path="target/classes/XML文档1.xml";
        File file=new File(path);
        DocumentBuilderFactory docBuildFactory =DocumentBuilderFactory.newInstance();
//        docBuildFactory.setValidating(false);
        docBuildFactory.setValidating(true);

        SchemaFactory schemaFactory =SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        String xsdPath="target/classes/Student.xsd";
        File xsdFile=new File(xsdPath);
        Schema schema =schemaFactory.newSchema(xsdFile);
        System.out.println(schema);


        //验证器实施/检查此对象表示的约束集。Validator -> 根据 Schema 检查 XML 文档的处理器。
        Validator validator = schema.newValidator();
        System.out.println(0);
        //验证指定的输入。 Source -> 实现此接口的对象包含充当源输入（XML 源或转换指令）所需的信息
        Source source = new StreamSource(path);
        validator.validate(source);
        System.out.println(1);

        docBuildFactory. setAttribute(JAXP_SCHEMA_LANGUAGE,W3C_XML_SCHEMA);
        docBuildFactory.setAttribute(Schema_Source,XsdLocation+' '+XsdLocation1+' '+XsdLocation2+' '+XsdLocation3+' '+XsdLocation4);

        docBuildFactory.setNamespaceAware(true);
        docBuildFactory.setXIncludeAware(true);
        docBuildFactory.setCoalescing(true);
//        docBuildFactory.setSchema(schema);
        docBuildFactory.setExpandEntityReferences(true);

        docBuildFactory.setIgnoringElementContentWhitespace(false);
        DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();

        Document doc = docBuilder.parse(path);





        System.out.println(2);
        NodeList nodeList =doc.getFirstChild().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(    nodeList.item(i) );
        }




        // 创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();


        // 创建Transformer对象
        Transformer tf = tff.newTransformer();


        //设置输出数据时换行
        tf.setOutputProperty(OutputKeys.INDENT, "yes");

        doc.getFirstChild().appendChild( nodeList.item(0));
        String path1="target/classes/XML文档2.xml";
        File file1=new File(path1);
        // 使用Transformer的transform()方法将DOM树转换成XML
        tf.transform(new DOMSource(doc), new StreamResult(file1));



    }


}
