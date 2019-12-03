package application;

import java.util.LinkedList;

public class UserNode {
	String username;
	LinkedList<UserNode> friendList;
	
	public UserNode(String username) {
		this.username = username;
		this.friendList = new LinkedList<UserNode>();
	}
	
	public void addFriend(UserNode user) {				//Maybe change return value to boolean to tell if user already in or not
		boolean alreadyPresent = false;
		for(int i = 0; i < this.friendList.size(); i++) {
			if(this.friendList.get(i).equals(user)) {
				alreadyPresent = true;
				return;
			}
		}
		this.friendList.add(user);
		return;
	}
	
	public void removeFriend(UserNode user) {			//Same as above
		boolean notPresent = false;
		for(int i = 0; i < this.friendList.size(); i++) {
			if(this.friendList.get(i).equals(user)) {
				this.friendList.remove(user);
				return;
			}
		}
		notPresent = true;
		return;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public LinkedList<UserNode> getFriendList(){
		return this.friendList;
	}
	
}
