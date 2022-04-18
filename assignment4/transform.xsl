<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns="http://jw.nju.edu.cn/schema"
                xmlns:nju="http://www.nju.edu.cn/schema"
                xmlns:jw="http://jw.nju.edu.cn/schema"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                >



    <xsl:output method="xml" indent="yes" standalone="yes"></xsl:output>

    <xsl:template match="nju:studentList">
        <xsl:element name="课程成绩列表" namespace="http://jw.nju.edu.cn/schema" >
            <xsl:attribute name="xsi:schemaLocation">http://jw.nju.edu.cn/schema ScoreList.xsd</xsl:attribute>
            <xsl:for-each select="nju:student/jw:grades/jw:课程成绩">
                <xsl:sort select="attribute::课程编号"></xsl:sort>
                <xsl:sort select="attribute::成绩性质"></xsl:sort>
                <xsl:sort select="jw:成绩/jw:得分" data-type="number" order="descending"></xsl:sort>
                <xsl:sort select="parent::*/preceding-sibling::*[2]/jw:学号"></xsl:sort>

                <xsl:element name="课程成绩">
                    <xsl:attribute name="课程编号">
                        <xsl:value-of select="attribute::课程编号"/>
                    </xsl:attribute>
                    <xsl:attribute name="成绩性质">
                        <xsl:value-of select="attribute::成绩性质"/>
                    </xsl:attribute>

                    <xsl:element name="成绩">
                        <xsl:element name="学号"><xsl:value-of select="jw:成绩/jw:学号"/></xsl:element>
                        <xsl:element name="得分"><xsl:value-of select="jw:成绩/jw:得分"/></xsl:element>
                    </xsl:element>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>



</xsl:stylesheet>