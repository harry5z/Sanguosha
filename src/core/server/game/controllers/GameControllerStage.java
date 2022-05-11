package core.server.game.controllers;

/**
 * A hacky helper interface to avoid implementing "nextStage" on all those
 * stage enums, e.g. DamageStage.
 * 
 * <p>NOTE: This interface MUST be implemented by an enum</p>
 * <p>NOTE: This enum MUST have an "END" value which is used to indicate the unloading stage</p>
 * 
 * @param <T> an enum that needs "nextStage" method
 */
public interface GameControllerStage<T extends Enum<T>> {

	@SuppressWarnings("unchecked")
	default public T nextStage() {
		T[] values = (T[]) this.getClass().getEnumConstants();
		return values[(((T) this).ordinal() + 1) % values.length];
	}
	
	@SuppressWarnings("unchecked")
	default public boolean isLastStage() {
		T[] values = (T[]) this.getClass().getEnumConstants();
		return ((T) this).ordinal() == values.length - 1;
	}
	
}
