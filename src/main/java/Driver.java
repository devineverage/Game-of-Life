import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Driver class for Conway's Game of Life
 */
public class Driver {
	
    /**
     * Entry point into the application
     */
    public static void main(String[] args) {

	if (args.length != 2) {
	    System.out.println("sbt usage: ./sbt \"run Driver <seedFile>\"");
	    System.exit(0);
	} // if

	// reference variable to hold the file object
	File path = null;

	// reference variable to hold the scanner object
	Scanner file = null;

	try {

	    // open the file
	    path = new File(args[1]);

	    // create a scanner
	    file = new Scanner(path);

	} catch (IOException e) {
	    System.out.printf("Error handling file: %s \n", e);
	    System.exit(0);
	} // try

	// the first token will be the game type
	String gameType = file.next().trim();

	// the second and third tokens are the grid size
	int rows = file.nextInt();
	int cols = file.nextInt();

	// determine the number of cells that will be alive in the initial
	// population
	int numCells = file.nextInt();

	// create a new game of life
	Life game = new Life(rows, cols);

	// if the game is random, call the init method, otherwise set according
	// to the positions in the file
	if (gameType.equalsIgnoreCase("random")) {

	    // call the init method
	    game.init(numCells);

	} else if (gameType.equalsIgnoreCase("set")) {

	    // set according to the positions in the file
	    for (int i = 0; i < numCells; i++) {

		// determine the row and column of a cell to make alive
		int row = file.nextInt();
		int col = file.nextInt();

		// make that cell alive
		game.setCell(row, col, true);

	    } // for

	} else {
	    System.out.printf("Error parsing file: %s (unrecognized game type) \n", path);
	    System.exit(0);
	} // if

	// game loop
	while (game.isRunning()) {
	    game.display();
	    game.prompt();
	    game.update();
	} // while

	System.out.println("Game Over!");

    } // main
	
} // Driver
