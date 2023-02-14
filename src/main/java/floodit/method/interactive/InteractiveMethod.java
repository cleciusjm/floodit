package floodit.method.interactive;

import floodit.FSolution;
import floodit.FloodItGame;
import floodit.utils.Utils;

import java.util.Scanner;

public class InteractiveMethod {

	private final int[][] board;
	private final int colorsNumber;

	public InteractiveMethod(int[][] board, int colorsNumber) {
		this.board = board;
		this.colorsNumber = colorsNumber;
	}

	public FSolution getBestSolution() {
		long start = System.currentTimeMillis();
		FloodItGame game = new FloodItGame(board);
		try (Scanner reader = new Scanner(System.in)) {
			System.out.println("Iniciando jogo (para sair, pressione qualquer tecla)");
			while (!game.isFinished()) {
				Utils.printBoard(System.out, game.getBoard());
				System.out.print("Informe o passo: ");
				int nextInt = reader.nextInt();
				System.out.println();
				try {
					if (nextInt >= colorsNumber) {
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
