import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Polynomial {
	public double[] coefficients;
	public int[] exponents;
	
	//sets  the  polynomial  to  zero
    public Polynomial() {
		this.coefficients = new double[0];
		this.exponents = new int[0];
	}
		
		
    
    
	/* takes  an  array  of  double  as  an  argument  and  sets  the 
	coefficients accordingly */
	public Polynomial(double[] coefficients1, int[] exponents1) {
		
		this.coefficients = new double[coefficients1.length];
		this.exponents = new int[exponents1.length];
		
		for(int i = 0; i < coefficients1.length; i++ ) {
	
			
			this.coefficients[i] = coefficients1[i];
			this.exponents[i] = exponents1[i];
			
		}
		
		
	}
	
	public Polynomial(File file) throws IOException {
		
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();

        if (line == null || line.trim().isEmpty()) {
        	
            throw new IllegalArgumentException("File is empty or does not contain a valid polynomial.");
        }

        parsePolynomial(line);
    }
	
	
	
	//getter for coefficients
	public double[] getCoefficients() {
        return this.coefficients;
    }
	
	
	

	//getter for exponents
    public int[] getExponents() {
        return this.exponents;
    }
    
    
	/*
	 *Simplifies the polynomial so there are no redundant exponents 
	 */
    public void simplify() {
        int n = this.coefficients.length;
        int newSize = 0;

        // Create new arrays to store the simplified polynomial
        double[] newCoefficients = new double[n];
        int[] newExponents = new int[n];

        for (int i = 0; i < n; i++) {
            
        	//if eliminates the zero coefficients
        	
        	if (this.coefficients[i] != 0) {
        		
                int currentExp = this.exponents[i];
                double currentCoeff = this.coefficients[i];

                // Combine coefficients of the same exponent
                for (int j = i + 1; j < n; j++) {
             
                	
                    if (this.exponents[j] == currentExp) {
                    
                        currentCoeff += this.coefficients[j];
                        coefficients[j] = 0; // Mark the term to remove it
                    }
                }

                // If the currentCoeff is not zero, add it to the simplified polynomial
                if (currentCoeff != 0) {
                	
                    newCoefficients[newSize] = currentCoeff;
                    newExponents[newSize] = currentExp;
                 
                    newSize++;
                }
            }
        }

        // Resize the arrays to the actual size of the simplified polynomial
        
			this.coefficients = Arrays.copyOf(newCoefficients, newSize); 
			this. exponents = Arrays.copyOf(newExponents, newSize);
		
    }
    
    
    
    
	/*
	 * Takes one argument of type Polynomial and returns the polynomial resulting
	 * from adding the calling object and the argument
	 */
    
    public Polynomial add(Polynomial other) {
    	
        // Create arrays to store all coefficients and exponents
    	double[] allCoefficients = new double[coefficients.length + other.coefficients.length];
        int[] allExponents = new int[exponents.length + other.exponents.length];

        // Copy coefficients and exponents from the calling polynomial
        System.arraycopy(coefficients, 0, allCoefficients, 0, coefficients.length);
        System.arraycopy(exponents, 0, allExponents, 0, exponents.length);

        // Copy coefficients and exponents from the argument polynomial
        System.arraycopy(other.coefficients, 0, allCoefficients, coefficients.length, other.coefficients.length);
        System.arraycopy(other.exponents, 0, allExponents, exponents.length, other.exponents.length);

        // Create a new Polynomial representing the sum of all terms
        Polynomial result = new Polynomial(allCoefficients, allExponents);

        // Simplify the result and return it
        result.simplify();
        return result;
    }
    
    
    
    
    
	/*It has a method named evaluate that takes one argument of type double
	 * representing a value of x and evaluates the polynomial accordingly.
	 */
    public double evaluate(double x) {
        double result = 0.0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }

        return result;
    }
    
    
    
    
    //determines whether this value is a root of the polynomial or not
  	public boolean hasRoot(double x) {
  		boolean isRoot = evaluate(x) == 0;
  		return isRoot;
  	}
  	
  	
	/*
	 * Takes one argument of type Polynomial and returns the polynomial resulting
	 * from multiplying the calling object and the argument. The resulting
	 * polynomial should not contain redundant exponents.
	 */
  	
  	public Polynomial multiply(Polynomial other) {
  	    int n1 = coefficients.length;
  	    int n2 = other.coefficients.length;

  	    // Create arrays to store coefficients and exponents of the resulting polynomial
  	    double[] resultCoefficients = new double[n1 * n2];
  	    int[] resultExponents = new int[n1 * n2];
  	    int resultSize = 0; // Size of the resulting polynomial

  	    // Iterate through each term in the calling object's polynomial
  	    for (int i = 0; i < n1; i++) {
  	    	
  	    	//Iterate through each term in the argument (other polynomial)
  	        for (int j = 0; j < n2; j++) {
  	        	double newCoefficient = coefficients[i] * other.coefficients[j]; // Multiply coefficients
  	            int newExponent = exponents[i] + other.exponents[j]; // Add exponents
  	            
  	            resultCoefficients[resultSize] = newCoefficient;
  	            resultExponents[resultSize] = newExponent;
  	            resultSize++;
  	        }
  	    }

  	    // Create a new Polynomial object using the resulting coefficients and exponents
  	    Polynomial result = new Polynomial(resultCoefficients,resultExponents);

  	    // Simplify the result and return it
  	    result.simplify();
  	    return result;
  	}
  	
  	private void parsePolynomial(String input) {
  		
  	    // Split the input string into individual polynomial terms based on '+' or '-'
  	    String[] terms = input.split("(?=[-+])");

  	    
  	    // Initialize arrays to store coefficients and exponents
  	    coefficients = new double[terms.length];
  	    exponents = new int[terms.length];

  	    
  	    // Loop through each term and extract coefficients and exponents
  	    for (int i = 0; i < terms.length; i++) {
  	        String term = terms[i].trim(); // Trim leading/trailing spaces

  	        // Initialize variables to store coefficient and exponent
  	        double coefficient = 0.0;
  	        int exponent = 0;

  	        // Start parsing the term
  	        int j = 0; // Index to iterate through the characters of the term

  	        // Check if the term starts with a minus sign
  	        if (term.startsWith("-")) {
  	        	
  	            coefficient = -1.0; // Set the coefficient to -1
  	            j++; // Move to the next character after the minus sign
  	        } 
  	        
  	        else if (term.startsWith("+")) {
  	        	
  	            j++; // Move past the leading plus sign
  	        }

  	        // Read the coefficient part (including decimals)
  	        StringBuilder coeffStrBuilder = new StringBuilder();
  	        
  	        while (j < term.length() && (Character.isDigit(term.charAt(j)) || term.charAt(j) == '.')) {
  	        	
  	            coeffStrBuilder.append(term.charAt(j));
  	            j++;
  	        }

  	        // Check if a coefficient was found
  	        if (coeffStrBuilder.length() > 0) {
  	        	
  	            coefficient *= Double.parseDouble(coeffStrBuilder.toString());
  	        } 
  	        else {
  	            coefficient = 1.0; // Default coefficient is 1.0 if not specified
  	        }

  	        // Check if 'x' is present
  	        if (j < term.length() && term.charAt(j) == 'x') {
  	            j++; // Move past 'x'

  	            // Read the exponent part (digits)
  	            StringBuilder exponentStrBuilder = new StringBuilder();
  	            while (j < term.length() && Character.isDigit(term.charAt(j))) {
  	                exponentStrBuilder.append(term.charAt(j));
  	                j++;
  	            }

  	            // Check if an exponent was found
  	            if (exponentStrBuilder.length() > 0) {
  	                exponent = Integer.parseInt(exponentStrBuilder.toString());
  	            } else {
  	                exponent = 1; // Default exponent is 1 if not specified
  	            }
  	        }

  	        // Store the coefficient and exponent in the arrays
  	        coefficients[i] = coefficient;
  	        exponents[i] = exponent;
  	    }
  	}
    
    public void saveToFile(String fileName) {
    	
        try (FileWriter fileWriter = new FileWriter(fileName);
        		
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            // Generate the polynomial string representation and write it to the file
        	
            String polynomialStr = toString();
            
            writer.write(polynomialStr);
            
        } 
        
        catch (IOException e) {
        	
            e.printStackTrace();
        }
    }
    
    public String toString() {
    	
        StringBuilder polynomialStr = new StringBuilder();
        
        for (int i = 0; i < coefficients.length; i++) {
        	
            if (coefficients[i] != 0) {
            	
                if (polynomialStr.length() > 0) {
                	
                    if (coefficients[i] > 0) {
                    	
                        polynomialStr.append(" + ");
                    } 
                    
                    else {
                        polynomialStr.append(" - ");
                    }
                }
                
                double absCoefficient = Math.abs(coefficients[i]);
                
                if (exponents[i] == 0 || absCoefficient != 1) {
                	
                    polynomialStr.append(absCoefficient);
                }
                
                if (exponents[i] > 0) {
                	
                    polynomialStr.append("x");
                    
                    if (exponents[i] != 1) {
                    	
                        polynomialStr.append(exponents[i]);
                    }
                }
            }
        }
        
        if (polynomialStr.length() == 0) {
            return "0";
        }
        
        return polynomialStr.toString();
    }
        	
        	
}
 
   
