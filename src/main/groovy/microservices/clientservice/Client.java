package microservices.clientservice;

public class Client {
	private String firstName;
	private String lastName;
	private Integer age;
	private String loanId;

	public Client() {
	}

	public Client(String firstName, String lastName, Integer age, String loanId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.loanId = loanId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAge() {
		return age;
	}

	public String getLoanId() {
		return loanId;
	}

}