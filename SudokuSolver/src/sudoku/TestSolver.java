package sudoku;

import java.io.File;
import java.io.FileNotFoundException;

public class TestSolver {
	public static void main(String[] args) throws FileNotFoundException {
		File dir = new File("src/sudoku/puzzles/");
		for (File file : dir.listFiles()) {
			Solver solver = new Solver(file);
//			solver.benchmarkSolver(puzzle);
			solver.printSoultion();
			System.out.print("\n");
		}
	}
}
