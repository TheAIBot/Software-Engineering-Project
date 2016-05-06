package SoftwareHouse.ThrowingLambdaInterfaces;

import java.util.function.Consumer;

public interface ThrowingConsumer<T> {
	void accept(T t) throws Exception;
}
