/**Piece is the superclass for all other pieces (like Rooks, or Knights).*/
public abstract class Piece {
    /**Allows to use Piece.BLACK to refer to the color black, rather than having to remember some other encoding.*/
    public static final int WHITE = 1;
    /**Allows you to use Piece.BLACK to refer to the color black, rather than having to remember some other encoding.*/
    public static final int BLACK = -1;
    /**The Location of the piece*/    
    protected Location loc;
    /**The Color of the piece*/
    protected final int color;
    /**Creates a Piece of a certain color at a certain location.*/
    protected Piece(char col, int row, int color)throws IllegalArgumentException{
	loc = new Location(col,row);
	this.color = color;
    }
    
    
    /**Creates a Piece of a certain color at a certain location.*/
    protected Piece(Location loc,int color)throws IllegalArgumentException{
	this.loc = loc;
	this.color = color;
    }
   

    /**Returns the Location*/
    public Location getLoc(){
	return this.loc;
    }
    /**Returns the color, matching either Piece.WHITE or Piece.BLACK */
    public int getColor(){
	return color;
    }
    /**To check if moving to toSpot is a legal move for that Piece.*/
    protected abstract boolean checkMove(Location toSpot);
    /**Move the piece from its current location to the input location.*/    
    public abstract boolean makeMove(Location toSpot);
    /** Print out the Unicode character for this chess piece.*/
    public abstract String toString();
    /**Checks the required and available spots for the piece between two spots*/
    public abstract Location[] mustBeOpen(Location toSpot);
}
