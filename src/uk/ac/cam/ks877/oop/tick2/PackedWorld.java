package uk.ac.cam.ks877.oop.tick2;

public class PackedWorld extends World  {
    private long world;

    public PackedWorld(String serial) throws java.io.IOException{
        super(serial);
        if(getHeight() * getWidth() > 64) {
            throw new java.io.IOException ("Too long for long implementation");
        }

        world = 0L;
        getPattern().initialise(this);

    }

    private static boolean get_helper(long packed, int position) {
        long check = ((packed >> position) << 63)>>>63;
        return (check == 1);
    }

    public static long set_helper(long packed, int position, boolean value) {
        if (value) {
            packed |= (1L << position);
        }
        else {
            packed &= ~(1L << position);
        }
        return packed;
    }

    public boolean getCell(int col, int row) {

        int cellNumber = col + (getWidth() * row);

        if ((row > getWidth()-1 || row < 0) || (col > getHeight()-1 || col < 0)) {
            return false;
        } else {
            return get_helper(world, cellNumber);
        }
    }

    public void setCell(int col, int row, boolean value) {
        int position = col + (getWidth() * row);

        if ((row > getWidth()-1 || row < 0) || (col > getHeight()-1 || col < 0)) {
            System.out.println("Sorry out of bounds");
        }

        else {
            world = set_helper(world, position, value);
        }

    }

    public void nextGenerationImpl() {
        long tempWorld = world;
        for (int row = 0; row < getWidth(); row++) {
            for (int col = 0; col < getHeight(); col++) {
                boolean cellState = computeCell(col, row);
                int cellNumber = col + (getWidth() * row);
                tempWorld = set_helper(tempWorld, cellNumber, cellState);
            }
        }
        world = tempWorld;
    }

}
