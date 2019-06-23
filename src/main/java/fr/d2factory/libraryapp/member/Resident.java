package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import java.time.temporal.ChronoUnit;

public class Resident extends Member {
	
	
	private String name;
	private String email;
    private float wallet;
    private FeedBack feedback ; 
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();
    
    public Resident(String name, String email, FeedBack feedBack, float wallet, 
    		int studiesLevel, Map<Book, LocalDate> borrowedBooks ) {
    	super(name, email, wallet, borrowedBooks);
        this.feedback = feedBack ; 
        this.borrowedBooks = borrowedBooks;
    	
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getWallet() {
		return wallet;
	}

	public void setWallet(float wallet) {
		this.wallet = wallet;
	}

	public FeedBack getFeedback() {
		return feedback;
	}

	public void setFeedback(FeedBack feedback) {
		this.feedback = feedback;
	}

	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	public TypeMemeber getTypeMember() {
		return TypeMemeber.RESIDENT;
	}


	@Override
	public void payBook(int numberOfDays) {
		boolean isNormalBorrowing = numberOfDays <= 60;
		if (isNormalBorrowing) {
			feedback = FeedBack.NORMAL;
			setWallet(wallet - (numberOfDays*10));
		}
		else {
			feedback = FeedBack.LATE;
			setWallet(wallet - ((numberOfDays - 60)*20) + 60*10);
		}
		
	}

	@Override
	public boolean isAbleToBorrow() {
		return borrowedBooks.isEmpty();
	}

	@Override
	public void payBook(Book book) {
		BookRepository bookRepository = new BookRepository();
		
		// we calculate the difference between the date of borrowing 
		// and the date of returning the book
		Long l = (ChronoUnit.DAYS.between(bookRepository.findBorrowedBookDate(book), 
				LocalDate.now())) ;
		int nbrDaysLong = l.intValue();
		payBook(nbrDaysLong);
	}

}
