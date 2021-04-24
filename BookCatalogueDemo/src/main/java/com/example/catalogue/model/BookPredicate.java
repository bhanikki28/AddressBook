package com.example.catalogue.model;

import java.util.function.Predicate;

public class BookPredicate {

	public static Predicate<Book> getBookByTitle(String title) {
		return book -> book.getTitle().equalsIgnoreCase(title);
	}
	
	public static Predicate<Book> getBookByIsbn(String isbn) {
		return book -> book.getIsbn().equalsIgnoreCase(isbn);
	}
	
	public static Predicate<Book> getPredicate(String filterType,String filterValue) {
	    switch (filterType) {
	        case "title":
	            return BookPredicate.getBookByTitle(filterValue);
	        case "isbn":
	        	return BookPredicate.getBookByIsbn(filterValue);
	        case "author":
	            return BookPredicate.getBookByIsbn(filterValue);
	        default:
	            break;
	    }
	    return null;
	}
}
