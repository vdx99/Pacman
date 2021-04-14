import java.awt.*;

/**
 * Klasa przechowywująca obiekt - czaszka
 */
public class Skull {
    /**
     * Obiekt punkt przechowujacy pozycje na mapie obiektu
     */
    public Point position;
    /**
     * Definiuje typ obiektu
     */
        public int type;

    /**
     * Konstruktor obiektu Skull
     * @param x wspolrzedna x
     * @param y wspolrzedna y
     * @param type typ obiektu
     */
    public Skull(int x, int y, int type){
        position = new Point(x,y);
        this.type = type;
    }

}
