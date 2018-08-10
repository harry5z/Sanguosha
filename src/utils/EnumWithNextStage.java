package utils;

/**
 * A hacky helper interface to avoid implementing "nextStage" on all those
 * stage enums, e.g. DamageStage. The "nextStage" method only works
 * if an enum implements this interface.
 * 
 * @param <T> an enum that needs "nextStage" method
 */
public interface EnumWithNextStage<T extends Enum<T>> {

	@SuppressWarnings("unchecked")
	default public T nextStage() {
		T[] values = (T[]) this.getClass().getEnumConstants();
		return values[(((T) this).ordinal() + 1) % values.length];
	}
}
