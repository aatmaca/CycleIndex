package myPackage;

import java.math.BigInteger;
import java.util.List;

public class Test2 {

	public static void main(String[] args) throws Exception {
		
		int indexVal1 = 10;
		int indexVal2 = 11;

		CycleIndex[] cycleIndexArray = CycleIndexGenerator.cycleIndexArray(20, false);

		for (int i = 1; i < indexVal1; i++) {
			for (int j = 1; j < indexVal2; j++) {

//				if ((i == indexVal1 && j == indexVal2) == false) {
//					continue;
//				}
				
				if (i == j || i>j) {
					continue;
				}
				
				//System.out.println("Product of " + i + " and " + j);

				int index1 = i;
				int index2 = j;
				BigInteger denominator = Utils.getFactorial(index1).multiply(Utils.getFactorial(index2));

				List<CompositeElement> ceList = tensorProduct(cycleIndexArray[index1].copy(),
						cycleIndexArray[index2].copy());

				//for (CompositeElement ce : ceList) {
					// System.out.println(ce);
				//}

				BigInteger exactValue = calculateExactValue(ceList, denominator);
				System.out.println("Tensor product result for " + index1 + " and " + index2 + ": " + exactValue);
				System.out.println("*****************************************************************************\n");
			}
		}

	}

	private static List<CompositeElement> tensorProduct(CycleIndex ci1, CycleIndex ci2) throws Exception {
		CycleIndex cycleIndex = new CycleIndex();
		
		int counter = 1;
		BigInteger second= null;
		BigInteger sonSum = null;

		for (CompositeElement ce1 : ci1.getCompositeElements()) {
			
			BigInteger sum = BigInteger.ZERO;
			sonSum = BigInteger.ZERO;
			
			for (CompositeElement ce2 : ci2.getCompositeElements()) {
				CompositeElement tensorProduct = CompositeElement.tensorProduct(ce1, ce2);
				//System.out.println(ce1 + " X " + ce2 + " = " + tensorProduct);
				
				sum = sum.add(Utils.TWO().pow((int)tensorProduct.getPower()));
				

				sonSum = sonSum.add(Utils.TWO().pow((int)tensorProduct.getPower()));
				
				cycleIndex.addCompositeElement(tensorProduct);
			}
			
			if(counter == 2){
				//System.out.println("2nd Term SUM:" + sum);
				second =sum;
			} else if (counter>2) {
				if (second.compareTo(sum) < 1) {
					System.out.println("Dif::::::::::::::::::::::::::::::::::::::::::::::::::" + second.subtract(sum));
				}
			}
			
			counter++;
		}
		
		BigInteger c1 = Utils.calculate1(ci1.getCycleIndex(),ci2.getCycleIndex());
		
		//System.out.println("Last summation:" + sonSum + "----" + c1);
		return cycleIndex.getCompositeElements();
	}

	private static BigInteger calculateExactValue(List<CompositeElement> ceList, BigInteger denominator) {

		BigInteger exactValue = BigInteger.ZERO;
		for (CompositeElement ce : ceList) {

			// System.out.println(ce);

			if ((denominator.compareTo(ce.getDenominatorCoefficient())==-1) || (denominator.remainder(ce.getDenominatorCoefficient()).equals(BigInteger.ZERO) == false)) {
				throw new RuntimeException("hata2");
			}

			BigInteger part = BigInteger.ONE;
			for (Element e : ce.getElements()) {
				part = part.multiply(BigInteger.valueOf(2).pow(e.getExponent()));
			}
			exactValue = exactValue.add(part.multiply(ce.getNumeratorCoefficient()).multiply(denominator.divide(ce.getDenominatorCoefficient())));
		}
		return exactValue.divide(denominator);
	}
}