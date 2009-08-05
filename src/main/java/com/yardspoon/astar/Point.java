package com.yardspoon.astar;

import javax.vecmath.Point2i;
import javax.vecmath.Tuple2i;

/**
 * Wrapper class around Point2i adding some utility methods
 * 
 * @author benjamin.lee
 *
 */
public class Point extends Point2i {
	
	private static final long serialVersionUID = -7152898373916421954L;

	/**
     * Constructs and initializes a Point from the specified
     * x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Point(int x, int y) {
	super(x, y);
    }

    /**
     * Constructs and initializes a Point from the array of length 2.
     * @param t the array of length 2 containing x and y in order.
     */
    public Point(int[] t) {
	super(t);
    }

    /**
     * Constructs and initializes a Point from the specified Tuple2i.
     * @param t1 the Tuple2i containing the initialization x and y
     * data.
     */
    public Point(Tuple2i t1) {
	super(t1);
    }

    /**
     * Constructs and initializes a Point to (0,0).
     */
    public Point() {
	super();
    }
    
    /**
     * @return new point equal to the neighbor to the <b>west</b> of the given point
     */
	public Point west() {
		return new Point(x - 1, y);
	}
	
	/**
	 * @return new point equal to the neighbor to the <b>east</b> of the given point
	 */
	public Point east() {
		return new Point(x + 1, y);
	}
	
	/**
	 * @return new point equal to the neighbor to the <b>north</b> of the given point
	 */
	public Point north() {
		return new Point(x, y - 1);
	}
	
	/**
	 * @return new point equal to the neighbor to the <b>south</b> of the given point
	 */
	public Point south() {
		return new Point(x, y + 1);
	}
	
	/**
	 * @return new point equal to the neighbor to the <b>north east</b> of the given point
	 */
	public Point northEast() {
		return new Point(x + 1, y - 1);
	}
	
	/**
	 * @return new point equal to the neighbor to the <b>north west</b> of the given point
	 */
	public Point northWest() {
		return new Point(x - 1, y - 1);
	}
	
	/**
	 * @return new point equal to the neighbor to the <b>south east</b> of the given point
	 */
	public Point southEast() {
		return new Point(x + 1, y + 1);
	}
	
	/**
	 * @return new point equal to the neighbor to the <b>south west</b> of the given point
	 */
	public Point southWest() {
		return new Point(x - 1, y + 1);
	}
}