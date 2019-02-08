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
        /*if (isEmpty()) {
            items[nextFirst] = item;
            nextFirst = nextFirstChecker(nextFirst);
        }*/ if (nextFirst != nextLast) {
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
        /*if (isEmpty()) {
            items[nextLast] = item;
            nextLast = nextLastChecker(nextLast);
        }*/ if (nextLast != nextFirst) {
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
        } else if (arraySizeChecker(size, items)) {
            downsize();
            nextFirst = nextLastChecker(nextFirst);
            size -= 1;
            return items[nextFirst];

        } else {
            nextFirst = nextLastChecker(nextFirst);
            size -= 1;
            return items[nextFirst];
        }
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else if (arraySizeChecker(size, items)) {
            downsize();
            nextLast = nextFirstChecker(nextLast);
            size -= 1;
            return items[nextLast];
        } else {
            nextLast = nextFirstChecker(nextLast);
            size -= 1;
            return items[nextLast];
        }
    }

    public T get(int index) {
        if (isEmpty() || index >= size) {
            return null;
        } else {
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

    private boolean arraySizeChecker(int i, T[] a) {
        return (((double) i / (double) a.length) < 0.25);
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

    private void downsize() {
        T[] newArray = (T[]) new Object[arraySize / 2];
        int position = (newArray.length / 2) - (size / 2);
        System.arraycopy(sortedArray(), 0, newArray, position, size);
        items = newArray;
        nextFirst = position - 1;
        nextLast = position + size;
        arraySize = arraySize / 2;
    }
}
