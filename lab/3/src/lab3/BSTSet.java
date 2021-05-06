package lab3;

public class BSTSet {
	private TNode root;

	public TNode getRoot() {
		return root;
	}

	public boolean isLeaf(TNode n) {
		return (n != null && n.left == null && n.right == null);
	}

	public BSTSet() {
		this.root = null;
	}

	public void copy(BSTSet s, TNode t) {
		if (t == null) {
			return;
		}
		if (t != null) {
			s.add(t.element);
			copy(s, t.left);
			copy(s, t.right);
		}
	}

	public TNode findTreeMin() {
		TNode t = root;
		if (t == null) {
			return null;
		}
		while (t.left != null) {
			t = t.left;
		}
		return t;
	}

	public TNode removeMin() {
		TNode t = root;
		if (t == null) {
			return null;
		}
		while (t.left != null) {
			t = t.left;
		}
		t = null;
		return t;
	}

	public TNode findTreeMax() {
		TNode t = root;
		if (t == null) {
			return null;
		}
		while (t.left != null) {
			t = t.right;
		}
		return t;
	}

	public TNode removeMax() {
		TNode t = root;
		if (t == null) {
			return null;
		}
		while (t.right != null) {
			t = t.right;
		}
		t = null;
		return t;
	}

	public BSTSet(int[] input) {
		if (input.length == 0) {
			this.root = null;
		} else {
			this.root = new TNode(input[0], null, null);
			TNode node = this.root;
			int len = input.length;

			for (int i = 1; i < len; i++) {
				do {
					if (input[i] < node.element) {
						if (node.left == null) {
							node.left = new TNode(input[i], null, null);
						} else {
							node = node.left;
						}

					} else if (input[i] > node.element) {
						if (node.right == null) {
							node.right = new TNode(input[i], null, null);
						} else {
							node = node.right;
						}
					} else if (input[i] == node.element) {
						node.element = input[i];
						break;
					}

				} while (node != null);
				node = this.root;
			}
		}
	}

	public boolean isEmpty() {
		if (this.root == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isIn(int v) {
		if (this.root == null) {
			return false;
		}
		TNode temp = root;
		while (temp != null) {// do recursion
			if (v == temp.element) {
				return true;
			} else if (v < temp.element) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
		return false;
	}

	public void add(int v) {
		if (this.isIn(v)) {
			return;
		} else {
			TNode temp = root;
			while (temp.left != null && temp.right != null) {// do recursion
				if (v < temp.left.element) {
					temp = temp.left;
				} else {
					temp = temp.right;
				}
			}
			if (temp.element < v) {
				temp.left = new TNode(v, null, null);
			} else {
				temp.right = new TNode(v, null, null);
			}

		}

	}

	public boolean remove(int v) {
		boolean isLeft = true;
		TNode temp = root;
		TNode parent = root;

		if (!isIn(v)) {
			return false;
		}
		// go into the node to be removed
		while (v != temp.element) {
			if (temp.element > v) {
				isLeft = true;
				if (temp.left != null) {
					parent = temp;
					temp = temp.left;
				}
			} else {
				isLeft = false;
				if (temp.right != null) {
					parent = temp;
					temp = temp.right;
				}
			}
			if (temp == null) {
				return false;
			}
		}

		// the node to be removed is leaf
		if (temp.left == null && temp.right == null) {
			if (temp == root) {
				root = null;
			} else if (isLeft) {
				parent.left = null;
			} else {
				parent.right = null;
			}
		}

		// the node to be removed only has left child
		else if (temp.right == null) {
			if (temp == root) {
				root = root.left;
			} else if (isLeft) {
				parent.left = temp.left;
			} else {
				parent.right = temp.left;
			}
		}

		// the node to be removed only has right child
		else if (temp.left == null) {
			if (temp == root) {
				root = root.right;
			} else if (isLeft) {
				parent.left = temp.right;
			} else {
				parent.right = temp.right;
			}
		}

		// the node to be removed is an internal node
		else {
			TNode succ = getSuccessor(temp);
			if (temp == root) { // the node to be removed is the root
				root = succ;
			} else if (temp == parent.left) {
				parent.left = succ;
			} else {
				parent.right = succ;
			}
			succ.left = temp.left;
		}
		return true;
	}

	public BSTSet union(BSTSet s) {
		BSTSet temp = this;

		if (this.root == null) {
			return s;
		}
		if (s.root == null) {
			return this;
		}
		temp.preOrderAddition(temp, s.root);
		return temp;
	}

	public void preOrderAddition(BSTSet s, TNode node) {
		if (node == null) {
			return;
		}
		this.add(node.element);
		preOrderAddition(s, node.left);
		preOrderAddition(s, node.right);
	}

	public BSTSet intersection(BSTSet s) {
		if (this.root == null) {
			return this;
		}
		if (s.root == null) {
			return s;
		}
		s.preOrderDeletion(this, s.root);
		return s;
	}

	public void preOrderDeletion(BSTSet s, TNode node) {
		if (node == null) {
			return;
		}
		if (!s.isIn(node.element)) {
			this.remove(node.element);
		}
		preOrderDeletion(s, node.left);
		preOrderDeletion(s, node.right);

	}

	public BSTSet difference(BSTSet s) {
		if (this.root == null) {
			return s;
		}
		if (s.root == null) {
			return this;
		}
		this.preOrderDifference(this, s.root);
		return this;

	}

	public void preOrderDifference(BSTSet s, TNode node) {
		if (node == null) {
			return;
		}
		if (s.isIn(node.element)) {
			remove(node.element);
		}
		preOrderDeletion(s, node.left);
		preOrderDeletion(s, node.right);

	}

	public int size() {
		int size;
		if (root == null) {
			return 0;
		} else {
			size = sizeCount(root);
		}
		return size;
	}

	public int sizeCount(TNode node) {
		if (node == null) {
			return 0;
		} else {
			return sizeCount(node.left) + sizeCount(node.right) + 1;
		}
	}

	public int height() {
		if (root == null) {
			return -1;
		} else {
			return heightCount(root);
		}
	}

	public int heightCount(TNode node) {
		if (node == null) {
			return -1;
		} else {
			return ((heightCount(node.left) > heightCount(node.right)) ? heightCount(node.left)
					: heightCount(node.right)) + 1;
		}
	}

	public void printBSTSet() {
		if (root == null)
			System.out.println("The set is empty");
		else {
			System.out.print("The set elements are: ");
			printBSTSet(root);
			System.out.print("\n");
		}
	}

	private void printBSTSet(TNode t) {
		if (t != null) {
			printBSTSet(t.left);
			System.out.print(" " + t.element + ", ");
			printBSTSet(t.right);
		}
	}

	public void printNonRec() {
		TNode temp = root;
		MyStack stack = new MyStack();
		while (temp != null || !stack.isEmpty()) {
			while (temp != null) {
				stack.push(temp);
				temp = temp.left;
			}
			if (!stack.isEmpty()) {
				TNode node = stack.pop();
				System.out.print(node.element + ",");
				temp = node.right;
			}
		}
	}

	public void printLevelOrder() {
		if (root != null) {
			MyQueue q = new MyQueue();
			q.enqueue(root);

			while (q.first != q.end) {
				TNode node = q.dequeue();
				System.out.println(node.element + ",");

				if (node.left != null) {
					q.enqueue(node.left);
				}
				if (node.right != null) {
					q.enqueue(node.right);
				}
			}
		}
	}

	public TNode getSuccessor(TNode del) {// the next node should be on the right side and the smallest one
		TNode temp = del.right; // (only right node is possible)
		TNode succ = del; // store the key node which should be deleted
		TNode succParent = null;
		while (temp != null) {
			succParent = succ;
			succ = temp;
			temp = temp.left; // we want to get the smallest node on the right side
		}
		if (succ != del.right) {
			succParent.left = succ.right;
			succ.right = del.right;
		}
		return succ;
	}

	public void preOrderTravel(TNode node) {
		if (node == null) {
			return;
		}

		preOrderTravel(node.left);
		preOrderTravel(node.right);
	}

	public class MyStack {
		private Node top;

		public boolean isEmpty() {
			if (top == null) {
				return true;
			} else {
				return false;
			}
		}

		public TNode top() {
			return top.field;
		}

		public TNode pop() {
			if (top == null) {
				System.out.println("Empty stack.");
			}
			TNode pop = this.top();
			top = top.next;
			return pop;
		}

		public void push(TNode newNode) {
			top = new Node(newNode, top);
		}
	}

	public class MyQueue {
		public Node first;
		public Node end;

		public MyQueue() {
			first = new Node(null, null);
			end = first;
		}

		public boolean isEmpty() {
			if (first == end) {
				return true;
			} else {
				return false;
			}
		}

		public void enqueue(TNode t) {
			end.next = new Node(t, null);
			end = end.next;
		}

		public TNode dequeue() {
			if (first == end) {
				System.out.println("Empty queue");
			}
			if (end == first.next) {
				end = first;
			}
			TNode field = first.next.field;
			first.next = first.next.next;
			return field;
		}

	}

	public class Node {// linked list
		public Node next;
		public TNode field;

		public Node(TNode field, Node next) {
			this.next = next;
			this.field = field;
		}
	}

	public static void main(String[] args) {
		int[] arr1 = { 2, 3, 7, 17, 8, 10, 30, 16, };
		int[] arr2 = { 2, 8, 9, 17, 30, };
		BSTSet bst = new BSTSet(arr1);
		BSTSet bst2 = new BSTSet(arr2);
		BSTSet result = new BSTSet();
		result = bst.union(bst2);// test cases 11
//		result = bst.difference(bst2);
//		bst.printBSTSet();
		result.printNonRec();
//		bst.printLevelOrder();
//		bst.add(10);
//		bst.remove(7);

//		System.out.println(bst.intersection(bst2));

	}
}
