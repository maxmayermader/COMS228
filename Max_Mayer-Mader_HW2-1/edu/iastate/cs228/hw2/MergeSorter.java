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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int l = 0;
		int h = pts.length-1;
		int m = (h+l)/2;

		if (pts.length == 1){
			return;
		}

		else {
			Point[] lh = copyPts(pts, l, m);
			Point[] rh = copyPts(pts, m + 1, h);

			mergeSortRec(lh);
			mergeSortRec(rh);

			merge(pts, lh, rh, lh.length, rh.length);

		}

	}

		private void merge(Point[] pts, Point[] lh, Point[] rh, int l, int r){
			int i = 0, j = 0, k = 0;
			while (i < l && j < r) {
				if (pointComparator.compare(lh[i], rh[j]) == -1) {
					pts[k++] = lh[i++];
				}
				else {
					pts[k++] = rh[j++];
				}
			}
			while (i < l) {
				pts[k++] = lh[i++];
			}
			while (j < r) {
				pts[k++] = rh[j++];
			}

		}


}
