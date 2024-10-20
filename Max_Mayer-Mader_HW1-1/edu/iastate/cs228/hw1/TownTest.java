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
class TownTest {

	@Test
	void toStringTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//should be casual
		String s = "O R O R\nE E C O\nE S O S\nE O R R";
		assertEquals(t.toString(), s);
	}
	
	 @Test
	 void radInnitTest() throws FileNotFoundException {
		//create a town
		Town t = new Town(4, 4);
		
		//populate grid randomly
		t.randomInit(10);
		
		String s = "O R O R\nE E C O\nE S O S\nE O R R"; 
		assertEquals (s, t.toString());
	 }


}
