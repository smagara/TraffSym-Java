// Main Traffic simulator applet.
// Contains all the others
import java.awt.*;
import java.applet.*;
import java.util.Date;

public class TraffSym extends Applet
{
	// State variables
	private _INTERSECTION_STATE IntersectionState = new _INTERSECTION_STATE();
	private _INPUT_VECTOR InputVector = new _INPUT_VECTOR();
	
	private DrawCanvas drawingArea = new DrawCanvas(IntersectionState, InputVector);

	// text input gui
	Label lblEastWestT = new Label("East/West cars/min:");
	Label lblNorthSouthT = new Label("North/South cars/min:");
	Label lblTurnT = new Label("Turning Lanes cars/min:");
	Label lblEastWestL = new Label("East/West light duration:");
	Label lblNorthSouthL = new Label("North/South light duration:");
	Label lblTurnL = new Label("Turning Lanes light duration:");
	TextField txtEastWestT   = new TextField("15");
	TextField txtNorthSouthT = new TextField("10");
	TextField txtTurnT       = new TextField("5");
	TextField txtEastWestL   = new TextField("40");
	TextField txtNorthSouthL = new TextField("20");
	TextField txtTurnL       = new TextField("15");


	// buttons
	Button btnStart;
	Button btnNS_East;
	Button btnStop;
	
	/**
	 * The entry point for the applet. 
	 */
	public void init()
	{
		// Initial light state
		IntersectionState.State = _LIGHT_STATES.LS_EWG;
		
		this.setBackground(Color.lightGray);
		this.setForeground(Color.black);
		this.setLayout(new BorderLayout());

		// Setup input boxes
		lblEastWestT.setForeground(Color.black);
		lblNorthSouthT.setForeground(Color.black);
		lblTurnT.setForeground(Color.black);
		lblEastWestL.setForeground(Color.black);
		lblNorthSouthL.setForeground(Color.black);
		lblTurnL.setForeground(Color.black);

		txtEastWestT.setForeground(Color.black);
		txtNorthSouthT.setForeground(Color.black);
		txtTurnT.setForeground(Color.black);
		txtEastWestL.setForeground(Color.black);
		txtNorthSouthL.setForeground(Color.black);
		txtTurnL.setForeground(Color.black);

		setLayout(new BorderLayout());
	
		drawingArea.setSize(300, 200);
		add("Center", drawingArea);

		// layout the upper input panel
		panText.add(lblEastWestT);   panText.add(txtEastWestT);
		panText.add(lblTurnT);       panText.add(txtTurnT);

		Panel panButton = new Panel();
		btnStart = new Button("Start");
		btnNS_East = new Button("Walk-East");
		btnStart.setForeground(Color.black);
		panButton.add(btnStart);
		btnStop = new Button("Stop");
		btnStop.setForeground(Color.black);
		panButton.add(btnNS_East);
		panButton.add(btnStop);
		
		add("South", panButton);
		
		drawingArea.paint(drawingArea.getGraphics());		
		// TODO: Add any constructor code after initForm call.
	}
	

	private	final String labelParam = "label";

	/**
	 * Converts a string formatted as "rrggbb" to an awt.Color object
	 */
	private Color stringToColor(String paramValue)
	{
		int red;
		int green;
		int blue;

		red = (Integer.decode("0x" + paramValue.substring(0,2))).intValue();
		green = (Integer.decode("0x" + paramValue.substring(2,4))).intValue();
		blue = (Integer.decode("0x" + paramValue.substring(4,6))).intValue();

		return new Color(red,green,blue);
	}

	/**
	 * External interface used by design tools to show properties of an applet.
	 */
	public String[][] getParameterInfo()
	{
		String[][] info =
		{
			{ "background", "String", "Background color, format \"rrggbb\"" },
			{ "foreground", "String", "Foreground color, format \"rrggbb\"" },
		};
		return info;
	}

	// Respond to button clicks
	// ignore the deprecated warning from this function
	public boolean action (Event evt, Object arg) 
	{
		try
		{
			{
				InputVector.EastWestPerMin   = Long.parseLong(txtEastWestT.getText());
				InputVector.NorthSouthPerMin = Long.parseLong(txtNorthSouthT.getText());
				InputVector.MaxNsGreen       = Long.parseLong(txtNorthSouthL.getText());
				drawingArea.paint(drawingArea.getGraphics());
				execObject = new Executive(IntersectionState, InputVector, drawingArea);
				if (!execObject.IsRunning)
				btnStart.setEnabled(false);
			
				// West walk button pressed
				IntersectionState.WestWalk = true;
			else if (evt.target==btnNS_East)
				// East walk button pressed
				IntersectionState.EastWalk = true;
					
			else if (evt.target==btnStop) 
			{
				if (execObject != null)
			e.notifyAll();
				
		return true;
	}
	
} // end main