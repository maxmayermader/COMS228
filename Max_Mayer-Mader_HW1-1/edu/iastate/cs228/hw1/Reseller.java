package edu.iastate.cs228.hw1;
import edu.iastate.cs228.hw1.State;

/**
 * 
 * @author Max Mayer-Mader
 *The Reseller is a specifc type of TownClass
 *it repreents a Reseller
 */
public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		census(nCensus);
		//determines what the cell will become in the new grid based on its neighbours
		if (nCensus[CASUAL] <= 3) {
			return new Empty(tNew, row, col);
		} else if (nCensus[EMPTY] >= 3) {
			return new Empty(tNew, row, col);
		} else if (nCensus[STREAMER] > 0) {
			return new Outage(tNew, row, col); 
		} else if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		}
		//no change is made
		return new Reseller(tNew, row, col);
	}

}
