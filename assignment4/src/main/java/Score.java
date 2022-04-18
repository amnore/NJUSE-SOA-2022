public class Score {
//    <课程成绩 课程编号="111111" 成绩性质="平时成绩">
//        <成绩>
//            <学号>191250150</学号>
//            <得分>90</得分>
//        </成绩>
//    </课程成绩>
    String id;
    String type;
    String studendId;
    String score;

    public Score(){}

    public Score(String id, String type, String studendId, String score) {
        this.id = id;
        this.type = type;
        this.studendId = studendId;
        this.score = score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStudendId(String studendId) {
        this.studendId = studendId;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStudendId() {
        return studendId;
    }

    public String getScore() {
        return score;
    }
}
