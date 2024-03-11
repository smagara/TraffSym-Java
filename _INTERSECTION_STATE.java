// This structure fully describes the state of all the inputs
// and outputs of the system at any given point in time.
public class _INTERSECTION_STATE
{
	_INTERSECTION_STATE()
	{
	}
	
    // Outputs
    int State;

    // Inputs
    boolean NorthWalk;
    boolean SouthWalk;
    boolean EastWalk;
    boolean WestWalk;
    boolean EastTurn;
    boolean WestTurn;
    boolean Northbound;
    boolean Southbound;

	// Counter
	long Counter;

    int  Day;
    int  Hour;
    int  Minute;
    int  Second;

}
