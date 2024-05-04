package roomescape.domain.exception;

public class IllegalNullArgumentException extends IllegalArgumentException {
    public IllegalNullArgumentException() {
        super("인자 중에 입력되지 않은 null 값이 존재합니다.");
    }
}
