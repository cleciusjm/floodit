package floodit.utils;

import java.io.PrintStream;
import java.util.Random;

public abstract class Utils {
	public static int[][] arrayCopy(int[][] aSource) {
		int[][] result = new int[aSource.length][aSource[0].length];
		for (int i = 0; i < aSource.length; i++) {
			System.arraycopy(aSource[i], 0, result[i], 0, aSource[i].length);
		}
		return result;
	}

	public static int[][] randomBoard(int numColors, int size) {
		return randomBoard(numColors, size, size);
	}

	public static int[][] randomBoard(int numColors, int xSize, int ySize) {
		int[][] board = new int[xSize][ySize];
		Random random = new Random();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = random.nextInt(numColors);
			}
		}
		return board;
	}

	public static void printBoard(PrintStream out, int[][] board) {
		for (int[] ints : board) {
			for (int anInt : ints) {
				out.printf(" %d", anInt);
			}
			out.println();
		}
	}
}
