package floodit;

import java.util.Collections;
import java.util.List;

public class FSolution {

	private final Class<?> method;
	private final List<Integer> steps;
	private final long start;
	private final long end;

	public FSolution(Class<?> method, List<Integer> steps, long start, long end) {
		this.method = method;
		this.steps = steps == null ? Collections.emptyList() : steps;
		this.start = start;
		this.end = end;
	}

	public Class<?> getMethod() {
		return method;
	}

	public List<Integer> getSteps() {
		return steps;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public long getTime() {
		return end - start;
	}

	@Override
	public String toString() {
		return String.format("\n%s: %d passos em %d ms %s", method.getSimpleName(), steps.size(), getTime(),
				steps.toString());
	}
}
