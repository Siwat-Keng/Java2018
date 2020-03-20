package people;

import Interface.Editable;
import Interface.Member;

public class Manager extends User implements Editable, Member {

	// TODO make scheduleTable

	public Manager(String username, String password, String job) {
		super(username, password, job);
	}

	public Manager(User user) {
		super(user.username,user.password,user.job);
	}

}
