import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.text.*;

public class quoteMethods extends JFrame{
    private JFrame frame; 
    private JTextPane quoteArea; 
    private JButton newQuoteButton; 

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
        this.setLayout(new BorderLayout());

        quoteArea = new JTextPane();
        quoteArea.setFont(new Font("Serif", Font.PLAIN,16));
        quoteArea.setEditable(false);
        quoteArea.setOpaque(false);

        StyledDocument doc = quoteArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JScrollPane scrollPane = new JScrollPane(quoteArea);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, BorderLayout.CENTER);

        newQuoteButton = new JButton("Another Quote!");
        newQuoteButton.setFont(new Font("SansSerif", Font.PLAIN, 14));

        newQuoteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String quote = getRandomQuote("quotes.txt");
                quoteArea.setText(quote);

                StyledDocument doc = quoteArea.getStyledDocument();
                doc.setParagraphAttributes(0, doc.getLength(), center, false);
            }
        });

        this.add(newQuoteButton, BorderLayout.SOUTH);

        String firstQuote = getRandomQuote("quotes.txt");
        quoteArea.setText(firstQuote);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    public String getRandomQuote(String filename){
        try{
            int targetLine = (int)(Math.random() * 100)+1;

            Scanner reader = new Scanner(new File("quotes.txt"));
            String line = "";

            for(int i = 0; i <= targetLine; i++){
                if(reader.hasNextLine()){
                    line = reader.nextLine(); 
                } 
            }
            reader.close(); 

            String[] parts = line.split("\\|",3);

            if(parts.length < 3){
                return line; 
            }
            String quote = parts[0];
            String speaker = parts[1];
            String year = parts[2]; 

            if(year.endsWith("|")) {
                year = year.substring(0, year.length() - 1).trim();
            }
            return quote + "\n" + speaker + ", " + year ; 
        }catch(FileNotFoundException e){
            return "Quote not found: " + filename; 
        }

    }
}
