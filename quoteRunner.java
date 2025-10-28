import javax.swing.SwingUtilities;

public class quoteRunner {
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                quoteMethods frame1 = new quoteMethods(); 
            }

        });
    }

}
