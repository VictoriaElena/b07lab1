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
	
	
    public Polynomial() {
		this.coefficients = new double[1];
		this.exponents = new int[1];
	    	Arrays.fill(coefficients, 0);
	        Arrays.fill(exponents, 0 );
	    
	}
		
		
    
    
	
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
	
	
	

	public double[] getCoefficients() {
        return this.coefficients;
    }
	
	
	

	
    public int[] getExponents() {
        return this.exponents;
    }
    
    
	
    public void simplify() {
        int n = this.coefficients.length;
        int newSize = 0;

        double[] newCoefficients = new double[n];
        int[] newExponents = new int[n];

        for (int i = 0; i < n; i++) {
            
        	
        	
        	if (this.coefficients[i] != 0) {
        		
                int currentExp = this.exponents[i];
                double currentCoeff = this.coefficients[i];

                
                for (int j = i + 1; j < n; j++) {
             
                	
                    if (this.exponents[j] == currentExp) {
                    
                        currentCoeff += this.coefficients[j];
                        coefficients[j] = 0; // Mark the term to remove it
                    }
                }

                
                if (currentCoeff != 0) {
                	
                    newCoefficients[newSize] = currentCoeff;
                    newExponents[newSize] = currentExp;
                 
                    newSize++;
                }
            }
        }

        
			this.coefficients = Arrays.copyOf(newCoefficients, newSize); 
			this. exponents = Arrays.copyOf(newExponents, newSize);
		
    }
    
    
    
    
	
    public Polynomial add(Polynomial other) {
    	
        
    	double[] allCoefficients = new double[coefficients.length + other.coefficients.length];
        int[] allExponents = new int[exponents.length + other.exponents.length];

        
        System.arraycopy(coefficients, 0, allCoefficients, 0, coefficients.length);
        System.arraycopy(exponents, 0, allExponents, 0, exponents.length);

        
        System.arraycopy(other.coefficients, 0, allCoefficients, coefficients.length, other.coefficients.length);
        System.arraycopy(other.exponents, 0, allExponents, exponents.length, other.exponents.length);

        
        Polynomial result = new Polynomial(allCoefficients, allExponents);

        result.simplify();
        return result;
    }
    
    
    
    
    
	
    public double evaluate(double x) {
        double result = 0.0;

        for (int i = 0; i < this.coefficients.length; i++) {
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }

        return result;
    }
    
    
    
    
   
  	public boolean hasRoot(double x) {
  		boolean isRoot = evaluate(x) == 0;
  		return isRoot;
  	}
  	
  	

  	
  	public Polynomial multiply(Polynomial other) {
  	    int n1 = coefficients.length;
  	    int n2 = other.coefficients.length;

  	    double[] resultCoefficients = new double[n1 * n2];
  	    int[] resultExponents = new int[n1 * n2];
  	    int resultSize = 0; 

  	    for (int i = 0; i < n1; i++) {
  	    	
  	        for (int j = 0; j < n2; j++) {
  	        	double newCoefficient = coefficients[i] * other.coefficients[j]; 
  	            int newExponent = exponents[i] + other.exponents[j];
  	            
  	            resultCoefficients[resultSize] = newCoefficient;
  	            resultExponents[resultSize] = newExponent;
  	            resultSize++;
  	        }
  	    }

  	    Polynomial result = new Polynomial(resultCoefficients,resultExponents);

  	    result.simplify();
  	    return result;
  	}
  	
  	private void parsePolynomial(String input) {
  		
  	    String[] terms = input.split("(?=[-+])");

  	    
  	    coefficients = new double[terms.length];
  	    exponents = new int[terms.length];

  	    
  	    for (int i = 0; i < terms.length; i++) {
  	        String term = terms[i].trim(); 

  	        double coefficient = 0.0;
  	        int exponent = 0;

  	        int j = 0; 

  	        if (term.startsWith("-")) {
  	        	
  	            coefficient = -1.0; 
  	            j++; 
  	        } 
  	        
  	        else if (term.startsWith("+")) {
  	        	
  	            j++; 
  	        }

  	        StringBuilder coeffStrBuilder = new StringBuilder();
  	        
  	        while (j < term.length() && (Character.isDigit(term.charAt(j)) || term.charAt(j) == '.')) {
  	        	
  	            coeffStrBuilder.append(term.charAt(j));
  	            j++;
  	        }

  	        if (coeffStrBuilder.length() > 0) {
  	        	
  	            coefficient *= Double.parseDouble(coeffStrBuilder.toString());
  	        } 
  	        else {
  	            coefficient = 1.0; 
  	        }

  	        if (j < term.length() && term.charAt(j) == 'x') {
  	            j++; 

  	            StringBuilder exponentStrBuilder = new StringBuilder();
  	            while (j < term.length() && Character.isDigit(term.charAt(j))) {
  	                exponentStrBuilder.append(term.charAt(j));
  	                j++;
  	            }

  	            if (exponentStrBuilder.length() > 0) {
  	                exponent = Integer.parseInt(exponentStrBuilder.toString());
  	            } else {
  	                exponent = 1; 
  	            }
  	        }

  	        coefficients[i] = coefficient;
  	        exponents[i] = exponent;
  	    }
  	}
    
    public void saveToFile(String fileName) {
    	
        try (FileWriter fileWriter = new FileWriter(fileName);
        		
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
        	
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
 
   
