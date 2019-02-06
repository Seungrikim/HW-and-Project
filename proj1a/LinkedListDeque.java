public class LinkedListDeque<T> {
    private IntNode sentinel;
    private int size = 0;

    public class IntNode {
        public IntNode prev;
        public IntNode next;
        public T item;
        public IntNode(IntNode p, T i, IntNode n){
            prev = p;
            next = n;
            item = i;
        }
    }

    public LinkedListDeque() {
        //sentinel.next = sentinel.prev;
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public void addFirst(T x){
        if (size == 0){
            IntNode newNode = new IntNode(sentinel, x, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
        } else {
            IntNode newNode = new IntNode(sentinel, x, sentinel.next);
            sentinel.next = newNode;
            sentinel.next.prev = newNode;
            size += 1;
        }
    }

    public void addLast(T x){
        if (size == 0){
            IntNode newNode = new IntNode(sentinel, x, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
        } else {
            IntNode newNode = new IntNode(sentinel.prev, x, sentinel);
            sentinel.prev.next = newNode;
            sentinel.prev = newNode;
            size += 1;
        }
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }else{
            return false;
        }
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        IntNode copyNode = sentinel.next;
        //copyNode = sentinel.next;
        for (int j = 0; j < size; j++){
            System.out.print(copyNode.item + " ");
            copyNode = copyNode.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            IntNode copyNode = sentinel.next;
            T firsttItem = sentinel.next.item;
            sentinel.next.item = null;
            sentinel.next = copyNode.next;
            copyNode.next = sentinel;
            size -= 1;
            return firsttItem;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            IntNode copyNode = sentinel.prev;
            T lastItem = sentinel.prev.item;
            sentinel.prev.item = null;
            sentinel.prev = copyNode.prev;
            copyNode.next = sentinel;
            size -= 1;
            return lastItem;
        }
    }

    public T get(int index){
        if (size == 0) {
            return null;
        } else {
            IntNode newPointer = sentinel;
            for (int i = 0; i < index; i++) {
                newPointer = newPointer.next;
            }
            return newPointer.next.item;
        }
    }

    public LinkedListDeque(LinkedListDeque other){
        LinkedListDeque newDeque = new LinkedListDeque();
        if (isEmpty()){
            newDeque.sentinel = other.sentinel;
        }
        while (other.sentinel.next != sentinel){
            newDeque.addFirst(other.sentinel.next.item);
        }
    }

    public T getRecursive(int index){
        return null;
    }

    /*public static void main(String[] args) {
        LinkedListDeque x = new LinkedListDeque();
        LinkedListDeque y = new LinkedListDeque();
        x.addFirst(10);
        x.addFirst(9);
        x.addFirst(8);
        x.addFirst(7);
        x.addFirst(6);
        x.addFirst(5);
        x.addLast(11);
        x.printDeque();
        System.out.println(x.size());
        System.out.println(x.removeFirst());
        x.printDeque();
        System.out.println(x.size());
        System.out.println(x.removeLast());
        x.printDeque();
        System.out.println(x.size());
        System.out.println(x.get(4));
    }*/
}
