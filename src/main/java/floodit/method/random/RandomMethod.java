package floodit.method.random;

import floodit.FSolution;
import floodit.FloodItGame;

import java.util.Random;

public class RandomMethod {

	private final int[][] board;
	private final int colorsNumber;

	public RandomMethod(int[][] board, int colorsNumber) {
		this.board = board;
		this.colorsNumber = colorsNumber;
	}

	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		FloodItGame game = new FloodItGame(board);
		Random rand = new Random();
		while (!game.isFinished()) {
			int nextInt = rand.nextInt(colorsNumber);
			try {
				game.addStep(nextInt);
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		return new FSolution(this.getClass(), game.getSteps(), start, System.currentTimeMillis());
	}
}
