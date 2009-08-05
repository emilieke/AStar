package com.yardspoon.astar;

/**
 * Implements common {@link IAStarContext} methods for general uses.
 * 
 * @author benjamin.lee
 *
 */
public abstract class GeneralContext implements IAStarContext {
	protected final int height;
	protected final int width;

	/**
	 * Records height and width upfront.
	 * 
	 * @param height
	 * @param width
	 */
	public GeneralContext(int height, int width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * Does not allow diagonal movements by default.
	 */
	public boolean allowDiagonalMovements() {
		return false;
	}

	/**
	 * No extra g(x) factor for points; returns 1.
	 */
	public double g_factor(Point point) {
		return 1;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	/**
	 * Returns a direct line distance between the two points.
	 */
	public double h(Point from, Point to) {
		return directPathEstimate(from, to);
	}

	private double directPathEstimate(Point from, Point to) {
		return Math.sqrt(Math.pow(Math.abs(from.getX() - to.getX()), 2) + Math.pow(Math.abs(from.getY() - to.getY()), 2));
	}
}
