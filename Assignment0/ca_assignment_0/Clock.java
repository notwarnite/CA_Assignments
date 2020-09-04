//Adding Clock Class to package
package ca_assignment_0;

//Clock Class definition
public class Clock {

    //Declaring time attribute
    int time = 0;

    //Method to reset time to zero
    public void resetTime() {
        time = 0;
    }

    //Method to get current value of time
    public int getTime() {
        return time;
    }

    //Method to increment time value by 10 units
    public void incrementTime10() {
        time += 10;
    }

}