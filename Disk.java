/**This class holds the Rook on the Chess board*/
public class Disk extends Piece {
 
    /**Constructor which calls the super constructor and builds appropriate Bishops respective to their character and color*/
    public Disk (Location loc, int color){
	super(loc, color);
    } 
     
    /**Inherits from piece and defines it.Returns an array of Locations that must all be open for the move to be legal, because most pieces (except Knight) cannot "jump" another Piece to make a move.*/
    
      public Location[] mustBeOpen(Location toSpot){
         if(loc.getCol() == toSpot.getCol())
             return Location.colLocations(loc, toSpot);
         else if(loc.getRow() == toSpot.getRow())
            return Location.rowLocations(loc, toSpot);
         else 
             return Location.diagLocations(loc, toSpot);
     }

    /**To check if moving to toSpot is a legal move for that Piece.
     * @param toSpot the location to make the move
     * @return true or false
     */
    protected boolean checkMove(Location toSpot){
	
	if(loc.getCol() == toSpot.getCol() || loc.getRow() == toSpot.getRow()){
	    return true;
	}
	else {
	    return false;
	}
    }
    
    /**Move the piece from its current location to the input location. If this is a legal move, and the move was successful, then it should return true.
       @param toSpot the location to make the move
       * @return true or false
       */
    public boolean makeMove(Location toSpot){
	if(checkMove(toSpot)){
	    loc = toSpot;
	  
	    return true;
	}
	else
	    return false;
    }


    /**Print out the Unicode character for this chess piece.*/
    public String toString(){
	String s;
	if (getColor()==Piece.WHITE)
	    s="\u25CB";
	else
	    s="\u25CF";
	return s;
    }
}