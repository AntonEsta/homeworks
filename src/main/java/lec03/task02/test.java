package lec03.task02;

import java.util.ArrayList;

public class test {

    public static void main(String[] args) {

        ArrayList<Object> collection = new ArrayList<>();

        collection.add(new Object());
        collection.add(new Object());
        collection.add(new Object());
        collection.add(new Object());

        ObjectBox objBox = new ObjectBox(collection);
        Object obj = new Object();

        System.out.println("objBox.addObject(): " + objBox.addObject(obj));
        System.out.println("objBox.deleteObject(): " + objBox.deleteObject(obj));

        objBox.dump();
    }

}
