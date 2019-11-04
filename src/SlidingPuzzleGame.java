import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SlidingPuzzleGame extends JFrame {
    private static JFrame jFrame;
    private static ImageGrid imageGrid;
    static String pathToImagesFolder = "./src/assets/";

    private static void startGame(){
        if (jFrame != null) {
            jFrame.setVisible(false);
            jFrame.removeAll();
        }
        createJFrame();
        addImageGrid();
        addRestartButton();
        addScrabbleButton();
        jFrame.setSize(880, 520);
        jFrame.setVisible(true);
    }

    public static void main (String[] args){
        startGame();
    }

    private static void addImageGrid() {
        imageGrid = new ImageGrid();
        imageGrid.setBounds(20, 20, 820, 400);
        jFrame.add(imageGrid);
    }

    private static void addRestartButton(){
        JButton restart = new JButton("Restart");
        restart.setBounds(20, 440, 100, 20);
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                startGame();
            }
        });
        jFrame.add(restart);
    }

    private static void addScrabbleButton(){
        JButton scrabble = new JButton("Scrabble");
        scrabble.setBounds(320, 440, 100, 20);
        scrabble.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                imageGrid.scrabble();
            }
        });
        jFrame.add(scrabble);
    }

    private static void createJFrame(){
        jFrame = new JFrame("Sliding Puzzle");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(null);
        jFrame.setResizable(false);
    }
}
