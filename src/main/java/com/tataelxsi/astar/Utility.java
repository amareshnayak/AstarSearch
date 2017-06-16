package com.tataelxsi.astar;
/**
 * 
 */

/**
 * @author amaresh
 * 15-Jun-2017
 */
import java.util.Map;
//utility class for matrix design
public class Utility {
	int ROW;
	int COL;
	int grid[][];
	

	Utility(int ROW, int COL) {
		this.ROW = ROW;
		this.COL = COL;
		grid = new int[ROW][COL];

	}

	public boolean isValid(int row, int col) {
		// Returns true if row number and column number is in range
		return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL);
	}

	public boolean isUnBlocked(int grid[][], int row, int col) {
		// Returns true if the cell is not blocked else false
		if (grid[row][col] == 1)
			return (true);
		else
			return (false);
	}

	public boolean isDestination(Map<String, Coordinates> srcMap, Map<String, Coordinates> destMap) {
		Coordinates destCoord = destMap.get("goal");
		Coordinates srcCoord = srcMap.get("start");
		if (srcCoord.getX() == destCoord.getX() && srcCoord.getY() == destCoord.getY())
			return (true);
		else
			return (false);
	}

}
