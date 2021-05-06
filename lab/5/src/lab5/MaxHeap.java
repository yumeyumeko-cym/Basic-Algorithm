package lab5;

public class MaxHeap {
	// fields
	private Integer[] heap;
	private int memory;// size of memory
	private int size;// number of items

	// constructors

	public MaxHeap(int memory) {// create an empty array
		this.memory = memory;
		this.heap = new Integer[memory];
		this.size = 0;

	}

	public MaxHeap(Integer[] someArray) { //logn
		heap = someArray;
		memory = someArray.length;
		size = someArray.length;
		for (int i = parent(someArray.length - 1); i >= 0; i--) {
			shiftDown(i);
		}
	}

	// accessors

	public Integer[] getHeap() {
		return heap;
	}

	public int getSizeArr() {
		return memory;
	}

	public int getSizeHeap() {
		return size;
	}

	// methods
	public int left(int i) {
		return i * 2 + 1;
	}

	public int right(int i) {
		return i * 2 + 2;
	}

	public int parent(int i) {
		if (i == 0) {
			throw new IllegalArgumentException("index 0 has no parent.");
		}
		return (i - 1) / 2;
	}

	public int data(int index) {
		return heap[index];
	}

	public void rangeCheck(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Index is illegal");
		}

	}

	public void addLast(int k) {

		if (this.memory <= this.size + 1) {
			this.memory = this.memory * 2;
		}

		Integer[] container = new Integer[memory];

		// copy the original items into container
		for (int i = 0; i < this.size; i++) {
			container[i] = heap[i];
		}
		container[size] = k;
		this.size++;
		this.heap = container;
	}

	public void swap(int pos1, int pos2) {
		int temp;
		temp = heap[pos1];
		heap[pos1] = heap[pos2];
		heap[pos2] = temp;
	}

	public void insert(int n) {//1, worst logn

		addLast(n);
		shiftUp(this.size - 1);
	}

	public boolean find(int k) {

		for (int i = 0; i < this.size; i++) {
			if (heap[i] == k) {
				return true;
			}
		}
		return false;
	}

	public void shiftUp(int k) {
		while (k > 0 && data(k) > data(parent(k))) {
			swap(k, parent(k));
			k = parent(k);
		}
	}

	public void shiftDown(int k) {
		while (left(k) < this.size) {// do recursively
			int j = left(k);
			if (j + 1 < this.size && data(j + 1) > data(j)) {
				j++;
			}
			if (data(k) >= data(j)) {
				break;
			}
			swap(k, j);
			k = j;
		}

	}

	public int findMax() {
		if (this.size == 0) {
			throw new IllegalArgumentException("heap is empty");
		}
		return heap[0];
	}

	
	public void deleteLast() {
		this.size--;
		heap[size]=null;
	}
	
	private int deleteMax() {//average logn
		if(this.size == 0) {
			throw new IllegalArgumentException("empty heap.");
		}
		int max = heap[0];
		swap(0,this.size-1);
		this.deleteLast();
		shiftDown(0);
		return max;
	}

	public String toString() {
		String result = "";
		if (size == 0) {
			return "empty heap.";
		}
		for (int i = 0; i < size; i++) {
			result += heap[i] + ",";

		}

		return result;
	}

	
	public static void heapsort(Integer[] arrayToSort) {//build a heap logn, delete nlogn
		MaxHeap newheap = new MaxHeap(arrayToSort);
		Integer[]container = new Integer[arrayToSort.length];
		for (int i = 0; i < arrayToSort.length; i++) {
			container[i] = newheap.deleteMax();
		}
		for (int i = 0; i < arrayToSort.length;  i++) {
			arrayToSort[i] = container[i];
		}
		
	}

	public static void main(String[] args) {
		Integer[] arr = { 5, 6, 7, 8, 9 };
		Integer[] arr2 = { 1, 2, 3, 4, 9 };
		Integer[] arr3 = new Integer[10];
		MaxHeap heap = new MaxHeap(arr);
		for (int i = 0; i < arr2.length; i++) {
			heap.insert(arr2[i]);
		}

		Integer[] arrayToSort = { 751403, 190798, 429191, -950855, 724813, -614175, 402408, 402408, 674500, 402408, -217013, 459839, -360775, -426757, 925603};
		MaxHeap newheap = new MaxHeap(arrayToSort);
		arrayToSort = new Integer[arrayToSort.length];
		for (int i = 0; i < arrayToSort.length; i++) {
			arrayToSort[i] = newheap.deleteMax();
		}
		for (int i = 0; i < arrayToSort.length;  i++) {
			System.out.println(arrayToSort[i]);
		}
		
		
		
		
//		System.out.println(newheap.toString());
		System.out.println(heap.toString());
		System.out.println("memory = " + heap.getSizeArr());
		System.out.println("number of elements = " + heap.getSizeHeap());
	}

}
