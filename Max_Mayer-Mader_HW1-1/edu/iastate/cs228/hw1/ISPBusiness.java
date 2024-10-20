package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

//if then else for casual
//have original grid then a new that updates based on whats around original grid

/**
 * @author Max Mayer-Mader
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		
		//Loop goes through the old town and finds the next user
		for (int i = 0; i < tNew.getLength(); i++) {
			for (int j = 0; j < tNew.getWidth(); j++) {
				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);
			}
		}
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return profit for 1 month
	 */
	public static int getProfit(Town town) {
		//the number of casual users in town
		int numC=0;
		
		//for loop goes through grid to count the number of casual users
		for (TownCell[] n : town.grid) {
			for (TownCell j : n ) {
				if(j.who() == State.CASUAL)
					numC++;
			}
		}
		//return the
		return numC;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * 
	 */
	public static void main(String []args) throws FileNotFoundException {
		//new scanner for input
		Scanner scnr = new Scanner(System.in);
		
		System.out.println("How to populate grid (type 1 or 2): \n1: from a file. 2: randomly with seed");
		int num;
		
		//user enters 1 or 2
		num = scnr.nextInt();
		
		if (num == 1) {
			
			//user types the file path
			System.out.println("Please enter file path:" );
			String file = scnr.next();
			
			//create a new Town based on the given file 
			//and print it
			Town T = new Town (file);
			System.out.println(T.toString());
			
			//Create 2 town variables. 1 is o is a placeholder
			Town n = T;
			Town o;
			
			//var for all the profit earnerd over the year
			int allProfit = getProfit(n);
			System.out.println("Profit: "+getProfit(n));
			System.out.println();
			
			//loop through the next 11 months
			for (int i = 1; i<=11; i++) {
				System.out.println("After itr "+i);
				o = updatePlain(n);
				System.out.println(o.toString());
				System.out.println("Profit: "+getProfit(o));
				
				//print the profit for each mont
				allProfit += getProfit(o);
				if (i<11)
					System.out.println();
				n = o;
				
			}
			//prints the profit as a percentage. the yearly profit divided by the potential for the year
			//yealry profit / (row*col*12)
			double endProfit = 100*((double)allProfit)/(T.getLength()*T.getWidth()*12);
			String formatted = String.format("%.2f",endProfit);
			System.out.print(formatted+"%");
			
		
			}
		
		else if (num == 2) {
			
			//get user input for the row, column and seed
			System.out.println("Provide rows, cols and seed integer separated by spaces: ");
			int row = scnr.nextInt();
			int col = scnr.nextInt();
			int seed = scnr.nextInt();
			
			//create a new Town based on the number of rows, columns and seed.
			//and print it
			Town T = new Town(row, col);
			T.randomInit(seed);
			
			//print the town
			System.out.println(T.toString());
			
			//Create 2 town variables. 1 is o is a placeholder
			Town n = T;
			Town o;
			
			//var for all the profit earnerd over the year
			int allProfit = getProfit(n);
			System.out.println("Profit: "+getProfit(n));
			System.out.println();
			
			//loop through the next 11 months
			for (int i = 1; i<=11; i++) {
				System.out.println("After itr "+i);
				o = updatePlain(n);
				System.out.println(o.toString());
				System.out.println("Profit: "+getProfit(o));
				
				//print the profit for each mont
				allProfit += getProfit(o);
				if (i<11)
					System.out.println();
				n = o;
				
			}
			//prints the profit as a percentage. the yearly profit divided by the 
			double endProfit = 100*((double)allProfit)/(T.getLength()*T.getWidth()*12);
			String formatted = String.format("%.2f",endProfit);
			System.out.print(formatted+"%");
			
		}
		 
		//close scanner
		scnr.close();
		
	}
}
