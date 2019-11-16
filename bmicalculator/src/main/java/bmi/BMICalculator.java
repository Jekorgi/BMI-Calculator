package bmi;

import java.text.DecimalFormat;

/**
 * Hello world!
 *
 */
public class BMICalculator {
    public String countBmi(Integer mass, Double height) {
        double heigh = height / 100;
        double heightpow2 = Math.pow(heigh, 2);
        double output = mass / heightpow2;
        System.out.println("Heigh: " + heigh + " / HeighPow2: " + heightpow2 + " / output: " + output);
        DecimalFormat format = new DecimalFormat("###.##");
        String output2 = format.format(output);
        return output2;
    }
}
