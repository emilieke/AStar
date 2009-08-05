package com.yardspoon.astar;

import com.yardspoon.astar.GeneralContext;
import com.yardspoon.astar.IAStarContext;
import com.yardspoon.astar.Point;

/**
 * Stub {@link IAStarContext} object for testing.
 * 
 * @author benjamin.lee
 *
 */
public class StubAStarContext extends GeneralContext {
	private final boolean allowDiagonal;
	private final Point highEstimatePoint;
	private final static int[][] traversableSquares = new int[][] {
		{ 0,0,0,0,0,0,0,1,0,0 },
		{ 0,0,0,0,0,1,0,1,0,0 },
		{ 0,0,0,0,0,1,0,0,0,0 },
		{ 0,0,0,0,0,1,0,0,0,0 },
		{ 0,0,0,0,0,1,0,0,0,0 },
		{ 0,1,1,1,1,0,0,0,0,0 },
		{ 0,0,0,0,0,0,0,0,0,0 },
		{ 1,1,0,0,0,0,0,0,0,0 },
		{ 0,0,0,0,0,0,0,0,0,1 },
		{ 0,0,0,0,0,0,0,0,1,0 }
	};
	
	public StubAStarContext() {
		this(false);
	}

	public StubAStarContext(boolean allowDiagonal) {
		super(10, 10);
		this.allowDiagonal = allowDiagonal;
		this.highEstimatePoint = new Point(-1, -1);
	}
	
	public StubAStarContext(Point highEstimatePoint) {
		super(10, 10);
		this.allowDiagonal = false;
		this.highEstimatePoint = highEstimatePoint;
	}

	@Override
	public boolean allowDiagonalMovements() {
		return allowDiagonal;
	}

	@Override
	public double h(Point from, Point to) {
		double estimate = super.h(from, to);
		
		if(highEstimatePoint.equals(from)) {
			estimate *= 10;
		}
		
		return estimate;
	}

	public boolean isTraversable(Point point) {
		return traversableSquares[point.getX()][point.getY()] == 0;
	}
}