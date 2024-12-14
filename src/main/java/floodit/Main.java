package floodit;

import floodit.method.bruteforce.BruteforceMethod;
import floodit.method.interactive.InteractiveMethod;
import floodit.method.mh.aco.AcoMethod;
import floodit.method.mh.genetics.GeneticMethod;
import floodit.method.random.RandomMethod;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		var board = Board.random(4, 9);

		var methods = List.of(
            new InteractiveMethod(board),
			new RandomMethod(board),
			new AcoMethod(board, 80, 20),
			new GeneticMethod(board, 500, 10, true),
			new BruteforceMethod(board)
		);

		for (var method : methods) {
			System.out.println(method.getBestSolution());
		}

	}

}
