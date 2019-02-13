public class Palindrome  {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque newDeque = new LinkedListDeque();
        for (int i = 0; i < word.length(); i++) {
            newDeque.addLast(word.charAt(i));
        }
        return newDeque;
    }
    /*public boolean isPalindrome(String word){
        if (word.length() == 0 || word.length() == 1){
            return true;
        }
        for (int i = 0; i < (word.length() / 2) - 1; i++){
            if (word.charAt(i) != word.charAt(word.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }*/

    /** recursion version */
    public boolean isPalindrome(String word) {
        Deque testDeque = wordToDeque(word);
        if (word.length() == 1 || word.length() == 0) {
            return true;
        } else {
            return palindromeHelper((Character) testDeque.removeFirst(),
                     (Character) testDeque.removeLast(), testDeque);
        }
    }

    private boolean palindromeHelper(char a, char b, Deque d) {
        if (a != b) {
            return false;
        } else if (d.size() == 1 || d.size() == 0) {
            return true;
        } else {
            return palindromeHelper((Character) d.removeFirst(), (Character) d.removeLast(), d);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        for (int i = 0; i < (word.length() / 2); i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}
