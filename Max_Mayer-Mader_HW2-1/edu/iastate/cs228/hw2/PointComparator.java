package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * @author Max Mayer-Mader
 * PointComparator class comparres two points
 */
public class PointComparator implements Comparator<Point> {

    /**
     * compares two point objects
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return -1 if o2 is bigger, 0 if they are equal and 1 if o1 is bigger
     */
    @Override
    public int compare(Point o1, Point o2) {
        return o1.compareTo(o2);
    }
}
