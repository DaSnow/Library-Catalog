package main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {

	private int bookId;
	private String bookTitle;
	private String author;
	private String genre;
	private LocalDate lastCheckOut;
	private boolean checkedOut;

	/**
	 * Creates a new instance of Book
	 * @param id
	 * @param title
	 * @param author
	 * @param genre
	 * @param lastChecked
	 * @param checkedOut
	 */
	public Book(int id, String title, String author, String genre, LocalDate lastChecked, boolean checkedOut) {
		this.bookId = id;
		this.bookTitle = title;
		this.author = author;
		this.genre = genre;
		this.lastCheckOut = lastChecked;
		this.checkedOut = checkedOut;
	}

	/**
	 * Book ID Getter
	 * @return ID
	 */
	public int getId() {
		return this.bookId;
	}

	/**
	 * Sets new ID
	 * @param id
	 */
	public void setId(int id) {
		this.bookId = id;
	}

	/**
	 * Book Title Getter
	 * @return Book Title
	 */
	public String getTitle() {
		return this.bookTitle;
	}

	/**
	 * Sets new Book Title
	 * @param title
	 */
	public void setTitle(String title) {
		this.bookTitle = title;
	}

	/**
	 * Book Author Getter
	 * @return
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * Sets new author
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Book Genre Getter
	 * @return
	 */
	public String getGenre() {
		return this.genre;
	}

	/**
	 * Sets new Genre
	 * @param genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * LastCheckOut Date Getter
	 * @return
	 */
	public LocalDate getLastCheckOut() {
		return this.lastCheckOut;
	}

	/**
	 * Sets new CheckOut Date
	 * @param lastCheckOut
	 */
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut = lastCheckOut;
	}

	/**
	 * CheckOut status Getter
	 * @return
	 */
	public boolean isCheckedOut() {
		return this.checkedOut;
	}

	/**
	 * Sets new CheckOut Status
	 * @param checkedOut
	 */
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	/**
	 * returns a combination of the book title followed by the author
	 * @return Book Title followed by the author all in uppercase
	 */
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		return (this.bookTitle + " by " + this.author).toUpperCase();
	}

	/**
	 * checks the amount days from the LastCheckOut Date and "2023-09-15" to calculate if it warrants 
	 * late fees and the fees amount
	 * @return late fees based on the difference between 2 dates
	 */
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
		float fees = 0;
		long daysSinceCheckOut = ChronoUnit.DAYS.between(lastCheckOut, LocalDate.parse("2023-09-15"));
		
		if (daysSinceCheckOut >= 31)
			fees = (float) (10 + 1.5 * (daysSinceCheckOut - 31));
		
		return fees;
	}
}
