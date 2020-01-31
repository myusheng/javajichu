package day14;

public class Student extends Person implements Move , Study {
   /* public Student() {

    }*/
   public String school;
   private String privateField;

   public  Student() {
       System.out.println("调用的是public  Student");

   }
   public  Student(String school) {
       this.school = school;
       System.out.println("调用的是public  Student(String school)");
   }
   private Student(String name, int age) {
       this.name = name;
       this.age = age;
       System.out.println("调用的是private Student(String name, int age)");
   }

   public void showInfo() {
       System.out.println("学校是" + this.school);
   }
    @Override
    public void moveType() {
        System.out.println("骑自行车");
    }
    @Override
    public void study() {
        System.out.println("学习的中学知识");
    }
    private void  test(String name) {
       System.out.println("这个是test(String name)私有方法");

    }
    private String getSchool() {
       return this.school;
    }
    public void setInfo(String name, String school) {
       this.name = name;
       this.school = school;
       System.out.println("这个是setInfo(String name, String school)方法");
    }
    public void setInfo(int age) {
        System.out.println("这个是setInfo(int age)方法");
    }
}
