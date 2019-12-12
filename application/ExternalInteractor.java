///////////////////////////////////////////////////////////////////////////////
// Assignment				 UserWeb
// Title:            ExternalInteractor.java
// Semester:         CS400 Fall 2019
//
// Author:           Ben Haisting, Kennedy Soehren, Yatharth Bindal, 
//									 Robert Bourguignon, Luke Vandenheuvel
// Email:            bhaisting@wisc.edu, ksoehren@wisc.edu, ybindal@wisc.edu, 
//									 bourguignon@wisc.edu, lvandenheuve@wisc.edu
// CS Login:         haisting, soehren, yatharth, bourguignon, vandenheuvel
// Lecturer's Name:  Debra Deppeler
//
// Description: ExternalInteractor handles all external interactions (saving and
//							loading files) as well as logging all information necessary to
//							recreate the graph again. Note: log.txt will reset when the
//							program is restarted, it is recommended, therefore, to save
//							files at a different location.
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Responsible for all external interactions with files, also keeps log and
 * status data for some functions of the program.
 */
public class ExternalInteractor {
	private UserNetwork network;
	private String log = "";
	private PrintWriter toLog;
	public String status = "None"; // edited when a command is typed

	/**
	 * Constructor, creates connection to log.txt so that it can be printed to at
	 * all times, and initializes network.
	 * 
	 * @param net - UserNetwork being worked with
	 */
	public ExternalInteractor(UserNetwork net) {
		network = net;
		try {
			toLog = new PrintWriter("log.txt");
		} catch (Exception e) {
			System.out.println("An error was thrown creating the log: " + e);
		}
	}

	/**
	 * Clears the log and log.txt when the UserNetwork is cleared
	 */
	public void clearNetwork() {
		log = "";
		status = "cleared";
		try {
			toLog = new PrintWriter("log.txt"); // clears log.txt
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Loads a file and takes in all commands line by line, accounts for bad input
	 * or incorrect commands
	 * 
	 * @param fileName - name of file to be loaded
	 * @return UserNode if perspective is to be displayed, null if general
	 *         perspective is to be displayed
	 */
	public UserNode load(String fileName) {
		try {
			Scanner scan = new Scanner(new File(fileName));
			UserNode perspective = null;
			while (scan.hasNext()) {
				String inputText = scan.nextLine();
				updateLog(inputText);
				if (Main.validateInput(inputText)) { // Checks line for bad input
					String input[] = inputText.split(" ");
					if (input[0].equals("a")) { // add cases
						if (input.length == 3) { // add friend
							network.setFriend(input[1], input[2]);
						} else if (input.length == 2) { // add user
							network.createUser(input[1]);
						}
					} else if (input[0].equals("r")) { // remove cases
						if (input.length == 3) { // remove friend
							network.deleteFriend(input[1], input[2]);
						} else if (input.length == 2) { // remove user
							network.deleteUser(input[1]);
						}
					} else if (input[0].equals("s")) { // perspective change case
						if (network.getUser(input[1]) != null) {
							Main.perspective = true;
							Main.perspectivePerson = network.getUser(input[1]);
							perspective = Main.perspectivePerson;
						}
					}
				}
			}
			status = "File loaded!";
			scan.close();
			// handles fringe case where the perspective of a deleted person is shown
			if (!network.getUserList().contains(Main.perspectivePerson)) {
				Main.perspective = false;
				Main.perspectivePerson = null;
				return null;
			}
			return perspective;
		} catch (Exception e) {
			status = "Load failed";
			System.out.println("An error was thrown: " + e);
		}
		return null;
	}

	/**
	 * Takes in an input line and inserts it into the log
	 * 
	 * @param line - Command line to be put into the log
	 */
	public void updateLog(String line) {
		toLog.println(line);
		status = line;
		log += line + "\n";
	}

	/**
	 * Given a fileName, the log is printed to the fileName, and the printwriter
	 * for log.txt is closed.
	 * 
	 * @param fileName - Name of the file where the log will be printed
	 * @return true if save was successful, false if it wasn't
	 */
	public boolean saveLog(String fileName) {
		toLog.close();
		if (fileName != null) {
			try {
				PrintWriter printer = new PrintWriter(fileName);
				printer.print(log);
				printer.close();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
}
