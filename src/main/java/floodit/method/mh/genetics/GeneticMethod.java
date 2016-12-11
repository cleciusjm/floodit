package floodit.method.mh.genetics;

import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import floodit.FSolution;
import floodit.FloodItGame;

public class GeneticMethod {
	private static final double INAPT_SURVIVE_RATE = 0.2;
	private static final double MUTATION_RATE = 0.3;

	private int[][] board;
	private int colorsNumber;
	private int iterations;
	private int populationSize;
	private boolean crossover;

	public GeneticMethod(int[][] board, int colorsNumber, int iterations, int population, boolean crossover) {
		this.board = board;
		this.colorsNumber = colorsNumber;
		this.iterations = iterations;
		this.populationSize = population;
		this.crossover = crossover;
	}

	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		Chromosome best = randomChromosome();
		List<Chromosome> population = IntStream.range(0, populationSize).mapToObj(i -> randomChromosome())
				.collect(toList());
		for (int i = 0; i < iterations; i++) {
			Optional<Chromosome> lbest = population.stream()
					.sorted(Comparator.comparing(Chromosome::fitness).reversed()).findFirst();
			if (lbest.isPresent() && best.fitness() < lbest.get().fitness()) {
				best = lbest.get();
			}
			double avg = population.stream().mapToDouble(Chromosome::fitness).average().orElse(0.0);
			population.removeIf(c -> c.fitness() < avg && !surviveChance(c));
			for (int j = 0; j < (populationSize - population.size()); j++) {
				if (crossover) {
					population.add(crossOver(randomChoose(population), randomChoose(population)));
				} else {
					population.add(randomChromosome());
				}
			}
			for (int j = 0; j < (MUTATION_RATE * population.size()); j++) {
				Chromosome c = randomChoose(population);
				population.removeIf(c::equals);
				mutate(c);
			}
		}
		return new FSolution(this.getClass(), best.getGens(), start, System.currentTimeMillis());
	}

	private void mutate(Chromosome c) {
		FloodItGame game = new FloodItGame(board);
		Iterator<Integer> i = c.getGens().iterator();
		Random rand = new Random();
		while (!game.isFinished()) {
			int nextInt = 0;
			if (i.hasNext() && rand.nextBoolean()) {
				nextInt = i.next();
			} else {
				nextInt = rand.nextInt(colorsNumber);
			}
			if (nextInt != game.getCurrentColor()) {
				game.addStep(nextInt);
			}
		}
		c.getGens().clear();
		c.getGens().addAll(game.getSteps());
	}

	private Chromosome crossOver(Chromosome c1, Chromosome c2) {
		FloodItGame game = new FloodItGame(board);
		Iterator<Integer> i1 = c1.getGens().iterator();
		Iterator<Integer> i2 = c2.getGens().iterator();
		Random rand = new Random();
		while (!game.isFinished()) {
			int nextInt = 0;
			if (i1.hasNext() && rand.nextBoolean()) {
				nextInt = i1.next();
			} else if (i2.hasNext()) {
				nextInt = i2.next();
			} else {
				nextInt = rand.nextInt(colorsNumber);
			}
			if (nextInt != game.getCurrentColor()) {
				game.addStep(nextInt);
			}
		}
		return new Chromosome(game.getSteps());
	}

	private Chromosome randomChoose(List<Chromosome> population) {
		return population.get((int) Math.random() * population.size());
	}

	private boolean surviveChance(Chromosome c) {
		return Math.random() < INAPT_SURVIVE_RATE;
	}

	private Chromosome randomChromosome() {
		FloodItGame game = new FloodItGame(board);
		Random rand = new Random();
		while (!game.isFinished()) {
			int nextInt = rand.nextInt(colorsNumber);
			if (nextInt != game.getCurrentColor()) {
				game.addStep(nextInt);
			}
		}
		return new Chromosome(game.getSteps());
	}
}
