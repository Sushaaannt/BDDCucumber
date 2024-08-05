package javaDoc.allCommonMethodsForPractice;

import org.testng.annotations.Test;

import java.util.ArrayList;

public class commonMethods {
    @Test
    public void listInterface(){
        ArrayList<Integer> al = new ArrayList<>(5);
        al.add(111);
        al.add(3334);
        al.add(342);
        al.add(675);
        al.add(445);
        al.add(688);
        System.out.println(al.size());
        for(Integer al1:al){
            System.out.println(al1);
        }
        System.out.println("========Remove==========");
        al.remove(0);
        for(Integer al1:al){
            System.out.println(al1);
        }
        System.out.println("=========Replace/update=========");
        al.set(3,787);
        int i =al.set(4,555);
        for(Integer al1:al){
            System.out.println(al1);
        }
        System.out.println("Replaced integer at 4th place is "+ i);
    }
}
