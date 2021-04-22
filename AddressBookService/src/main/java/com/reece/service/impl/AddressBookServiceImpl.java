package com.reece.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reece.model.AddressBook;
import com.reece.model.Contact;
import com.reece.service.AddressBookService;

/**
 * 
 * @author jannikki 
 * Service to do add/update/remove/print contact to AddressBook
 *
 */
public class AddressBookServiceImpl implements AddressBookService {

	private static Logger logger = LoggerFactory.getLogger(AddressBookServiceImpl.class);

	private Map<String, AddressBook> books;

	public Set<String> getAllAdressBooks() {
		return getBooks().keySet();
	}

	public AddressBook createAddressBook(String name) {
		logger.info("Inside createAddressBook: creating address for:" + name);

		AddressBook book = new AddressBook(name);
		book.validate();
		getBooks().put(name, book);
		return book;
	}

	public void removeAddressBook(String name) {
		logger.info("Inside removeAddressBook: removing address for:" + name);
		getBooks().remove(name);
	}

	@Override
	public Contact addContact(Contact contact) {
		logger.info("Inside addContact: adding contact:" + contact.getName());
		return addContact(contact, DEFAULT_BOOK);
	}

	public Contact addContact(Contact contact, String addressBook) {
		if (contact == null)
			throw new RuntimeException("A contact is mandatory");

		contact.validate();

		AddressBook book = findOrCreateAddressBook(addressBook);

		contact.setBook(book);
		book.addContact(contact);

		return contact;
	}

	public void removeContactByName(String name) {
		removeContactByName(name, DEFAULT_BOOK);
	}

	public void removeContactByName(String name, String addressBook) {
		final AddressBook book = getAddressBook(addressBook);

		if (book == null)
			throw new RuntimeException("Address book not found: " + book);

		Contact contact = new Contact(name);
		book.removeContact(contact);
	}

	public Set<Contact> getContacts(String addressBook) {
		AddressBook book = getAddressBook(addressBook);
		if (book == null)
			throw new RuntimeException("Address book not found: " + addressBook);

		return book.getContacts();
	}

	public Set<Contact> getAllContacts() {
		final Set<Contact> allContacts = new HashSet<Contact>();
		for (String bookName : getBooks().keySet()) {
			allContacts.addAll(getContacts(bookName));
		}

		return allContacts;
	}

	public void printContacts(String addressBook) {
		logger.info("Contacts list in " + addressBook + ":");
		printContacts(getContacts(addressBook));
	}

	public void printAllContacts() {
		logger.info("Printing All Contacts:");
		printContacts(getAllContacts());
	}

	private Map<String, AddressBook> getBooks() {
		if (books == null) {
			books = new HashMap<String, AddressBook>();
			getBooks().put(DEFAULT_BOOK, new AddressBook(DEFAULT_BOOK));
		}

		return books;
	}

	private AddressBook getDefaultAddressBook() {
		return getBooks().get(DEFAULT_BOOK);
	}

	private AddressBook getAddressBook(String name) {
		if (name == null) {
			return getDefaultAddressBook();
		}
		return getBooks().get(name);
	}

	private AddressBook findOrCreateAddressBook(String addressBook) {
		AddressBook book = getAddressBook(addressBook);
		return book == null ? createAddressBook(addressBook) : book;
	}

	private void printContacts(Set<Contact> contacts) {
		contacts.stream().forEach(s -> System.out.println(s));
	}

}
