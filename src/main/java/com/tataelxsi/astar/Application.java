package com.tataelxsi.astar;
/**
 * 
 */

/**
 * @author amaresh
 * 15-Jun-2017
 */

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Application {

	private Utility util = null;
	Coordinates srcCoord = null; // source coordinate
	private Coordinates destCoord = null; // dest coordinate
	private int row; // num of rows for each path
	private int column; // num of col for each path

	public static void main(String[] args) {

		System.out.println("Please enter filepath like /Users/amaresh/Desktop/demo/small_2.txt");
		Scanner sc = new Scanner(System.in);
		String filename = sc.nextLine();
		Application app = new Application();
		app.readAndCalculateScore(filename); // read input file processor
		app.drawPathToDestination(); // output that is draw best path from
										// source to
										// destination

	}

	// read and calculate cost from source to dest

	public void readAndCalculateScore(String filename) {

		List<String> records = InputFileReader.readLargeMapFile(filename);
		row = records.size();
		column = records.get(0).length();
		char start = '@'; // Acc to req @ will be the starting point
		char target = 'X'; // Acc. to req. X will be destination

		Map<String, Coordinates> srcMap = InputFileReader.getLocationPoint(records, start, "start");
		Map<String, Coordinates> destMap = InputFileReader.getLocationPoint(records, target, "goal");
		util = new Utility(row, column);
		try {
			InputFileReader.convertGridValueToWeight(records, util);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (aStarSearch(util.grid, srcMap, destMap) == true) {
			AStarSearchImpl aStarSearch = new AStarSearchImpl();
			aStarSearch.AStar(util.grid, srcMap, destMap, row, column);
		}
	}

	// It will draw best path from source to destination
	public void drawPathToDestination() {
		Stack<AStarSearchImpl.Cell> stack = AStarSearchImpl.stackToPush;
		int stackSize = stack.size();
		for (int k = 0; k < stackSize; k++) {
			InputFileReader.tempMatrix[stack.peek().Xcoordinate][stack.peek().Ycoordinate] = '#';
			stack.pop();
		}
		System.out.println();
		System.out.println("Final matrix after best path");
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				System.out.print(InputFileReader.tempMatrix[i][j]);
			}

			System.out.println();
		}

	}

	// check source and dest coordinates
	private boolean aStarSearch(int grid[][], Map<String, Coordinates> srcMap, Map<String, Coordinates> destMap) {

		srcCoord = srcMap.get("start");
		if (srcCoord == null) {
			System.out.println("Source/Start not found\n");
			return false;
		}
		if (util.isValid(srcCoord.getX(), srcCoord.getY()) == false) {
			System.out.println("Source/Start is invalid\n");
			return false;
		}
		destCoord = destMap.get("goal");
		if (destCoord == null) {
			System.out.println("Goal/Destinataion not found\n");
			return false;
		}
		if (util.isValid(destCoord.getX(), destCoord.getY()) == false) {
			System.out.println("Goal/Destinataion is invalid\n");
			return false;
		}

		if (util.isUnBlocked(grid, srcCoord.getX(), srcCoord.getY()) == false
				|| util.isUnBlocked(grid, destCoord.getX(), destCoord.getY()) == false) {
			System.out.println("Start/Source or the Goal/destination is blocked\n");
			return false;
		}
		if (util.isDestination(srcMap, destMap) == true) {
			System.out.println("We are already at the Goal/Destination\n");
			return false;
		}
		return true;
	}

}
