import com.sun.xml.internal.ws.util.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
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
import java.util.Random;

public class DomParser {
     DocumentBuilderFactory docBuildFactory;
     boolean ignoreWhiteSpace=false;
     String [] courseList={"111111","222222","333333","444444","555555"};
     Student [] studentsList={
             //小组10
             new Student("191250150","吴圣鑫","191250150@smail.nju.edu.cn","21","male","软件工程","0"),
             new Student("191250128","孙钰昇","191250128@smail.nju.edu.cn","20","male","软件工程","0"),
             new Student("191250065","康森","191250065@smail.nju.edu.cn","21","male","软件工程","0"),
             new Student("191250206","郑伟鑫","191250206@smail.nju.edu.cn","20","male","软件工程","0"),
             //小组9
             new Student("191250089","刘晨","191250089@smail.nju.edu.cn","20","male","软件工程","0"),
             new Student("191250175","杨晖","191250175@smail.nju.edu.cn","21","male","软件工程","0"),
             new Student("191830164","武宇杰","191830164@smail.nju.edu.cn","21","male","软件工程","2"),
             new Student("191850059","胡越","1918500590@smail.nju.edu.cn","20","female","软件工程","1"),
             new Student("191250158","谢昊澄","191250158@smail.nju.edu.cn","20","male","软件工程","0"),
             new Student("191250099","陆健","191250099@smail.nju.edu.cn","20","male","软件工程","0"),
             //小组11
             new Student("191250200","张子蓉","191250200@smail.nju.edu.cn","21","female","软件工程","0"),
             new Student("191250120","石善羽","191250120@smail.nju.edu.cn","21","male","软件工程","0"),
             new Student("191250062","蒋坤鹏","191250062@smail.nju.edu.cn","21","male","软件工程","0"),
             new Student("191250002","蔡文俊","191250002@smail.nju.edu.cn","20","male","软件工程","0"),
             new Student("181250014","陈文龙","181250014@smail.nju.edu.cn","22","male","软件工程","0")

     };


    public static void main(String[] args) {
        String path="XML文档1.xml";
        String xsdPath="Student.xsd";
        DomParser domParser=new DomParser();

        //设置是否跳过空白字符
        domParser.setIgnoreWhiteSpace(true);
//        domParser.setIgnoreWhiteSpace(false);

        //是否启用验证
        Document doc= domParser.parseByValidate(path,xsdPath);
//        Document doc= domParser.parseByNotValidate(path);

        assert doc != null;
        Element element=doc.getDocumentElement();

        printNode(element);
        System.out.println("************");

        //文档的操作是基于删除空白节点的所以请使用跳过空白字符的方法
        if(domParser.ignoreWhiteSpace) {
            domParser.opWithDOM(doc);

            String outPath = "XML文档2.xml";
            domParser.cresteXml(outPath, doc);
        }


//        NodeList nodeList =doc.getDocumentElement().getChildNodes();
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            System.out.println(    nodeList.item(i) );
//        }
    }




    DomParser(){
        this.docBuildFactory=DocumentBuilderFactory.newInstance();
    }
    DomParser(boolean ignoreWhiteSpace){
        this.docBuildFactory=DocumentBuilderFactory.newInstance();
        this.ignoreWhiteSpace=ignoreWhiteSpace;
    }

    public void setIgnoreWhiteSpace(boolean ignoreWhiteSpace) {
        this.ignoreWhiteSpace = ignoreWhiteSpace;
    }


    public void opWithDOM(Document doc){
        Node student=  doc.getFirstChild().cloneNode(true);
        doc.removeChild(doc.getFirstChild());
        NodeList nodeList =student.getChildNodes();

        //根节点以及命名空间
        Element root= (Element) doc.createElementNS("http://www.nju.edu.cn/schema","nju:studentList");
        root.setAttribute("xmlns","http://jw.nju.edu.cn/schema");
        root.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xsi:schemaLocation","http://www.nju.edu.cn/schema StudentList.xsd " +
                        "    http://jw.nju.edu.cn/schema Student.xsd");

        //复制解析的xml1 student节点
        Node student1=doc.createElement("nju:student");
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(    nodeList.item(i) );
            Node temp =nodeList.item(i).cloneNode(true) ;
            student1.appendChild(temp);
        }
        //处理stdent1为需要的格式
        Node grades=student1.getChildNodes().item(8);

        //作业成绩
        Node hwScore= grades.getChildNodes().item(1);
        //期中成绩
        Node midScore=grades.getChildNodes().item(2);
        grades.removeChild(hwScore);
        grades.removeChild(midScore);

        NodeList oneCourseScoreList=grades.getChildNodes();
        int size=oneCourseScoreList.getLength();
        for (int i = 0; i < 4; i++) {
            Node comment=doc.createComment("******************课程"+(i+2)+"***************************");
            grades.appendChild(comment);
            for (int j = 0; j < size; j++) {
                Node temp=oneCourseScoreList.item(j).cloneNode(true);
                grades.appendChild(temp);
            }
        }
        Node comment=doc.createComment("******************课程"+1+"***************************");

        grades.insertBefore(comment, grades.getFirstChild());
        setCourseGrade1(grades,"191250013");



        Node stuComment=doc.createComment("##########学生"+(1)+"##########"+"学生"+1+"##########"+"学生"+(1)+"##########"+"学生"+(1)+"##########");
        root.appendChild(stuComment);
        root.appendChild(student1);

        //student1处理完毕

        for (int i = 0; i < studentsList.length; i++) {
            Node newStudent=cloneNodeAndSet(student1,studentsList[i]);
            Node stuComment1=doc.createComment("##########学生"+(i+2)+"##########"+"学生"+(i+2)+"##########"+"学生"+(i+2)+"##########"+"学生"+(i+2)+"##########");
            root.appendChild(stuComment1);
            root.appendChild(newStudent);
        }



         doc.appendChild(root);


    }
    public Node cloneNodeAndSet(Node student1,Student studentinfo){
        Node student2=student1.cloneNode(true);
        setNjuId(student2,studentinfo.getId());
        setNjuName(student2,studentinfo.getName());
        setNjuContact(student2,studentinfo.getEmail());
        setNjuAge(student2,studentinfo.getAge());
        setNjuDapt(student2,studentinfo.getDepartment());
        setNjuGender(student2,studentinfo.getGender());
        setStudentId(student2,studentinfo.getId());
        setMajor(student2,studentinfo.getMajor());
        Node grades1=student2.getChildNodes().item(8);
        setCourseGrade1(grades1,studentinfo.getId());

        return student2;
    }

    public void setNjuId(Node root,String mes){
        root.getFirstChild().setTextContent(mes);
    }
    public void setNjuName(Node root,String mes){
        root.getChildNodes().item(1).setTextContent(mes);
    }
    public void setNjuContact(Node root,String mes){
        root.getChildNodes().item(2).setTextContent(mes);
    }
    public void setNjuAge(Node root,String mes){
        root.getChildNodes().item(3).setTextContent(mes);
    }
    public void setNjuDapt(Node root,String mes){
        root.getChildNodes().item(4).setTextContent(mes);
    }
    public void setNjuGender(Node root,String mes){
        root.getChildNodes().item(5).setTextContent(mes);
    }
    public void setStudentId(Node root,String mes){
        root.getChildNodes().item(6).setTextContent(mes);
    }
    public void setMajor(Node root,String mes){
        root.getChildNodes().item(7).setTextContent(mes);;
    }

    public void setCourseGrade1(Node grades,String id){
        NodeList gradesList= grades.getChildNodes();
        for (int i = 0; i < 5; i++) {
            //平时、期末 总评
            Node s1=gradesList.item(4*i+1);
            Node s2=gradesList.item(4*i+2);
            Node s3=gradesList.item(4*i+3);

            s1.getAttributes()
                    .getNamedItem("课程编号")
                    .setNodeValue(courseList[i]);
            s2.getAttributes()
                    .getNamedItem("课程编号")
                    .setNodeValue(courseList[i]);
            s3.getAttributes()
                    .getNamedItem("课程编号")
                    .setNodeValue(courseList[i]);
            Random random=new Random();
            int score= random.nextInt(100);
            setSingleCourseScoreItem(s1,id,score);
            score=random.nextInt(100);
            setSingleCourseScoreItem(s2,id,score);
            score=random.nextInt(100);
            setSingleCourseScoreItem(s3,id,score);




        }

    }


    //@param 是课程成绩
    public void setSingleCourseScoreItem(Node singleScoreItem,String id,int score){
        //学号
        singleScoreItem.getFirstChild().getFirstChild().setTextContent(id);
        //得分
        singleScoreItem.getFirstChild().getLastChild().setTextContent(String.valueOf( score));
    }





    public void cresteXml(String path,Document doc){

        // 创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();


        // 创建Transformer对象
        Transformer tf = null;
        try {
            tf = tff.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }


        //设置输出数据时换行
        tf.setOutputProperty(OutputKeys.INDENT, "yes");


        File file1=new File(path);
        // 使用Transformer的transform()方法将DOM树转换成XML
        try {
            tf.transform(new DOMSource(doc), new StreamResult(file1));
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }

    public  Document parseByValidate(String xmlPath, String xsdPath){
        File file=new File(xmlPath);
        File xsdFile=new File(xsdPath);


        //启用命名空间插件
        docBuildFactory.setNamespaceAware(true);
        //启用include功能
        docBuildFactory.setXIncludeAware(true);
        docBuildFactory.setIgnoringElementContentWhitespace(ignoreWhiteSpace);

        SchemaFactory schemaFactory =SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
        Schema schema =schemaFactory.newSchema(xsdFile);
        //载入Schema进行验证
        docBuildFactory.setSchema(schema);

            DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            return doc;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public  Document parseByNotValidate(String xmlPath){
        File file=new File(xmlPath);

        DocumentBuilderFactory docBuildFactory =DocumentBuilderFactory.newInstance();

        //启用命名空间插件
        docBuildFactory.setNamespaceAware(true);
        //启用include功能
        docBuildFactory.setXIncludeAware(true);
        docBuildFactory.setIgnoringElementContentWhitespace(ignoreWhiteSpace);

        try {
            DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            return doc;

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void printNode(Element e) {
        if (e.hasChildNodes()) {
            NodeList subList = e.getChildNodes();
            for (int i = 0; i < subList.getLength(); i++) {
                Node n = subList.item(i);
                System.out.println(    subList.item(i).toString() );

                if (n instanceof Element) {
                    printNode((Element) n);
                }
            }
        }
    }



}
