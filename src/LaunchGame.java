import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*; 

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;


public class LaunchGame implements ActionListener{
    private JFrame frame = new JFrame("Wordle");
    private JButton myButton = new JButton("Start Game");
    private GamePanel gamePanel = new GamePanel();
    
    LaunchGame(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        myButton.setBounds((int)size.getWidth()/2-100,(int)size.getHeight()/2-20,200,40);
        //myButton.setFocusable(false);
        myButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(myButton);
    }

    @Override 
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==myButton){
            frame.getContentPane().removeAll(); 
            frame.add(gamePanel); 
            frame.revalidate(); // update layout
            frame.repaint(); // reflect changes
        }
    }
}
