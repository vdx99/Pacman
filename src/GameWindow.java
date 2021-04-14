import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 * Klasa okna gry
 */
public class GameWindow extends JFrame {
    /**
     *Konstruktor okna gry
     */
    public GameWindow() {
        setTitle(config.windowTitle);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.black);

        setSize(config.gameWindowWidth, config.gameWindowHeight);
        setLocationRelativeTo(null);

        JLabel scoreboard = new JLabel("    Score : 0");
        scoreboard.setForeground(new Color(255, 243, 36));

        MapData mapd = getMapFromResource("resources/maps/map"+config.actualLevel +".txt");
        adjustMap(mapd);

        GamePanel pb = new GamePanel(scoreboard, mapd, this);

        addKeyListener(pb.pacman);

        this.getContentPane().add(scoreboard, BorderLayout.SOUTH);
        this.getContentPane().add(pb);
        setVisible(true);
    }

    /**
     * Funkcja wczytująca mape z pliku
     * @param relPath sciezka do pliku z mapa
     * @return przetworzona postac mapy do wyswietlenia
     */
    public MapData getMapFromResource(String relPath) {
        String mapStr = "";
        try {
            Scanner scn = new Scanner(this.getClass().getResourceAsStream(relPath));
            StringBuilder sb = new StringBuilder();
            String line;
            while (scn.hasNextLine()) {
                line = scn.nextLine();
                sb.append(line).append('\n');
            }
            mapStr = sb.toString();
        } catch (Exception e) {
        }
        if ("".equals(mapStr)) {
        }
        return MapEditor.compileMap(mapStr);
    }

    /**
     * Funkcja ustawiajaca elementy mapy
     * @param mapd obiekt danych mapy
     */
    //Dynamically Generate Map Segments
    public void adjustMap(MapData mapd) {
        int[][] map = mapd.getMap();
        int mx = mapd.getX();
        int my = mapd.getY();
        for (int y = 0; y < my; y++) {
            for (int x = 0; x < mx; x++) {


                if (map[x][y] > 0 && map[x][y] < 26) {
                    int mustSet = 23;
                    //LEFT
                    if (x > 0 && map[x - 1][y] > 0 && map[x - 1][y] < 26) {
                        //l = true;
                    }
                    //RIGHT
                    if (x < mx - 1 && map[x + 1][y] > 0 && map[x + 1][y] < 26) {
                        //r = true;
                    }
                    //TOP
                    if (y > 0 && map[x][y - 1] > 0 && map[x][y - 1] < 26) {
                        //t = true;
                    }
                    //Bottom
                    if (y < my - 1 && map[x][y + 1] > 0 && map[x][y + 1] < 26) {
                        //b = true;
                    }
                    //TOP LEFT
                    if (x > 0 && y > 0 && map[x - 1][y - 1] > 0 && map[x - 1][y - 1] < 26) {
                        //tl = true;
                    }
                    //TOP RIGHT
                    if (x < mx - 1 && y > 0 && map[x + 1][y - 1] > 0 && map[x + 1][y - 1] < 26) {
                        //tr = true;
                    }
                    //Bottom LEFT
                    if (x > 0 && y < my - 1 && map[x - 1][y + 1] > 0 && map[x - 1][y + 1] < 26) {
                        //bl = true;
                    }
                    //Bottom RIGHT
                    if (x < mx - 1 && y < my - 1 && map[x + 1][y + 1] > 0 && map[x + 1][y + 1] < 26) {
                        //br = true;
                    }

                    //System.out.println("MAP SEGMENT : " + mustSet);
                    map[x][y] = mustSet;
                }
                mapd.setMap(map);
            }
        }
        //System.out.println("map adjust succes");
    }
}