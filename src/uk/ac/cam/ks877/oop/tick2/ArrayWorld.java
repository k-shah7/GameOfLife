package uk.ac.cam.ks877.oop.tick2;

public class ArrayWorld extends World {

    private boolean[][] world;

    public ArrayWorld(String serial) {
        super(serial);
        world = new boolean[getHeight()][getWidth()];
        getPattern().initialise(this);
    }


    public boolean getCell(int col, int row) {
        if (row < 0 || row >= getHeight()) {
            return false;
        }

        if (col < 0 || col >= getWidth()) {
            return false;
        }

        return world[row][col];
    }

    public void setCell(int col, int row, boolean value) {
        if (row < 0 || row > getHeight()) {
            return;
        }

        if (col < 0 || col > getWidth()) {
            return;
        }

        world[row][col] = value;

    }


    public void nextGenerationImpl() {
        boolean[][] nextGeneration = new boolean[world.length][];
        for (int y=0; y < world.length; ++y) {
            nextGeneration[y] = new boolean[world[y].length];
            for (int x=0; x < world[y].length; ++x) {
                boolean nextCell = computeCell(x,y);
                nextGeneration[y][x] = nextCell;
            }
        }

        world = nextGeneration;
    }

}
