package com.yardspoon.astar;

/**
 * Common unit to represent one search node.  Note: hashcode and equals only take location into account.
 * 
 * @author benjamin.lee
 *
 */
class PathNode implements Comparable<PathNode> {
	public PathNode parent;
	public Point location;
	public double g;
	public double h;
	
	/**
	 * @param parent parent PathNode (current shortest path to this node) which may change later
	 * @param location where on the search grid this node lives
	 * @param g true shortest path to this node from start point, via parent
	 * @param h estimated path cost to end point from this node
	 */
	public PathNode(PathNode parent, Point location, double g, double h) {
		this.parent = parent;
		this.location = location;
		this.g = g;
		this.h = h;
	}

	/**
	 * Computes f(x) = g(x) + h(x) : where x is this node
	 * 
	 * @return g+h
	 */
	public double f() {
		return g + h;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathNode other = (PathNode) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	/**
	 * compares PathNodes by their f(x) values for sorting
	 */
	public int compareTo(PathNode other) {
		return (int)(f() * 100.0 - other.f() * 100.0);
	}
}