package myPackage;

public class Element {

	int index;
	int exponent;

	public Element() {
	}

	public Element(int index, int exponent) {
		this.index = index;
		this.exponent = exponent;
	}

	public Element(long i, long exp) {
		this.index = (int) i;
		this.exponent = (int) exp;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public void incrementExponent(int increment) {
		this.exponent = this.exponent + increment;
	}

	@Override
	public boolean equals(Object obj) {

		Element e = (Element) obj;

		if ((e.index == this.index) && (e.exponent == this.exponent)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "x" + index + "^" + exponent + " ";
	}

	public Element copy() {
		return new Element(getIndex(), getExponent());
	}

	public Element tensorProduct(Element e) {
		return new Element(Utils.lcm(getIndex(), e.getIndex()), getExponent() * e.getExponent() * Utils.gcd(getIndex(), e.getIndex()));
	}
}