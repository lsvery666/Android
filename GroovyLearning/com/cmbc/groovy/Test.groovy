package com.cmbc.groovy

class Test{
    String name
    String title

    Test(name, title){
        this.name = name
        this.title = title
    }

    def print(){
        println name + " " + title
    }
}