package com.example.catalogue.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.catalogue.model.Book;
import com.example.catalogue.service.CatalogueService;

@Service
public class CatalogueServiceImpl implements CatalogueService {

	final static Logger logger = Logger.getLogger(CatalogueServiceImpl.class.getName());

	private final List<Book> books = new ArrayList<>();

	public List<Book> getBooks() {
		return books;
	}

	@Override
	public void getBookByName(String name) {
		logger.info("inside getBookByName" + name);
	}

	@Override
	public List<Book> getAllBooks() {
		// TODO Auto-generated method stub
		return books;
	}

	@Override
	public void createBook(Book book) throws Exception {
		// TODO Auto-generated method stub
		if (validate(book)) {
			this.books.add(book);
		}else {
			throw new Exception("Exception in storing the book, as field values are not proper");
		}
		

	}

	private boolean validate(Book book) {
		if (book.getTitle() == null) {
			return false;
		}
		if(book.getIsbn()==null || book.getIsbn().length()<13) {
			return false;
		}
		return true;
	}

}
