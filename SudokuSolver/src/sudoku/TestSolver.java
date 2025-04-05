package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestSolver {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(new File(".").getAbsolutePath());
		boolean solvable;
		
		Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the file name to read the puzzle from: ");
        String fileName = scanner.nextLine();
        
		Solver solver = new Solver(fileName);
		
		solvable = solver.solve();
		if (solvable) {
			System.out.println("");
		}
		
        System.out.print("Enter the file name to read the puzzle from (or leave blank to print to console): ");
        String outputFileName = scanner.nextLine();
        
        if (outputFileName.equals("")) {
        	solver.printPuzzle();
        } else {
        	solver.saveToFile(outputFileName);
        }
		
        scanner.close();
	}
}
