package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import data_structures.ArrayList;
import interfaces.List;

public class LibraryGUI {

	private static LibraryCatalog LC;
	private JFrame frame;
	private JPanel panel;
	private JTable table;
	private String[][] data = new String[50][6];

	public LibraryGUI() throws IOException {
		LC = new LibraryCatalog();
		frame = new JFrame();
		panel = new JPanel();

//		panel.setBorder(BorderFactory.createEmptyBorder(30, 300, 100, 300));
		panel.setLayout(new GridLayout(0, 1));

		createTable();
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Library");
		frame.pack();
		frame.setVisible(true);
	}
	
	public void createTable() {		
		String[] columns = { "ID", "Title", "Author", "Genre", "Last Check Out Date", "Checked Out" };
		int index = 0;
		
		for (Book book : LC.getBookCatalog()) {
			String[] row = new String[6];
			row[0] = Integer.toString(book.getId());
			row[1] = book.getTitle();
			row[2] = book.getAuthor();
			row[3] = book.getGenre();
			row[4] = book.getLastCheckOut().toString();
			row[5] = Boolean.toString(book.isCheckedOut());
			data[index] = row;
			index += 1;
		}
		
		table = new JTable(data, columns);
		table.setBounds(660,40,200,300);
		JScrollPane sp = new JScrollPane(table);
		panel.add(sp);
	}

	public static void main(String[] args) throws IOException {
		new LibraryGUI();
	}
}
