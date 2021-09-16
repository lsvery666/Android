package com.bytedance.annotationtest.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ArrayList<E>中的E称为类型参数变量
 * ArrayList<Integer>中的Integer称为实际类型参数
 * 整个ArrayList<E>称为泛型类型
 * 整个ArrayList<Integer>称为参数化的类型ParameterizedType
 */

class A<T>{
    public A(){
        // Q: 如何在这里获得子类的实际类型参数？
        // 这里的this是子类对象B
        Class clazz = this.getClass();

        // 得到父类（参数化类型），这一步相当于拿到A<String>，父类及泛型
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();

        // 得到实际类型参数
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        // 由于A只有一个泛型
        Class actualTypeArgument = (Class) actualTypeArguments[0];
        System.out.println(actualTypeArgument);
        System.out.println(actualTypeArgument.getName());
    }
}

class B extends A<String>{

}


public class ParameterizedTypeTest {
    public static void main(String[] args) {
        new B();
    }
}
