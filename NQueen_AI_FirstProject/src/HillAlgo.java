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


    //Method to create a new random board
    public static MyQueenClass[] CreateBoredRandmoly() {
        MyQueenClass[] startBoard = new MyQueenClass[BoredSize];
        Random random = new Random();
        for(int i=0; i<BoredSize; i++){
            startBoard[i] = new MyQueenClass(random.nextInt(BoredSize), i);
        }
        return startBoard;
    }


    //Method to print the Current State
    private static int[][] printState (MyQueenClass[] state) {
        //Creating temporary board from the present board
        int[][] tempBoard = new int[BoredSize][BoredSize];
        for (int i=0; i<BoredSize; i++) {
            //Get the positions of Queen from the Present board and set those positions as 1 in temp board
            tempBoard[state[i].getRow()][state[i].getColumn()]=1;
        }
		return tempBoard;
            
		/*
		 * bpanel=new JPanel(new GridLayout(n, n, 3, 3)); bpanel.setSize(700,700);
		 * bpanel.setLocation(0,0); for(int i=0;i<n;i++) { for(int j=0;j<n;j++) {
		 * MyButtonList[i][j]=new JRButton("",700,700); //
		 * MyButtonList[i][j].addMouseListener(new MouseHandler() );
		 * bpanel.add(MyButtonList[i][j]);
		 * 
		 * } } for (int i2=0; i2<n; i2++) { //Get the positions of Queen from the
		 * Present board and set those positions as 1 in temp board
		 * if(tempBoard[state[i2].getRow()][state[i2].getColumn()]==1) {
		 * MyButtonList[state[i2].getRow()][state[i2].getColumn()].setIcon(icon); } }
		 * JFrame frame = new JFrame(); frame.setSize(1000,1000);
		 * frame.setVisible(true); frame.setLayout(null); frame.add(bpanel);
		 */
	        
        }//function
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
	        	//from the generted array take the 1s where it hints that there is a queen there
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
	 		  HValue2 = findHeuristic(InitialBored);
	 	        // test if the present board is the solution board
	 	        while (HValue2 != 0) {
	 	            InitialBored = nextBoard(InitialBored);
	 	            HValue2  = HValue;
	 	        }
	 	        //Printing the solution
	 	        frame.dispose();
	 	        int[][] GUIB1 =  printState(InitialBored);
	 	        GUI(GUIB1, frame);
	 	        
	 		  
	 	   }
	    }//action

    // Method to find Heuristics of a state
    public static int findHeuristic (MyQueenClass[] state) {
        int heuristic = 0;
        for (int i = 0; i< state.length; i++) {
            for (int j=i+1; j<state.length; j++ ) {
                if (state[i].FindConflict(state[j])) {
                    heuristic++;    
                }
            }
        }
        return heuristic;
    }

    // Method to get the next board with lower heuristic
    public static MyQueenClass[] nextBoard (MyQueenClass[] presentBoard) {
        MyQueenClass[] nextBoard = new MyQueenClass[BoredSize];
        MyQueenClass[] tmpBoard = new MyQueenClass[BoredSize];
        int presentHeuristic = findHeuristic(presentBoard);
        int bestHeuristic = presentHeuristic;
        int tempH;

        for (int i=0; i<BoredSize; i++) {
            //  Copy present board as best board and temp board
            nextBoard[i] = new MyQueenClass(presentBoard[i].getRow(), presentBoard[i].getColumn());
            tmpBoard[i] = nextBoard[i];
        }
        //  Iterate each column
        for (int i=0; i<BoredSize; i++) {
            if (i>0)
                tmpBoard[i-1] = new MyQueenClass (presentBoard[i-1].getRow(), presentBoard[i-1].getColumn());
            tmpBoard[i] = new MyQueenClass (0, tmpBoard[i].getColumn());
            //  Iterate each row
            for (int j=0; j<BoredSize; j++) {
                //Get the heuristic
                tempH = findHeuristic(tmpBoard);
                //Check if temp board better than best board
                if (tempH < bestHeuristic) {
                    bestHeuristic = tempH;
                    //  Copy the temp board as best board
                    for (int k=0; k<BoredSize; k++) {
                        nextBoard[k] = new MyQueenClass(tmpBoard[k].getRow(), tmpBoard[k].getColumn());
                    }
                }
                //Move the queen
                if (tmpBoard[i].getRow()!=BoredSize-1)
                    tmpBoard[i].move();
            }
        }
        //Check whether the present board and the best board found have same heuristic
        //Then randomly generate new board and assign it to best board
        if (bestHeuristic == presentHeuristic) {
            
            nextBoard = CreateBoredRandmoly();
            HValue = findHeuristic(nextBoard);
        } else
            HValue = bestHeuristic;
        
        return nextBoard;
    }

    public static void main(String[] args) {
    	
    	
        //Creating the initial Board
         InitialBored = CreateBoredRandmoly();
        int[][] GUIB =  printState(InitialBored);
       frame = new JFrame();
        GUI(GUIB, frame);
       
    
    }//main
}//Hill class