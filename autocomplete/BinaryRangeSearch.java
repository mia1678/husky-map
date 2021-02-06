package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BinaryRangeSearch implements Autocomplete {
    private ArrayList<Term> list;

    /**
     * Validates and stores the given terms.
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Collection<Term> terms) {
        if (terms == null) {
            throw new IllegalArgumentException("The terms cannot be null.");
        } else {
            this.list = new ArrayList<Term>(terms);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null) {
                    throw new IllegalArgumentException("The terms cannot contain null.");
                }
            }
        }
        Collections.sort(list);
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public List<Term> allMatches(String prefix) {
        if (prefix == null){
            throw new IllegalArgumentException("The prefix cannot be null.");
        } else if (prefix == "") {
            // when string is empty, return the entire list
            List<Term> l = new ArrayList<Term>();
            for (int i = 0; i < list.size(); i++) {
                l.add(list.get(i));
            }
            Collections.sort(l, Term.byReverseWeightOrder());
            return l;
        } else {
            int r = prefix.length();
            List<Term> l = new ArrayList<Term>();
            int first = findFirst(list, prefix);
            int last = findLast(list, prefix);
            // System.out.println("byPrefixOrder: ");
            // print(list);
            for (int i = first; i < last; i++) {
                l.add(list.get(i));
            }
            // System.out.println("From first to last:");
            // print(l);

            // System.out.println("First: " + first + "\nLast: " + last);
            Collections.sort(l, Term.byReverseWeightOrder());
            // System.out.println("byReverseWeightOrder: ");
            // print(l);
            return l;
        }
    }


    private int findFirst(List<Term> listt, String prefix){
        int left = 0;
        int right = listt.size() - 1;
        int mid = 0;
        while (left < right) {
            mid = (left + right)/2;
            int a = prefix.compareTo(listt.get(mid).queryPrefix(prefix.length()));
            if (a == 0) {
                //  we find the string that matches the prefix,
                right = mid;
            } else if (a > 0) {
                // prefix is after the current string
                left = mid + 1;
            } else {
                // prefix is before the current string
                right = mid - 1;
            }
        }
        return left;
    }

    private int findLast(List<Term> listt, String prefix){
        int size = listt.size();
        int left = 0;
        int right = listt.size() - 1;
        int mid = 0;
        while (left < right) {
            mid = (left + right)/2;
            int a = prefix.compareTo(listt.get(mid).queryPrefix(prefix.length()));
            if (a == 0) {
                //  we find the string that matches the prefix
                left = mid + 1;
            } else if (a > 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }


    private int findMid(List<Term> listt, String prefix){
        int size = listt.size();
        int l = 0;
        int r = listt.size() - 1;
        int mid = 0;
        while (l <= r) {
            mid = (l + r)/2;
            int a = prefix.compareTo(listt.get(mid).queryPrefix(prefix.length()));
            if (a == 0) {
                return mid;
            }
            if (a > 0) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    private void print(List<Term> l) {
        for (int i = 0; i < l.size(); i++) {
            System.out.println("Query: " + l.get(i).query());
        }
    }


}
