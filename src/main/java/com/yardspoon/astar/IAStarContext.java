package com.yardspoon.astar;

/**
 * Context in which to run the A* algorithm.  Supplies the search algorithm with the situation specific particulars.
 * 
 * @author benjamin.lee
 *
 */
public interface IAStarContext {
	/**
	 * @return height of the 2d search space
	 */
	public abstract int getHeight();
	
	/**
	 * @return width of the 2d search space
	 */
	public abstract int getWidth();
	
	/**
	 * Returns the relative cost of accessing a particular point in the seach space.  Normal return value is 1.0.
	 * 
	 * @param point point to evaluate
	 * @return cost true to access that point
	 */
	public abstract double g_factor(Point point);
	
	/**
	 * Estimated cost to travel between the given points.  This estimate must not be an overestimte if optimal solutions are desired.
	 * Often this is a direct line cost.
	 * 
	 * @param from where to begin
	 * @param to where you are going
	 * @return estimate cost to get there
	 */
	public abstract double h(Point from, Point to);
	
	/**
	 * @param point point to evaluate
	 * @return True if this point is traversable, False otherwise.
	 */
	public abstract boolean isTraversable(Point point);
	
	/**
	 * @return specifies if this context allows paths that cross between diagonally located points
	 */
	public abstract boolean allowDiagonalMovements();
}
