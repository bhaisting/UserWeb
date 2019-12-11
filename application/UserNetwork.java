package application;

import java.util.ArrayList;
import java.util.LinkedList;

public class UserNetwork implements UserNetworkADT {
	LinkedList<UserNode> userList;

	public UserNetwork() {
		this.userList = new LinkedList<UserNode>();
	}

	@Override
	public void setFriend(String username, String username2) {
		UserNode name1 = null;
		UserNode name2 = null;
		if (username.equals(username2)) {
			return;
		}
		for (UserNode i : this.userList) {
			if (i.getUsername().equals(username)) {
				name1 = i;
			} else if (i.getUsername().equals(username2)) {
				name2 = i;
			}
		}
		if (name1 == null) {
			name1 = new UserNode(username);
			this.userList.add(name1);
		}
		if (name2 == null) {
			name2 = new UserNode(username2);
			this.userList.add(name2);
		}
		name1.addFriend(name2);
		name2.addFriend(name1);
	}

	@Override
	public boolean deleteFriend(String username, String username2) {
		UserNode name1 = null;
		UserNode name2 = null;
		for (UserNode i : this.userList) {
			if (i.getUsername().equals(username)) {
				name1 = i;
			} else if (i.getUsername().equals(username2)) {
				name2 = i;
			}
		}
		if (name1 == null || name2 == null || !name1.getFriendList().contains(name2)) {
			return false;
		}
		name1.removeFriend(name2);
		name2.removeFriend(name1);
		return true;
	}

	@Override
	public void createUser(String username) {
		UserNode newUser = new UserNode(username);
		for (UserNode i : this.userList) {
			if (i.getUsername().equals(username)) {
				return;
			}
		}
		this.userList.add(newUser);
	}

	@Override
	public boolean deleteUser(String username) {
		UserNode deletedUser = null;
		for (UserNode i : userList) {
			if (i.getUsername().equals(username)) {
				userList.remove(i);
				deletedUser = i;
				break;
			}
		}
		if (deletedUser != null) {
			for (UserNode i : userList) {
				i.removeFriend(deletedUser);
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public LinkedList<UserNode> getUserList() {
		return userList;
	}

	@Override
	public UserNode getUser(String username) {
		for (UserNode i : userList) {
			if (i.getUsername().equals(username)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Returns the mutual friends of two names, if either name doesn't exist,
	 * returns null. If the names are the same, all friends of that user will be
	 * printed
	 * 
	 * @param name1 - Name of the first user
	 * @param name2 - Name of the second user
	 * @return LinkedList of mutual friends
	 */
	public LinkedList<UserNode> getMutualFriends(UserNode user1, String name2) {
		LinkedList<UserNode> mutualList = new LinkedList<UserNode>();
		UserNode user2 = getUser(name2);
		if (user2 == null) {
			return null;
		}
		for (UserNode i : user1.getFriendList()) {
			if (user2.getFriendList().contains(i)) {
				mutualList.add(i);
			}
		}
		return mutualList;
	}

	public int getNumGroups() {
		LinkedList<UserNode> tempList = new LinkedList<UserNode>();
		for (UserNode i : userList) {// necessary to ensure that userList isn't
																	// changed
			tempList.add(i);
		}
		int count = 0;
		while (!tempList.isEmpty()) {
			numGroupHelper(tempList, tempList.get(0));
			count++;
		}
		return count;
	}

	private void numGroupHelper(LinkedList<UserNode> bigList, UserNode node) {
		if (bigList.remove(node)) {
			for (UserNode i : node.getFriendList()) {
				numGroupHelper(bigList, i);
			}
		}
	}

	/**
	 * 
	 * @param name1
	 * @param name2
	 * @return
	 */
	public LinkedList<UserNode> shortestPath(String name1, String name2) {
		UserNode user1 = getUser(name1);
		UserNode user2 = getUser(name2);
		if (user1 == null || user2 == null) {
			return null;
		}
		ArrayList<Object[]> tempList = new ArrayList<Object[]>();
		LinkedList<Object[]> queue = new LinkedList<Object[]>();
		Object[] initial = { user1, new LinkedList<UserNode>() };
		queue.add(initial);
		for (UserNode i : userList) {
			Object[] arr = { i, false };
			tempList.add(arr);
		}

		while (queue.size() != 0) {
			Object[] curr = queue.remove();
			for (Object[] obj : tempList) {
				if (obj[0].equals(curr[0])) {
					if ((boolean) obj[1] == false) { // node hasn't been visited
						obj[1] = true;
						UserNode node = (UserNode) curr[0];
						LinkedList<UserNode> inner = new LinkedList<UserNode>();
						for (UserNode i : (LinkedList<UserNode>) curr[1]) {
							inner.add(i);
						}
						inner.add(node);
						if (node.equals(user2)) { // shortest path found!
							return inner;
						}
						for (UserNode i : node.getFriendList()) {
							Object[] newArr = { i, inner };
							queue.add(newArr);
						}
					} else { // node has been visited
						break;
					}
				}
			}
		}
		return new LinkedList<UserNode>();
	}

	public static void main(String args[]) {
		UserNetwork net = new UserNetwork();
		net.setFriend("joe","bob");
		net.setFriend("joe","ben");
		net.setFriend("ben","tar");
		net.setFriend("ben","bob");
		net.createUser("ste");
		for(UserNode i:net.shortestPath("joe","joe")) {
			System.out.println(i.getUsername());
		}
	}

}
