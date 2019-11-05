package mvc;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JFrame;

import gamePieces.Direction;

public class Controller {
	
	private PlayBoard game;
	private View view;
	boolean select;
	private String name;
	private Point sourcePoint, destPoint;
	private GridButton sourceButton;
	
	public Controller() {
		this.game = new PlayBoard();
		this.view = new View();
		
		this.select = false;
		
		this.view.initButton(game.getBoardName(), new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If the player is currently in the selection phase
				if (!select) {
					name = ((JToggleButton)e.getSource()).getText();
					
					sourcePoint = ((GridButton) e.getSource()).getGridLocation();
					sourceButton = (GridButton) e.getSource();
					
					select = true;
				} else {	// If the player is in the movement phase
					if (name != null) {
						if (!name.equals("Hole") && !name.equals("mushroom")) {
							
							destPoint = ((GridButton) e.getSource()).getGridLocation();
						
							if(name.equals("rabbit1") || name.equals("rabbit2") || name.equals("rabbit3")) {
								game.jumpTo(game.getRabbit(name), getDirection(sourcePoint, destPoint));
								
							} else if (name.equals("fox1") || name.equals("fox2")) {
								if (game.getFox(name)[0].getDirection().equals(Direction.HORIZONTAL)) {
									game.moveTo(game.getFox(name), (int) destPoint.getX());
								} else {
									game.moveTo(game.getFox(name), (int) destPoint.getY());
								}
							}
							
							// Toggle both buttons to show as off
							sourceButton.setSelected(false);
							((GridButton) e.getSource()).setSelected(false);
							
							view.update(game.getBoardName());
						}
					}
					select = false;
					if (game.isWin()) {
						view.popWin();	
					}
				}
			}
		});
	}
	
	//row1, col1 of original position and row2, col2 of destination
	private Direction getDirec(int row1, int col1, int row2, int col2) {
		if (row1 != row2 && col1 != col2) {
			return null;
		}
		if (row1 == row2 && col1 == col2) {
			return null;
		}
		if (row1 == row2) {
			if (col1 > col2) {
				return Direction.WEST;
			} else {
				return Direction.EAST;
			}
		} else if(col1 == col2){
			if(row1 > row2) {
				return Direction.NORTH;
			} else {
				return Direction.SOUTH;
			}
		}
		return null;
	}
	
	//row1, col1 of original position and row2, col2 of destination
	private Direction getDirection(Point source, Point dest) {
		// Check if new destination is in line with the source
		if (source.getY() != dest.getY() && source.getX() != dest.getX()) {
			return null;
		}
		
		// Check if the destination is the same point as the source
		if (source.equals(dest)) {
			return null;
		}
		
		// If source and dest are on same row
		if ((int) source.getY() == (int) dest.getY()) {
			if (source.getX() > dest.getX()) {
				return Direction.WEST;
			} else {
				return Direction.EAST;
			}
		} else if ((int) source.getX() == (int) dest.getX()) { // If source and dest are in same column
			if (source.getY() > dest.getY()) {
				return Direction.NORTH;
			} else {
				return Direction.SOUTH;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		Controller con = new Controller();
		
		JFrame frame=new JFrame("Jump-In");
        frame.setLayout(new BorderLayout());
        frame.setBounds(500, 500, 500, 500);
        frame.getContentPane().add(con.view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}
	

}
