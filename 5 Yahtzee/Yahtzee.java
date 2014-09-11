/*
 * File: Yahtzee.java
 * ------------------
 * This program plays the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		IODialog dialog = getDialog();
		// Get number of players
		nPlayers = dialog.readInt("Enter number of players");
		while (nPlayers < 0 || nPlayers > 4) {
			dialog.println("This game requires 1-4 players.");
			nPlayers = dialog.readInt("Enter number of players");
		}
		// Get names of players
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		// Create an array for the score card
		scoreCard = new int[nPlayers][TOTAL];
		initCard(scoreCard);
		// Display the game
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	/* 
	 * Initializes score card array.
	 * -1 value = scoring category has not been used yet.
	 */
	private void initCard( int[][] scoreCard ) {
		 for (int i = 0; i < scoreCard.length; i++ ) {
			for (int j = ONES-1; j <= SIXES-1; j++) {
				scoreCard[i][j] = -1;
			}
			for (int j = THREE_OF_A_KIND-1; j <= CHANCE-1; j++) {
				scoreCard[i][j] = -1;
			}
		}
	}
	
	private void playGame() {
		// Loop from first to last turn
		for (int turn = 0; turn < N_SCORING_CATEGORIES; turn++) {
			// Loop from first to last player
			for (int player = 1; player <= nPlayers; player++) {
				firstRoll(player);
				nextRoll();
				nextRoll();
				updateScoringCategory(player);
			}
		}
		updateRestOfCard();
		displayWinner();
	}

	// All dice are rolled
	private void firstRoll(int player) {
		display.printMessage("Click \"Roll Dice\" to roll all the dice.");
		display.waitForPlayerToClickRoll(player);
		for (int i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}	
		display.displayDice(dice);
	}
	
	// Player selects dice to re-roll and re-rolls them
	private void nextRoll() {
		display.printMessage("Select the dice you want to re-roll (if any). " +
				"Click \"Roll again\" to proceed.");
		display.waitForPlayerToSelectDice();
		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
			}
		}
		display.displayDice(dice);
	}
	
	/*
	 *  Player selects scoring category; category value is updated based on dice.
	 *  Assumption: Array was initialized so -1 = category is unused.
	 */
	private void updateScoringCategory(int player) {
		display.printMessage("Click a row in the score card to update that category.");
		int n = display.waitForPlayerToSelectCategory();
		while (true) {
			// Unused category selected; update value for scoring category
			if (scoreCard[player-1][n-1] == -1) { 
				int score = calculateScore(n);
				// Update array and graphics for score card
				scoreCard[player-1][n-1] = score;
				scoreCard[player-1][TOTAL-1] += score;
				display.updateScorecard(n, player, score);
				display.updateScorecard(TOTAL, player, scoreCard[player-1][TOTAL-1]);
				break;
			} 
			// Category previously selected; require user to select new category
			else {
				display.printMessage("Category already used!");
				n = display.waitForPlayerToSelectCategory();
			}
		}
	}
	
	/**
	 * Input: The number of the scoring category that the user selected.
	 * Output: The calculated score for that category, based on current dice.
	 * Consequence: Dice are sorted from least to greatest.
	 */
	private int calculateScore(int n) {
		Arrays.sort(dice);
		int score = 0;
		if (n >= ONES && n <= SIXES) {
			for(int i = 0; i < dice.length; i++) {
				if (dice[i] == n) { score += n; }
			}
		}
		else if (n == THREE_OF_A_KIND) {
			if(isThreeKind()) { score = sumOfDice(); }
		}
		else if (n == FOUR_OF_A_KIND) {
			if(isFourKind()) { score = sumOfDice(); }
		}
		else if (n == FULL_HOUSE) {
			if(isFullHouse()) { score = 25; }
		}
		else if (n == SMALL_STRAIGHT) {
			if(isSmallStraight()) { score = 30; }
		}
		else if (n == LARGE_STRAIGHT) {
			if(isLargeStraight()) { score = 40; }
		}
		else if (n == YAHTZEE) {
			if(isYahtzee()) { score = 50; }
		}
		else if (n == CHANCE) {
			score = sumOfDice();
		} 
		return score;
	}
	
	// Returns sum of all dice values.
	private int sumOfDice() {
		int sum = 0;
		for(int i = 0; i < N_DICE; i++) {
			sum += dice[i];
		}
		return sum;
	}
	
	private boolean isThreeKind() {
		int match = 0;
		for (int i = 0; i < N_DICE - 2; i++) { // Select die to compare
			for (int j = i+1; j < N_DICE; j++) { // Find matches in remaining dice
				if (dice[i] == dice[j]) {
					match++;
				}
			}
			if (match == 2) { return true; } // 2 matches means 3 dice of same kind
			else { match = 0; }
		}
		return false;
	}
	
	private boolean isFourKind() {
		int match = 0;
		for (int i = 0; i < N_DICE - 3; i++) { // Select die to compare
			for (int j = i+1; j < N_DICE; j++) { // Find matches in remaining dice
				if (dice[i] == dice[j]) {
					match++;
				}
			}
			if (match == 3) { return true; } // 3 matches means 4 dice of same kind
			else { match = 0; }
		}
		return false;
	}
	
	// Assumes exactly five dice will be analyzed and dice are sorted
	private boolean isFullHouse() {
		// Check for AA-BBB configuration
		if ((dice[0] == dice[1]) && 
				(dice[2] == dice[3]) && (dice[3] == dice[4])) {
			return true;
		}
		// Check for AAA-BB configuration
		else if ((dice[0] == dice[1]) && (dice[1] == dice[2]) && 
				(dice[3] == dice[4])) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Assumes dice array has been sorted via binarySearch
	private boolean isSmallStraight() {
		int sequence = 0;
		// Pick a starting die
		for (int i = 0; i < N_DICE -1; i++) {
			// Check if starting die begins a small straight
		    for (int j = 1; j < 4; j++) {
		        if (Arrays.binarySearch(dice,  dice[i]+j) >= 0) {
		            sequence++;
		        }
		    }
		    if (sequence == 3) { return true; }
		    else { sequence = 0; }
		}
		return false;
	}
	
	// Assumes dice array has been sorted
	private boolean isLargeStraight() {
		// Check if all dice are in sequence
		for(int i = 0; i < N_DICE-1; i++) {
			if(dice[i] != dice[i+1]-1) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isYahtzee() {
		for (int i = 1; i < N_DICE; i++) {
			if (dice[0] != dice [i]) {
				return false;
			}
		}
		return true;
	}
	
	private void updateRestOfCard() {
		for (int player = 1; player <= nPlayers; player++) {
			// Update score card array
			scoreCard[player-1][UPPER_SCORE-1] = sumUpperScore(player);
			if (scoreCard[player-1][UPPER_SCORE-1] >= 63) { // Add bonus
				scoreCard[player-1][UPPER_BONUS-1] = 35; 
			} else { 
				scoreCard[player-1][UPPER_BONUS-1] = 0; 
			}
			scoreCard[player-1][LOWER_SCORE-1] = sumLowerScore(player); // Lower score
			scoreCard[player-1][TOTAL-1] = scoreCard[player-1][UPPER_SCORE-1] +
				scoreCard[player-1][UPPER_BONUS-1] + scoreCard[player-1][LOWER_SCORE-1];
			
			// Update displayed score card
			display.updateScorecard(UPPER_SCORE, player, scoreCard[player-1][UPPER_SCORE-1]);
			display.updateScorecard(UPPER_BONUS, player, scoreCard[player-1][UPPER_BONUS-1]);
			display.updateScorecard(LOWER_SCORE, player, scoreCard[player-1][LOWER_SCORE-1]);
			display.updateScorecard(TOTAL, player, scoreCard[player-1][TOTAL-1]);
		}
	}
	
	private int sumLowerScore(int player) {
		int sum = 0;
		for (int i = THREE_OF_A_KIND-1; i <= CHANCE-1; i++) {
			sum += scoreCard[player-1][i];
		}
		return sum;
	}
	
	private int sumUpperScore(int player) {
		int sum = 0;
		for (int i = 0; i <= SIXES-1; i++) {
			sum += scoreCard[player-1][i];
		}
		return sum;
	}
	
	/**
	 * Finds the player with the highest TOTAL score and declares the winner.
	 * Accounts for ties.
	 */
	private void displayWinner() {
		if (nPlayers == 1) {
			display.printMessage("You win, obviously."); 
			return;
		}
		int highScore = scoreCard[0][TOTAL-1];
		for (int player = 1; player < nPlayers; player++) {
			if (scoreCard[player][TOTAL-1] > scoreCard[player-1][TOTAL-1]) {
				highScore = scoreCard[player][TOTAL-1];
			}
		}
		ArrayList<String> winners = new ArrayList<String>();
		for (int player = 0; player < nPlayers; player++) {
			if (scoreCard[player][TOTAL-1] == highScore) {
				winners.add(playerNames[player]);
			}
		}
		if (winners.size() == 1) {
			display.printMessage(winners.get(0) + " wins!");
		} else {
			// Create string of winners
			String winnerStr = "";
			for (int i = 0; i < winners.size(); i++) {
				winnerStr += winners.get(i) + ", ";
			}
			// Remove the last ", " from the string
			winnerStr = winnerStr.substring(0, winnerStr.length()-2);
			display.printMessage("It's a tie! The winners are: " + winnerStr);
		}
	}

/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice = new int[N_DICE];
	private int[][] scoreCard;
}
