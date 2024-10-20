package edu.iastate.cs228.hw1;
import edu.iastate.cs228.hw1.State;

/**
 * 
 * @author Max Mayer-Mader
 *The Empty Class is a sepcifc type of TownCell
 *It Represents an Empty cell
 */
public class Empty extends TownCell {

	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		census(nCensus);
		//determines what the cell will become in the new grid based on its neighbours
		if ((nCensus[OUTAGE] + nCensus[EMPTY] <= 1)) {
			return new Reseller(tNew, row, col);
		}
		//no change is made
		return new Casual(tNew, row, col);
	}

}
