import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame{

    private static JFrame mainFrame;

    public static void main(String[] args) throws IOException {
            mainFrame = new ArticlesUI();
            mainFrame.setVisible(true);
            }
}