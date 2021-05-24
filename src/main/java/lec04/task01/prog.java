package lec04.task01;

import java.util.ArrayList;

public class prog {

    public static void main(String[] args) {

        CardFile cf = new CardFile();
        int count = 0;  // счетчик

        System.out.println(cf.findCard("Rad"));

        /* Добавление питомцев */
        try {
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Tim", 6.1f, "Richard", 24, Sex.MAN));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }

        try {
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Tim", 5.6f, "Richard", 24, Sex.MAN));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }
        
        try{
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Tim", 5.6f, "Richard", 24, Sex.MAN));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }

        try{
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Tim", 8.8f, "Richard", 24, Sex.MAN));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }

        try{
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Frey", 5.6f, "Richard", 24, Sex.MAN));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }

        try{
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Rad", 8.3f, "Bob", 23, Sex.FEMALE));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }

        try{
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Batty", 8.3f, "Sara", 23, Sex.FEMALE));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }

        try{
            System.out.println("Add Card #" + (++count) + ": with ID=" + cf.addCard("Archi", 8.3f, "Lars", 23, Sex.FEMALE));
        } catch (Exception e){
            System.out.println("\nWARNING!!! " + e +"\n");
        }

        System.out.println(cf); // вывод содержимого картотеки

        Card card = cf.get(1L);             // получение карты по ID

        if (card != null){                  // замена данных
            card.pet.weight = 234;
            card.owner.age = 99;
        }

        System.out.println("Update pet by id 1: " + cf.put(1L, card)); // обновление каты питомца по ID
        System.out.println(cf);

        /* Поиск питомца по кличке */
        CardFile fc = cf.findCard("Rad");
        System.out.println("Поиск питомца по кличке:\n" + fc);

        /* Вывод сортированного списка типомцев */
        ArrayList<Card> spl = cf.sortedPetList();
        System.out.println("Вывод сортированного списка типомцев:\n" + spl);

        /* Удаление по номеру */
        System.out.println("Удаление по номеру ID=1:\n" + cf.deleteCard(1L));

    }

}
