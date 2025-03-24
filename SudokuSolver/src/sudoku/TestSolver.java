package sudoku;

import java.io.FileNotFoundException;

public class TestSolver {
	public static void main(String[] args) throws FileNotFoundException {
		Solver solver = new Solver();
		solver.solve();
		solver.printSolution();
	}
}
