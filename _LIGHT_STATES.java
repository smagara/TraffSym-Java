// the set of possible light state transitions
public class _LIGHT_STATES // extends Enum
{
    // Normal Operation
    public static final int LS_EWG  = 0;   // East/West light is Green
    public static final int LS_EWX  = 1;   // East/West light is Green, East/West crosswalks are on
    public static final int LS_NSG  = 2;   // North/South light is Green
    public static final int LS_NSX  = 3;   // North/South light is Green, North/South crosswalks are on
    public static final int LS_ETWT = 4;   // West/East turn arrow is Green
    public static final int LS_WTWG = 5;   // West turn arrow is Green, West light is Green
    public static final int LS_ETEG = 6;   // East turn arrow is Green, East light is Green

    // Emergency Operation
    public static final int LS_WEST  = 7;  // West turn arrow is Green, West light is Green
    public static final int LS_EAST  = 8;  // East turn arrow is Green, East light is Green
    public static final int LS_NORTH = 9;  // North light is Green
    public static final int LS_SOUTH = 10;  // South light is Green

    public static final int LS_MAX_VALUE = 11;
	
	// some static helpers to simplify caller logic
	public static boolean IsNorthGreen(int state)
	{
		if (state == LS_NSX || state == LS_NSG || state == LS_NORTH)
			return true;
		else 
			return false;
	}
	public static boolean IsSouthGreen(int state)
	{
		if (state == LS_NSX || state == LS_NSG || state == LS_SOUTH)
			return true;
		else 
			return false;
	}
	public static boolean IsWestGreen(int state)
	{
		if (state == LS_WEST || state == LS_EWG || state == LS_EWX || state == LS_WTWG)
			return true;
		else 
			return false;
	}
	public static boolean IsWestTurnGreen(int state)
	{
		
		if (state == LS_WTWG || state == LS_ETWT)
			return true;
		else 
			return false;
	}

	public static boolean IsEastTurnGreen(int state)
	{
		if (state == LS_ETWT || state == LS_ETEG)
			return true;
		else 
			return false;
	}

    public static boolean IsEastGreen(int state)
    {
		if (state == LS_EAST || state == LS_EWG || state == LS_EWX || state == LS_ETEG)
			return true;
		else 
			return false;
    }
  
}
