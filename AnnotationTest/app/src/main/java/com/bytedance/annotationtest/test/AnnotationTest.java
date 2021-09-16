package com.bytedance.annotationtest.test;

import com.bytedance.annotationtest.annotations.After;
import com.bytedance.annotationtest.annotations.Before;
import com.bytedance.annotationtest.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationTest {
    public static void main(String[] args) throws Exception {
        Class clazz = User.class;
        Object obj = clazz.newInstance();

        Method[] methods = clazz.getMethods();

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        for(Method method: methods){
            if(method.isAnnotationPresent(Before.class)){
                beforeMethods.add(method);
            }else if(method.isAnnotationPresent(After.class)){
                afterMethods.add(method);
            }else if(method.isAnnotationPresent(Test.class)){
                testMethods.add(method);
            }
        }

        for(Method test: testMethods){
            for(Method before: beforeMethods){
                before.invoke(obj);
            }
            test.invoke(obj);
            for(Method after: afterMethods){
                after.invoke(obj);
            }
        }
    }
}
