// Name: Zhien Yu
// USC NetID: zhienyu
// CS 455 PA3
// Fall 2023


import java.io.*;

/*
  MineFieldTester is a tester for MineField.
  It would check for the 
  for 1-arg and 3-arg constructor
  Then check for some methods of MineField.
  Client can call it by Linux command in the terminal.
  It would print out messages about the tests. 
*/
public class MineFieldTester {
   public static void main(String[] args) {
      // Test two constructors and the toString method
      
      // Test 1-arg constructor
      boolean[][] input1 = {
         {true, false, false},
         {false, false, false},
         {false, false, true}};
         
      MineField mineField1 = new MineField(input1);
      
      System.out.println("The real value of mineField1.toString() should be:");
      System.out.println("TFF FFF FFT ");
      System.out.println("The constructed mineField1.toString() is:");
      System.out.println(mineField1.toString());
      System.out.print("The mineField1 should contains 2 mine now, the numMines() is: ");
      System.out.println(mineField1.numMines());
      
      // Test 3-arg constructor
      MineField mineField2 = new MineField(4, 4, 5);
      System.out.println("The real value of mineField2.toString() should be all F now");
      System.out.println("The constructed mineField1.toString() is:");
      System.out.println(mineField2.toString());
      System.out.print("The numMines() of mineField2 should be 5 now: ");
      System.out.println(mineField2.numMines());
      
      // Test  populateMineField(row, col)
      testPopulate(mineField1);
      testPopulate(mineField2);
      
      // Test for numAdjacentMines()
      MineField mineField3 = new MineField(input1);
      System.out.println("test for numAdjacentMines(), should be 2: "+ mineField3.numAdjacentMines(1,1));
      System.out.println(" should be 0: "+ mineField3.numAdjacentMines(0,0));
      System.out.println(" should be 1: "+ mineField3.numAdjacentMines(0,1));
      
      
      
      System.out.println("Test for reset: ");
      System.out.print("before: ");
      System.out.println(mineField1.toString());
      System.out.print("after: ");
      mineField1.resetEmpty();
      System.out.println(mineField1.toString());
      
      
      
      
      
   }
   
   /* This private method testPopulate will test for the method populateMineField(row, col).
      It will print out the error message and the outline of testing. 
      @param myField is a MineField obejct that has been constructed. 
        
   */
   private static void testPopulate(MineField myField){
      System.out.print("Test for populate: ");
      System.out.print("before: ");
      System.out.println(myField.toString());
      System.out.println("Number: " + myField.numMines());
      System.out.println("row = 0, col = 0");
      myField.populateMineField(0, 0);
      System.out.print("after: ");
      System.out.println(myField.toString());
      System.out.println("Number: " + myField.numMines());
      
      
      
   }
   
   
   
}