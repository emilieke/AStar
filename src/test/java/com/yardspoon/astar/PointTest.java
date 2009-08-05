package com.yardspoon.astar;

import org.junit.Test;

import com.yardspoon.astar.Point;

import static org.junit.Assert.*;

public class PointTest {

	@Test
	public void checkNeighbors() {
		Point start = new Point(5, 5);
		
		assertEquals(new Point(4, 5), start.west());
		assertEquals(new Point(6, 5), start.east());
		assertEquals(new Point(5, 4), start.north());
		assertEquals(new Point(5, 6), start.south());
		assertEquals(new Point(6, 4), start.northEast());
		assertEquals(new Point(4, 4), start.northWest());
		assertEquals(new Point(6, 6), start.southEast());
		assertEquals(new Point(4, 6), start.southWest());
	}
}
