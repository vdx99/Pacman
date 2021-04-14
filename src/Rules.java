import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa do przechowywania i wyświetlania zasad gry
 */
public class Rules extends JFrame {
    /**
     * Konstruktor Rules
     */
    public Rules(){
        setSize(650,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.black);

        JPanel button = new JPanel();
        button.setLayout(new BorderLayout());
        button.setBackground(Color.black);

        setLayout(new BorderLayout());
        getContentPane().add(button,BorderLayout.SOUTH);

        JTextArea ta = new JTextArea();
        ta.setBackground(Color.black);
        ta.setForeground(Color.yellow);
        ta.setText(config.rulesText);
        getContentPane().add(ta);

        MyButton okButton = new MyButton("OK");
        button.add(okButton,BorderLayout.EAST);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}
