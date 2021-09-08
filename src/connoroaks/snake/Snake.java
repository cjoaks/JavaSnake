package connoroaks.snake;

/**
 * @author Connor Oaks 
 * @summary this class contains code for the snake itself
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Snake {
	/**
	 * @summary this class contains code for the nodes that make up the snake
	 */
	private class Node extends Point {
		private static final long serialVersionUID = 1L;	// Why eclipse ?
		
		private Node next;
		
		public Node(int x, int y) {
			super(x, y); 
			next = null; 
		}
		
		/**
		 * Draws the node on the graphics object passed to it.
		 * @param g - The Graphics object passed in from JPanel paintComponent  
		 */
		public void draw(Graphics g) {
			Utilities.drawPoint(g, this, border, fill);
		}
	}
	 
	private Node head, tail; 
	private final Color border, fill; 
	private Direction direction; 
	
	public Snake() {
		int center = GameBoard.GRID_SIZE / 2;
		head = new Node(center, center); 
		tail = head; 
		border = Utilities.randomColor(); 
		fill = Utilities.randomColor();
	}
	
	/**
	 * @summary advance the snake forward
	 * @param add - Whether to add to the size of the snake. True when the snake gets food 
	 */
	public void advance(boolean add) {
		 Node next = (Node)head.clone();
		 switch(direction) {
		 	case UP:    --next.y; break; 
		 	case DOWN:  ++next.y; break;
		 	case LEFT:  --next.x; break; 
		 	case RIGHT: ++next.x; break; 
		 }
		 head.next = next; 
		 head = next;
		 if(!add) tail = tail.next; 
	}
	
	/**
	 * @return the location of the head of the snake. 
	 */
	public Point head() {
		return head; 
	}

	/**
	 * @summary determine if a given point is part of the snake 
	 * @param pt - The node to test
	 * @param includeHead - True if the head should be included in the comparison
	 * @return whether the given point exists as part of the snake
	 */
	public boolean occupies(Point pt, boolean includeHead) {
		for(Node n = tail; n.next != null; n = n.next) {
			if(n.equals(pt) && (n != head || includeHead)) return true;
		}
		return false;
	}
	
	/**
	 * @return the direction the snake is currently moving
	 */
	public Direction getDirection() {
		return direction; 
	}
	
	/**
	 * @param currentDirection - the new direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * @summary draw the snake on the screen 
	 * @param g - Graphics object passed in from the game board
	 */
	public void draw(Graphics g) {
		for(Node n = tail; n != null; n = n.next) {
			n.draw(g);
		} 
	}
	
	/**
	 * @return the border color of the snake
	 */
	public Color borderColor() {
		return border;
	}

	/**
	 * @return the fill color of the snake
	 */
	public Color fillColor() {
		return fill;
	}
}

