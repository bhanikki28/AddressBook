package com.example.catalogue.service;

import java.util.List;

import com.example.catalogue.model.Book;

public interface CatalogueService {

	public void getBookByName(String name);

	public void createBook(Book book) throws Exception;

	public List<Book> getAllBooks();
}
