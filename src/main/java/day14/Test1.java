package day14;

import day14.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test1 {
    public static void main(String[] args)  {
   /*     Student student = new Student();
        Class clazz = student.getClass();
        System.out.println(student.getClass());//clazz对象中就包含对象student所属的Student类的所有信息
        //创建Class类的实例，四种方法
        Class c0 = Student.class;//通过类名.class创建指定类的Class实例
        Class c1 = clazz.getClass();//通过一个类的实例对象的getClass()方法。获取对应实例对象的类的Class实例
        //通过Class的静态方法forName()来获取一个类的Class实例
        try {
            Class c2 = Class.forName("day14.Student");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        还有一种是通过getClassLoader()方法  不常用
   */
        try {
           Class clazz = Class.forName("day14.Student");
    /*       //获取父类
           Class superClazz = clazz.getSuperclass();
           System.out.println("父类:" + superClazz.getName());
           //获取接口
            Class[] interfaces = clazz.getInterfaces();
            for(Class c : interfaces) {
                System.out.println("接口:" + c.getName());
            }
            //获取类的共有构造方法;修饰符表示构造方法的类型
             Constructor[] cons = clazz.getConstructors();
             for(Constructor c : cons) {
                 System.out.println("构造方法名称:" + c.getName());
                 System.out.println("构造方法" + c.getName() + "的修饰符" + c.getModifiers());
             }
             System.out.println("--------------------");
             //获取类的所有构造方法，包括私有和公有
             Constructor[] cons1 = clazz.getDeclaredConstructors();
             for (Constructor c:cons1) {
                 System.out.println("构造方法名称:" + c.getName());
                 System.out.println("构造方法" + c.getName() + "的修饰符" + c.getModifiers());
                 //getParameterType是获取构造方法的参数类型，有几个参数，数组的元素就有几个
                 Class[] paramclazz = c.getParameterTypes();
                 for (Class pc : paramclazz) {
                     System.out.println("构造方法" + c.getName() + "的参数类型" + pc.getName());
                 }
             }*/
             //如何用反射的构造方法创建对象
            try {
              /* Object obj = clazz.newInstance();//相当于调用Student类的无参公有的构造方法
                Student stu = (Student) obj;
                System.out.println(stu.school);*/
              Constructor c = clazz.getConstructor(String.class);//指定获取有一个参数并且为String类型公有的构造方法
                Student stu1 = (Student) c.newInstance("第一中学");//newInstance实例化对象
                System.out.println(stu1.school);

                //通过发射机制，可以强制的调用私有构造方法
               Constructor c1 = clazz.getDeclaredConstructor(String.class,int.class);//指定获取有两个构造参数（String，int）的构造方法
               c1.setAccessible(true);//解除私有的封装，下面就可以对这个私有的方法进行强制调用
                Student stu2 = (Student)c1.newInstance("zhangsan",12);
                System.out.println(stu2.name);

            } catch (Exception e) {
                e.printStackTrace();}
            //反射机制获取类的方法
            Method[] ms = clazz.getMethods();//获取到类的所有的公有方法
//            Method[] ms = clazz.getDeclaredMethods();//获取到类的所有方法，包括公有和私有
            for(Method c3:ms){
                System.out.println("方法名" + c3.getName());
                System.out.println("返回值类型" + c3.getReturnType());
                System.out.println("修饰符" + c3.getModifiers());
                Class[] pcs = c3.getParameterTypes();//获取方法的参数类型，是一个数组，方法有几个参数，数组就有几个元素
                if (pcs != null && pcs.length > 0) {

                    for(Class pc:pcs) {
                        System.out.println("参数类型" + pc.getName());
                    }

                }
                System.out.println("-------------------");
            }

            //反射机制获取类的属性和包
//            Field[] fs = clazz.getFields();//获取类的公有属性，包含父类的属性
            Field[] fs = clazz.getDeclaredFields();//获取本类（不包括父类的属性）所有的属性，包括私有
            for(Field f : fs) {
                System.out.println("修饰符" + f.getModifiers());
                System.out.println("属性的类型：" + f.getType());
                System.out.println("属性的名称：" + f.getName());
            }
            //获取类所属的包
            System.out.println("-----------");
            Package pk = clazz.getPackage();
            System.out.println(pk.getName() + "\n" + "-----------");
            //获取类的指定方法
/**
 * 注意：下面不论是反射调用setInfo还是test方法  都是调用的是obj对象的方法
 * obj实际上就是student对象
 */
            try {
                //m.invoke(obj,args);//参数1是需要实例化的对象，后面的参数是调用当前的方法实际参数
                Constructor con = clazz.getConstructor();//获取无参构造
                Object obj = con.newInstance();//使用无参构造创建对象
                Method m = clazz.getMethod("setInfo", String.class, String.class);
                m.invoke(obj,"zhangsan","第一中学");
                //调用私有方法
                Method m1 = clazz.getDeclaredMethod("test", String.class);
                m1.setAccessible(true);//解除私有的封装，下面可以强制调用私有方法
                m1.invoke(obj, "李四");
                //调用一个重载方法
                Method m2 = clazz.getMethod("setInfo", int.class);
                m2.invoke(obj,1);
                //有返回值的方法
                Method m3 = clazz.getDeclaredMethod("getSchool");
                m3.setAccessible(true);
                String school = (String)m3.invoke(obj);//调用有返回值，但是没有参数的方法
                System.out.println(school);



            } catch (Exception e) {
                e.printStackTrace();
            }
            //反射创建一个对象
            Constructor con = null;
            try {
                con = clazz.getConstructor();
                Student stu = (Student)con.newInstance();
                Field  f = clazz.getField("school");//获取名称为school的属性
                f.set(stu,"第四中学");//对stu对象的school属性设置值
                String school = (String)f.get(stu);//获取stu对象的school属性的值
                System.out.println(school);
               //如果是私有属性
                Field  f1 = clazz.getDeclaredField("privateField");
                f1.setAccessible(true);
                f1.set(stu,"测试私有属性");
                System.out.println(f1.get(stu));




            } catch (Exception e) {
                e.printStackTrace();
            }




        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        System.out.println(clazz.class);

    }
}
