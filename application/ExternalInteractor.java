package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ExternalInteractor {
	private UserNetwork network;
	private String log = "";
	private PrintWriter toLog;

	public ExternalInteractor(UserNetwork net) {
		network = net;
		try {
			toLog = new PrintWriter("log.txt");
		} catch (Exception e) {
			System.out.println("An error was thrown creating the log: " + e);
		}
	}
	
	public void clearNetwork() {
		log="";
		try {
			toLog = new PrintWriter("log.txt");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public UserNode load(String fileName) {
		try {
			Scanner scan = new Scanner(new File(fileName));
			UserNode perspective = null;
			while (scan.hasNext()) {
				String inputText = scan.nextLine();
				updateLog(inputText);
				String input[] = inputText.split(" ");
				if (input[0].equals("a")) {
					if (input.length == 3) {
						network.setFriend(input[1], input[2]);
					} else {
						network.createUser(input[1]);
					}
				} else if (input[0].equals("r")) {
					if (input.length == 3) {
						network.deleteFriend(input[1], input[2]);
					} else {
						network.deleteUser(input[1]);
					}
				} else {
					if (network.getUser(input[1]) != null) {
						Main.perspective = true;
						Main.perspectivePerson = network.getUser(input[1]);
						perspective = Main.perspectivePerson;
					}
				}
			}
			scan.close();
			// handles fringe case where the perspective of a deleted person is shown
			if (!network.getUserList().contains(Main.perspectivePerson)) {
				Main.perspective = false;
				Main.perspectivePerson = null;
				return null;
			}
			return perspective;
		} catch (Exception e) {
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
		if (line.equals("clear")) {
			log = "";
			try {
			toLog = new PrintWriter("log.txt");
			}catch(Exception e) {
				System.out.println(e);
			}
		} else {
			toLog.println(line);
			log += line + "\n";
		}
	}

	public boolean saveLog(String fileName) {
		toLog.close();
		try {
			PrintWriter printer = new PrintWriter(fileName);
			printer.print(log);
			printer.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
