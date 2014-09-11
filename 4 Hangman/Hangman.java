/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

@SuppressWarnings("serial")
public class Hangman extends ConsoleProgram {

	public void init() {
		resize(APPLICATION_WIDTH, APPLICATION_HEIGHT); 
		canvas = new HangmanCanvas();
		canvas.reset();
		add(canvas);
	}
	
    public void run() {
    	String secret_w = getWord("HangmanLexicon.txt"); // Randomly pick a secret word from the specified text file;
    	if (secret_w != "") {
    		playGame(secret_w); // Play game
            endGame(secret_w); // Display appropriate info when game ends
    	} else {
    		println("Could not get secret word...");
    	}
    }
    
    /* Open specified file and return a randomly selected word from that file */
    private String getWord(String filename) {
    	String line = "";
    	int arraySize = 0;
    	ArrayList<String> strlist = new ArrayList<String>();
    	BufferedReader rd = null;
    	try {
    		rd = new BufferedReader(new FileReader(filename));
    	} catch (IOException ex) {
    		println("Could not open dictionary file.");
    		return "";
    	}
    	try {
        	line = rd.readLine();
        	while (line != null) {
        		strlist.add(line);
        		line = rd.readLine();
        		arraySize++;
        	}
        	rd.close();
    	} catch (IOException ex){ 
    		println("Could not read dictionary file.");
    		return "";
    	}
    	int sw_index = rgen.nextInt(arraySize);
    	String secret_w = strlist.get(sw_index);
		return secret_w;
	}
    
	/* Play game until turns run out */
    private void playGame(String secret_w) {
    	println("Welcome to Hangman!");
    	for (int i = 0; i < secret_w.length(); i++) { // Build displayed word
    		displayed_w = displayed_w + "-";
    	}
    	int guess_remain = 8; // # of guesses remaining
    	println("The word now looks like this: " + displayed_w);
    	canvas.displayWord(displayed_w); // Update word on canvas
    	println("You have " + guess_remain + " guesses left.");
    	while (guess_remain > 0) {
    		String input = "";
    		while (input.length() < 1) {
    			input = readLine("Your guess: "); // Keep asking until user enters something
    		}
        	if ( Character.isLetter(input.charAt(0)) && input.length() == 1 ) { // Make sure input is a single letter
        		guess = Character.toUpperCase(input.charAt(0)); // Convert string to upper case character
           		displayed_w = updateWord(secret_w); // Update displayed_w w/ correctly guessed characters
            	if (displayed_w.equals(secret_w)) {
            		win = true;
            		break;
            	}
            	if (secret_w.indexOf(guess) == -1) { // Wrong guess updates list on canvas
            		canvas.noteIncorrectGuess(guess);
                	guess_remain--;
            	}
            	println("The word now looks like this: " + displayed_w);
            	canvas.displayWord(displayed_w);
        	} else {
        		println("Invalid input. Enter a single letter.");
        	}
        	if (guess_remain > 1) {
            	println("You have " + guess_remain + " guesses left.");
        	} else if (guess_remain == 1) { 
        		println("This is your last guess.");
        	}
    	}
	}

	/* Updates displayed word to show correctly guessed character(s) */
	private String updateWord(String secret_w) {
   		String new_displayed_w = "";
    	for (int i = 0; i < displayed_w.length(); i++) { 
    		if (guess == secret_w.charAt(i)) {
    			new_displayed_w = new_displayed_w + guess;
    		}
    		else {
    			new_displayed_w = new_displayed_w + displayed_w.charAt(i);
    		}
    	}
    	displayed_w = new_displayed_w;
		return new_displayed_w;
	}
    
    /* Game is over -- display appropriate win/lose message */
    private void endGame(String secret_w) {
    	if (win) {
    		println("You guessed the word: " + secret_w + ".");
    		println("You win."); 
    		canvas.displayWord(displayed_w);
    	} else {
    		if (secret_w.indexOf(guess) < 0) {
        		println("There are no " + guess + "'s in the word.");
    		}
    		println("You're completely hung.");
    		println("The word was: " + secret_w + ".");
    		println ("You lose.");
    	}		
    }
    
    private HangmanCanvas canvas;
	private RandomGenerator rgen = RandomGenerator.getInstance(); // Create random value generator
	private boolean win = false; // Whether you win or lose
	private char guess = '-'; // The single-letter guess during a turn
	private String displayed_w = ""; // The word you see in the console
	
	public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500; 
}
