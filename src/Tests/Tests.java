package Tests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tests {

    @Test
    public void testIsInPosition() {
        Game.ImageChunk imageChunk = new Game.ImageChunk(1, 1, 1, false);

        Boolean result = imageChunk.isInPosition();

        assertEquals(true, result);
    }

    @Test
    public void testImageChunkToString() {
        Game.ImageChunk imageChunk = new Game.ImageChunk(2, 1, 1, false);

        String result = imageChunk.toString();

        assertEquals("2:1", result);
    }

    @Test
    public void testMoveByStep() {
        Game.ImageGrid imageGrid = new Game.ImageGrid();
        Game.ImageChunk imageChunk = new Game.ImageChunk(1, 1, 1, false);

        imageGrid.moveByStep(true, 1, imageChunk);
        String result = imageChunk.toString();

        assertEquals("2:1", result);
    }
}
