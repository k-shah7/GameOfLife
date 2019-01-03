package uk.ac.cam.ks877.oop.tick3;

public abstract class World {
    private int generation;
    private Pattern pattern;

    public World(String pattern) throws PatternFormatException{
        try {
            generation = 0;
            this.pattern = new Pattern(pattern);
        }

        catch(PatternFormatException e) {
            throw new PatternFormatException("Something is wrong try again please.");
        }
    }

    public World(Pattern p) {
        generation = 0;
        pattern = p;
    }

    public int getWidth() {
        return pattern.getWidth();
    }

    public int getHeight() {
        return pattern.getHeight();
    }

    public int getGenerationCount() {
        return generation;
    }

    protected void incrementGenerationCount() {
        generation ++;
    }

    protected Pattern getPattern() {
        return pattern;
    }

    public void nextGeneration() {
        nextGenerationImpl();
        generation++;
    }
    public abstract void nextGenerationImpl();

    public abstract boolean getCell(int c, int r);

    public abstract void setCell(int c, int r, boolean value);

    protected int countNeighbours(int c, int r) {
        int neighbour_no = 0;
        for (int cell_row = r - 1; cell_row < r + 2; cell_row++) {
            for (int cell_col = c - 1; cell_col < c + 2; cell_col++) {
                if (getCell(cell_col, cell_row)) {
                    neighbour_no++;
                }
            }
        }

        if (getCell(c, r)) {
            neighbour_no--;
        }

        return neighbour_no;
    }

    protected boolean computeCell(int c, int r) {
        boolean liveCell = getCell(c, r);
        int neighbours = countNeighbours(c, r);
        boolean nextCell = false;

        if (liveCell && neighbours == 2 || neighbours == 3) {
            nextCell = true;
        }

        return nextCell;
    }

}