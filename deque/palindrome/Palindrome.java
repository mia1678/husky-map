package deque.palindrome;

import deque.ArrayDeque;
import deque.Deque;

public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> w = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++){
            w.addLast(word.charAt(i));
        }
        return w;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1){
            return true;
        } else {
            return helper(wordToDeque(word), 0, word.length() -1);
        }
    }

    private boolean helper(Deque<Character> word, int front, int back){
        if (word.removeFirst() == word.removeLast()){
            if (front + 1 < back - 1){
                return helper(word, front + 1, back - 1);
            }
            return true;
        }
        return false;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1){
            return true;
        } else {
            // word = word.toLowerCase();
            boolean compare = cc.equalChars(word.charAt(0), word.charAt(word.length() - 1));
            String w = word.substring(1, word.length() - 1);
            return compare && isPalindrome(w, cc);
        }
    }
}