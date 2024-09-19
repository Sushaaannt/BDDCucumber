package javaDoc.allCommonMethodsForPractice;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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

        System.out.println("=========addAll========");
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(100);
        arr.add(200);
        arr.add(300);
        System.out.println(arr);

        al.addAll(arr);

        System.out.println(al);
    }
    @Test
    public void hashsetmethod()
    {
        // Creating an empty HashSet
        HashSet<String> h = new HashSet<String>();

        // Adding elements into HashSet
        // using add() method
        h.add("UK");
        h.add("India");
        h.add("Australia");
        h.add("South Africa");

        // Adding duplicate elements
        h.add("India");

        // Displaying the HashSet
        System.out.println(h);
        System.out.println("List contains India or not:"
                + h.contains("India"));

        // Removing items from HashSet
        // using remove() method
        h.remove("Australia");
        System.out.println("List after removing Australia:"
                + h);

        // Display message
        System.out.println("Iterating over list:");

        // Iterating over hashSet items
        Iterator<String> i = h.iterator();

        // Holds true till there is single element remaining
        while (i.hasNext())

            // Iterating over elements
            // using next() method
            System.out.println(i.next());
        //for (String s : h) System.out.println(s);
        //========================================
        HashSet cloned_set = new HashSet();

        // Cloning the set using clone() method
        cloned_set = (HashSet)h.clone();
        //HashSet cloned_set = (HashSet) h.clone();

        // Displaying the new Set after Cloning;
        System.out.println("The new set: " + cloned_set);
    }
}
