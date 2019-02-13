
public class OffByN implements CharacterComparator {
    public int position;

    OffByN(int N) {
        this.position = N;
    }

    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == position);
    }
}
