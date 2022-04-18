import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLT_Transform {

    public static void main(String[] args) {
        String xmlPath="XML文档2.xml";
        String xslPath="transform.xsl";
        String out="XML文档3.xml";
        createXml(xmlPath,xslPath,out);




    }


    public static void createXml(String xmlPath,String xslPath,String out){

        // 创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();


        // 创建Transformer对象
        Transformer tf = null;
        try {
            Source xsl = new StreamSource(xslPath);
            tf = tff.newTransformer(xsl);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }


        //设置输出数据时换行
        tf.setOutputProperty(OutputKeys.INDENT, "yes");


        File file1=new File(out);
        // 使用Transformer的transform()方法将DOM树转换成XML
        try {
            Source xml = new StreamSource(xmlPath);
            tf.transform(xml, new StreamResult(file1));
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }
}
