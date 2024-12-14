package floodit.method.interactive;

import floodit.Board;
import floodit.FSolution;
import floodit.FloodItGame;
import floodit.method.OptimizationMethod;

import java.util.Scanner;

public class InteractiveMethod implements OptimizationMethod {

	private final Board board;

	public InteractiveMethod(Board board) {
		this.board = board;
	}

	@Override
	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		FloodItGame game = new FloodItGame(board);
		try (Scanner reader = new Scanner(System.in)) {
			System.out.println("Iniciando jogo (para sair, pressione qualquer tecla)");
			while (!game.isFinished()) {
				board.print(System.out);
				System.out.print("Informe o passo: ");
				int nextInt = reader.nextInt();
				System.out.println();
				try {
					if (nextInt >= board.colorsCount()) {
						throw new IllegalArgumentException("Passo inválido");
					}
					game.addStep(nextInt);
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		System.out.printf("\nFinalizado em %d passos [%s]", game.getSteps().size(), game.getSteps().toString());
		return new FSolution(this.getClass(), game.getSteps(), start, System.currentTimeMillis());
	}
}
