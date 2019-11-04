import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageGrid extends JPanel {
    private ImageChunk[][] imageChunksArray = new ImageChunk[6][6];//x; y;
    private BufferedImage previewImage;
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            for (ImageChunk[] imageChunks : imageChunksArray) {
                for (ImageChunk imageChunk : imageChunks) {
                    if (imageChunk == null) continue;
                    if (imageChunk.clicked) {
                        imageChunk.clicked = false;
                        moveImage(imageChunk);
                        if (isDone()){
                            System.out.println("Victory");
                        }
                    }
                }
            }
        }
    };

    private boolean isDone(){
        for (ImageChunk[] imageChunks : imageChunksArray) {
            for (ImageChunk imageChunk : imageChunks) {
                if (imageChunk != null)
                    if (!imageChunk.isInPosition())
                        return false;
            }
        }
        return true;
    }

    ImageGrid() {
        fillImageChunksArray();
        scrabble();
        addImageChunksToJPanel();
        setPreviewImage();
        addMouseListener(mouseAdapter);
    }

    private void setPreviewImage() {
        try {
            previewImage = ImageIO.read(new File(SlidingPuzzleGame.pathToImagesFolder + "preview.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addImageChunksToJPanel() {
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if (imageChunksArray[i][j] != null) {
                    this.add(imageChunksArray[i][j]);
                }
            }
        }
    }

    private void fillImageChunksArray() {
        for (int i = 0; i < 6; i++) {
            imageChunksArray[i][0] = new ImageChunk(0, i, -1, true);
            imageChunksArray[i][5] = new ImageChunk(5, i, -1, true);
            if (i > 0 && i < 5) {
                imageChunksArray[0][i] = new ImageChunk(i, 0, -1, true);
                imageChunksArray[5][i] = new ImageChunk(i, 5, -1, true);
            }
        }
        int index = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                imageChunksArray[i][j] = new ImageChunk(i - 1, j - 1, index, false, mouseAdapter);
                index++;
            }
        }
        imageChunksArray[1][1] = null;
    }

    private void moveImage(@NotNull ImageChunk imageChunk) {
        if (imageChunksArray[imageChunk.positionX][imageChunk.positionY + 1] == null) {
            moveLeft(imageChunk);
        } else if (imageChunksArray[imageChunk.positionX + 2][imageChunk.positionY + 1] == null) {
            moveRight(imageChunk);
        } else if (imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY] == null) {
            moveUp(imageChunk);
        } else if (imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 2] == null) {
            moveDown(imageChunk);
        } else {
            return;
        }
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(previewImage, 420, 0, null);
    }

    private void moveLeft(@NotNull ImageChunk imageChunk) {
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = null;
        imageChunk.positionX--;
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = imageChunk;
    }

    private void moveRight(@NotNull ImageChunk imageChunk) {
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = null;
        imageChunk.positionX++;
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = imageChunk;
    }

    private void moveUp(@NotNull ImageChunk imageChunk) {
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = null;
        imageChunk.positionY--;
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = imageChunk;
    }

    private void moveDown(@NotNull ImageChunk imageChunk) {
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = null;
        imageChunk.positionY++;
        imageChunksArray[imageChunk.positionX + 1][imageChunk.positionY + 1] = imageChunk;
    }

    private List<ImageChunk> getMovableImageChunks(){
        List<ImageChunk> movableImageChunks = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            for (int j = 1; j < 5; j++){
                if (imageChunksArray[i][j] == null){
                    if (!imageChunksArray[i + 1][j].isWall)
                        movableImageChunks.add(imageChunksArray[i + 1][j]);
                    if (!imageChunksArray[i - 1][j].isWall)
                        movableImageChunks.add(imageChunksArray[i - 1][j]);
                    if (!imageChunksArray[i][j + 1].isWall)
                        movableImageChunks.add(imageChunksArray[i][j + 1]);
                    if (!imageChunksArray[i][j - 1].isWall)
                        movableImageChunks.add(imageChunksArray[i][j - 1]);
                }
            }
        }
        return movableImageChunks;
    }

    void scrabble() {
        double randomNumber;
        for (int i = 0; i < 50; i++) {
            List<ImageChunk> imageChunks = getMovableImageChunks();
            randomNumber = Math.random() * imageChunks.size();
            if (randomNumber > 3)
                moveImage(imageChunks.get(3));
            else if (randomNumber > 2)
                moveImage(imageChunks.get(2));
            else if (randomNumber > 1)
                moveImage(imageChunks.get(1));
            else
                moveImage(imageChunks.get(0));
        }
        repaint();
    }
}
