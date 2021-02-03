public class ChildTrace<T> extends Trace<T> {

    @SafeVarargs
    private ChildTrace(T head, T... items) {
    	super(head, items);
    }

    @SafeVarargs
    public static <T> ChildTrace<T> of(T head, T... items) {
        return new ChildTrace<T>(head, items);
    }

}