public class Brick extends GameObj{
	private boolean showBrick;
	
	public Brick(int pos_x, int pos_y, int v_x, int v_y, int w, int h, boolean showBrick) {
		super(pos_x, pos_y, v_x, v_y, w, h);
		this.setShowBrick(showBrick);
	}

	@Override
	public void move() {
		setV_x(0);
		setV_y(0);
	}

	public boolean isShowBrick() {
		return showBrick;
	}

	public void setShowBrick(boolean showBrick) {
		this.showBrick = showBrick;
	}

}
