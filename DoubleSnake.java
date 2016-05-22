import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;

/*
 <applet code="Snake" width=200 height=200>
 </applet>
 */

class Timer extends Applet implements Runnable{
	int time;
	Timer(int t) {
		time = t;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(time>0){
			time--;
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int getTime(){
		return time;
	}

	public void paint(Graphics g){
		g.drawString("Timer : "+time,100,20);
	}
}

public class DoubleSnake extends Applet implements KeyListener, Runnable {

	private static final int CELL_HEIGHT = 10;
	private static final int CELL_WIDTH = 10;
	private static final int RIGHT = 1240;
	private static final int LEFT = 40;
	private static final int TOP = 40;
	private static final int BOTTOM = 640;
	private static final int DELAY = 30;
	private static final int NORTH = KeyEvent.VK_UP;
	private static final int SOUTH = KeyEvent.VK_DOWN;
	private static final int EAST = KeyEvent.VK_RIGHT;
	private static final int WEST = KeyEvent.VK_LEFT;
	LinkedList<Point> ll1 = new LinkedList<Point>();
	LinkedList<Point> ll2 = new LinkedList<Point>();
	
	int black = 0,white = 0;
	
	Thread t;
	int dir1 = EAST;
	int dir2 = WEST;
	int score1 = 0;
	int score2 = 0;
	
	
	int delay = 50;
	int rx1, ry1, rw1, rh1;
	int rx2, ry2, rw2, rh2;
	
	int time = 60;
	
	Thread timer;
	Timer tThread;
	int length = 10;
	boolean gameOver = false;
	int foodx,foody;
	boolean ate1 = false,ate2 = false;

	public void setup() {
		rx1 = 100;
		ry1 = 100;
		rw1 = 10;
		rh1 = 10;
		
		rx2 = 400;
		ry2 = 400;
		rw2 = 10;
		rh2 = 10;
		
		Point p1 = new Point(rx1, ry1);
		Point p2 = new Point(rx2, ry2);
		
		for (int i = 0; i < 10; i++) {
			p1 = new Point(rx1 + (i * rw1), ry1);
			ll1.add(p1);
			repaint();
		}
		for (int i = 0; i < 10; i++) {
			p2 = new Point(rx2 - (i * rw2), ry2);
			ll2.add(p2);
			repaint();
		}
		
		createFood();
	}

	public void init() {
		setup();
		addKeyListener(this);
		t = new Thread(this);
		tThread = new Timer(time);
		timer = new Thread(tThread);
		
		
		timer.start();
		t.start();
	}

	public void addCell1() {
		Point q;
		if (dir1 == EAST) {
			Point p1 = ll1.getLast();
			int x = p1.x + rw1;
			if (x + rw1 > RIGHT) {
				x = LEFT;
			}
			q = new Point(x, p1.y);
			if(q.x == foodx && q.y == foody){
				createFood();
				score1++;
				ll1.add(q);
				return;
			}
			ll1.add(q);
			ll1.removeFirst();

			repaint();
		} else if (dir1 == WEST) {
			Point p = ll1.getLast();
			int x = p.x - rw1;
			if (x < LEFT) {
				x = RIGHT - rw1;
			}
			q = new Point(x, p.y);
			if(q.x == foodx && q.y == foody){
				createFood();
				ll1.add(q);
				score1++;
				return;
			}
			
			ll1.add(q);
			ll1.removeFirst();
			repaint();
		} else if (dir1 == NORTH) {
			Point p = ll1.getLast();
			int y = p.y - rw1;
			if (y < TOP) {
				y = BOTTOM - rw1;
			}
			q = new Point(p.x, y);
			if(q.x == foodx && q.y == foody){
				createFood();
				ll1.add(q);
				score1++;
				return;
			}

			ll1.removeFirst();
			ll1.add(q);
			repaint();
		} else if (dir1 == SOUTH) {
			Point p = ll1.getLast();
			int y = p.y + rw1;
			if (y + rh1> BOTTOM) {
				y = TOP;
			}
			q = new Point(p.x, y);
			if(q.x == foodx && q.y == foody){
				createFood();
				ll1.add(q);
				score1++;
				return;
			}
			ll1.removeFirst();
			ll1.add(q);
			repaint();
		}
	}
	

	public void addCell2() {
		Point q;
		if (dir2 == EAST) {
			Point p1 = ll2.getLast();
			int x = p1.x + rw1;
			if (x + rw1 > RIGHT) {
				x = LEFT;
			}
			q = new Point(x, p1.y);
			if(q.x == foodx && q.y == foody){
				createFood();
				score2++;
				ll2.add(q);
				return;
			}
			ll2.add(q);
			ll2.removeFirst();

			repaint();
		} else if (dir2 == WEST) {
			Point p = ll2.getLast();
			int x = p.x - rw1;
			if (x < LEFT) {
				x = RIGHT - rw1;
			}
			q = new Point(x, p.y);
			if(q.x == foodx && q.y == foody){
				createFood();
				score2++;
				ll2.add(q);
				return;
			}
			
			ll2.add(q);
			ll2.removeFirst();
			repaint();
		} else if (dir2 == NORTH) {
			Point p = ll2.getLast();
			int y = p.y - rw1;
			if (y < TOP) {
				y = BOTTOM - rw1;
			}
			q = new Point(p.x, y);
			if(q.x == foodx && q.y == foody){
				createFood();
				ll2.add(q);
				score2++;
				return;
			}

			ll2.removeFirst();
			ll2.add(q);
			repaint();
		} else if (dir2 == SOUTH) {
			Point p = ll2.getLast();
			int y = p.y + rw1;
			if (y + rh2> BOTTOM) {
				y = TOP;
			}
			q = new Point(p.x, y);
			if(q.x == foodx && q.y == foody){
				createFood();
				ll2.add(q);
				score2++;
				return;
			}
			ll2.removeFirst();
			ll2.add(q);
			repaint();
		}
	}


	public void run() {

		while (true) {
			addCell1();
			addCell2();
			if (checkBite1()) {
				break;
			}
			if(checkBite2()){
				break;
			}
			if(tThread.getTime() == 0){
				break;
			}
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createFood() {
		int xRandom = (int)(Math.random() * 1000000)%1200;
		int yRandom = (int)(Math.random() * 1000000)%600;;

		int xPos = LEFT + xRandom - xRandom % 10;
		int yPos = LEFT + yRandom - yRandom % 10;
		foodx = xPos;
		foody = yPos;
	}

	private boolean checkBite1() {
		double xH, yH;
		xH = ll1.getLast().getX();
		yH = ll1.getLast().getY();

		for (int i = 1; i < ll1.size() - 3; i++) {
			if (xH == ll1.get(i).getX() && yH == ll1.get(i).getY()) {
				black = 1;
				return true;
			}
		}
		return false;
	}
	
	private boolean checkBite2() {
		double xH, yH;
		xH = ll2.getLast().getX();
		yH = ll2.getLast().getY();

		for (int i = 1; i < ll2.size() - 3; i++) {
			if (xH == ll2.get(i).getX() && yH == ll2.get(i).getY()) {
				white  =1;
				return true;
			}
		}
		return false;
	}

	public void paint(Graphics g) {
		if(black == 1){
			g.drawString("Player 1 wins", 500, 300);
		}
		else if(white == 1){
			g.drawString("Player 2 wins", 500, 300);
		}
		setBackground(Color.orange);
		for (int i = 0; i < ll1.size(); i++) {
			Point p = ll1.get(i);
			g.drawOval(p.x, p.y, rw1, rh1);
			g.fillOval(p.x, p.y, rw1, rh1);
			
		}
		
		for (int i = 0; i < ll2.size(); i++) {
			Point p = ll2.get(i);
			g.drawOval(p.x, p.y, rw1, rh1);
		}
		
		g.drawRect(LEFT, TOP, RIGHT - LEFT, BOTTOM - TOP);
	
		g.drawRect(foodx, foody, 10, 10);
		g.drawString("black  : "+ score1, 1000, 30);
		g.drawString("white  : "+ score2, 1100, 30);
		g.drawString("time : "+tThread.getTime(), 100, 30);
		
		if(ll1.size() > ll2.size() && tThread.time == 0){
			g.drawString("Player 1 wins", 500, 300);
		}
		else if(ll1.size() < ll2.size() && tThread.time == 0){
			g.drawString("Player 2 wins", 500, 300);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int c = e.getKeyCode();
		if(c == KeyEvent.VK_W && dir2  != SOUTH){
			dir2 = NORTH;
		}
		else if(c == KeyEvent.VK_S && dir2  != NORTH){
			dir2 = SOUTH;
		}
		else if(c == KeyEvent.VK_A && dir2  != EAST){
			dir2 = WEST;
		}
		else if(c == KeyEvent.VK_D && dir2  != WEST){
			dir2 = EAST;
		}
		
		
		
		if (c == NORTH && dir1 != SOUTH) {
			dir1 = NORTH;

			repaint();
			e.consume();
		} else if (c == SOUTH && dir1 != NORTH) {
			dir1 = SOUTH;

			repaint();
			e.consume();
		} else if (c == WEST && dir1 != EAST) {
			dir1 = WEST;

			repaint();
			e.consume();
		} else if (c == EAST && dir1 != WEST) {
			dir1 = EAST;

			repaint();
			e.consume();
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
