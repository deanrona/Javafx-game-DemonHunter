package DemonHunter;

public class Hunter extends CaveDweller {
	private Coordinate xyz;
	private int x;
	private int y;
	private Room room;
	private int row;
	private int col;
	private String type = "hunter";

	public Hunter(Coordinate xyz) {
		super(xyz);
		this.x = xyz.getX();
		this.y = xyz.getY();
		this.type = xyz.getType();
	}

	// check near by room for a certain type
	public boolean detect(GridMap gridMap, String type1) {

		if (this.getRow() <= 0 || this.getCol() <= 0) {
			// out of bound
			return false;

		} else {
			// northern room
			if (this.getCol() <= 1) {
				// don't check

			} else {
				Room r = new Room(this.getRow(), this.getCol() - 1);

				if (r.findType(gridMap, type1) == true) {
					return true;
				}
			}

			// Eastern room
			if (this.getRow() >= (int) gridMap.getRowSize() / 4) {
				// don't check
			} else {
				Room r = new Room(this.getRow() + 1, this.getCol());

				if (r.findType(gridMap, type1) == true) {
					return true;
				}
			}

			// Southern room
			if (this.getCol() >= (int) gridMap.getColSize() / 4) {
				// don't check
			} else {
				Room r = new Room(this.getRow(), this.getCol() + 1);
				if (r.findType(gridMap, type1) == true) {
					return true;
				}
			}

			// western room
			if (this.getRow() <= 1) {
				// don't check
			} else {
				Room r = new Room(this.getRow() - 1, this.getCol());
				if (r.findType(gridMap, type1) == true) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean shoot(GridMap gridMap, String direction, String type) {

		if (this.getRow() <= 0 || this.getCol() <= 0) {
			// out of bound
			return false;

		} else {

			// northern room
			if (direction == "north") {
				if (this.getCol() <= 1) {
					// don't check

				} else {
					Room r = new Room(this.getRow(), this.getCol() - 1);

					if (r.findType(gridMap, type) == true) {
						return true;
					}
				}
			}

			// Eastern room
			if (direction == "east") {
				if (this.getRow() >= (int) gridMap.getRowSize() / 4) {
					// don't check
				} else {
					Room r = new Room(this.getRow() + 1, this.getCol());

					if (r.findType(gridMap, type) == true) {
						return true;
					}
				}
			}

			// Southern room
			if (direction == "south") {
				if (this.getCol() >= (int) gridMap.getColSize() / 4) {
					// don't check
				} else {
					Room r = new Room(this.getRow(), this.getCol() + 1);
					if (r.findType(gridMap, type) == true) {
						return true;
					}
				}
			}

			// western room
			if (direction == "west") {
				if (this.getRow() <= 1) {
					// don't check
				} else {
					Room r = new Room(this.getRow() - 1, this.getCol());
					if (r.findType(gridMap, type) == true) {
						return true;
					}
				}
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Override
	public String toString() {
		return "Hunter [xyz=" + this.getXyz() + ", x=" + x + ", y=" + y + ", row=" + row + ", col=" + col + ", room="
				+ this.getRoom() + ", type=" + type + "]";
	}

}
