package polynomial;

import java.util.ArrayList;
import java.util.List;

public class Functions {
	
	public static Polynomial add(Polynomial p1, Polynomial p2) {
		
		Polynomial pr = new Polynomial();
		List<Term> termsPol1 = p1.getTerms();
		List<Term> termsPol2 = p2.getTerms();
		List<Term> terms = new ArrayList<Term>();
		
		int index;
		Term t2, t;
		
		int ind1 = 0, ind2 = 0;
		
		for(Term term : termsPol2) {
			terms.add(new Term(term));
		}
		
		pr.setTerms(terms);
		
		boolean exists = false;
		
		for(Term t1 : termsPol1) {
			exists = false;
			if((t = pr.hasCoeffOfDegree(t1.getDegree())) != null) {
				t.setCoeff(t.getCoeff() + t1.getCoeff());
				exists = true;
			}
			if(!exists) {
				pr.addTerm(new Term(t1.getDegree(), t1.getCoeff()));
			}
		}
		
		return pr;
	}
	
	
	public static Polynomial sub(Polynomial p1, Polynomial p2) {
	
		Polynomial pr = new Polynomial();
		List<Term> termsPol1 = p1.getTerms();
		List<Term> termsPol2 = p2.getTerms();
		List<Term> terms = new ArrayList<Term>();
		
		int index;
		Term t2, t;
		
		int ind1 = 0, ind2 = 0;
		
		for(Term term : termsPol2) {
			terms.add(new Term(term.getDegree(), -term.getCoeff()));
		}
		
		pr.setTerms(terms);
		
		boolean exists = false;
		
		for(Term t1 : termsPol1) {
			exists = false;
			if((t = pr.hasCoeffOfDegree(t1.getDegree())) != null) {
				t.setCoeff(t.getCoeff() + t1.getCoeff());
				if(t.getCoeff() == 0)
					terms.remove(t);
				exists = true;
			}
			if(!exists) {
				pr.addTerm(new Term(t1.getDegree(), t1.getCoeff()));
			}
		}
		
		return pr;
	}
	
	public static Polynomial mul(Polynomial p1, Polynomial p2) {
		Polynomial pr = new Polynomial();
		
		List<Term> termsPol1 = p1.getTerms();
		List<Term> termsPol2 = p2.getTerms();
		List<Term> termsPr = pr.getTerms();
		
		int index, degree;
		Double coeff;
		Term tr;
		
		
		for(Term t1 : termsPol1) {
			for(Term t2 : termsPol2) {
				degree = t1.getDegree()+t2.getDegree();
				coeff = t1.getCoeff() * t2.getCoeff();
				
				if((tr = pr.hasCoeffOfDegree(degree)) != null) {
					tr.setCoeff((Double)tr.getCoeff() + coeff);
				}
				else {
					pr.addTerm(new Term(degree, coeff));
				}
			}
		}
		return pr;
	}
	
	private static int polynomialDegree(Polynomial p) {
		List<Term> terms = p.getTerms();
		if(terms.size() > 0)
			return terms.get(terms.size() - 1).getDegree();
		else 
			return 0;
	}
	
	private static boolean remainderGreater(Polynomial pRem, Polynomial p) {
		
		if(pRem.getTerms().size() == 0)
			return false;
		
		if(pRem.getTerms().get(0).getCoeff() > p.getTerms().get(0).getCoeff())
			return true;
		return false;
		
	}
	
	private static Polynomial divide(Polynomial p1, Polynomial p2, Polynomial pr) {
		
		Polynomial tempPol = new Polynomial();
		Polynomial remPol = new Polynomial();
		
		List<Term> termsPol1 = p1.getTerms();
		List<Term> termsPol2 = p2.getTerms();
		
		int degree = polynomialDegree(p1) - polynomialDegree(p2);
		//Double coeff = (double)((Integer)termsPol1.get(termsPol1.size() - 1).coeff / (Integer)termsPol2.get(termsPol2.size() - 1).coeff);
		Double coeff = termsPol1.get(termsPol1.size() - 1).getCoeff() / termsPol2.get(termsPol2.size() - 1).getCoeff();
		
		pr.addTerm(new Term(degree, coeff));
		
		tempPol.addTerm(new Term(degree, coeff));
		tempPol = mul(tempPol, p2);
		remPol = sub(p1, tempPol);
		
		return remPol;
	}
	
	public static List<Polynomial> divideMaster(Polynomial p1, Polynomial p2) {
		
		List<Polynomial> divResult = new ArrayList<Polynomial>();
		
		if(polynomialDegree(p1) < polynomialDegree(p2))
			return divResult;
		
		Polynomial pRes = new Polynomial();
		Polynomial pRem = new Polynomial();
		
		
		pRem = divide(p1, p2, pRes);
		
		while(polynomialDegree(pRem) >= polynomialDegree(p2)) {
				if(polynomialDegree(pRem) == 0 && !remainderGreater(pRem, p2)) 
					break;
			pRem = divide(pRem, p2, pRes);
		}
		
		divResult.add(pRes);
		divResult.add(pRem);
		
		return divResult;
	}
	
	public static Polynomial der(Polynomial p) {
		
		Polynomial pr = new Polynomial();
		
		List<Term> polTerm = p.getTerms();
		
		for(Term t : polTerm) {
			if(t.getDegree() != 0) {
				pr.addTerm(new Term(t.getDegree() -1, t.getDegree() * t.getCoeff()));
			}
		}
		
		return pr;
	}
	
	public static Polynomial intg(Polynomial p) {
		
		Polynomial pr = new Polynomial();
		
		List<Term> polTerm = p.getTerms();
		
		for(Term t : polTerm) {
			pr.addTerm(new Term(t.getDegree() + 1, t.getCoeff() / (t.getDegree() + 1)));
		}
		
		return pr;
		
	}
	
}
