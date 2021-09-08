package connoroaks.snake;

/**
 * @author Connor Oaks
 * @summary This class contains the logic behind the snake game, and the code for the graphical portion of the game 
 */

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JPanel {

	private static final long serialVersionUID = 1L;		// Eclipse is happy
	
	/**
	 * @summary this class contains code for the snake food 
	 */
	private class Food extends Point {
		private static final long serialVersionUID = 1L;    // but i am not :,( 
		public Food() {
			// Random point on the game board that is not on the snake 
			do {
				this.x = Utilities.randomInteger(GRID_SIZE + 1);
				this.y = Utilities.randomInteger(GRID_SIZE + 1); 
			} while(snake.occupies(this, true)); 
		}
		
		/**
		 * @param g - Graphics object passed in from JPanel paintComponent
		 */
		public void draw(Graphics g) {
			// colors are inverse of the snake 
			Utilities.drawPoint(g, this, snake.fillColor(), snake.borderColor());
		}
	}
	
	public static final int GRID_SIZE = 30, GRID_SQUARE_SIZE = 20; 
	private static final long START_GAME_DELAY = 1000; 
	
	private Snake snake; 
	private Food apple; 
	private int score;
	private final JLabel scoreBoard;  
	private ScheduledFuture<?> scheduler;
	private boolean gameInProgress;
	
	public GameBoard() {
		scoreBoard = new JLabel(); 
		scoreBoard.setFont(new Font("Arial", Font.BOLD, 50));
		this.add(scoreBoard);
		gameInProgress = false; 	
	}
	
	/**
	 * @summary resets fields as necessary and starts a new game  
	 */
	public void start(Difficulty difficulty) {
		gameInProgress = true; 
		score = 0; 
		scoreBoard.setText("0");
		scoreBoard.setForeground(Utilities.randomColor());
		this.setBackground(Utilities.randomColor());
		snake = new Snake();
		Direction[] directions = Direction.values(); 
		this.setSnakeDirection(directions[Utilities.randomInteger(directions.length)]);
		apple = new Food(); 
		// start game loop
		scheduler = Executors.newSingleThreadScheduledExecutor()
				.scheduleAtFixedRate(this::gameLoop, START_GAME_DELAY, difficulty.getInterval(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * @summary game loop function called by scheduler
	 */
	private void gameLoop() {
		boolean growSnake = snake.head().equals(apple); 
		snake.advance(growSnake);		
		if(growSnake) {			
			// snake got food 
			scoreBoard.setText(String.valueOf(++score));	
			apple = new Food(); 								
		}
		else if(this.gameIsOver()) {	// game can't be over if the snake just got the apple
			gameInProgress = false;
			Utilities.gameOverScreen();
			scheduler.cancel(true);
		}
		this.repaint();
	}
	
	/**
	 * @return whether there is a game in progress
	 */
	public boolean gameInProgress() {
		return gameInProgress; 
	}
	
	/**
	 * @return the score of the current game 
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @param direction - The new direction given by the user. 
	 */
	public void setSnakeDirection(Direction direction) {
		snake.setDirection(direction);
	}
	
	/**
	 * @return the current direction the snake is moving
	 */
	public Direction getSnakeDirection() {
		return snake.getDirection(); 
	}
	
	/**
	 * @return true if the game is over
	 */
	public boolean gameIsOver() {
		Point head = snake.head(); 
		// check if snake is out of bounds or eating itself
		return snake.occupies(head, false) || (head.x < 0 || head.x > GRID_SIZE || head.y < 0 || head.y > GRID_SIZE); 
	}

	@Override
	public java.awt.Dimension getPreferredSize() {
		return Window.PREFERRED_GAME_AREA_SIZE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		snake.draw(g);		
		apple.draw(g);		 
	}
	
}
