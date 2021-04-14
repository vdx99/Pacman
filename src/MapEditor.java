import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasa sluzaca do edycji mapy
 */
public class MapEditor {
    /**
     * Funkcja przetwarzajaca dane mapy
     * @param input odczytana mapa
     * @return odpowiednio przetworzona mapa
     */
    //Resolve Map
    public static MapData compileMap(String input){
        int mx = input.indexOf('\n');
        int my = StringHelper.countLines(input);

        MapData customMap = new MapData(mx,my);
        customMap.setCustom(true);
        int[][] map = new int[mx][my];

        //Pass Map As Argument
        int i=0;
        int j=0;
        for(char c : input.toCharArray()){
            if(c == '2'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.PINK));
            }
            if(c == 'P'){
                map[i][j] = 0;
                customMap.setPacmanPosition(new Point(i,j));
            }
            if(c == 'X'){
                map[i][j] = 23;
            }
            //if(c == 'Y'){
            //    map[i][j] = 26;
            //}
            if(c == '_'){
                map[i][j] = 0;
                customMap.getFoodPositions().add(new Food(i,j));
            }
            if(c == '='){
                map[i][j] = 0;
            }
            if(c == 'S'){ //czaszka
                map[i][j] = 0;
                customMap.getSkulls().add(new Skull(i,j,0));
            }
            if(c == 'F'){
                map[i][j] = 0;
                customMap.getDiamonds().add(new Diamond(i,j, 0));
            }
            i++;
            if(c == '\n'){
                j++;
                i=0;
            }
        }

        customMap.setMap(map);
        customMap.setCustom(true);
        //System.out.println("Map Read OK !");
        return customMap;
    }
}
