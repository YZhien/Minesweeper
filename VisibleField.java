// Name: Zhien Yu
// USC NetID: zhienyu                 
// CS 455 PA3
// Fall 2023


/**
  VisibleField class
  This is the data that's being displayed at any one point in the game (i.e., visible field, because
  it's what the user can see about the minefield). Client can call getStatus(row, col) for any 
  square.  It actually has data about the whole current state of the game, including the underlying
  mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), 
  isGameOver().  It also has mutators related to actions the player could do (resetGameDisplay(),
  cycleGuess(), uncover()), and changes the game state accordingly.
  
  It, along with the MineField (accessible in mineField instance variable), forms the Model for the
  game application, whereas GameBoardPanel is the View and Controller in the MVC design pattern.  It
  contains the MineField that it's partially displaying.  That MineField can be accessed
  (or modified) from outside this class via the getMineField accessor.  
  
  VieisbleField can be used to construct a MineSweeper game. It shows the covered and uncovered data of a MineField object. 
 */
public class VisibleField {
   /**
      Representation invariant:

      The myField must have myView.length = myField.numRows() and myView[0].length = myField.numCols().
      
      The row and col that accepted as parameters for all the methods must have myField.inRange(row, col)
      For all 0 <= i <myView.length, myView[i].length keeps the same. 

   */
   
   // ----------------------------------------------------------   
   // The following public constants (plus values [0,8] mentioned in comments below) are the
   // possible states of one location (a "square") in the visible field (all are values that can be
   // returned by public method getStatus(row, col)).
   
   // The following are the covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // The following are the uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this opened square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already
                                          // (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of
                                                  // losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused
                                                 // you to lose)
   // ----------------------------------------------------------   
  
   // <put instance variables here>
   // the mineField that contains the mines. 
   private MineField myField;
   // the visible Field get stored in the myView.
   private int[][] myView;
   // store wheather the game is over
   private boolean gameOver;

   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the locations covered, no mines guessed, and the game not
      over.
      @param mineField  the minefield to use for for this VisibleField
    */
   public VisibleField(MineField mineField) {
      myField = mineField;
      gameOver = false;
      // record the given mineField size
      int nRow = mineField.numRows();
      int nCol = mineField.numCols();
      
      myView = new int[nRow][nCol];
      // initial all place with covered.
      for(int i = 0; i < nRow; i++){
         for(int j = 0; j < nCol; j++){
            myView[i][j] = COVERED;
            
         }
         
         
      }
   }
   
   
   /**
      Reset the object to its initial state (see constructor comments), using the same underlying
      MineField. 
   */     
   public void resetGameDisplay() {
      // set gameOver to false, indicating that this is a new game. 
      gameOver = false;
      // iterate through the whole view. 
      for(int i = 0; i < myField.numRows(); i++){
         for(int j = 0; j < myField.numCols(); j++){
            // set all elements to COVERED
            myView[i][j] = COVERED;
            
         }
         
      }
      
   }
  
   
   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   public MineField getMineField() {
      return myField;       
   }
   
   
   /**
      Returns the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  See the public constants at the
            beginning of the class for the possible values that may be returned, and their meanings.
      PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {
      return myView[row][col];       
   }

   
   /**
      Returns the the number of mines left to guess.  This has nothing to do with whether the mines
      guessed are correct or not.  Just gives the user an indication of how many more mines the user
      might want to guess.  This value will be negative if they have guessed more than the number of
      mines in the minefield.     
      @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      // the number of guessed mine will be store in guessed. 
      int guessed = 0;
      for(int i = 0; i < myField.numRows(); i++){
         for(int j = 0; j < myField.numCols(); j++){
            // if guessed, increase number of guessed
            if(myView[i][j] == MINE_GUESS){
               guessed++;
            }
            
         }
         
      }
      // return the difference between number of mine and guessed. 
      return myField.numMines() - guessed;       

   }
 
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on
      a COVERED square changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to
      QUESTION;  call on a QUESTION square changes it to COVERED again; call on an uncovered square
      has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
    */
   public void cycleGuess(int row, int col) {
      
      // if COVERED, change to MINE_GUESS
      if(myView[row][col] == COVERED){
         myView[row][col] = MINE_GUESS;
      }else if(myView[row][col] == MINE_GUESS){
         // if MINE_GUESS, change to QUESTION
         myView[row][col] = QUESTION;
      }else if(myView[row][col] == QUESTION){
         // if QUESTION, change to COVERED
         myView[row][col] = COVERED;
      }
      
   }

   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in the
      neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form (possibly along with
      parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
      or a loss (opened a mine).
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {
      // if the square is MINE_GUESS, don't uncover it.  
      if(myView[row][col] != MINE_GUESS){
         // if the uncovered place is not mine
         if(myField.hasMine(row, col)){
            // show that this mine has been exploded
            myView[row][col] = EXPLODED_MINE;
            exploedMine();
            return false;
         }else{
            // show the number of mines in nearby area
            int adjMine = myField.numAdjacentMines(row, col);
            myView[row][col] = adjMine;
            // if not win and no mine in adjecent fields
            // if win, the winning view will get displayed. 
            if(!win() && adjMine == 0){
               for(int i = row - 1; i <= row + 1; i++){
                  for(int j = col - 1; j <= col +1; j++){
                     // if the square has not been opened
                     // also, it is inRange
                     if(myField.inRange(i,j)){
                        if(!isUncovered(i, j)){
                           uncover(i, j);

                        }

                     }

                  }

               }
            }
         }
         
      }
      return true;
      
   }
 
   
   /**
      Returns whether the game is over.
      (Note: This is not a mutator.)
      @return whether game has ended
    */
   public boolean isGameOver() {
      return gameOver;      
   }
 
   
   /**
      Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
      vs. any one of the covered states).
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
      // all the covered status has been fixed to negative number
      // hence, return true when there is a positive number or zero
      if(myView[row][col] >= 0){
         
         return true;
      }
      return false;       
   }
   
 
   // <put private methods here>
   /**
      exploedMine does not return anything. It would get called if the client opens a mine.
      It would change the gameOver variable to true. 
      It would show all the MINES who was in uncovered state. 
      It keeps the correct guessed mine. 
      It would turn the view to show all the mines haven't been guessed and wrongly guessed.
    */
   private void exploedMine(){
      for(int i = 0; i < myView.length; i++ ){
         for(int j = 0; j < myView[0].length; j++){
            // if covered/guessed/question
            if(myView[i][j] < 0){
               
               if(myView[i][j] == MINE_GUESS){
                                    
                  // if the square has been guessed as a MINE, but it is not
                  if(!myField.hasMine(i, j)){
                     // show that wrong guess
                     myView[i][j] = INCORRECT_GUESS;
                  }
                  
               }else{
                  
                  // if the square is COVERED or question, 
                  // and it is a mine, show MINE
                  if(myField.hasMine(i, j)){
                     myView[i][j] = MINE;
                  }
                  
               }
               
            }
         }
         
      }
      gameOver = true;
      
   }
   
   /**
      win would check if all the non-mine square has been opened. 
      If all the non-mine square has been opened, return true, otherwise false.
      If the status has been varified as win, we should change gameOver to true and show all the mines as MINE_GUESS.
      @return a boolean show wheather win now. 
    */
   private boolean win(){
      // check if all the non-mine square has been opened
      for(int i = 0; i < myView.length; i++ ){
         for(int j = 0; j < myView[0].length; j++){
            if(!myField.hasMine(i, j) && myView[i][j] < 0){
               return false;
               
            }
         }
         
      }
      // if not return false after checking all the non-mine area
      // player win the game, gameOver = true
      gameOver = true;
      // mark all the mines by MINE_GUESS
      for(int i = 0; i < myView.length; i++ ){
         for(int j = 0; j < myView[0].length; j++){
            if(myField.hasMine(i, j)){
               myView[i][j] = MINE_GUESS;
               
            }
         }
         
      }
      return true;
   }
   
}
