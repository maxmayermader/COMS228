/**
 * 
 */
package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Max Mayer-Mader
 *
 */
class EmptyTest {

	@Test
	void whoTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		
		//should be empty
		assertEquals(State.EMPTY, t.grid[1][0].who());
	}
	
	@Test
	void nextTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		
		//next state should be causel
		assertEquals(State.CASUAL, t.grid[1][0].next(t).who() );
	}
	
	@Test
	void cenususTest() throws FileNotFoundException {
		//create a town

		Town t = new Town("ISP4x4.txt");
		t.grid[1][0].census(t.grid[1][0].nCensus);
		assertEquals(1, t.grid[1][0].nCensus[0]);
	}

}
