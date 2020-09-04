//Adding Sensor Class to package
package ca_assignment_0;

//Importing Random Class for random number generation
import java.util.Random;

//Sensor Class definition
public class Sensor {

    //Sensor state variable
    //0 when OFF and 1 when ON
    int sensorState = 0;

    //Defining probability for duty cycling
    double pvalue = 0.1;

    //Method to initialise sensorState value
    public void intialiseState() {

        //Random Class instance
        Random random = new Random();

        //Picking out a random number between 0 to 100
        double plimit = random.nextDouble();
        plimit = plimit*100;

        //If picked random number is less than or equal to duty cycle probability
        //sensor turns ON otherwise OFF
        pvalue = pvalue*100;
        //pvalue = (int) pvalue;

        if (pvalue >= plimit) {
            sensorState = 1;
        }
        else {
            sensorState = 0;
        }

    }

    //Method to get sensor state
    public int getSensorState() {
        return sensorState;
    }

    //Method to get Pvalue
    public double getPvalue() {
        return pvalue;
    }

    //Method to change probability value
    public void changePvalue(double newPvalue) {
        pvalue = newPvalue;
    }

}