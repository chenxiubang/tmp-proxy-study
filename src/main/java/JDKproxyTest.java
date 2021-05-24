import lombok.var;

import java.lang.reflect.Proxy;

public class JDKproxyTest {
    public static void main(String[] args) {
        var obj = new MyClass();//目标对象
        var o = Proxy.newProxyInstance(
                obj.getClass().getClassLoader()
                , obj.getClass().getInterfaces(),
                //第三个参数是InvocationHandler的实现对象
                (proxy, method, argss) -> {
                    System.out.println("代理前打印");
                    var result = method.invoke(obj, argss); //方法反射，参数一个是目标对象obj，一个是参数数组
                    System.out.println("代理后打印");
                    return result;
                }
        );
        ((TargetInterface) o).targetMethod();//代理对象强转后调用方法
    }
}

interface TargetInterface {
    void targetMethod();
}

class MyClass implements TargetInterface {
    @Override
    public void targetMethod() {
        System.out.println("我是业务，我被执行了");
    }
}
