package connoroaks.snake;

/**
 * @author Connor Oaks
 * @date October 18, 2016
 * @summary enumerated type for the difficulties associated with the snake game.
 */

public enum Difficulty {
	EASY(110), MEDIUM(80), HARD(65), TURBO(40);
	
	private final int interval; // the number of milliseconds for the rate at which the snake advances its position 
	
	Difficulty(int interval) {
		this.interval = interval;
	}
	
	public int getInterval() {
		return interval;
	}
}
