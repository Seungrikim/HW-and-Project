public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int arraySize;
    public ArrayDeque() {
        arraySize = 8;
        items = (Item[]) new Object[arraySize];
        size = 0;
        this.nextFirst = items.length / 2;
        this.nextLast = nextFirst + 1;
    }

    public void addFirst(Item item) {
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

    public void addLast(Item item) {
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

    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        } /*else if (nextFirst == (items.length - 1)) {
            nextFirst = nextLastChecker(nextFirst);
            Item first = items[nextFirst];
            size -= 1;
            System.out.println(first);
            return first;*/
        else {
            //Item first = items[nextFirst + 1];
            nextFirst = nextLastChecker(nextFirst);
            size -= 1;
            System.out.println(items[nextFirst]);
            return items[nextFirst];
        }
    }

    public Item removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            nextLast = nextFirstChecker(nextLast);
            size -= 1;
            System.out.println(items[nextLast]);
            return items[nextLast];
        }
    }

    public Item get(int index){
        //Item[] array = (Item[]) new Object[size];
        if (isEmpty() || index >= size) {
            return null;
        } else {
            System.out.println(sortedArray()[index]);
            return sortedArray()[index];
        }
    }

    public ArrayDeque(ArrayDeque<Item> other) {
        arraySize = 8;
        items = (Item[]) new Object[arraySize];
        size = 0;
        this.nextFirst = items.length / 2;
        this.nextLast = nextFirst + 1;
        for (int i = 0; i < other.size(); i += 1 ) {
            addLast(other.get(i));
        }

    }

    public int nextFirstChecker(int position) {
        if (position == 0) {
            return items.length - 1;
        } else {
            return position - 1;
        }
    }

    public int nextLastChecker(int position) {
        if (position == items.length - 1) {
            return 0;
        } else {
            return position + 1;
        }
    }

    public Item[] sortedArray() {
        Item[] array = (Item[]) new Object[size];
        int tracker = nextLastChecker(nextFirst);
        for (int i = 0; i < size; i++) {
            array[i] = items[tracker];
            tracker = nextLastChecker(tracker);
        }
        return array;
    }

    public void resize() {
        Item[] newArray = (Item[]) new Object[arraySize * 2];
        int position = (newArray.length / 2) - (size / 2);
        System.arraycopy(sortedArray(), 0, newArray, position, size);
        items = newArray;
        nextFirst = position - 1;
        nextLast = position + size;
    }


    public static void main(String[] args) {
        ArrayDeque A = new ArrayDeque();
        A.addFirst(1);
        A.addFirst(2);
        A.addFirst(3);
        A.addFirst(4);
        A.addFirst(5);
        A.addFirst(6);
        A.addLast(7);
        A.addLast(6);
        A.addLast(5);
        A.addLast(4);
        A.addLast(3);
        A.addLast(2);
        A.addLast(1);
        A.printDeque();
        A.resize();
        A.printDeque();
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
        A.printDeque();
    }

}
