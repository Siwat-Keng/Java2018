package people;

import java.time.LocalDate;

import Exception.MyInvalidException;

public class Patient {

	private String name;
	private String room;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String from;
	private String to;

	public Patient(String name, LocalDate fromDate, LocalDate toDate , String room) throws MyInvalidException {
		this.name = name;
		this.room = room;
		setDate(fromDate, toDate);
	}
	
	public Patient(Patient patient) throws MyInvalidException {
		this.name = patient.name;
		this.room = patient.room;
		setDate(patient.fromDate, patient.toDate);	
	}

	public Patient(String name) throws MyInvalidException {
		this.name = name;
		this.room = "";
		setDate(LocalDate.now(),LocalDate.now());
	}

	public boolean equal(Patient patient) {
		if(this.name.equals(patient.getName())) return true;
		return false;
	}
	
	public void setDate(LocalDate fromDate, LocalDate toDate) throws MyInvalidException {
		if (fromDate.isAfter(toDate)) {
			throw new MyInvalidException("toDate can not be after fromDate");
		}
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.from = fromDate.toString();
		this.to = toDate.toString();
	}

	public String getName() {
		return this.name;
	}

	public LocalDate getfromDate() {
		return this.fromDate;
	}

	public LocalDate gettoDate() {
		return this.toDate;
	}

	public String getFrom() {
		return this.from;
	}

	public String getTo() {
		return this.to;
	}
	
	public String getRoom() {
		return this.room;
	}

}
