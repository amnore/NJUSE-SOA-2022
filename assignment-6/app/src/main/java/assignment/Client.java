package assignment;

import jakarta.xml.soap.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        //正常修改
        Edit newInfo = new Edit("期末成绩",191250065,81,1);
        System.out.println("======正常修改======");
        System.out.println("发送数据");
        System.out.println(client.generatePostMsg(newInfo));
        System.out.println("接收数据");
        System.out.println(client.doPost(newInfo));
        //有属性为空
        newInfo = new Edit("",191250065,81,1);
        System.out.println("======有属性为空======");
        System.out.println("发送数据");
        System.out.println(client.generatePostMsg(newInfo));
        System.out.println("接收数据");
        System.out.println(client.doPost(newInfo));

    }

    private String doPost(Edit editScore) {
        try {
            URL url = new URL("http://localhost:8080/score");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            String msg = generatePostMsg(editScore);
            PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
            printWriter.write(msg);
            printWriter.flush();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String generatePostMsg(Edit entity) {
        try {
            MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            SOAPMessage message = messageFactory.createMessage();
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            soapEnvelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
            SOAPHeader soapHeader=soapEnvelope.getHeader();
            SOAPElement headelement = soapHeader.addChildElement(soapEnvelope.createName("transaction", "t", "http://www.w3school.com.cn/transaction/")).addTextNode("5");
            headelement.addAttribute(soapEnvelope.createQName("encodingStyle", "env"), "http://www.w3.org/2001/12/soap-encoding");
            headelement.addAttribute(soapEnvelope.createQName("mustUnderstand", "env"), "false");
            SOAPBody soapBody = soapEnvelope.getBody();
            SOAPElement element = soapBody.addChildElement(soapEnvelope.createName("课程成绩列表", "jw", "http://jw.nju.edu.cn/schema"));
            SOAPElement courseElement = element.addChildElement(element.createQName("课程成绩", "jw"));
            courseElement.addAttribute(element.createQName("成绩性质","jw"),
                    entity.getType());
            courseElement.addAttribute(element.createQName("课程编号", "jw"),
                    entity.getCourseId().toString());
            SOAPElement scoreElement = courseElement.addChildElement(courseElement.createQName("成绩", "jw"));
            scoreElement.addChildElement(scoreElement.createQName("学号", "jw"))
                    .addTextNode(entity.getId().toString());
            scoreElement.addChildElement(scoreElement.createQName("得分", "jw"))
                    .addTextNode((entity.getGrade()).toString() + "");
            message.saveChanges();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            message.writeTo(stream);
            return new String(stream.toByteArray(), "utf-8");
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
