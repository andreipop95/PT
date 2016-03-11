package polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Polynomial {
	
	private List<Term> terms = new ArrayList<Term>();
	
	public void addTerm(Term term) {
		terms.add(term);
		Collections.sort(terms, new Comparator<Term>(){
		
			@Override
			public int compare(Term t1, Term t2) {
				if(t1.getDegree() < t2.getDegree())
					return -1;
				else {
					if(t1.getDegree() == t2.getDegree())
						return 0;
					else
						return 1;
				}
			}
		}
		);
	}
	
	public Term hasCoeffOfDegree(int degree) {
		
		for(Term t : terms) {
			if(t.getDegree() == degree) 
				return t;
		}
		
		return null;
	}
	
	public List<Term> getTerms() {
		return terms;
	}
	
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}
	
	@Override
	public boolean equals(Object pol) {
		Polynomial p = (Polynomial) pol;
		List<Term> pTerms = p.getTerms();
		
		return pTerms.equals(this.terms);
		
	}
	
}
