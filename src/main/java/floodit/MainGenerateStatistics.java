package floodit;

import floodit.method.bruteforce.BruteforceMethod;
import floodit.method.mh.aco.AcoMethod;
import floodit.method.mh.genetics.GeneticMethod;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class MainGenerateStatistics {

	public static void main(String[] args) {
		var board = Board.random(5, 8);
		board.print(System.out);
//		FSolution interactiveSolution = new InteractiveMethod(board).getBestSolution();
//		System.out.println(interactiveSolution);
		List<FSolution> randomSols = new ArrayList<>();
		List<FSolution> acoSols = new ArrayList<>();
		List<FSolution> genWithCrossSols = new ArrayList<>();
		List<FSolution> genWithoutCrossSols = new ArrayList<>();
//		RandomMethod randomMethod = new RandomMethod(board);
		AcoMethod acoMethod = new AcoMethod(board, 80, 20);
		GeneticMethod geneticMethodWithCross = new GeneticMethod(board, 50, 30, true);
		GeneticMethod geneticMethodWithoutCross = new GeneticMethod(board, 100, 30, false);
		for (int i = 0; i < 1; i++) {
//			randomSols.add(randomMethod.getBestSolution());
			acoSols.add(acoMethod.getBestSolution());
			genWithCrossSols.add(geneticMethodWithCross.getBestSolution());
			genWithoutCrossSols.add(geneticMethodWithoutCross.getBestSolution());
		}
		FSolution bfSolution = new BruteforceMethod(board).getBestSolution();
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
