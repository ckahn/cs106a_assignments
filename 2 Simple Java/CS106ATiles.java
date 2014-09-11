/*
 * File: CS106ATiles.java
 * Name: 
 * Section Leader: 
 * ----------------------
 * This file is the starter file for the CS106ATiles problem.
 */

import acm.graphics.*;
import acm.program.*;

public class CS106ATiles extends GraphicsProgram {
	
	/** Pixel width of each tile **/
	private static final int TILE_WIDTH = 120;
	
	/** Pixel height of each tile **/
	private static final int TILE_HEIGHT = 40;
	
	/** Amount of space (in pixels) between tiles */
	private static final int TILE_SPACE = 20;

	public void run() {
		
		/* Top tile and label */
		add(new GRect(getWidth()/2 - TILE_WIDTH/2, getHeight()/2 - TILE_SPACE - TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT));
		centerLabel(new GLabel("Program", getWidth()/2 - TILE_WIDTH/2, getHeight()/2 - TILE_SPACE));
		
		/* Bottom center tile and label */
		add(new GRect(getWidth()/2 - TILE_WIDTH/2, getHeight()/2 + TILE_SPACE, TILE_WIDTH, TILE_HEIGHT));
		centerLabel(new GLabel("ConsoleProgram", getWidth()/2 - TILE_WIDTH/2, getHeight()/2 + TILE_SPACE + TILE_HEIGHT));
		
		/* Bottom left tile and label */
		add(new GRect(getWidth()/2 - TILE_WIDTH/2 - TILE_SPACE - TILE_WIDTH, getHeight()/2 + TILE_SPACE, TILE_WIDTH, TILE_HEIGHT));
		centerLabel(new GLabel("GraphicsProgram", getWidth()/2 - TILE_WIDTH/2 - TILE_SPACE - TILE_WIDTH, getHeight()/2 + TILE_SPACE + TILE_HEIGHT));
		
		/* Bottom right tile and label */
		add(new GRect(getWidth()/2 + TILE_WIDTH/2 + TILE_SPACE, getHeight()/2 + TILE_SPACE, TILE_WIDTH, TILE_HEIGHT));
		centerLabel(new GLabel("DialogProgram", getWidth()/2 + TILE_WIDTH/2 + TILE_SPACE, getHeight()/2 + TILE_SPACE + TILE_HEIGHT));
		
		/* All three lines connecting the top tile to the bottom three */
		add(new GLine(getWidth()/2, getHeight()/2-TILE_SPACE, getWidth()/2, getHeight()/2 + TILE_SPACE));
		add(new GLine(getWidth()/2, getHeight()/2-TILE_SPACE, getWidth()/2 - TILE_WIDTH - TILE_SPACE, getHeight()/2 + TILE_SPACE));
		add(new GLine(getWidth()/2, getHeight()/2-TILE_SPACE, getWidth()/2 + TILE_WIDTH + TILE_SPACE, getHeight()/2 + TILE_SPACE));
	}
	
	/* Takes a label whose origin is the bottom-left corner of a tile and centers the label */
	private void centerLabel(GLabel lab) {
		int lab_width = (int)lab.getWidth();
		int lab_height = (int)lab.getAscent();
		lab.move((TILE_WIDTH - lab_width)/2, (TILE_HEIGHT - lab_height)/-2);
		add(lab);
	}
	
}

