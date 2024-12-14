package floodit.method.random;

import floodit.Board;
import floodit.FSolution;
import floodit.FloodItGame;
import floodit.method.OptimizationMethod;

import java.util.Random;

public class RandomMethod implements OptimizationMethod {

	private final Board board;

	public RandomMethod(Board board) {
		this.board = board;
	}

	@Override
	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		FloodItGame game = new FloodItGame(board);
		Random rand = new Random();
		while (!game.isFinished()) {
			int nextInt = rand.nextInt(board.colorsCount());
			try {
				game.addStep(nextInt);
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		return new FSolution(this.getClass(), game.getSteps(), start, System.currentTimeMillis());
	}
}
