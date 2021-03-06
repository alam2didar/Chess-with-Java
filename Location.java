/**
  * A Location encodes a row and column on a Chess board.  This is preferable
  * to storing these values separately, because it performs error checking; if
  * a Location object exists, then it corresponds to a specific spot on the
  * board.  This is not true of an arbitrary int and char.
  *
  * Locations are stored using Chess' <a
  * href="http://en.wikipedia.org/wiki/Algebraic_notation_%28chess%29">algebraic
  * notation</a>, consisting of an int (1-8) for the row, and a char (a-h) for
  * the columns.
  *
  * Additionally, there are some helpful static methods in here for returning
  * all Locations a piece must go through to move diagonally, for example,
  * from one Location to another.  These are useful for Step 4.
  */
public class Location {
  private int row;
  private char col;

  /**
    * This constructor takes a String consisting of a char, followed by an
    * int, like "a5".
    * 
    * @param s A 2-character String like "a5" encoding a location on the
    * board.
    * @throws IllegalArgumentException If the String cannot be parsed into a
    * valid Location, or if the int and char do not correspond to a spot on
    * the chess board, then it will throw an IllegalArgumentException; the
    * Exception's message contains which one is the problem.
    */
  public Location(String s) throws IllegalArgumentException {
      char col = s.charAt(0);
    int row;
    try {
      Integer i = Integer.decode(s.substring(1,2));
      row = i.intValue();
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException(s + " could not be parsed into " +
          "a Location");
    }
    checkRowCol(col, row);
    this.row = row;
    this.col = col;
  }

  /**
    * This constructor takes a char and an int, and creates a Location object
    * out of it.
    * 
    * @param col A char containing the column of the Location.
    * @param row An int containing the row of the Location.
    * @throws IllegalArgumentException If the parameters do not correspond to
    * a spot on the chess board, then it will throw an
    * IllegalArgumentException.
    */
  public Location(char col, int row) throws IllegalArgumentException {
    checkRowCol(col, row);
    this.row = row;
    this.col = col;
  }

  /**
    * This construct builds a Location from two array indices; in other words,
    * given two ints 0-7, it builds a Location with the matching char (a-h)
    * and int (1-8).
    * @param colIndex The index of the column: (0-7, inclusive)
    * @param rowIndex The index of the row: (0-7, inclusive)
    * @throws IllegalArgumentException if the indices are out of legal bounds
    */
  public Location(int colIndex, int rowIndex) throws IllegalArgumentException {
    this((char)('a'+colIndex), rowIndex+1);
  }

  /**
    * Checks to see if the input col and row are valid chessboard locations.
    * @throws IllegalArgumentException if they are not a valid location.
    */
  private void checkRowCol(char col, int row) throws IllegalArgumentException {
    if (row < 1 || row > 8)
      throw new IllegalArgumentException("Row is bad: " + row);
    if (col < 'a' || col > 'h')
      throw new IllegalArgumentException("Column is bad: " + col);
  }

  /**
    * Returns the row of the Location.  This will be an int between 1-8,
    * inclusive.
    */
  public int getRow() {
    return row;
  }

  /**
    * Returns the col of the Location.  This will be a char between a-h,
    * inclusive.
    */
  public char getCol() {
    return col;
  }

  /**
    * Returns the "computer science" equivalent of the row; in other words,
    * instead of 1-8, it returns 0-7.  This is helpful for putting Locations
    * into arrays.
    */
  public int getRowIndex() {
    return row-1;
  }

  /**
    * Returns the "computer science" equivalent of the col; in other words,
    * instead of a-h, it returns 0-7.  This is helpful for putting Locations
    * into arrays.
    */
  public int getColIndex() {
    return (col-'a');
  }

  /**
    * Returns, for example, the String "a5"
    */
  @Override
  public String toString() {
    return ""+col+row;
  }

  /**
    * Determines if two Locations are equal.
    */
  @Override
  public boolean equals(Object o) {
    if (o.getClass()!=this.getClass())
      return false;
    Location o2 = (Location)o;
    return (row == o2.getRow() && col == o2.getCol());
  }

  /**
    * Given two Locations, on a diagonal, returns an array of Locations
    * between the two, NOT including start, and INCLUDING end.
    *
    * @param start The Location where the chess piece is beginning.
    * @param end The Location where the chess piece is ending.
    * @return The array of Locations between start and end, including end, and
    * not including start.
    * @throws IllegalArgumentException if start and end are not on a diagonal.
    */
  public static Location[] diagLocations(Location start, Location end) {
    // if (Math.abs(end.getRow()-start.getRow())!=
    //     Math.abs(end.getCol()-start.getCol()))
    //   throw new IllegalArgumentException("Other pieces in the way.");
    Location[] array = new Location[Math.abs(end.getRow() - start.getRow())];
    int rInc;
    if (end.getRow() > start.getRow())
      rInc = 1;
    else
      rInc = -1;
    int cInc;
    if (end.getCol() > start.getCol())
      cInc = 1;
    else
      cInc = -1;
    for (int i = 0; i < array.length; i++) {
      array[i] = new Location(
          (char)(start.getCol()+((i+1)*cInc)),
          start.getRow()+((i+1)*rInc) );
    }
    return array;
  }

  /**
    * Given two Locations, on a column, returns an array of Locations
    * between the two, NOT including start, and INCLUDING end.
    *
    * @param start The Location where the chess piece is beginning.
    * @param end The Location where the chess piece is ending.
    * @return The array of Locations between start and end, including end, and
    * not including start.
    * @throws IllegalArgumentException if start and end are not on a column.
    */
  public static Location[] colLocations(Location start, Location end) {
    if (start.getCol() != end.getCol())
      throw new IllegalArgumentException("Illegal Move for this piece.\n");
    Location[] array = new Location[Math.abs(end.getRow() - start.getRow())];
    int inc;
    if (end.getRow() > start.getRow())
      inc = 1;
    else
      inc = -1;
    for (int i = 0; i < array.length; i++) {
      array[i] = new Location( start.getCol(),
          start.getRow()+((i+1)*inc) );
    }
    return array;
  }

  /**
    * Given two Locations, on a row, returns an array of Locations
    * between the two, NOT including start, and INCLUDING end.
    *
    * @param start The Location where the chess piece is beginning.
    * @param end The Location where the chess piece is ending.
    * @return The array of Locations between start and end, including end, and
    * not including start.
    * @throws IllegalArgumentException if start and end are not on a row.
    */
  public static Location[] rowLocations(Location start, Location end) {
    if (start.getRow() != end.getRow())
      throw new IllegalArgumentException("Illegal Move for this piece.\n");
    Location[] array = new Location[Math.abs(end.getCol() - start.getCol())];
    int inc;
    if (end.getCol() > start.getCol())
      inc = 1;
    else
      inc = -1;
    for (int i = 0; i < array.length; i++) {
      array[i] = new Location(
          (char)(start.getCol()+((i+1)*inc)),
          start.getRow());
    }
    return array;
  }
}
