package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver {
	
	private int[][] puzzle;
	private boolean solved;

	public Solver(File file) throws FileNotFoundException {
		puzzle = readPuzzle(file);
	}
	
	public int[][] readPuzzle(File file) throws FileNotFoundException {
		int[][] puzzle = new int[9][9];
		Scanner fileText = new Scanner(file);
		for (int i = 0; i < 9; i++) {
			String line = fileText.nextLine();
			String[] nums = line.split(" ");
			for (int j = 0; j < 9; j++) {
				puzzle[i][j] = Integer.parseInt(nums[j]);
			}
		}
		fileText.close();
		return puzzle;
	}
	
	public void benchmarkSolver() {
        long startTime = System.nanoTime();
        
        boolean solvable = solve();
        
        long endTime = System.nanoTime();
        
        System.out.println("Solver Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Puzzle is " + ((solvable)?"solvable.":"not solvable."));
    }
	
	/**
	 * Checks if current puzzle state is "legal"
	 * @param puzzle The puzzle
	 * @return If the state is legal or not
	 */
	public boolean constraintCheck() {
		int leftDiagTotal = 0;
		int rightDiagTotal = 0;
		boolean[] seenInLeftDiag = new boolean[10];
		boolean[] seenInRightDiag = new boolean[10];
		boolean[][] seenInRow = new boolean[10][10];
		boolean[][][] seenIn3x3 = new boolean[3][3][10];
		for (int i = 0; i < 9; i++) {
			boolean[] seenInCol = new boolean[10];
			for (int j = 0; j < 9; j++) {
				int currentValue = puzzle[i][j];
				if (currentValue == 0) continue;
				if (i == j) {
					if ((leftDiagTotal+=currentValue) > 45 && seenInLeftDiag[i]) return false;
					seenInLeftDiag[i] = true;
				}
				if (i == 9-j) {
					if ((rightDiagTotal+=currentValue) > 45 && seenInRightDiag[i]) return false;
				}
				if (seenInCol[currentValue]) return false;
				if (seenInRow[j][currentValue]) return false;
				if (seenIn3x3[i/3][j/3][currentValue]) return false;
				seenInCol[currentValue] = true;
				seenInRow[j][currentValue] = true;
				seenIn3x3[i/3][j/3][currentValue] = true;
			}
		}
		return true;
	}
	
	public boolean solve() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (puzzle[i][j] == 0) {
					for (int k = 1; k < 10; k++) {
						puzzle[i][j] = k;
						if (constraintCheck() && solve()) return true;
						puzzle[i][j] = 0;
					}
					return false;
				}
			}
		}
		solved = true;
		return true;
	}

	public void printSoultion() {
		if (!solved) solve();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(puzzle[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
}
