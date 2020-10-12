import java.awt.Font;
import java.awt.Graphics;

public abstract class Power extends GameObj{
	private boolean down;
	private boolean powerOn;
	private int time;
	private int fontSize=1;
	
	public Power(int pos_x, int pos_y, int v_x, int v_y, int w, int h, boolean down, 
			boolean powerOn, int time) {
		super(pos_x, pos_y, v_x, v_y, w, h);
		this.setDown(down);
		this.setPowerOn(powerOn);
		this.setTime(time);
	}
	
	public boolean isPowerOn() {
		return powerOn;
	}

	public void setPowerOn(boolean powerOn) {
		this.powerOn = powerOn;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void showPowerName(Graphics g, String powerName) {
		Font messageFont = new Font("Helvetica", Font.BOLD, fontSize);
        g.setFont(messageFont);
        if(getTime() <= 30) {
        	fontSize++;
        	g.drawString(powerName, 150 - fontSize/5, 40);
        }
        if(getTime() <= 70) {
        	g.drawString(powerName, 150 - fontSize/5, 40);
        }
        if(getTime() > 70) {
        	fontSize = 0;
        }
	}
	
	public void draw(Graphics g) {
		g.fillOval(getPos_x(), getPos_y(), getW(), getH());
	}
	
	public void powerMove() {
		setPos_y(getPos_y() + getV_y());
	}
	
	public abstract void powerImplementation(Graphics g);


}
