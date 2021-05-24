import lombok.var;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 我试了下 jdk16会报错 看来高版本和其不兼容
 */
public class CGLIBProxyTest {
    public static void main(String[] args) {
        //cglib代理需要引入依赖
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetClass.class);//设置它父类的Class对象（本质是继承）
        //设置回调，传入的是CallBack接口的实现类，实际上传入会使是 它子接口MethodInterceptor的实现类
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
                    System.out.println("我在业务之前");
                    var result = methodProxy.invokeSuper(o, objects); //第一个参数应该是目标对象，第二个参数应该是参数数组
                    System.out.println("我在业务之后");
                    return result;
                }
        );

        var o = (TargetClass)enhancer.create();//代理后的对象要调用create方法获得
        o.method();
    }
}

class TargetClass {
    public void method() {
        System.out.println("我是业务！");
    }
}
