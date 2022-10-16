package telran.citizens.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.citizens.dao.CitizensImpl;
import telran.citizens.interfaces.Citizens;
import telran.citizens.model.Person;

class CitizensTest {
	Citizens citizens;

	@BeforeEach
	void setUp() throws Exception {
//		citizens = new CitizensImpl(new Person(1, "Peter", "Jackson", LocalDate.of(2010, 7, 03)),
//				new Person(2, "John", "Smith", LocalDate.of(1980, 2, 14)),
//				new Person(3, "Mary", "Jackson", LocalDate.of(2005, 12, 22)),
//				new Person(4, "Tigran", "Petrosian", LocalDate.of(1990, 9, 01)))); //
//	}
	citizens = new CitizensImpl( new Person(1, "Peter", "Jackson", LocalDate.of(2010, 7, 03)),
			new Person(2, "John", "Smith", LocalDate.of(1980, 2, 14)),
			new Person(3, "Mary", "Jackson", LocalDate.of(2005, 12, 22)),
			new Person(4, "Tigran", "Petrosian", LocalDate.of(1990, 9, 01))); //
}

	@Test
	void testCitizensImplListOfPerson() {
		citizens = new CitizensImpl(List.of(new Person(1, "Peter", "Jackson", LocalDate.of(2010, 7, 03)),
				new Person(1, "Peter", "Jackson", LocalDate.of(2010, 7, 03)))); //
		assertEquals(1, citizens.size());
	}

	@Test
	void testAdd() {
		assertFalse(citizens.add(null));
		assertFalse(citizens.add(new Person(2, "John", "Smith", LocalDate.of(1999, 1, 03)))); //
		assertEquals(4, citizens.size());
		assertTrue(citizens.add(new Person(5, "John", "Smith", LocalDate.of(2002, 5, 23)))); //
		assertEquals(5, citizens.size());
	}

	@Test
	void testRemove() {
		assertFalse(citizens.remove(5));
		assertEquals(4, citizens.size());
		assertTrue(citizens.remove(2));
		assertEquals(3, citizens.size());
	}

	@Test
	void testFindInt() {
		Person person = citizens.find(1);
		assertEquals(1, person.getId());
		assertEquals("Peter", person.getFirstName());
		assertEquals("Jackson", person.getLastName());
		assertEquals(LocalDate.of(2010, 7, 03), person.getAge());// ?????
		assertNull(citizens.find(5));
	}

	@Test
	void testFindIntInt() {
		Iterable<Person> temp = citizens.find(13, 32);
		Iterable<Person> expected = Arrays.asList(new Person(3, "Mary", "Jackson", LocalDate.of(2005, 12, 22)),
				new Person(4, "Tigran", "Petrosian", LocalDate.of(1990, 9, 01))); //
		ArrayList<Person> actual = new ArrayList<Person>();
		temp.forEach(p -> actual.add(p));
		Collections.sort(actual);
		assertIterableEquals(expected, actual);
	}

	@Test
	void testFindString() {
		Iterable<Person> temp = citizens.find("Jackson");
		Iterable<Person> expected = Arrays.asList(new Person(1, "Peter", "Jackson", LocalDate.of(2010, 7, 03)), //
				new Person(3, "Mary", "Jackson", LocalDate.of(2005, 12, 22))); //
		ArrayList<Person> actual = new ArrayList<Person>();
		temp.forEach(p -> actual.add(p));
		Collections.sort(actual);
		assertIterableEquals(expected, actual);
	}

	@Test
	void testGetAllPersonSortedById() {
		Iterable<Person> res = citizens.getAllPersonSortedById();
		int id = 0;
		for (Person person : res) {
			assertTrue(person.getId() > id);
			id = person.getId();
		}
	}

	@Test
	void testGetAllPersonSortedByLastName() {
		Iterable<Person> res = citizens.getAllPersonSortedByLastName();
		String lastName = "";
		for (Person person : res) {
			assertTrue(person.getLastName().compareTo(lastName) >= 0);
			lastName = person.getLastName();
		}
	}

	@Test
	void testGetAllPersonSortedByAge() {

		Iterable<Person> res = citizens.getAllPersonSortedByAge();
		Integer age = null;
		for (Person person : res) {
			if (age != null) {
				assertTrue(person.getAge().getYear() >= age); 
			}
			age = person.getAge().getYear(); 
		}
	}

	@Test
	void testSize() {
		assertEquals(4, citizens.size());
	}

}
