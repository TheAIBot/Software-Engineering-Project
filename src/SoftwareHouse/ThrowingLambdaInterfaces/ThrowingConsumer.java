package SoftwareHouse.ThrowingLambdaInterfaces;

/**
 * @author Andreas
 */
public interface ThrowingConsumer<T> {
	void accept(T t) throws Exception;
}
