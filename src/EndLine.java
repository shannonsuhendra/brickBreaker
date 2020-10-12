public class EndLine extends GameObj{

	public EndLine(int pos_x, int pos_y, int v_x, int v_y, int w, int h) {
		super(pos_x, pos_y, v_x, v_y, w, h);
	}

	@Override
	public void move() {
		setV_x(0);
		setV_y(0);
	}
}
