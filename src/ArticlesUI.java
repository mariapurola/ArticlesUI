import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class ArticlesUI extends JFrame {

    private static final String[] ARTICLES = new String[] {"der","die","das"};
    private static final String ARTICLES_FILE = "./src/Sonad/sonad.txt";

    private static JFrame mainFrame;

    private Map<String, String> wordArticlesPairs = new HashMap<String, String>();
    private Iterator<String> articlesIterator;
    private String currentWord;
    private String selectedArticle;
    private String filePath;
    private int correctAnswers = 0;
    private int cookie = 1;

    private JLabel wordLabel;

    public static void main(String[] args) throws IOException {
        mainFrame = new ArticlesUI();
        mainFrame.setVisible(true);
    }

    public ArticlesUI() throws IOException {
        setTitle("DerDieDasGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadArticlesFromFile();
        articlesIterator = wordArticlesPairs.keySet().iterator();
        currentWord = articlesIterator.next();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // padding
        wordLabel = new JLabel(currentWord);
        panel.add(wordLabel, BorderLayout.NORTH);
        panel.add(createRadioPanel(), BorderLayout.CENTER);
        panel.add(createAnswerButton(), BorderLayout.SOUTH);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null); // center window
    }

    private void loadArticlesFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ARTICLES_FILE));
        for (int i = 0; i < 10; i++) {
            //System.out.println(br.readLine());
            wordArticlesPairs.put(reader.readLine(), reader.readLine());
        }
        reader.close();
    }

    private JPanel createRadioPanel() {
        JPanel panel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        for (String article : ARTICLES) {
            JRadioButton radioButton = new JRadioButton(article);
            radioButton.setActionCommand(article);
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedArticle = e.getActionCommand();
                }
            });
            group.add(radioButton);
            panel.add(radioButton);
        }
        return panel;
    }

    private JButton createAnswerButton() {
        JButton button = new JButton("Answer");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedArticle == null) { // user did not select anything
                    return; // do nothing
                }
                if (selectedArticle.equals(wordArticlesPairs.get(currentWord))) {
                    JOptionPane.showMessageDialog(mainFrame, "Correct!");
                    correctAnswers += cookie;
                    if (articlesIterator.hasNext()) {
                        currentWord = articlesIterator.next();
                        wordLabel.setText(currentWord);
                        cookie = 1;
                    } else {
                        float tulemus = ((float)correctAnswers/wordArticlesPairs.size()) * 100;
                        JOptionPane.showMessageDialog(mainFrame, "tulemus "+tulemus + " %");
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Wrong! Try again");
                    cookie = 0;
                }
            }
        });
        return button;
    }

}
