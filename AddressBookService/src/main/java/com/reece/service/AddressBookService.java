package com.reece.service;

import java.util.Set;

import com.reece.model.AddressBook;
import com.reece.model.Contact;

public interface AddressBookService {

	/**
	 * Name of the default address book.
	 */
	String DEFAULT_BOOK = "default";

	/**
	 * @return All address books currently being managed
	 */
	Set<String> getAllAdressBooks();

	/**
	 * Creates a new address book with the name received as parameter
	 * 
	 * @param name
	 *            name for the new address book
	 * @return the newly created address book
	 */
	AddressBook createAddressBook(String name);

	/**
	 * Removes an existing address book, along with all its contacts
	 * 
	 * @param name
	 *            name of the address book to be removed
	 */
	void removeAddressBook(String name);

	/**
	 * Adds a new contact to the default address book
	 * 
	 * @param contact
	 *            contact object with valid name and phone
	 * @return the newly created contact
	 */
	Contact addContact(Contact contact);

	/**
	 * Adds a new contact to the address book specified. If addressBook is null,
	 * adds it the default address book
	 * 
	 * @param contact
	 *            contact object with valid name and phone
	 * @param addressBook
	 *            address book to which the contact will be added
	 * @return the newly created contact
	 */
	Contact addContact(Contact contact, String addressBook);

	/**
	 * Removes a contact from the default address book given its name.
	 * 
	 * @param name
	 *            name of contact to be removed
	 */
	void removeContactByName(String name);

	/**
	 * Removes a contact from the default address book given its name
	 * 
	 * @param name
	 *            name of contact to be removed
	 * @param addressBook
	 *            name of address book from which the contact will be removed
	 */
	void removeContactByName(String name, String addressBook);

	/**
	 * @param addressBook
	 * @return All contacts in the address book with the received name.
	 */
	Set<Contact> getContacts(String addressBook);

	/**
	 * @return Contacts across all address books.
	 */
	Set<Contact> getAllContacts();

	/**
	 * Sends to system out all contacts in the specified address book
	 * @param addressBook name of the address book
	 */
	void printContacts(String addressBook);

	/**
	 * Sends to system out contacts in all address books
	 */
	void printAllContacts();

}