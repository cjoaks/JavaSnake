package connoroaks.snake;

/**
 * @author Connor Oaks
 * @date October 18, 2016
 * @summary this class is the entry point of the JavaSnake application. 
 */

public class Main
{
	private static Window instance; 
	
	public static void main(String[] args) {
		instance = new Window(); 
	}
	
	public static Window instance() {
		return instance; 
	}
}

