package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LinearRangeSearch implements Autocomplete {
    private ArrayList<Term> list;


    /**
     * Validates and stores the given terms.
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Collection<Term> terms) {
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
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public List<Term> allMatches(String prefix) {
        if (prefix == null){
            throw new IllegalArgumentException("The prefix cannot be null.");
        } else {
            int r = prefix.length();
            List<Term> l = new ArrayList<Term>();

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).queryPrefix(r).equals(prefix)) {
                    l.add(list.get(i));
                }
            }
            Collections.sort(l, Term.byReverseWeightOrder());
            return l;
        }
    }
}

