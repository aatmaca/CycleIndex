package myPackage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class CycleIndexTensorProduct {

	public static void main(String[] args) throws Exception {
		
		int indexVal1 = 16;
		int indexVal2 = 16;

		CycleIndex[] cycleIndexArray = CycleIndexGenerator.cycleIndexArray(20, false);

		for (int i = 1; i < indexVal1; i++) {
			System.out.print("{");
			for (int j = 1; j < indexVal2; j++) {

//				if ((i == indexVal1 && j == indexVal2) == false) {
//					continue;
//				}
				
				int index1 = i;
				int index2 = j;
				
				if (i == j) {
					System.out.print( "{"+ -1 + "," + -1 + "," + -1 + "},");
					continue;
				}
				
				if (i>j) {
					index1 = j;
					 index2 = i;
				}
				
				//System.out.println("Product of " + i + " and " + j);

				
				BigInteger denominator = Utils.getFactorial(index1).multiply(Utils.getFactorial(index2));

				List<CompositeElement> ceList = tensorProduct(cycleIndexArray[index1].copy(),
						cycleIndexArray[index2].copy());
				
				BigInteger exactValue = calculateExactValue(ceList, denominator);

				for (CompositeElement ce : ceList) {
					// System.out.println(ce);
				}
				
				BigDecimal lowerBound = Utils.lowerBound(index1,index2);
				BigDecimal upperBound =  new BigDecimal(Utils.TWO()).multiply(lowerBound);
				
				BigInteger lowerBoundBigInteger = lowerBound.toBigInteger();
				BigInteger upperBoundBigInteger = upperBound.toBigInteger();
				
				if (upperBound.compareTo(new BigDecimal(upperBoundBigInteger)) == 1) {
					upperBoundBigInteger.add(BigInteger.ONE);
				}
				

				
				System.out.print( "{"+ lowerBoundBigInteger + "," + exactValue + "," + upperBoundBigInteger + "},");
				//System.out.print( exactValue + ",");
				//System.out.print( lowerBound + ",");
				//System.out.print( lowerBound.multiply(Utils.TWO()) + ",");
				//System.out.print( exactValue.subtract(lowerBound) + ",");
				//System.out.print( (exactValue.subtract(lowerBound)).divide(lowerBound) + ",");
				//System.out.print( (lowerBound.multiply(Utils.TWO())).subtract(exactValue) + ",");
				//System.out.println("Tensor product result for " + index1 + " and " + index2 + ": " + exactValue);
				//System.out.println("*****************************************************************************\n");
			}
			System.out.println("},");
		}

	}

	private static List<CompositeElement> tensorProduct(CycleIndex ci1, CycleIndex ci2) throws Exception {
		CycleIndex cycleIndex = new CycleIndex();
		
		int counter = 1;
		int arrayCounter = 0;
		long[] secondTermArray = new long[ci2.getCompositeElements().size()];

		for (CompositeElement ce1 : ci1.getCompositeElements()) {
			
			arrayCounter = 0;
			
			for (CompositeElement ce2 : ci2.getCompositeElements()) {
				CompositeElement tensorProduct = CompositeElement.tensorProduct(ce1, ce2);
				//System.out.println(ce1 + " X " + ce2 + " = " + tensorProduct);
				
				if(counter == 2){
					secondTermArray[arrayCounter]=tensorProduct.getPower();
					arrayCounter++;
				} else if (counter>2) {
					if (secondTermArray[arrayCounter] >= tensorProduct.getPower()) {
						//Ok.
					} else {
//						System.out.println("ArrayCounter:" + arrayCounter + ", Values: "+secondTermArray[arrayCounter] + " , " + tensorProduct.getPower());
//						System.out.println(ce1 + " X " + ce2 + " = " + tensorProduct);
					}
					arrayCounter++;
				}
				
				
				cycleIndex.addCompositeElement(tensorProduct);
			}
			
			counter++;
		}
		//System.out.println("Counter MAX Value:" + arrayCounter);
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