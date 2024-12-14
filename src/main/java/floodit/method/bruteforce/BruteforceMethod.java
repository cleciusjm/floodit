package floodit.method.bruteforce;

import floodit.Board;
import floodit.FSolution;
import floodit.FloodItGame;
import floodit.method.OptimizationMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;

public class BruteforceMethod implements OptimizationMethod {

	private final Board board;

	public BruteforceMethod(Board board) {
		this.board = board;
	}

	@Override
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
		Optional<List<Integer>> solution = IntStream.range(0, board.colorsCount()).parallel()
			.filter(i -> game.getCurrentColor() != i).mapToObj(i -> {
				FloodItGame clone = game.clone();
				clone.addStep(i);
				return clone;
			})
			.filter(c -> c.getFlooded().size() > game.getFlooded().size())
			.map(this::testSolutions)
			.min(nullsLast(comparing(List::size)));

		return solution.orElse(null);

	}
}
