package polynomial;

public class Term{
	private int degree;
	private double coeff;
	
	public Term(int degree, double coeff) {
		this.degree = degree;
		this.coeff = coeff;
	}
	
	public Term(Term term) {
		this.degree = term.degree;
		this.coeff = term.coeff;
	}
	
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public void setCoeff(double coeff) {
		this.coeff =  coeff;
	}
	
	public int getDegree() {
		return this.degree;
	}
	
	public double getCoeff() {
		return this.coeff;
	}
	
	@Override
	public boolean equals(Object term) {
		Term t = (Term) term;
		if(t.getCoeff() == this.coeff && t.getDegree() == this.degree)
			return true;
		return false;
	}

	
}
