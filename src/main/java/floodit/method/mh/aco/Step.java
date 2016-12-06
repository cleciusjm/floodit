package floodit.method.mh.aco;

class Step {
	private final int currentNumber;
	private final int currentColor;
	private double pheromone = 0.0;
	private final int destColor;

	public Step(int currentColor, int currentNumber, int destColor) {
		this.currentColor = currentColor;
		this.currentNumber = currentNumber;
		this.destColor = destColor;
	}

	public double getPheromone() {
		return pheromone;
	}

	public int getCurrentColor() {
		return currentColor;
	}

	public int getCurrentNumber() {
		return currentNumber;
	}

	public int getDestColor() {
		return destColor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Step) {
			Step o = (Step) obj;
			return this.currentColor == o.currentColor && this.currentNumber == o.currentNumber
					&& this.destColor == o.destColor;
		}
		return false;
	}

	public void incrementPheromone(double fitness) {
		this.pheromone += fitness;
	}

	public void evaporate(double tax) {
		if (tax > 1)
			throw new IllegalArgumentException();
		this.pheromone *= (1 - tax);
	}
}