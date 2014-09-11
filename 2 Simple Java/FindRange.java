/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	public void run() {
		println("This program finds the largest and smallest number.");
		println("Enter 0 when you want to see the results.");
		int smallest = 0;
		int largest = 0;
		int x = readInt("? ");
		if(x == 0) {
			println("No input to analyze.");
		} else {
			while(x != 0) {
				smallest = getSmallest(x, smallest);
				largest = getLargest(x, largest);
				x = readInt("? ");
			}
			println("smallest: " + smallest);
			println("largest: " + largest);
		}
	}
	
	private int getSmallest(int x, int smallest) {
		if(x < smallest) {
			smallest = x;
		}
		return smallest;
	}
	
	private int getLargest(int x, int largest) {
		if(x > largest) {
			largest = x;
		}
		return largest;
	}
	
}

