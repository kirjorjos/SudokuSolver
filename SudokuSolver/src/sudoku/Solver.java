package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver {
	
	private int[][] puzzle;
	private boolean solved;

	public Solver() throws FileNotFoundException {
        puzzle = readPuzzle();  
    }

	public int[][] readPuzzle() throws FileNotFoundException { 
		File workingDir = new File(".");
		String puzzlesDir = workingDir.getAbsolutePath().split("SudokuSolver/")[0]+"SudokuSolver/SudokuSolver/src/sudoku/puzzles/";
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name to read the puzzle from: ");
        String fileName = scanner.nextLine();
        scanner.close();

		File file = new File(puzzlesDir+fileName);  
        int[][] puzzle = new int[9][9];

        try (Scanner fileText = new Scanner(file)) {
            for (int i = 0; i < 9; i++) {

                String line = fileText.nextLine();
                String[] nums = line.split(" ");

                for (int j = 0; j < 9; j++) {
                    try {
                        puzzle[i][j] = Integer.parseInt(nums[j]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid number in the file. Ensure all numbers are integers.");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file was not found.");
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }

        return puzzle;
    }
	
	public long benchmarkSolver() {
        long startTime = System.nanoTime();
        
        boolean solvable = solve();
        
        long endTime = System.nanoTime();
        
        System.out.println("Solver Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Puzzle is " + ((solvable)?"solvable.":"not solvable."));
        return endTime - startTime;
    }
	
	/**
	 * Checks if current puzzle state is "legal"
	 * @param puzzle The puzzle
	 * @return If the state is legal or not
	 */
	public boolean constraintCheck(int row, int col, int num) {

		// check rows 
		for(int i = 0; i < 9; i++){
			if (puzzle[row][i] == num){
				return false;

			}
		}

		//columns
		for(int j = 0; j < 9; j++){
			if(puzzle[j][col] == num){
				return false;
			}
		}

		//check squares
		int intSquareRow = row - row % 3;
		int intSquareCol = col - col % 3;
		for (int i = intSquareRow; i < intSquareRow + 3; i++){
			for (int j = intSquareCol; j < intSquareCol + 3; j++) {
				if (puzzle[i][j] == num) {
					return false;
				}
			}
				}

			
			
		return true;
	}
	public boolean solve() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (puzzle[i][j] == 0) {
					for (int num = 1; num <= 9; num++) {
						if (constraintCheck(i, j, num)) {  
							puzzle[i][j] = num;
							if (solve()) {       
								return true;
							}
							puzzle[i][j] = 0;    
						}
					}
					return false;              
				}
			}
		}
		solved = true;
		return true;  
	}

	public void printSolution() {
		if (!solved) solve();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(puzzle[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
}
