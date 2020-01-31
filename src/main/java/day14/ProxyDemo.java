package day14;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 */
public class ProxyDemo implements InvocationHandler {
    Object obj;//被代理的对象
    public ProxyDemo(Object obj) {
        this.obj = obj;
    }
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println(method.getName() + "方法开始执行");
       Object result =  method.invoke(this.obj);//执行的是指定代理对象的指定方法
        System.out.println(method.getName() + "方法执行完毕");
        return result;
    }
}
