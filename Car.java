// Encapsulate Car state
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public	class Car extends Canvas
	{
		public int X;
		public int Y;
		private int old_X;
		private int old_Y;
		private final int nWidth = 8;
		private final int nHeight = 8;
		public static final int EastType = 1;
		public static final int OtherType = 0;
		public boolean Stopped = false;
        public int CarType = 0;
		public boolean Turned = false; // have I turned?
		private int myColor = 0;
		public int Pos = 0;
		
		public Car(int x, int y, int color) 
		{
			X = x;
			Y = y;
			old_X = 1000;
			old_Y = 1000;
            CarType = OtherType;
			myColor = color;
		}
		
		// Move a car as an offset of where it is currently
		public void Move(int x, int y)
		{
			old_X = X;
			old_Y = Y;
			X = X + x;
			Y = Y + y;
			Pos++;
		}
        
		// Erase this car from the screen
		public void Erase(Graphics canvasG)
		{
			//canvasG.clearRect(X, Y, nWidth, nHeight);
			canvasG.setColor(Color.white);
			canvasG.fillRect(X, Y, nWidth, nHeight);
		}
		
		// Draw the car
		public void paint(Graphics canvasG)
		{
			Color oldColor = canvasG.getColor();
			
			{
				// erase old
				canvasG.setColor(Color.white);
				canvasG.fillRect(old_X, old_Y, nWidth, nHeight);
				
				// draw new
				switch(myColor)
				{
				case 0:
					canvasG.setColor(Color.orange);
					break;
				case 1:
					canvasG.setColor(Color.cyan);
					break;
				case 2:
					canvasG.setColor(Color.green);
					break;
				case 3:
					canvasG.setColor(Color.blue);
					break;
				default:
					canvasG.setColor(Color.black);
					break;
				}
				
				// draw new
				canvasG.fillRect(X, Y, nWidth, nHeight);
				canvasG.setColor(oldColor);
			}
		}
	};
