///////////////////////////////////////////////////////////////////////////////
// Assignment				 UserWeb
// Title:            UserNetwork.java
// Semester:         CS400 Fall 2019
//
// Author:           Ben Haisting, Kennedy Soehren, Yatharth Bindal, 
//									 Robert Bourguignon, Luke Vandenheuvel
// Email:            bhaisting@wisc.edu, ksoehren@wisc.edu, ybindal@wisc.edu, 
//									 bourguignon@wisc.edu, lvandenheuve@wisc.edu
// CS Login:         haisting, soehren, yatharth, bourguignon, vandenheuvel
// Lecturer's Name:  Debra Deppeler
//
// Description: Class that acts as a graph storing UserNodes. Contains method to
//							add and remove connections, add and remove users, and algorithm 
//							methods such as shortestPath and getNumGroups that use 
//							algorithms on the data they store
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Graph class, stores a list of UserNodes and manipulates the list through the
 * UserNetwork methods
 */
public class UserNetwork implements UserNetworkADT {
	LinkedList<UserNode> userList; // List of all users in the network

	/**
	 * Constructor, initializes an empty userList
	 */
	public UserNetwork() {
		this.userList = new LinkedList<UserNode>();
	}

	/**
	 * Sets friendship connection between two users, and creates the users if they
	 * didn't previously exist in the network
	 * 
	 * @param username  - name of the first user
	 * @param username2 - name of the second user
	 */
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

	/**
	 * Removes friendship connection between two users, if they exist
	 * 
	 * @param username  - name of the first user
	 * @param username2 - name of the second user
	 * @return true if friendship was removed, false if the friendship didn't
	 *         exist or either user didn't exist
	 */
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
		if (name1 == null || name2 == null
				|| !name1.getFriendList().contains(name2)) {
			return false;
		}
		name1.removeFriend(name2);
		name2.removeFriend(name1);
		return true;
	}

	/**
	 * Adds user to the user network
	 * 
	 * @param username - name of the user to be added
	 */
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

	/**
	 * Deletes user from the user network, if they exist
	 * 
	 * @param username - name of the user to be removed
	 * @return true if user was deleted, false if user doesn't exist
	 */
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

	/**
	 * Returns the LinkedList of all users in the network
	 * 
	 * @return UserList, the LinkedList of all UserNodes
	 */
	@Override
	public LinkedList<UserNode> getUserList() {
		return userList;
	}

	/**
	 * Returns a UserNode given a username, and null if they aren't in the network
	 * 
	 * @param username - name of the user
	 * @return UserNode if user exists, null if they don't
	 */
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
	 * printed.
	 * 
	 * @param name1 - Name of the first user
	 * @param name2 - Name of the second user
	 * @return LinkedList of mutual friends, null if user2 doesn't exist
	 */
	public LinkedList<UserNode> getMutualFriends(UserNode user1, String name2) {
		LinkedList<UserNode> mutualList = new LinkedList<UserNode>();
		UserNode user2 = getUser(name2);
		if (user2 == null) { // checks that user2 exists
			return null;
		}
		for (UserNode i : user1.getFriendList()) {
			if (user2.getFriendList().contains(i)) {
				mutualList.add(i);
			}
		}
		return mutualList;
	}

	/**
	 * Finds number of groups in the network
	 * 
	 * @return number of groups in the network
	 */
	public int getNumGroups() {
		LinkedList<UserNode> tempList = new LinkedList<UserNode>();
		for (UserNode i : userList) {// temporary list of all users
			tempList.add(i);
		}
		int count = 0;
		while (!tempList.isEmpty()) { // Removes one group at a time until the list
																	// of all users is empty
			numGroupHelper(tempList, tempList.get(0));
			count++;
		}
		return count;
	}

	/**
	 * Recursive helper method for getNumGroups, removes every member of a group
	 * from the bigList
	 * 
	 * @param bigList - temporary list of all users
	 * @param node    - current node being looked at
	 */
	private void numGroupHelper(LinkedList<UserNode> bigList, UserNode node) {
		if (bigList.remove(node)) {
			for (UserNode i : node.getFriendList()) {
				numGroupHelper(bigList, i);
			}
		}
	}

	/**
	 * Utilizes a breadth first search algorithm to find and return the shortest
	 * path between two users.
	 * 
	 * @param name1 - Name of the start user
	 * @param name2 - Name of the target user
	 * @return list of path from start user to target user, empty list if they
	 *         can't be connected, null if either name doesn't exist in the
	 *         network
	 */
	public LinkedList<UserNode> shortestPath(String name1, String name2) {
		UserNode user1 = getUser(name1);
		UserNode user2 = getUser(name2);
		if (user1 == null || user2 == null) { // if either name isn't in the network
			return null;
		}
		// tempList - contains each user with whether they have been visited or not
		ArrayList<Object[]> tempList = new ArrayList<Object[]>();
		// queue - ensures breadth first search, each array contains the UserNode
		// along with a LinkedList containing the path taken to get to it
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
						// inner - path taken to get to node
						LinkedList<UserNode> inner = new LinkedList<UserNode>();
						for (UserNode i : (LinkedList<UserNode>) curr[1]) {
							inner.add(i);
						}
						inner.add(node);
						if (node.equals(user2)) { // shortest path found!
							return inner;
						}
						// all friends of node are added to the queue along with the path to
						// get to them
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
		// no path found, both users exist but are in separate groups
		return new LinkedList<UserNode>();
	}

}
