package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageChunk extends JPanel {
    int positionX;
    int positionY;
    private int correctPositionX;
    private int correctPositionY;
    boolean isWall;
    private BufferedImage image;
    boolean clicked = false;

    ImageChunk (int positionX, int positionY, int index, boolean isWall, MouseAdapter mouseAdapter){
        this.positionX = positionX;
        this.positionY = positionY;
        this.correctPositionX = positionX;
        this.correctPositionY = positionY;
        this.isWall = isWall;
        if (!isWall){
            setImage(index);
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clicked = true;
                mouseAdapter.mouseClicked(e);
            }
        });
    }

    public ImageChunk(int positionX, int positionY, int index, boolean isWall){
        this(positionX, positionY, index, isWall, null);
    }

        @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBounds(this.positionY * 100, this.positionX * 100, 100, 100);
        graphics.drawImage(image, 0,0, null);
        graphics.setColor(Color.GREEN);
    }

    private void setImage(int index){
        try{
            image = ImageIO.read(new File(SlidingPuzzleGame.pathToImagesFolder + index + ".jpg"));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isInPosition(){
        return (correctPositionY == positionY) && (correctPositionX == positionX);
    }

    @Override
    public String toString(){
        return positionX + ":" + positionY;
    }

}
