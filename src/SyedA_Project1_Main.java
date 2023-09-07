import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class ThresholdSelection{
    //member vars
    int numRows, numCols, minVal, maxVal;
    int [] histAry; // needs to be dynamically allocated at run time; initialize to 0
    int [] GaussAry; // size: maxVal + 1;
    int BiGaussThrVal; //auto selected val by the Bi-Gaussian method
    int maxHeight; // largest hist[i]

    //methods
    public ThresholdSelection(int numRows, int numCols, int minVal, int maxVal){ //dynamically allocate all member arrays and initializations
        this.numRows = numRows;
        this.numCols = numCols;
        this.minVal = minVal;
        this.maxVal = maxVal;

        //allocate member arrays
        histAry = new int[this.maxVal + 1];

        //initialize member arrays to zero
        //can be replaced with Arrays.fill() -- easier method
        for(int i = 0; i < histAry.length; i++){
            histAry[i] = 0;
        }
    }//constructor

    int loadHist(int [] histAry, File inFile){
        return 0;
    }//loadHist

    void dispHist(){

    }//dispHist

    void setZero(int [] Ary){

    }//setZero

    int biGauss(){
        return 0;
    }//biGauss

    double computeMean(){
        return 0.0;
    }//computeMean

    double computeVar(){
        return 0.0;
    }//computeVar

    void modifiedGauss(int x, double mean, int var, int maxHeight){

    }//modifiedGauss

    void fitGauss(){

    }//fitGauss
}//ThresholdSelection
public class SyedA_Project1_Main {
    public static void main(String[] args) {
        //Step 0:
        //Open input file and create output files
        File inFile =  new File("./src/data2.txt");
        File outFile1 = new File("./src/outFile1.txt");
        File debugFile = new File("./src/debugFile.txt");

        int numRows = 0, numCols = 0, minVal = 0, maxVal = 0; //vars to pass into constructor
        try{
            Scanner scanner = new Scanner(inFile);
            //Step 1:
            if(scanner.hasNext()){
                numRows = scanner.nextInt();
                numCols = scanner.nextInt();
                minVal = scanner.nextInt();
                maxVal = scanner.nextInt();
            }
            //TEST
            System.out.println(numRows + " " + numCols + " " + minVal + " " + maxVal);

            //close scanner and fileWriter/s
            scanner.close();
        } catch(IOException e){
            System.out.println(e);
        }
        ThresholdSelection ts = new ThresholdSelection(numRows, numCols, minVal, maxVal);
        ts.maxHeight = ts.loadHist(ts.histAry, inFile);
    }
}
