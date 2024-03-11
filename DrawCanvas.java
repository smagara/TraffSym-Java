// Handle drawing of the main GUIimport java.awt.*;
import java.applet.*;
import java.util.Date;

public class DrawCanvas extends Canvas
{	static String strOldState = "";		// Create the stoplights
	MyLight oSouthLight = new MyLight(129, 105, 1);	MyLight oNorthLight = new MyLight(141, 130, 1);	MyLight oEastLight = new MyLight(125, 127, 1);	MyLight oWestLight = new MyLight(145, 107, 1);
	MyLight oWestTurn = new MyLight(145, 117, 1);
	MyLight oEastTurn = new MyLight(125, 117, 1);
	MyLight oWestWalk = new MyLight(110, 95, 2);
	MyLight oEastWalk = new MyLight(155, 95, 2);
	
	// our reference to the global state
	_INTERSECTION_STATE IntersectionState;
	_INPUT_VECTOR InputVector;
		// Encapsulate Light visuals
	class MyLight extends Canvas
	{
		int m_X;
		int m_Y;		int m_type;		boolean m_On;
				MyLight(int x, int y, int type) 
		{			m_X = x;			m_Y = y;
			m_type = type;
			m_On = false;		}
		
		// paint a traffic light		void paint(Graphics canvasG, boolean bOn)		{
			Color oldColor = canvasG.getColor();
			if (bOn)
			{				if (m_type == 1)
					canvasG.setColor(Color.green);				else if (m_type == 2)					canvasG.setColor(Color.lightGray);			}		
			else
			{				if (m_type == 1)				{
					// warn with yellow 1 tick					if (m_On)						canvasG.setColor(Color.yellow);					else						canvasG.setColor(Color.red);				}				else if (m_type == 2)
				{					// warn with magenta 1 tick
					if (m_On)						canvasG.setColor(Color.magenta);
					else						canvasG.setColor(Color.orange);				}
			}			
			if (m_type == 1)
				canvasG.fillOval(m_X, m_Y, 5, 5);			else
			{				canvasG.fillRect(m_X, m_Y, 6, 4);			}
						canvasG.setColor(oldColor);
			m_On = bOn;		}
	};
	// Constructor takes IntersectionState reference
	DrawCanvas(_INTERSECTION_STATE pIntersectionState, _INPUT_VECTOR pInput)
	{		super();
		IntersectionState = pIntersectionState;		InputVector = pInput;
		setBackground(Color.white);
	}	
	// Display the yellow state panel	private void displayState(Graphics canvasG, String strState)	{
		Color oColor= canvasG.getColor();
		
		canvasG.clearRect(10, 0, 80, 55);
		canvasG.setColor(Color.black);		canvasG.drawRect(10, 0, 80, 55);		canvasG.setColor(Color.yellow);
		canvasG.fillRect(10, 0, 80, 55);		canvasG.setColor(Color.blue);		canvasG.drawString("Light: " + strState, 10, 10);		canvasG.drawString("Duration:", 10, 30);		canvasG.setColor(Color.green);
		canvasG.fillRect(10, 35, Math.min(Math.max(0, (int)IntersectionState.Counter), 50), 15);
		
		// Reset		canvasG.setColor(oColor);
	}
		// Draw the intersection and lights
	public void paint(Graphics canvasG)
	{
		try
		{
		  // Draw intersection			
          canvasG.setColor(Color.black);		  // North/South lanes
	      canvasG.drawLine(125, 25, 125, 105);
          canvasG.drawLine(125, 137, 125, 400);
		  canvasG.drawLine(150, 25, 150, 105);
		  canvasG.drawLine(150, 137, 150, 400);		  		  // East/West lanes
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
					
				// Adjust lighting				oWestLight.paint(canvasG, true);
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
