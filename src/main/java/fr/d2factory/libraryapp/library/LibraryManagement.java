package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.member.Member;

public class LibraryManagement implements Library {
	
	
	private Member member;
	private Book book;
	private BookRepository bookRepository;
	
	
	

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
		if (member.isAbleToBorrow() && bookRepository.findBook(isbnCode) != null) {
			Map<Book, LocalDate> borrowedBooks = new HashMap<>();
			borrowedBooks.put(bookRepository.findBook(isbnCode), borrowedAt);
			member.setBorrowedBooks(borrowedBooks);
			return bookRepository.findBook(isbnCode);
		} 
		else if (!member.isAbleToBorrow()) {
			System.out.println("This user could not borrow a book");
			return null;
		}
		else if (bookRepository.findBook(isbnCode) == null) {
			throw new HasLateBooksException("This member has being late for returning a book");
		}
		else {
			return null;
		}
	}

	@Override
	public void returnBook(Book book, Member member) {
		member.payBook(book);
	}

}
