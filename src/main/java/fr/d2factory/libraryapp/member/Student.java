package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;

public class Student extends Member{

	private String name;
	private String email;
    private float wallet;
    private int studiesLevel;
    private FeedBack feedback ; 
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();
    
    public Student(String name, String email, float wallet, FeedBack feedBack,
    		int studiesLevel, Map<Book, LocalDate> borrowedBooks) {
    	super(name, email, wallet, studiesLevel, borrowedBooks);
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

	public int getStudiesLevel() {
		return studiesLevel;
	}

	public void setStudiesLevel(int studiesLevel) {
		this.studiesLevel = studiesLevel;
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
		return TypeMemeber.STUDENT;
	}


	@Override
	public void payBook(int numberOfDays) {
		boolean isNormalBorrowing = numberOfDays <= 30;
		boolean isFirstYearStudent = studiesLevel == 1;
		
		
		// First year student
		if (isFirstYearStudent && isNormalBorrowing && numberOfDays <= 15) {
			// in this case the student won't pay any charges
			feedback = FeedBack.NORMAL;
		}
		
		if (isFirstYearStudent && isNormalBorrowing && numberOfDays >= 15) {
			// the student will pay the second few days after the free period
			feedback = FeedBack.NORMAL;
			setWallet(wallet - ((numberOfDays - 15)*10));
		}
		
		if (isFirstYearStudent && !isNormalBorrowing) {
			// the student will pay the extract charges for the late borrowing
			feedback = FeedBack.LATE;
			setWallet(wallet - ((15)*10) + (numberOfDays - 30)*15);
		}
		
		// NOT a first year student
		if (!isFirstYearStudent && isNormalBorrowing) {
			// Normal case for a student who's not in the first year
			feedback = FeedBack.NORMAL;
			setWallet(wallet - (numberOfDays*10));
		}
		else if (!isFirstYearStudent && !isNormalBorrowing) {
			// LATE case for a student who's not in the first year
			feedback = FeedBack.LATE;
			setWallet(wallet - ((30*10) + (numberOfDays - 30)*10));
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
