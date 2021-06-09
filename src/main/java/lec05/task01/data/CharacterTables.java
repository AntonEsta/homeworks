package lec05.task01.data;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;


@UtilityClass
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CharacterTables {

    public final static char[] latinLettersCharArray = {'A','B','C','D','E','F','G','H','I','J','K','L','M',
                                                        'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                                                        'a','b','c','d','e','f','g','h','i','j','k','l','m',
                                                        'n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public final static char[]latinLowerCaseCharArray= {'a','b','c','d','e','f','g','h','i','j','k','l','m',
                                                        'n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public final static char[]cyrillicLettersCharArray={'Ё','А','Б','В','Г','Д','Е','Ж','З','И','Й','К','Л',
                                                        'М','Н','О','П','Р','С','Т','У','Ф','Х','Ц','Ч','Ш',
                                                        'Щ','Ъ','Ы','Ь','Э','Ю','Я','а','б','в','г','д','е',
                                                        'ж','з','и','й','к','л','м','н','о','п','р','с','т',
                                                        'у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я','ё'};
}