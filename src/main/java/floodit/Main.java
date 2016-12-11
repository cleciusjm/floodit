package floodit;

import floodit.method.bruteforce.BruteforceMethod;
import floodit.method.interactive.InteractiveMethod;
import floodit.method.mh.aco.AcoMethod;
import floodit.method.mh.genetics.GeneticMethod;
import floodit.method.random.RandomMethod;
import floodit.utils.Utils;

public class Main {
	public static void main(String[] args) {
		int numColors = 3;
		int[][] board = Utils.randomBoard(numColors, 16);
		FSolution interactiveSolution = new InteractiveMethod(board, numColors).getBestSolution();
		System.out.println(interactiveSolution);
		FSolution randomSolution = new RandomMethod(board, numColors).getBestSolution();
		System.out.println(randomSolution);
		FSolution acoSolution = new AcoMethod(board, numColors, 80, 20).getBestSolution();
		System.out.println(acoSolution);
		FSolution genSolution = new GeneticMethod(board, numColors, 2000, 30,false).getBestSolution();
		System.out.println(genSolution);
		FSolution bfSolution = new BruteforceMethod(board, numColors).getBestSolution();
		System.out.println(bfSolution);

	}

}
