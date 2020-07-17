//This class is used to present the current state, 
public class MyQueenClass {
    private int row;
    private int column;
    public MyQueenClass(int row, int column) {
        this.row = row;
        this.column = column;
        
    }

    public void move () {
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