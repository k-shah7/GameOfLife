package uk.ac.cam.ks877.oop.tick3;

public class Pattern implements Comparable<Pattern> {

    private String name;
    private String author;
    private int width;
    private int height;
    private int startCol;
    private int startRow;
    private String cells;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public String getCells() {
        return cells;
    }


    public Pattern(String format) throws PatternFormatException {


        if(format.equals("")) {
            throw new PatternFormatException("Please specify a pattern.");
        }

        String[] parts = format.split(":"); //split the input string by :

        if(parts.length != 7) {
            throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found " + parts.length + ")");
        }
        //check width is an integer
        try {
            width = Integer.parseInt(parts[2]);
        }

        catch (NumberFormatException e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number ('" + parts[2] + "' given)." );
        }
        //check height is an integer
        try {
            height = Integer.parseInt(parts[3]);
        }

        catch (NumberFormatException e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the height field as a number ('" + parts[3] + "' given)." );
        }

        //check startCol is an integer
        try {
            startCol = Integer.parseInt(parts[4]);
        }

        catch (NumberFormatException e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startX field as a number ('" + parts[4] + "' given)." );
        }

        //check startRow is an integer
        try {
            startRow = Integer.parseInt(parts[5]);
        }

        catch (NumberFormatException e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startY field as a number ('" + parts[5] + "' given)." );
        }

        cells = parts[6];
        String[] state_cells = cells.split(" ");


        for(int i = 0; i<state_cells.length; i++) {
            for (int j = 0; j < state_cells[i].length(); j++) {
                //check that each character of state_cells is an integer
                if (state_cells[i].charAt(j) != '1' && state_cells[i].charAt(j) != '0') {
                    throw new PatternFormatException("Invalid pattern format: Malformed pattern '" + cells + "'");
                }
            }
        }

        name = parts[0];
        author = parts[1];


    }

    public void initialise(World world) {
        String[] state_cells = cells.split(" "); //splits the cells by spaces, indicating the next row

        for (int i=0; i<state_cells.length; i++) { //loops through the 'rows' in state_cells
            for (int j=0; j<state_cells[i].length(); j++) { //loops through the characters in the rows of state_cells
                if (state_cells[i].charAt(j) == '1') { //if the char is a 1 then change from false to true
                    world.setCell(startCol + j, startRow + i, true);
                }

                else {
                    world.setCell(startCol + j, startRow + i, false);
                }
            }
        }

    }

    @Override
    public int compareTo(Pattern o) {
        String o_name = o.getName();
        return this.name.compareToIgnoreCase(o_name);
    }

    //print function for pattern
    public void print() {
        System.out.println("Pattern object for " + this.getName());
    }

    public static void main(String[] args) throws Exception {
        String initial_state = args[0];
        Pattern info = new Pattern(initial_state);
    }

}