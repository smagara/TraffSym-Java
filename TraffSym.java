// Main Traffic simulator applet.
// Contains all the others
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TraffSym extends Frame // Applet
{
	// State variables
	private _INTERSECTION_STATE IntersectionState = new _INTERSECTION_STATE();
	private _INPUT_VECTOR InputVector = new _INPUT_VECTOR();
	
	private DrawCanvas drawingArea = new DrawCanvas(IntersectionState, InputVector);
	private Executive execObject;

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
	Button btnNS_West;
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
	
		drawingArea.setSize(800, 500);
		add("Center", drawingArea);

		// layout the upper input panel
		Panel panText = new Panel(new GridLayout(3, 4, 5, 5));
		panText.setForeground(Color.gray);
		panText.add(lblEastWestT);   panText.add(txtEastWestT);
		panText.add(lblEastWestL);   panText.add(txtEastWestL);
		panText.add(lblNorthSouthT); panText.add(txtNorthSouthT);
		panText.add(lblNorthSouthL); panText.add(txtNorthSouthL);
		panText.add(lblTurnT);       panText.add(txtTurnT);
		panText.add(lblTurnL);       panText.add(txtTurnL);

		// layout lower button panel
		Panel panButton = new Panel();
		panButton.setLayout(new GridLayout(1,3,20,10));
		btnStart = new Button("Start");
		btnNS_West = new Button("Walk-West");
		btnNS_East = new Button("Walk-East");
		btnStart.setForeground(Color.black);
		panButton.add(btnStart);
		panButton.add(new Label("  ")); 
		btnStop = new Button("Stop");
		btnStop.setForeground(Color.black);
		panButton.add(btnNS_West);
		panButton.add(btnNS_East);
		panButton.add(btnStop);
		
		add("North", panText);
		add("South", panButton);

		setupListener(btnStart);
		setupListener(btnNS_East);
		setupListener(btnNS_West);
		setupListener(btnStop);
		
		drawingArea.paint(drawingArea.getGraphics());		
		// TODO: Add any constructor code after initForm call.
	}
	
	// wire up buttons to the shared legacy handler function
	private void setupListener(Button btn)
	{
		btn.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action(e);
			}
		});
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
	public boolean action (ActionEvent evt) 
	{
		try
		{
			if (evt.getSource()==btnStart) 
			{
				// fetch epochs from text field
				InputVector.EastWestPerMin   = Long.parseLong(txtEastWestT.getText());
				InputVector.NorthSouthPerMin = Long.parseLong(txtNorthSouthT.getText());
				InputVector.TurnPerMin       = Long.parseLong(txtTurnT.getText());
				InputVector.MaxEwGreen       = Long.parseLong(txtEastWestL.getText());
				InputVector.MaxNsGreen       = Long.parseLong(txtNorthSouthL.getText());
				InputVector.MaxTurnGreen     = Long.parseLong(txtTurnT.getText());

				IntersectionState.State = 2;
				drawingArea.paint(drawingArea.getGraphics());
				execObject = null;
				execObject = new Executive(IntersectionState, InputVector, drawingArea);
				if (!execObject.IsRunning)
					execObject.start();
				btnStart.setEnabled(false);
			}
			
			else if (evt.getSource()==btnNS_West)
			{
				// West walk button pressed
				IntersectionState.WestWalk = true;
			}
			else if (evt.getSource()==btnNS_East)
			{
				// East walk button pressed
				IntersectionState.EastWalk = true;
			}
					
			else if (evt.getSource()==btnStop) 
			{
				if (execObject != null)
					execObject.stop();
				btnStart.setEnabled(true);
		    	drawingArea.getGraphics().clearRect(0, 0, 1000, 1000);
				drawingArea.paint(drawingArea.getGraphics());
			}
		}
		catch (Exception e)
		{
			e.notifyAll();
		}
				
		return true;
	}
	
	// main entry point: show initial form
        public static void main(String[] args)
        {
			TraffSym appletNot = new TraffSym();
			appletNot.setBounds(50, 50, 900, 400);
			appletNot.setVisible(true);
        	appletNot.addWindowListener(
                        new WindowAdapter()
                        {
                                public void windowClosing(WindowEvent e)
                                {
                                        System.exit(-1);
                                }
                        }
                );
			appletNot.init();
			appletNot.revalidate();
			appletNot.repaint();
        }

} // end main
