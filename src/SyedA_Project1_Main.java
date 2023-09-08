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
        setZero(histAry);
        setZero(GaussAry);
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
            FileWriter fw = new FileWriter(outFile1, true);
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
        //can be replaced with Arrays.fill() -- easier method
        for(int i = 0; i < Ary.length; i++){
            Ary[i] = 0;
        }
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
                fw_deBugFile.write("dividePt: " + dividePt + ", sum1: " + sum1 + ", sum2: " + sum2 + ", total: " + total + ", minSumDiff: " + minSumDiff + ", bestThr: " + bestThr + "\n");
                fw_deBugFile.close(); //needs to be closed every iteration

                //Step 7:
                dividePt++;
            }

            //Step 9:
            fw_deBugFile = new FileWriter(deBugFile, true);
            fw_deBugFile.write("Leaving biGaussian method, minSumDiff = " + minSumDiff + " bestThr is " + bestThr + "\n");
            fw_deBugFile.write("\n");
            //close fw
            fw_deBugFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        //Step 10:
        return bestThr;
    }//biGauss

    double computeMean(int leftIndex, int rightIndex, int maxHeight, int [] histAry, File deBugFile) {
        maxHeight = 0;
        int sum = 0;
        int numPixels = 0;
        double result = 0.0;

        //Step 0:
        try{
            FileWriter fw = new FileWriter(deBugFile, true);
            fw.write("Entering computeMean method\n");

            //Step 1:
            int index = leftIndex;

            //Step 5:
            while(index < rightIndex) {
                //Step 2:
                sum += (histAry[index] * index);
                numPixels += histAry[index];

                //Step 3:
                if (histAry[index] > maxHeight) {
                    maxHeight = histAry[index];
                }

                //Step 4:
                index++;
            }

            //Step 6:
            result = (double) sum / (double) numPixels;

            //Step 7:
            fw.write("Leaving computeMean method, maxHeight is: " + maxHeight + " and result is: " + result + "\n");
            fw.write("\n");
            fw.close();
        }
        catch(IOException e){
            System.out.println(e);
        }

        //Step 8:
        return result;
    }//computeMean

    double computeVar(int leftIndex, int rightIndex, double mean, int[]histAry, File deBugFile) {
        double sum = 0.0;
        int numPixels = 0;
        double result = 0.0;

        try{
            FileWriter fw = new FileWriter(deBugFile, true);
            //Step 0:
            fw.write("Entering computerVar method \n");

            //Step 1:
            int index = leftIndex;

            //Step 4:
            while(index < rightIndex) {
                //Step 2:
                sum += (double) histAry[index] * ((double) index - mean) * ((double) index - mean);
                numPixels += histAry[index];

                //Step 3:
                index++;
            }

            //Step 5:
            result = sum / (double) numPixels;

            //Step 6:
            fw.write("Leaving computerVar method returning result: " + result + "\n");
            fw.write("\n");
            fw.close();
        }catch(IOException e){
            System.out.println(e);
        }
        //Step 7:
        return result;
    }//computeVar

    double modifiedGauss(int x, double mean, double var, int maxHeight) {
        return (double)(maxHeight * Math.exp(-((((double)x-mean)*((double)x-mean))/(2*var))));
    }//modifiedGauss

    double fitGauss(int leftIndex, int rightIndex, int[] histAry, int[] GaussAry, File deBugFile) {
        //putting vars outside of try block for global scope
        double mean;
        double var;
        double sum = 0.0;
        double Gval;
        double maxGval;

        try {
            FileWriter fw = new FileWriter(deBugFile, true);
            //Step 0:
            fw.write("Entering fitGauss method\n");
            fw.close();//need to close before computeMean and computeVar can use the file

            //Step 1:
            mean = computeMean(leftIndex, rightIndex, maxHeight, histAry, deBugFile);
            var = computeVar(leftIndex, rightIndex, mean, histAry, deBugFile);

            //Step 2:
            int index = leftIndex;

            //Step 7:
            while(index <= rightIndex) {
                //Step 3:
                Gval = modifiedGauss(index, mean, var, maxHeight);

                //Step 4:
                sum += Math.abs(Gval - (double) histAry[index]);

                //Step 5:
                GaussAry[index] = (int) Gval;

                //Step 6:
                index++;
            }
            //Step 8:
            fw = new FileWriter(deBugFile, true);
            fw.write("Leaving fitGauss method, sum is: " + sum + "\n");
            fw.write("\n");

            //close files
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return sum;
    }//fitGauss
}//ThresholdSelection

public class SyedA_Project1_Main {
    public static void main(String[] args) {
        //Step 0:
        //Open input file and create output files
        File inFile = new File(args[0]);
        File outFile1 = new File(args[1]);
        File deBugFile = new File(args[2]);

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
            //display the header in outFile1
            fw_outFile.write(numRows + " " + numCols + " " + minVal + " " + maxVal + "\n");
            fw_outFile.close();

            ThresholdSelection ts = new ThresholdSelection(numRows, numCols, minVal, maxVal);
            ts.maxHeight = ts.loadHist(ts.histAry, inFile);

            //Step 2:
            ts.dispHist(ts.histAry, outFile1);

            //Step 3:
            ts.BiGaussThrVal = ts.biGauss(ts.histAry, ts.GaussAry, ts.maxHeight, ts.minVal, ts.maxVal, deBugFile);

            fw_outFile = new FileWriter(outFile1, true);
            fw_outFile.write("BiGaussThrval: " + ts.BiGaussThrVal + "\n");

            //Step 4:
            //close scanner and fileWriter/s
            scanner.close();
            fw_outFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
