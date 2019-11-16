package bmi;

/**
 * BMIEvents
 */
public class BMIEvents {

    public void run() {
        System.out.println("SIEMSON GOSICU");
    }

    public Boolean checkIfNumber(String text) {
        System.out.println("Got string: " + text);
        if (text.matches("[0-9]+")) {
            return true;
        } else {
            return false;
        }
    }
}