package DemonHunter;

public class Room {

	private int row;
	private int col;
	private String type;
	private GridMap gridMap;

	public Room(int row, int col) {
		this.row = row;
		this.col = col;

	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean findType(GridMap gridMap, String typeIn) {
		for (int i = ((this.row - 1) * 4); i < (this.row * 4); i++) {
			for (int j = ((this.col - 1) * 4); j < ((this.col) * 4); j++) {

				if (i > 0 && i < gridMap.getRowSize() && j > 0 && j < gridMap.getColSize()) {
					if (i % 4 != 0 && j % 4 != 0) {
						Coordinate c = gridMap.getMap()[i][j];
				

						if (c.getType() == typeIn) {
							return true;
						}
					}
				}

			}
		}

		return false;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Room [row=" + row + ", col=" + col + "]";
	}

}
