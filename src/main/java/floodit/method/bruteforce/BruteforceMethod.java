package floodit.method.bruteforce;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import floodit.FSolution;
import floodit.FloodItGame;

public class BruteforceMethod {

	private int[][] board;
	private int colorsNumber;

	public BruteforceMethod(int[][] board, int colorsNumber) {
		this.board = board;
		this.colorsNumber = colorsNumber;
	}

	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		FloodItGame game = new FloodItGame(board);
		List<Integer> solutions = testSolutions(game.clone());
		return new FSolution(this.getClass(), solutions, start, System.currentTimeMillis());
	}

	private List<Integer> testSolutions(FloodItGame game) {
		if (game.isFinished()) {
			return game.getSteps();
		}
		Optional<List<Integer>> solution = IntStream.range(0, colorsNumber).parallel()
				.filter(i -> game.getCurrentColor() != i).mapToObj(i -> {
					FloodItGame clone = game.clone();
					clone.addStep(i);
					return clone;
				}).filter(c -> c.getFlooded().size() > game.getFlooded().size()).map(this::testSolutions)
				.min(Comparator.nullsLast(Comparator.comparing(List::size)));
		return solution.isPresent() ? solution.get() : null;

	}
}
