package com.tataelxsi.astar;
/**
 * 
 */

/**
 * @author amaresh
 * 15-Jun-2017
 */

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStarSearchImpl {

	static class Cell {
		int heuristicCost = 0;
		int finalCost = 0;
		int Xcoordinate, Ycoordinate;

		Cell parent;

		Cell(int i, int j) {
			this.Xcoordinate = i;
			this.Ycoordinate = j;
		}

	}

	// Blocked cells are just null Cell values in grid
	public static Cell[][] grids;

	private static PriorityQueue<Cell> open;
	private static boolean closed[][] = new boolean[InputFileReader.lineCount][InputFileReader.lineCount];
	static Stack<Cell> stackToPush = new Stack<>();
	private static Coordinates srcCoord = null;
	private static Coordinates destCoord = null;
	private static Cell srcCell = null;
	private static Cell destCell = null;

	private static void setBlocked(int i, int j) {
		grids[i][j] = null;
	}

	static void AStar(int grid[][], Map<String, Coordinates> srcMap, Map<String, Coordinates> destMap, Integer ROW,
			Integer COL) {
		srcCoord = srcMap.get("start");
		destCoord = destMap.get("goal");
		PriorityQueueSort priorityQueueSort = new PriorityQueueSort();
		open = new PriorityQueue<Cell>(priorityQueueSort);
		grids = new Cell[ROW][COL];

		for (int i = 0; i < ROW; ++i) {
			for (int j = 0; j < COL; ++j) {
				grids[i][j] = new Cell(i, j);
				grids[i][j].heuristicCost = Math.abs(i - destCoord.getX()) + Math.abs(j - destCoord.getY());

			}

		}
		grids[srcCoord.getX()][srcCoord.getY()].finalCost = 0;
		for (int i = 0; i < ROW; ++i) {
			for (int j = 0; j < COL; ++j) {
				if (grid[i][j] == 0) {
					setBlocked(i, j);
				}
			}
		}

		srcCell = new Cell(srcCoord.getX(), srcCoord.getY());
		destCell = new Cell(destCoord.getX(), destCoord.getY());

		tracePathAndCost(grid);
		tracePathSourceToGoal();
	}

	private static void tracePathSourceToGoal() {
		if (closed[destCoord.getX()][destCoord.getY()]) {
			Cell current_ = grids[destCoord.getX()][destCoord.getY()];
			stackToPush.push(current_);
			while (current_.parent != null) {
				current_ = current_.parent;
				stackToPush.push(current_);

			}

		} else
			System.out.println("No possible path");
	}

	private static void tracePathAndCost(int grid[][]) {
		open.add(srcCell);
		Cell current;

		while (true) {
			current = open.poll();
			if (current == null)
				break;
			closed[current.Xcoordinate][current.Ycoordinate] = true;
			if (current.equals(destCell)) {
				return;
			}
			Cell t;

			if (current.Xcoordinate - 1 >= 0) {
				t = grids[current.Xcoordinate - 1][current.Ycoordinate];
				checkAndUpdateCost(current, t, current.finalCost + grid[current.Xcoordinate - 1][current.Ycoordinate]);

				if (current.Ycoordinate - 1 >= 0) {
					t = grids[current.Xcoordinate - 1][current.Ycoordinate - 1];
					checkAndUpdateCost(current, t,
							current.finalCost + grid[current.Xcoordinate - 1][current.Ycoordinate - 1]);
				}

				if (current.Ycoordinate + 1 < grid[0].length) {
					t = grids[current.Xcoordinate - 1][current.Ycoordinate + 1];
					checkAndUpdateCost(current, t,
							current.finalCost + grid[current.Xcoordinate - 1][current.Ycoordinate + 1]);
				}
			}

			if (current.Ycoordinate - 1 >= 0) {
				t = grids[current.Xcoordinate][current.Ycoordinate - 1];
				checkAndUpdateCost(current, t,
						current.finalCost + grid[current.Xcoordinate][(current.Ycoordinate - 1)]);
			}
			if ((current.Ycoordinate + 1) < grid[0].length) {
				t = grids[current.Xcoordinate][current.Ycoordinate + 1];
				checkAndUpdateCost(current, t,
						current.finalCost + grid[current.Xcoordinate][(current.Ycoordinate + 1)]);
			}

			if (current.Xcoordinate + 1 < grid.length) {
				t = grids[current.Xcoordinate + 1][current.Ycoordinate];
				checkAndUpdateCost(current, t, current.finalCost + grid[current.Xcoordinate + 1][current.Ycoordinate]);
				if (current.Ycoordinate - 1 >= 0) {
					t = grids[current.Xcoordinate + 1][current.Ycoordinate - 1];
					checkAndUpdateCost(current, t,
							current.finalCost + grid[current.Xcoordinate + 1][current.Ycoordinate - 1]);
				}

				if (current.Ycoordinate + 1 < grid[0].length) {
					t = grids[current.Xcoordinate + 1][current.Ycoordinate + 1];
					checkAndUpdateCost(current, t,
							current.finalCost + grid[current.Xcoordinate + 1][current.Ycoordinate + 1]);
				}
			}

		}

	}

	static void checkAndUpdateCost(Cell current, Cell t, int cost) {
		if (t == null || closed[t.Xcoordinate][t.Ycoordinate])
			return;
		int t_final_cost = t.heuristicCost + cost;
		boolean inOpen = open.contains(t);
		if (!inOpen || t_final_cost < t.finalCost) {
			t.finalCost = t_final_cost;
			t.parent = current;
			if (!inOpen)
				open.add(t);
		}
	}

	public static class PriorityQueueSort implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			Cell c1 = (Cell) o1;
			Cell c2 = (Cell) o2;
			return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
		}
	}

}
