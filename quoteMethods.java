import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.text.*;

public class quoteMethods extends JFrame{
    private JTextPane quoteArea; 
    private JButton newQuoteButton; 
    private JLabel backgroundLabel;
    private ImageIcon[] frames;
    private int currentFrame = 0;
    private Timer animationTimer;
    private int direction = 1;

    public quoteMethods(){
        initialize(); 
    }

    public void initialize(){
        this.setTitle("Inspirational Quotes!");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);   

        initBackground(); 

        JPanel content = (JPanel) this.getContentPane();
        content.setOpaque(false);
        content.setLayout(new BorderLayout());

        quoteArea = new JTextPane();
        quoteArea.setFont(new Font("Serif", Font.PLAIN,16));
        quoteArea.setEditable(false);
        quoteArea.setOpaque(false);
        quoteArea.setForeground(Color.BLACK);

        StyledDocument doc = quoteArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JScrollPane scrollPane = new JScrollPane(quoteArea);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        content.add(scrollPane, BorderLayout.CENTER);

        newQuoteButton = new JButton("Another Quote!");
        newQuoteButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        newQuoteButton.addActionListener(e -> {
            String quote = getRandomQuote("quotes.txt");
            quoteArea.setText(quote);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
        });

        content.add(newQuoteButton, BorderLayout.SOUTH);

        String firstQuote = getRandomQuote("quotes.txt");
        quoteArea.setText(firstQuote);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        this.setVisible(true);   
    }

    public String getRandomQuote(String filename){
        try{
            int targetLine = (int)(Math.random() * 100)+1;
            Scanner reader = new Scanner(new File(filename));
            String line = "";

            for(int i = 0; i <= targetLine; i++){
                if(reader.hasNextLine()){
                    line = reader.nextLine(); 
                } 
            }
            reader.close(); 

            String[] parts = line.split("\\|",3);
            if(parts.length < 3) return line; 

            return parts[0] + "\n" + parts[1] + ", " + parts[2]; 
        }catch(FileNotFoundException e){
            return "Quote not found: " + filename; 
        }
    }

    private void initBackground() {
        frames = new ImageIcon[]{
            new ImageIcon("frame1.png"),
            new ImageIcon("frame2.png"),
            new ImageIcon("frame3.png")
        };

        for (int i = 0; i < frames.length; i++) {
            Image img = frames[i].getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH);
            frames[i] = new ImageIcon(img);
        }

        backgroundLabel = new JLabel(frames[0]);
        backgroundLabel.setBounds(0, 0, 600, 500);

        this.getLayeredPane().add(backgroundLabel, new Integer(Integer.MIN_VALUE));

        animationTimer = new Timer(400, e -> {
            currentFrame += direction;
            if(currentFrame == frames.length - 1 || currentFrame == 0) direction *= -1;
            backgroundLabel.setIcon(frames[currentFrame]);
        });
        animationTimer.start();
    }

    public static void main(String[] args){
        new quoteMethods();
    }
}
