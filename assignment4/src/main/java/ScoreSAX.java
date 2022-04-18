import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScoreSAX extends DefaultHandler {

    List<Score> failList;
    Score currScore;
    String currTag;

    // 提取不及格的成绩
    public static void extractFailList(String in, String out){
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser=factory.newSAXParser();
            ScoreSAX saxHandler = new ScoreSAX();
            File f=new File(in);
            parser.parse(f,saxHandler);
            List<Score> failList = saxHandler.getFailList();
            createXML4(out,failList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 生成XML文档4.xml
    public static void createXML4(String out, List<Score> list){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.newDocument();
            Element scoreList = doc.createElement("课程成绩列表");
            scoreList.setAttribute("xmlns","http://jw.nju.edu.cn/schema");
            scoreList.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            scoreList.setAttribute("xsi:schemaLocation","http://jw.nju.edu.cn/schema ScoreList.xsd");
            doc.appendChild(scoreList);
            for(Score s:list){
                Element courseScore = doc.createElement("课程成绩");
                courseScore.setAttribute("课程编号",s.getId());
                courseScore.setAttribute("成绩性质",s.getType());

                Element score = doc.createElement("成绩");
                Element stuId = doc.createElement("学号");
                stuId.setTextContent(s.getStudendId());
                Element grade = doc.createElement("得分");
                grade.setTextContent(s.getScore());

                score.appendChild(stuId);
                score.appendChild(grade);
                courseScore.appendChild(score);
                scoreList.appendChild(courseScore);
            }
            // 创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            // 创建 Transformer对象
            Transformer tf = tff.newTransformer();

            // 输出内容是否使用换行
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            // 创建xml文件并写入内容
            tf.transform(new DOMSource(doc), new StreamResult(new File(out)));
            System.out.println("生成XML文档4.xml成功");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("生成XML文档4.xml失败");
        }
    }

    public List<Score> getFailList(){
        return failList;
    }
    /* 此方法有三个参数
       arg0是传回来的字符数组，其包含元素内容
       arg1和arg2分别是数组的开始位置和结束位置 */
    @Override
    public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
        String content = new String(arg0, arg1, arg2);
        System.out.println(content);
        if("学号".equals(currTag)) currScore.setStudendId(content);
        if("得分".equals(currTag)) currScore.setScore(content);

        super.characters(arg0, arg1, arg2);
    }

    /*arg0是名称空间
      arg1是包含名称空间的标签，如果没有名称空间，则为空
      arg2是不包含名称空间的标签
      arg3很明显是属性的集合 */
    @Override
    public void startElement(String ns, String fullName, String localName,
                             Attributes arg3) throws SAXException {
        System.out.println("开始解析元素 " + localName);
        if(localName.equals("课程成绩")) currScore=new Score();

        if (arg3 != null) {
            for (int i = 0; i < arg3.getLength(); i++) {
                // getQName()是获取属性名称，
                switch (arg3.getQName(i)){
                    case "课程编号":
                        currScore.setId(arg3.getValue(i));
                        break;
                    case "成绩性质":
                        currScore.setType(arg3.getValue(i));
                        break;
                }
            }
        }
        currTag = localName;
    }

    /* arg0是名称空间
       arg1是包含名称空间的标签，如果没有名称空间，则为空
       arg2是不包含名称空间的标签 */
    @Override
    public void endElement(String ns, String fullName, String localName)
            throws SAXException {
        System.out.println("结束解析元素  " + localName);
        if(currScore!=null&&localName.equals("课程成绩")){
            int score = Integer.parseInt(currScore.getScore());
            if(score<60){
                failList.add(currScore);
            }
            currScore=null;
        }
        currTag=null;
    }





    @Override
    public void startDocument() throws SAXException {
        System.out.println("…………开始解析文档…………\n");
        failList = new ArrayList<>();
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("\n…………结束解析文档…………");
        super.endDocument();
    }

}
