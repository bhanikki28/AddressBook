package com.reece.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reece.model.Contact;
import com.reece.service.impl.AddressBookServiceImpl;

public class AcceptanceCriteriaTest {
	private AddressBookService bookService;

	/*
	 * Initializing the addressBook with few contacts
	 */
	@Before
	public void before() throws Exception {
		bookService = new AddressBookServiceImpl();
		// Add some contacts to the default address book
		bookService.addContact(new Contact("Police", "000"));

		// Add contacts to other address books
		bookService.addContact(new Contact("Dad", "0429147328"), "family");
		bookService.addContact(new Contact("Mom", "0429147329"), "family");

		bookService.addContact(new Contact("Nikhita", "0450147326"), "friends");
		bookService.addContact(new Contact("Arun", "0450147327"), "friends");
		bookService.addContact(new Contact("Swati", "0450147328"), "friends");

		bookService.addContact(new Contact("Sachin", "0429147370"), "work");
	}

	/*
	 * method to tear down the service , after running all the tests
	 */
	@After
	public void teardown() throws Exception {
		bookService = null;
	}

	/**
	 * Test case for adding a contact to default address book
	 */

	@Test
	public void addContactTest() {
		bookService.addContact(new Contact("Ambulance", "000"));

		assertEquals(8, bookService.getAllContacts().size());
		assertTrue(bookService.getAllContacts().contains(new Contact("Ambulance")));
	}

	/*
	 * Test case to remove a contact and assert contact size to confirm it
	 */
	@Test
	public void removeContactTest() {
		bookService.removeContactByName("Ambulance");

		Set<Contact> contacts = bookService.getAllContacts();
		assertEquals(7, contacts.size());
		assertFalse(contacts.contains(new Contact("Ambulance")));
	}

	/*
	 * Test case to get contact from a particular address book
	 */
	@Test
	public void getContactsInAdressBookTest() {
		Set<Contact> contacts = bookService.getContacts("friends");
		assertTrue(contacts.contains(new Contact("Nikhita")));
		assertTrue(contacts.contains(new Contact("Arun")));
		assertTrue(contacts.contains(new Contact("Swati")));

		contacts = bookService.getContacts("family");
		assertTrue(contacts.contains(new Contact("Dad")));
		assertTrue(contacts.contains(new Contact("Mom")));

		contacts = bookService.getContacts("work");
		assertTrue(contacts.contains(new Contact("Sachin")));

		contacts = bookService.getContacts("default");
		assertTrue(contacts.contains(new Contact("Police")));
	}

	/*
	 * Test case to maintain multiple address book
	 */
	@Test
	public void maintainMultipleAddressBooksTest() {

		// Assert the existence of all address books
		Set<String> books = bookService.getAllAdressBooks();
		assertEquals(4, books.size());
		assertTrue(books.contains("family"));
		assertTrue(books.contains("friends"));
		assertTrue(books.contains("work"));

		// Remove the "work" address book
		bookService.removeAddressBook("work");

		books = bookService.getAllAdressBooks();
		assertEquals(3, books.size());
		assertFalse(books.contains("work"));

		// Remove the "friends" address book
		bookService.removeAddressBook("friends");

		books = bookService.getAllAdressBooks();
		assertEquals(2, books.size());
		assertFalse(books.contains("friends"));

		// Add a new address book
		bookService.createAddressBook("cricket");

		books = bookService.getAllAdressBooks();
		assertEquals(3, books.size());
		assertTrue(books.contains("cricket"));
	}

	/*
	 * Test case to maintain uniqueness
	 */
	@Test
	public void uniqueContactsInAllAddressBooksTest() {
		// let's add some duplicated entries across different address books:
		bookService.addContact(new Contact("Arun", "0450147327"), "family"); // exists in "friends"
		bookService.addContact(new Contact("Swati", "0450147328"), "work"); // exists in "friends"

		assertEquals(1, bookService.getContacts(AddressBookService.DEFAULT_BOOK).size());
		assertEquals(3, bookService.getContacts("family").size());
		assertEquals(3, bookService.getContacts("friends").size());
		assertEquals(2, bookService.getContacts("work").size());

		Set<Contact> allContacts = bookService.getAllContacts();
		assertEquals(7, allContacts.size()); // only 8 unique contacts
		assertTrue(allContacts.contains(new Contact("Nikhita")));
		assertTrue(allContacts.contains(new Contact("Arun")));
		assertTrue(allContacts.contains(new Contact("Swati")));
		assertTrue(allContacts.contains(new Contact("Sachin")));
		assertTrue(allContacts.contains(new Contact("Dad")));
		assertTrue(allContacts.contains(new Contact("Mom")));
		assertTrue(allContacts.contains(new Contact("Police")));
	}

}
