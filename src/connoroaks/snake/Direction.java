package connoroaks.snake;

/**
 * @author Connor Oaks
 * @date October 18, 2016 
 * @summary Enumerated type for the four directions the snake can move.  
 */

public enum Direction { 	
	LEFT(0), UP(1), RIGHT(0), DOWN(1);	
	
	private final int value;	// 0 for horizontal, 1 for vertical
	
	Direction(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}

