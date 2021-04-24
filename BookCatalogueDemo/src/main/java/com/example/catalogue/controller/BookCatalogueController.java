package com.example.catalogue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogue.config.Producer;
import com.example.catalogue.model.Book;
import com.example.catalogue.model.BookPredicate;
import com.example.catalogue.service.CatalogueService;

@RestController
@RequestMapping("/book")

public class BookCatalogueController {

	final static Logger logger = Logger.getLogger(BookCatalogueController.class.getName());

	@Autowired
	CatalogueService service;
	
	@Autowired
	Producer producer;


	@GetMapping("/list")
	public List<Book> getBook() {
		return service.getAllBooks();
	}

	@PostMapping("/create")
	public ResponseEntity<?> createBook(@RequestBody Book book) {
		logger.info("Storing/updating the entry:" + book.getTitle());
		try {
			service.createBook(book);
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().build();
			
		}
		producer.sendMessage("Book:"+book.getTitle()+"created !");
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete")
	public String deleteBook(@RequestParam String title) {
		
		service.getAllBooks().removeIf(BookPredicate.getBookByTitle(title));
		producer.sendMessage("Book:"+title+" got deleted !");

		return "Book got deleted!";
	}

	@PostMapping("/search")
	public List<Book> searchBook(@RequestBody String filterString) {
		final JSONObject obj = new JSONObject(filterString);
		Map<String, String> filterMap = new HashMap<>();
		ArrayList<String> filterList = new ArrayList<>(obj.keySet());
		for (String key : filterList) {
			filterMap.put(key, obj.get(key).toString());
		}
		ArrayList<String> keyList = new ArrayList<>(filterMap.keySet());
		List<Book> bookList = service.getAllBooks();
		logger.info("Book List size:" + bookList.size());

		for (String filterKey : keyList) {
			logger.info("Querying the list based on key:" + filterKey);
			logger.info("Querying the list based on value:" + filterMap.get(filterKey));

			bookList = bookList.stream()
					.filter(BookPredicate.getPredicate(filterKey, filterMap.get(filterKey).toString()))
					.collect(Collectors.toList());
			logger.info("Book List Size after applying filter{},size{}" + filterKey + ":" + bookList.size());
		}
		return bookList;
	}
}
