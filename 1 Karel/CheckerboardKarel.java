/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * Karel fills out a rectangular world by dropping
 * beepers in a checkerboard patter. Assumes Karel
 * starts on the 1st and 1st and faces east.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	public void run() {
		/* First, create pattern for first row */
		putBeeper();
		fillRow();
		/* Face north to look for next row. */
		turnLeft(); 
		/* Fill out next row and repeat, if possible. */
		doNextRows();
	}

	/**
	 * Checks if there's another row to fill out.
	 * If so, fills out row and repeats until all 
	 * rows are done.
	 */
	private void doNextRows() {
		while(frontIsClear()) {
			if(beepersPresent()) {
				moveToNextRow();
				while(frontIsClear()) {
					move();
					putBeeper();
					fillRow();
				}
			}
			else {
				moveToNextRow();
				putBeeper();
				fillRow();
			}
			if (facingWest()) {
				turnRight();
			}
			else {
				turnLeft();
			}
		}
	}

	/** 
	 * Fills out current row. Assumes last beeper has
	 * been placed in correct position.
	 */
	private void fillRow() {
		while(frontIsClear()) {
			move();
			if(frontIsClear()) {
				move();
				putBeeper();
			}
		}
	}

	/**
	 * Moves to next row, if possible, and then faces forward. 
	 * Assumes Karel was facing north when invoked.
	 */
	private void moveToNextRow() {
		move();
		if(rightIsBlocked()) {
			turnLeft();
		}
		else {
			turnRight();
		}
	}

	/**
	 * Turns right by making 3 lefts.
	 */
	public void turnRight() {
		turnLeft();
		turnLeft();
		turnLeft();
	}
}