/*
 * File: HangmanCanvas.java
 *
------------------
------
 * This file keeps track of the Hangman display.
 */
import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	GCompound picture;
	GLabel displayed_w; // Current state of guessed word
	GLabel wrong_answers; // Displayed list of incorrect guesses
	String list = ""; // String to display in wrong_answers
	
	/* Resets the display so that only the scaffold appears */
	public void reset() {
		displayed_w = new GLabel("");
		displayed_w.setFont("Courier New-plain-36");
		displayed_w.setLocation(20, 450);
		add(displayed_w);
		
		wrong_answers = new GLabel("");
		wrong_answers.setFont("Arial-plain-18");
		wrong_answers.setLocation(20, 500);
		add(wrong_answers);
		
		picture = new GCompound();
		
		add(new GLine(700/8, 500/4, 700/8, 500/4+SCAFFOLD_HEIGHT)); // Add scaffold pole
		add(new GLine(700/8, 500/4, 700/8+BEAM_LENGTH, 500/4)); // Add beam
		add(new GLine(700/8+BEAM_LENGTH, 500/4, 700/8+BEAM_LENGTH, 500/4+ROPE_LENGTH)); // Add rope
	}
	
	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		displayed_w.setLabel(word);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		list = list + letter;
		wrong_answers.setLabel(list);
		incorrect_guesses++;
		switch (incorrect_guesses) {
			case 1: add(new GOval(700/8+BEAM_LENGTH-HEAD_RADIUS, 500/4+ROPE_LENGTH, HEAD_RADIUS*2, HEAD_RADIUS*2)); // Add head
					break;
			case 2: add (new GLine(700/8+BEAM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2, 
					700/8+BEAM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH)); // Add body
					break;
			case 3: add (new GLine(700/8+BEAM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH,
					700/8+BEAM_LENGTH-UPPER_ARM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH));
					add (new GLine(700/8+BEAM_LENGTH-UPPER_ARM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH,
					700/8+BEAM_LENGTH-UPPER_ARM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH+LOWER_ARM_LENGTH)); // Add left arm
					break;
			case 4: add (new GLine(700/8+BEAM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH,
					700/8+BEAM_LENGTH+UPPER_ARM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH));
					add (new GLine(700/8+BEAM_LENGTH+UPPER_ARM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH,
					700/8+BEAM_LENGTH+UPPER_ARM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+ROPE_LENGTH+LOWER_ARM_LENGTH)); // Add right arm
					break;
			case 5: add (new GLine(700/8+BEAM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH,
					700/8+BEAM_LENGTH-HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH)); 
					add (new GLine(700/8+BEAM_LENGTH-HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH,
					700/8+BEAM_LENGTH-HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH)); // Add left leg
					break;
			case 6: add (new GLine(700/8+BEAM_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH,
					700/8+BEAM_LENGTH+HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH)); 
					add (new GLine(700/8+BEAM_LENGTH+HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH,
					700/8+BEAM_LENGTH+HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH)); // Add right leg
					break;
			case 7: add (new GLine(700/8+BEAM_LENGTH-HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH,
					700/8+BEAM_LENGTH-HIP_WIDTH-FOOT_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH)); // Add left food
					break;
			case 8: add (new GLine(700/8+BEAM_LENGTH+HIP_WIDTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH,
					700/8+BEAM_LENGTH+HIP_WIDTH+FOOT_LENGTH, 500/4+ROPE_LENGTH+HEAD_RADIUS*2+BODY_LENGTH+LEG_LENGTH)); // Add right food
					break;
		}
	}

	private int incorrect_guesses = 0;
	
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 200;
	private static final int BEAM_LENGTH = 88;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 20;
	private static final int BODY_LENGTH = (SCAFFOLD_HEIGHT-HEAD_RADIUS*2-ROPE_LENGTH)/2+ROPE_LENGTH;
	private static final int UPPER_ARM_LENGTH = BEAM_LENGTH/2;
	private static final int LOWER_ARM_LENGTH = UPPER_ARM_LENGTH/2;
	private static final int HIP_WIDTH = UPPER_ARM_LENGTH*7/10;
	private static final int LEG_LENGTH = SCAFFOLD_HEIGHT-ROPE_LENGTH-HEAD_RADIUS*2-BODY_LENGTH;
	private static final int FOOT_LENGTH = HIP_WIDTH/4;

}