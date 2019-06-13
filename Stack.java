/**
 * @author Deanna Liu
 */
public class Stack<T>{

    private Node<T> head;
    public static final int MAX = Integer.MAX_VALUE;

    public Stack(){
        head = null;
    }

    public Stack(T t) {
        head = new Node<T>(t);
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int size = 0;
        Node<T> temp = head;
        while(temp != null)
        {
            if(size == MAX)
                return MAX;
            size++;
            temp = temp.getNext();
        }
        return size;
    }

    public void push(T t) {
        Node<T> temp = new Node<T>(t, head);
        head = temp;
    }

    public T pop() {
        if(size() == 0)
            return null;
        T takeout = head.getData();
        head = head.getNext();
        return takeout;
    }

    public T peek() {
        if(size() == 0)
            return null;
        return head.getData();
    }

    public String toString() {
        String info = "";
        Node<T> temp = head;
        if(size() == 1)
            return head.getData().toString();
        for(int i = 0; i <= size(); i++) {
            if (temp != null) {
                info += temp.getData() + " ";
                temp = temp.getNext();
            }
        }
        return info;
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
        public void setData(T data){
            this.data = data;
        }

        public void setNext(Node<T> next){
            this.next = next;
        }

        public void setNext(T t){
            this.next = new Node<>(t);
        }

        public T getData(){
            return this.data;
        }

        public Node<T> getNext(){
            return this.next;
        }
    }
}
