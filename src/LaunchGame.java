import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class LaunchGame implements ActionListener{
    private final int screenWidth = 500;
    private final int screenHeight = 600;

    private JFrame frame = new JFrame();
    private JButton myButton = new JButton("New Window");
    
    LaunchGame(){
        myButton.setBounds(100,160,200,40);
        myButton.setFocusable(false);
        myButton.addActionListener(this);

        frame.add(myButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth,screenHeight);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override 
    public void actionPerformed(ActionEvent e){

    }
}
