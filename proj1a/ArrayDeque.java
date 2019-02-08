public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int arraySize = 8;
    public ArrayDeque() {
        items = (T[]) new Object[arraySize];
        size = 0;
        this.nextFirst = items.length / 2;
        this.nextLast = nextFirst + 1;
    }

    public void addFirst(T item) {
        if (isEmpty()) {
            items[nextFirst] = item;
            nextFirst = nextFirstChecker(nextFirst);
        } else if (nextFirst != nextLast) {
            items[nextFirst] = item;
            nextFirst = nextFirstChecker(nextFirst);
        } else if (nextFirst == nextLast) {
            resize();
            items[nextFirst] = item;
            nextFirst = nextFirstChecker(nextFirst);
        }
        size += 1;
    }

    public void addLast(T item) {
        if (isEmpty()) {
            items[nextLast] = item;
            nextLast = nextLastChecker(nextLast);
        } else if (nextLast != nextFirst) {
            items[nextLast] = item;
            nextLast = nextLastChecker(nextLast);
        } else if (nextLast == nextFirst) {
            resize();
            items[nextLast] = item;
            nextLast = nextLastChecker(nextLast);
        }
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int tracker = nextLastChecker(nextFirst);
        while (tracker != nextLast) {
            System.out.print(items[tracker] + " ");
            tracker = nextLastChecker(tracker);
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            //Item first = items[nextFirst + 1];
            nextFirst = nextLastChecker(nextFirst);
            size -= 1;
            System.out.println(items[nextFirst]);
            return items[nextFirst];
        }
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            nextLast = nextFirstChecker(nextLast);
            size -= 1;
            System.out.println(items[nextLast]);
            return items[nextLast];
        }
    }

    public T get(int index) {
        //Item[] array = (Item[]) new Object[size];
        if (isEmpty() || index >= size) {
            return null;
        } else {
            System.out.println(sortedArray()[index]);
            return sortedArray()[index];
        }
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[arraySize];
        size = 0;
        this.nextFirst = items.length / 2;
        this.nextLast = nextFirst + 1;
        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }

    }

    private int nextFirstChecker(int position) {
        if (position == 0) {
            return items.length - 1;
        } else {
            return position - 1;
        }
    }

    private int nextLastChecker(int position) {
        if (position == items.length - 1) {
            return 0;
        } else {
            return position + 1;
        }
    }

    private T[] sortedArray() {
        T[] array = (T[]) new Object[size];
        int tracker = nextLastChecker(nextFirst);
        for (int i = 0; i < size; i++) {
            array[i] = items[tracker];
            tracker = nextLastChecker(tracker);
        }
        return array;
    }

    private void resize() {
        T[] newArray = (T[]) new Object[arraySize * 2];
        int position = (newArray.length / 2) - (size / 2);
        System.arraycopy(sortedArray(), 0, newArray, position, size);
        items = newArray;
        nextFirst = position - 1;
        nextLast = position + size;
        arraySize = arraySize * 2;
    }


    public static void main(String[] args) {
        ArrayDeque A = new ArrayDeque();
        A.addFirst(1);
        A.addFirst(2);
        A.addFirst(3);
        A.addFirst(4);
        A.addFirst(5);
        A.addFirst(6);
        ArrayDeque B = new ArrayDeque(A);
        B.printDeque();
        B.printDeque();
        /*for (int i = 0; i < 100; i++) {
            A.addFirst(i);
        }
        A.printDeque();
        //A.resize();
        //A.printDeque();
        //System.out.println(Arrays.toString(A.sortedArray()));
        //A.get(2);
        //A.get(3);
        //A.get(6);
        //A.get(7);
        A.removeLast();
        A.removeLast();
        A.removeLast();
        A.removeLast();
        A.removeLast();
        //A.addLast(9);
        //A.addLast(10);
        A.printDeque();*/
    }

}
