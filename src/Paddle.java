
public class Paddle extends GameObj{
	private boolean padLeft;
	private boolean padRight;

	public Paddle(int pos_x, int pos_y, int v_x, int v_y, int w, int h, boolean padLeft, 
			boolean padRight) {
		super(pos_x, pos_y, v_x, v_y, w, h);
		this.setPadLeft(padLeft);
		this.setPadRight(padRight);
	}

	public boolean isPadLeft() {
		return padLeft;
	}

	public void setPadLeft(boolean padLeft) {
		this.padLeft = padLeft;
	}

	public boolean isPadRight() {
		return padRight;
	}

	public void setPadRight(boolean padRight) {
		this.padRight = padRight;
	}
	
	@Override
	public void move() {
		if(isPadLeft()) {
			setPos_x(getPos_x() - getV_x());
		}
		if(isPadRight()) {
			setPos_x(getPos_x() + getV_x());
		}
		
	}
	
	

}
