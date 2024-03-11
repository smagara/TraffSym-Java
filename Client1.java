/* This Client1 used to transfer 2000 float numbers to ServerTest2*/

import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;


public class Client1 extends Applet
{
	int svpt = 5001; 
	float N=0;
	float M=0;
	Socket svskt = null;
	  
//	String svhost = "herb.engr.ucf.edu";
	String svhost = "132.170.204.72";
  
	DataInputStream dis = null;
	DataOutputStream dos = null;
  
	Button Btn;
	String Btn_string = "Run" ;
	Button Reset;
	String Reset_string = "Reset";
	Label size_descr = new Label("Input Set Value ");
	Label size_descr2 = new Label("Input Control Gain");
	
	TextField num1Field = new TextField("2");
	TextField num2Field = new TextField("1");
    
	Label Chk;
	String Chk_istr = "Transforted Result: ";
	
	Label Chk2;
	String Chk_istr2 = "Deviation Value= ";
	
	MyCanvas drawingArea = new MyCanvas();
	
    public void init () 
    {
		setLayout(new BorderLayout());
	
		drawingArea.resize(400, 200);
		add("Center", drawingArea);

		Panel descr_p = new Panel();
		
		size_descr.setForeground(Color.black);
		size_descr2.setForeground(Color.black);
	
		descr_p.setLayout(new GridLayout(1, 2));
		Panel choice_p = new Panel();
		Panel button_p = new Panel();
		choice_p.setLayout(new GridLayout(4,2)); 
		button_p.setLayout(new BorderLayout());
		choice_p.setForeground(Color.gray);
		choice_p.add(size_descr);choice_p.add(new Label("  "));
		choice_p.add(num1Field);choice_p.add(new Label("  "));
		choice_p.add(size_descr2);choice_p.add(new Label("  "));
		choice_p.add(num2Field);  choice_p.add(new Label("  "));
	
		Chk = new Label("        ",Label.CENTER);
		Chk.setForeground(Color.blue);
		Chk2 = new Label("        ",Label.CENTER);
		Chk2.setForeground(Color.blue);
	
		button_p.add("West",new Label("      ")); 
		button_p.add("North",Chk);
		button_p.add("Center", Chk2); 
		descr_p.add(choice_p);
		descr_p.add(button_p);
		add("North", descr_p);

		Panel reset_p = new Panel();
		reset_p.setLayout(new GridLayout(1,3,20,10));
	
		Btn = new Button(Btn_string);
		Btn.setForeground(Color.black);
		reset_p.add(Btn);reset_p.add(new Label("  ")); 
		Reset = new Button(Reset_string);
		Reset.setForeground(Color.black);
	
		reset_p.add(Reset);
		add("South", reset_p);

	}

final int setupsocket()
{
// bind to the socket
	try 
	{
		System.out.println("Contact with host: " +svhost);

		svskt = new Socket(svhost,svpt);
		System.out.println(svskt.getInetAddress().getHostName());
		System.out.println(svskt.getPort());
		System.out.println(svskt.getLocalPort());
      
	} catch (UnknownHostException e) 
	{
		System.err.println("Don't know about host: "+svhost);
		return(1);
	} catch (IOException e) 
	{
		System.err.println("Couldn't get socket connection"+e);
		return(1);
	} catch (SecurityException e) 
	{
		System.err.println("SecEx: " +e);
	}


	try 
	{ 
		dis = new DataInputStream(new BufferedInputStream(svskt.getInputStream()));
                dos = new DataOutputStream(new BufferedOutputStream(svskt.getOutputStream()));
	} catch (IOException e) 
	{
		System.err.println("Couldn't get I/O connection"+e);
		return(2);
	}

	return(0);
}

public boolean action(Event evt, Object arg) 
{
  if (evt.target==Btn) 
  {
	Btn.setLabel("...Running ");
		
	N = (new Float(num1Field.getText())).floatValue();
	M = (new Float(num2Field.getText())).floatValue();

	setupsocket();

	try 
	{
  	  dos.writeFloat(N);
  	  dos.writeFloat(M);

	  dos.flush();
	} catch (IOException e) 
	{
	  System.err.println("IOException:  " + e);
	  return(false);
	}

	float[] seed ; seed = new float[500];

	int num;
    for (num=0;num<100;num++)
	{
		try 
		{ 
			seed[num] = dis.readFloat();
			
			System.out.println("Seed of the Moment: " + seed[num] );
 		} catch (IOException e) 
		{
			System.err.println("IOException:  " + e);
			return(false);
		}
               drawingArea.paint(drawingArea.getGraphics(), seed, num);
		String Chk_str = Chk_istr;
		String Chk_str2 = Chk_istr2 + seed[num] +"   ";
	
		Chk.setText(Chk_str);
		Chk2.setText(Chk_str2);
        }
	Btn.setLabel("...Completed! ");
	
	
  }
		
 
  else if (evt.target==Reset) 
  {
	Btn.setLabel(Btn_string);
	Chk.setText("  ");
	Chk2.setText("  ");
	drawingArea.reset_n();
  } 
	
  return true;
 }
}

class MyCanvas extends Canvas
{	static int n = 1;
	MyCanvas()
	{
		super();
		setBackground(Color.white);
	}
	
	public void reset_n()
	{
	   n = 1;
	   repaint();
	}
	 
	public void paint(Graphics canvasG)
	{
	      int temp1 = 25;
	      int temp2 = 25;
	      int temp3 = 400;
	      int temp4 = 300;
	      
	      canvasG.setColor(Color.black);
              canvasG.drawLine(temp1, temp4, temp3, temp4);
              canvasG.drawLine(temp3, temp4, temp3-10, temp4-2);
              canvasG.drawLine(temp3, temp4, temp3-10, temp4+2);

              canvasG.drawLine(temp1, temp2, temp1, temp4);
              canvasG.drawLine(temp1, temp2, temp1-2, temp2 + 10);
              canvasG.drawLine(temp1, temp2, temp1+2, temp2 + 10);
              
              for (int k=1; k<11; k++){
                int grid = 25*k;
                canvasG.drawLine(temp1, temp4 - grid, temp1 + 5, temp4 - grid);
                canvasG.drawString(k+".0", temp1- 20, temp4 - grid + 5);
               }
               
              canvasG.drawString("Time", temp3-10, temp4-10);
              canvasG.drawString("Deviation ", temp1 - 15, temp2 - 15);
              canvasG.drawString("  (v)", temp1 - 15, temp2 - 2);
         
	}
	
	public void paint(Graphics canvasG, float s[], int num)
	{    n++;
	      int temp1 = 25;
	      int temp2 = 25;
	      int temp3 = 400;
	      int temp4 = 300;
	      
	   //   System.out.println("test the drawing: " + s[num] );
	      
	      canvasG.setColor(Color.black);
              canvasG.drawLine(temp1, temp4, temp3, temp4);
              canvasG.drawLine(temp3, temp4, temp3-10, temp4-2);
              canvasG.drawLine(temp3, temp4, temp3-10, temp4+2);

              canvasG.drawLine(temp1, temp2, temp1, temp4);
              canvasG.drawLine(temp1, temp2, temp1-2, temp2 + 10);
              canvasG.drawLine(temp1, temp2, temp1+2, temp2 + 10);
              
              for (int k=1; k<11; k++){
                int grid = 25*k;
                canvasG.drawLine(temp1, temp4 - grid, temp1 + 5, temp4 - grid);
                canvasG.drawString(k+".0", temp1- 20, temp4 - grid + 5);
               }
               
              canvasG.drawString("Time", temp3-10, temp4-10);
              canvasG.drawString("Deviation ", temp1 - 15, temp2 - 15);
              canvasG.drawString("  (v)", temp1 - 15, temp2 - 2);
              
              if (num > 0)
                 
                 canvasG.drawLine(25+4*(n-1), 300-(int)(25*s[num-1]), 
25+4*n, 300-(int)(25*s[num]));  
	}
}


