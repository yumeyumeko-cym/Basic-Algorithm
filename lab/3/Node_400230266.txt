package lab3;

public class Node {//linked list
	public Node next;
	public TNode field;
	
	public Node(TNode field,Node next) {
		this.next = next;
		this.field = field;
	}
}
