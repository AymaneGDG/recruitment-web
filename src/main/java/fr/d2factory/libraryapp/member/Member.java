package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
	
	private String name;
	private String email;
	
    /**
     * An initial sum of money the member has
     */
    private float wallet;
    
    /**
     * studies level
     * 1 : first year
     * 2: second year
     * ..etc
     */
    private int studiesLevel;
    
    /**
     * 
     * @author aymane
     * An enum for the different types of members
     */
    enum TypeMemeber {STUDENT, RESIDENT} ; 
    
    /**
     * An enum for the status of a borrowed book normal/late
     * normal : before the deadline
     * late : after the deadline
     */
    enum FeedBack {LATE, NORMAL} ; 
    
    /**
     * The books borrowed and the their dates of borrowing by this member 
     */
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();
    
   
    public Member(String name, String email, float wallet, 
    		int studiesLevel, Map<Book, LocalDate> borrowedBooks ) {
    	this.name = name;
    	this.email = email;
    	this.wallet = wallet;
    	this.studiesLevel = studiesLevel;
    }
    
    public Member(String name, String email, float wallet, 
    		Map<Book, LocalDate> borrowedBooks ) {
    	this.name = name;
    	this.email = email;
    	this.wallet = wallet;
    }
    
    /**
     * verify the type of member
     * 
     * Using a template design pattern when the parent calls 
     * a method in the child class
     */
    public abstract void payBook(Book book);

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);
    
    /**
     * The member could borrow a book from the library
     */
    public abstract boolean isAbleToBorrow();
    
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


	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}
    
    
}
