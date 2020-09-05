//Adding Main Class to package
//package ca_assignment_0;

//Import FileWriter Class
import java.io.FileWriter;

//Main Class definition
public class Main {

    //Main method definition (Driver Method)
    public static void main(String[] args) {

        //Defining number of experiments to perform for given values of p-value and width
        int numberOfExp = 1000;
        
        //Experiment Class instance
        Experiment experiment = new Experiment();

        //Try block for File IO
        try {
            
            //csvWriter Object instantiation to write ouput to file : output.csv
            FileWriter csvWriter = new FileWriter("output.csv");

            //Writing the Column Names in output.csv
            csvWriter.write("Width");
			csvWriter.write(",");
			csvWriter.write("P-value");
			csvWriter.write(",");
			csvWriter.write("Average_Time_1000_runs");
            csvWriter.write("\n");

            //Local P-value variable definition
            double expProb = 10;
            
            //Varying Width value from 5 to 100 in steps of 5
            for (int expw = 5; expw < 105; expw += 5) {

                //Varying expProb value from 0 to 0.95 in steps of 0.05
                for (int prob = 0; prob < 100; prob += 5) {

                    expProb = prob;

                    int avgtime = experiment.doExperiment(numberOfExp, expProb, expw);
                    //experiment.doExperiment(numberOfExp, expProb, expw);

                    //Writing experiment values to file : output.csv for each iteration
                    csvWriter.write(String.valueOf(expw));
                    csvWriter.write(",");
                    csvWriter.write(String.valueOf(expProb));
                    csvWriter.write(",");
                    csvWriter.write(String.valueOf(avgtime));
                    csvWriter.write("\n");

                    //Console OUT message for each iteration completion
                    System.out.println("Writing values for WIDTH = " + expw + " and P-VALUE = " + String.format("%.2f",expProb) + " and AvgTimeTaken = " + avgtime);

                }

            }

            //Flushing out buffer data from csvWriter object
            csvWriter.flush();

            //Closing csvWriter object
            csvWriter.close();

        }

        //Catch Block in case of any exception to catch the exception
        catch (Exception e) {

            //Print Exception details to console OUT
            System.out.println(e);

        }

        //Final confirmation of completion of writing file : output.csv
        System.out.println("Writing FILE : 'output.csv' completed!");

    }
    
}