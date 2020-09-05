//Adding Infiltrator Class to package
//package ca_assignment_0;

//Infiltrator Class definition
public class Infiltrator {
    
    //Infiltrator state variable
    public int infiltratorState = 0;

    //Method to get Infiltrator state
    public int getInfiltratorState() {
        return this.infiltratorState;
    }

    //Method to change Infiltrator state
    public void changeInfiltratorState() {
        if (this.infiltratorState == 0) {
            this.infiltratorState = 1;
        }
        else {
            this.infiltratorState = 0;
        }
    }

}