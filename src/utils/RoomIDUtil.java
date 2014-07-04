package utils;

import java.util.PriorityQueue;

/**
 * This class is used for room ID assignment.
 * It guarantees that each ID given is unique
 * 
 * @author Harry
 *
 */
public class RoomIDUtil {
	private static final PriorityQueue<Integer> PQ = new PriorityQueue<Integer>();
	/**
	 * invariant: PQ.isEmpty() || (PQ.poll() < availableID)
	 */
	private static int availableID = 0;
	
	// This class is not to be initialized
	private RoomIDUtil() {}
	
	/**
	 * Retrieves a unique available room ID
	 * @return availableID : next available ID
	 */
	public synchronized static int getAvailableID() {
		if (!PQ.isEmpty()) 
			return PQ.poll();
		return availableID++;
	}
	
	/**
	 * Returns ID when room is deleted
	 * @param id : id to be removed
	 */
	public synchronized static void returnID(int id) {
		if (id == availableID - 1)
			availableID = id;
		else
			PQ.add(id);
	}
}
