import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String[] args) {
	
	        double[] coefficients = {6, -2, 5};
	        int[] exponents = {0, 1, 3};

	        // Create a Polynomial object using the provided coefficients and exponents
	        Polynomial polynomial = new Polynomial(coefficients, exponents);

	        // Display the polynomial and evaluate it at x = 2
	        System.out.println("Polynomial: " + polynomial);
	        System.out.println("Evaluate at x = 2: " + polynomial.evaluate(2));

	        // Save the polynomial to a file and then read it from the file
	        polynomial.saveToFile("polynomial.txt");

	        try {
	            Polynomial polynomialFromFile = new Polynomial(new File("polynomial.txt"));
	            
	            System.out.println("Polynomial from file: " + polynomialFromFile);
	            
	            System.out.println("Evaluate at x = 3: " + polynomialFromFile.evaluate(3));
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	   }
}
