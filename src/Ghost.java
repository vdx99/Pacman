import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Klasa abstrakcyjna ducha
 */
public abstract class Ghost {
    /**
     * Timer do animacji
     */
    //Anim Vars
    Timer animTimer;
    /**
     * Interfejs do obslugi animacji
     */
    ActionListener animAL;
    /**
     * Timer do ruchu ducha
     */
    //Pending Vars
    Timer pendingTimer;
    /**
     * Interfejs do obslugi ruchu ducha
     */
    ActionListener pendingAL;
    /**
     * Timer do obsługi ruchu obiektów
     */
    //Move Vars
    Timer moveTimer;
    /**
     * Interfejs do obslugi ruchu obiektów
     */
    ActionListener moveAL;
    /**
     * Zmienna do określania rodzaju ruchu
     */
    public moveType activeMove;
    /**
     * Okresla czy obiekt utknal
     */
    protected boolean isStuck = true;
    /**
     * Sluzy do obslugi ruchu ducha
     */
    boolean isPending = false;
    /**
     * Wspolrzedne opisujace pixel
     */
    public Point pixelPosition;
    /**
     * Wspolrzedne opisujace polozenie obiektu na mapie
     */
    public Point logicalPosition;
    /**
     * Obraz ducha
     */
    Image ghostI;
    /**
     * Przechowuje opoznienie w ruchu ducha
     */
    int ghostNormalDelay;
    /**
     * Panel gry (rodzic)
     */
    protected GamePanel parentBoard;

    /**
     * Konstruktor klasy Ghost
     * @param x wspolrzedne poczatkowe x
     * @param y wspolrzedne poczatkowe y
     * @param pb obiekt panelu gry
     * @param ghostDelay opoznienie ducha
     */
    public Ghost (int x, int y,GamePanel pb,int ghostDelay) {

        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);

        parentBoard = pb;

        activeMove = moveType.RIGHT;

        ghostNormalDelay = ghostDelay;

        loadImages();

        animTimer = new Timer(1,animAL);
        animTimer.start();

        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

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
                        parentBoard.dispatchEvent(new ActionEvent(this,Messeges.UPDATE,null));
                    }


                    activeMove = getMoveAI();
                    isStuck = true;

                }else{
                    isStuck = false;
                    //animTimer.start();
                }
                // }

                switch(activeMove){
                    case RIGHT:
                        if(pixelPosition.x >= (parentBoard.m_x-1) * 28){
                            return;
                        }
                        if((logicalPosition.x+1 < parentBoard.m_x) && (parentBoard.map[logicalPosition.x+1][logicalPosition.y]>0) && ((parentBoard.map[logicalPosition.x+1][logicalPosition.y]<26)||isPending)){
                            return;
                        }
                        pixelPosition.x ++;
                        break;
                    case LEFT:
                        if(pixelPosition.x <= 0){
                            return;
                        }
                        if((logicalPosition.x-1 >= 0) && (parentBoard.map[logicalPosition.x-1][logicalPosition.y]>0) && ((parentBoard.map[logicalPosition.x-1][logicalPosition.y]<26)||isPending)){
                            return;
                        }
                        pixelPosition.x --;
                        break;
                    case UP:
                        if(pixelPosition.y <= 0){
                            return;
                        }
                        if((logicalPosition.y-1 >= 0) && (parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0) && ((parentBoard.map[logicalPosition.x][logicalPosition.y-1]<26)||isPending)){
                            return;
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if(pixelPosition.y >= (parentBoard.m_y-1) * 28){
                            return;
                        }
                        if((logicalPosition.y+1 < parentBoard.m_y) && (parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0) && ((parentBoard.map[logicalPosition.x][logicalPosition.y+1]<26)||isPending)){
                            return;
                        }
                        pixelPosition.y ++;
                        break;
                }

                parentBoard.dispatchEvent(new ActionEvent(this,Messeges.COLTEST,null));
            }
        };
        moveTimer = new Timer(ghostDelay,moveAL);
        moveTimer.start();


        pendingAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPending = false;
                pendingTimer.stop();
            }
        };
        pendingTimer = new Timer(7000,pendingAL);
        activeMove = getMoveAI();

    }
    //load Images from Resource
    public abstract void loadImages();

    //get Move Based on AI
    public abstract moveType getMoveAI();

    //get possible Moves
    public ArrayList<moveType> getPossibleMoves(){
        ArrayList<moveType> possibleMoves = new ArrayList<>();

        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {

            if (!(parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0)) {
                possibleMoves.add(moveType.RIGHT);
            }

            if (!(parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0)) {
                possibleMoves.add(moveType.LEFT);
            }

            if(!(parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0)){
                possibleMoves.add(moveType.UP);
            }

            if(!(parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0)){
                possibleMoves.add(moveType.DOWN);
            }
        }

        return possibleMoves;
    }

    public Image getGhostImage(){
                return ghostI;
    }
}
