package asgn2Customers;

import asgn2Exceptions.CustomerException;

/** A class that represents a customer that has chosen to have their pizza delivered by a drone. 
 * This class extends the abstract Customer class and calculates the delivery distance as the Euclidean 
 * Distance between the customer and the restaurant.  A description of the class's
 * fields and their constraints is provided in Section 5.2 of the Assignment Specification.
 * 
 * @author Gyeongmin Jee
 *
 */
public class DroneDeliveryCustomer extends Customer {
	final static String TYPE = "Drone Delivery";

	/**
	 *  This class represents a customer of the Pizza Palace restaurant that has chosen to have their pizza delivered by 
	 *  a drone.  A CustomerException is thrown if the any of the constraints listed in Section 5.2 of the Assignment
	 *  Specification are violated. 
     *
     * <P> PRE: TRUE
     * <P> POST: All field values are set
     *
	 * @param name - The Customer's name 
	 * @param mobileNumber - The customer mobile number
	 * @param locationX - The customer x location relative to the Pizza Palace Restaurant measured in units of 'blocks' 
	 * @param locationY  The customer y location relative to the Pizza Palace Restaurant measured in units of 'blocks' 
	 * @throws CustomerException if:
	 * 1. the length of the name is below 0 or more than 20,
	 * 2. the name contains characters other than alphabet and spaces,
	 * 3. the name contains only white spaces
	 * 5. the mobile number length is not 10, does not begin with 0 or contains characters other than numbers
	 * 6. the locationX and location Y are 0
	 * 7. the location is beyond valid distance from the restaurant
	 * 
	 */
	
	public DroneDeliveryCustomer(String name, String mobileNumber, int locationX, int locationY) throws CustomerException {
		super(name, mobileNumber, locationX, locationY, TYPE);
	}

	/**
	 * Returns the Euclidean Distance between the instance of DroneDeliveryCustomer and the restaurant. Overrides  
	 * getDeliveryDistance() in Customer.
	 * 
     * @return The distance between the restaurant and the customer in Euclidean distance.
	 */
	@Override
	public double getDeliveryDistance() {
		double distance;
		double xDiff = Math.pow((locationX - RESTAURANT_X), 2);
		double yDiff = Math.pow((locationY - RESTAURANT_Y), 2);
		distance = Math.sqrt(xDiff + yDiff);
		return distance;
	}
	

}
