package edu.iastate.cs228.hw2;

/**
 *  
 * @author Max Mayer-Mader
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		

		//store user choice
		int userNum = 0;
		//store the number of trials
		int trials = 1;
		Algorithm[] algo = {Algorithm.SelectionSort, Algorithm.InsertionSort, Algorithm.MergeSort, Algorithm.QuickSort};
		PointScanner[] scanners = new PointScanner[4];

		//ask user for input
		//user random integers or a users file
		System.out.println("keys:  1 (random integers)  2 (file input)  3 (exit) ");
		//create scanner and get user num
		Scanner scnr = new Scanner(System.in);
		System.out.print("Trial "+trials+": ");
		userNum = scnr.nextInt();

		while (userNum != 3) {


			//random points
			if (userNum == 1) {
				System.out.print("Enter the number of points: ");
				int num = scnr.nextInt();
				Random rand = new Random(10);
				for (int i = 0; i< scanners.length; i++){
					scanners[i] = new PointScanner(generateRandomPoints(num, rand), algo[i]);
					scanners[i].scan();
				}

				//points from a file
			} else  if (userNum == 2){
				System.out.println("Points from a file");
				System.out.print("Enter file name: ");
				String name = scnr.next();

				for (int i = 0; i < scanners.length; i++){
					scanners[i] = new PointScanner(name, algo[i]);
					scanners[i].scan();
				}
			}

			//Create a string table
			System.out.println();
			String s = String.format("%-16s %-6s %s","algorithm","size","time (ns)");
			System.out.println(s);
			System.out.println("----------------------------------");
			for (int i = 0; i<scanners.length; i++){
				System.out.println(scanners[i].stats());
			}
			System.out.println("----------------------------------");
			System.out.println();

			//increase trials count
			trials++;

			//get conditions for next loop

			System.out.print("Trial "+trials+": ");
			userNum = scnr.nextInt();

		}

	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{
		//create new Point list based on given length
		Point[] pts = new Point[numPts];

		//add random point values to the array
		for (int i = 0; i<pts.length; i++){
			pts[i] = new Point(rand.nextInt(-50,51), rand.nextInt(-50,50));
		}
		return pts;
	}
	
}
