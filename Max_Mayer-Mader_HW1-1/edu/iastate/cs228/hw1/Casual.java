package edu.iastate.cs228.hw1;
import edu.iastate.cs228.hw1.State;
/*
 * @author Max Mayer-Mader
 * The Casual Class is a specific kind of town cell.
 * It represents a Casual user.
 * It is the only cell that generates profit
 */
public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {
		census(nCensus);
		//determines what the cell will become in the new grid based on its neighbours
		//if there is more than 0 resellers, casual becomes an outage
		if ((nCensus[OUTAGE] + nCensus[EMPTY] <= 1)) {
			return new Reseller(tNew, row, col);
		}
		else if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		} else if (nCensus[STREAMER] > 0) {
			return new Streamer(tNew, row, col);
		} else if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		}
		return new Casual(tNew, row, col);
	}

}
