package application;

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
		if(username.equals(username2)) {
			return;
		}
		for (UserNode i : this.userList) {
			if (i.getUsername().equals(username)) {
				name1 = i;
			}else if (i.getUsername().equals(username2)) {
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
	public void deleteFriend(String username, String username2) {
		UserNode name1 = null;
		UserNode name2 = null;
		for (UserNode i : this.userList) {
			if (i.getUsername().equals(username)) {
				name1 = i;
			}else if (i.getUsername().equals(username2)) {
				name2 = i;
			}
		}
		if (name1 == null || name2 == null) {
			return;
		}
		name1.removeFriend(name2);
		name2.removeFriend(name1);
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
	public void deleteUser(String username) {
		for (UserNode i : this.userList) {
			if (i.getUsername().equals(username)) {
				this.userList.remove(i);
			}
		}
	}

	@Override
	public LinkedList<UserNode> getUserList() {
		return this.userList;
	}

	@Override
	public UserNode getUser(String username) {
		for (UserNode i : this.userList) {
			if (i.getUsername().equals(username)) {
				return i;
			}
		}
		return null;
	}
}
