public class LinkedListDeque<T> {
    private IntNode sentinel;
    private int size = 0;

    private class IntNode {
        private IntNode prev;
        private IntNode next;
        private T item;
        public IntNode(IntNode p, T i, IntNode n) {
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

    public void addFirst(T x) {
        if (size == 0) {
            IntNode newNode = new IntNode(sentinel, x, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
            size += 1;
        } else {
            IntNode newNode = new IntNode(sentinel, x, sentinel.next);
            sentinel.next.prev = newNode;
            sentinel.next = newNode;
            size += 1;
        }
    }

    public void addLast(T x) {
        if (size == 0) {
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

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        IntNode copyNode = sentinel.next;
        //copyNode = sentinel.next;
        for (int j = 0; j < size; j++) {
            System.out.print(copyNode.item + " ");
            copyNode = copyNode.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            //IntNode copyNode = sentinel.next;
            T firsttItem = sentinel.next.item;
            sentinel.next.item = null;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return firsttItem;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            //IntNode copyNode = sentinel.prev;
            T lastItem = sentinel.prev.item;
            sentinel.prev.item = null;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            //sentinel.prev = copyNode.prev;
            //copyNode.next = sentinel;
            size -= 1;
            return lastItem;
        }
    }

    public T get(int index) {
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

    public LinkedListDeque(LinkedListDeque other) {
        LinkedListDeque newDeque = new LinkedListDeque();
        IntNode newPointer = other.sentinel;
        if (isEmpty()) {
            newDeque.sentinel = new IntNode(null, null, null);
        }
        while (newPointer.next.next != sentinel) {
            newDeque.addFirst(newPointer.next.item);
            newPointer.next = newPointer.next.next;

        }
    }

    public T getRecursive(int index) {
        IntNode newPointer = new IntNode(null, null, null);
        newPointer = sentinel.next;
        return getRecursiveHelper(index, newPointer);
    }

    private T getRecursiveHelper(int index, IntNode p) {
        if (index == 0) {
            return p.item;
        }
        /*else {
            p.next = p.next.next;
        }*/
        return getRecursiveHelper(index - 1, p.next);
    }

    /*public static void main(String[] args) {

    }*/
}
