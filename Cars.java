// Manage behavior/drawing of the cars

import java.awt.*;
import java.applet.*;
import java.util.Vector;

// Car list to hold traffic from a given direction
class CarList extends Vector
{
	Car GetCar(int idx)
	{
		return (Car)elementAt(idx);
	}
	
	void RemoveCar(int idx, DrawCanvas drawingArea)
	{
		GetCar(idx).Erase(drawingArea.getGraphics());
		removeElementAt(idx);
	}
	
	void DrawCars(Graphics canvasG)
	{
		try
		{
			for (int i=0; i<this.size();i++)
			{
				GetCar(i).paint(canvasG);
			}
		}
		catch (Exception e)
		{
			e.notifyAll();
		}
	}
}

public class Cars  extends Canvas
{
	private int nMaxCars = 5;
	
	// Lists of traffic
	private CarList WestCars = new CarList();
	private CarList EastCars = new CarList();
	private CarList NorthCars = new CarList();
	private CarList SouthCars = new CarList();
	private CarList WestTurnCars = new CarList();
	private CarList EastTurnCars = new CarList();

	private boolean bBusy = false;
	private String m_strDebug = new String();
	
	DrawCanvas drawingArea;
	
	Cars(DrawCanvas pdrawingArea)
	{
		drawingArea = pdrawingArea;
	}
	
	private int GetStoppedCount(CarList theCars)
	{
		int i;
		int nStopped = 0;
		for (i=0; i < theCars.size(); i++)
		{
			if (theCars.GetCar(i).Stopped == true)
				nStopped ++;
		}
		return nStopped;
	}

	// helpers for executive
	public int GetEastTurnStopped()
	{
		return GetStoppedCount(EastTurnCars);
	}
	public int GetWestTurnStopped()
	{
		return GetStoppedCount(WestTurnCars);
	}
	public int GetWestStopped()
	{
		return GetStoppedCount(WestCars);
	}
	public int GetNorthStopped()
	{
		return GetStoppedCount(NorthCars);
	}

	
	// Add a west bound car
	public void AddWestCar()
	{
		WestCars.addElement(new Car(290, 105, 0));
	}

	// Add a east bound car
	public void AddEastCar()
	{
		EastCars.addElement(new Car(10, 125, 1));
	}
    
	// Add a west bound car
	public void AddWestTurnCar()
	{
		WestTurnCars.addElement(new Car(290, 115, 2));
	}

	// Add a west bound car to turning lane
	public void AddEastTurnCar()
	{
		EastTurnCars.addElement(new Car(10, 115, 3));
	}
	
	// Add a northbound car
	public void AddNorthCar()
	{
		NorthCars.addElement( new Car(140, 390, 4));
	}

	// Add a northbound car
	public void AddSouthCar()
	{
		SouthCars.addElement( new Car(130, 10, 5));
	}

	// I should stop if the car ahead of me does
	private boolean IsPrevCarStopped(CarList theCars, int idx)
	{
		boolean bStopped = false;
		try
		{
			if (idx > 0)
			{
				int myPos = theCars.GetCar(idx).Pos;
				int prevPos = theCars.GetCar(idx-1).Pos;
				boolean prevStopped = theCars.GetCar(idx-1).Stopped;
				if (prevStopped && (myPos + 1 == prevPos))
				{
					bStopped = true;
				}
			}
		}
		catch (Exception e)
		{
			e.notifyAll();
		}
		return bStopped;
	}
	
	
	// Move West bound traffic 1 unit
	public void MoveWestCars(_INTERSECTION_STATE pIntersectionState)
	{
		int i;
		try
		{
			for (i=0; i< WestCars.size(); i++)
			{
				Car theCar = WestCars.GetCar(i);
				
				// check light and proceed if OK
				if (_LIGHT_STATES.IsWestGreen(pIntersectionState.State)
					|| theCar.X < 150
					|| (!_LIGHT_STATES.IsWestGreen(pIntersectionState.State)
					&& theCar.X > 150)
					&& !IsPrevCarStopped(WestCars, i))
				{
					// vaporize cars when they leave the scene
					if (theCar.X >= 10)
					{
						if (!theCar.Stopped) // wait one tick
						{
							theCar.Move(-10, 0);
						}
						theCar.Stopped = false; // Not stopped
					}
					else
					{
						WestCars.RemoveCar(i, drawingArea);
					}
				}
				else
					theCar.Stopped = true; // Stopped
			}
		}
		catch(Exception e)
		{
			e.notifyAll();
		}
	}

	// Move East bound traffic 1 unit
	public void MoveEastCars(_INTERSECTION_STATE pIntersectionState)
	{
		int i;
		try
		{
			// check light and proceed if OK
			for (i=0; i< EastCars.size(); i++)
			{
				Car theCar = EastCars.GetCar(i);
				
				// check light and proceed if OK
				if ((_LIGHT_STATES.IsEastGreen(pIntersectionState.State)
					|| theCar.X < 120
					|| (!_LIGHT_STATES.IsEastGreen(pIntersectionState.State)
					&& theCar.X > 120))
					&& !IsPrevCarStopped(EastCars, i))
				{
					
					// vaporize cars when they leave the scene
					if (theCar.X <= 290)
					{
						if (!theCar.Stopped) // wait one tick
						{
							theCar.Move(10, 0);
						}
						theCar.Stopped = false; // Not stopped
					}
					else
					{
						EastCars.RemoveCar(i, drawingArea);
					}
				}
				else
					theCar.Stopped = true; // Stopped
			}
		}
		catch(Exception e)
		{
			e.notifyAll();
		}
	}
    
	public void MoveCars(_INTERSECTION_STATE pIntersectionState)
	{
		this.MoveWestCars(pIntersectionState);
		this.MoveEastCars(pIntersectionState);
		this.MoveNorthCars(pIntersectionState);
		this.MoveSouthCars(pIntersectionState);
		this.MoveWestTurnCars(pIntersectionState);
		this.MoveEastTurnCars(pIntersectionState);
	}

	// Move West turn traffic 1 unit
	public void MoveWestTurnCars(_INTERSECTION_STATE pIntersectionState)
	{
		int i;
		try
		{
			for (i=0; i< WestTurnCars.size(); i++)
			{
				Car theCar = WestTurnCars.GetCar(i);
				
				// check light and proceed if OK
				if (
					(_LIGHT_STATES.IsWestTurnGreen(pIntersectionState.State)
					|| (!_LIGHT_STATES.IsWestTurnGreen(pIntersectionState.State) 
						&& theCar.X > 170 )
					&& !IsPrevCarStopped(WestTurnCars, i))
					|| theCar.Turned)
				{
					if (theCar.Y >= 10)
					{
						if (theCar.X == 170)
						{
							if (!theCar.Stopped) // wait one tick
							{
								theCar.Move(-10, 0);
							}
							theCar.Turned = true;
						}
						else if (theCar.X == 160)
						{
							theCar.Move(-10, 2);
							theCar.Turned = true;
						}
						else if (theCar.X == 150)
							theCar.Move(-10, 5);
						else if (theCar.X == 140)
							theCar.Move(-10, 2);
						else if (theCar.X == 130)
							theCar.Move(0, 10);
						else if (theCar.Turned)
							theCar.Move(0, 10);
						else
							theCar.Move(-10, 0);
						
						theCar.Stopped = false; // Not stopped
					}
					else
					{
						WestTurnCars.RemoveCar(i, drawingArea);
					}
					
				}
				else
				{
					// stop at the turn light
					theCar.Stopped = true; // Stopped
				}
			}
		}
		catch(Exception e)
		{
			e.notifyAll();
		}
	}

	// Move East turn traffic 1 unit
	public void MoveEastTurnCars(_INTERSECTION_STATE pIntersectionState)
	{
		int i;
		try
		{
			for (i=0; i< EastTurnCars.size(); i++)
			{
				Car theCar = EastTurnCars.GetCar(i);
				
				if ((_LIGHT_STATES.IsEastTurnGreen(pIntersectionState.State)
					|| (!_LIGHT_STATES.IsEastTurnGreen(pIntersectionState.State)
					&& theCar.X < 100 )
					&& !IsPrevCarStopped(EastTurnCars, i))
					|| theCar.Turned)
				{
					if (theCar.Y >= 10)
					{
						if (theCar.X == 100)
						{
							if (!theCar.Stopped) // wait one tick
							{
								theCar.Move(10, 0);
							}
							theCar.Turned = true;
						}
						else if (theCar.X == 110)
							theCar.Move(10, -2);
						else if (theCar.X == 120)
							theCar.Move(10, -5);
						else if (theCar.X == 130)
							theCar.Move(10, -2);
						else if (theCar.X == 140)
							theCar.Move(0, -10);
						else if (theCar.Turned)
							theCar.Move(0, -10);
						else
							theCar.Move(10, 0);
						
						theCar.Stopped = false; // Not stopped
					}
					else
					{
						EastTurnCars.RemoveCar(i, drawingArea);
					}
				}
				else
				{
					// stop at the turn light
					theCar.Stopped = true; // Stopped
				}
			}
		}
		catch(Exception e)
		{
			e.notifyAll();
		}
	}
	
	// Move Northbound traffic 1 unit
	public void MoveNorthCars(_INTERSECTION_STATE pIntersectionState)
	{
		int i;
		try
		{
			for (i=0; i< NorthCars.size(); i++)
			{
				Car theCar = NorthCars.GetCar(i);
				
				// check light and proceed if OK
				if (_LIGHT_STATES.IsNorthGreen(pIntersectionState.State)
					|| theCar.Y < 140
					|| (!_LIGHT_STATES.IsNorthGreen(pIntersectionState.State)
					&& theCar.Y > 140)
					&& !IsPrevCarStopped(NorthCars, i))
				{
					if (theCar.Y >= 10)
					{
						if (!theCar.Stopped) // wait one tick
						{
							theCar.Move(0, -10);
						}
						theCar.Stopped = false; // not stopped
					}
					else
					{
						NorthCars.RemoveCar(i, drawingArea);
					}
				}
				else
					theCar.Stopped = true; // i'm stopped
			}
		}
		catch(Exception e)
		{
			e.notifyAll();
		}
	}

	// Move Southbound traffic 1 unit
	public void MoveSouthCars(_INTERSECTION_STATE pIntersectionState)
	{
		int i;
		try
		{
			for (i=0; i< SouthCars.size(); i++)
			{
				Car theCar = SouthCars.GetCar(i);
				
				// check light and proceed if OK
				if (_LIGHT_STATES.IsSouthGreen(pIntersectionState.State)
					|| theCar.Y > 100
					|| (!_LIGHT_STATES.IsSouthGreen(pIntersectionState.State)
					&& theCar.Y < 100)
					&& !IsPrevCarStopped(SouthCars, i))
				{
					if (theCar.Y <= 300)
					{
						if (!theCar.Stopped) // wait one tick
						{
							theCar.Move(0, 10);
						}
						theCar.Stopped = false; // not stopped
					}
					else
					{
						SouthCars.RemoveCar(i, drawingArea);
					}
				}
				else
					theCar.Stopped = true; // i'm stopped
			}
		}
		catch(Exception e)
		{
			e.notifyAll();
		}
	}
	
	public void DrawCars()
	{
		
		// Draw the west bound traffic
		WestCars.DrawCars(drawingArea.getGraphics());
		// Draw the East bound cars
		EastCars.DrawCars(drawingArea.getGraphics());
		// Draw the Northbound
		NorthCars.DrawCars(drawingArea.getGraphics());
		// Draw the WestTurn cars
		WestTurnCars.DrawCars(drawingArea.getGraphics());
		// Draw the East Turn cars
		EastTurnCars.DrawCars(drawingArea.getGraphics());
		// Draw the Southbound cars
		SouthCars.DrawCars(drawingArea.getGraphics());
	}
	
	public void paint(Graphics canvasG)	{		canvasG.clearRect(0, 80, 120, 20);
		canvasG.setColor(Color.black);
		canvasG.drawString(m_strDebug, 10, 90);
	}
	
	// for debugging info in a region of the screen	public void DebugPrint(String str)	{
		m_strDebug = str;
		this.paint(drawingArea.getGraphics());	}

	// Display the traffic state panel	public void DisplayState()	{		Graphics canvasG = drawingArea.getGraphics();
		Color oColor= canvasG.getColor();		int nWestStopped = GetStoppedCount(WestCars);		int nEastStopped = GetStoppedCount(EastCars);		int nNorthStopped = GetStoppedCount(NorthCars);		int nSouthStopped = GetStoppedCount(SouthCars);
		int nEastTurnStopped = GetStoppedCount(WestTurnCars);
		int nWestTurnStopped = GetStoppedCount(EastTurnCars);
		
		canvasG.clearRect(190, 0, 80, 100);
		canvasG.setColor(Color.black);		canvasG.drawRect(190, 0, 80, 100);		canvasG.setColor(Color.yellow);
		canvasG.fillRect(190, 0, 80, 100);		canvasG.setColor(Color.red);		canvasG.drawString("Waiting Cars", 190, 10);
		canvasG.setColor(Color.blue);		canvasG.drawString("East:",             190, 25);		canvasG.drawString(""+nEastStopped,     250, 25);		canvasG.drawString("West:",             190, 40);
		canvasG.drawString(""+nWestStopped,     250, 40);		canvasG.drawString("North:",            190, 55);		canvasG.drawString(""+nNorthStopped,    250, 55);		canvasG.drawString("South:",            190, 70);		canvasG.drawString(""+nSouthStopped,    250, 70);		canvasG.drawString("EastTurn:",         190, 85);		canvasG.drawString(""+nEastTurnStopped, 250, 85);		canvasG.drawString("WestTurn:",         190, 100);		canvasG.drawString(""+nWestTurnStopped, 250, 100);		
		// Reset		canvasG.setColor(oColor);
	}
	
	public void AdjustTraffic(_INPUT_VECTOR Input)
	{
		// get user input on traffic rates by direction
		double NS = Input.NorthSouthPerMin;
		double EW = Input.EastWestPerMin;
		double EWT = Input.TurnPerMin;

		double temp = 0.0;
		int count = 0;
		int n = 0;

		Math.random();

		temp = Math.floor(NS / 60.0);
		if ((NS / 60.0) - temp > Math.random())
			count = (int) (temp + 1.0);
		else
			count = (int) temp;

		for (n = 0; n < count; n++)
		{
			if (Math.random() < 0.5)
				AddNorthCar();
			else
				AddSouthCar();
		}

		temp = Math.floor(EW / 60.0);
		if ((EW / 60.0) - temp > Math.random())
			count = (int) (temp + 1.0);
		else
			count = (int) temp;

		for (n = 0; n < count; n++)
		{
			if (Math.random() < 0.5)
				AddEastCar();
			else
				AddWestCar();
		}

		temp = Math.floor(EWT / 60.0);
		if ((EWT / 60.0) - temp > Math.random())
			count = (int) (temp + 1.0);
		else
			count = (int) temp;

		for (n = 0; n < count; n++)
		{
			if (Math.random() < 0.5)
				AddEastTurnCar();
			else
				AddWestTurnCar();
		}

	} // AdjustTraffic
	
} // class Cars
