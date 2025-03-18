package sudoku;

import java.io.File;
import java.io.FileNotFoundException;

public class TestSolver {
	public static void main(String[] args) throws FileNotFoundException {
		File dir = new File("SudokuSolver/src/sudoku/puzzles/");
		System.out.println(dir.getAbsolutePath());
		double totalTime = 0;
		int puzzleCount = 0;
		for (File file : dir.listFiles()) {
			Solver solver = new Solver(file);
//			totalTime+=solver.benchmarkSolver();
//			puzzleCount++;
			solver.printSolution();
			System.out.print("\n");
		}
		System.out.println("Average: " + totalTime/puzzleCount);
	}
}
