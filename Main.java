import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Image newImage = new Image(args);
		try {
			ChainCode chain = new ChainCode(args, newImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ChainCode{
	public Point neighborCoord[] = new Point[8];
	public Point startP, currentP, nextP;
	int lastQ, nextQ, nextDir, Pchain;
	int nextDirTable[] = new int[8];
	int[][] myArray;
	
	public ChainCode(String[] args, Image newImage) throws IOException{
		FileWriter out1, out2;
		out1 = out2 = null;
		try {
			out1 = new FileWriter(args[1]);
			out2 = new FileWriter(args[2]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int iRows = newImage.numRows;
		int jCols = newImage.numCols;
		int firsti, firstj;
		firsti = firstj = 0;
		
		myArray = newImage.zeroFramedAry;
		
		for(int i = 0; i < 8; i++){
			neighborCoord[i] = new Point(0,0);
		}
        nextDirTable[0]=5;
        nextDirTable[1]=6;
        nextDirTable[2]=7;
        nextDirTable[3]=0;
        nextDirTable[4]=1;
        nextDirTable[5]=2;
        nextDirTable[6]=3;
        nextDirTable[7]=4;	
        
        out1.write(iRows + " " + jCols + " " + newImage.minVal
        		 + " " + newImage.maxVal + " ");
        out2.write(iRows + " " + jCols + " " + newImage.minVal
       		     + " " + newImage.maxVal + "\n");
        
        outloop:
        for(int i = 1; i < iRows+1; i++){
        	for(int j = 1; j < jCols+1; j++){
        		if(myArray[i][j] > 0){
        			out1.write(i + " " + j + " "
        					 + myArray[i][j] + "\n");
        			out2.write(i + " " + j + " "
       					     + myArray[i][j] + "\n");
        			firsti = i;
        			firstj = j;
        			break outloop;
        		}
        	}
        } 
        startP = new Point(firsti, firstj);
        currentP = new Point(firsti, firstj);
        nextP = new Point(0,0);
        lastQ = 4;
    //step 4:
        int count = 0;
        do{
        	nextQ = (lastQ+1)%8;
    //step 5:
        	Pchain = findNextP(currentP, nextQ, nextP);
    //step 6:
        	out1.write(Integer.toString(Pchain));
        	out2.write(Pchain + " ");
        	count++;
        	if(count > 0 && count%20 == 0)
        		out2.write("\n");
    //step 7:
        	lastQ = nextDirTable[Pchain];
        	currentP.update(nextP.row, nextP.col);
        }while(startP.row != currentP.row || startP.col != currentP.col);
        
        out1.close();
        out2.close();
	}
	
	void loadNeighborsCoord(Point current){
		neighborCoord[3].update(current.row-1, current.col-1);
        neighborCoord[2].update(current.row-1, current.col);
        neighborCoord[1].update(current.row-1, current.col+1);
        neighborCoord[4].update(current.row, current.col-1);
        neighborCoord[0].update(current.row, current.col+1);
        neighborCoord[5].update(current.row+1, current.col-1);
        neighborCoord[6].update(current.row+1, current.col);
        neighborCoord[7].update(current.row+1, current.col+1); 
	}
	
	int findNextP(Point cP, int nextQ, Point nP){
		int chainDir, scanningQ;
		loadNeighborsCoord(cP);
		chainDir = 0;
		scanningQ = nextQ;
		for(int i = 0; i < 8; i++){
			int currenti = neighborCoord[scanningQ].row;
			int currentj = neighborCoord[scanningQ].col;
			if(myArray[currenti][currentj]==1){
				chainDir = scanningQ;
				break;
			}
			scanningQ++;
			scanningQ = scanningQ%8;
		}
		nextP.update(neighborCoord[chainDir].row, neighborCoord[chainDir].col);
		return chainDir;
	}

	public void prettyPrint(){
		//not used;
	}
	
}


class Point{
	public int row, col;
	
	public Point(int i, int j){
		row = i;
		col = j;
	}
	
	public void update(int i, int j){
		row = i;
		col = j;
	}
	
}


class Image{
	public int numRows, numCols, minVal, maxVal;
	int[][] zeroFramedAry;
	
	public Image(String[] args){
		File file = new File(args[0]);
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		numRows = in.nextInt();
		numCols = in.nextInt();
		minVal = in.nextInt();
		maxVal = in.nextInt();
		
		zeroFramedAry = new int[numRows+2][numCols+2];
		loadImage(in, zeroFramedAry);
		
		in.close();
	}
	
	public void loadImage(Scanner in, int[][] myArray){
		for(int i = 1; i < numRows+1; i++){
			for(int j = 1; j < numCols+1; j++){
				myArray[i][j] = in.nextInt();
			}
		}
	}
	
	void zeroFramed(int[][] myArray){
		//no point for this in Java;
	}
	

}