package SoftwareHouse.ThrowingLambdaInterfaces;

public interface ThrowingConsumer<T> {
	void accept(T t) throws Exception;
}
