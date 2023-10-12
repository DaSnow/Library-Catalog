package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import data_structures.ArrayList;
import data_structures.DoublyLinkedList;
import data_structures.SinglyLinkedList;
import interfaces.FilterFunction;
import interfaces.List;

public class LibraryCatalog {

	private List<Book> catalog;
	private List<User> users;

	public LibraryCatalog() throws IOException {
		this.catalog = getBooksFromFiles();
		this.users = getUsersFromFiles();
	}

	/**
	 * Compiles a list of books from the file catalog.csv since books can be added or removed and I dont want 
	 * extra data
	 * @return Compiled list of books
	 * @throws IOException
	 */
	private List<Book> getBooksFromFiles() throws IOException {
		List<Book> catalog = new DoublyLinkedList<Book>();
		String line;
		BufferedReader file = new BufferedReader(new FileReader("./data/catalog.csv"));
		boolean flag = false;

		while ((line = file.readLine()) != null) {
			if (!flag) {
				flag = true;
				continue;
			}
			String[] fields = line.split(",");
			Book book = new Book(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3],
					LocalDate.parse(fields[4]), Boolean.parseBoolean(fields[5]));

			// I decided to add the books in 0 to make it more efficient that's why its
			// inverted compared to the expected report
			catalog.add(book);
		}

		file.close();
		return catalog;
	}

	/**
	 * Compiles a list of users from the file user.csv in an ArrayList since non are added or removed
	 * @return Compiled list of users
	 * @throws IOException
	 */
	private List<User> getUsersFromFiles() throws IOException {
		List<User> users = new ArrayList<User>();
		String line;
		BufferedReader file = new BufferedReader(new FileReader("./data/user.csv"));
		boolean flag = false;

		while ((line = file.readLine()) != null) {
			if (!flag) {
				flag = true;
				continue;
			}
			String[] fields = line.split(",");
			String[] rented = null;
			if (fields.length > 2) {
				rented = fields[2].replaceAll("[{}]", "").split(" ");
			}
			List<Book> books = new DoublyLinkedList<Book>();

			if (fields.length > 2)
				for (String n : rented)
					books.add(0, searchForBook(book -> book.getId() == Integer.parseInt(n)).get(0));

			User user = new User(Integer.parseInt(fields[0]), fields[1], books);
			users.add(user);
		}

		file.close();
		return users;
	}

	/**
	 * Book List Getter
	 * @return List of books
	 */
	public List<Book> getBookCatalog() {
		return this.catalog;
	}

	/**
	 * User List Getter
	 * @return List of Users
	 */
	public List<User> getUsers() {
		return this.users;
	}

	/**
	 * Creates a new book from the given parameters and adds it to the catalog list
	 * @param title
	 * @param author
	 * @param genre
	 */
	public void addBook(String title, String author, String genre) {
		Book book = new Book(catalog.size() + 1, title, author, genre, LocalDate.parse("2023-09-15"), false);
		catalog.add(book);
		return;
	}

	/**
	 * Removes a book from the catalog list given its ID
	 * @param id
	 */
	public void removeBook(int id) {
		for (Book book : catalog)
			if (book.getId() == id)
				catalog.remove(book);

		return;
	}

	/**
	 * Searches for a book in the catalog given an ID and checks it out if available;
	 * @param id
	 * @return True or false based on whether the book can be checkout or not
	 */
	public boolean checkOutBook(int id) {
		List<Book> books = searchForBook(book -> book.getId() == id && !book.isCheckedOut());

		for (Book book : books) {
			if (!book.isCheckedOut()) {
				book.setCheckedOut(true);
				book.setLastCheckOut(LocalDate.parse("2023-09-15"));
				return true;				
			}
		}

		return false;
	}

	/**
	 * Searches for a book given its ID and checks if it can be returned
	 * @param id
	 * @return True or false based on whether the book can be returned
	 */
	public boolean returnBook(int id) {
		List<Book> books = searchForBook(book -> book.getId() == id);

		for (Book book : books) {
			if (book.getId() == id && book.isCheckedOut()) {
				book.setCheckedOut(false);
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches though the book list given its ID and checks if  it can be checked out
	 * @param id
	 * @return True or False on whether the book is available for checkout
	 */
	public boolean getBookAvailability(int id) {
		return searchForBook(book -> book.getId() == id && !book.isCheckedOut()).size() > 0;
	}

	/**
	 * Searches though the book list given its title and counts how many there are
	 * @param title
	 * @return Count of books given a title
	 */
	public int bookCount(String title) {
		return searchForBook(book -> book.getTitle().equals(title)).size();
	}

	/**
	 * Generates a new file to report the amount of books given a genre, the amount of books checked out 
	 * and the total, and the amount of late fees per user and the total fees.
	 * @throws IOException
	 */
	public void generateReport() throws IOException {

		String output = "\t\t\t\tREPORT\n\n";
		output += "\t\tSUMMARY OF BOOKS\n";
		output += "GENRE\t\t\t\t\t\tAMOUNT\n";
		/*
		 * In this section you will print the amount of books per category.
		 * 
		 * Place in each parenthesis the specified count.
		 * 
		 * Note this is NOT a fixed number, you have to calculate it because depending
		 * on the input data we use the numbers will differ.
		 * 
		 * How you do the count is up to you. You can make a method, use the
		 * searchForBooks() function or just do the count right here.
		 */
		output += "Adventure\t\t\t\t\t"
				+ Integer.toString(searchForBook(book -> book.getGenre().equals("Adventure")).size()) + "\n";
		output += "Fiction\t\t\t\t\t\t"
				+ Integer.toString(searchForBook(book -> book.getGenre().equals("Fiction")).size()) + "\n";
		output += "Classics\t\t\t\t\t"
				+ Integer.toString(searchForBook(book -> book.getGenre().equals("Classics")).size()) + "\n";
		output += "Mystery\t\t\t\t\t\t"
				+ Integer.toString(searchForBook(book -> book.getGenre().equals("Mystery")).size()) + "\n";
		output += "Science Fiction\t\t\t\t\t"
				+ Integer.toString(searchForBook(book -> book.getGenre().equals("Science Fiction")).size()) + "\n";
		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + Integer.toString(catalog.size()) + "\n\n";

		/*
		 * This part prints the books that are currently checked out
		 */
		output += "\t\t\tBOOKS CURRENTLY CHECKED OUT\n\n";
		/*
		 * Here you will print each individual book that is checked out.
		 * 
		 * Remember that the book has a toString() method. Notice if it was implemented
		 * correctly it should print the books in the expected format.
		 * 
		 * PLACE CODE HERE
		 */

		for (Book book : searchForBook(book -> book.isCheckedOut())) {
			output += book.toString() + "\n";
		}

		output += "====================================================\n";
		output += "\t\t\tTOTAL AMOUNT OF BOOKS\t" + Integer.toString(searchForBook(book -> book.isCheckedOut()).size())
				+ "\n\n";

		/*
		 * Here we will print the users the owe money.
		 */
		output += "\n\n\t\tUSERS THAT OWE BOOK FEES\n\n";
		/*
		 * Here you will print all the users that owe money. The amount will be
		 * calculating taking into account all the books that have late fees.
		 * 
		 * For example if user Jane Doe has 3 books and 2 of them have late fees. Say
		 * book 1 has $10 in fees and book 2 has $78 in fees.
		 * 
		 * You would print: Jane Doe\t\t\t\t\t$88.00\n
		 * 
		 * Notice that we place 5 tabs between the name and fee and the fee should have
		 * 2 decimal places.
		 * 
		 * PLACE CODE HERE!
		 */

		float total = 0;
		List<User> checkedUsers = searchForUsers(user -> !user.getCheckedOutList().isEmpty());

		for (User user : checkedUsers) {
			float fee = 0;

			for (Book book : user.getCheckedOutList()) {
				fee += book.calculateFees();
			}

			output += user.getName() + "\t\t\t\t\t" + String.format("%.2f", fee) + "\n";
			total += fee;
		}

		output += "====================================================\n";
		output += "\t\t\t\tTOTAL DUE\t$" + String.format("%.2f", total) + "\n\n\n";
		output += "\n\n";
		System.out.println(output);// You can use this for testing to see if the report is as expected.

		/*
		 * Here we will write to the file.
		 * 
		 * The variable output has all the content we need to write to the report file.
		 * 
		 * PLACE CODE HERE!!
		 */

		File file = new File("./report/report.txt");
		BufferedWriter editableFile = new BufferedWriter(new FileWriter(file));
		editableFile.write(output);
		editableFile.close();
	}

	/*
	 * BONUS Methods
	 * 
	 * You are not required to implement these, but they can be useful for other
	 * parts of the project.
	 */
	
	/**
	 * Loops through the book list to filter a new book list given lambda function
	 * @param func
	 * @return Filtered book list given the lambda function
	 */
	public List<Book> searchForBook(FilterFunction<Book> func) {
		List<Book> booksFiltered = new DoublyLinkedList<Book>();

		for (Book book : catalog)
			if (func.filter(book))
				booksFiltered.add(book);

		return booksFiltered;
	}

	/**
	 * Loops through the user list to filter a new user list given lambda function
	 * @param func
	 * @return Filtered user list given the lambda function
	 */
	public List<User> searchForUsers(FilterFunction<User> func) {
		List<User> usersFiltered = new ArrayList<User>();

		for (User user : users)
			if (func.filter(user))
				usersFiltered.add(user);

		return usersFiltered;
	}

}
