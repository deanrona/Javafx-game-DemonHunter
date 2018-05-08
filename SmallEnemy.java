package DemonHunter;

public class SmallEnemy extends Enemy{
	private int x;
	private int y;
	private int xyz;
	private String type;
	private int row;
	private int col;

	public SmallEnemy(Coordinate xyz) {
		super(xyz);
		this.x = xyz.getX();
		this.y = xyz.getY();
		this.type = xyz.getType();
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
	
	
	
	
	

}
