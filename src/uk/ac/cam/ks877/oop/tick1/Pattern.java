package uk.ac.cam.ks877.oop.tick1;

public class Pattern {

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


    public Pattern(String format) {
        String[] parts = format.split(":"); //split the input string by :
        name = parts[0];
        author = parts[1];
        width = Integer.parseInt(parts[2]); //parseInt makes the string an integer
        height = Integer.parseInt(parts[3]);
        startCol = Integer.parseInt(parts[4]);
        startRow = Integer.parseInt(parts[5]);
        cells = parts[6];

    }

    public void initialise(boolean[][] world) {
        String[] state_cells = cells.split(" "); //splits the cells by spaces, indicating the next row

        for (int i=0; i<state_cells.length; i++) { //loops through the 'rows' in state_cells
            for (int j=0; j<state_cells[i].length(); j++) { //loops through the characters in the rows of state_cells
                if (state_cells[i].charAt(j) == '1') { //if the char is a 1 then change from false to true
                    world[startRow + i][startCol + j] = true;
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        String initial_state = args[0];
        Pattern info = new Pattern(initial_state);
    }

}