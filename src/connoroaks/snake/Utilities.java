package connoroaks.snake;

/**
 * @author Connor Oaks
 * @summary this class contains a few useful methods for miscellaneous tasks repeated throughout this program 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import javax.swing.JOptionPane;

public class Utilities {
	private static final Random r = new Random(); 
	private static final int RGB_MAX = 256, GAME_OVER_DELAY = 1000, ES_THREAD_INTERRUPTED = 1;
	
	/** 
	 * @param max - Maximum value for returned integer
	 * @return a random integer range [0, max)
	 */
	public static int randomInteger(int max) {
		return r.nextInt(max); 
	}
	
	/**
	 * @return a random color 
	 */
	public static Color randomColor() {
		return new Color(r.nextInt(RGB_MAX), r.nextInt(RGB_MAX), r.nextInt(RGB_MAX)); 
	}
	
	/**
	 * @summary draw a point on the screen 
	 * @param g - Graphics object
	 * @param pt - The point to be drawn 
	 * @param borderColor - The border color 
	 * @param fillColor - The fill color 
	 */
	public static void drawPoint(Graphics g, Point pt, Color borderColor, Color fillColor) {
		int size = GameBoard.GRID_SQUARE_SIZE;
		g.setColor(fillColor);
		g.fillRect(pt.x * size, pt.y * size, size, size);
		g.setColor(borderColor);
		g.drawRect(pt.x * size, pt.y * size, size, size);
	}
	
	/**
	 * @summary display game over screen
	 * @note the keyboard is frozen for one second after a game ends so the user doesn't accidentally start another game before they intend to 
	 */
	public static void gameOverScreen() {
		Window inst = Main.instance(); 
		inst.toggleFrozen();
		inst.gameOverScreen();
		try {
			Thread.sleep(GAME_OVER_DELAY);		
		} catch(InterruptedException ex) {
			System.err.print(ex);
			// display error message and exit program 
			JOptionPane.showMessageDialog(inst, "JavaSnake has stopped working.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(ES_THREAD_INTERRUPTED);
		}
		inst.toggleFrozen(); 
	}
}
