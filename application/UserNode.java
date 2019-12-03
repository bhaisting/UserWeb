///////////////////////////////////////////////////////////////////////////////
// Assignment				 UserWeb
// Title:            UserNode.java
// Semester:         CS400 Fall 2019
//
// Author:           Ben Haisting, Kennedy Soehren, Yatharth Bindal, 
//									 Robert Bourguignon, Luke Vandenheuvel
// Email:            bhaisting@wisc.edu, ksoehren@wisc.edu, ybindal@wisc.edu, 
//									 bourguignon@wisc.edu, lvandenheuve@wisc.edu
// CS Login:         haisting, soehren, yatharth, bourguignon, vandenheuvel
// Lecturer's Name:  Debra Deppeler
//
// Description: UserNode node class that contains username and friendList as 
//							well as basic methods to manipulate them. This is the core data
//							of the UserNetwork ADT
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;

import java.util.LinkedList;

public class UserNode {
	String username; // name of the User
	LinkedList<UserNode> friendList; // List of all friends of the user

	public UserNode(String username) {
		this.username = username;
		this.friendList = new LinkedList<UserNode>();
	}

	/**
	 * Adds friend to friendList, if present
	 * 
	 * @param user - Friend to be added to the friendList
	 */
	public void addFriend(UserNode user) {
		if (!friendList.contains(user)) {
			this.friendList.add(user);
		}
	}

	/**
	 * Removes friend from friendList, if present
	 * 
	 * @param user - Friend to be removed
	 */
	public void removeFriend(UserNode user) { // Same as above
		friendList.remove(user);
	}

	/**
	 * Returns username
	 * 
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns friendList
	 * 
	 * @return friendList
	 */
	public LinkedList<UserNode> getFriendList() {
		return this.friendList;
	}

}
