package com.reece.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reece.model.AddressBook;
import com.reece.model.Contact;

/**
 * {@link AddressBook} class unit tests, using a simple fixture.
 */
public class AddressBookTest
{
	private AddressBook book;

	@Before
	public void before() throws Exception {
		book = new AddressBook("cousins");

		// Add some contacts to the default address book
		book.addContact(new Contact("Bharathy", "0450 70510997"));
		book.addContact(new Contact("Janani",  "0450 70510998"));
		book.addContact(new Contact("Saroja", "0450 70510995"));
	}

	/**
	 * After the execution of test, the fixture is tore down.
	 * @throws Exception
	 */
	@After
	public void teardown() throws Exception {
		book = null;
	}

    /**
     * {@link AddressBook#getContacts()} test.
     */
	@Test
	public void should_getContacts_inBookAddress() {
		assertEquals(3, book.getContacts().size());
	}

    /**
     * {@link AddressBook#addContact(Contact)} test.
     */
	@Test
	public void should_addContact_toBookAddress() {
		book.addContact(new Contact("Nikki", "0450 70510999"));

		assertEquals(4, book.getContacts().size());
		assertTrue(book.getContacts().contains(new Contact("Nikki")));
	}

	@Test
	public void should_removeContact_fromBookAddress() {
		book.removeContact(new Contact("Janani"));

		assertEquals(2, book.getContacts().size());
		assertFalse(book.getContacts().contains(new Contact("Janani")));
	}

}
