package test;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import polynomial.Functions;
import polynomial.Polynomial;
import polynomial.Term;

public class TestOperations {

	private Polynomial p1 = new Polynomial();
	private Polynomial p2 = new Polynomial();
	private Polynomial prAdd = new Polynomial();
	private Polynomial prSub = new Polynomial();
	private Polynomial prMul = new Polynomial();
	private Polynomial prInt = new Polynomial();
	private Polynomial prDiv = new Polynomial();
	private Polynomial prDiff = new Polynomial();
	
	
	@Before
	public void initializePolynomials() {
		p1.addTerm(new Term(5, 1));
		p1.addTerm(new Term(4, 5));
		p1.addTerm(new Term(3, 13));
		p1.addTerm(new Term(2, 16));
		p1.addTerm(new Term(1, 8));
		p1.addTerm(new Term(0, 1));
		
		p2.addTerm(new Term(3, 1));
		p2.addTerm(new Term(2, 3));
		p2.addTerm(new Term(1, 6));
		p2.addTerm(new Term(0, 1));
		
		prAdd.addTerm(new Term(5, 1));
		prAdd.addTerm(new Term(4, 5));
		prAdd.addTerm(new Term(3, 14));
		prAdd.addTerm(new Term(2, 19));
		prAdd.addTerm(new Term(1, 14));
		prAdd.addTerm(new Term(0, 2));
		
		
		prSub.addTerm(new Term(5, 1));
		prSub.addTerm(new Term(4, 5));
		prSub.addTerm(new Term(3, 12));
		prSub.addTerm(new Term(2, 13));
		prSub.addTerm(new Term(1, 2));
		
		prMul.addTerm(new Term(8, 1));
		prMul.addTerm(new Term(7, 8));
		prMul.addTerm(new Term(6, 34));
		prMul.addTerm(new Term(5, 86));
		prMul.addTerm(new Term(4, 139));
		prMul.addTerm(new Term(3, 134));
		prMul.addTerm(new Term(2, 67));
		prMul.addTerm(new Term(1, 14));
		prMul.addTerm(new Term(0, 1));
		
		prDiv.addTerm(new Term(2, 1));
		prDiv.addTerm(new Term(1, 2));
		prDiv.addTerm(new Term(0, 1));
		
		prInt.addTerm(new Term(4, 0.25));
		prInt.addTerm(new Term(3, 1));
		prInt.addTerm(new Term(2, 3));
		prInt.addTerm(new Term(1, 1));
		
		prDiff.addTerm(new Term(2, 3));
		prDiff.addTerm(new Term(1, 6));
		prDiff.addTerm(new Term(0, 6));
	}
	
	@Test
	public void testAddition() {
		assertEquals(prAdd, Functions.add(p1, p2));
	}
	
	@Test
	public void testSubtraction() {
		assertEquals(prSub, Functions.sub(p1, p2));
	}
	
	@Test
	public void testMultiplication() {
		assertEquals(prMul, Functions.mul(p1, p2));
	}
	
	@Test
	public void testDivision() {
		assertEquals(prDiv, Functions.divideMaster(p1, p2).get(0));
	}
	
	@Test
	public void testIntegration() {
		assertEquals(prInt, Functions.intg(p2));
	}
	
	@Test
	public void testDifferentiation() {
		assertEquals(prDiff, Functions.der(p2));
	}
	
}
