/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * Karel should leaves a beeper on the corner closest to the center 
 * of 1st St.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		dropBeepersAcrossRow();
		moveToCenter();
		putBeeper();
	}

	/**
	 * Karel places one beeper on every corner of the current street.
	 * Pre-condition: Karel starts on 1st Ave. and is facing east.
	 * Post-condition: Karel stops at last avenue facing west.
	 */
	private void dropBeepersAcrossRow() {
		putBeeper();
		while(frontIsClear()) {
			move();
			putBeeper();
		}
		turnAround();
	}

	/**
	 * Karel removes the outermost beepers and repeats
	 * until all beepers are gone and he is in the middle of the
	 * street.
	 * Pre-condition: The street has a beeper on every corner, and
	 * Karel is on the far side of the street facing the other side.
	 * Post-condition: Karel is standing in the middle of 
	 * the street, and the street has no beepers.	 
	 */
	private void moveToCenter() {
		pickWallBeepers();
		pickEndBeepers();
	}

	/**
	 * Karel takes the beepers that are on the far sides of the street.
	 * Pre-condition: The street has a beeper on every corner, and
	 * Karel is on the far side of the street facing the other side.
	 * Post-condition: Karel is standing on the last beeper in a row,
	 * and he is facing the other end of the row.
	 */
	private void pickWallBeepers() {
		pickBeeper();
		while(frontIsClear()) {
			move();
		}
		if(beepersPresent()) {
			pickBeeper();
		}
		turnAround();
		if(frontIsClear()) {
			move();
		}
	}

	/** 
	 * Karel takes the beeper at the ends of the row and repeats.
	 * Pre-condition: Karel is standing at the end of a 
	 * row of beepers and is facing the other end of the row.
	 */
	private void pickEndBeepers() {
		while(beepersPresent()) {
			pickBeeper();
			if(frontIsClear()) {
				move();
			}
			while(beepersPresent()) {
				move();
			}
			turnAround();
			if(frontIsClear()) {
				move();
			}
		}
	}

}