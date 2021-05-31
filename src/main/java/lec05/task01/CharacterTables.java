package lec05.task01;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CharacterTables {
    public final char[] latinLettersCharArray = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz").toCharArray();
    public final char[] latinLowerCaseCharArray = ("abcdefghijklmnopqrstuvwxyz").toCharArray();
    public final char[] latinUpperCaseCharArray = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
    public final char[] cyrillicLettersCharArray = ("ЁАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяё").toCharArray();
}
