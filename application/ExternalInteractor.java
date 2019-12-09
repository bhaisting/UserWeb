package application;

import java.io.File;
import java.util.Scanner;

public class ExternalInteractor {
	private UserNetwork network;

	public ExternalInteractor(UserNetwork net) {
		network = net;
	}

	public UserNode load(String fileName) {
		try {
			Scanner scan = new Scanner(new File(fileName));
			UserNode perspective = null;
			while (scan.hasNext()) {
				String input[] = scan.nextLine().split(" ");
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
			return perspective;
		} catch (Exception e) {
			System.out.println("An error was thrown: " + e);
		}
		return null;
	}
}
