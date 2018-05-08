package DemonHunter;

public abstract class Enemy extends CaveDweller {
	
	protected Coordinate xyz;
	protected int x;
	protected int y;
	protected int row;
	protected int col;
	protected String type;
	
	public Enemy(Coordinate xyz) {
		super(xyz);
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
		
		int row1 = (int) Math.ceil((double) this.getX()/4);
		return row1;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		
		int col1 = (int) Math.ceil((double) this.getY()/4);
 		return col1;
	}

	public void setCol(int col) {
		this.col = col;
	}

	@Override
	public String toString() {
		return "Enemy [" + xyz + "] ";
	}
	
	




}
