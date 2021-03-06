package asgn2Restaurant;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import asgn2Customers.Customer;
import asgn2Customers.CustomerFactory;
import asgn2Exceptions.CustomerException;
import asgn2Exceptions.LogHandlerException;
import asgn2Exceptions.PizzaException;
import asgn2Pizzas.Pizza;
import asgn2Pizzas.PizzaFactory;

/**
 *
 * A class that contains methods that use the information in the log file to return Pizza 
 * and Customer object - either as an individual Pizza/Customer object or as an
 * ArrayList of Pizza/Customer objects.
 * 
 * @author Matthew Holdsworth and Gyeongmin Jee
 *
 */
public class LogHandler {
	
	final static int LOG_STRING_NUM_PARAMETERS = 9;
	final static String COMMA = ",";
	private static ArrayList<Customer> customers;
	private static ArrayList<Pizza> pizzas;
	private static String[] pizzaParameters;
	
	/**
	 * Returns an ArrayList of Customer objects from the information contained in the log file ordered as they appear in the log file.
	 * @param filename The file name of the log file
	 * @return an ArrayList of Customer objects from the information contained in the log file ordered as they appear in the log file. 
	 * @throws CustomerException If the log file contains an invalid customer code that is not one of PUC, DNC or DVC, or the log file contains invalid customer detail such that constructor of each customer types throw CustomerException.
	 * @throws LogHandlerException If there was a problem with the log file not related to the semantic errors above such as parsing errors and invalid number of parameters, or if there are other exceptions not specified such as accessing invalid index.
	 */
	public static ArrayList<Customer> populateCustomerDataset(String filename) throws CustomerException, LogHandlerException{		 
		 try{
			 customers = new ArrayList<Customer>();
			 BufferedReader br = new BufferedReader(new FileReader(filename));
			 String line = br.readLine(); 
			 while (line != null){
				 customers.add(createCustomer(line));
				 line = br.readLine();	 
			 }
			 br.close();
			 return customers;
		 } catch (CustomerException e){
			 throw new CustomerException(e.getMessage());
		//All other exceptions are translated to LogHandlerException
		 } catch (Exception e){
			 throw new LogHandlerException(e.getMessage());
		 }
	}		

	/**
	 * Returns an ArrayList of Pizza objects from the information contained in the log file ordered as they appear in the log file. .
	 * @param filename The file name of the log file
	 * @return an ArrayList of Pizza objects from the information contained in the log file ordered as they appear in the log file. .
	 * @throws PizzaException If the log file contains semantic errors leading that violate the pizza constraints listed in Section 5.3 of 
	 * the Assignment Specification or contain an invalid pizza code (passed by another class).
	 * @throws LogHandlerException If there was a problem with the log file not related to the semantic errors above such as parsing errors and invalid number of parameters, or if there are other exceptions not specified such as accessing invalid index.
	 * 
	 */
	public static ArrayList<Pizza> populatePizzaDataset(String filename) throws PizzaException, LogHandlerException {
		try {
			//Create new arraylist 'pizzas'
			pizzas = new ArrayList<Pizza>();
			//Create new buffered reader to read in the log file
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			//Store the input in a variable
			String input = reader.readLine();
			//While that variable is not empty, add pizza to dataset
			while (input != null) {
				pizzas.add(createPizza(input));
				input = reader.readLine();
			}//end while loop
			//Close the reader and return the arraylist
			reader.close();
			return pizzas;
		} catch (PizzaException e) {
			//To catch invalid pizza exceptions
			throw new PizzaException(e.getMessage());
		} catch (Exception e) {
			//To catch invalid log file input
			throw new LogHandlerException(e.getMessage());
		}//end try-catch block
	}//end PopulateDataSet<Pizza>		

	/**
	 * Creates a Customer object by parsing the  information contained in a single line of the log file. The format of 
	 * each line is outlined in Section 5.3 of the Assignment Specification.  
	 * @param line - A line from the log file
	 * @return- A Customer object containing the information from the line in the log file
	 * @throws CustomerException - If the log file contains semantic errors (invalid customer detail) that causes customer class constructor to throw CustomerException or contain an invalid customer code (passed by another class).
	 * @throws LogHandlerException - If there was a problem with the log file not related to the semantic errors above such as parsing errors and invalid number of parameters, or if there are other exceptions not specified such as accessing invalid index.
	 */
	public static Customer createCustomer(String line) throws CustomerException, LogHandlerException{
		//Throw a LogHandlerException if the line is empty or has no comma
		if (line == "" || !line.contains(COMMA)){
			throw new LogHandlerException("The line is empty or is not comma separated");
		}
		String[] parameters = line.split(COMMA);
		//Throw a LogHandlerException if the number of parameters is not correct
		if (parameters.length != LOG_STRING_NUM_PARAMETERS){
			throw new LogHandlerException("One of the line does not contain the right number of parameters");
		}
		
		try {
			String name = parameters[2];
			String mobile = parameters[3];
			String code = parameters[4];
			int locX = Integer.parseInt(parameters[5]);
			int locY = Integer.parseInt(parameters[6]);
			
			Customer newCustomer = CustomerFactory.getCustomer(code, name, mobile, locX, locY);
			return newCustomer;
		} catch (CustomerException e){
			throw new CustomerException(e.getMessage());
		} catch (Exception e){
			//Parsing related exceptions (e.g. locationX or locationY containing non-numeric characters)
			throw new LogHandlerException("Parsing error. Incorrect locationX or locationY or Non integer locationx/y");
		}
	}
	
	/**
	 * Creates a Pizza object by parsing the information contained in a single line of the log file. The format of 
	 * each line is outlined in Section 5.3 of the Assignment Specification.  
	 * @param line - A line from the log file
	 * @return- A Pizza object containing the information from the line in the log file
	 * @throws PizzaException If the log file contains semantic errors leading that violate the pizza constraints listed in Section 5.3
	 * of the Assignment Specification or contain an invalid pizza code (passed by another class).
	 * @throws LogHandlerException - If there was a problem with the log file not related to the semantic errors above such as parsing errors and invalid number of parameters, or if there are other exceptions not specified such as accessing invalid index.
	 */
	public static Pizza createPizza(String line) throws PizzaException, LogHandlerException{
		//Throw exception if the line passed is empty or contains no commas
		if (line == "" || !line.contains(COMMA)){
			throw new LogHandlerException("The line is empty or is not comma separated");
		}//end if
		//Split the input by each comma and add to 'parameters' array
		String[] pizzaParameters = line.split(COMMA);
		//If the length of the array is not equal to the expected number of log parameters, throw exception
		if (pizzaParameters.length != LOG_STRING_NUM_PARAMETERS){
			throw new LogHandlerException("A line does not contain the right number of parameters");
		}//end if
		try {
			//Assign parameters to respective fields with parsing
			String pizzaCode = pizzaParameters[7];
			int quantity = Integer.parseInt(pizzaParameters[8]);
			//Used to check that the time format is "HH:mm:ss"
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalTime orderTime = LocalTime.parse(pizzaParameters[0], formatter);
			LocalTime deliveryTime = LocalTime.parse(pizzaParameters[1], formatter);
			//Creates a new pizza with the provided parameters and returns it
			Pizza newPizza = PizzaFactory.getPizza(pizzaCode, quantity, orderTime, deliveryTime);
			return newPizza;
		} catch (PizzaException e) {
			//To catch invalid pizza exceptions
			throw new PizzaException(e.getMessage());
		} catch (Exception e) {
			//To catch invalid logfile input exceptions
			throw new LogHandlerException("Parsing error. Incorrect orderTime, deliveryTime or quantity");
		}//end try-catch block
	}//end CreatePizza

}//end LogHandler
