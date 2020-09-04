//Adding Experiment Class to package
package ca_assignment_0;

//Experiment Class definition
public class Experiment {

    //Sensors definition for current, front-left, front-right and front-center cells
    Sensor ownCell = new Sensor();
    Sensor frontLeftCell = new Sensor();
    Sensor frontCenterCell = new Sensor();
    Sensor frontRightCell = new Sensor();

    //Clock instance for experimentation
    Clock clock = new Clock();

    //Border instance for experimentation
    Border border = new Border();

    //Covered Width variable to keep track of width covered
    public int coveredWidth;

    //Method to do experiment with given value of number of experiments, experiment p-value and experiment width
    public int doExperiment (int numOfExp, double expPvalue, int expWidth) {

        //Changing p-value of sensors to expPvalue
        ownCell.changePvalue(expPvalue);
        frontCenterCell.changePvalue(expPvalue);
        frontLeftCell.changePvalue(expPvalue);
        frontRightCell.changePvalue(expPvalue);

        //Changing width to expWidth
        border.changeWidth(expWidth);

        //Sum variable for average time calculation
        int sum = 0;

        //Doing numOfExp number of experiments
        for (int i = 0; i < numOfExp; i++) {

            //Resetting time to zero initially
            clock.resetTime();

            //Setting coveredWidth value to zero initially
            coveredWidth = 0;

            //Infiltrator standing just behind the first row of the Sensor-grid
            while (true) {

                frontCenterCell.intialiseState();
                frontLeftCell.intialiseState();
                frontRightCell.intialiseState();

                clock.incrementTime10();

                if (coveredWidth == 1) {
                    break;
                }
                else {
                    if (frontCenterCell.getSensorState() == 0 || frontLeftCell.getSensorState() == 0 || frontRightCell.getSensorState() == 0) {
                        coveredWidth++;
                    }
                }

            }
            //Infiltrator comes to the first row of the Sensor-grid

            //Infiltrator covers rest of the Sensor-grid to reach at the last row
            while (true) {

                ownCell.intialiseState();
                frontCenterCell.intialiseState();
                frontLeftCell.intialiseState();
                frontRightCell.intialiseState();

                clock.incrementTime10();

                if (coveredWidth == expWidth) {
                    break;
                }
                else {
                    if (ownCell.getSensorState() == 0) {
                        if (frontCenterCell.getSensorState() == 0 || frontLeftCell.getSensorState() == 0 || frontRightCell.getSensorState() == 0) {
                            coveredWidth++;
                        }
                    }
                }

            }
            //Infiltrator reaches last row of the Sensor-grid

            //Infiltrator tries to cross the last row of the Sensor-grid and enters the DC
            while (true) {

                ownCell.intialiseState();

                clock.incrementTime10();

                if (coveredWidth == expWidth + 1) {
                    break;
                }
                else {
                    if (ownCell.getSensorState() == 0) {
                        coveredWidth++;
                    }
                }

            }
            //Infilrator successfully enters DC by crossing last row of the Sensor-grid
        
            //Appending sum value to calculate average time after last step
            sum += clock.getTime();
        
        }

        //Calculating average time taken and storing it in avg_time variable
        int avg_time = sum/numOfExp;

        //Returning avg_time value
        return avg_time;

    }

}