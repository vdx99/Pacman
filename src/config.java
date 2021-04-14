import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Klasa parsująca plik konfiguracyjny xml z javą
 */

public final class config {

    /**
     * Ścieżka do pliku konfiguracyjnego
     */

    public static final String xmlConfig = "src/resources/config/config.xml";

    /**
     * Wysokość okna gry
     */

    public static int gameWindowHeight;

    /**
     * Szerokość okna gry
     */

    public static int gameWindowWidth;

    /**
     * Liczba życ gracza
     */

    public static int lives;
    /**
     * Tytuł ramki z grą
     */

    public static String windowTitle;
    /**
     * Tekst zawierający zasady rozgrywki
     */

    public static String rulesText;
    /**
     * Numer aktualnego poziomu
     */
    public static int actualLevel;
    /**
     * Szybkosc ducha
     */
    public static int ghostSpeed;
    /**
     * Szybkosc pacmana
     */
    public static int pacSpeed;

    /**
     * Ilość poziomów do przejścia
     */
    public static int maxLevel;


    /**
     * Metoda parsująca plik config.xml z klasą java
     */

    static{
        parseConfig();
    }


    private config(){}
    /**
     * Metoda parsująca plik config.xml z klasą java
     */
    public static void parseConfig(){
        try{
            File xmlInputFile = new File(xmlConfig);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputFile);
            doc.getDocumentElement().normalize();

            gameWindowHeight=Integer.parseInt(doc.getElementsByTagName("gameWindowHeight").item(0).getTextContent());
            gameWindowWidth=Integer.parseInt(doc.getElementsByTagName("gameWindowWidth").item(0).getTextContent());
            actualLevel =Integer.parseInt(doc.getElementsByTagName("actualLevel").item(0).getTextContent());
            maxLevel =Integer.parseInt(doc.getElementsByTagName("maxLevel").item(0).getTextContent());
            ghostSpeed =Integer.parseInt(doc.getElementsByTagName("ghostSpeed").item(0).getTextContent());
            pacSpeed =Integer.parseInt(doc.getElementsByTagName("pacSpeed").item(0).getTextContent());
            windowTitle=doc.getElementsByTagName("windowTitle").item(0).getTextContent();
            rulesText=doc.getElementsByTagName("rulesText").item(0).getTextContent();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
