import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Klasa okna do wyswietlania wyników
 */
public class ScoresWindow extends JFrame {
    /**
     * Konstruktor okna do wyswietlania wynikow
     */
    public ScoresWindow() {

        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.black);

        Scores scores = new Scores();
        scores.update();

        JPanel button = new JPanel();
        button.setLayout(new BorderLayout());
        button.setBackground(Color.black);

        setLayout(new BorderLayout());
        getContentPane().add(button, BorderLayout.SOUTH);

        JTextArea ta = new JTextArea();
        ta.setBackground(Color.black);
        ta.setForeground(Color.yellow);
        ArrayList<String> topscores = scores.getScoresArray();

        String str1 = "NAJLEPSZE WYNIKI\n";

        for(int i=0;i<=4;i++){
            str1 = str1 + "   " + (i+1) + ". " + topscores.get(i) + "\n";
        }

        ta.setText(str1);

        getContentPane().add(ta);

        MyButton okButton = new MyButton("OK");
        button.add(okButton, BorderLayout.EAST);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}
