package utils;

public class UIDProvider {

	private static int uid = 1;
	
	private UIDProvider() {}
	
	public static synchronized int getUID() {
		return uid++;
	}
}
