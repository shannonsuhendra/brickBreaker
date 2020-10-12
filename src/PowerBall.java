import java.awt.Color;
import java.awt.Graphics;

public class PowerBall extends Power{
	
	public PowerBall(int pos_x, int pos_y, int v_x, int v_y, int w, int h, boolean down, 
			boolean powerOn, int time) {
		super(pos_x, pos_y, v_x, v_y, w, h, down, powerOn,time);
	}

	@Override
	public void move() {
		setPos_y(getPos_y() + getV_y());
	}
	
	@Override
	public void powerImplementation(Graphics g) {
		if(isPowerOn()) {
			g.setColor(Color.green);
			setTime(getTime()+1);
			showPowerName(g, "Power Ball");
			if(getTime()%200==0) {
				setPowerOn(false);
			}
		}
	}
}
