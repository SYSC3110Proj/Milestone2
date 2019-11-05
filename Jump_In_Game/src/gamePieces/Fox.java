package gamePieces;

/** 
 * The fox class is used to keep track of the coordinates and get the direction of a fox. Foxes are constructed with co-ords, direction,
 * and optionally a name.
 * @author Mika Argyle
 */

public class Fox extends Square {
	
	private Direction direction;
	
	/**
	 * Constructor for fox object
	 * @param x The x coordinate of the fox
	 * @param y The y coordinate of the fox
	 * @param direction The direction that the fox is facing
	 * @param name The name of the fox
	 */
	public Fox(int x, int y, Direction direction, String name) {
		super(x,y, name);
		super.setPieceType(PieceType.FOX);
		this.direction = direction;
	}
	
	/**
	 * Constructor for fox object
	 * @param x The x coordinate of the fox
	 * @param y The y coordinate of the fox
	 * @param direction The direction that the fox is facing
	 */
	public Fox(int x, int y, Direction direction) {
		super(x,y);
		this.direction = direction;
	}
	
	/**
	 * Get the direction that the fox is facing
	 * @return The direction that the fox is facing
	 */
	public Direction getDirection() {
		return direction;
	}
	
}
