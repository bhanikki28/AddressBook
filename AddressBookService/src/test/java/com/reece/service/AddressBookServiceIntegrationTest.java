package com.reece.service;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.reece.model.Contact;
import com.reece.service.impl.AddressBookServiceImpl;

/**
 * Integration tests for the {@link AddressBookServiceImpl} class.
 */
public class AddressBookServiceIntegrationTest
{
    @Test
    public void should_addValidContact_toDefaultAddressBook()
    {
    	AddressBookService service = new AddressBookServiceImpl();
    	service.addContact(new Contact("Nikhita", "0450705109"));

    	final Set<Contact> contacts = service.getAllContacts();

    	Assert.assertEquals(1, contacts.size());
    	Assert.assertTrue(contacts.contains(new Contact("Nikhita")));
    }

	@Test
    public void should_addValidContact_toNamedAddressBook()
    {
    	AddressBookService service = new AddressBookServiceImpl();
    	service.addContact(new Contact("Nikhita", "0450705109"), "friends");

    	final Set<Contact> contacts = service.getAllContacts();

    	Assert.assertEquals(1, contacts.size());
    	Assert.assertTrue(contacts.contains(new Contact("Nikhita")));
    }

	
	@Test
    public void shouldNot_addNullContact_toNamedAddressBook()
    {
		AddressBookServiceImpl service = new AddressBookServiceImpl();

    	try {
			service.addContact(null, "newBook");
			Assert.fail("Should not add a null contact.");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("A contact is mandatory"));
		}

    	// the address book should have zero contacts
    	Assert.assertEquals(0, service.getAllContacts().size());
    	// the address book should have just the default address book
    	Assert.assertEquals(1, service.getAllAdressBooks().size());
    }

	@Test
    public void shouldNot_addInvalidNameContact_toDefaultAddressBook()
    {
		AddressBookServiceImpl service = new AddressBookServiceImpl();

    	try {
			service.addContact(new Contact("", "0450705110"));
			Assert.fail("Should not add a contact with an empty name.");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("Name is mandatory"));
		}

    	Assert.assertEquals(0, service.getAllContacts().size());
    }

	@Test
    public void shouldNot_addNullNameContact_toDefaultAddressBook()
    {
		AddressBookServiceImpl service = new AddressBookServiceImpl();

    	try {
			service.addContact(new Contact(null, "0450705110"));
			Assert.fail("Should not add a contact with a null name.");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("Name is mandatory"));
		}

    	Assert.assertEquals(0, service.getAllContacts().size());
    }

	@Test
    public void shouldNot_addInvalidPhoneContact_toNamedAddressBook()
    {
    	AddressBookService service = new AddressBookServiceImpl();

    	try {
			service.addContact(new Contact("Nikhita", "   "), "friends");
			Assert.fail("Should not allow the addition of a contact with no phone.");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("Phone is mandatory"));
		}

    	// the address book should have zero contacts
    	Assert.assertEquals(0, service.getAllContacts().size());
    	// the address book should have just the default address book
    	Assert.assertEquals(1, service.getAllAdressBooks().size());
    }

	@Test
    public void shouldNot_addNullPhoneContact_toNamedAddressBook()
    {
		AddressBookServiceImpl service = new AddressBookServiceImpl();

    	try {
			service.addContact(new Contact("Nikhita", null), "friends");
			Assert.fail("Should not allow the addition of a contact with no phone.");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("Phone is mandatory"));
		}

    	// the address book should have zero contacts
    	Assert.assertEquals(0, service.getAllContacts().size());
    	// the address book should have just the default address book
    	Assert.assertEquals(1, service.getAllAdressBooks().size());
    }

    /**
     * Users should be able to remove existing contact entries
     */
	@Test
    public void should_removeContactByName_fromDefaultAddressBook()
    {
		// First an entry is added>
		AddressBookServiceImpl service = new AddressBookServiceImpl();
    	service.addContact(new Contact("Nikhita", "0450705109"));

    	Set<Contact> contacts = service.getAllContacts();

    	Assert.assertEquals(1, contacts.size());
    	Assert.assertTrue(contacts.contains(new Contact("Nikhita")));

    	// Remove the existing entry:
    	service.removeContactByName("Nikhita");
    	contacts = service.getAllContacts();

    	Assert.assertEquals(0, contacts.size());
    	Assert.assertFalse(contacts.contains(new Contact("Nikhita")));
    }

    /**
     * Users should be able to remove existing contact entries
     */
	@Test
    public void should_removeValidContact_fromNamedAddressBook()
    {
		// First an entry is added to a new address book
		AddressBookServiceImpl service = new AddressBookServiceImpl();
    	service.addContact(new Contact("Nikhita", "0450705109"), "friends");

    	Set<Contact> contacts = service.getAllContacts();

    	Assert.assertEquals(1, contacts.size());
    	Assert.assertTrue(contacts.contains(new Contact("Nikhita")));

    	// Remove the existing entry from the address book:
    	service.removeContactByName("Nikhita", "friends");
    	contacts = service.getAllContacts();

    	Assert.assertEquals(0, contacts.size());
    	Assert.assertFalse(contacts.contains(new Contact("Nikhita")));
    }

	@Test
    public void should_allowCreation_ofValidAddressBook()
    {
		AddressBookServiceImpl service = new AddressBookServiceImpl();
		Assert.assertEquals(1, service.getAllAdressBooks().size());
		Assert.assertTrue(service.getAllAdressBooks().contains(AddressBookService.DEFAULT_BOOK));

		service.createAddressBook("1");
		service.createAddressBook("Family");
		service.createAddressBook("!@#$$%^");

		Assert.assertEquals(4, service.getAllAdressBooks().size());
		Assert.assertTrue(service.getAllAdressBooks().contains("1"));
		Assert.assertTrue(service.getAllAdressBooks().contains("Family"));
		Assert.assertTrue(service.getAllAdressBooks().contains("!@#$$%^"));
    }

	@Test
    public void shouldNot_allowCreation_ofInvalidAddressBook()
    {
		AddressBookService service = new AddressBookServiceImpl();

		try {
			service.createAddressBook("   ");
			Assert.fail("Should not add a contact with an empty name.");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("Name is mandatory"));
		}
    }

	@Test
    public void shouldNot_allowCreation_ofNullAddressBook()
    {
		AddressBookServiceImpl service = new AddressBookServiceImpl();

		try {
			service.createAddressBook(null);
			Assert.fail("Should not add a contact with a null name.");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("Name is mandatory"));
		}
    }

	@Test
    public void should_allowAddContacts_toMultipleAddressBooks()
    {
		// A new service is created, with contacts in 2 address books:
		AddressBookServiceImpl service = new AddressBookServiceImpl();
		service.addContact(new Contact("Dad",      "0429147328"), "family");
		service.addContact(new Contact("Mom",      "0429147329"), "family");
    	service.addContact(new Contact("Nikhita",   "0450147326"), "friends");
    	service.addContact(new Contact("Arun",    "0450147327"), "friends");
    	service.addContact(new Contact("Swati", "0450147328"), "friends");

    	// Assert number of address books (default, family, friends)
    	Assert.assertEquals(3, service.getAllAdressBooks().size());

    	// Assert contacts per address book
    	Assert.assertEquals(0, service.getContacts("default").size());
    	Assert.assertEquals(2, service.getContacts("family").size());
    	Assert.assertEquals(3, service.getContacts("friends").size());
    	Assert.assertEquals(5, service.getAllContacts().size());
    }

	@Test
    public void should_allowRemoval_ofCompleteAddressBooks()
    {
		// A new service is created, with contacts in 2 address books:
		AddressBookServiceImpl service = new AddressBookServiceImpl();
		service.addContact(new Contact("Dad",      "0429147328"), "family");
		service.addContact(new Contact("Mom",      "0429147329"), "family");
    	service.addContact(new Contact("Nikhita",   "0450147326"), "friends");
    	service.addContact(new Contact("Arun",    "0450147327"), "friends");
    	service.addContact(new Contact("Swati", "0450147328"), "friends");

    	// Remove an address book
    	service.removeAddressBook("family");

    	Assert.assertEquals(2, service.getAllAdressBooks().size());
    	Assert.assertEquals(3, service.getAllContacts().size());
    }

	@Test
    public void should_shouldKeep_UniqueContacts()
    {
		// A new service is created, with contacts in 2 address books:
		AddressBookServiceImpl service = new AddressBookServiceImpl();
		service.addContact(new Contact("Dad",      "0429147328"), "family");
		service.addContact(new Contact("Mom",      "0429147329"), "family");
    	service.addContact(new Contact("Nikhita",   "0450147326"), "friends");
    	service.addContact(new Contact("Arun",    "0450147327"), "friends");
    	service.addContact(new Contact("Swati", "0450147328"), "friends");

		// Add a contact to all address books, to check contact uniqueness
		// across address books
    	service.addContact(new Contact("Sachin", "0429147370")); // added to the default address book
    	service.addContact(new Contact("Sachin", "0429147370"), "friends");
    	service.addContact(new Contact("Sachin", "0429147370"), "family");
    	service.addContact(new Contact("Sachin", "0429147370"), "work"); // contact added to new address book

    	// Assert contacts per address book
    	Assert.assertEquals(1, service.getContacts("default").size());
    	Assert.assertEquals(3, service.getContacts("family").size());
    	Assert.assertEquals(4, service.getContacts("friends").size());
    	Assert.assertEquals(1, service.getContacts("work").size());
    	Assert.assertEquals(6, service.getAllContacts().size());
    }

}
