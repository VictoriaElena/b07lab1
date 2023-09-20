public class Polynomial {
	
	double[] coefficients;
	
	
	//sets  the  polynomial  to  zero
	public Polynomial() {
		this.coefficients = new double[0];
	}
	
	
	/* takes  an  array  of  double  as  an  argument  and  sets  the 
	coefficients accordingly */

	public Polynomial(double[] coeff) {
		this.coefficients = new double[coeff.length];
		
	    int length = coeff.length;
	    
	    for (int i = 0; i<length; i ++) {
	    	
	    	this.coefficients[i] = coeff[i];
	    }
	}
	
	
	
	//Sets the coefficient coeff at index index 
	
	public void setCoefficients(int index, double coeff) {
		
		if(index > coefficients.length - 1) {
			
			 double temp[] = coefficients;
			 coefficients = new double[index + 1];
			 
			 for (int i = 0; i < temp.length; i++) {
				 
				 coefficients[i] = temp[i]; 
			 }
			
		}
		
		coefficients[index] = coeff;
	}
	

	/*takes  one  argument  of  type  Polynomial  and returns the polynomial 
	 *resulting from adding the calling object and the argument 
	 */
	
	public Polynomial  add(Polynomial other) {
		
		Polynomial result = new Polynomial();
		
		int len_first = this.coefficients.length;
		int len_second = other.coefficients.length;
		
		int len = Math.min(len_first, len_second);
		int i;
		
		
		for (i = 0; i < len; i++) {
            result.setCoefficients(i, this.coefficients[i] + other.coefficients[i]);
        }
        while (i < len_first) {
            result.setCoefficients(i, this.coefficients[i]);
            i++;
        }
        while (i < len_second) {
            result.setCoefficients(i, other.coefficients[i]);
            i++;
        }
		
		return result;
	}
	
	
	
	//evaluates the polynomial 
	
	public double evaluate (double value) {
		 
		double eval = 0;
		
		for (int i = 0; i < this.coefficients.length; i ++) {
			
			eval += Math.pow (value, i)* this.coefficients[i] ;
			
		}
		return eval;

	}
	
	/*Has Root that takes one argument of type double and
	determines whether this value is a root of the polynomial or not*/
	
	public boolean hasRoot (double value) {
		
		if(evaluate(value) == 0) {
			
			return true;
		}
		return false;
		
	}
	

}