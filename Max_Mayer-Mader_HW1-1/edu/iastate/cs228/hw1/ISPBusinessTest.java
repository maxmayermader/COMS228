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
class ISPBusinessTest {

	@Test
	void getProfitTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");
		//profit should be 1
		assertEquals(1, ISPBusiness.getProfit(t));
	}
	
	//test output
	@Test
	void updatePlainTest() throws FileNotFoundException {
		//create a town
		Town t = new Town("ISP4x4.txt");

		//output should match
		String s = "E E E E\nC C O E\nC O E O\nC E E E";
		assertEquals(s, ISPBusiness.updatePlain(t).toString());
		
	}
	
	

}
