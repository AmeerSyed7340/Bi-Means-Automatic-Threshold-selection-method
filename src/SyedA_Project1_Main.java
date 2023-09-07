import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class ThresholdSelection{
    //member vars
    int numRows, numCols, minVal, maxVal;
    int [] histAry; // needs to be dynamically allocated at run time; initialize to 0
    int [] GaussAry; // size: maxVal + 1;
    int BiGaussThrVal; //auto selected val by the Bi-Gaussian method
    int maxHeight; // largest hist[i]

    //methods
    public ThresholdSelection(){ //dynamically allocate all member arrays and initializations

    }//constructor

    int loadHist(){
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
        File input_data1 =  new File("./src/data1.txt");
        try{
            Scanner scanner = new Scanner(input_data1);
            String line;
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                System.out.println(line);
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
    }
}
