package lab4;

public class HashTableLin {
	private Integer[] table;
	private int size;
	private int key;
	private int limit;
	private double load;

	public HashTableLin(int maxNum, double load) {

		int sizeMaximum = (int) Math.ceil(maxNum / load); // get upper bound of (maxNum/load)
		while (isPrime(sizeMaximum) == false) {
			sizeMaximum++;
		}

		this.size = sizeMaximum;
		this.table = new Integer[size];
		this.key = 0;
		this.limit = (int) (size * load);
		this.load = load;

	}

//===================================================================
// Accessors
	public Integer[] getTable() {
		return table;
	}

	public void setTable(Integer[] table) {
		this.table = table;
	}

	public int getTableSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumKeys() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public double getMaxLoadFactor() {
		return load;
	}

	public void setLoad(double load) {
		this.load = load;
	}

//=============================================================
// methods
	public boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public void insert(int n) {
		if (this.isIn(n)) {
			return;
		}

		if (!this.isIn(n)) {
			this.setKey(this.key + 1);
			if (key > limit) {
				rehash();
			}

			int i = n % size;
			if (table[i] == null) {
				table[i] = n;
			} else {
				while (table[i] != null) {
					i++;
					if (i > (size - 1)) { // exceeds limit
						i = 0;
					}
				}
				table[i] = n;

			}

		}
	}

	public int insertCount(int n) {
		int count = 1;
		if (this.isIn(n)) {
			int i = n % size;
			while (table[i] != n) {
				i++;
				count++;
				if (i > (size - 1)) { // exceeds limit
					i = 0;
					count++;
				}
			}
			return count;
		}

		if (!this.isIn(n)) {
			this.setKey(this.key + 1);
			if (key > limit) {
				rehash();
			}

			int i = n % size;
			if (table[i] == null) {
				table[i] = n; 
			} else {
				while (table[i] != null) {
					i++;
					count++;
					if (i > (size - 1)) { // exceeds size
						i = 0;
						count++;
					}
				}
				table[i] = n;

			}

		}
		return count;
	}

	public void rehash() {
		this.size = size * 2;
		Integer[] copy = this.table;
		int keyNum = this.key;
		while (isPrime(this.size) == false) {
			this.size++;
		}

		this.limit = (int) (size * load);
		this.table = new Integer[size];
		this.key = 1;
		for (int i = 0; i < copy.length; i++) {
			if (copy[i] != null) {
				this.insert(copy[i]);
			}
		}
		this.setKey(keyNum);
	}

	public boolean isIn(int n) {
		int i = n % this.size;
		int originalIndex = i;
		if (this.getNumKeys() == 0) {
			return false;
		}
		while (table[i] != null) {
			if (table[i] == n) {
				return true;
			} else {
				i++;
				if (i > (size - 1)) { // exceeds limit
					i = 0;
				} else if (i == originalIndex) { // a loop has been completed
					return false;
				}
			}
		}
		return false;
	}

	public void printKeys() {
		if (this.getNumKeys() == 0) {
			return;
		} else {
			for (int i = 0; i < this.getTableSize(); i++) {
				if (table[i] != null) {
					System.out.println(table[i] + ", ");
				}
			}
		}

	}

	public void printKeysAndIndexes() {
		if (this.getNumKeys() == 0) {
			return;
		} else {
			for (int i = 0; i < this.getTableSize(); i++) {
				if (table[i] != null) {
					System.out.println("index" + i + "is" + table[i] + ", ");
				}
			}
		}

	}

	public static void main(String[] args) {
		HashTableLin HTest = new HashTableLin(5, 0.4);
		HTest.insert(3);
		HTest.printKeys();

	}
}
