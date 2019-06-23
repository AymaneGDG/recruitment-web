package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
	
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();
    
    public BookRepository() {
    	
    }

    public void addBooks(List<Book> books){
    	ISBN isbn = new ISBN(4L);
    	books.add(new Book("title", "title", isbn));
    }

    @SuppressWarnings("unlikely-arg-type")
	public Book findBook(long isbnCode) {
        return availableBooks.get(isbnCode);
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        this.borrowedBooks.put(book, borrowedAt);
    }

    @SuppressWarnings("unlikely-arg-type")
	public LocalDate findBorrowedBookDate(Book book) {
    	if (borrowedBooks.get(book.getIsbn()) == null) {
    		return null;
    	}
    	else {
    		return borrowedBooks.get(book.getIsbn());
    	}
    }
}
