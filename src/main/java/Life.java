import java.util.Scanner; 
/**
 * This class represents an implementation of Conway's Game of Life.
 */
public class Life {

    // is the game running?
    private boolean running = true;

    // the number of rows
    private int rows;

    // the number of columns
    private int cols;

    // the 2-dimensional grid
    private boolean[][] grid;

    /**
     * The Constructor
     *
     * @param rows the number of rows in the grid
     * @param cols the number of columns in the grid
     */
    public Life(int rows, int cols) {

	// 1. Set the instance variables for rows and cols appropriately
    	this.rows = rows;
    	this.cols = cols;

	// 2. Create the 2-dimensional array with the appropriate size

    	grid = new boolean [rows][cols];
    } // Life


    /**
     * Generates the initial population randomly
     *
     * @param numCells the number of initial cells
     */
    public void init(int numCells) {
    	
    	int plus = 0;
    	for(int i = 0; i<getRows(); i++) {
    	//fills each spot in array with a True or False	
    		for(int t = 0; t<getCols(); t++){
    			grid[i][t] = randomBoolean();
          //fills each spot with a random boolean
    			if(grid[i][t]){
    				plus++;
    			}
 			   //exits out of loop once the number of alive cells is filled
    			if(plus == numCells){
    				break;
    				}
    			}
    		if(plus == numCells){
				break;
    		}
    	}

    } // init

    /**
     * Displays the grid.
     */
    public void display() {
    	
    	for(int h = 0; h<getCols()+2; h++){
    		System.out.print("*");
    		//prints the top row of asterisks
    	}
    	
    	System.out.println();
    	//prints an asteris at the beginning of each row
    	for(int k = 0; k<getRows(); k++) {
    		System.out.print("*");
    		for(int j = 0; j<getCols(); j++){
    			if(grid[k][j]){
          //prints a "+" if a spot is equal to true
          //I found the code needed to set color at http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    				System.out.print("\u001B[32m" + "+" + "\u001B[0m");
    			}
    			else  {
          //prints a " " if a spot is equal to false
    				System.out.print(" ");
    			}
    			
    		}
       //prints an asterisk at the end of each row
    		System.out.println("*");
    	}
    	for(int r = 0; r<getCols()+2; r++){
    		System.out.print("*");
    		//prints the bottom row of asterisks
    	}
    	System.out.println();

    } // display

    /**
     * Prompts the user for input.
     */
    public void prompt() {
    	
    String user;
    //prompts the user
    System.out.print("(c)ontinue or (q)uit: ");
    Scanner keyboard = new Scanner(System.in);
    user = keyboard.next();
    //takes the users input and determines what to do based on the response
    if(user.equals("c")){
    //if they choose to continue, the grid will update and display the new grid then prompt the user again
    	update();
    	display();
    	prompt();
    	
    }else if(user.equals("q")){
    //if they choose to quit, the game will stop
    	this.running = false;
    	//if they respond using anything other than "c" or "q" it prompts them to re-enter a response
    }else{
    	System.out.println("Incorrect input. Please try again");
    	prompt();
    }
    

    } // prompt

    /**
     * Updates the grid according to the rules.
     */
    public void update() {
    //creates new grid
    	boolean[][] newGrid;
     //sets grid size equal to the size of original grid
    	newGrid = new boolean[rows][cols];
     //goes through each element of the array and sets cells "dead" or "alive" based on its neighbors
    	for(int m = 0; m<getRows(); m++){
    		for(int b = 0; b<getCols(); b++){
       //if a cell is dead but has 3 living neighbors, its set to alive
    			if(grid[m][b] == false && numNeighbors(m,b) == 3){
    				
    				newGrid[m][b] = true;
    			}
          //if a cell is alive and has 2 or 3 living neighbors, its set to alive
    			if(grid[m][b] == true && numNeighbors(m,b) == 2 || numNeighbors(m,b) == 3){
    				
    				newGrid[m][b] = true;
    			}
          //if a cell has less than 2 neighbors or more than 3 living neighbors, its set to dead
    			if(numNeighbors(m,b) < 2 || numNeighbors(m,b) > 3){
    				
    				newGrid[m][b] = false;
    			}
    			
    		}
    		
    	}
     //sets the original grid equal to the new one
    	grid = newGrid;
    	

    } // update

    /**
     * Returns true if the game is running, false otherwise.
     *
     * @return true if the game is running, false otherwise.
     */
    public boolean isRunning() {
	return this.running;
    } // isRunning

    /** 
     * Returns the number of rows in the grid.
     * 
     * @return the number of rows in the grid
     */
    public int getRows() {
	return this.rows;
    } // getRows

    /** 
     * Returns the number of columns in the grid.
     *
     * @return the number of columns in the grid
     */
    public int getCols() {
	return this.cols;
    } // getCols

    /**
     * Returns the value of the cell at the specified location within the grid.
     *
     * @return the value of the cell at the specified location within the grid
     */
    public boolean getCell(int row, int col) {
	// HINT: You can modify this method to make your algorithm a lot easier
	return this.grid[row][col];
    } // getCell

    /**
     * Sets the value of the cell at the specified location within the grid.
     *
     * @param row the row index of the cell
     * @param col the col index of the cell
     * @param alive true if you the specified cell should be made alive, false
     *        otherwise.
     */
    public void setCell(int row, int col, boolean alive) {
	// HINT: You can modify this method to make your algorithm a lot easier
	if(row > this.rows){
	    row = row%this.rows;

		}else if(col > this.cols){
	    col = col%this.cols;

	}

	this.grid[row][col] = alive;
    } // setCell
    
    //creates a random boolean based on the Math.random() method
    public static boolean randomBoolean() {
        return Math.random() < 0.4;
    }
    //returns the number of living neighbors for a specific cell
    public int numNeighbors(int x, int y){
    	int neigh = 0;
    	int up;
    	int down;
    	int left;
    	int right;
    	int f = x;
    	int b = y;
    	//if the cell is in a row greater than 0, up equals the cell right above the cell
    	if(f > 0){
    		up = f-1;
    	}
     //if the cell is equal to 0 (the first row of array) then up equals the cell in the last row of the array
    	else{
    		up = grid.length - 1;
    	}
    	if(f < grid.length-1)
    	{
    		down = f+1;
    	}
    	else{
    		down = 0;
    	}
     //if the cell is in a column greater than 0, left equals the cell to the left
    	if(b > 0)
    	{
    		left = b-1;
    	}
      //if the cell is equal to 0 (the first column of array) then left equals the cell in the last column of the array
    	else{
    		left = getCols()-1;
    	}
    	if(b < (getCols()-1))
    	{
    		right = b+1;
    	}
    	else{
    		right = 0;
    	}
     //if the cell to the top left of the observed cell is true then the neighbor count goes up by 1
    	if(grid[up][left] == true)
    	{
    		neigh++;
    	}
     //if the cell to above the observed cell is true then the neighbor count goes up by 1
    	if(grid[up][b] == true)
    	{
    		neigh++;
    	}
     //if the cell to the top right of the observed cell is true then the neighbor count goes up by 1
    	if(grid[up][right])
    	{
    		neigh++;
    	}
     //if the cell to the left of the observed cell is true then the neighbor count goes up by 1
    	if(grid[f][left] == true)
    	{
    		neigh++;
    	}
     //if the cell to the right of the observed cell is true then the neighbor count goes up by 1
    	if(grid[f][right])
    	{
    		neigh++;
    	}
     //if the cell to the bottom left of the observed cell is true then the neighbor count goes up by 1
    	if(grid[down][left] == true)
    	{
    		neigh++;
    	}
     //if the cell below the observed cell is true then the neighbor count goes up by 1
    	if(grid[down][b] == true)
    	{
    		neigh++;
    	}
     //if the cell to the bottom right of the observed cell is true then the neighbor count goes up by 1
    	if(grid[down][right] == true)
    	{
    		neigh++;
    	}
     //returns the number of neighbors
    	return neigh;
    }


} // Life

