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
class StreamerTest {

	@Test
	void whoTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should be casual
		assertEquals(State.STREAMER, t.grid[2][1].who() );
	}
	
	@Test
	void nextTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should become an outage
		assertEquals(State.OUTAGE, t.grid[2][1].next(t).who()) ;
	}

	@Test
	void cenususTest() throws FileNotFoundException {
		//create a town

		Town t = new Town("ISP4x4.txt");
		t.grid[2][1].census(t.grid[2][1].nCensus);
		assertEquals(4, t.grid[2][1].nCensus[1] );
	}
}
