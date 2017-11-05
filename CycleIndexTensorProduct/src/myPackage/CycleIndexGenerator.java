package myPackage;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CycleIndexGenerator {

	CycleIndex baseCycleIndex = new CycleIndex();

	public static void main(String[] args) throws Exception {

		cycleIndexArray(20, true);
	}

	public static CycleIndex[] cycleIndexArray(int n, boolean printCycleIndexes) throws Exception {

		int cycleIndex = n;

		CycleIndex[] cycleIndexArray = new CycleIndex[cycleIndex + 1];
		cycleIndexArray[0] = new CycleIndex(0);
		cycleIndexArray[1] = CycleIndex.baseCase_n_Equals_1();

		if (printCycleIndexes) {
			System.out.println("Cycle index 1: " + cycleIndexArray[1]);
		}

		for (int i = 2; i <= cycleIndex; i++) {
			cycleIndexArray[i] = new CycleIndex(i);
		}

		for (int i = 2; i <= cycleIndex; i++) {
			cycleIndexRecursion(cycleIndexArray, i);
			if (printCycleIndexes) {
				System.out.println("Cycle index " + i + ": " + cycleIndexArray[i]);
			}
		}
		System.out.println("----------------------------------------------------------");
		System.out.println("Cycle Indexes have been calculated.");
		System.out.println("----------------------------------------------------------");

		return cycleIndexArray;
	}

	public static void cycleIndexRecursion(CycleIndex[] cycleIndexArray, int index) throws Exception {
		for (int i = 1; i <= index; i++) {
			cycleIndexArray[index].addCompositeElementList(cycleIndexMultipliedByElement(index, index - i,
					cycleIndexArray[index - i].copy(), new Element(i, 1)));
		}
	}

	public static List<CompositeElement> cycleIndexMultipliedByElement(int index, int cycleIndex, CycleIndex ci,
			Element e) {

		if (cycleIndex == 0) {
			CompositeElement ce = new CompositeElement();
			ce.setDenominatorCoefficient(BigInteger.valueOf(index));
			ce.getElements().add(e);

			List<CompositeElement> ceList = new ArrayList<CompositeElement>();
			ceList.add(ce);

			return ceList;
		}

		for (CompositeElement compositeElement : ci.getCompositeElements()) {
			compositeElement.multiplyElement(e);
			compositeElement.setDenominatorCoefficient(compositeElement.getDenominatorCoefficient().multiply(BigInteger.valueOf(index)));
		}

		return ci.compositeElements;
	}
}