import javax.swing.JFrame; 

public class quoteMethods extends JFrame{
    private JFrame frame; 

    public quoteMethods(){
        initialize(); 
    }

    public void initialize(){
        frame = new JFrame(); 

        this.setTitle("Inspirational Quotes!");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
