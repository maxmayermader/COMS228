package edu.iastate.cs228.hw1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.io.FileInputStream;


/**
 *  @author Max Mayer-Mader
 * Town Class is used to create a new town by using either a file or by being populated randomly.
 * Town Class has a 2d array for all users in the neighbour
 * 
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
		
		
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		FileInputStream fileByteStream = null;
		fileByteStream = new FileInputStream(inputFileName);
		Scanner scnr = new Scanner(fileByteStream);
		
		this.length = scnr.nextInt();
		this.width = scnr.nextInt();
		
		 grid = new TownCell[length][width];
		 for (int i = 0; i<length; i++) {
			 for (int j = 0; j<width; j++) {
				 String letter = scnr.next();
				 if (letter.equals("C")) {
					 grid[i][j] = new Casual(this, i, j);
				 } else if (letter.equals("E")) {
					 grid[i][j] = new Empty(this, i, j);
				 } else if (letter.equals("O")) {
					 grid[i][j] = new Outage(this, i, j);
				 } else if (letter.equals("R")) {
					 grid[i][j] = new Reseller(this, i, j);
				 } else if (letter.equals("S")) {
					 grid[i][j] = new Streamer(this, i, j);
				 }
			 }
		 }
		 scnr.close();
	}
	
	/**
	 * Returns width of the grid.
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		//create new random object by using given seed
		Random rand = new Random(seed);
		//initialize TownCell based on length and width
		grid = new TownCell[length][width];
		
		//loop to populate the town
		 for (int i = 0; i<length; i++) {
			 for (int j = 0; j<width; j++) {
				 //get a random value with a range from 0-4
				 int type = rand.nextInt(5);
				 if (type == TownCell.CASUAL) {
					 grid[i][j] = new Casual(this, i, j);
				 } else if (type == TownCell.EMPTY) {
					 grid[i][j] = new Empty(this, i, j);
				 } else if (type == TownCell.OUTAGE) {
					 grid[i][j] = new Outage(this, i, j);
				 } else if (type == TownCell.RESELLER) {
					 grid[i][j] = new Reseller(this, i, j);
				 } else if (type == TownCell.STREAMER) {
					 grid[i][j] = new Streamer(this, i, j);
				 }
			 }
		 }
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j].who() == State.CASUAL) {
					s+="C";
				} else if (grid[i][j].who() == State.EMPTY) {
					s+="E";
				} else if (grid[i][j].who() == State.OUTAGE) {
					s+="O";
				} else if (grid[i][j].who() == State.RESELLER) {
					s+="R";
				} else if (grid[i][j].who() == State.STREAMER) {
					s+="S";
				}
				if (j < width-1) {
					s+=" ";
				}
			}
			if (i < length-1)
				s+="\n";
		}
		return s;
	}
}
