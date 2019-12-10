package application;

import java.util.LinkedList;

public interface UserNetworkADT {
	LinkedList<UserNode> userList = new LinkedList<UserNode>();
	

	public void setFriend(String username, String username2);
	
	
	public boolean deleteFriend(String username, String username2);
	
	
	public void createUser(String username);
	
	
	public boolean deleteUser(String username);
	
	
	public LinkedList<UserNode> getUserList();
	
	
	public UserNode getUser(String username);
	}

