import java.awt.Color;
import java.awt.Graphics;

public class PowerFreeze extends Power{
	private int brickUpdate;
	private Color brick1Color;
	private Color brick2Color;
	private Color brick3Color;
    
	public PowerFreeze(int pos_x, int pos_y, int v_x, int v_y, int w, int h, boolean down, 
			boolean powerOn, int time) {
		super(pos_x, pos_y, v_x, v_y, w, h, down, powerOn,time);
	}

	public int getBrickUpdate() {
		return brickUpdate;
	}

	public void setBrickUpdate(int brickUpdate) {
		this.brickUpdate = brickUpdate;
	}

	public Color getBrick1Color() {
		return brick1Color;
	}

	public Color getBrick2Color() {
		return brick2Color;
	}

	public Color getBrick3Color() {
		return brick3Color;
	}

	@Override
	public void move() {
		setPos_y(getPos_y()+getV_y());
		
	}
	
	@Override
	public void powerImplementation(Graphics g) {
		if(isPowerOn()) {
			setBrickUpdate(0);
			setTime(getTime() + 1);
			g.setColor(Color.blue);
			showPowerName(g, "Power Freeze");
			brick3Color = new Color(0,60,255);
            brick2Color = new Color(75,118,255);
            brick1Color = new Color(129,159,255);
			if(getTime() % 200 == 0) {
				setPowerOn(false);
			}
		}else {
			setBrickUpdate(3);
			brick3Color = new Color(204,136,0);
            brick2Color = new Color(230,191,0);
            brick1Color = new Color(230,230,0);
			
		}
		
	}

}
