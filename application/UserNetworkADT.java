///////////////////////////////////////////////////////////////////////////////
// Assignment				 UserWeb
// Title:            UserNetworkADT.java
// Semester:         CS400 Fall 2019
//
// Author:           Ben Haisting, Kennedy Soehren, Yatharth Bindal, 
//									 Robert Bourguignon, Luke Vandenheuvel
// Email:            bhaisting@wisc.edu, ksoehren@wisc.edu, ybindal@wisc.edu, 
//									 bourguignon@wisc.edu, lvandenheuve@wisc.edu
// CS Login:         haisting, soehren, yatharth, bourguignon, vandenheuvel
// Lecturer's Name:  Debra Deppeler
//
// Description: Interface for the UserNetwork class that gives the necessary 
//							methods for creating a map. UserNetwork implements this 
//							interface.
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;

import java.util.LinkedList;

/**
 * Interface for the
 */
public interface UserNetworkADT {

	/**
	 * Sets friendship connection between two users, and creates the users if they
	 * didn't previously exist in the network
	 * 
	 * @param username  - name of the first user
	 * @param username2 - name of the second user
	 */
	public void setFriend(String username, String username2);

	/**
	 * Removes friendship connection between two users, if they exist
	 * 
	 * @param username  - name of the first user
	 * @param username2 - name of the second user
	 * @return true if friendship was removed, false if the friendship didn't
	 *         exist or either user didn't exist
	 */
	public boolean deleteFriend(String username, String username2);

	/**
	 * Adds user to the user network
	 * 
	 * @param username - name of the user to be added
	 */
	public void createUser(String username);

	/**
	 * Deletes user from the user network, if they exist
	 * 
	 * @param username - name of the user to be removed
	 * @return true if user was deleted, false if user doesn't exist
	 */
	public boolean deleteUser(String username);

	/**
	 * Returns the LinkedList of all users in the network
	 * 
	 * @return UserList, the LinkedList of all UserNodes
	 */
	public LinkedList<UserNode> getUserList();

	/**
	 * Returns a UserNode given a username, and null if they aren't in the network
	 * 
	 * @param username - name of the user
	 * @return UserNode if user exists, null if they don't
	 */
	public UserNode getUser(String username);
}
