package uk.ac.cam.ks877.oop.tick2;

public class GameOfLife {

    private World world;

    public GameOfLife(World w) {
        world = w;
    }

    public void play() throws java.io.IOException {
        int userResponse = 0;
        while (userResponse != 'q') {
            print();
            userResponse = System.in.read();
            world.nextGeneration();
        }
    }

    public void print() {
        System.out.print("- ");
        System.out.println(world.getGenerationCount());
        for (int row = 0; row < world.getHeight(); row++) {
            for (int col = 0; col < world.getWidth(); col++) {
                System.out.print(world.getCell(col, row) ? "# " : "_ ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]) throws java.io.IOException {

        World w = null;
        System.out.println(args[0]);
        // TODO: initialise w as an ArrayWorld or a PackedWorld
        // based on the command line input
        if(args[0].equals("--array")) {
            w = new ArrayWorld(args[1]);
        }

        else if(args[0].equals("--packed")) {
            w = new PackedWorld(args[1]);
        }

        else {
            w = new ArrayWorld(args[0]);
        }

        GameOfLife gol = new GameOfLife(w);
        gol.play();
    }
}