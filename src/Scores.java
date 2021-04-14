import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Klasa do zapisu/odczytu wyników
 */
public class Scores {
    /**
     * Lista wszystkich wyników wczytana z pliku
     */

    private static ArrayList<String> scoresArray;

    /**
     * Tablica 5 najwyższych wyników
     */

    private static String[] highScores={"-","-","-","-","-"};

    /**
     * Konstruktor klasy wyników
     */

    Scores(){
        scoresArray = new ArrayList<String>();
    }

    /**
     * Aktualizacja listy wyników z jednoczesnym posortowaniem od najwyższych
     */

    public final void update() {
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream("scores.txt"),
                            StandardCharsets.UTF_8
                    )
            );

            String row;
            scoresArray.clear();

            while ((row = bfr.readLine()) != null) {
                scoresArray.add(row);
            }

            Collections.sort(scoresArray, scoresComp);

            bfr.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        choose5Scores();
    }

    /**
     * Porównanie dwóch wyników
     */

    private static Comparator<String> scoresComp = new Comparator<String>() {
        public int compare(final String s1, final String s2) {
            int num1 = Integer.parseInt(s1.split("-")[1]);
            int num2 = Integer.parseInt(s2.split("-")[1]);

            return num2 - num1;
        }
    };

    /**
     * Zapis wyniku do pliku -                      =DO ZROBIENIA=
     * @param name nick gracza
     * @param score wynik gracza
     */

    public static final void writeScore(final String name, final int score) {
        if (score > 0) {
            try {
                OutputStreamWriter output = new OutputStreamWriter(
                        new FileOutputStream("scores.txt", true),
                        StandardCharsets.UTF_8
                );

                output.append(name + "-" + score);
                output.append('\n');
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Wybór tylko 5 najwyższych wyników z listy
     */

    public static void choose5Scores(){
        for(int i=0;i<=4;i++){
            if(scoresArray.size()>=i+1){
                highScores[i]=scoresArray.get(i)+"\n";
            }
            else
                highScores[i]="-";
        }
    }


    public static ArrayList<String> getScoresArray() {
        return scoresArray;
    }

    public static String[] getHighScores() {
        return highScores;
    }
}
