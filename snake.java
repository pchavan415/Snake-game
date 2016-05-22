import acm.program.*;
import acm.graphics.*;
import java.awt.event.*;
import java.util.*;

public class snake extends GraphicsProgram{
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
	
     public void run()
     { 
    	dir = EAST;
    	setup();
    	addKeyListeners();
    	
        while(true)
        {
        	checkBoundary();
        	addCell();
        	checkFood();
        	if(checkBite())break;	
            
        	remove(ll.getFirst());
        	ll.removeFirst();
        	pause(DELAY);
        }
     }
    
    private boolean checkBite()
    {
    	double xH,yH;
    	xH = ll.getLast().getX();
    	yH = ll.getLast().getY();
    	
    	for(int i=1;i<ll.size()-3;i++)
    	{
    	    if(xH == ll.get(i).getX() && yH == ll.get(i).getY())
    	    {
    	    	gameOver();
    	    	return true;
    	    }
    	}
    	return false;
    }
     
    private void gameOver()
    {
    	GLabel label = new GLabel("Game Over",(RIGHT-LEFT)/2,(BOTTOM - TOP)/2);
    	add(label);
    	label.setFont("Courier-40");
    }
    
    private void checkFood()
    {
    	if(ll.getLast().getX() == food.getX() && ll.getLast().getY() == food.getY())
    	{
    		remove(food);
    		growSnake();
    		createFood();
    	}
    }
    
    private void createFood()
    {
    	int xRandom = random.nextInt(1200);
    	int yRandom = random.nextInt(600);
    	
    	int xPos = LEFT + xRandom - xRandom % 10 ;
    	int yPos = LEFT + yRandom - yRandom % 10 ;
    	
    	food = new GOval(xPos,yPos,CELL_WIDTH,CELL_HEIGHT);
    	food.setFillColor(Color.cyan);
    	food.setFilled(true);
    	add(food);
    }
    
    private void growSnake(){
    	addCell();
    	addCell();
    	count = count + 2;
    }
     
    private void checkBoundary()
    {
    	int posX = (int)cell.getX();
    	int posY = (int)cell.getY();
    	
    	if(posX < LEFT)
    		cell.move(RIGHT - LEFT, 0);	
    	else if(posX + CELL_WIDTH > RIGHT)
    		cell.move(-(RIGHT - LEFT), 0);
        else if(posY < TOP)
        	cell.move(0, BOTTOM - TOP);
        else if(posY + CELL_HEIGHT > BOTTOM)
    		cell.move(0,-(BOTTOM - TOP));
    }
   
    private void addCell()
    {
        if(dir == NORTH)
        {
        	cell = new GRect(cell.getX() ,cell.getY()- CELL_HEIGHT,CELL_WIDTH,CELL_HEIGHT);
        	cell.setFillColor(Color.lightGray);
        	cell.setFilled(true);
        	add(cell);
        	ll.add(cell);
        }
        	
        else if(dir == SOUTH)
        {
        	cell = new GRect(cell.getX() ,cell.getY()+ CELL_HEIGHT,CELL_WIDTH,CELL_HEIGHT);
        	cell.setFillColor(Color.lightGray);
        	cell.setFilled(true);
        	add(cell);
        	ll.add(cell);
        }
        else if (dir == EAST)
        {
        	cell = new GRect(cell.getX() + CELL_WIDTH,cell.getY(),CELL_WIDTH,CELL_HEIGHT);
        	cell.setFillColor(Color.lightGray);
        	cell.setFilled(true);
        	add(cell);
        	ll.add(cell);
        }
        else if(dir == WEST)
        {
        	cell = new GRect(cell.getX() - CELL_WIDTH,cell.getY(),CELL_WIDTH,CELL_HEIGHT);
        	cell.setFillColor(Color.lightGray);
        	cell.setFilled(true);
        	add(cell);
        	ll.add(cell);
        }
        count++;
        addScore();
    }
     
    public void addScore()
    {    	
    	score.setLabel("Score = "+(ll.size()-10));
    	score.setFont("Courier-30");
    	add(score);
    }
    
     public void keyPressed(KeyEvent e)
     {
    	 int key = e.getKeyCode();
    	 if(key == EAST && dir != WEST)
    	 {
    		 dir = EAST;
    		 pause(DELAY);
    	 }
    	 else if(key == WEST && dir != EAST)
    	 {
    		 dir = WEST;
    		 pause(DELAY);
    	 }
    	 else if(key == NORTH && dir != SOUTH)
    	 {
    		 dir = NORTH;
    		 pause(DELAY);
    	 }
    	 else if(key == SOUTH && dir != NORTH)
    	 {
    		 dir = SOUTH;
    		 pause(DELAY);
    	 }
     }
     
     public void setup()
     {
    	cell = new GRect(200,200,CELL_WIDTH,CELL_HEIGHT);
     	box = new GRect(LEFT,TOP,RIGHT - LEFT,BOTTOM - TOP);
     	for(int i = 0; i < 10; i++)
     		addCell();
     	cell.setFillColor(Color.lightGray);
     	add(cell);
        add(box);
        ll.add(cell);
        
        box.setFillColor(Color.yellow);
        box.setFilled(true);
        createFood();
    	setBackground(Color.orange);
     }
     
     private int dir;
     private GRect cell;
     private GRect box;
     private GOval food;
     private LinkedList<GRect> ll = new LinkedList<GRect>();
     private Random random = new Random();
     private int count = 0;
     private GLabel score = new GLabel("",1000,30);
}

