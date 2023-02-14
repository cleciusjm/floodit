package floodit;

import floodit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FloodItGame {

	private final List<Point> flooded = new ArrayList<>();
	private final List<Integer> steps = new ArrayList<>();
	private final int[][] currBoard;

	private FloodItGame(FloodItGame game) {
		this.flooded.addAll(game.flooded);
		this.steps.addAll(game.steps);
		this.currBoard = Utils.arrayCopy(game.currBoard);
	}

	public FloodItGame(int[][] board) {
		this.currBoard = Utils.arrayCopy(board);
		Point p0 = new Point(0, 0);
		this.flooded.add(p0);
	}

	public List<Point> getFlooded() {
		return flooded;
	}

	public int[][] getBoard() {
		return currBoard;
	}

	public List<Integer> getSteps() {
		return steps;
	}

	public boolean isFinished() {
		return this.flooded.size() == currBoard.length * currBoard[0].length;
	}

	public int[][] addStep(int color) {
		int lastColor = getCurrentColor();
		if (lastColor == color) {
			throw new IllegalArgumentException("Passo repetido");
		}
		steps.add(color);
		List<Point> newPoints = new ArrayList<>();
		for (Point p : flooded) {
			currBoard[p.x][p.y] = color;
			scanNeighborhood(color, newPoints, p);
		}
		this.flooded.addAll(newPoints);
		return currBoard;
	}

	public int getCurrentColor() {
		return currBoard[0][0];
	}

	private void scanNeighborhood(int color, List<Point> newPoints, Point p) {
		if (p.x > 0) {
			// olha para cima
			Point up = new Point(p.x - 1, p.y);
			if (canFlood(up, color, newPoints)) {
				currBoard[up.x][up.y] = color;
				newPoints.add(up);
				this.scanNeighborhood(color, newPoints, up);
			}
		}
		if (p.y > 0) {
			// olha para esquerda
			Point left = new Point(p.x, p.y - 1);
			if (canFlood(left, color, newPoints)) {
				currBoard[left.x][left.y] = color;
				newPoints.add(left);
				this.scanNeighborhood(color, newPoints, left);
			}
		}
		if (p.x < currBoard.length - 1) {
			// olha para cima
			Point down = new Point(p.x + 1, p.y);
			if (canFlood(down, color, newPoints)) {
				currBoard[down.x][down.y] = color;
				newPoints.add(down);
				this.scanNeighborhood(color, newPoints, down);
			}
		}
		if (p.y < currBoard[0].length - 1) {
			// olha para direita
			Point right = new Point(p.x, p.y + 1);
			if (canFlood(right, color, newPoints)) {
				currBoard[right.x][right.y] = color;
				newPoints.add(right);
				this.scanNeighborhood(color, newPoints, right);
			}
		}
	}

	private boolean canFlood(Point point, int color, List<Point> newPoints) {
		return currBoard[point.x][point.y] == color && !flooded.contains(point) && !newPoints.contains(point);
	}

	@Override
	public FloodItGame clone() {
		return new FloodItGame(this);
	}

	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point o = (Point) obj;
				return this.x == o.x && this.y == o.y;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return 31 * x + y;
		}
	}
}
