package edu.iastate.cs228.hw2;

/**
 * 
 * @author Max Mayer-Mader
 *
 */

import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import  java.util.ArrayList;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		points = AbstractSorter.copyPts(pts, 0, pts.length-1);
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm = algo;
		File f = new File(inputFileName);
		//FileInputStream fileByteStream = new FileInputStream(inputFileName);
		Scanner scnr = new Scanner(f);
		//create an arraylist of points
		ArrayList<Point> ptsLst = new ArrayList<>();
		//TODO
		//count number of ints and make sure input is right
		while(scnr.hasNextInt()){
			int x = scnr.nextInt();
			try {
				int y = scnr.nextInt();
				ptsLst.add(new Point(x, y));
			} catch (NoSuchElementException e){
				//TODO
				InputMismatchException InputMismatchException;
				System.out.println("There needs to be an even number of integers");
				throw new InputMismatchException();
			}
		}

		points = new Point[ptsLst.size()];
		for(int i = 0; i<ptsLst.size(); i++){
			points[i] = ptsLst.get(i);
		}

		//close scnr
		scnr.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param //algo
	 * @return
	 */
	public void scan()
	{
		//int of median x and y cords
		int medianX;
		int medianY;
		AbstractSorter aSorter;

		//determine which algorithm to use for sorting
		if (sortingAlgorithm == Algorithm.SelectionSort){
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else {
			aSorter = new InsertionSorter(points);
		}


		//sort by x values and find median x
		aSorter.setComparator(0);

		//create new long
		long scanTime1 = System.nanoTime();
		aSorter.sort();
		//ger scan time
		scanTime1 = System.nanoTime()-scanTime1;
		medianX = points[points.length/2].getX();

		//sort by y values and find median y
		aSorter.setComparator(1);

		//create new long
		long scanTime2 = System.nanoTime();
		aSorter.sort();
		//ger scan time
		scanTime2 = System.nanoTime()-scanTime2;
		//get scan time and add it
		medianY = points[points.length/2].getY();

		//add median x and y points to the MCP
		medianCoordinatePoint = new Point(medianX, medianY);

		scanTime = scanTime1 + scanTime2;

		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String s = String.format("%-16s %-6s %s", sortingAlgorithm, points.length, scanTime);
		return s;

	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: "+medianCoordinatePoint.toString();
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		//create a new writer
		try {
			FileWriter writer = new FileWriter("MCPtoFile.txt", true);
			writer.write(toString()+"\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("error");
		}
	}	

}
