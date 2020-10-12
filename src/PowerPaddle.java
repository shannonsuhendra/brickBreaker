import java.awt.Color;
import java.awt.Graphics;

public class PowerPaddle extends Power{
	private int paddleLength;
	private Color paddleColor;
    
	public PowerPaddle(int pos_x, int pos_y, int v_x, int v_y, int w, int h, boolean down, 
			boolean powerOn, int time, int paddleLength) {
		super(pos_x, pos_y, v_x, v_y, w, h, down, powerOn,time);
		this.paddleLength = paddleLength;
	}

	public int getPaddleLength() {
		return paddleLength;
	}

	public void setPaddleLength(int paddleLength) {
		this.paddleLength = paddleLength;
	}

	public Color getPaddleColor() {
		return paddleColor;
	}

	@Override
	public void move() {
		setPos_y(getPos_y()+getV_y());
		
	}
	
	@Override
	public void powerImplementation(Graphics g) {
		if(isPowerOn()) {
			setPaddleLength(140);
			setTime(getTime() + 1);
			g.setColor(Color.red);
			showPowerName(g, "Power Paddle");
			if(getTime() == 250) {
				setPowerOn(false);
				setTime(0);
			}
			paddleColor = Color.red;
		}else {
			setPaddleLength(70);
			paddleColor = Color.gray;
		}
		
	}
}