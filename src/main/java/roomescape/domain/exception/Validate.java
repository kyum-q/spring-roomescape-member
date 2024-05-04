package roomescape.domain.exception;

public class Validate {
    public static void AllNonNull(Object... objects) {
        for (Object object : objects) {
            requireNonNull(object);
        }
    }

    private static <T> void requireNonNull(T obj) {
        if (obj == null)
            throw new IllegalNullArgumentException();
    }

}
