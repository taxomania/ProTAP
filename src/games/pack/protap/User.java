/**
 * 
 */
package games.pack.protap;

/**
 * @author Tariq Patel
 * @version 1.0.1
 * 
 * User object class to create instances of each player in the game.
 */
public class User {
	private final int id;
	private int score;
	public static int players = 0;
	
	public User(int idNo){
		id = idNo;
		score = 0;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	
}
