public abstract class GameObj {
	private int pos_x;
	private int pos_y;
	private int v_x;
	private int v_y;
	private int w;
	private int h;
	
	public GameObj(int pos_x, int pos_y, int v_x, int v_y, int w, int h) {
		this.setPos_x(pos_x);
		this.setPos_y(pos_y);
		this.setV_x(v_x);
		this.setV_y(v_y);
		this.w = w;
		this.h = h;
	}
	
	public int getH() {
		return h;
	}

	public int getPos_x() {
		return pos_x;
	}

	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}

	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	public int getV_x() {
		return v_x;
	}

	public void setV_x(int v_x) {
		this.v_x = v_x;
	}

	public int getV_y() {
		return v_y;
	}

	public void setV_y(int v_y) {
		this.v_y = v_y;
	}
	
	public int getW() {
		return w;
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public boolean intersects(GameObj that) {
        return (this.pos_x + this.w >= that.pos_x
            && this.pos_y + this.h >= that.pos_y
            && that.pos_x + that.w >= this.pos_x 
            && that.pos_y + that.h >= this.pos_y);
    }
	
	public abstract void move();
	
}
