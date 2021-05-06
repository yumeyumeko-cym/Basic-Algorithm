package lab3;

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
