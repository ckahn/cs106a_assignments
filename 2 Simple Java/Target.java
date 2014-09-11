/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	
	private static final double OUTER_CIRCLE_RADIUS = 1;
	private static final double WHITE_CIRCLE_RADIUS = 0.65;
	private static final double BULLSEYE_RADIUS = 0.3;
	
	public void run() {
		add(drawCircle(OUTER_CIRCLE_RADIUS));
		
		GOval white_circle = drawCircle(WHITE_CIRCLE_RADIUS);
		white_circle.setFillColor(Color.WHITE);
		add(white_circle);
		
		add(drawCircle(BULLSEYE_RADIUS));
	}
	
	private GOval drawCircle(double radius_inches) {
		int radius = (int)(radius_inches*72);
		int x = (int)(getWidth()/2 - radius);
		int y = (int)(getHeight()/2 - radius);
		GOval circle = new GOval(x, y, radius*2, radius*2);
		circle.setFilled(true);
		circle.setFillColor(Color.RED);
		return circle;
	}
}	
