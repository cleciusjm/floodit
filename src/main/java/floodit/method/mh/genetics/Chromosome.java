package floodit.method.mh.genetics;

import java.util.List;

public class Chromosome {

	private List<Integer> gens;

	public Chromosome(List<Integer> steps) {
		this.gens = steps;
	}

	public List<Integer> getGens() {
		return gens;
	}

	public double fitness() {
		if (gens.isEmpty())
			return 0;
		return 1.0 / gens.size();
	}
}
