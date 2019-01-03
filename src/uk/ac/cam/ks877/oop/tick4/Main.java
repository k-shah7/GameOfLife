package uk.ac.cam.ks877.oop.tick4;

public class Main {

    public static void main(String[] args) {
        try {
            /*Pattern p = new Pattern("glider:richard k. guy:8:8:1:1:001 101 011");
            PackedWorld test = new PackedWorld(p);
            System.out.print("Test ");
            System.out.println(test.getCell(0, 0));
            PackedWorld testcopy = new PackedWorld(test);
            System.out.print("TestCopy ");
            System.out.println(testcopy.getCell(0, 0));
            testcopy.setCell(0, 0, true);
            System.out.print("Test ");
            System.out.println(test.getCell(0, 0));
            System.out.print("TestCopy ");
            System.out.println(testcopy.getCell(0, 0));*/

            Pattern p = new Pattern("glider:richard k. guy:8:8:1:1:001 101 011");
            ArrayWorld test = new ArrayWorld(p);
            System.out.println("Test ");
            System.out.println(test.getCell(0, 0));
            ArrayWorld testcopy = new ArrayWorld(test);
            System.out.print("TestCopy ");
            System.out.println(testcopy.getCell(0, 0));

            testcopy.setCell(0,0,true);
            System.out.print("TestCopy ");
            System.out.println(testcopy.getCell(0, 0));

            System.out.print("Test ");
            System.out.println(test.getCell(0, 0));


        }
        catch (PatternFormatException e) {
            System.out.println(e);
        }

    }
}
