package filters;

import twitter4j.Status;

import java.util.List;

/**
 * A filter that represents the logical not of its child filter
 */
public class AndFilter implements Filter {
    private final Filter child1, child2;

    public AndFilter(Filter child1, Filter child2) {
        this.child1 = child1;
        this.child2 = child2;
    }

    /**
     * A not filter matches when its child doesn't, and vice versa
     * @param s     the tweet to check
     * @return      whether or not it matches
     */
    @Override
    public boolean matches(Status s) {
        return child1.matches(s) && child2.matches(s);
    }

    @Override
    public List<String> terms() {
        List<String> concatList = child1.terms();
        concatList.addAll(child2.terms());
        return concatList;
    }

    public String toString() {
        return String.format("(%s and %s)", child1.toString(), child2.toString());
    }
}
