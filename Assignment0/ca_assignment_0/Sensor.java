//Adding Sensor Class to package
//package ca_assignment_0;

//Importing Random Class for random number generation
import java.util.Random;

//Sensor Class definition
public class Sensor {

    //Sensor state variable
    //0 when OFF and 1 when ON
    public int sensorState = 0;

    //Defining probability for duty cycling
    public double pvalue = 80;

    //Method to initialise sensorState value
    public void intialiseState() {

        //Random Class instance
        Random random = new Random();

        //Picking out a random number between 0 to 100
        double plimit = random.nextDouble();
        plimit = plimit*100;

        //If picked random number is less than or equal to duty cycle probability
        //sensor turns ON otherwise OFF
        if (this.pvalue >= plimit) {
            this.sensorState = 1;
        }
        else {
            this.sensorState = 0;
        }

    }

    //Method to get sensor state
    public int getSensorState() {
        return this.sensorState;
    }

    //Method to get Pvalue
    public double getPvalue() {
        return this.pvalue;
    }

    //Method to change probability value
    public void changePvalue(double newPvalue) {
        this.pvalue = newPvalue;
    }

    public static void main(String[] args) {
        
        Sensor sensor = new Sensor();
        for (int i=0; i<100; i++) {
            sensor.intialiseState();
            System.out.println(sensor.getSensorState());
        }

    }

}