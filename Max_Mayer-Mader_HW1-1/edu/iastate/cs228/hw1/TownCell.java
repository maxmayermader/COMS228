package edu.iastate.cs228.hw1;

/**
 * 
 * @author Max Mayer-Mader
 *	TownCell is an abstract class
 *  TownCell is a specific cell in a Town grid. It is initialized in a town at a specific row and column.
 *  
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neigborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 
		
		//Initialize bounds for census
		int r = row;
		int c = col;
		int endRow = row;
		int endCol = col;
		
		//Makes sure that the coordinate are within bounds of the grid
		if (row > 0) {
			r = row - 1; 
		}
		if (col > 0) {
			c = col - 1;
		}
		if (row + 1 < plain.getLength()) {
			endRow = row + 1;
		}
		if (col + 1 < plain.getWidth()) {
			endCol = col + 1;
		}
		
		//loops through a smaller 2d array centered around a specific town cell
		for (int i = r; i <= endRow; i++) {
			for (int j = c; j <= endCol; j++) {
				if (plain.grid[i][j].who() == State.RESELLER) {
					nCensus[RESELLER]++;
				} else if (plain.grid[i][j].who() == State.EMPTY) {
					nCensus[EMPTY]++;
				} else if (plain.grid[i][j].who() == State.CASUAL ) {
					nCensus[CASUAL]++;
				} else if (plain.grid[i][j].who() == State.OUTAGE ) {
					nCensus[OUTAGE]++;
				} else {
					nCensus[STREAMER]++;
				}
			}
		}
		
		//Removes the the middle TownCell from being counted
		if (who() == State.RESELLER) {
			nCensus[RESELLER]--;
		} else if (who() == State.EMPTY) {
			nCensus[EMPTY]--;
		} else if (who() == State.CASUAL) {
			nCensus[CASUAL]--;
		} else if (who() == State.OUTAGE) {
			nCensus[OUTAGE]--;
		} else {
			nCensus[STREAMER]--;
		}
		

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
	
	
}
