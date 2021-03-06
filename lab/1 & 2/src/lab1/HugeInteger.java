package lab1;

/*
 * In this lab, on the first digit of singleDigits[], I chose to use 1/-1 to represent the sign of the final result. 
 * And on the add method, carry will stand for whether do we need to +1 for the high digit.
 * Name: YIMING CHEN
 * Student Number: 400230266
 * MacID: cheny466
 * 
 * */
public class HugeInteger {
	// create the container to store the digits
	private int[] singleDigits;

//==============================================================
	// null container
	public HugeInteger() {
		singleDigits = null;
	}

//==============================================================
	// isInputValid
	public boolean isNumber(String s) {
		String reg = "^[-\\\\+]?([0-9]+\\\\.?)?[0-9]+$";
		return s.matches(reg);
	}

//============================================================
	// temporaryContainer
	public HugeInteger tempContainer(HugeInteger h) {
		HugeInteger tc = new HugeInteger();
		tc.singleDigits = new int[h.getLen()];
		for (int i = 0; i < h.singleDigits.length; i++) {
			tc.singleDigits[i] = h.singleDigits[i];
		}
		return tc;
	}

//============================================================
	// constructor for String
	public HugeInteger(String val) {

		if(!isNumber(val)) {
			System.out.println("invalid input!");
			return;
		}
		if (val.charAt(0) == '-') {
			singleDigits = new int[val.length()];
			singleDigits[0] = -1;
			for (int i = 1; i < val.length(); i++) {
				String temp = "";
				temp += val.charAt(i);
				singleDigits[i] = Integer.parseInt(temp);
			}
		} else if (val.charAt(0) != '-') {
			singleDigits = new int[val.length() + 1];
			singleDigits[0] = 1;
			for (int i = 0; i < val.length(); i++) {
				String temp = "";
				temp += val.charAt(i);
				singleDigits[i + 1] = Integer.parseInt(temp);
			}
		} 
	}

//==============================================================	
	// constructor for creating an n digits random big integer
	public HugeInteger(int n) {
		if (n > 0) {
			// decide the sign of the singleDigits
			singleDigits = new int[n + 1];
			int sign = (int) (Math.random() * 2);
			if (sign == 0) {
				singleDigits[0] = -1;
			} else {
				singleDigits[0] = 1;
			}

			if (n == 1) {
				singleDigits[1] = (int) (1 + Math.random() * (9));
			} else if (n > 1) {
				singleDigits[1] = (int) (1 + Math.random() * (9));
				for (int i = 2; i < n; i++) {
					singleDigits[i] = (int) (Math.random() * (10));
				}
			}
		} else {
			// so it should let the system know the input is invalid and turn into Exception
			// Dealing Scheme
			HugeInteger i = new HugeInteger("%");
		}
	}

//==============================================================	
	// toString method
	public String toString() {
		String out = "";
		if (this.singleDigits[0] == -1) {
			out += "-";
		}

		int zeros = 0;
		for (int i = 1; i < this.getLen(); i++) {
			if (this.singleDigits[i] == 0) {
				zeros++;
			} else {
				break;
			}
		}
		if (zeros == this.getLen() - 1) {
			return "0";
		}
		//[-1,0,0,0,0,0,0] = 0
		//[-1,0,0,6,4,6]
		for (int i = 1 + zeros; i < this.singleDigits.length; i++) {
			out += this.singleDigits[i];
		}
		return out;

	}

// ==============================================================
	// find the array length
	public int getLen() {
		return this.singleDigits.length;
	}

//==============================================================
	// huge integer addition
	public HugeInteger add(HugeInteger h) {
		int len = 1 + Math.max(this.getLen(), h.getLen()); // len = the number with longer digits + 1
		HugeInteger result = new HugeInteger();
		result.singleDigits = new int[len];
		result.singleDigits[1] = 0;
		int carry = 0;
		int longDigit = this.getLen() > h.getLen() ? this.getLen() : h.getLen();
		int shortDigit = this.getLen() < h.getLen() ? this.getLen() : h.getLen();

		// one is zero

		// determine the sign
		if (this.singleDigits[0] == -1 && h.singleDigits[0] == -1) {
			result.singleDigits[0] = -1;
		} else if (this.singleDigits[0] == 1 && h.singleDigits[0] == 1) {
			result.singleDigits[0] = 1;
		} else if (this.singleDigits[0] == 1 && h.singleDigits[0] == -1) {
			HugeInteger tc = tempContainer(h);
			tc.singleDigits[0] = 1;
			if (this.compareTo(tc) == 1) {
				result.singleDigits[0] = 1;
			} else {
				result.singleDigits[0] = -1;
			}
		} else {
			HugeInteger tc = tempContainer(this);
			tc.singleDigits[0] = 1;
			if (tc.compareTo(h) == 1) {
				result.singleDigits[0] = -1;
			} else {
				result.singleDigits[0] = 1;
			}
		}

		// both positive

		if (this.singleDigits[0] == 1 && h.singleDigits[0] == 1 && this.getLen() >= h.getLen()) {
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry) >= 10) {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 1;
					} else {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((this.singleDigits[this.getLen() - i] + carry) >= 10) {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry) % 10;
						carry = 1;
					} else {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}

		} else if (this.singleDigits[0] == 1 && h.singleDigits[0] == 1 && this.getLen() < h.getLen()) {
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry) >= 10) {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 1;
					} else {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((h.singleDigits[h.getLen() - i] + carry) >= 10) {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry) % 10;
						carry = 1;
					} else {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}
		}

		// both negative
		if (this.singleDigits[0] == -1 && h.singleDigits[0] == -1 && this.getLen() >= h.getLen()) {
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry) >= 10) {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 1;
					} else {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((this.singleDigits[this.getLen() - i] + carry) >= 10) {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry) % 10;
						carry = 1;
					} else {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}

		} else if (this.singleDigits[0] == -1 && h.singleDigits[0] == -1 && this.getLen() < h.getLen()) {
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry) >= 10) {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 1;
					} else {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((h.singleDigits[h.getLen() - i] + carry) >= 10) {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry) % 10;
						carry = 1;
					} else {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}
		}

		// this positive and h negative
		if (this.singleDigits[0] == 1 && h.singleDigits[0] == -1 && this.getLen() >= h.getLen()) {
			if (this.getLen() == 2 && this.singleDigits[1] == 0) {
				return h;
			}
			//1664+(-323)
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((this.singleDigits[this.getLen() - i] - h.singleDigits[h.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i]
								- h.singleDigits[h.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] - h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((this.singleDigits[this.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}

		} else if (this.singleDigits[0] == 1 && h.singleDigits[0] == -1 && this.getLen() < h.getLen()) {
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((-this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (-this.singleDigits[this.getLen() - i]
								+ h.singleDigits[h.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len
								- i] = (-this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((h.singleDigits[h.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}
		}

		// this negative and h positive
		if (this.singleDigits[0] == -1 && h.singleDigits[0] == 1 && this.getLen() >= h.getLen()) {
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((this.singleDigits[this.getLen() - i] - h.singleDigits[h.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i]
								- h.singleDigits[h.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len
								- i] = (this.singleDigits[this.getLen() - i] - h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((this.singleDigits[this.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len - i] = (this.singleDigits[this.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}

		} else if (this.singleDigits[0] == -1 && h.singleDigits[0] == 1 && this.getLen() < h.getLen()) {
			for (int i = 1; i < len; i++) {
				if (i < shortDigit) {
					if ((-this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (-this.singleDigits[this.getLen() - i]
								+ h.singleDigits[h.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len
								- i] = (-this.singleDigits[this.getLen() - i] + h.singleDigits[h.getLen() - i] + carry)
										% 10;
						carry = 0;
					}
				} else if (i >= shortDigit && i < longDigit) {
					if ((h.singleDigits[h.getLen() - i] + carry) < 0) {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry + 10) % 10;
						carry = -1;
					} else {
						result.singleDigits[len - i] = (h.singleDigits[h.getLen() - i] + carry) % 10;
						carry = 0;
					}

				} else {
					result.singleDigits[len - i] = carry;
				}
			}
		}

		return result;
	}

//=======================================================================
	// subtraction method
	public HugeInteger subtract(HugeInteger h) {

		HugeInteger result = tempContainer(h);
		result.singleDigits[0] *= -1;

		result = this.add(result);

		return result;
	}

// [-1,0,0,0,0,0,4,6,7,9] 
//====================================================================
	// multiplication method
	public HugeInteger multiply(HugeInteger h) {
		HugeInteger result = new HugeInteger();
		result.singleDigits = new int[this.getLen() + h.getLen()];
		// determine the sign
		if (this.singleDigits[0] == h.singleDigits[0]) {
			result.singleDigits[0] = 1;
		} else {
			result.singleDigits[0] = -1;
		}

		// calculation
		for (int a = 1; a < this.getLen(); a++) {
			for (int b = 1; b < h.getLen(); b++) {
				result.singleDigits[this.getLen() + h.getLen() - a - b + 1] += this.singleDigits[this.getLen() - a]
						* h.singleDigits[h.getLen() - b];
				
			}
		}
		// carry
		int temp;
		for (int k = result.getLen() - 1; k > 0; k--) {
			temp = result.singleDigits[k] / 10; //25/10=2
			result.singleDigits[k - 1] += temp;
			result.singleDigits[k] = result.singleDigits[k] % 10; //25%10=5
		}

		return result;
	}

//====================================================================
	// huge integer compare
	public int compareTo(HugeInteger h) {
		if (this.getLen() == h.getLen()) {
			boolean flag = true;
			for (int i = 0; i < this.getLen(); i++) {
				if (this.singleDigits[i] != h.singleDigits[i]) {
					flag = false;
				}
			}
			if (flag) {
				return 0;
			}
		}
		if (this.getLen() > h.getLen()) {
			if (this.singleDigits[0] == 1) {
				return 1;
			} else {
				return -1;
			}

		} else if (this.getLen() < h.getLen()) {
			if (h.singleDigits[0] == 1) {
				return -1;
			} else {
				return 1;
			}
		} else {
			if (this.singleDigits[0] == 1 && h.singleDigits[0] == -1) {
				return 1;
			} else if (this.singleDigits[0] == -1 && h.singleDigits[0] == 1) {
				return -1;
			} else if (this.singleDigits[0] == 1 && h.singleDigits[0] == 1) {// both positive
				for (int i = 1; i < this.getLen(); i++) {
					if (this.singleDigits[i] > h.singleDigits[i]) {
						return 1;
					} else if (this.singleDigits[i] < h.singleDigits[i]) {
						return -1;
					} else {
						continue;
					}
				}
				
			} else {// both negative
				for (int i = 1; i < this.getLen(); i++) {
					if (this.singleDigits[i] < h.singleDigits[i]) {
						return 1;
					} else if (this.singleDigits[i] > h.singleDigits[i]) {
						return -1;
					} else {
						continue;
					}
				}

			}
			return 1;
		}

	}

	public static void main(String[] args) {
		HugeInteger i = new HugeInteger("a");
		HugeInteger m = new HugeInteger("30");
		HugeInteger h = new HugeInteger("2222");
		HugeInteger n = new HugeInteger("-449");
		HugeInteger z = new HugeInteger("0");
		HugeInteger s = new HugeInteger("30");
		
		System.out.println(m.add(h));
		System.out.println(m.add(z));
		System.out.println(m.add(n));
		System.out.println(m.add(s));
		
		System.out.println(m.multiply(h));
		System.out.println(m.multiply(n));
		System.out.println(m.multiply(z));
		System.out.println(m.multiply(s));
		
		System.out.println(m.subtract(h));
		System.out.println(m.subtract(n));
		System.out.println(m.subtract(z));
		System.out.println(m.subtract(s));
		
		System.out.println(m.compareTo(h));
		System.out.println(m.compareTo(n));
		System.out.println(m.compareTo(z));
		System.out.println(m.compareTo(s));
	}
}
