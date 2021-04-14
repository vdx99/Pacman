/**
 * Klasa do obsługi danych string przy wczytywaniu mapy
 */
public class StringHelper {
    public static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }
}
