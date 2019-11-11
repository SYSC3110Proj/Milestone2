package mvc.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JFrame;

import gamePieces.Direction;
import gamePieces.GridPoint;
import gamePieces.NewFox;
import gamePieces.PieceType;
import gamePieces.Rabbit;
import mvc.view.*;
import gamePieces.Tile;
import gamePieces.TilePlayBoard;

/** 
 * The Controller class creates the Playboard and View objects within a frame that the player interacts with, 
 * it also contains some of the game logic.
 * @author Ruixuan Ni
 * @author Craig Worthington
 * 
 */

public class Controller {
	
	private TilePlayBoard game;
	private View view;
	boolean select;
	private String name;
	
	private Tile sourceTile, destTile;
	
	private GridPoint sourcePoint, destPoint;
	private GridButton sourceButton;
//	private PointSquare sourceSquare, destSquare;
	
	public Controller() {
		this.game = new TilePlayBoard();
		this.view = new View();
		
		this.select = false;
		
		this.view.initButton(game.getBoardName(), new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(game.toString());
				
				// If the player is currently in the selection phase
				if (!select) {
					name = ((GridButton) e.getSource()).getText();
					sourceTile = game.getBoard().getTileAt((((GridButton) e.getSource()).getGridLocation()));
					
					System.out.println(game.getBoard().getTileAt(((GridButton) e.getSource()).getGridLocation()).toString());
					System.out.println(game.getBoard().getTileAt(((GridButton) e.getSource()).getGridLocation()).toString());
					
					sourcePoint = ((GridButton) e.getSource()).getGridLocation();
					sourceButton = (GridButton) e.getSource();
					
					System.out.println("sourceTile = " + sourceTile);
					System.out.println("sourcePoint = " + sourcePoint);
					
					select = true;
				} else {	// If the player is in the movement phase
					if (name != null) {

						// Test if the player is trying to move a hole or mushroom
						if (sourceTile.getToken() != null && sourceTile.getToken().getPieceType() != PieceType.MUSHROOM) {
							
							destTile = game.getBoard().getTileAt(((GridButton) e.getSource()).getGridLocation());
							destPoint = ((GridButton) e.getSource()).getGridLocation();
							
							System.out.println("destTile = " + destTile);
							System.out.println("destPoint = " + destPoint);
							
							if (sourceTile.getToken().getPieceType() == PieceType.RABBIT) {
								
								Rabbit rabbit = (Rabbit) sourceTile.getToken(); // For additional clarity, convert sourceSquare to rabbit
								
								try {
									game.moveRabbit(rabbit, destPoint);
								} catch(IllegalArgumentException exception) {
									System.out.println("Something went wrong!");
									System.err.println(exception);
								}
								
							} else if (sourceTile.getToken().getPieceType() == PieceType.FOX) {
								try {
									NewFox foxToMove = game.getFoxAtLocation(sourceTile.getLocation());
									
									// Check if the user clicked the tail
									if (sourceTile.getLocation().equals(foxToMove.getHead().getLocation())) {
										// Convert the movement into a point that is equivalent
										if (foxToMove.getOrientation() == Direction.NORTH) {
											destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()-1);
										} else if (foxToMove.getOrientation() == Direction.SOUTH) {
											destPoint = new GridPoint(destPoint.getRow(), destPoint.getCol()+1);
										} else if (foxToMove.getOrientation() == Direction.EAST) {
											destPoint = new GridPoint(destPoint.getRow()-1, destPoint.getCol());
										} else if (foxToMove.getOrientation() == Direction.WEST) {
											destPoint = new GridPoint(destPoint.getRow()+1, destPoint.getCol());
										}
									} 
									
									game.moveFox(foxToMove, destPoint);
									
								} catch (IllegalArgumentException error) {
									System.out.println("Something went wrong!");
									System.err.println(error);
								}
							}
							
							// Toggle both buttons to show as off
							sourceButton.setSelected(false);
							((GridButton) e.getSource()).setSelected(false);
							
							view.update(game.getBoardName());
						}
					}
					select = false;
					if (game.checkWinState()) {
						view.popWin();	
					}
				}
			}
		});
	}
	
	//row1, col1 of original position and row2, col2 of destination
	private Direction getDirection(GridPoint source, GridPoint dest) {
		// Check if new destination is in line with the source
		if (source.getRow() != dest.getRow() && source.getCol() != dest.getCol()) {
			return null;
		}
		
		// Check if the destination is the same point as the source
		if (source.equals(dest)) {
			return null;
		}
		
		// If source and dest are on same row
		if (source.getRow() == dest.getRow()) {
			if (source.getCol() > dest.getCol()) {
				return Direction.WEST;
			} else {
				return Direction.EAST;
			}
		} else if (source.getCol() == dest.getCol()) { // If source and dest are in same column
			if (source.getRow() > dest.getRow()) {
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
