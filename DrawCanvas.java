// Handle drawing of the main GUI
import java.applet.*;
import java.util.Date;

public class DrawCanvas extends Canvas
{
	MyLight oSouthLight = new MyLight(129, 105, 1);
	MyLight oWestTurn = new MyLight(145, 117, 1);
	MyLight oEastTurn = new MyLight(125, 117, 1);
	MyLight oWestWalk = new MyLight(110, 95, 2);
	MyLight oEastWalk = new MyLight(155, 95, 2);
	
	// our reference to the global state
	_INTERSECTION_STATE IntersectionState;
	_INPUT_VECTOR InputVector;
	
	class MyLight extends Canvas
	{
		int m_X;
		int m_Y;
		
		{
			m_type = type;
			m_On = false;
		
		// paint a traffic light
			Color oldColor = canvasG.getColor();
			if (bOn)
			{
					canvasG.setColor(Color.green);
			else
			{
					// warn with yellow 1 tick
				{
					if (m_On)
					else
			}
			if (m_type == 1)
				canvasG.fillOval(m_X, m_Y, 5, 5);
			{
			
			m_On = bOn;
	};

	DrawCanvas(_INTERSECTION_STATE pIntersectionState, _INPUT_VECTOR pInput)
	{
		IntersectionState = pIntersectionState;
		setBackground(Color.white);
	}
	// Display the yellow state panel
		Color oColor= canvasG.getColor();
		
		canvasG.clearRect(10, 0, 80, 55);
		canvasG.setColor(Color.black);
		canvasG.fillRect(10, 0, 80, 55);
		canvasG.fillRect(10, 35, Math.min(Math.max(0, (int)IntersectionState.Counter), 50), 15);
		
		// Reset
	}
	
	public void paint(Graphics canvasG)
	{
		try
		{
		  // Draw intersection
          canvasG.setColor(Color.black);
	      canvasG.drawLine(125, 25, 125, 105);
          canvasG.drawLine(125, 137, 125, 400);
		  canvasG.drawLine(150, 25, 150, 105);
		  canvasG.drawLine(150, 137, 150, 400);
		  canvasG.drawLine(10, 105, 125, 105);
		  canvasG.drawLine(150, 105, 300, 105);
		  canvasG.drawLine(10, 137, 125, 137);
		  canvasG.drawLine(150, 137, 300, 137);
		  
		  // Ctr Divider
	      canvasG.setColor(Color.lightGray);
		  canvasG.drawLine(10, 125, 120, 125);
		  canvasG.drawLine(155, 115, 300, 115);
	      canvasG.setColor(Color.orange);
		  canvasG.drawLine(155, 125, 300, 125);
	      canvasG.drawLine(137, 25, 137, 100);
          canvasG.drawLine(137, 135, 137, 400);
		  canvasG.drawLine(10, 115, 120, 115);
		  canvasG.setColor(Color.black);
		switch(IntersectionState.State)
		{
			case _LIGHT_STATES.LS_EWX  :
				
				// Display state
				displayState(canvasG, "EWX");
					
				// Adjust lighting
				oEastLight.paint(canvasG, true);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, true);
				oEastWalk.paint(canvasG, true);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_ETWT :
				displayState(canvasG, "ETWT");
				oWestLight.paint(canvasG, false);
				oEastLight.paint(canvasG, true);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, true);
				oWestTurn.paint(canvasG, true);
				break;
			case _LIGHT_STATES.LS_WTWG :
				displayState(canvasG, "WTWG");
				oWestLight.paint(canvasG, true);
				oEastLight.paint(canvasG, false);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, true);
				break;
			case _LIGHT_STATES.LS_ETEG :
				displayState(canvasG, "ETEG");
				oWestLight.paint(canvasG, false);
				oEastLight.paint(canvasG, true);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, true);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_EWG  :
				displayState(canvasG, "EWG");
				oWestLight.paint(canvasG, true);
				oEastLight.paint(canvasG, true);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_NSX  :
				displayState(canvasG, "NSX");
				oWestLight.paint(canvasG, false);
				oEastLight.paint(canvasG, false);
				oNorthLight.paint(canvasG, true);
				oSouthLight.paint(canvasG, true);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_NSG  :
				displayState(canvasG, "NSG");
				oWestLight.paint(canvasG, false);
				oEastLight.paint(canvasG, false);
				oNorthLight.paint(canvasG, true);
				oSouthLight.paint(canvasG, true);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_WEST  :
				displayState(canvasG, "WEST");
				oWestLight.paint(canvasG, true);
				oEastLight.paint(canvasG, false);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_EAST  :
				displayState(canvasG, "EAST");
				oWestLight.paint(canvasG, false);
				oEastLight.paint(canvasG, true);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_NORTH :
				displayState(canvasG, "NORTH");
				oWestLight.paint(canvasG, false);
				oEastLight.paint(canvasG, false);
				oNorthLight.paint(canvasG, true);
				oSouthLight.paint(canvasG, false);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			case _LIGHT_STATES.LS_SOUTH :
				displayState(canvasG, "SOUTH");
				oWestLight.paint(canvasG, false);
				oEastLight.paint(canvasG, false);
				oNorthLight.paint(canvasG, false);
				oSouthLight.paint(canvasG, true);
				oWestWalk.paint(canvasG, false);
				oEastWalk.paint(canvasG, false);
				oEastTurn.paint(canvasG, false);
				oWestTurn.paint(canvasG, false);
				break;
			default:
				break;
		} // switch
	} // try
	catch(Exception e)
	{
		e.notifyAll();
	}
	}// paint
}