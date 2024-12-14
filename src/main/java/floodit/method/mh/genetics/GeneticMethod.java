package floodit.method.mh.genetics;

import floodit.Board;
import floodit.FSolution;
import floodit.method.OptimizationMethod;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class GeneticMethod implements OptimizationMethod {
	private static final double INAPT_SURVIVE_RATE = 0.1;
	private static final double MUTATION_RATE = 0.2;

	private final Board board;
	private final int iterations;
	private final int populationSize;
	private final boolean crossover;
	private final Random rand = new Random();

	public GeneticMethod(Board board, int iterations, int population, boolean crossover) {
		this.board = board;
		this.iterations = iterations;
		this.populationSize = population;
		this.crossover = crossover;
	}

	@Override
	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		Chromosome best = Chromosome.random(board);
		List<Chromosome> population = IntStream.range(0, populationSize)
			.parallel()
			.mapToObj(i -> Chromosome.random(board))
			.collect(toList());
		for (int i = 0; i < iterations; i++) {
			Optional<Chromosome> lbest = population.stream().max(comparing(Chromosome::getFitness));

			if (lbest.isPresent() && best.getFitness() < lbest.get().getFitness()) {
				best = lbest.get();
			}

			double avg = population.stream().mapToDouble(Chromosome::getFitness).average().orElse(0.0);

			for (int j = 0; j < population.size(); j++) {
				var chromosome = population.get(j);
				if (chromosome.getFitness() < avg && !surviveChance(chromosome)) {
					if (crossover && rand.nextBoolean()) {
						var p1 = randomChoose(population, avg);
						var p2 = randomChoose(population, avg);
						population.set(j, p1.crossOver(p2, board));
					} else {
						population.set(j, Chromosome.random(board));
					}
				}
			}

			for (int j = 0; j < (MUTATION_RATE * population.size()); j++) {
				Chromosome c = randomChoose(population);
				population.set(population.indexOf(c), c.mutate(board));
			}
		}
		return new FSolution(this.getClass(), best.getGens(), start, System.currentTimeMillis());
	}

	private Chromosome randomChoose(List<Chromosome> population, double avg) {
		Chromosome choosed;
		do {
			choosed = randomChoose(population);
		} while (choosed.getFitness() < avg);
		return choosed;
	}

	private Chromosome randomChoose(List<Chromosome> population) {
		return population.get((int) (Math.random() * population.size()));
	}

	private boolean surviveChance(Chromosome c) {
		return Math.random() < INAPT_SURVIVE_RATE;
	}


}
