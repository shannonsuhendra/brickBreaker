public class Ball extends GameObj{
	
	public Ball(int pos_x, int pos_y, int v_x, int v_y, int w, int h) {
		super(pos_x, pos_y, v_x, v_y, w, h);
	}

	@Override
	public void move() {
		setPos_x(getPos_x() + getV_x());
		setPos_y(getPos_y() + getV_y());
	}
	
	public void flipYDirection() {
		setV_y(getV_y() * -1);
	}
	
	public void flipXDirection() {
		setV_x(getV_x() * -1);
	}
	
	public void flipDirection() {
		setV_y(getV_y() * -1);
		setV_x(getV_x() * -1);
	}
}
