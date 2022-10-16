package telran.citizens.dao;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import telran.citizens.interfaces.Citizens;
import telran.citizens.model.Person;

public class CitizensImpl implements Citizens {
	private List<Person> idList;
	private List<Person> lastNameList;
	private List<Person> ageList;
	private static Comparator<Person> lastNameComparator;
	private static Comparator<Person> ageComparator;

	static {
		lastNameComparator = (p1, p2) -> {
			int res = p1.getLastName().compareTo(p2.getLastName());
			return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
		};
		ageComparator = (p1, p2) -> {
			int res = Integer.compare(fromLocalDateToInt(p1.getAge()), fromLocalDateToInt(p2.getAge()));
			return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
		};

	}// analog constructora dlia statik poley

	public CitizensImpl() {
		idList = new ArrayList<>();
		lastNameList = new ArrayList<>();
		ageList = new ArrayList<>();

	}

	public CitizensImpl(List<Person> citizens) {
		this();// vizov bez argumenta k konstructoru
		for (Person person : citizens) {
			add(person);

		}
	}
		// citizens.forEach(p->add(p));
		
		
		public CitizensImpl( Person... citizens) {//ostatotnix 
			this();// vizov bez argumenta k konstructoru
			for (Person person : citizens) {
				add(person);

			}
	}

	private static int fromLocalDateToInt(LocalDate age) {			//
		return Period.between(LocalDate.now(), age).getYears();      //
	}

	// O(1)
	@Override
	public boolean add(Person person) {
		if (person == null) {
			return false;
		}
		// find ili contains
		int index = Collections.binarySearch(idList, person);
		if (index >= 0) {
			return false;
		}
		index = -index - 1;
		idList.add(index, person);
		index = Collections.binarySearch(lastNameList, person, lastNameComparator);
		index = index >= 0 ? index : -index - 1;
		lastNameList.add(index, person);
		index = Collections.binarySearch(ageList, person, ageComparator);
		index = index >= 0 ? index : -index - 1;
		ageList.add(index, person);
		return true;
	}

	// 0(n)
	@Override
	public boolean remove(int id) {
		Person victim = new Person(id, null, null, LocalDate.now());                            //                                        
		return idList.remove(victim) && lastNameList.remove(victim) && ageList.remove(victim);
	}

	// 0(Log(n))
	@Override
	public Person find(int id) {
		Person pattern = new Person(id, null, null, LocalDate.now());						//
		int index = Collections.binarySearch(idList, pattern);
		return index < 0 ? null : idList.get(index);
	}

	// O(log(n))
	@Override
	public Iterable<Person> find(int minAge, int maxAge) {
		Person pattern = new Person(Integer.MIN_VALUE, null, null,
				LocalDate.of(LocalDate.now().getYear() - maxAge, 1, 1)); //
		int from = -Collections.binarySearch(ageList, pattern, ageComparator) - 1;
		pattern = new Person(Integer.MAX_VALUE, null, null, LocalDate.of(LocalDate.now().getYear() - minAge, 1, 1)); //
		int to = -Collections.binarySearch(ageList, pattern, ageComparator) - 1;
		return ageList.subList(from, to);
	}

	// O(log(n))
	@Override
	public Iterable<Person> find(String lastName) {
		Person pattern = new Person(Integer.MIN_VALUE, null, lastName, LocalDate.now()); //
		int from = -Collections.binarySearch(lastNameList, pattern, lastNameComparator) - 1;
		pattern = new Person(Integer.MAX_VALUE, null, lastName, LocalDate.now()); //
		int to = -Collections.binarySearch(lastNameList, pattern, lastNameComparator) - 1;
		return lastNameList.subList(from, to);
	}

//O(1)
	@Override
	public Iterable<Person> getAllPersonSortedById() {
		return idList;
	}

	// O(1)
	@Override
	public Iterable<Person> getAllPersonSortedByLastName() {
		return lastNameList;
	}

	// O(1)
	@Override
	public Iterable<Person> getAllPersonSortedByAge() {
		return ageList;
	}

	// O(1)
	@Override
	public int size() {
		return idList.size();
	}

}
