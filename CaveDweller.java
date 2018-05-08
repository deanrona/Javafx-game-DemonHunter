package DemonHunter;

public abstract class CaveDweller {

	private Coordinate xyz;
	private int x;
	private int y;
	private Room room;
	private int row;
	private int col;
	private String type;

	public CaveDweller(Coordinate xyz) {
		this.xyz = xyz;
		this.x = xyz.getX();
		this.y = xyz.getY();
		this.type = xyz.getType();
	}

	public Coordinate getXyz() {
		return xyz;
	}

	public void setXyz(Coordinate xyz) {
		this.xyz = xyz;
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

	public Room getRoom() {
		Room room1 = new Room(this.xyz.getRow(), this.xyz.getCol());

		return room1;
	}

	public void setRoom(int row, int col) {
		this.room = new Room(row, col);
	}

	@Override
	public String toString() {
		return "CaveDweller [xyz=" + xyz + ", x=" + x + ", y=" + y + ", row=" + row + ", col=" + col + ", room=" + this.getRoom()
				+ ", type=" + type + "]";
	}

}

	
	

