package uk.ac.cam.ks877.oop.tick1;

public class ArrayLife {

    private boolean[][] world;
    private int width;
    private int height;
    private Pattern pattern;

    public ArrayLife(String format) {
        pattern = new Pattern(format);
        height = pattern.getHeight();
        width = pattern.getWidth();
        world = new boolean[height][width];
        for (int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                world[i][j] = false;
            }
        }
        //sets world to all false
        pattern.initialise(world);
        //based on the input sets up world so that the alive cells are true
    }


    public boolean getCell(int col, int row) {
        if (row < 0 || row >= height) {
            return false;
        }

        if (col < 0 || col >= width) {
            return false;
        }

        return world[row][col];
    }

    public void setCell(int col, int row, boolean value) {
        if (row < 0 || row > height) {
            return;
        }

        if (col < 0 || col > width) {
            return;
        }

        world[row][col] = value;

    }

    public void print() {
        System.out.println("-");
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                System.out.print(getCell(col, row) ? "# " : "_ ");
            }
            System.out.println();
        }
    }

    private int countNeighbours(int col, int row) {
        int neighbour_no = 0;
        for (int cell_row = row - 1; cell_row < row + 2; cell_row++) {
            for (int cell_col = col - 1; cell_col < col + 2; cell_col++) {
                if (getCell(cell_col, cell_row)) {
                    neighbour_no++;
                }
            }
        }

        if (getCell(col, row)) {
            neighbour_no--;
        }

        return neighbour_no;
    }

    private boolean computeCell(int col, int row) {
        boolean liveCell = getCell(col, row);
        int neighbours = countNeighbours(col, row);
        boolean nextCell = false;

        if (liveCell && neighbours == 2 || neighbours == 3) {
            nextCell = true;
        }

        return nextCell;
    }

    public void nextGeneration() {
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

    public void play() throws java.io.IOException {
        int userResponse = 0;
        while (userResponse != 'q') {
            print();
            userResponse = System.in.read();
            nextGeneration();
        }
    }

    public static void main(String[] args) throws Exception {
        String initial_state = args[0]; //initial state is the user input that specifies the board
        ArrayLife al = new ArrayLife(initial_state); //created an ArrayLife object with format as the user input initial
        al.play();
    }
}
