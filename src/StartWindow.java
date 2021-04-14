import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Klasa okna startowego do wyswietlania menu
 */
public class StartWindow extends JFrame {


    /**
     * Konstruktor okna startowego
     */
    public StartWindow(){
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        config.lives = 3;
        config.actualLevel=1;

        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(ImageIO.read(this.getClass().getResource("resources/images/pacman_logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        setLayout(new BorderLayout());
        getContentPane().add(new JLabel(logo),BorderLayout.NORTH);

        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);

        buttonsC.setLayout(new BoxLayout(buttonsC,BoxLayout.Y_AXIS));
        MyButton startButton = new MyButton("NOWA GRA");
        MyButton rulesButton = new MyButton("ZASADY GRY");
        //MyButton difficultyButton = new MyButton("POZIOM TRUDNOSCI");
        MyButton scoresButton = new MyButton("NAJLEPSZE WYNIKI");
        MyButton quitButton = new MyButton("WYJDZ Z GRY");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //difficultyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameWindow pw = new GameWindow();
                dispose();
            }
        });

        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rules rules = new Rules();
            }
        });

        scoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Scores scores = new Scores();
                //scores.update();
                //JOptionPane.showMessageDialog(null, scores.getHighScores());
                ScoresWindow scoresWindow = new ScoresWindow();
                //dispose();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dispose();
                System.exit(0);
            }
        });
        buttonsC.add(startButton);
        buttonsC.add(rulesButton);
        buttonsC.add(scoresButton);
       // buttonsC.add(difficultyButton);
        buttonsC.add(quitButton);

        getContentPane().add(buttonsC);

        setVisible(true);
    }
}
