package uk.ac.cam.ks877.oop.tick4;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class GameOfLife {

    private World world;
    private PatternStore store;
    private ArrayList<World> cachedWorlds = new ArrayList<>();

    public GameOfLife(PatternStore s) {
        store = s;
    }

    private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
        if(!useCloning) {
            if(this.world instanceof ArrayWorld) {
                return new ArrayWorld((ArrayWorld)world);
            }
            else {
                return new PackedWorld((PackedWorld)world);
            }
        }
        else {
            return world.clone();
        }
    }

    public void play() throws java.io.IOException, CloneNotSupportedException {

        String response="";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please select a pattern to play (l to list:");
        while (!response.equals("q")) {
            response = in.readLine();
            //System.out.println(response);
            if (response.equals("f")) {
                // System.out.println(world.getGenerationCount());
                if (world == null) {
                    System.out.println("Please select a pattern to play (l to list):");
                }
                // if next generation hasn't been already computed then use function
                // add world to cachedWorlds
                else if(world.getGenerationCount() + 1 >= cachedWorlds.size()) {
                    try {
                        world = copyWorld(true);
                        world.nextGeneration();
                        // World addWorld = copyWorld(true);
                        cachedWorlds.add(world);
                        print();
                        System.out.println("World added to cached");
                    }
                    catch(CloneNotSupportedException e) {
                        throw new CloneNotSupportedException("Something went wrong with cloning");
                    }
                }
                else {
                    int currentGen = world.getGenerationCount();
                    world = cachedWorlds.get(currentGen + 1);
                    print();

                }
            }

            else if (response.equals("b")) {
                if(world.getGenerationCount() == 0) {
                    world = cachedWorlds.get(0);
                    // System.out.println("Used cached");
                    print();
                }

                else {
                    int currentGen = world.getGenerationCount();
                    world = cachedWorlds.get(currentGen - 1);
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
                    cachedWorlds.add(world);
                    System.out.println("World added to cached");
                }
                else {
                    world = new ArrayWorld(pattern);
                    print();
                    cachedWorlds.add(world);
                    System.out.println("World added to cached");
                }
            }
        }
    }

    public static void main(String args[]) throws IOException, CloneNotSupportedException {
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
        catch (CloneNotSupportedException e) {
            System.out.print("Something went wrong with cloning");
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