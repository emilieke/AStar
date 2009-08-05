package com.yardspoon.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A* algorithm implementation for finding optimal paths between two points.  Situation particular are passed to the algorithm by way of
 * the {@link IAStarContext} through the constructor and reused.  Particular points are passed to the findPath method.
 * 
 * @author benjamin.lee
 *
 */
public class AStarPathFinder implements IPathFinder {
	private final IAStarContext context;

	public AStarPathFinder(IAStarContext context) {
		this.context = context;
	}

	/**
	 * Implements method from {@link IPathFinder}.
	 */
	public List<Point> findPath(Point start, Point end) {
		assertPointIsInBounds(start);
		assertPointIsInBounds(end);
		
		PriorityQueue<PathNode> openSet = new PriorityQueue<PathNode>();
		Map<Point, PathNode> openSetLookupMap = new HashMap<Point, PathNode>();
		Set<Point> closedSet = new HashSet<Point>();
		
		PathNode startNode = new PathNode(null, start, 0, context.h(start, end));
		openSet.add(startNode);
		openSetLookupMap.put(startNode.location, startNode);
		
		while(!openSet.isEmpty()) {
			PathNode current = openSet.remove();
			openSetLookupMap.remove(current.location);
			
			if(current.location.equals(end)) {
				return reconstructPath(current);
			}
			
			closedSet.add(current.location);
			
			for(Point neighbor : findNeighbors(current.location, context.allowDiagonalMovements())) {
				if(!isInValidPoint(neighbor) && !closedSet.contains(neighbor)) {
					PathNode neighborNode = openSetLookupMap.get(neighbor);
					double tentativeGvalue = current.g + context.g_factor(neighbor);
					
					if(neighborNode == null) {
						neighborNode = new PathNode(current, neighbor, tentativeGvalue, context.h(neighbor, end));
					}
					else {
						neighborNode.g = Math.min(neighborNode.g, tentativeGvalue);
					}
					
					// TODO modify to use HashSet instead of PriorityQueue since remove is linear anyway
					// TODO the above should allow us to condense two openSets into a single one, replacing openSet.remove() w/ linear time lookup
					// TODO consider inserting things into self sorting data structure, that way remove is constant time and lookups are log n (binary search?)
					openSet.remove(neighborNode);
					openSet.add(neighborNode);
				}
			}
		}
		
		return new ArrayList<Point>();
	}

	private List<Point> findNeighbors(Point location, boolean allowDiagonals) {
		List<Point> neighbors = new ArrayList<Point>(8);
		
		addIfValid(neighbors, location.west());
		addIfValid(neighbors, location.east());
		addIfValid(neighbors, location.north());
		addIfValid(neighbors, location.south());
		
		if(allowDiagonals) {
			addIfValid(neighbors, location.northEast());
			addIfValid(neighbors, location.northWest());
			addIfValid(neighbors, location.southEast());
			addIfValid(neighbors, location.southWest());
		}
		
		return neighbors;
	}

	private void addIfValid(List<Point> neighbors, Point neighbor) {
		if(!isInValidPoint(neighbor)) {
			neighbors.add(neighbor);
		}
	}

	private List<Point> reconstructPath(PathNode node) {
		List<Point> path = new ArrayList<Point>();
		
		while(node != null) {
			path.add(node.location);
			node = node.parent;
		}
		
		Collections.reverse(path);
		
		return path;
	}

	private void assertPointIsInBounds(Point point) {
		if(isInValidPoint(point)) {
			throw new IndexOutOfBoundsException();
		}
	}

	// TODO reverse sign
	private boolean isInValidPoint(Point point) {
		return (point.getX() < 0) || 
		        (point.getY() < 0) || 
		        (point.getX() >= context.getWidth()) || 
		        (point.getY() >= context.getHeight()) || 
		        (!context.isTraversable(point));
	}
}