
public class OffByN implements CharacterComparator {
    private int position;

    public OffByN(int N) {
        this.position = N;
    }

    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == position);
    }
}
