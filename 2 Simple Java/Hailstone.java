/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int n = readInt("Enter an integer: ");
		showSequence(n);
	}
	
	/**
	 * Prints the calculation for each number in the hailstone sequence,
	 * starting with the integer n. Then prints the number of calculations
	 * that were required for the sequence to end with 1.
	 */
	private void showSequence(int n) {
		int number_of_steps = 0;
		while(n > 1) {
			int result = 0;
			if(n%2==0) {
				result = n/2;
				println(n + " is even, so I take half: " + result);
				n = result;
			} else {
				result = 3*n + 1;
				println(n + " is odd, so I make 3n + 1: " + result);
				n = result;
			}
			number_of_steps++;
		}
		println("The process took " + number_of_steps + " steps to reach 1.");
	}
	
}

