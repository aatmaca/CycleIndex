package myPackage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Utils {

	public static long gcd(long a, long b) {
		BigInteger b1 = BigInteger.valueOf(a);
		BigInteger b2 = BigInteger.valueOf(b);
		BigInteger gcd = b1.gcd(b2);
		return gcd.longValue();
	}

	public static long lcm(long a, long b) {
		return (a / gcd(a, b)) * b;
	}

	// public static long getFactorial(long n) {
	// long f = 1;
	//
	// for (long i = n; i >= 1; i--) {
	// f *= i;
	// }
	//
	// return f;
	// }

	public static BigInteger gcd(BigInteger b1, BigInteger b2) {
		return b1.gcd(b2);
	}

	public static BigInteger lcm(BigInteger b1, BigInteger b2) {
		BigInteger gcd = gcd(b1, b2);
		return b1.divide(gcd).multiply(b2);
	}

	public static BigInteger getFactorial(long n) {
		BigInteger f = BigInteger.ONE;

		for (long i = n; i >= 1; i--) {
			f = f.multiply(BigInteger.valueOf(i));
		}

		return f;
	}

	public static BigInteger combination(long n, long r) {
		if (n >= r) {
			return permutation(n, r).divide(getFactorial(r));
		} else
			throw new RuntimeException("r cannot be greater than n");
	}

	public static BigInteger permutation(long n, long r) {
		if (n >= r) {
			BigInteger f = BigInteger.ONE;

			for (long i = n; i >= 1; i--) {
				f = f.multiply(new BigInteger(i +""));
				r--;
				if (r == 0) {
					break;
				}
			}

			return f;
		} else
			throw new RuntimeException("r cannot be greater than n");
	}

	public static BigInteger TWO() {
		return new BigInteger("2");
	}

	public static BigInteger calculate1(int n, int r) {

		BigInteger bi = BigInteger.ZERO;
		bi = bi.add(Utils.TWO().pow((n-1)*r));
		
		for (int k = 2; k <= r; k++) {
			BigInteger temp = Utils.TWO().pow((n-1)*(r-k+1));
			temp = temp.multiply(combination(r, k));
			temp = temp.multiply(getFactorial(k-1));
			bi.add(temp);
		}
		
		return bi;
	}

	public static BigDecimal lowerBound(int i, int j) {
		BigInteger r = new BigInteger(j+"");
		BigInteger twoPowern=Utils.TWO().pow(i);
		
		BigInteger combination = combination( (twoPowern.add(r).subtract(BigInteger.ONE)).longValue(), r.longValue());
		return new BigDecimal(combination).divide(new BigDecimal(getFactorial(i)), 2, RoundingMode.HALF_DOWN);
	}
	
	public static BigDecimal upperBound(int i, int j) {
		return new BigDecimal(Utils.TWO()).multiply(lowerBound(i, j));
	}
}