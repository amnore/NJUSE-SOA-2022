
import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;


/**
 * <p>XML Schema约束验证测试类</p>
 * @author  <a href="mailto:code727@gmail.com">Daniele</a>
 * @version 1.0.0, 2013-7-14
 * @see
 * @since   AppDemo1.0.0
 */
public class JAXPTest {

    public static void main(String[] args) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaSource = new File("target/classes/Student.xsd");
        try {
            Schema schema = factory.newSchema(schemaSource);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setXIncludeAware(true);
            builderFactory.setNamespaceAware(true);
            builderFactory.setSchema(schema);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.parse(new File("target/classes/XML文档1.xml"));
            System.out.println("XML有效!");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

}