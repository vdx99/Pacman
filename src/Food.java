import java.awt.*;

/**
 * Klasa kulki (jedzenia)
 */
public class Food {
    /**
     * Obiekt punkt przechowujacy pozycje na mapie obiektu
     */
    public Point position;

    /**
     * Konstruktor obiektu Food
     * @param x wspolrzedna x
     * @param y wspolrzedna y
     */
    public Food(int x,int y){
        position = new Point(x,y);
    }

}
