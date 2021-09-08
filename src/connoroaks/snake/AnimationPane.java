package connoroaks.snake;

/**
 * @author Connor Oaks
 * @summary this class contains code for the start screen / game over screen. 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point; 
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AnimationPane extends JPanel {
	private static final long serialVersionUID = 1L; // Eclipse be like 
	
	/**
	 * @summary type that can be drawn by the outer AnimationPane class. 
	 */
	private static class Animation {
		private static final int X = 0, Y = 1; 
		private final int[][] data; 
		private final int length;
		private Point current;
		private int index; 
		
		public Animation(int[][] data) {
			this.data = data; 
			length = data[X].length; 
			this.current = new Point(); 
			index = 0; 
		}
		
		/**
		 * @summary reset index to 0, starting the animation over
		 */
		public void start() {
			index = 0; 
		}
		
		/**
		 * @summary draw the previous points up to and including the current one 
		 * @param g - Graphics passed from JPanel 
		 */
		public void drawNext(Graphics g) {
			for(int i = 0; i < index; ++i) {
				this.current.x = data[X][i];
				this.current.y = data[Y][i]; 
				Utilities.drawPoint(g, this.current, Color.BLACK, Color.GREEN);
			}
			if(!this.isOver()) ++index;
		}
		
		/**
		 * @return whether this animation is over. 
		 */
		public boolean isOver() {
			return index >= length; 
		}
	}
	
	public static final Animation START = new Animation(new int[][] {	
	/* X */ {5,4,3,2,2,2,2,3,4,5,5,5,5,4,3,2,7,7,7,7,7,7,7,8,8,9,9,10,10,11,12,12,12,12,12,12,12,
				14,14,14,14,14,14,14,15,16,17,17,17,17,17,17,17,15,16,19,19,19,19,19,19,19,20,21,
				22,23,21,22,23,28,27,26,25,25,25,25,25,25,25,26,27,28,26,27},
	/* Y */ {12,12,12,12,13,14,15,15,15,15,16,17,18,18,18,18,18,17,16,15,14,13,12,12,13,14,15,16,
				17,18,18,17,16,15,14,13,12,18,17,16,15,14,13,12,12,12,12,13,14,15,16,17,18,14,14,
				18,17,16,15,14,13,12,15,14,13,12,16,17,18,18,18,18,18,17,16,15,14,13,12,12,12,12,15,15}
	}); 
	public static final Animation GAME_OVER = new Animation(new int[][] {
	/* X */	{8,7,6,5,5,5,5,5,6,7,8,8,8,7,10,10,10,10,10,11,12,13,13,13,13,13,11,12,15,15,15,15,15,
				16,17,18,19,19,19,19,19,17,17,17,24,23,22,21,21,21,21,21,22,23,24,22,23,5,5,5,5,5,
				6,7,8,8,8,8,8,7,6,10,10,10,10,11,11,12,12,13,13,13,13,13,18,17,16,15,15,15,15,15,
				16,17,18,16,17,20,20,20,20,20,21,22,23,23,23,22,21,22,23},
	/* Y */	{9,9,9,9,10,11,12,13,13,13,13,12,11,11,13,12,11,10,9,9,9,9,10,11,12,13,11,11,13,12,
				11,10,9,9,9,9,9,10,11,12,13,10,11,12,13,13,13,13,12,11,10,9,9,9,9,11,11,15,16,17,
				18,19,19,19,19,18,17,16,15,15,15,15,16,17,18,18,19,19,18,18,17,17,16,15,15,15,15,
				15,16,17,18,19,19,19,19,17,17,19,18,17,16,15,15,15,15,16,17,17,17,18,19}
	}); 
	private static final long DELAY = 300, INTERVAL = 40;
	private ScheduledFuture<?> scheduler;
	private Animation current;  
	
	public AnimationPane() {
		JLabel instructions = new JLabel("Press \u2190 for easy, \u2191 for medium, \u2192 for hard, or \u2193 for turbo.");
		instructions.setFont(new Font("Arial", Font.PLAIN, 20));
		instructions.setForeground(Color.YELLOW);
		this.add(instructions);
		this.setBackground(Color.BLACK);
	}
	
	/**
	 * @summary draw start/game over screen
	 * @param animation - which animation to display. 
	 */
	public void animate(Animation animation) {
		current = animation; 
		current.start();
		scheduler = Executors.newSingleThreadScheduledExecutor()
				.scheduleAtFixedRate(this::drawNext, DELAY, INTERVAL, TimeUnit.MILLISECONDS); 
	}
	
	/**
	 * @summary helper method called by scheduler
	 */
	private void drawNext() {
		this.repaint();
		if(current.isOver()) this.stop();
	}
	
	/**
	 * @summary stop an animation 
	 */
	public void stop() {
		scheduler.cancel(true); 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		current.drawNext(g);
	}
	
	@Override
	public java.awt.Dimension getPreferredSize() {
		return Window.PREFERRED_GAME_AREA_SIZE;
	}
		
}
