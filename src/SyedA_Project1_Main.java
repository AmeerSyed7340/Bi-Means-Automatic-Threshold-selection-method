import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class ThresholdSelection {
    //member vars
    int numRows, numCols, minVal, maxVal;
    int[] histAry; // needs to be dynamically allocated at run time; initialize to 0
    int[] GaussAry; // size: maxVal + 1;
    int BiGaussThrVal; //auto selected val by the Bi-Gaussian method
    int maxHeight; // largest hist[i]

    //methods
    public ThresholdSelection(int numRows, int numCols, int minVal, int maxVal) { //dynamically allocate all member arrays and initializations
        this.numRows = numRows;
        this.numCols = numCols;
        this.minVal = minVal;
        this.maxVal = maxVal;

        //allocate member arrays
        histAry = new int[this.maxVal + 1];
        GaussAry = new int[this.maxVal + 1];

        //initialize member arrays to zero
        //can be replaced with Arrays.fill() -- easier method
        for (int i = 0; i < this.maxVal + 1; i++) {
            histAry[i] = 0;

            //utilizing the same count for both arrays. Not a big fan personally
            GaussAry[i] = 0;
        }
    }//constructor

    int loadHist(int[] histAry, File inFile) {
        //max to keep track of the max value of b's
        int max = 0;

        try {
            Scanner sc = new Scanner(inFile);
            //string variable to ommit the first line
            String line;
            line = sc.nextLine();

            int a, b;
            while (sc.hasNextInt()) {
                a = sc.nextInt();
                b = sc.nextInt();
                if (max < b) {
                    max = b;
                }
                histAry[a] = b;
            }


            //close scanner
            sc.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return max;
    }//loadHist

    void dispHist(int[] histAry, File outFile1) {
        try {
            FileWriter fw = new FileWriter(outFile1);
            for (int i = 0; i < histAry.length; i++) {
                fw.write(i + " (" + histAry[i] + "): ");
                for (int j = 0; j < histAry[i]; j++) {
                    fw.write("+");
                }
                fw.write("\n");
            }
            //close FileWriter
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }//dispHist

    void setZero(int[] Ary) {

    }//setZero

    int biGauss(int[] histAry, int[] GaussAry, int maxHeight, int minVal, int maxVal, File deBugFile) {
        double sum1;
        double sum2;
        double total;
        double minSumDiff;
        int offset = (maxVal - minVal) / 10;
        int dividePt = offset;
        int bestThr = dividePt;
        minSumDiff = 99999.0; //Some large val

        try {
            //Step 0:
            FileWriter fw_deBugFile = new FileWriter(deBugFile);
            fw_deBugFile.write("Entering biGaussian method \n");
            fw_deBugFile.close();

            //Step 8:
            while (dividePt < (maxVal - offset)) {
                //Step 1:
                setZero(GaussAry);

                //Step 2:
                sum1 = fitGauss(0, dividePt, histAry, GaussAry, deBugFile);

                //Step 3:
                sum2 = fitGauss(dividePt, maxVal, histAry, GaussAry, deBugFile);

                //Step 4:
                total = sum1 + sum2;

                //step 5:
                if (total < minSumDiff) {
                    minSumDiff = total;
                    bestThr = dividePt;
                }

                //Step 6:
                fw_deBugFile = new FileWriter(deBugFile, true);
                fw_deBugFile.write("dividePt: " + dividePt + ", sum1: " + sum1 + ", sum2: " + sum2 + ", total: " + total + ", minSumDiff: " + minSumDiff + ", bestThr: " + bestThr +"\n");
                fw_deBugFile.close(); //needs to be closed every iteration

                //Step 7:
                dividePt++;
            }

            //Step 9:
            fw_deBugFile = new FileWriter(deBugFile, true);
            fw_deBugFile.write("Leaving biGaussian method, minSumDiff = " + minSumDiff + " bestThr is " + bestThr + "\n");
            //close fw
            fw_deBugFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        //Step 10:
        return bestThr;
    }//biGauss

    double computeMean() {
        return 0.0;
    }//computeMean

    double computeVar() {
        return 0.0;
    }//computeVar

    void modifiedGauss(int x, double mean, int var, int maxHeight) {

    }//modifiedGauss

    double fitGauss(int leftIndex, int rightIndex, int[] histAry, int[] GaussAry, File deBugFile) {
        try {
            FileWriter fw = new FileWriter(deBugFile, true);
            fw.write("Entering fitGauss method\n");

            //close files
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return 0.0;
    }//fitGauss
}//ThresholdSelection

public class SyedA_Project1_Main {
    public static void main(String[] args) {
        //Step 0:
        //Open input file and create output files
        File inFile = new File("./src/data2.txt");
        File outFile1 = new File("./src/outFile1.txt");
        File deBugFile = new File("./src/deBugFile.txt");

        int numRows = 0, numCols = 0, minVal = 0, maxVal = 0; //vars to pass into constructor
        try {
            Scanner scanner = new Scanner(inFile);
            FileWriter fw_outFile = new FileWriter(outFile1, true);
            //Step 1:
            if (scanner.hasNext()) {
                numRows = scanner.nextInt();
                numCols = scanner.nextInt();
                minVal = scanner.nextInt();
                maxVal = scanner.nextInt();
            }
            //TEST
            System.out.println(numRows + " " + numCols + " " + minVal + " " + maxVal);

            ThresholdSelection ts = new ThresholdSelection(numRows, numCols, minVal, maxVal);
            ts.maxHeight = ts.loadHist(ts.histAry, inFile);

            //Step 2:
            ts.dispHist(ts.histAry, outFile1);

            //Step 3:
            ts.BiGaussThrVal = ts.biGauss(ts.histAry, ts.GaussAry, ts.maxHeight, ts.minVal, ts.maxVal, deBugFile);
            fw_outFile.write("BiGaussThrval: " + ts.BiGaussThrVal);

            //Step 4:
            //close scanner and fileWriter/s
            scanner.close();
            fw_outFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
