package lec04.task01;

import java.util.Objects;

enum Sex {MAN, FEMALE}

public class Person {
    String name;
    int age;
    Sex sex;

    public Person(String name, int age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

//    private String normalizeName(String name){
//        if (name != null) {
//            name = name.toLowerCase();
//            char ch = name.charAt(0);
//            name(0) String.valueOf(ch).toLowerCase();
//            name.charAt(0);
//        }
//        return "";
//    }


    @Override
    public String toString() {
        return "name: " + name +
                "; age: " + age +
                "; sex:" + sex + "\n";
        //        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && name.equals(person.name) && sex == person.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex);
    }
}
