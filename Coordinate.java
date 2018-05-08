package DemonHunter;

public class Coordinate {
	private int x;
	private int y;
	private String type;
	private int row;
	private int col;
	
	
	public Coordinate(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.type = type;

	}

	public int getRow() {

		int row1 = (int) Math.ceil((double) this.getX() / 4);
		return row1;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {

		int col1 = (int) Math.ceil((double) this.getY() / 4);
		return col1;
	}

	public void setCol(int col) {
		this.col = col;
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

	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + ", type=" + type + "]";
	}

}
