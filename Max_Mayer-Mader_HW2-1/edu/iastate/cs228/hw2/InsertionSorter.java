package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Max Mayer-Mader
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "insertion sort";
	}	

	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		//get length of array
		int n = points.length;

		//loop through array
		for (int i = 1; i<n; i++){
			//find min val
			Point min = points[i];
			int j = i-1;
			while (j >= 0 && pointComparator.compare(points[j], min) == 1){
				points[j+1] = points[j];
				j--;
			}

			//move min to correct pos
			points[j+1] = min;
		}
	}		
}
