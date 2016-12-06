package floodit.method.random;

import java.util.Random;

import floodit.FSolution;
import floodit.FloodItGame;

public class RandomMethod {

	private int[][] board;
	private int colorsNumber;

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
