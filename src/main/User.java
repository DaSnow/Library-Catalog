package main;

import interfaces.List;

public class User {

	private int id;
	private String name;
	private List<Book> checkedOutList;
	
	/**
	 * Creates instance of the User Class
	 * @return A new User
	 */
	public User (int id, String name, List<Book> checkedOutList) {
		this.id = id;
		this.name = name;
		this.checkedOutList = checkedOutList;
	}
	
	/**
	 * User ID Getter
	 * @return ID
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets new ID
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Name Getter
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets new Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * CheckOutList Getter
	 * @return CheckOutList
	 */
	public List<Book> getCheckedOutList() {
		return this.checkedOutList;
	}

	/**
	 * Sets new CheckOutList
	 * @param checkedOutList
	 */
	public void setCheckedOutList(List<Book> checkedOutList) {
		this.checkedOutList = checkedOutList;
	}
	
}
