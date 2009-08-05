package com.yardspoon.astar;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yardspoon.astar.AStarPathFinder;
import com.yardspoon.astar.IPathFinder;
import com.yardspoon.astar.Point;

public class AStarPathFinderTest {
	private IPathFinder noDiagFinder;
	private IPathFinder diagFinder;
	
	@Before
	public void setup() {
		noDiagFinder = new AStarPathFinder(new StubAStarContext());
		diagFinder = new AStarPathFinder(new StubAStarContext(true));
	}

	@Test
	public void endPointsAreEqualResultsInSinglePointPath() {
		List<Point> path = noDiagFinder.findPath(new Point(), new Point());
		
		assertEquals(1, path.size());
		assertEquals(new Point(), path.get(0));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void startPointIsNegativeThrowsException() {
		new AStarPathFinder(null).findPath(new Point(-1,-2), new Point());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void endPointIsNegativeThrowsException() {
		noDiagFinder.findPath(new Point(), new Point(-5,-1));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void startPointIsBeyondTheBoundsThrowsException() {
		noDiagFinder.findPath(new Point(20,50), new Point());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void endPointIsBeyondTheBoundsThrowsException() {
		noDiagFinder.findPath(new Point(), new Point(10,11));
	}
	
	@Test
	public void adjacentEndPointsReturnBothPointsAsPath() {
		assertEndPointsProducePath(noDiagFinder, new Point(1,1), new Point(1,0), new Point(1,1), new Point(1,0));
		assertEndPointsProducePath(noDiagFinder, new Point(1,1), new Point(0,1), new Point(1,1), new Point(0,1));
		assertEndPointsProducePath(noDiagFinder, new Point(1,1), new Point(2,1), new Point(1,1), new Point(2,1));
		assertEndPointsProducePath(noDiagFinder, new Point(1,1), new Point(1,2), new Point(1,1), new Point(1,2));
	}
	
	@Test
	public void contextWithoutDiagonalsAllowedProducesLongerPaths() {
		assertPathLength(noDiagFinder, new Point(1,1), new Point(2,0), 3);
		assertPathLength(noDiagFinder, new Point(1,1), new Point(2,2), 3);
		assertPathLength(noDiagFinder, new Point(1,1), new Point(0,0), 3);
		assertPathLength(noDiagFinder, new Point(1,1), new Point(0,2), 3);
	}
	
	@Test
	public void diagonalAdjacentProducesTwoPointPathIfAllowed() {
		assertEndPointsProducePath(diagFinder, new Point(1,1), new Point(2,0), new Point(1,1), new Point(2,0));
		assertEndPointsProducePath(diagFinder, new Point(1,1), new Point(2,2), new Point(1,1), new Point(2,2));
		assertEndPointsProducePath(diagFinder, new Point(1,1), new Point(0,0), new Point(1,1), new Point(0,0));
		assertEndPointsProducePath(diagFinder, new Point(1,1), new Point(0,2), new Point(1,1), new Point(0,2));
	}
	
	@Test
	public void ifUnableToFindPathReturnsEmptyPath() {
		List<Point> path = noDiagFinder.findPath(new Point(), new Point(9, 9));
		
		assertEquals(0, path.size());
	}
	
	@Test
	public void diagonalDirectPathCanBeFound() {
		List<Point> path = diagFinder.findPath(new Point(), new Point(9, 9));
		
		assertEquals(10, path.size());
		
		for(int i = 0; i < 10; i++) {
			assertEquals(new Point(i, i), path.get(i));
		}
	}
	
	@Test
	public void goesInStraightLines() {
		assertEndPointsProducePath(noDiagFinder, new Point(6,1), new Point(6,4), new Point(6,1), new Point(6,2), new Point(6,3), new Point(6,4));
		assertEndPointsProducePath(noDiagFinder, new Point(8,2), new Point(6,2), new Point(8,2), new Point(7,2), new Point(6,2));
	}
	
	@Test
	public void goesAroundCorners() {
		assertEndPointsProducePath(noDiagFinder, new Point(6,1), new Point(8,1), new Point(6,1), new Point(6,2), new Point(7,2), new Point(8,2), new Point(8,1));
		assertEndPointsProducePath(diagFinder, new Point(6,1), new Point(8,1), new Point(6,1), new Point(7,2), new Point(8,1));
	}
	
	@Test
	public void findsShortestPath() {
		assertPathLength(noDiagFinder, new Point(4,4), new Point(6,4), 11);
		assertPathLength(diagFinder, new Point(4,4), new Point(6,4), 3);
	}
	
	@Test
	public void algorithmTriesLowerEstimatesFirst() {
		IPathFinder finderWithHighEstimate_2_8 = new AStarPathFinder(new StubAStarContext(new Point(2,8)));
		assertEndPointsProducePath(finderWithHighEstimate_2_8, new Point(2,9), new Point(3,8), new Point(2,9), new Point(3,9), new Point(3,8));
		
		IPathFinder finderWithHighEstimate_3_9 = new AStarPathFinder(new StubAStarContext(new Point(3,9)));
		assertEndPointsProducePath(finderWithHighEstimate_3_9, new Point(2,9), new Point(3,8), new Point(2,9), new Point(2,8), new Point(3,8));
	}
	
	@Test
	public void algorithmUsesContextMethodToComputeActualDistanceFactors() {
		IPathFinder finderWithCostlySpot = new AStarPathFinder(new StubAStarContext() {
			private final Point costlyPoint = new Point(1,4);

			@Override
			public double g_factor(Point point) {
				if(costlyPoint.equals(point)) {
					return 10.0;
				}
				
				return 1.0;
			}
			
		});
		
		assertPathLength(noDiagFinder, new Point(4,4), new Point(4,6), 11);
		assertPathLength(finderWithCostlySpot, new Point(4,4), new Point(4,6), 13);
	}
	
	private static void assertPathLength(IPathFinder finder, Point start, Point end, int length) {
		List<Point> path = finder.findPath(start, end);
		assertEquals(length, path.size());
	}

	private static void assertEndPointsProducePath(IPathFinder finder, Point start, Point end, Point ... pathPoints) {
		List<Point> path = finder.findPath(start, end);
		
		assertEquals(pathPoints.length, path.size());
		
		for (int i = 0; i < pathPoints.length; i++) {
			assertEquals(pathPoints[i], path.get(i));
		}
	}
}
