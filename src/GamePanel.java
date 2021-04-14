import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Klasa panelu gry - do obslugi graficznego interfejsu gry
 */
public class GamePanel extends JPanel{

    /**
     * Timer do aktualizacji grafiki
     */
    Timer redrawTimer;
    /**
     * Interfejs do obslugi aktualizacji grafiki
     */
    ActionListener redrawAL;
    /**
     * Przechowuje współrzędne mapy
     */
    int[][] map;
    /**
     * Obraz elementu mapy
     */
    Image mapSegment;
    /**
     * Obraz kulki (jedzenia)
     */
    Image foodImage;
    /**
     * Obraz diamentu
     */
    Image diamondImage;
    /**
     * Obraz czaszki
     */
    Image skullImage;

    /**
     * Obraz wyświetlany po utracie życia
     */
    Image goImage;
    /**
     * Obraz wyświetlany po zwyciestwie
     */
    Image vicImage;
    /**
     * Obraz wyświetlany w czasie pauzy
     */
    Image pauseImage;
    /**
     * Obiekt klasy Pacman
     */
    Pacman pacman;
    /**
     * Tablica obiektów klasy Food
     */
    ArrayList<Food> foods;
    /**
     * Tablica obiektów klasy  Diamond
     */
    ArrayList<Diamond> diamonds;
    /**
     * Tablica obiektów klasy  Skull
     */
    ArrayList<Skull> skulls;
    /**
     * Tablica obiektów klasy  Ghost
     */
    ArrayList<Ghost> ghosts;
    /**
     * Zmienna boolean do wczytywania mapy
     */
    boolean isCustom = false;
    /**
     * Zmienna boolean do sprawdzania stanu gry
     */
    boolean isGameOver = false;
    /**
     * Zmienna boolean do sprawdzania wygranej
     */
    boolean isWin = false;

    /**
     * Zmienna boolean do sprawdzania stanu gry
     * */
     boolean isPaused = false;
    /**
     * Zmienna boolean określająca wyświetlanie wyniku
     */
    boolean drawScore = false;
    /**
     * Zmienna boolean czyszcząca wynik
     */
    boolean clearScore = false;
    /**
     * Liczba punktów do dodania
     */
    int scoreToAdd = 0;

    /**
     *  Nazwa gracza
     */
    private static String name;
    /**
     * Wynik
     */
    int score;
    /**
     * Ramka wyświetlająca się po śmierci gracza
     */

    JFrame f;
    /**
     * Miejsce w oknie na wyniki
     */
    JLabel scoreboard;
    /**
     * Zmienna używana do wczytywania mapy
     */
    public int levelN;

    /**
     * Zmienna używana do wczytywania mapy
     */
    public int m_x;
    /**
     * Zmienna używana do wczytywania mapy
     */
    public int m_y;
    /**
     *  Obiekt klasy MapData używany przy wczytywaniu mapy
     */
    MapData md_backup;
    /**
     * Okno gry
     */
    GameWindow windowParent;

    /**
     * Kontruktor Panelu gry
     * @param scoreboard tablica/miejsce z wynikiem
     * @param md obiekt z danymi o mapie
     * @param pw okno gry
     */
    public GamePanel(JLabel scoreboard, MapData md, GameWindow pw){
        this.scoreboard = scoreboard;
        this.setDoubleBuffered(true);
        //md_backup = md;
        windowParent = pw;


        m_x = md.getX();
        m_y = md.getY();

        this.map = md.getMap();
        this.isCustom = md.isCustom();

        score = (config.actualLevel-1)*1000;

        pacman = new Pacman(md.getPacmanPosition().x,md.getPacmanPosition().y,this);
        addKeyListener(pacman);

        foods = new ArrayList<>();
        diamonds = new ArrayList<>();
        skulls = new ArrayList<>();
        ghosts = new ArrayList<>();


        //reading map
        if(!isCustom) {
            for (int i = 0; i < m_x; i++) {
                for (int j = 0; j < m_y; j++) {
                    if (map[i][j] == 0)
                        foods.add(new Food(i, j));
                }
            }
        }else{
            foods = md.getFoodPositions();
        }



        diamonds = md.getDiamonds();
        skulls = md.getSkulls();

        ghosts = new ArrayList<>();
        for(GhostData gd : md.getGhostsData()){
            switch(gd.getType()) {
                case PINK:
                    ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                   break;
       //different ghost types?
            }
        }

        setLayout(null);
        setSize(20*m_x,20*m_y);
        setBackground(Color.black);

            try {
                mapSegment = ImageIO.read(this.getClass().getResource("resources/images/wall.png"));
            }catch(Exception e){}


        //Image pfoodImage = new Image();

            try {
                diamondImage = ImageIO.read(this.getClass().getResource("resources/images/diamond.png"));
            }catch(Exception e){}

            try {
                skullImage = ImageIO.read(this.getClass().getResource("resources/images/skull.png"));
            }catch(Exception e){}

        try{
            foodImage = ImageIO.read(this.getClass().getResource("resources/images/food.png"));
            goImage = ImageIO.read(this.getClass().getResource("resources/images/lost.png"));
            vicImage = ImageIO.read(this.getClass().getResource("resources/images/win.png"));
            pauseImage = ImageIO.read(this.getClass().getResource("resources/images/pause.png"));
        }catch(Exception e){}


        redrawAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Draw Board
                repaint();
            }
        };
        redrawTimer = new Timer(-1,redrawAL);
        redrawTimer.start();
    }

    /**
     * Funkcja wykrywająca nałozenie/kolizje obiektow graficznych
     */
    private void collisionTest() {
        Rectangle pr = new Rectangle(pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 13, 2, 2);
        for (Ghost g : ghosts) {
            Rectangle gr = new Rectangle(g.pixelPosition.x, g.pixelPosition.y, 28, 28);

            if (pr.intersects(gr)) {

                        pacman.moveTimer.stop();
                        g.moveTimer.stop();
                        //lives = lives-1;

                        isGameOver = true;
                        scoreboard.setText("YOU LOST 1 LIFE! - PRESS C TO CONTINUE");
                        scoreboard.setForeground(Color.red);
            }
        }
    }

    /**
     * Funkcja aktualizująca mape (usuwanie jedzenia, liczenie punktow)
     */
    private void update(){
        Food foodToEat = null;

        //Check food eat
        for(Food f : foods){
            if(pacman.logicalPosition.x == f.position.x && pacman.logicalPosition.y == f.position.y)
                foodToEat = f;
        }
        if(foodToEat!=null) {

            foods.remove(foodToEat);
            score ++;
            scoreboard.setText("    Score : "+ score + "    Lives : " + (Life.livesLeft+1) + "      Level: "+ config.actualLevel);

            if(foods.size() == 0){
                isWin = true;
                pacman.moveTimer.stop();
                for(Ghost g : ghosts){
                    g.moveTimer.stop();
                }
            }
        }

        Diamond diamondS = null;
        //Check diamond eat
        for(Diamond dia : diamonds){
            if(pacman.logicalPosition.x == dia.position.x && pacman.logicalPosition.y == dia.position.y)
                diamondS = dia;
        }
        if(diamondS!=null) {
                    diamonds.remove(diamondS);
                    scoreToAdd = 1;
                    drawScore = true;
        }

        Skull skullS = null;
        for(Skull sku : skulls){
            if(pacman.logicalPosition.x == sku.position.x && pacman.logicalPosition.y == sku.position.y)
                skullS = sku;
        }
        if(skullS!=null) {
            skulls.remove(skullS);

            pacman.moveTimer.stop();

            isGameOver = true;
            scoreboard.setText("YOU LOST 1 LIFE! - PRESS C TO CONTINUE");
            scoreboard.setForeground(Color.red);
        }
    }

    /**
     * Funkcja rysujaca elementy mapy (sciany, pacman, duchy, jedzenie, diamenty)
     * @param g obiekt graficzny
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw Walls
        g.setColor(Color.blue);
        for(int i=0;i<m_x;i++){
            for(int j=0;j<m_y;j++){
                if(map[i][j]>0){
                    //g.drawImage(10+i*28,10+j*28,28,28);
                    g.drawImage(mapSegment,10+i*28,10+j*28,null);
                }
            }
        }

        //Draw Food
        g.setColor(new Color(204, 122, 122));
        for(Food f : foods){
            //g.fillOval(f.position.x*28+22,f.position.y*28+22,4,4);
            g.drawImage(foodImage,10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw Diamonds
        g.setColor(new Color(204, 174, 168));
        for(Diamond f : diamonds){
            //g.fillOval(f.position.x*28+20,f.position.y*28+20,8,8);
            g.drawImage(diamondImage,10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw Skulls
        g.setColor(new Color(204, 174, 168));
        for(Skull s : skulls){
            //g.fillOval(f.position.x*28+20,f.position.y*28+20,8,8);
            g.drawImage(skullImage,10+s.position.x*28,10+s.position.y*28,null);
        }

        //Draw Pacman
        switch(pacman.activeMove){
            case NONE:
            case RIGHT:
                g.drawImage(pacman.getPacmanImage(),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case LEFT:
                g.drawImage(ImageHelper.flipHor(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case DOWN:
                g.drawImage(ImageHelper.rotate90(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case UP:
                g.drawImage(ImageHelper.flipVer(ImageHelper.rotate90(pacman.getPacmanImage())),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
        }

        //Draw Ghosts
        for(Ghost gh : ghosts){
            g.drawImage(gh.getGhostImage(),10+gh.pixelPosition.x,10+gh.pixelPosition.y,null);
        }

        if(clearScore){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            clearScore =false;
        }

        if(drawScore) {
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.setColor(Color.yellow);
            Integer s = scoreToAdd*100;
            g.drawString(s.toString(), pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 50);
            score += s;
            scoreboard.setText("    Score : "+score);
            clearScore = true;

        }

        if(isGameOver){
            g.drawImage(goImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }

        if(isWin){

            config.actualLevel = config.actualLevel +1;
            if(config.actualLevel > 3){

                g.drawImage(vicImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
                windowParent.dispose();
                endGame("Wygrales!!!");


            }else {
                newLevel();

            }
            //g.drawImage(vicImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }
        if(isPaused){
            g.drawImage(pauseImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }

    }

    /**
     * Funkcja obslugująca wydarzenie (aktualizacja, sprawdzanie kolizji, reset i koniec gry)
     * @param ae
     */
    @Override
    public void processEvent(AWTEvent ae){
        //int lives = pacman.livesleft;
        if(ae.getID()==Messeges.UPDATE) {
            update();
        }else if(ae.getID()==Messeges.COLTEST) {
            if (!isGameOver) {
                collisionTest();
            }
        }else if(ae.getID()==Messeges.RESET){

            if(isGameOver && Life.livesLeft != 0){

                restart();
                System.out.println(Life.livesLeft);
            }else if(Life.livesLeft == 0){
                endGame("Koniec gry!");
                //backToMenu();
            }
        }else if(ae.getID()==Messeges.PAUSE){

                //System.out.println("PAUZA");

            pacman.moveTimer.stop();
            for(Ghost g : ghosts){
                g.moveTimer.stop();
            }
            isPaused = true;

        }else if(ae.getID()==Messeges.UNPAUSE){

            System.out.println("UNPAUSE");

            pacman.moveTimer.start();
            for(Ghost g : ghosts){
                g.moveTimer.start();
            }
            isPaused = false;

        }else {
            super.processEvent(ae);
        }
    }

    /**
     * Funkcja do resetu gry po utracie 1 próby
      */
    public void restart(){
        Life.livesLeft--;
        new GameWindow();
        windowParent.dispose();

    }
    /**
     * Po przejsciu lvl
     */
    public void newLevel(){
        new GameWindow();
        windowParent.dispose();

    }

    /**
     * Funkcja pozwalająca wrócic do menu głównego - po stracie wszystkich żyć
     */
    public void backToMenu(){
        new StartWindow();
        windowParent.dispose();
    }

    public void endGame(String endGameMessage){
        f=new JFrame();
        name = JOptionPane.showInputDialog(f,endGameMessage+ "\nTwój wynik: "+ score + "\nPodaj nick");
        Scores.writeScore(name,score);
        backToMenu();
        //State.setState(handler.getGame().menuState);
        //handler.getMap().setId(0);
    }


}
