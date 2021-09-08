package connoroaks.snake;

/**
 * @author Connor Oaks
 * @date October 2016
 * @summary this class contains all code for the JFrame housing the snake game, key presses are handled here
 */

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Window extends JFrame
{
	private static final long serialVersionUID = 1L; // eclipse 
	private static final int ARROW_KEY_LOWER_BOUND = 0x25, ARROW_KEY_UPPER_BOUND = 0x28;
	public static final Dimension PREFERRED_GAME_AREA_SIZE = new Dimension(609, 609);
	
	private final GameBoard gameBoard;
	private final AnimationPane animator;

	private boolean acceptingInput;
	
	public Window() {
		gameBoard = new GameBoard();		
		this.add(gameBoard); 
		animator = new AnimationPane();
		this.add(animator);
		acceptingInput = true;
		// display start screen
		gameBoard.setVisible(false);	
		animator.animate(AnimationPane.START);		
		// Add key listener to the window.	
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(acceptingInput) Window.this.handleKeyPress(e.getKeyCode()); 	
			}
		});
		// JFrame settings 
		setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS)); 	
		pack();				
		setVisible(true);
		setTitle("JavaSnake by Connor Oaks");
		setLocationRelativeTo(null);			 
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}
	
	/**
	 * @summary Handle a key press from the user. 
	 * @param which - the key code corresponding to a key on the keyboard 
	 */
	private void handleKeyPress(int which) {
		// only proceed if the key pressed is an arrow key 
		if(which < ARROW_KEY_LOWER_BOUND && which > ARROW_KEY_UPPER_BOUND) return;
		if(gameBoard.gameInProgress()) {
			// change the direction of the snake 
			Direction newDirection = Direction.values()[which - ARROW_KEY_LOWER_BOUND]; 
			if(gameBoard.getScore() == 0 || gameBoard.getSnakeDirection().getValue() + newDirection.getValue() == 1)
				gameBoard.setSnakeDirection(newDirection); 		
		} else {
			// start game
			animator.stop();
			animator.setVisible(false);
			gameBoard.setVisible(true);
			gameBoard.start(Difficulty.values()[which - ARROW_KEY_LOWER_BOUND]);
		}
	}
	
	/**
	 * @summary display game over screen 
	 */
	public void gameOverScreen() {
		gameBoard.setVisible(false);
		animator.setVisible(true);  
		animator.animate(AnimationPane.GAME_OVER);
	}
	
	/**
	 * @summary freeze or un-freeze the keyboard 
	 */
	public void toggleFrozen() {
		acceptingInput = !acceptingInput; 
	}
}
