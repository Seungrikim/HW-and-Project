/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        int N = 1;
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
        String temp = "";
        int count = 0;
        int tempCount = 0;
        while (N != 94) {
            while(!in.isEmpty()) {
                String word = in.readString();
                CharacterComparator odo = new OffByN(N);
                if (word.length() >= minLength && palindrome.isPalindrome(word, odo)) {
                    //System.out.println(word);
                    count++;
                    if (word.length() > temp.length()) {
                        temp = word;
                    }
                }
            }
            if (count > tempCount) {
                tempCount = count;
            }
            N += 1;
        }
        System.out.println(tempCount);
        System.out.println(temp);
    }

}