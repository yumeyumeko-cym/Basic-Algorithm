package lab3;

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
