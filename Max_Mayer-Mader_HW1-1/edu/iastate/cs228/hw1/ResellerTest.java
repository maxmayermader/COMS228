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
class ResellerTest {

	@Test
	void whoTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should be casual
		assertEquals(State.RESELLER, t.grid[3][2].who() );
	}
	
	@Test
	void nextTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should become an outage
		assertEquals(State.EMPTY, t.grid[3][2].next(t).who() );
	}
	
	@Test
	void cenususTest() throws FileNotFoundException {
		//create a town

		Town t = new Town("ISP4x4.txt");
		t.grid[0][3].census(t.grid[0][3].nCensus);
		assertEquals(2, t.grid[0][3].nCensus[3]);
	}
}
