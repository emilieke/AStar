package com.yardspoon.astar;

import java.util.List;

/**
 * Common interface for any path finding algorithm
 * 
 * @author benjamin.lee
 *
 */
public interface IPathFinder {

	/**
	 * Return the optimal path of points between the start and end Point.  Both the start and end points should be part of the path.
	 * If start and end are equal, will return path of single point.  If no path is able to be found, empty list is returned.
	 * 
	 * @param start where to begin your path
	 * @param end where to end it
	 * @return optimal path to take
	 */
	public abstract List<Point> findPath(Point start, Point end);

}