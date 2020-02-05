/**The Board class does much of the work here, keeping track of what pieces
 * are where, and drawing the board on the screen.  
 */
public class Board {
    /**These two fields are used to draw dark and light squares on the terminal
     * for empty squares. */
    private static final char hoz='\u2550';
    private static final char ver='\u2551';
    protected static int move_W = 0;
    protected static int move_B = 0;
    /**These two fields are used to control the diagonal movements of the Pawn*/
    protected static boolean capture_W = false;
    protected static boolean capture_B = false;
    /**This field contains the board.  Every element either holds a Piece
     * object, or is null.  A null element indicates an empty square. */
    private Piece[][] board = new Piece[8][8];
    /**Builds a Board object, filling in the board array.  You can either call
     * setUpBoard, which sets up the full board, or setUpBeginningBoard, which
     * only sets up Rooks.*/
    public Board() {
     
        setUpBeginningBoard();
    }
 
    /**Builds two Rook objects per side, and assigns them to the array.*/ 
    private void setUpBeginningBoard() {
        board[3][3] = new Disk(new Location(0,0),Piece.WHITE);
        board[4][4] = new Disk(new Location(7,0),Piece.WHITE);
    
        board[3][4] = new Disk(new Location(0,7),Piece.BLACK);
        board[4][3] = new Disk(new Location(7,7),Piece.BLACK);
    }
    
    public String toString() {
        String s = "\n   ";
        int r = 0, row=0;
        // print the column chars across the top
        for (int c = 0; c < 8; c++)
            s+= (char)(c+'A') + "  ";
        //end the column labels line
        s+='\n'+"  ";
        for(int i = 0;i < 8; i++){
            for (int c = 0; c < 25; c++)
                s+= hoz;
            //end the column labels line
            s+= '\n'+" "+(r+1);
            r++;
            for (int c = 0, col=0; c < 9; c++,col++){
                s+= ver;
                if( col<=7 && board[row][col] != null){
                    s+=board[row][col].toString()+" ";
                }
                else{
                    s+="  ";
                }
            }
            //end the column labels line
            s+='\n'+"  ";
            row++;

            
       
        }
        for (int c = 0; c < 25; c++){
            s+= hoz;
        }
        return s;
    }
    public boolean checkMove(int ar, int ac, String clr){
        if(clr.equals("W")){
        if(ar<7&&(board[ar+1][ac] != null) && (board[ar+1][ac].getColor()!= Piece.WHITE))
            return true;
        if(ar>0&&(board[ar-1][ac] != null) && (board[ar-1][ac].getColor()!= Piece.WHITE))
            return true;
        if(ac<7&&(board[ar][ac+1] != null) && (board[ar][ac+1].getColor()!= Piece.WHITE))
            return true;
        if(ac>0&&(board[ar][ac-1] != null) && (board[ar][ac-1].getColor()!= Piece.WHITE))
            return true;
        if(ar<7&&ac<7&&(board[ar+1][ac+1] != null) && (board[ar+1][ac+1].getColor()!= Piece.WHITE))
            return true;
        if(ar<7&&ac>0&&(board[ar+1][ac-1] != null) && (board[ar+1][ac-1].getColor()!= Piece.WHITE))
            return true;
        if(ar>0&&ac<7&&(board[ar-1][ac+1] != null) && (board[ar-1][ac+1].getColor()!= Piece.WHITE))
            return true;
        if(ar>0&&ac>0&&(board[ar-1][ac-1] != null) && (board[ar-1][ac-1].getColor()!= Piece.WHITE))
            return true;
        }
        else if(clr.equals("B")){
            if(ar<7&&(board[ar+1][ac] != null) && (board[ar+1][ac].getColor()!= Piece.BLACK))
                return true;
            if(ar>0&&(board[ar-1][ac] != null) && (board[ar-1][ac].getColor()!= Piece.BLACK))
                return true;
            if(ac<7&&(board[ar][ac+1] != null) && (board[ar][ac+1].getColor()!= Piece.BLACK))
                return true;
            if(ac>0&&(board[ar][ac-1] != null) && (board[ar][ac-1].getColor()!= Piece.BLACK))
                return true;
            if(ar<7&&ac<7&&(board[ar+1][ac+1] != null) && (board[ar+1][ac+1].getColor()!= Piece.BLACK))
                return true;
            if(ar<7&&ac>0&&(board[ar+1][ac-1] != null) && (board[ar+1][ac-1].getColor()!= Piece.BLACK))
                return true;
            if(ar>0&&ac<7&&(board[ar-1][ac+1] != null) && (board[ar-1][ac+1].getColor()!= Piece.BLACK))
                return true;
            if(ar>0&&ac>0&&(board[ar-1][ac-1] != null) && (board[ar-1][ac-1].getColor()!= Piece.BLACK))
                return true;
        }
        
            return false;

    }
    
    public boolean makeMove(String spot, String color){
        boolean val = false;
        int r=0 ,w =0;
        Location a = new Location(spot);
        // Get that location's indices for the board array
        int ar = a.getRowIndex();
        int ac = a.getColIndex();
        // If there is no piece at that location, throw an Exception
        if(board[ar][ac] == null && checkMove(ar,ac, color)&& color.equals("W")){
            board[ar][ac] = new Disk(new Location(ar,ac),Piece.WHITE); 
            val=true;
        }
        else if(board[ar][ac] == null && checkMove(ar,ac, color)&& color.equals("B")){
            board[ar][ac] = new Disk(new Location(ar,ac),Piece.BLACK); 
            val=true;    
        }
        
        if(val){
        if(ar<7 && board[ar+1][ac] != null && board[ar+1][ac].getColor()!= board[ar][ac].getColor()){
            for(int i=ar+1, j=ac; i<=7; i++){
                if( board[i][j]!=null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
            
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;        
        }
        else if(ar>0 && board[ar-1][ac]!= null && board[ar-1][ac].getColor()!= board[ar][ac].getColor()){
            for(int i=ar-1, j=ac; i>=0; i--){
                if( board[i][j] != null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
            
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;        
        } else if(ac<7&&board[ar][ac+1]!= null && board[ar][ac+1].getColor()!= board[ar][ac].getColor()){
            for(int i=ar, j=ac+1; j<=7; j++){
                if( board[i][j] != null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
            
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;        
        } else if(ac>0&&board[ar][ac-1]!= null && board[ar][ac-1].getColor()!= board[ar][ac].getColor()){
            for(int i=ar, j=ac-1; j>=0; j--){
                if( board[i][j] != null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
            
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;        
        } 
        else if(ar<7&&ac<7&&(board[ar+1][ac+1] != null) && (board[ar+1][ac+1].getColor()!= board[ar][ac].getColor())){
            for(int i=ar+1, j=ac+1; i<=7&&j<=7; i++,j++){
                if( board[i][j] != null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
        
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;        
        } else if(ar<7&&ac>0&&(board[ar+1][ac-1] != null) && (board[ar+1][ac-1].getColor()!= board[ar][ac].getColor())){
            for(int i=ar+1, j=ac-1; i<=7 && j>=0; i++,j--){
                if( board[i][j] != null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
        
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;        
        }else if(ar>0&&ac<7&&(board[ar-1][ac+1] != null) && (board[ar-1][ac+1].getColor()!= board[ar][ac].getColor())){
            for(int i=ar-1, j=ac+1; i>=0 && j<=7; i--,j++){
                if( board[i][j] != null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
        
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;        
        }else if(ar>0&&ac>0&&(board[ar-1][ac-1] != null) && (board[ar-1][ac-1].getColor()!= board[ar][ac].getColor())){
            for(int i=ar-1, j=ac-1; i>=0 || j>=0; i--,j--){
                if( board[i][j] != null && board[i][j].getColor()== board[ar][ac].getColor())
                    r=i;w=j;
            }
        
            Location loc = new Location (r,w);
            Location [] l =  board[ar][ac].mustBeOpen(loc);
            for(int i = 0; i < l.length-2; i++){
                if(board[ar][ac].getColor()==Piece.WHITE){
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.WHITE); 
                }
                else{
                    board[l[i].getRowIndex()][l[i].getColIndex()] = new Disk(new Location(l[i].getRowIndex(),l[i].getColIndex()),Piece.BLACK); 
                }
            }
            
            val =true;     
        }   
        }

        else 
            val = false;
        
        

        return val;
        
    }
    
}