/**
 * 
 */
package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import edu.iastate.cs228.hw1.State;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Max Mayer-Mader
 *
 */
class CasualTest  {
	
	@Test
	void whoTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should be casual
		assertEquals(State.CASUAL, t.grid[1][2].who() );
	}
	
	@Test
	void nextTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should become an outage
		assertEquals(State.OUTAGE, t.grid[1][2].next(t).who());
	}
	
	@Test
	void cenususTest() throws FileNotFoundException {
		//create a town

		Town t = new Town("ISP4x4.txt");
		t.grid[1][2].census(t.grid[1][2].nCensus);
		assertEquals(0, t.grid[1][2].nCensus[2]);
	}
}

