import java.awt.*;
import java.util.ArrayList;

/**
 * Klasa danych mapy
 */
public class MapData {
    /**
     * wspolrzedna x
     */
    private int x;
    /**
     * wspolrzedna y
     */
    private int y;
    /**
     * przechowuje wspolrzedne
     */
    private int[][] map;
    /**
     * przechowuje punkt w ktorym jest pacman
     */
    private Point pacmanPosition;
    /**
     * Zmienna boolean do wczytywania mapy
     */
    private boolean isCustom;
    /**
     * Tablica obiektów klasy Food
     */
    private ArrayList<Food> foodPositions;
    /**
     * Tablica obiektów klasy  Diamond
     */
    private ArrayList<Diamond> diamonds;
    /**
     * Tablica obiektów klasy  Skull
     */
    private ArrayList<Skull> skulls;
    /**
     * Tablica obiektów klasy  GhostData
     */
    private ArrayList<GhostData> ghostsData;

    /**
     * Konstruktor klasy danych mapy
     */
    public MapData(){
        foodPositions = new ArrayList<>();
        diamonds = new ArrayList<>();
        skulls = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    /**
     * Konstruktor klasy danych mapy
     * @param x wspolrzedna x (wczytanie)
     * @param y wspolrzedna y (wczytanie)
     */
    public MapData(int x,int y){
        this.x = x;
        this.y = y;

        foodPositions = new ArrayList<>();
        diamonds = new ArrayList<>();
        skulls = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Point getPacmanPosition() {
        return pacmanPosition;
    }

    public void setPacmanPosition(Point pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }

    public ArrayList<Food> getFoodPositions() {
        return foodPositions;
    }

    public ArrayList<Diamond> getDiamonds() {
        return diamonds;
    }

    public ArrayList<Skull> getSkulls() {
        return skulls;
    }

    public ArrayList<GhostData> getGhostsData() {
        return ghostsData;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}
