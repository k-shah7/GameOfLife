package uk.ac.cam.ks877.oop.tick3;
import java.io.*;
import java.util.*;

public class GameOfLife {

    private World world;
    private PatternStore store;

    public GameOfLife(PatternStore s) {
        store = s;
    }

    public void play() throws java.io.IOException {

        String response="";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please select a pattern to play (l to list:");
        while (!response.equals("q")) {
            response = in.readLine();
            //System.out.println(response);
            if (response.equals("f")) {
                if (world == null) {
                    System.out.println("Please select a pattern to play (l to list):");
                }
                else {
                    world.nextGeneration();
                    print();
                }
            }
            else if (response.equals("l")) {
                List<Pattern> names = store.getPatternsNameSorted();
                int i = 0;
                for (Pattern p : names) {
                    System.out.println(i+" "+p.getName()+"  ("+p.getAuthor()+")");
                    i++;
                }
            }
            else if (response.startsWith("p")) {
                List<Pattern> names = store.getPatternsNameSorted();
                // Extract the integer after the p in response
                int x = Integer.parseInt(response.split(" ")[1]);
                // Get the associated pattern
                Pattern pattern = names.get(x);
                // Initialise world using PackedWorld or ArrayWorld based
                // on pattern world size
                if (pattern.getHeight() * pattern.getWidth() <= 64) {
                    world = new PackedWorld(pattern);
                    print();
                }
                else {
                    world = new ArrayWorld(pattern);
                    print();
                }
            }
        }
    }

    public static void main(String args[]) throws IOException {
        if (args.length!=1) {
            System.out.println("Usage: java GameOfLife <path/url to store>");
            return;
        }

        try {
            PatternStore ps = new PatternStore(args[0]);
            GameOfLife gol = new GameOfLife(ps);
            gol.play();
        }
        catch (IOException ioe) {
            System.out.println("Failed to load pattern store");
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

}