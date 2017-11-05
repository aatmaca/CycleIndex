package myPackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CompositeElement {

	BigInteger numeratorCoefficient = BigInteger.ONE;
	BigInteger denominatorCoefficient = BigInteger.ONE;
	List<Element> elements = new ArrayList<Element>();

	public CompositeElement() {
	}

	public CompositeElement(BigInteger numeratorCoefficient, BigInteger denominatorCoefficient) {
		this.numeratorCoefficient = numeratorCoefficient;
		this.denominatorCoefficient = denominatorCoefficient;
	}

	public CompositeElement(BigInteger numeratorCoefficient, BigInteger denominatorCoefficient, List<Element> elements) {
		this.numeratorCoefficient = numeratorCoefficient;
		this.denominatorCoefficient = denominatorCoefficient;
		this.elements = elements;
	}

	public BigInteger getNumeratorCoefficient() {
		return numeratorCoefficient;
	}

	public void setNumeratorCoefficient(BigInteger numeratorCoefficient) {
		this.numeratorCoefficient = numeratorCoefficient;
	}

	public BigInteger getDenominatorCoefficient() {
		return denominatorCoefficient;
	}

	public void setDenominatorCoefficient(BigInteger denominatorCoefficient) {
		this.denominatorCoefficient = denominatorCoefficient;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	public void multiplyElement(Element e) {

		boolean found = false;

		for (Element element : elements) {
			if (element.getIndex() == e.index) {
				element.incrementExponent(e.exponent);
				found = true;
				break;
			}
		}

		if (found == false) {
			elements.add(e);
		}
	}

	public static CompositeElement multiplyCompositeElement(CompositeElement ce1, CompositeElement ce2) {
		
		if (ce1.elements.size() == 0) {
			return ce2.copy();
		}

		BigInteger numerator = ce1.getNumeratorCoefficient().multiply(ce2.getNumeratorCoefficient());
		BigInteger denominator = ce1.getDenominatorCoefficient().multiply(ce2.getDenominatorCoefficient());
		BigInteger gcd = Utils.gcd(numerator, denominator);

		CompositeElement newCE = new CompositeElement(numerator.divide(gcd), denominator.divide(gcd));

		for (Element e : ce1.elements) {
			CompositeElement ce2Copy = ce2.copy();
			ce2Copy.multiplyElement(e);
			for (Element element : ce2Copy.getElements()) {
				newCE.multiplyElement(element);
			}
		}

		return newCE;
	}

	public static CompositeElement tensorProduct(CompositeElement ce1, CompositeElement ce2) {

		BigInteger numerator = ce1.getNumeratorCoefficient().multiply(ce2.getNumeratorCoefficient());
		BigInteger denominator = ce1.getDenominatorCoefficient().multiply(ce2.getDenominatorCoefficient());
		BigInteger gcd = Utils.gcd(numerator, denominator);

		CompositeElement newCE = new CompositeElement(numerator.divide(gcd), denominator.divide(gcd));
		
		for (Element e : ce1.getElements()) {
			CompositeElement temp = ce2.copy().tensorProduct(e);
			for (Element element : temp.getElements()) {
				newCE.multiplyElement(element);
			}
		}

		return newCE;
	}

	public CompositeElement tensorProduct(Element e) {

		CompositeElement newCE = new CompositeElement(getNumeratorCoefficient(), getDenominatorCoefficient());

		for (Element element : elements) {
			newCE.multiplyElement(element.tensorProduct(e));
		}

		return newCE;
	}

	public boolean contains(Element e) {

		for (Element element : elements) {
			if (element.equals(e)) {
				return true;
			}
		}
		return false;
	}

	public void addSameSignatureCompositeElement(CompositeElement ce) throws Exception {
		if (this.equals(ce) == false)
			throw new Exception("hata1");

		BigInteger numerator = getNumeratorCoefficient().multiply(ce.getDenominatorCoefficient()).add(
				getDenominatorCoefficient().multiply(ce.getNumeratorCoefficient()));
		BigInteger denominator = getDenominatorCoefficient().multiply(ce.getDenominatorCoefficient());

		BigInteger gcd = Utils.gcd(numerator, denominator);

		setNumeratorCoefficient(numerator.divide(gcd));
		setDenominatorCoefficient(denominator.divide(gcd));
	}

	@Override
	public boolean equals(Object obj) {

		CompositeElement ce = (CompositeElement) obj;

		if (ce.elements.size() != this.elements.size()) {
			return false;
		}

		for (Element element : elements) {
			if (ce.contains(element) == false) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {

		String s = "";
		
		long powerTotal = 0;

		for (Element element : elements) {
			s = element.toString() + s;
			powerTotal += element.getExponent();
		}

		//return numeratorCoefficient + "/" + denominatorCoefficient + "( " + s + ":" + powerTotal + ")";
		return  "( " + s + ":" + powerTotal + ")";
	}

	public long getPower() {

		long powerTotal = 0;

		for (Element element : elements) {
			powerTotal += element.getExponent();
		}
		
		return powerTotal;
	}

	public CompositeElement copy() {

		List<Element> copyList = new ArrayList<Element>();

		for (Element element : elements) {
			copyList.add(element.copy());
		}

		return new CompositeElement(getNumeratorCoefficient(), getDenominatorCoefficient(), copyList);
	}
}