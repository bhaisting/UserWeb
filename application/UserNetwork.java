package application;

import java.util.LinkedList;

public class UserNetwork implements UserNetworkADT {
	LinkedList<UserNode> userList;
	
public UserNetwork() {
	this.userList = new LinkedList<UserNode>();
	
	
}

@Override
public void setFriend(String username, String username2) {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteFriend(String username, String username2) {
	// TODO Auto-generated method stub
	
}

@Override
public void createUser(String username) {					//Might want to add boolean flag to show if user already created
	boolean alreadyPresent = false;
	for(int i = 0; i < this.userList.size(); i++) {
		if(this.userList.get(i).getUsername().equals(username)) {
			alreadyPresent = true;
			return;
		}
	}
	UserNode newUser = new UserNode(username);
	this.userList.add(newUser);
	return;
}

@Override
public void deleteUser(String username) {					//Same as above
	boolean notPresent = false;
	for(int i = 0; i < this.userList.size(); i++) {
		if(this.userList.get(i).getUsername().equals(username)) {
			this.userList.remove(i);
			return;
		}
	}
	notPresent = true;
	return;
}

@Override
public LinkedList<UserNode> getUserList() {
	return this.userList;
}

@Override
public UserNode getUser(String username) {
	for(int i = 0; i < this.userList.size(); i++) {
		if(this.userList.get(i).getUsername().equals(username)) {
			return this.userList.get(i);
		}
	}
	return null;
}
}
