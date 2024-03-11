// Cyclic Executive, running as its own thread
import java.awt.*;
import java.applet.*;
import java.util.Date;

public class Executive implements Runnable
{
	private _INPUT_VECTOR InputVector;
	private _INTERSECTION_STATE IntersectionState;
	private DrawCanvas drawingArea;
	Thread theThread;
	Cars theCars;
	public boolean IsRunning = false;
	private boolean bAlternate = true;
	
	Executive(_INTERSECTION_STATE pIntersectionState, _INPUT_VECTOR pInputVector, DrawCanvas pdrawingArea)
	{
		IntersectionState = pIntersectionState;
		InputVector = pInputVector;
		drawingArea = pdrawingArea;
		theCars = new Cars(drawingArea);
	}
	
	// Start the executive
	public void start()
	{
		IsRunning = true;
		theThread = new Thread(this);
		theThread.start();
	}
	
	// Stop the executive
	public void stop()
	{
		try
		{
			theThread.stop();
			IsRunning = false;
		}
		catch(Exception e)
		{
			e.notifyAll();
		}
	}
			 
	// Main executive thread function
	public void run()
	{
		// Initial Tick
		IsRunning = true;
		Init();
		Tick();
		
		// Begin the main loop
		while (IsRunning)
		{
		    // Move the traffic
		    AdjustTraffic();

		    // Generate the simulated clock interrupt
		    Tick();
							
			drawingArea.paint(drawingArea.getGraphics());

			// delay
			try
			{
				Thread.sleep(200);
			}
			catch(InterruptedException e)
			{
				e.notifyAll();
			}
		}
	}

	// Move the traffic appropriately
	private void AdjustTraffic()
	{
		//try
		{
			theCars.AdjustTraffic(InputVector);
			
			// Move cars 1 unit
			theCars.MoveCars(IntersectionState);
		
			// Draw the cars
			theCars.DrawCars();
			
			// Display traffic state
			theCars.DisplayState();
		}
		//catch(Exception e)
		{
			//e.notifyAll();
		}
	}

	void Init()
	{
		// Initialization
		IntersectionState.State = _LIGHT_STATES.LS_EWG;
		IntersectionState.Counter = InputVector.MaxEwGreen;
	}
	
	// One unit of time: transition to next state
	private void Tick()
	{
		Date tmTime = new Date();

		// Decrement counter
		IntersectionState.Counter--;

		// Look for requests
		if (theCars.GetWestTurnStopped() > 0)
			IntersectionState.WestTurn = true;
		if (theCars.GetEastTurnStopped() > 0)
			IntersectionState.EastTurn = true;
		if (theCars.GetNorthStopped() > 0)
			IntersectionState.Northbound = true;
		
		// Transition to next state
		if (IntersectionState.Counter <= 0)
		{
			switch(IntersectionState.State)
			{
				// East-West
				case _LIGHT_STATES.LS_EWX  :
				case _LIGHT_STATES.LS_ETWT :
				case _LIGHT_STATES.LS_WTWG :
				case _LIGHT_STATES.LS_ETEG :
				case _LIGHT_STATES.LS_EWG  :
					// North/South cross walk traffic
					if (IntersectionState.NorthWalk || IntersectionState.SouthWalk)
					{
						IntersectionState.State = _LIGHT_STATES.LS_NSX;
						IntersectionState.Counter = InputVector.MaxTurnGreen;
						IntersectionState.NorthWalk = false;
					}
					// N/S traffic waiting
					else if (IntersectionState.Northbound || IntersectionState.Southbound)
					{
						IntersectionState.State = _LIGHT_STATES.LS_NSG;
						IntersectionState.Counter = InputVector.MaxTurnGreen;
						IntersectionState.Northbound = false;
						IntersectionState.Southbound = false;
					}
					// Otherwise, cycle the North/South light
					else
					{
						IntersectionState.State = _LIGHT_STATES.LS_NSG;
						IntersectionState.Counter = InputVector.MaxNsGreen;
					}
					break;

				// North-South
				case _LIGHT_STATES.LS_NSX  :
				case _LIGHT_STATES.LS_NSG  :
					// East/West cross walk traffic
					if (IntersectionState.EastWalk || IntersectionState.WestWalk)
					{
						IntersectionState.State = _LIGHT_STATES.LS_EWX;
						IntersectionState.Counter = InputVector.MaxTurnGreen;
						IntersectionState.WestWalk = false;
						IntersectionState.EastWalk = false;
					}
					// East turn waiting
					else if (IntersectionState.EastTurn && bAlternate)
					{
						IntersectionState.State = _LIGHT_STATES.LS_ETEG;
						IntersectionState.Counter = InputVector.MaxTurnGreen;
						IntersectionState.EastTurn = false;
						bAlternate = !bAlternate; // turn signals starve each other..
					}
					// West turn waiting
					else if (IntersectionState.WestTurn && !bAlternate)
					{
						IntersectionState.State = _LIGHT_STATES.LS_WTWG;
						IntersectionState.Counter = InputVector.MaxTurnGreen;
						IntersectionState.WestTurn = false;
						bAlternate = !bAlternate;
					}
					// Otherwise, cycle the East/West light
					else
					{
						IntersectionState.State = _LIGHT_STATES.LS_EWG;
						IntersectionState.Counter = InputVector.MaxEwGreen;
					}
					break;

				default:
					IntersectionState.Counter = 0;
					break;

			}
		}
	}
}

