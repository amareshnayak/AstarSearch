package com.tataelxsi.astar;
/**
 * 
 */

import java.io.File;

/**
 * @author amaresh
 * 15-Jun-2017
 */

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Application {

	private static Utility util = null;
	static Coordinates srcCoord = null;
	private static Coordinates destCoord = null;
	private static InputFileReader readFile;
	private static int row;     //num of rows for each path
	private static int column;   //num of col for each path
    
	public static void main(String[] args) {
		
		String filename= "src//main//resources//config//small.txt";
		readFile= new InputFileReader();
		List<String> records=readFile.readLargeMapFile(filename);
		
		readAndCalculateScore(records); //read input file
		drawPathToDestination();   //output that is draw best path from source to dest

	}

	private static void readAndCalculateScore(List<String> records) {
		row = records.size();
		column = records.get(0).length();
		char start = '@';
		char target = 'X';

		Map<String, Coordinates> srcMap = readFile.getLocationPoint(records, start, "start");
		Map<String, Coordinates> destMap = readFile.getLocationPoint(records, target, "goal");
		util = new Utility(row, column);
		readFile.convertGridValueToWeight(records, util);
		if (aStarSearch(util.grid, srcMap, destMap) == true) {
			AStarSearchImpl.AStar(util.grid, srcMap, destMap, row, column);
		}
	}

	static void drawPathToDestination() {
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
