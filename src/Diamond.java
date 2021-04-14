import java.awt.*;

/**
 * Klasa przechowywująca obiekt - diament
 */
public class Diamond {
    /**
     * Obiekt punkt przechowujacy pozycje na mapie obiektu
     */
    public Point position;
    /**
     * Definiuje typ obiektu
     */
    //beda dodane jeszce inne (np. czaszka)
    public int type;

    /**
     * Konstruktor obiektu Diamond
     * @param x wspolrzedna x
     * @param y wspolrzedna y
     * @param type typ obiektu
     */
    public Diamond(int x, int y, int type){
        position = new Point(x,y);
        this.type = type;
    }

}
