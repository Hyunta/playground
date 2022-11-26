package com.example.javaplayground.classloading;

public class Main {

    public static void main(String[] args) {
        System.out.println(Outer.Holder.value);// static 내부 클래스 static 변수 호출
    }
}

class Outer {
    // static 변수
    static String value = "> Outer 클래스의 static 필드 입니다.";

    // static final 상수
    static final String VALUE = "> Outer 클래스의 static final 필드 입니다.";

    Outer() {
        System.out.println("> Outer 생성자 초기화");
    }

    // static 메서드
    static void getInstance() {
        System.out.println("> Outer 클래스의 static 메서드 호출");
    }

    // inner 클래스
    class Inner {
        Inner() {
            System.out.println("> Inner 생성자 초기화");
        }
    }

    // static inner 클래스
    static class Holder {
        static String value = "> Holder 클래스의 static 필드 입니다.";
        static final String VALUE = "> Holder 클래스의 static final 필드 입니다.";

        Holder() {
            System.out.println("> Holder 생성자 초기화");
        }
    }
}

