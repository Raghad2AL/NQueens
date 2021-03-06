//This class is used to present the current state and the queens places in a bored  
public class MyQueenClass {
    private int row;
    private int column;
    
    //the row and the col where the queen is at
    public MyQueenClass(int row, int column) {
        this.row = row;
        this.column = column;
        
    }
    
// move queen to a different row
    public void ChangeQueenPlace() {
        row++;
    }
//check rows, columns and diagonals if there is two queens attacking each other in that state
    public boolean FindConflict(MyQueenClass q){
       
        if(row == q.getRow() || column == q.getColumn())
            return true;
           
        else if(Math.abs(column-q.getColumn()) == Math.abs(row-q.getRow()))
            return true;
        return false;
    }
    
    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}