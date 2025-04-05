package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Solver {
	
	private int[][] puzzle;
	private boolean solved;
	private final String[] possiblePuzzleDirs = new String[] {".", "./puzzles", "SudokuSolver/src/sudoku/puzzles", "src/sudoku/puzzles"};

	public Solver(String fileName) throws FileNotFoundException {
        puzzle = readPuzzle(fileName);  
    }

	public int[][] readPuzzle(String fileName) throws FileNotFoundException {
		File file = null;
		for (String puzzleDir : possiblePuzzleDirs) {
			file = new File(puzzleDir+"/"+fileName);
			if (file.isFile()) break;
		}
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
	
	/**
	 * Time how long it takes to run a solve
	 * @return The time it took to solve the puzzle in nanoseconds
	 */
	public long benchmarkSolver() {
        long startTime = System.nanoTime();
        
        solve();
        
        long endTime = System.nanoTime();
        
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
	
	/**
	 * Solve the puzzle
	 * @return True if the puzzle was successfully solved, false if the puzzle is unsolvable
	 */
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

	/**
	 * Print the current state of the puzzle to the console
	 */
	public void printPuzzle() {
		if (!solved) solve();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(puzzle[i][j]+" ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Save the current state of the puzzle to a file
	 * @param outputFileName
	 */
	public void saveToFile(String outputFileName) {
		try {
			File file = new File(outputFileName);
			FileWriter fileWriter = new FileWriter(file);
			String fileContent = "";
			for (int[] row : puzzle) {
				for (int cell : row) {
					fileContent += (Integer.toString(cell) + " ");
				}
				fileContent += "\n";
			}
			fileWriter.write(fileContent);
			fileWriter.close();
			System.out.println("File saved to \"" + file.getAbsolutePath() + "\".");
		} catch (IOException e) {
			System.out.println("There was a problem writing to \"" + new File(".").getAbsolutePath()+"/"+outputFileName+"\".  Please try again.");
		}
	}
}
