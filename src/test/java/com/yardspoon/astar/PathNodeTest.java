package com.yardspoon.astar;

import org.junit.Test;

import com.yardspoon.astar.PathNode;

import static org.junit.Assert.*;

public class PathNodeTest {
	
	@Test
	public void fIsComputedCorrectly() {
		assertEquals(4.0, new PathNode(null, null, 2, 2).f());
		assertEquals(1.0, new PathNode(null, null, 0, 1).f());
		assertEquals(10.0, new PathNode(null, null, 7, 3).f());
	}

	@Test
	public void compareToWorksCorrectly() {
		PathNode base = new PathNode(null, null, 5, 5);
		
		assertEquals(-500, base.compareTo(new PathNode(null, null, 10, 5)));
		assertEquals(300, base.compareTo(new PathNode(null, null, 5, 2)));
		assertEquals(0, base.compareTo(new PathNode(null, null, 5, 5)));
	}
}
