import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BrickBoard extends JPanel implements Runnable{
	
	//---GAME SCREENS---
	boolean startScreen = true;
	boolean ingame = false;
	boolean endScreen = false;
	boolean runOnce = false;
	boolean instructions = false;
	
	private Dimension d;
	private Thread animator;
	
	//---BOARD SIZE---
	int Board_W = 500;
	int Board_H = 500;
	
	//---POWERS---
	int time = 0;
	int powerW = 10;
	int powerS = 2;
	int choosePower = 1;
		//power paddle 
	int paddleW = 70;
	int powerPX = 200;
	int powerPY = 0;
	boolean powerPDown = false;
	boolean powerPON = false;
	PowerPaddle powerP = new PowerPaddle(powerPX, powerPY, 0, powerS, powerW, 
			powerW, powerPDown, powerPON, time, paddleW);
		//power freeze
	int powerFX = 400;
	int powerFY = 0;
	boolean powerFDown = false;
	boolean powerFON = false;
	int brickUpdate = 3;
	PowerFreeze powerF = new PowerFreeze(powerFX, powerFY, 0, powerS, powerW, 
			powerW, powerFDown, powerFON, time);
		//power ball
	int powerBX = 300;
	int powerBY = 0;
	boolean powerBDown = false;
	boolean powerBON = false;
	PowerBall powerB = new PowerBall(powerBX, powerBY, 0, powerS, powerW, powerW, 
			powerBDown, powerBON, time);
	//timer for next powerUp to go down
	int timer = 0;

	//---PADDLE---
	int paddleX = Board_W/2-25;
	boolean runOncePaddle = true;
	Paddle pad = new Paddle(paddleX, Board_H-50, 5, 5, paddleW, 10, false, false);
	
	//---BRICKS---
	//layers of bricks
	Brick[][] bricks1 = new Brick[6][12];
	Brick[][] bricks2 = new Brick[4][12];
	Brick[][] bricks3 = new Brick[2][12];
	//timer for brick to move down screen
	int brickTimer = 0;
	
	//---BALL---
	//start position of the multiple ball
    int restartPosX=20;
    //array list of the ball
	ArrayList<Ball> blist = new ArrayList<Ball>();
	//array list of balls that go past bottom screen
	ArrayList<Ball> removeBall = new ArrayList<Ball>();
	//create multiple ball
	Ball ball1 = new Ball(20, 200, 4, 5, 10, 10);
	Ball ball2 = new Ball(50, 200, 4, 5, 10, 10);
	Ball ball3 = new Ball(70, 200, 4, 5, 10, 10);
	Ball ball4 = new Ball(90, 200, 4, 5, 10, 10);
	Ball ball5 = new Ball(110, 200, 4, 5, 10, 10);
	Ball lastBall = ball1;
	
	//---ENDLINE---
	EndLine line = new EndLine(0, 200, 0, 0, 500, 0);
		
	//---LIFE---
	int life = 3;
	
	//---SCORES---
	int points = 0;
	int highScore = 0;
	int prevHighScore = 0;
		
	//---VARIABLES FOR ENDSCREEN---
		//player name
	String name = "";
		//message of final score
	String congratulations = "";
	String Endmessage = "";
	int msgPosn = 0;
		//gets score file
	File file = new File("scores.txt");;
		//gets all data from scores.txt
	List<Data> data = retrieveData();
	Font messageFont;
	String instruction = "";
	
	
	public BrickBoard() {
		addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(Board_W, Board_H);
        setBackground(Color.black);
        
            if (animator == null || !ingame) {
            animator = new Thread(this);
            animator.start();
            }
                    
  
        setDoubleBuffered(true);
        
        //sets up brick position
        brickPosition(bricks1);
        brickPosition(bricks2);
        brickPosition(bricks3);
        
        //game starts with 1 ball
        blist.add(ball1);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(startScreen) {
			//display game title
            g.setColor(Color.white);
            changeFont(20, g);
            g.setFont(messageFont);
            g.drawString("Brick Breaker", 180, 200);
            //display instructions
            changeFont(15, g);
            g.setFont(messageFont);
            g.drawString("Press Space To Start", 177, 220);
            g.drawString("Press Enter To See Instructions", 150, 240);
		}
		if(instructions) {
			//displays instructions
			g.setColor(Color.white);
            changeFont(20, g);
            g.setFont(messageFont);
            g.drawString("Welcome To Brick Breaker", 120, 95);
            changeFont(15, g);
            g.setFont(messageFont);
            g.drawString("Instructions:", 20, 120);
            g.fillOval(25, 135, 3, 3);
            g.drawString("Destroy the bricks with the ball", 35, 140);
            g.fillOval(25, 155, 3, 3);
            g.drawString("Each brick hit is worth 2 points", 35, 160);
            g.fillOval(25, 175, 3, 3);
            g.drawString("Move the paddle with the left and right arrow keys to keep the ball"
            		, 35, 180);
            g.drawString("from going off the screen or else you will lose a life", 35, 200);
            g.fillOval(25, 215, 3, 3);
            g.drawString("Power-Ups: ", 35, 220);
            g.drawString("Freeze Bricks ", 90, 240);
            g.drawString("Paddle Length Increase ", 90, 260);
            g.drawString("Multiple Balls ", 90, 280);
            g.fillOval(25, 295, 3, 3);
            g.drawString("Clear all the bricks and WIN", 35, 300);
            g.fillOval(25, 315, 3, 3);
            g.drawString("You have 3 lives, lose them all and it's GAME OVER", 35, 320);
            g.fillOval(25, 335, 3, 3);
            g.drawString("Brick touches the line and it's GAME OVER", 35, 340);
            g.drawString("Press Space To Start", 170, 365);
            g.setColor(Color.blue);
            g.fillOval(70, 230, 10, 10);
            g.setColor(Color.red);
            g.fillOval(70, 250, 10, 10);
            g.setColor(Color.green);
            g.fillOval(70, 270, 10, 10);
		}
		if(ingame) {
			//display score
            changeFont(20, g);
            g.setColor(Color.white);
            g.setFont(messageFont);
            g.drawString("Score: " + String.valueOf(points), 10, 20);
            //display life
            String lifeMessage = "";
            for(int i = 0; i < life; i++){
                lifeMessage = lifeMessage + ". ";
            }
            g.drawString(lifeMessage,10,450);
            //---ENDLINE---
            g.setColor(Color.white);
            changeFont(10, g);
            g.setFont(messageFont);
            g.drawString("END LINE", 230, 199);
            g.drawLine(line.getPos_x(), line.getPos_y(), line.getPos_x()+line.getW(), 
            		line.getPos_y()+line.getH());
            
            //---BRICKS---
                //1st layer brick
            g.setColor(powerF.getBrick1Color());
            setBricks(g, bricks1);
                //2nd layer brick
            g.setColor(powerF.getBrick2Color());
            setBricks(g, bricks2);
                //3rd layer brick
            g.setColor(powerF.getBrick3Color());
            setBricks(g, bricks3);
            //power freeze brick
            powerF.powerImplementation(g);
            brickUpdate = powerF.getBrickUpdate();
            if(powerF.isDown()) {
            	g.setColor(Color.blue);
                powerF.draw(g);
                powerF.powerMove();
                powerUp(powerF);
            }
            brickTimer++;
            if(brickTimer%100==0) {
            	updateBrickPosition(bricks1);
                updateBrickPosition(bricks2);
                updateBrickPosition(bricks3);
            }
            touchLine(bricks1);
            touchLine(bricks2);
            touchLine(bricks3);
            
            //---PADDLE---
            pad.setW(powerP.getPaddleLength());
            paddleX = pad.getPos_x() - pad.getW()/2;
            if (pad.getPos_x() + pad.getW() > 500) {
            	pad.setPos_x(500 - pad.getW());
            }
            if(pad.getPos_x() < 0) {
            	pad.setPos_x(0);
            }
            g.setColor(powerP.getPaddleColor());
            g.fillRect(pad.getPos_x(), pad.getPos_y(), pad.getW(), pad.getH());
            pad.move();
            //power paddle 
            powerP.powerImplementation(g);
            powerPX = powerP.getPos_x();
            powerPY = powerP.getPos_y();
            if(powerP.isDown()) {
            	g.setColor(Color.red);
                powerP.draw(g);
                powerP.powerMove();
                powerUp(powerP);
            }
            if(powerP.isPowerOn()) {
            	if(runOncePaddle) {
            		pad.setPos_x(pad.getPos_x()-pad.getW()/2);
            		runOncePaddle = false;
            	}
            }else{
            	runOncePaddle = true;
            }
            
            //---BALL---
            /*goes through blist and for each ball, draw circle, move the ball, bounce the ball and 
            check if it hits bricks*/
            for(Ball b: blist) {
            	g.setColor(Color.white);
            	g.fillOval(b.getPos_x(), b.getPos_y(), b.getW(), b.getH());
            	b.move();
            	bounceBall(b);
            	showBricks(bricks1,bricks2,bricks3, b);
            }
            /*if removeBall size more than 0, remove ball (in removeBall) from blist 
            and clear removeBall*/
            if(removeBall.size() > 0) {
            	for(Ball b: removeBall) {
            		resetBallDirection(b);
            		blist.remove(b);
            		b.setPos_x(restartPosX);
            		b.setPos_y(200);
            		if(b.getPos_x() == 100) {
            			lastBall = b;
            		}
            		restartPosX+=20;
            	}
            	removeBall.clear();
            }
            //reset starting position of multiple ball to 20
            if(blist.size() == 1) {
            	restartPosX = 20;
            	//powerB.setPowerOn(false);
            }else if(blist.size() == 0) {
            	lastBall.setPos_x(20);
               	lastBall.setPos_y(200);
               	blist.add(lastBall);
               	//powerB.setPowerOn(false);
            }
            //if ball intersect each other, bounce off
            for(Ball b1 : blist) {
            	for(Ball b2 : blist) {
            		if(b1.intersects(b2)){
                        b1.flipDirection();
                        b2.flipDirection();              
                    }
            	}
            }
            //power ball 
            powerB.powerImplementation(g);
            if(powerB.isDown()) {
            	g.setColor(Color.green);
                powerB.draw(g);
                powerB.powerMove();
                powerUp(powerB);
            }
            if(powerB.isPowerOn()) {
            	if(!blist.contains(ball1)) {
            		blist.add(ball1);
            	}
            	if(!blist.contains(ball2)) {
            		blist.add(ball2);
            	}
            	if(!blist.contains(ball3)) {
            		blist.add(ball3);
            	}
            	if(!blist.contains(ball4)) {
            		blist.add(ball4);
            	}
            	if(!blist.contains(ball5)) {
            		blist.add(ball5);
            	}
            	
            }

            //increments timer for next power to show up and choose randomly which power to go down 
            if(!powerP.isPowerOn() || !powerF.isPowerOn() || !powerB.isPowerOn()) {
            	timer++;
            }
            if(timer%500==0 && timer != 0) {
            	int random = (int)(Math.random() * 4 + 1)*100;
            	int random2 = (int)(Math.random() * 3 + 1);
            	choosePower = random2;
            	if(choosePower == 1) {
            		powerP.setDown(true);
            		powerP.setPos_x(random);
            	}else if(choosePower == 2){
            		powerF.setDown(true);
            		powerF.setPos_x(random);
            	}else{
            		if(blist.size() == 1) {
            			powerB.setDown(true);
                		powerB.setPos_x(random);
            		}
            	}
            }
            if(points == 288) {
            	ingame = false;
            	endScreen = true;
            }
		}
			if(endScreen){
	            g.setColor(Color.white);
	            //game over
	            changeFont(30, g);
	            g.setFont(messageFont);
	            int x = 160;
	            Endmessage = "GAME OVER";
	            prevHighScore = getHighestScore(data);
	            //get message depending on current score
	            if(prevHighScore < points){
	                congratulations = "Congratulations! You Scored the Highest Score!";
	                msgPosn = 20;
	            }
	            if(prevHighScore == points) {
	            	congratulations = "Congratulations! You are tied in the 1st place!";
	            	msgPosn = 20;
	            }
	            if(prevHighScore > points) {
	            	congratulations = "Better luck next time!";
	            	msgPosn = 100;
	            }
	            if(points == 288 && prevHighScore <= points) {
	            	congratulations = "Congratulations! YOU WIN!";
	            	msgPosn = 80;
	            	Endmessage = "YOU WIN!";
	            	x = 190;
	            }
	            g.drawString(Endmessage, x, 100);
	            //display score
	            changeFont(15, g);
	            g.setFont(messageFont);
	            highScore = getHighestScore(data);
	            if (points > highScore){
	            	highScore = points;
	            }
	            g.drawString("Score: " + points, 160, 120);
	            g.drawString("High Score: " + highScore, 240, 120);
	            //display instruction
	            g.drawString(instruction, 173, 300);
	            //only calls simpleJButton once
	            if(runOnce) {
	            	new SimpleJButton();
	            }
	            runOnce = false;
	            //display top 5 scores
	            showLeaderBoard(data, g);
	        }
		
		
	}
	
	public void changeFont(int size, Graphics g) {
		messageFont = new Font("TimesRoman", Font.BOLD, size);
	}
	
	//----------------------------------BALL METHODS-----------------------------------
	//reset position of multiple balls
	public void resetBallDirection(Ball b) {
		if(b.getV_x()<0) {
			b.flipXDirection();
		}if(b.getV_y()<0) {
			b.flipYDirection();
		}
	}
	
	//if ball hits left/right of board and paddle, change direction of ball
	public void bounceBall(Ball b) {
		//ball hits left/right of board
		if(b.getPos_x() + b.getW() > Board_W || b.getPos_x() < 0) {
			b.flipXDirection();
		}
		//ball hits top of board
		if(b.getPos_y() < 0) {
			b.flipYDirection();
		}
		//ball falls beyond paddle, lose a life
		if(b.getPos_y() > pad.getPos_y()){
	        if(blist.size()==1) {
	        	life-=1;
	        	b.setPos_x(20);
		        b.setPos_y(200);
	        }else {
	        	removeBall.add(b);
	        }
	        if (life == 0){
	            ingame = false;
	            endScreen = true;
	            runOnce = true;
	        }
	    }      
		if(b.intersects(pad)) {
			b.flipYDirection();
		}
	}
	
	//----------------------------------LINE METHODS-----------------------------------
	//if brick touches line, end game
	public void touchLine(Brick[][] brick) {
		for(int i = 0; i < brick.length; i++) {
			for(int j = 0; j < brick[0].length; j++) {
				if(brick[i][j].isShowBrick() && 
						brick[i][j].getPos_y() + brick[i][j].getH() >= line.getPos_y()) {
					ingame = false;
		            endScreen = true;
		            runOnce = true;
				}
			}
		}
	}
	
	//----------------------------------POWER METHODS-----------------------------------
	//checks if player catches the power up 
	public void powerUp(Power p) {
		if(p.intersects(pad)) {
			p.setPowerOn(true);
			restartPowerUp(p);
		}if(p.getPos_y() > pad.getPos_y()){
			restartPowerUp(p);
		}
	}
	
	//sets back the power ball position to top of screen for next power up to go down
	public void restartPowerUp(Power p) {
		p.setDown(false);
		p.setPos_x(powerPX);
		p.setPos_y(0);
		timer = 0;
	}
		
	//----------------------------------BRICK METHODS-----------------------------------
	//sets up the brick position
	public void brickPosition(Brick[][] bricks) {
		//starting position of 1st brick
		int xx = 12;
		int yy = 40;
		for(int i = 0; i < bricks.length; i++) {
			for(int j = 0; j < bricks[0].length; j++) {
				//brick v_x=0 v_y=0 width=38 height=10
				bricks[i][j] = new Brick(xx, yy, 0, 0, 38, 10, true);
				//space=2 between bricks
				xx+=40;
			}
			xx=12;
			yy+=12;
		}
	}
	//moves the brick down the screen
	public void updateBrickPosition(Brick[][] bricks) {
		for(int i = 0; i < bricks.length; i++) {
			for(int j = 0; j < bricks[0].length; j++) {
				bricks[i][j].setPos_y(bricks[i][j].getPos_y()+brickUpdate);
			}
		}
	}
	
	//draws the brick on screen
	public void setBricks(Graphics g, Brick[][] bricks) {
		for(int i = 0; i < bricks.length; i++) {
			for(int j = 0; j < bricks[0].length; j++) {
				if(bricks[i][j].isShowBrick()) {
					g.fillRect(bricks[i][j].getPos_x(), bricks[i][j].getPos_y(), 
							bricks[i][j].getW(), bricks[i][j].getH());
				}
			}
		}
	}
	
	//checks if ball hits 1st, 2nd or 3rd layer of brick 
	public void showBricks(Brick[][] bricks1, Brick[][] bricks2, Brick[][] bricks3, Ball b) {
		for(int i = 0; i < bricks3.length; i++) {
			for(int j = 0; j < bricks3[0].length; j++) {
				if(!bricks3[i][j].isShowBrick()) {
					if(!bricks2[i][j].isShowBrick()) {
						if(bricks1[i][j].isShowBrick() && b.intersects(bricks1[i][j])) {
							update(bricks1, i, j, b);
						}
					}
					if(bricks2[i][j].isShowBrick() && b.intersects(bricks2[i][j])) {
						update(bricks2, i, j, b);
					}
				}
				if(bricks3[i][j].isShowBrick() && b.intersects(bricks3[i][j])) {
					update(bricks3, i, j, b);
				}
			}
		}
		for(int i = bricks3.length; i < bricks2.length; i++) {
			for(int j = 0; j < bricks2[0].length; j++) {
				if(!bricks2[i][j].isShowBrick()) {
					if(bricks1[i][j].isShowBrick() && b.intersects(bricks1[i][j])) {
						update(bricks1, i, j, b);
					}
				}
				if(bricks2[i][j].isShowBrick() && b.intersects(bricks2[i][j])) {
					update(bricks2, i, j, b);
				}
			}
		}
		for(int i = bricks2.length; i < bricks1.length; i++) {
			for(int j = 0; j < bricks1[0].length; j++) {
				if(bricks1[i][j].isShowBrick() && b.intersects(bricks1[i][j])){
					update(bricks1, i, j, b);
				}
			}
		}
	}
	
	//when ball hits brick, brick disappears, add a point and change direction of ball
	public void update(Brick[][] bricks, int r, int c, Ball b) {
		bricks[r][c].setShowBrick(false);
		b.flipYDirection();
		points+=2;
	}
	
	//-----------------------READING AND WRITING NAME & SCORE METHODS-------------------
	//gets all scores from scores.txt file
	private List<Data> retrieveData(){
		List<Data> arr = new ArrayList<Data>();
	    try {
	    	BufferedReader reader = new BufferedReader(new FileReader(file));
		    String line = reader.readLine();
		    while(line != null) { 
		    	String[] data = line.split(", ");
				for(int i = 0; i < data.length; i+=2) {
					String name = data[i].substring(1);
					int score = Integer.parseInt(data[i+1].substring(0, data[i+1].length()-1));
					arr.add(new Data(name, score));
				}
		    	line = reader.readLine();
		    }
		    reader.close();
	    }catch(IOException e){
	    	System.out.println(e.getMessage());
	    }
		
	    return arr;
	}
	
	//store current data at index according to score (to store in descending order)
	private void addNewData(String name, int finalScore, List<Data> data) {
	    boolean scorePosition = false;
	    int i = 0;
	    while (!scorePosition && i < data.size()) {
	        if (finalScore >= data.get(i).getScore()) {
	            scorePosition = true;
	        }else {
	        	i++;
	        }
	    }
	    data.add(i, new Data(name, finalScore));
	}
	
	//writes data to scores.txt
	private void writeData(List<Data> data){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i < data.size(); i++) {
				writer.write(data.get(i).toString());
				writer.newLine();
			}
		    writer.close();
		} catch (IOException e) {
			System.out.println("Error writing scores to file");
		}
	    
	}
	
	//gets first 5 highscore and show in end screen
	private void showLeaderBoard(List<Data> data, Graphics g) {
		changeFont(20, g);
        g.setFont(messageFont);
	    g.drawString("TOP 5 LEADERBOARD", 150, 160);
	    int i = 0;
	    int xName = 150;
	    int xScore = 340;
	    int y = 180;
	    changeFont(15, g);
	    g.setFont(messageFont);
	    while (i < 5 && i < data.size()) {
	    	g.drawString(data.get(i).getName(), xName, y);
	    	g.drawString(""+data.get(i).getScore(), xScore, y);
	    	y+=20;
	        i++;
	    }
	    
	}
	
	//gets the highest score
	private int getHighestScore(List<Data> data) {
		if(data.size() == 0) {
			return 0;
		}
		return data.get(0).getScore();
	}
	
	//-----------------------FIELD TO GET NAME-------------------
	private class SimpleJButton{
		SimpleJButton(){
			JFrame f = new JFrame("Data");
			JButton b = new JButton("Enter");
			b.setBounds(100, 80, 130, 30);
			JLabel labelCongratz = new JLabel();
			labelCongratz.setText(congratulations);
			labelCongratz.setBounds(msgPosn, 10, 300, 20);
			JLabel label = new JLabel();
			label.setText("Enter Full Name:");
			label.setBounds(40, 30, 120, 50);
			JTextField textField = new JTextField();
			textField.setBounds(160, 40, 130, 30);
			
			f.add(labelCongratz);
			f.add(textField);
			f.add(label);
			f.add(b);    
			f.setSize(330,170);    
			f.setLayout(null);
			f.setLocationRelativeTo(null);
			f.setVisible(true);    
			name = textField.getText();
			b.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e){
			    	f.dispose();
			        name = textField.getText();
			        addNewData(name, points, data);
			        writeData(data);
			        instruction = "Press space to play again";
			    }
			});
			
		}
	}
	
	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
		     pad.setPadRight(false);
		     pad.setPadLeft(false);
		}

		public void keyPressed(KeyEvent e) {
		    int key = e.getKeyCode();
		        //right arrow key
		        if((pad.getPos_x() + pad.getW()) < 500 && key==39){
		        	pad.setPadRight(true);
		        }else{
		        	pad.setPadRight(false);
		        }
		         
		        //left arrow key
		        if(pad.getPos_x() > 0 && key==37){
		        	pad.setPadLeft(true);
		        }else {
		        	pad.setPadLeft(false);
		        }
		        
		        //change startScreen to gameScreen or instructionScreen
		        if(startScreen){
		            //spacebar key
		            if(key==32){
		                startScreen = false;
		                ingame = true;
		            }
		            //enter key
		            if(key==10) {
		            	startScreen = false;
		            	instructions = true;
		            }
		        }
		        
		        //change instruction screen to gameScreen
		        if(instructions) {
		        	if(key==32) {
		        		instructions = false;
		        		ingame = true;
		        	}
		        }
		        
		        //change endScreen to gameScreen
		        if(endScreen){
		            if(key==32){
		            	runOnce = false;
		                endScreen = false;
		                ingame = true;
		                //restart life, score, brick position, congrats message,
		                //paddle position and width, power position and activation,
		                //clear all the 'multiple balls' from list, reset direction
		                //and add 1 ball to list
		                life = 3;
		                points = 0;
		                brickPosition(bricks1);
		                brickPosition(bricks2);
		                brickPosition(bricks3);
		                congratulations = "";
		                pad.setPos_x(Board_W/2);
		                pad.setW(70);
		                powerP.setPos_y(0);
		                powerF.setPos_y(0);
		                powerB.setPos_y(0);
		                powerP.setTime(0);
		                powerF.setTime(0);
		                powerB.setTime(0);
		                powerP.setPowerOn(false);
		                powerF.setPowerOn(false);
		                powerB.setPowerOn(false);
		                brickTimer = 0;
		                timer = 0;
		                for(Ball b: blist) {
		                	resetBallDirection(b);
		                	b.setPos_x(restartPosX);
		                	b.setPos_y(200);
		                	restartPosX+=20;
		                	if(b.getPos_x()==100) {
		            			lastBall = b;
		            		}
		                }
		                blist.clear();
		                instruction = "";
		            }
		        }

			}
		}
	
	@Override
	public void run() {
		    while (true) {//infinite loop
		      repaint();
		      try {
		        Thread.sleep(20);
		      }catch (InterruptedException e) {
		        System.out.println(e);
		      }//end catch
		    }//end while loop
		
	}

}
