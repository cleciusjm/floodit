package floodit.method.mh.aco;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class StepRoulette {

	private static final int LIMIT = 100;
	private final Random rand = new Random();
	private final List<Step> currentPossibilities;
	private final double randChooseTax;
	private final int colorsNumber;
	private final double totalPheromone;

	public StepRoulette(List<Step> currentPossibilities, double randChooseTax, int colorsNumber) {
		this.currentPossibilities = currentPossibilities.stream().sorted(Comparator.comparing(Step::getPheromone))
			.toList();
		this.totalPheromone = currentPossibilities.stream().mapToDouble(Step::getPheromone).sum();
		this.randChooseTax = randChooseTax;
		this.colorsNumber = colorsNumber;

	}

	public int generateValue() {
		int choosed = rand.nextInt(LIMIT);
		if ((randChooseTax * LIMIT) >= choosed) {
			double[] relativePheromone = getRelativePheromone();
			double value = rand.nextDouble();
			double acc = 0.0;
			for (int i = 0; i < relativePheromone.length; i++) {
				relativePheromone[i] += acc;
				if (relativePheromone[i] > value) {
					return currentPossibilities.get(i).getDestColor();
				}
			}
		}
		return rand.nextInt(colorsNumber);
	}

	private double[] getRelativePheromone() {
		double[] relativePheremone = new double[currentPossibilities.size()];
		for (int i = 0; i < relativePheremone.length; i++) {
			relativePheremone[i] = currentPossibilities.get(i).getPheromone() / totalPheromone;
		}
		return relativePheremone;
	}

}
