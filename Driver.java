import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String[] args) {
        double[] coefficients = {6, -2, 5};
        int[] exponents = {0, 1, 3};

        
        Polynomial polynomial = new Polynomial(coefficients, exponents);

        
        System.out.println("Polynomial: " + polynomial);
        System.out.println("Evaluate at x = 2: " + polynomial.evaluate(2));

        
        polynomial.saveToFile("polynomial.txt");

        try {
            Polynomial polynomialFromFile = new Polynomial(new File("polynomial.txt"));
            System.out.println("Polynomial from file: " + polynomialFromFile);
            System.out.println("Evaluate at x = 3: " + polynomialFromFile.evaluate(3));
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        try {
            Polynomial additionalPolynomial = new Polynomial(new File("additional_polynomial.txt"));
            System.out.println("Additional Polynomial from file: " + additionalPolynomial);
            System.out.println("Evaluate at x = 4: " + additionalPolynomial.evaluate(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
