### 正常返回结果

post请求参数：

```xml
<env:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><env:Header><t:transaction xmlns:t="http://www.w3school.com.cn/transaction/" env:encodingStyle="http://www.w3.org/2001/12/soap-encoding" env:mustUnderstand="false">5</t:transaction></env:Header><env:Body><jw:课程成绩列表 xmlns:jw="http://jw.nju.edu.cn/schema"><jw:课程成绩 jw:成绩性质="期末成绩" jw:课程编号="1"><jw:成绩><jw:学号>191250065</jw:学号><jw:得分>81</jw:得分></jw:成绩></jw:课程成绩></jw:课程成绩列表></env:Body></env:Envelope>

```

输出数据：

```xml
<env:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope" xmlns="http://www.jw.nju.edu.cn/schema"><env:Header/><env:Body><课程成绩列表 xmlns=""><课程成绩 成绩性质="期末成绩" 课程编号="1" xmlns="http://www.jw.nju.edu.cn/schema"><成绩><学号>191250065</学号><得分>81</得分></成绩></课程成绩></课程成绩列表></env:Body></env:Envelope>

```

### 错误输入

post请求参数：

```xml
<env:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><env:Header><t:transaction xmlns:t="http://www.w3school.com.cn/transaction/" env:encodingStyle="http://www.w3.org/2001/12/soap-encoding" env:mustUnderstand="false">5</t:transaction></env:Header><env:Body><jw:课程成绩列表 xmlns:jw="http://jw.nju.edu.cn/schema"><jw:课程成绩 jw:成绩性质="期末成绩" jw:课程编号="1"><jw:成绩><jw:学号>191250065</jw:学号><jw:得分>81</jw:得分></jw:成绩></jw:课程成绩></jw:课程成绩列表></env:Body></env:Envelope>

```

返回参数：

```xml
<env:Envelope xmlns:env="http://www.w3.org/2003/05/soap-envelope" xmlns="http://www.jw.nju.edu.cn/schema"><env:Header/><env:Body><env:Fault><env:Code><env:Value>env:Receiver</env:Value></env:Code><env:Reason><env:Text xml:lang="zh-CN">Fault string, and possibly fault code, not set</env:Text><env:Text xml:lang="zh-CN">成绩性质，课程编号，学号，成绩均不可以为空</env:Text></env:Reason></env:Fault></env:Body></env:Envelope>
```

<img src="https://img-for-se.oss-cn-shanghai.aliyuncs.com/image-20220426202636702.png" alt="image-20220426202636702" style="zoom:50%;" />