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
import java.util.Stack;

public class Application {

	private static Utility util = null;
	static Coordinates srcCoord = null; // source coordinate
	private static Coordinates destCoord = null; // dest coordinate
	private static InputFileReader readFile;
	private static int row; // num of rows for each path
	private static int column; // num of col for each path

	public static void main(String[] args) {

		String filename = "src//main//resources//config//large.txt";

		readAndCalculateScore(filename); // read input file processor
		drawPathToDestination(); // output that is draw best path from source to
									// destination

	}

	// read and calculate cost from source to dest

	public static void readAndCalculateScore(String filename) {

		readFile = new InputFileReader();
		List<String> records = InputFileReader.readLargeMapFile(filename);
		row = records.size();
		column = records.get(0).length();
		char start = '@'; // Acc to req @ will be the starting point
		char target = 'X'; // Acc. to req. X will be destination

		Map<String, Coordinates> srcMap = readFile.getLocationPoint(records, start, "start");
		Map<String, Coordinates> destMap = readFile.getLocationPoint(records, target, "goal");
		util = new Utility(row, column);
		try{
		readFile.convertGridValueToWeight(records, util);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if (aStarSearch(util.grid, srcMap, destMap) == true) {
			AStarSearchImpl.AStar(util.grid, srcMap, destMap, row, column);
		}
	}

	// It will draw best path from source to destination
	public static void drawPathToDestination() {
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
	private static boolean aStarSearch(int grid[][], Map<String, Coordinates> srcMap,
			Map<String, Coordinates> destMap) {

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
