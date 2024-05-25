// Name: Zhien Yu
// USC NetID: zhienyu
// CS 455 PA3
// Fall 2023
import java.util.Random;

/** 
   MineField
      Class with locations of mines for a minesweeper game.
      This class is mutable, because we sometimes need to change it once it's created.
      Mutators: populateMineField, resetEmpty
      Includes convenience method to tell the number of mines adjacent to a location.
      This class can be used by VisibleField to construct a mineSweeper game.
 */
public class MineField {
   /*
   This is a Class that utilize an 2D array to store information of mines in a field.
   This class will be a part of further implementation of a Mine Sweeper game. 
   The boolean 2D array of the MineField will contains true if the suqare is a mine, false otherwise. 
   Client can generate a MineField object by 1-arg constructor or 3-arg constructor. 
   */
   /**
      Representation invariant:

      The myMineField must have myMineField.length >= 1 and myMineField[0].length >=1.
      The number of mines in the field must have numMines() < (1/3 * numRows() * numCols().
      The row and col that accepted as parameters for all the methods must have inRange(row, col),
      which means 0<= row < numRows(),  0<= col < numCols().
      For all 0 <= i <numRows(), myMineField[i].length keeps the same. 

   */
   // <put instance variables here>
   // myMindField will store the mine information.
   // true means that there is a mine, false otherwise. 
   private boolean[][] myMineField;
   // number of mines in the field
   private int numMines; 
   
   
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in
      the array such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice
      versa.  numMines() for this minefield will corresponds to the number of 'true' values in 
      mineData.
      @param mineData  the data for the mines; must have at least one row and one col,
                       and must be rectangular (i.e., every row is the same length)
    */
   public MineField(boolean[][] mineData) {
      
      numMines = 0;
      int numRow = mineData.length;
      int numCol = mineData[0].length;
      // make defensive copy of data
      myMineField = new boolean[numRow][numCol];
      for(int i = 0; i < numRow; i++){
         for(int j = 0; j < numCol; j++){
            myMineField[i][j] = mineData[i][j];
            if (mineData[i][j] == true){
               // count the number of mines during copy
               numMines++;
            }
         }
      }
      
   }
   
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a 
      MineField, numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
      myMineField = new boolean[numRows][numCols];
      this.numMines = numMines;
      // haven't puted the mines
      for(int i = 0; i < numRows; i++){
         for(int j = 0; j < numCols; j++){
            myMineField[i][j] = false;
         }
      }
      
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on
      the minefield, ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col) and numMines() < (1/3 * numRows() * numCols())
    */
   public void populateMineField(int row, int col) {
      
      resetEmpty();
      int[] positionAvoid = {row, col};
      for(int i = 0; i < numMines; i++){
         int[] posMine = pickPos(positionAvoid);
         myMineField[posMine[0]][posMine[1]] = true;
      }
      
   }
   
   
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or
      numCols().  Thus, after this call, the actual number of mines in the minefield does not match
      numMines().  
      Note: This is the state a minefield created with the three-arg constructor is in at the 
      beginning of a game.
    */
   public void resetEmpty() {
      
         
      int numRow = myMineField.length;
      int numCol = myMineField[0].length;

      for(int i = 0; i < numRow; i++){
         for(int j = 0; j < numCol; j++){
            myMineField[i][j] = false;
         }
      }

      
      
         
      
      
   }

   
  /**
     Returns the number of mines adjacent to the specified location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
      // adgMine will count the adjacent Mines.
      int adgMine = 0;
      // adjacent location from row,col - 1 to row,col + 1 
      for(int i = row - 1; i <= row + 1; i++){
         for(int j = col - 1; j <= col + 1; j++){
            // if the position i,j is valid
            if(inRange(i, j)){
               // if it is a mine, count
               if(myMineField[i][j]){
                  adgMine++;
               }
               
            }
         }
         
      }
      // if the position of row,col itself have a mine
      // we should not count it
      // thus minus one. 
      if(myMineField[row][col]){
          adgMine--;
      }
         
      return adgMine;      
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {
      // find the numRow and numCol
      int numRow = myMineField.length;
      int numCol = myMineField[0].length;
      // if the row index is smaller than 0
      // or largers/equals to number of rows
      if(row < 0 || row >= numRow){
         // invalid position
         return false;
      }
      // same for column
      if(col < 0 || col >= numCol){
         // invalid position
         return false;
      }
      return true;       
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return myMineField.length;       
   }
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */    
   public int numCols() {
      return myMineField[0].length;       
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {
      return myMineField[row][col];       
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg
      constructor, some of the time this value does not match the actual number of mines currently
      on the field.  See doc for that constructor, resetEmpty, and populateMineField for more
      details.
      @return number of mines
    */
   public int numMines() {
      
      return numMines;
      
   }
   
   /**
      Returns a string output of the Mine Field. 
      The format would be T for the place with mine, F other wise.
      Each row seperated by a space. 
      @return a string of Mine Field.
    */
   
   public String toString(){
      // store the toString value in output
      String output = "";
      for(int i = 0; i < numRows(); i++){
        
         for(int j = 0; j < numCols(); j++){
            if(myMineField[i][j] ){
               // if contains mine, show T
               output += "T";
            }else{
               // F otherwise
               output += "F";
            }
         }
         output += " ";
        
      }
      
      return output;
   }

   
   // <put private methods here>
  /**
      Returns a arrary with length of 2 which contains a position for mine.
      The position get picked randomly 
      and should be a non-occupied position at myMineField. 
      If the position get occupied, 
      call the method itself untile getting a non-occupied one. 
      The returned position should not be the position of posAvoid.
      @param posAvoid is a array with length of 2. Indicationg a position.
         with row index at index 0, column index at index 1.
      @return a int array with row index at index 0, column index at index 1.
   */ 
  private int[] pickPos(int[] posAvoid){
     
     int numRow = myMineField.length;
     int numCol = myMineField[0].length;
     
     Random random = new Random();
     // get a random position
     int randomRow = random.nextInt(numRow);
     int randomCol = random.nextInt(numCol);
     
     
     int[] position = {randomRow, randomCol};
     // if the position is occupied
     if(myMineField[randomRow][randomCol]){
        position = pickPos(posAvoid);
     }else if(randomRow == posAvoid[0] && randomCol == posAvoid[1]){
        // if the position is the position we need to avoid
        position = pickPos(posAvoid);
     }
     return position;
        
        
     
  }
  
  
         
}

