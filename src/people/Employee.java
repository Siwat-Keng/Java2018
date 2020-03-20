package people;

import Interface.Member;

public class Employee extends User implements Member {

	public Employee(String username, String password, String job) {
		super(username, password, job);
	}
	public Employee(User employee) {
		super(employee.username, employee.password, employee.job);
	}
}
