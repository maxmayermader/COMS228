package edu.iastate.cs228.hw1;
import edu.iastate.cs228.hw1.State;

/**
 * 
 * @author Max Mayer-Mader
 * The Outage Class is a specific type of TownCell
 * It represents an Outage
 * 
 *
 */
public class Outage extends TownCell {

	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.OUTAGE;
	}

	@Override
	public TownCell next(Town tNew) {		
		//determines what the cell will become in the new grid based on its neighbours
		census(nCensus);
		//no change is made
		return new Empty(tNew, row, col);
	}

}
