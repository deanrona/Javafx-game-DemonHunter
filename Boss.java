package DemonHunter;

import java.util.Random;

public class Boss extends CaveDweller {
	private Coordinate xyz;
	private int x;
	private int y;
	private Room room;
	private int row;
	private int col;
	private String type = "boss";

	public Boss(Coordinate xyz) {
		super(xyz);
		this.x = xyz.getX();
		this.y = xyz.getY();
		this.type = xyz.getType();
	}

	public boolean roam(GridMap gridMap, Hunter theHunter, boolean alert) {

		if (gridMap.getMap()[this.getX()][this.getY()].getType() != "trap") {

			if (alert == true) {
				// leave remains when alert == true
				gridMap.getMap()[this.getX()][this.getY()] = new Coordinate(this.getX(), this.getY(), "remainsFog");
			}
		}

		if (alert == true) {
			
//			this.setX(this.getX() + 4);
//			gridMap.getMap()[this.getX() + 4][this.getY()].setType("boss");
			
			if(this.getRoom() == theHunter.getRoom()) {
				return true;
			}

			int r = new Random().nextInt(100);
			int moved = 0;

			// while (moved != 1) {
			// go right
			if (r < 25) {

				if ((this.getX() + 4) < gridMap.getRowSize()) {
					this.setX(this.getX() + 4);
					// make change in the map as well
					if (gridMap.getMap()[this.getX()][this.getY()].getType() != "trap") {

						gridMap.getMap()[this.getX()][this.getY()].setType("boss");
					}
					moved = 1;
					

				}

			} // go left
			else if (r > 25 && r < 50) {
				if ((this.getX() - 4) > 1) {
					this.setX(this.getX() - 4);
					// make change in the map as well
					if (gridMap.getMap()[this.getX()][this.getY()].getType() != "trap") {

						gridMap.getMap()[this.getX()][this.getY()].setType("boss");
					}
					moved = 1;
					

				}

			} // go up
			else if (r > 50 && r < 75) {
				if ((this.getY() - 4) > 1) {
					this.setY(this.getY() - 4);
					// make change in the map as well
					if (gridMap.getMap()[this.getX()][this.getY()].getType() != "trap") {

						gridMap.getMap()[this.getX()][this.getY()].setType("boss");
					}
					moved = 1;
					
				}

			} // go down
			else {
				if ((this.y + 4) < gridMap.getColSize()) {
					this.setY(this.getY() + 4);
					// make change in the map as well
					if (gridMap.getMap()[this.getX()][this.getY()].getType() != "trap") {

						gridMap.getMap()[this.getX()][this.getY()].setType("boss");
					}
					moved = 1;
					
				}
			}

			if (moved != 1) {
				roam(gridMap, theHunter, alert);
			}

		}
		
		return false;

	}



	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRow() {

		row = (int) Math.ceil((double) this.getX() / 4);
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {

		col = (int) Math.ceil((double) this.getY() / 4);
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Room getRoom() {
		this.room = new Room(this.getRow(), this.getCol());

		return room;
	}

	public void setRoom(int row, int col) {
		this.room = new Room(row, col);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Boss [xyz=" + this.getXyz() + ", x=" + x + ", y=" + y + ", room=" + this.getRoom() + ", row=" + row
				+ ", col=" + col + ", type=" + type + "]";
	}

}
