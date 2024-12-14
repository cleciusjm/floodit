package floodit.method.mh.genetics;

import floodit.Board;
import floodit.FloodItGame;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Chromosome {

	private static final Random RAND = new Random();

	private final List<Integer> gens;

	private final double fitness;

	public Chromosome(List<Integer> steps) {
		this.gens = steps;
		this.fitness = gens.isEmpty() ? 0 : 1.0 / gens.size();
	}

	public List<Integer> getGens() {
		return gens;
	}

	public double getFitness() {
		return fitness;
	}

	public Chromosome mutate(Board board) {
		FloodItGame game = new FloodItGame(board);
		Iterator<Integer> i = gens.iterator();

		while (!game.isFinished()) {
			int nextInt;
			if (i.hasNext() && RAND.nextBoolean()) {
				nextInt = i.next();
			} else {
				nextInt = RAND.nextInt(board.colorsCount());
			}
			if (nextInt != game.getCurrentColor()) {
				game.addStep(nextInt);
			}
		}

		return new Chromosome(game.getSteps());
	}

	public Chromosome crossOver(Chromosome other, Board board) {
		FloodItGame game = new FloodItGame(board);
		Iterator<Integer> i1 = gens.iterator();
		Iterator<Integer> i2 = other.gens.iterator();
		Random rand = new Random();
		while (!game.isFinished()) {
			int nextInt;
			if (i1.hasNext() && rand.nextBoolean()) {
				nextInt = i1.next();
			} else if (i2.hasNext()) {
				nextInt = i2.next();
			} else {
				nextInt = rand.nextInt(board.colorsCount());
			}
			if (nextInt != game.getCurrentColor()) {
				game.addStep(nextInt);
			}
		}
		return new Chromosome(game.getSteps());
	}

	public static Chromosome random(Board board) {
		FloodItGame game = new FloodItGame(board);

		while (!game.isFinished()) {
			int nextInt = RAND.nextInt(board.colorsCount());
			if (nextInt != game.getCurrentColor()) {
				game.addStep(nextInt);
			}
		}
		return new Chromosome(game.getSteps());
	}
}
