package utils;

import java.util.ArrayList;
import java.util.List;

public class Base62 {

	public static final char[] base62 = 
		{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 
		 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
		 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

	public static String convertToBase62(int i) {
		StringBuilder res = new StringBuilder();

		List<Integer> ls = new ArrayList<>();
		
		if (i == 0) {
			return "a";
		}

		while (i > 0) {
			ls.add(i % 62);
			i /= 62;
		}

		for (int x = ls.size() - 1; x >= 0; x--) {
			res.append(base62[ls.get(x)]);
		}

		return res.toString();
	}

	public static int convertToBase10(String s) {
		int res = 0;
		
		int power = s.length() - 1;
		
		for (char c : s.toCharArray()) {
			if (48 <= c && c <= 57) { //Decimal
				res += (c % 48 + 52) * (Math.pow(62, power)); //52 is index of decimal starting point in base62 array above
			} else if (65 <= c && c <= 90) {
				res += (c % 65 + 26) * (Math.pow(62, power)); //26 is index of capital letters starting point in base62 array above
			} else {
				res += c % 97 * (Math.pow(62, power)); //Alphabets start at 0th position in base62 array above
			}
			power--;
		}

		return res;
	}

}
