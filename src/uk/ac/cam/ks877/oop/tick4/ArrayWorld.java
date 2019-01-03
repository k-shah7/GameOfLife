package uk.ac.cam.ks877.oop.tick4;

public class ArrayWorld extends World implements Cloneable {

    private boolean[][] world;
    private boolean[] deadRow;

    public ArrayWorld(String serial) throws PatternFormatException {
        super(serial);
        world = new boolean[getHeight()][getWidth()];
        deadRow = new boolean[getWidth()];
        System.out.println("ArrayWorldInitialised");
        getPattern().initialise(this);
        for(int i=0; i<getHeight(); i++) {
            int flag = 0;
            for(int j=0; j<getWidth(); j++) {
                if (world[i][j]) {
                    flag++;
                }
            }
            if(flag == 0) {
                world[i] = deadRow;
            }
        }
    }

    public ArrayWorld(Pattern p) {
        super(p);
        // System.out.println("ArrayWorldInitialised");
        world = new boolean[getHeight()][getWidth()];
        // System.out.println("new world made");
        deadRow = new boolean[getWidth()];
        p.initialise(this);
        // System.out.println("p initialised");
        for(int i=0; i<getHeight(); i++) {
            int flag = 0;
            for(int j=0; j<getWidth(); j++) {
                if (world[i][j]) {
                    flag++;
                }
            }
            if(flag == 0) {
                world[i] = deadRow;
            }
        }
    }

    // copy constructor
    public ArrayWorld(ArrayWorld copy) {
        super(copy);
        int width = copy.getWidth();
        int height = copy.getHeight();
        this.world = new boolean[height][width];
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                this.world[i][j] = copy.world[i][j];
            }
        }
        this.deadRow = copy.deadRow;
    }

    // using cloneable
    @Override
    public ArrayWorld clone() throws CloneNotSupportedException {
        ArrayWorld copy = (ArrayWorld) super.clone();
        boolean[][] temp = new boolean[copy.getHeight()][copy.getWidth()];
        for (int i = 0; i < copy.getHeight(); i++) {
            for(int j = 0; j < copy.getWidth(); j++) {
                temp[i][j] = copy.world[i][j];
            }
        }
        copy.world = temp;
        copy.deadRow = this.deadRow;
        for(int i=0; i<getHeight(); i++) {
            int flag = 0;
            for(int j=0; j<getWidth(); j++) {
                if (copy.world[i][j]) {
                    flag++;
                }
            }
            if(flag == 0) {
                copy.world[i] = copy.deadRow;
            }
        }
        return copy;
    }


    public boolean getCell(int col, int row) {
        // int height = world.length;
        // int width = world[0].length;
        // System.out.println("height " + height);
        // System.out.println("width " + width);
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
/*        for(int i=0; i<getHeight(); i++) {
            int flag = 0;
            for(int j=0; j<getWidth(); j++) {
                if (world[i][j]) {
                    flag++;
                }
            }
            if(flag == 0) {
                world[i] = deadRow;
            }
        }*/
    }

}
