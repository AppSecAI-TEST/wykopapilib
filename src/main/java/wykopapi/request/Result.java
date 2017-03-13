package wykopapi.request;

import wykopapi.dto.ErrorInfo;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Result<T> {
    private final T value;
    private final ErrorInfo error;

    private Result(T value) {
        this.value = value;
        this.error = new ErrorInfo(0, "Success");
    }

    private Result(ErrorInfo errorInfo) {
        this.value = null;
        this.error = errorInfo;
    }

    public static <T> Result<T> success(T value) {
        if (value == null) throw new NullPointerException();
        return new Result<>(value);
    }

    public static <T> Result<T> error(ErrorInfo errorInfo) {
        return new Result<>(errorInfo);
    }

    public boolean isSuccess() {
        return value != null;
    }

    public boolean isError() {
        return value == null;
    }

    public T get() {
        if (value == null) throw new NullPointerException(error.getMessage());
        return value;
    }

    public T orElse(T other) {
        return value == null ? other : value;
    }

    public T orElseGet(Supplier<T> other) {
        return value == null ? other.get() : value;
    }

    public <X extends Throwable> T orElseThrow(Supplier<X> exceptionSupplier) throws X {
        if (value == null) throw exceptionSupplier.get();
        return value;
    }

    public Result<T> ifSuccess(Consumer<T> consumer) {
        if (isSuccess()) consumer.accept(value);
        return this;
    }

    public Result<T> ifError(Consumer<ErrorInfo> consumer) {
        if (isError()) consumer.accept(error);
        return this;
    }

    public void consume(Consumer<T> ifSuccess, Consumer<ErrorInfo> ifError) {
        if (isSuccess()) ifSuccess.accept(value);
        else ifError.accept(error);
    }

    public int getErrorCode() {
        return error.getCode();
    }

    public String getErrorMessage() {
        return error.getMessage();
    }


}
