//Adding Infiltrator Class to package
package ca_assignment_0;

//Infiltrator Class definition
public class Infiltrator {
    
    //Infiltrator state variable
    int infiltratorState = 0;

    //Method to get Infiltrator state
    public int getInfiltratorState() {
        return infiltratorState;
    }

    //Method to change Infiltrator state
    public void changeInfiltratorState() {
        if (infiltratorState == 0) {
            infiltratorState = 1;
        }
        else {
            infiltratorState = 0;
        }
    }

}