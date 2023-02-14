package floodit.method.mh.aco;

import floodit.FSolution;
import floodit.FloodItGame;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class AcoMethod {
	private static final double RAND_CHOOSE_TAX = 0.1;
	private static final double EVAPORATION_TAX = 0.4;
	private final int[][] board;
	private final int colorsNumber;
	private final int iterationLimit;
	private final int populationSize;

	public AcoMethod(int[][] board, int colorsNumber, int iterationLimit, int populationSize) {
		this.board = board;
		this.colorsNumber = colorsNumber;
		this.iterationLimit = iterationLimit;
		this.populationSize = populationSize;
	}

	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		List<Step> pheromone = new ArrayList<>();
		Ant best = Ant.create(board, colorsNumber, pheromone);
		for (int i = 0; i < iterationLimit; i++) {
			List<Ant> ants = IntStream.range(0, populationSize).parallel()
					.mapToObj(j -> Ant.create(board, colorsNumber, pheromone))
					.sorted(Comparator.comparing(Ant::fitness).reversed()).collect(toList());
			evaporatePheromone(EVAPORATION_TAX, pheromone);
			updatePheremone(ants, pheromone);
			Ant ant = ants.get(0);
			if (ant.fitness() > best.fitness()) {
				best = ant;
			}
		}
		return new FSolution(this.getClass(), best.steps, start, System.currentTimeMillis());
	}

	private void evaporatePheromone(double evaporationTax, List<Step> pheromone) {
		pheromone.forEach(s -> s.evaporate(evaporationTax));
	}

	private void updatePheremone(List<Ant> ants, List<Step> pheromone) {
		for (Ant ant : ants) {
			for (int i = 1; i < ant.steps.size(); i++) {
				int destColor = ant.steps.get(i);
				int currentColor = ant.steps.get(i - 1);
				int currentNumber = i - 1;
				Step step = new Step(currentColor, currentNumber, destColor);
				Optional<Step> result = pheromone.stream().filter(step::equals).findAny();
				if (result.isPresent()) {
					result.get().incrementPheromone(ant.fitness());
				} else {
					step.incrementPheromone(ant.fitness());
					pheromone.add(step);
				}
			}
		}
	}

	static class Ant {

		List<Integer> steps = new LinkedList<>();

		public Ant(List<Integer> steps) {
			this.steps.addAll(steps);
		}

		public double fitness() {
			if (steps.isEmpty())
				return 0;
			return 1.0 / steps.size();
		}

		static Ant create(int[][] board, int colorsNumber, List<Step> pheromone) {
			FloodItGame game = new FloodItGame(board);
			while (!game.isFinished()) {
				StepRoulette roulette = new StepRoulette(
						pheromone.stream()
								.filter(p -> p.getCurrentColor() == game.getCurrentColor()
										&& p.getCurrentNumber() == game.getSteps().size())
								.collect(toList()),
						RAND_CHOOSE_TAX, colorsNumber);
				try {
					game.addStep(roulette.generateValue());
				} catch (IllegalArgumentException e) {
					continue;
				}
			}
			return new Ant(game.getSteps());
		}

		@Override
		public String toString() {
			return "Ant[f=" + this.fitness() + "|" + this.steps + "]";
		}
	}
}
