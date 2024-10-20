/**
 * 
 */
package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * @author Max Mayer-Mader
 *
 */
class OutageTest {

	@Test
	void whoTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should be casual
		assertEquals(State.OUTAGE, t.grid[0][0].who() );
	}
	
	@Test
	void nextTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should become an outage
		assertEquals(State.EMPTY, t.grid[0][0].next(t).who() );
	}
	
	@Test
	void cenususTest() throws FileNotFoundException {
		//create a town

		Town t = new Town("ISP4x4.txt");
		t.grid[1][0].census(t.grid[0][0].nCensus);
		assertEquals(2, t.grid[0][0].nCensus[1]);
	}

}
