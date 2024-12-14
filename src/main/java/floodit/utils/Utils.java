package floodit.utils;

public abstract class Utils {
	public static int[][] arrayCopy(int[][] aSource) {
		int[][] result = new int[aSource.length][aSource[0].length];
		for (int i = 0; i < aSource.length; i++) {
			System.arraycopy(aSource[i], 0, result[i], 0, aSource[i].length);
		}
		return result;
	}
}
