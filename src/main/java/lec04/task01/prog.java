package lec04.task01;

import java.util.ArrayList;

public class prog {

    public static void main(String[] args) {


        CardFile cf = new CardFile();
        ArrayList<Pet> pets = new ArrayList<>();
        pets.add(new Pet("Pitty", 5.4f, new Person("Dave", 28, Sex.MAN)));
        pets.add(new Pet("Dasy", 3.6f, new Person("Lara", 22, Sex.FEMALE)));
        pets.add(new Pet("Rex", 8.6f, new Person("Jonny", 30, Sex.MAN)));
        pets.add(new Pet("Tom", 3.9f, new Person("Donald", 30, Sex.MAN)));
        pets.add(new Pet("Dolly", 5.1f, new Person("Jerry", 30, Sex.FEMALE)));
        pets.add(new Pet("Archi", 2.6f, new Person("Tom", 30, Sex.MAN)));

        /* Добавление питомцев */
        try {
            for (Pet p : pets) {
                cf.addPet(p);
            }
        } catch (Exception e) {
            System.out.printf("Genetated exception: %s", e);
        }

        /* Exception demo */
        try {
            cf.addPet(pets.get(0));
        } catch (Exception e) {
            System.out.println("Exception demo! \n" + e + "\n");
        }

        System.out.println(cf);

        /* Delete pet by id. */
        System.out.println("Delete Pet:  " + cf.deletePet(2L));

        /* Find pet by nickname. */
        System.out.printf("Find pet by nickname \"Pitty\"...%n %s %n%n", cf.findPet("Pitty"));

        /* Get sorted list of pets. */
        System.out.println("Sorted list...\n" + cf.sortedPetList());

    }

}
