/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	public void run() {
		resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		gameSetup();
		gamePlay();
	}

	private void gameSetup() {
		createBricks();
		createPaddle();
		createBall();
		addMouseListeners();
	}

	/* Creates all the bricks */
	private void createBricks() {
		int x = BRICK_X_OFFSET;
		int y = BRICK_Y_OFFSET;
		/* Keeps track of row */
		for(int i = 0; i < NBRICK_ROWS; i++) {
			/* Keeps track of brick in row */
			for(int j = 0; j < NBRICKS_PER_ROW; j++) {
				add(colorBrick(i, new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT)));
				x = x + BRICK_WIDTH + BRICK_SEP;
			}
			y = y + BRICK_HEIGHT + BRICK_SEP;
			x = BRICK_X_OFFSET;
		}
	}

	/* Takes a GRect object, colors it according to the 
	 * provided i variable and then returns (a copy of) it. 
	 */
	private GRect colorBrick (int i, GRect brick) {
		brick.setFilled(true);
		if(i <= 1) {
			brick.setFillColor(Color.red);
			brick.setColor(Color.red);
		} else if (i <= 3) {
			brick.setFillColor(Color.orange);
			brick.setColor(Color.orange);
		} else if (i <= 5) {
			brick.setFillColor(Color.yellow);
			brick.setColor(Color.yellow);
		} else if (i <= 7) {
			brick.setFillColor(Color.green);
			brick.setColor(Color.green);
		} else {
			brick.setFillColor(Color.blue);
			brick.setColor(Color.blue);
		}
		return brick;
	}

	/* Creates paddle */
	private void createPaddle() {
		paddle = new GRect(WIDTH/2 - PADDLE_WIDTH/2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
	}

	/* Ball is created and stands still in middle of window */
	private void createBall() {
		ball = new GOval(WIDTH/2 - BALL_RADIUS, HEIGHT/2 - BALL_RADIUS, BALL_RADIUS*2, BALL_RADIUS*2);
		ball.setFilled(true);
		add(ball);
		start = true;
	}

	/* When mouse is clicked, initial ball trajectory is set */
	public void mousePressed(MouseEvent e) {
		double x = e.getX();
		paddleStopAtWall(x);
		if (start) {
			vx = rgen.nextDouble(1.0, 2.0);
			if (rgen.nextBoolean(0.5)) vx = -vx;
			vy = 2;
		}
		start = false;
	}

	/* Move paddle left and right with mouse (button pressed) */
	public void mouseDragged(MouseEvent e) {
		double x = e.getX();
		paddleStopAtWall(x);
	}

	/* Makes sure the paddle cannot move beyond the left/right walls of the window */
	private void paddleStopAtWall(double x) {
		/* Cursor is beyond left wall */
		if (x < PADDLE_WIDTH/2) {
			paddle.setLocation(0, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			/* Cursor is beyond right wall */
		} else if (x > WIDTH - PADDLE_WIDTH/2 - 2) {
			paddle.setLocation(WIDTH - PADDLE_WIDTH, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			/* Cursor is within walls */
		} else {
			paddle.setLocation(x - PADDLE_WIDTH/2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}

	/* Game begins with ball dropping at angle */
	private void gamePlay() {
		while (turn > 0) {
			ballMoves();
			ballCollides();
			ballPassesPaddle();
		}
		gameEnds();
	}

	/* Ball moves according to velocity, which changes on collision */
	private void ballMoves() {
		ball.move(vx, vy);
		pause(pause_time);
	}

	/* Ball bounces at 90-degree angle upon collision */
	private void ballCollides() {
		/* Ball hits wall */
		bounceOffWall();
		/* Ball hits paddle/brick */
		bounceOffElement();
	}

	/* Check if ball hit a wall. If so: bounce. */
	private void bounceOffWall() {
		/* Hits right wall of window */
		if (ball.getX() >= WIDTH - BALL_RADIUS*2) {
			vx = -1*vx;
			/* Hits left wall of window */
		} else if (ball.getX() <= 0) {
			vx = -1*vx;
			/* Hits top of window */
		} else if (ball.getY() <= 0) {
			vy = -1*vy;
		}
	}

	private void bounceOffElement() {
		/* Check each corner of a square surrounding the ball
		 * for a paddle/brick.
		 */
		//top-left
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			GObject collider = getElementAt(ball.getX(), ball.getY());
			eraseBrick(collider);
			//top-right
		} else if (getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY()) != null) { 
			GObject collider = getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY());
			eraseBrick(collider);
			//bottom-right
		} else if (getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2) != null) {
			GObject collider = getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2);
			eraseBrick(collider);
			//bottom-left
		} else if (getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2)  != null ) {
			GObject collider = getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2);
			eraseBrick(collider);
		}
	}

	/* Check if what ball collided with is a paddle. If not,
	 * it must be a brick, so remove brick and bounce. If so, 
	 * just bounce.
	 */
	private void eraseBrick(GObject collider) {
		if (collider != paddle) {
			remove(collider);
		}
		vy = -1*vy;
	}

	/* Check if ball has gotten to bottom of screen.
	 * If so: remove ball and create a new one if
	 * there are turns left.
	 */
	private void ballPassesPaddle() {
		if (ball.getY() > HEIGHT) {
			remove(ball);
			turn = turn - 1;
			vx = 0;
			vy = 0;
			if (turn > 0) {
				createBall();
			}
		}
	}

	/* Print "Game Over" in center of window */
	private void gameEnds() {
		GLabel gameover = new GLabel("Game Over");
		int x = (int)(WIDTH/2 - gameover.getWidth()/2);
		int y = (int)(HEIGHT/2 - gameover.getAscent()/2);
		gameover.setLocation(x, y);
		gameover.setFont(new Font("Serif", Font.BOLD, 18));
		add(gameover);
	}

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 650;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH ;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 8;

	/** Offset of the brick rows from the left */
	private static final int BRICK_X_OFFSET = 1;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private GRect paddle;
	private GOval ball;
	private boolean start;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double pause_time = 5; //Time between ball movements (ms)
	private int turn = NTURNS;

}