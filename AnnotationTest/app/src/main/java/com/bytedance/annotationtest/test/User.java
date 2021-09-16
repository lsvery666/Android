package com.bytedance.annotationtest.test;

import com.bytedance.annotationtest.annotations.After;
import com.bytedance.annotationtest.annotations.Before;
import com.bytedance.annotationtest.annotations.Test;

public class User {
    @Before
    public void init(){
        System.out.println("init");
    }

    @After
    public void destroy(){
        System.out.println("destroy");
    }

    @Test
    public void save(){
        System.out.println("save");
    }

    @Test
    public void delete(){
        System.out.println("delete");
    }
}
