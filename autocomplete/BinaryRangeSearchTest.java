package autocomplete;

import edu.princeton.cs.algs4.In;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class BinaryRangeSearchTest {

    private static Autocomplete linearAuto;
    private static Autocomplete binaryAuto;
    private static Collection<Term> terms;
    private BinaryRangeSearch b;
    private BinaryRangeSearch a;


    private static final String INPUT_FILENAME = "data/cities.txt";

    @Test
    public void findFirst() {
        List<Term> list = new ArrayList<Term>();

        list.add(new SimpleTerm("AAAAA", 11));
        list.add(new SimpleTerm("ABCDE", 12));
        list.add(new SimpleTerm("BCDEF", 13));

        list.add(new SimpleTerm("Seattle", 1));
        list.add(new SimpleTerm("Seattle2", 2));
        list.add(new SimpleTerm("Seattle3", 3));
        list.add(new SimpleTerm("Seattle4", 4));
        list.add(new SimpleTerm("Seattle5", 5));
        list.add(new SimpleTerm("Seattle6", 6));
        list.add(new SimpleTerm("Seattle7", 7));
        list.add(new SimpleTerm("Seattle8", 14));

        list.add(new SimpleTerm("ZZZZZZ", 8));
        list.add(new SimpleTerm("ZABC", 9));
        list.add(new SimpleTerm("ZZABC", 10));
        list.add(new SimpleTerm("ZZZZZZZZ", 7));


        b = new BinaryRangeSearch(list);
        // b.print(b.allMatches("Sea"));
        // System.out.println("First: " + b.findFirst(list, "Sea"));
        // System.out.println("Last: " + b.findLast(list, "Sea"));


        List<Term> list2 = new ArrayList<Term>();

        list2.add(new SimpleTerm("America", 1));
        list2.add(new SimpleTerm("Taipei", 2));
        list2.add(new SimpleTerm("Tainan", 3));
        list2.add(new SimpleTerm("Taiwan", 4));
        list2.add(new SimpleTerm("Zebra", 4));
        b.allMatches("Tai");
        //System.out.println("2_First: " + b.findFirst(list2, "Tai"));
        //System.out.println("2_Last: " + b.findLast(list2, "Tai"));

        a = new BinaryRangeSearch(list2);
        // a.print(a.allMatches("Tai"));
        //System.out.println("2_First: " + b.findFirst(list, "Tai"));
        //System.out.println("2_First: " + b.findLast(list, "Tai"));




        // assertEquals(linearAuto.allMatches("Seat"), binaryAuto.allMatches("Seat"));

    }

    /**
     * Creates LinearRangeSearch and BinaryRangeSearch instances based on the data from cities.txt
     * so that they can easily be used in tests.
     */
    @Before
    public void setUp() {
        if (terms != null) {
            return;
        }

        In in = new In(INPUT_FILENAME);
        int n = in.readInt();
        terms = new ArrayList<>(n);
        for (int i = 0; i < n; i += 1) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms.add(new SimpleTerm(query, weight));
        }

        linearAuto = new LinearRangeSearch(terms);
        binaryAuto = new BinaryRangeSearch(terms);
    }

    @Test
    public void testSimpleExample() {
        Collection<Term> moreTerms = List.of(
            new SimpleTerm("hello", 0),
            new SimpleTerm("world", 0),
            new SimpleTerm("welcome", 0),
            new SimpleTerm("to", 0),
            new SimpleTerm("autocomplete", 0),
                new SimpleTerm("Ormes", 3219),
                new SimpleTerm("Orléans", 2),
                new SimpleTerm("Ormesson-sur-Marne", 9922),
                new SimpleTerm("Ormelle", 4087),
                new SimpleTerm("Ormenis", 2939),
                new SimpleTerm("Ormea", 1967));
        BinaryRangeSearch brs = new BinaryRangeSearch(moreTerms);
        List<Term> expected = List.of(new SimpleTerm("Ormesson-sur-Marne", 9922),
                new SimpleTerm("Ormes", 0));
        System.out.println(binaryAuto.allMatches("Orm").size());


        // brs.print((List<Term>) moreTerms);
        // System.out.println("_First: " + brs.findFirst((List<Term>) moreTerms, "auto"));
        // System.out.println("_Last: " + brs.findLast((List<Term>) moreTerms, "auto"));
        // assertEquals(expected, brs.allMatches("Orm"));


    }


    @Test
    public void testExample() {
        List<Term> expected = List.of(
                new SimpleTerm("Ormesson-sur-Marne, France", 9922),
                new SimpleTerm("Ormelle, Italy", 4087),
                new SimpleTerm("Ormeniş, Romania", 3325),
                new SimpleTerm("Ormes, France", 3219),
                new SimpleTerm("Ormesby Saint Margaret, United Kingdom", 2939),
                new SimpleTerm("Ormea, Italy", 1967));

        assertEquals(expected, linearAuto.allMatches("Orme"));
        assertEquals(expected, binaryAuto.allMatches("Orme"));

        expected = List.of(
                new SimpleTerm("Ormesson-sur-Marne, France", 9922),
                new SimpleTerm("Ormes, France", 3219),
                new SimpleTerm("Ormesby Saint Margaret, United Kingdom", 2939));

        assertEquals(expected, linearAuto.allMatches("Ormes"));
        assertEquals(expected, binaryAuto.allMatches("Ormes"));

    }

    // Write more unit tests below.
}
