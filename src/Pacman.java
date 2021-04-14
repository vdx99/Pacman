import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Klasa pacman (obiektu sterowanego przez gracza)
 */
public class Pacman implements KeyListener{
    /**
     * Timer do aktualizacji ruchu
     */
    //Move Vars
    Timer moveTimer;
    /**
     * Interfejs do obslugi ruchu
     */
    ActionListener moveAL;
    /**
     * Typ ruchu
     */
    public moveType activeMove;
    /**
     * Typ ruchu (do wykonania)
     */
    moveType todoMove;
    boolean isStuck = true;
    /**
     * Obraz pacmana
     */
    Image pac;
    /**
     * Wspolrzedne opisujace pixel
     */
    public Point pixelPosition;
    /**
     * Wspolrzedne opisujace polozenie obiektu na mapie
     */
    public Point logicalPosition;
    /**
     * Panel gry (rodzic)
     */
    private GamePanel parentBoard;

    /**
     * Konstruktor klasy Pacman
     * @param x wspolrzedne x
     * @param y wspolrzedne y
     * @param pb panel gry
     */
    public Pacman (int x, int y, GamePanel pb) {

        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);

        parentBoard = pb;

        activeMove = moveType.NONE;
        todoMove = moveType.NONE;

        try {
            pac = ImageIO.read(this.getClass().getResource("resources/images/pac3.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Image!");
        }

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                //update logical position
                if((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)){
                    if(!isStuck) {
                        switch (activeMove) {
                            case RIGHT:
                                logicalPosition.x++;
                                break;
                            case LEFT:
                                logicalPosition.x--;
                                break;
                            case UP:
                                logicalPosition.y--;
                                break;
                            case DOWN:
                                logicalPosition.y++;
                                break;
                        }
                        //send update message
                        parentBoard.dispatchEvent(new ActionEvent(this,Messeges.UPDATE,null));
                    }
                    isStuck = true;

                    if(todoMove != moveType.NONE && isPossibleMove(todoMove) ) {
                        activeMove = todoMove;
                        todoMove = moveType.NONE;
                    }
                }else{
                    isStuck = false;
                }

                switch(activeMove){
                    case RIGHT:
                        if((pixelPosition.x >= (parentBoard.m_x-1) * 28)&&parentBoard.isCustom){
                            return;
                        }
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if (parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x ++;
                        break;
                    case LEFT:
                        if((pixelPosition.x <= 0)&&parentBoard.isCustom){
                            return;
                        }
                        if(logicalPosition.x > 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if (parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x--;
                        break;
                    case UP:
                        if((pixelPosition.y <= 0)&&parentBoard.isCustom){
                            return;
                        }
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if(parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0){
                                return;
                            }
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if((pixelPosition.y >= (parentBoard.m_y-1) * 28)&&parentBoard.isCustom){
                            return;
                        }
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if(parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0){
                                return;
                            }
                        }
                        pixelPosition.y ++;
                        break;
                }

                //send Messege to PacBoard to check collision
               parentBoard.dispatchEvent(new ActionEvent(this,Messeges.COLTEST,null));

            }
        };
        moveTimer = new Timer(config.pacSpeed,moveAL);
        moveTimer.start();

    }

    /**
     * Funkcja sprawdzajaca możliwość ruchu
     * @param move
     * @return
     */
    public boolean isPossibleMove(moveType move){
        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
            switch(move){
                case RIGHT:
                    return !(parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    return !(parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y+1] > 0);
            }
        }
        return false;
    }

    public Image getPacmanImage(){
        return pac;
    }

    @Override
    public void keyReleased(KeyEvent ke){
        //
    }

    @Override
    public void keyTyped(KeyEvent ke){
        //
    }

    //Handle Arrow Keys
    @Override
    public void keyPressed(KeyEvent ke){
        switch(ke.getKeyCode()){
            case 37:
                todoMove = moveType.LEFT;
                break;
            case 38:
                todoMove = moveType.UP;
                break;
            case 39:
                todoMove = moveType.RIGHT;
                break;
            case 40:
                todoMove = moveType.DOWN;
                break;
            case 67:
                parentBoard.dispatchEvent(new ActionEvent(this,Messeges.RESET,null));
                break;
            case 80:
                parentBoard.dispatchEvent(new ActionEvent(this,Messeges.PAUSE,null));
                break;
            case 85:
                parentBoard.dispatchEvent(new ActionEvent(this,Messeges.UNPAUSE,null));
                break;
        }
        //System.out.println(ke.getKeyCode());
    }
}
