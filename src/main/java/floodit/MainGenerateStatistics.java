package floodit;

import floodit.method.bruteforce.BruteforceMethod;
import floodit.method.mh.aco.AcoMethod;
import floodit.method.mh.genetics.GeneticMethod;
import floodit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class MainGenerateStatistics {

	public static void main(String[] args) {
		int numColors = 5;
		int[][] board = Utils.randomBoard(numColors, 8);
		Utils.printBoard(System.out, board);
//		FSolution interactiveSolution = new InteractiveMethod(board, numColors).getBestSolution();
//		System.out.println(interactiveSolution);
		List<FSolution> randomSols = new ArrayList<>();
		List<FSolution> acoSols = new ArrayList<>();
		List<FSolution> genWithCrossSols = new ArrayList<>();
		List<FSolution> genWithoutCrossSols = new ArrayList<>();
//		RandomMethod randomMethod = new RandomMethod(board, numColors);
		AcoMethod acoMethod = new AcoMethod(board, numColors, 80, 20);
		GeneticMethod geneticMethodWithCross = new GeneticMethod(board, numColors, 50, 30, true);
		GeneticMethod geneticMethodWithoutCross = new GeneticMethod(board, numColors, 100, 30, false);
		for (int i = 0; i < 1; i++) {
//			randomSols.add(randomMethod.getBestSolution());
			acoSols.add(acoMethod.getBestSolution());
			genWithCrossSols.add(geneticMethodWithCross.getBestSolution());
			genWithoutCrossSols.add(geneticMethodWithoutCross.getBestSolution());
		}
		FSolution bfSolution = new BruteforceMethod(board, numColors).getBestSolution();
		System.out.println(best(randomSols));
		System.out.println(best(acoSols));
		System.out.println("\nWCross:" + best(genWithCrossSols));
		System.out.println("\nWOCross:" + best(genWithoutCrossSols));
		System.out.println(bfSolution);
	}

	private static FSolution best(List<FSolution> sols) {
		return sols.stream().max(comparing(FSolution::size)).orElse(null);
	}
}
