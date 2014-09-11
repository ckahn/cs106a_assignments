/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		turnLeft();
		repairColumns(); /* First Ave columns repaired. */
		/* This loop checks to see if Karel can move four avenues
		over. If so, another avenue	of columns are repaired. */
		while (frontIsClear()) { 
			move();
			if (frontIsClear()) {
				move();
				if (frontIsClear()) {
					move();
					if (frontIsClear()) {
						move();
						turnLeft();
						repairColumns();
					}
				}
			}
		}
	}
	
	/**
	 * This method ensures that the current avenue has columns from 1st
	 * St to the closest northern wall.
	 * 
	 * Assumption: Method is invoked while Karel is on 1st St
	 * and facing north.
	 * 
	 * Consequence: Karel is on 1st St again and facing east.
	 */
	private void repairColumns() {
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
			move();
		}
		if (noBeepersPresent()) {
			putBeeper();
		}
		turnLeft();
		turnLeft();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
	
}
