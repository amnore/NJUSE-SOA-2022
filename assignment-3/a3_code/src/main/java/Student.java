public class Student {
  private   String id;
  private   String name;
  private   String email;
  private   String age;
  private   String gender;
  private   String major;
  private   String department;

    public Student(String id, String name, String email, String age, String gender, String major, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.major = major;
        this.department = department;
    }


    public void setAge(String age) {
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getId() {
        return id;
    }

    public String getMajor() {
        return major;
    }

    public String getName() {
        return name;
    }
}
