package javatest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vkarassouloff on 21/04/17.
 */
public class TestJava {


    /**
     * Test java.
     *
     * @param args aarararajraij
     */
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(new Integer(1));
        list.add(null);
        list.add(new Integer(55));

        list.add(null);
        list.add(new Integer(2));
        list.add(null);
        list.add(null);
        System.out.println(list.size());
        list.removeAll(Collections.singleton(null));
        System.out.println(list.size());
    }

    /**
     * lol.
     */
    private static void lol() {
        int i;
        Integer test = null;
        i = test;
        System.out.println(i);
    }

}
