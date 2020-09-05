//Adding Experiment Class to package
//package ca_assignment_0;

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
        this.ownCell.changePvalue(expPvalue);
        this.frontCenterCell.changePvalue(expPvalue);
        this.frontLeftCell.changePvalue(expPvalue);
        this.frontRightCell.changePvalue(expPvalue);

        //Changing width to expWidth
        this.border.changeWidth(expWidth);

        //Sum variable for average time calculation
        int sum = 0;

        //Doing numOfExp number of experiments
        for (int i = 0; i < numOfExp; i++) {

            //Resetting time to zero initially
            this.clock.resetTime();

            //Setting coveredWidth value to zero initially
            this.coveredWidth = 0;

            //Infiltrator standing just behind the first row of the Sensor-grid
            while (true) {

                this.frontCenterCell.intialiseState();
                this.frontLeftCell.intialiseState();
                this.frontRightCell.intialiseState();

                this.clock.incrementTime10();

                if (this.coveredWidth == 1) {
                    break;
                }
                else {
                    if (this.frontCenterCell.getSensorState() == 0 || this.frontLeftCell.getSensorState() == 0 || this.frontRightCell.getSensorState() == 0) {
                        this.coveredWidth++;
                    }
                }

            }
            //Infiltrator comes to the first row of the Sensor-grid

            //Infiltrator covers rest of the Sensor-grid to reach at the last row
            while (true) {

                this.ownCell.intialiseState();
                this.frontCenterCell.intialiseState();
                this.frontLeftCell.intialiseState();
                this.frontRightCell.intialiseState();

                this.clock.incrementTime10();

                if (this.coveredWidth == expWidth) {
                    break;
                }
                else {
                    if (this.ownCell.getSensorState() == 0) {
                        if (this.frontCenterCell.getSensorState() == 0 || this.frontLeftCell.getSensorState() == 0 || this.frontRightCell.getSensorState() == 0) {
                            this.coveredWidth++;
                        }
                    }
                }

            }
            //Infiltrator reaches last row of the Sensor-grid

            //Infiltrator tries to cross the last row of the Sensor-grid and enters the DC
            while (true) {

                this.ownCell.intialiseState();

                this.clock.incrementTime10();

                if (this.coveredWidth == expWidth + 1) {
                    break;
                }
                else {
                    if (this.ownCell.getSensorState() == 0) {
                        this.coveredWidth++;
                    }
                }

            }
            //Infilrator successfully enters DC by crossing last row of the Sensor-grid
        
            //Appending sum value to calculate average time after last step
            sum += this.clock.getTime();
        
        }

        //Calculating average time taken and storing it in avg_time variable
        int avg_time = sum/numOfExp;

        //Returning avg_time value
        return avg_time;

    }

    public static void main(String[] args) {

        int numberofExp = Integer.valueOf(args[0]);
        double expProb = Double.valueOf(args[1]);
        int experimentWidth = Integer.valueOf(args[2]);

        
        Experiment experiment = new Experiment();
        int averagetime = experiment.doExperiment(numberofExp, expProb, experimentWidth);
        System.out.println("For given input WIDTH = " + experimentWidth + " and P-VALUE = " + String.format("%.2f",expProb) + " the output AvgTimeTaken = " + averagetime);

    }

}