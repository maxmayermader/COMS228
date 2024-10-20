package edu.iastate.cs228.hw1;
import edu.iastate.cs228.hw1.State;

/**
 * 
 * @author Max Mayer-Mader
 *The Streamer class is a specific type of TownCell
 *It represnts a streamer
 */
public class Streamer extends TownCell {

	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		census(nCensus);
		//determines what the cell will become in the new grid based on its neighbours
		if ((nCensus[OUTAGE] + nCensus[EMPTY] <= 1)) {
			return new Reseller(tNew, row, col);
		}
		else if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		} else if (nCensus[OUTAGE] > 0) {
			return new Empty(tNew, row, col);
		} else if (nCensus[CASUAL] >= 5) {
			return new Reseller(tNew, row, col);
		}
		//no change is made
		return new Streamer(tNew, row, col);
	}

}
