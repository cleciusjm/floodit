package floodit;

import java.io.PrintStream;
import java.util.Random;

public record Board(
	int[][] board,
	int colorsCount
) {

	public void print(PrintStream out) {
		for (int[] ints : board) {
			for (int anInt : ints) {
				out.printf(" %d", anInt);
			}
			out.println();
		}
	}

	public static Board random(int numColors, int size) {
		return random(numColors, size, size);
	}

	public static Board random(int numColors, int xSize, int ySize) {
		int[][] board = new int[xSize][ySize];
		Random random = new Random();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = random.nextInt(numColors);
			}
		}
		return new Board(board, numColors);
	}


}
