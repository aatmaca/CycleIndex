package myPackage;

import java.util.ArrayList;
import java.util.List;

public class CycleIndex {

	int cycleIndex = 0;
	List<CompositeElement> compositeElements = new ArrayList<CompositeElement>();

	public CycleIndex() {
	}

	public CycleIndex(int cycleIndex) {
		this.cycleIndex = cycleIndex;
	}

	public CycleIndex(int cycleIndex, List<CompositeElement> compositeElements) {
		this.cycleIndex = cycleIndex;
		this.compositeElements = compositeElements;
	}
	
	public int getCycleIndex() {
		return cycleIndex;
	}
	
	public void setCycleIndex(int cycleIndex) {
		this.cycleIndex = cycleIndex;
	}

	public List<CompositeElement> getCompositeElements() {
		return compositeElements;
	}

	public void setCompositeElements(List<CompositeElement> compositeElements) {
		this.compositeElements = compositeElements;
	}

	public void addCompositeElementList(List<CompositeElement> ceList) throws Exception {

		for (CompositeElement ce : ceList) {
			addCompositeElement(ce);
		}
	}

	public void addCompositeElement(CompositeElement ce) throws Exception {

		CompositeElement pair = this.contains(ce);

		if (pair == null) {
			compositeElements.add(ce);
		} else {
			pair.addSameSignatureCompositeElement(ce);
		}
	}

	public CompositeElement contains(CompositeElement ce) {

		for (CompositeElement compositeElement : compositeElements) {
			if (compositeElement.equals(ce)) {
				return compositeElement;
			}
		}
		return null;
	}

	public static CycleIndex baseCase_n_Equals_1() {

		CompositeElement ce = new CompositeElement();
		ce.getElements().add(new Element(1,1));

		List<CompositeElement> ceList = new ArrayList<CompositeElement>();
		ceList.add(ce);

		return new CycleIndex(1, ceList);
	}
	
	@Override
	public String toString() {
		
		String s = "";
		
		for (CompositeElement compositeElement : compositeElements) {
			s += compositeElement.toString() + " + "; 
		}
		if (s.equals("")) {
			return "";
		} else {
			return s.substring(0, s.lastIndexOf('+'));
		}
	}

	public CycleIndex copy() {
		
		List<CompositeElement> copyList = new ArrayList<CompositeElement>();
		
		for (CompositeElement ce : compositeElements) {
			copyList.add(ce.copy());
		}
		
		return new CycleIndex(getCycleIndex(), copyList);
	}
}