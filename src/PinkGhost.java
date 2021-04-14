import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasa do tworzenia obiektu różowego ducha
 */
public class PinkGhost extends Ghost {
    /**
     * Konstruktor PinkGhost
     * @param x wspolrzedne x
     * @param y wspolrzedne y
     * @param gamePanel panel gry
     */
    public PinkGhost(int x, int y,GamePanel gamePanel){
        super(x,y,gamePanel,config.ghostSpeed);
    }

    /**
     * Funkcja wczytująca obraz rózowego ducha
     */
    @Override
    public void loadImages(){
        try {
            ghostI = ImageIO.read(this.getClass().getResource("resources/images/pink_ghost.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
       }
    }

    moveType lastCMove;

    moveType pendMove = moveType.UP;

    @Override
    public moveType getMoveAI(){
        if(isPending){
            if(isStuck){
                if(pendMove == moveType.UP){
                    pendMove = moveType.DOWN;
                }else if(pendMove == moveType.DOWN){
                    pendMove = moveType.UP;
                }
                return pendMove;
            }else{
                return pendMove;
            }
        }
            if (lastCMove == null || isStuck) {
                ArrayList<moveType> pm = getPossibleMoves();
                int i = ThreadLocalRandom.current().nextInt(pm.size());
                lastCMove = pm.get(i);
                return lastCMove;
            } else {
                return lastCMove;
            }
        }
    }

