import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class HillAlgo extends JFrame {
    private static int HValue = 0;
    private static JPanel bpanel;
    static Icon icon = new ImageIcon("res/queen1.PNG");
    JRButton btn;
    static JFrame  frame ;
    static MyQueenClass[] InitialBored;//for randomly generating an initial case 
    static int HValue2;
    private static int BoredSize = 5;
    private static JPanel panel;
    static JRButton SolveButton;
  //  public static ArrayList<JRButton> myList = new ArrayList<JRButton>();
    public static JRButton[][] MyButtonList = new JRButton[BoredSize][BoredSize];


    //This method is called first to create a random bored 
    //containing queens in random places. hence, this is the start point
    public static MyQueenClass[] CreateBoredRandmoly() {
        MyQueenClass[] Temp = new MyQueenClass[BoredSize];
        Random random = new Random();
        for(int i=0; i<BoredSize; i++){
            Temp[i] = new MyQueenClass(random.nextInt(BoredSize), i);
        }
        return Temp;
    }


    //Create an int matrix where 1 means there is queen and zero means there is no queen
    //this will help in creating the GUI in a simple way
    private static int[][] FindingQueens (MyQueenClass[] state) {
        int[][] temp = new int[BoredSize][BoredSize];
        for (int i=0; i<BoredSize; i++) {
            temp[state[i].getRow()][state[i].getColumn()]=1;
        }
		return temp;
	        
        }//Queen Method
    public static void GUI( int[][] tempBoard, JFrame frame)
    {
    	 bpanel=new JPanel(new GridLayout(BoredSize, BoredSize, 3, 3));
	        bpanel.setSize(700,700);
	        bpanel.setLocation(0,0);
	        for(int i=0;i<BoredSize;i++)
	        {
	            for(int j=0;j<BoredSize;j++)
	            {
	            	MyButtonList[i][j]=new JRButton("",700,700);
	            	//MyButtonList[i][j].setEnabled(false);
	                bpanel.add(MyButtonList[i][j]);
	                
	            }
	        }
	        for (int i2=0; i2<BoredSize; i2++) {
	        	//from the matrix take the 1s where it hints that there is a queen there
	        	for(int j=0; j<BoredSize;j++)
	        	{
	            if(tempBoard[i2][j]==1)
	            {
	            	MyButtonList[i2][j].setIcon(icon);
	            }
	        	}
	        }
	        panel = new JPanel();
	        panel.setSize(300, 700); 
	        panel.setLocation(700,0);
	        panel.setLayout(null);
	        SolveButton = new JRButton("Solve",200,100); 
	        SolveButton.setLocation(50, 100);
	        SolveButton.setSize(200, 100);
	        SolveButton.addActionListener(new HillAlgo().new ActionHandler());
	        panel.add(SolveButton);
	    
	        frame.setSize(1000,1000);
	        frame.setVisible(true);
	        frame.setLayout(null);
	        frame.add(bpanel);
	        frame.add(panel);
    	
    }//GUI
  
    private class ActionHandler implements ActionListener{
	 	   public void actionPerformed(ActionEvent event){
	 		  HValue2 = HFunction(InitialBored);
	 	        while (HValue2 != 0) {
	 	            InitialBored = NextStep(InitialBored);
	 	            HValue2  = HValue;
	 	        }
	 	        frame.dispose();
	 	        int[][] GUIB1 =  FindingQueens(InitialBored);
	 	        GUI(GUIB1, frame);
	 	        
	 		  
	 	   }
	    }//action

    // Method to find Heuristics of a state
    public static int HFunction (MyQueenClass[] state) {
        int heuristic = 0;
        //checking for conflicts
        for (int i = 0; i< state.length; i++) {
            for (int j=i+1; j<state.length; j++ ) {
                if (state[i].FindConflict(state[j])) {
                    heuristic++;    
                }
            }
        }
        return heuristic;
    }//Hfunction

    // This method is to find where to go next from our present state
    //it searches for the next step with the lowest H function and return it.
    public static MyQueenClass[] NextStep (MyQueenClass[] current) {
        MyQueenClass[] Next = new MyQueenClass[BoredSize];
        MyQueenClass[] temp = new MyQueenClass[BoredSize];
        int currentH = HFunction(current);
        int BestH = currentH;
        int tempH;

        for (int i=0; i<BoredSize; i++) {
            //  assume current is the best next step
            Next[i] = new MyQueenClass(current[i].getRow(), current[i].getColumn());
            temp[i] = Next[i];
        }
        //  Iterate each column
        for (int i=0; i<BoredSize; i++) {
            if (i>0)
                temp[i-1] = new MyQueenClass (current[i-1].getRow(), current[i-1].getColumn());
            temp[i] = new MyQueenClass (0, temp[i].getColumn());
            //  Iterate each row
            for (int j=0; j<BoredSize; j++) {
                //Get the heuristic
                tempH = HFunction(temp);
                //Check if temp board better than best board
                if (tempH < BestH) {
                    BestH = tempH;
                    //  Copy the temp board as best board
                    for (int k=0; k<BoredSize; k++) {
                        Next[k] = new MyQueenClass(temp[k].getRow(), temp[k].getColumn());
                    }//for
                }//if
                //Move the queen
                if (temp[i].getRow()!=BoredSize-1)
                {
                    temp[i].move();
                }//if
            }//second for
        }//first for
        //check if we got stuck as the current and best states are same then
        //randomly generate new state and call Hfunction again
        if (BestH == currentH) {
            
            Next = CreateBoredRandmoly();
            HValue = HFunction(Next);
        } else
            HValue = BestH;
        
        return Next;
    }//method

    
    
    
    //************************************************************************************************//
    public static void main(String[] args) {
    	
    	
        //Creating the initial Board
         InitialBored = CreateBoredRandmoly();
        int[][] GUIB =  FindingQueens(InitialBored);
       frame = new JFrame();
        GUI(GUIB, frame);
       
    
    }//main
}//Hill class