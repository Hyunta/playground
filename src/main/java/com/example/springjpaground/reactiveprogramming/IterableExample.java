package com.example.springjpaground.reactiveprogramming;

import java.util.Iterator;

public class IterableExample {

    public static void main(String[] args) {
        Iterable<Integer> iter = () ->
                new Iterator<>() {
                    int i = 0;
                    final static int MAX = 10;

                    public boolean hasNext() {
                        return i < MAX;
                    }

                    public Integer next() {
                        return ++i;
                    }
                };

        for (Iterator<Integer> it = iter.iterator(); it.hasNext(); ) {
            System.out.println("it = " + it.next());
        }
    }
}
