package facades;

import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import dto.*;
import entities.Address;
import java.util.Date;
import exceptions.*;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone, String street, String zip, String city) throws MissingInputException {
        EntityManager em = getEntityManager();
        if (fName.isEmpty() || lName.isEmpty()) {
            throw new MissingInputException("First name and/or Last name is missing");
        }
        Person person = new Person(fName, lName, phone, new Date(), new Date());
        Address address = new Address(street, zip, city);
        person.setAddress(address);
        PersonDTO personDTO = new PersonDTO(person);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return personDTO;
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new PersonNotFoundException("Could not delete, provided id does not exist");
        }
        PersonDTO personDTO = new PersonDTO(person);
        try {
            em.getTransaction().begin();
            em.remove(person);
            em.remove(person.getAddress());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return personDTO;
    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Person with id not found");
            }
            PersonDTO pDTO = new PersonDTO(person);
            return pDTO;
        } finally {
            em.close();
        }

    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            List<Person> personList = em.createNamedQuery("Person.getAll").getResultList();
            PersonsDTO allPersons = new PersonsDTO(personList);
            return allPersons;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, p.getId());
        if (person == null) {
            throw new PersonNotFoundException("Could not edit person, id does not match any in database");
        }
        if (p.getfName().isEmpty() || p.getlName().isEmpty()) {
            throw new MissingInputException("First name and/or Last name is missing");
        }
        
        Address adr = new Address(p.getStreet(), p.getZip(), p.getCity());
        person.setLastEdited();
        person.setFirstName(p.getfName());
        person.setLastName(p.getlName());
        person.setPhone(p.getPhone());
        person.setAddress(adr);
        
        
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            em.clear();
        }
        return new PersonDTO(person);
    }

//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
//        EntityManager em = emf.createEntityManager();
//        Person p1 = new Person("Mads", "Kristensen", "1234", new Date(), new Date());
//        Address a1 = new Address("Østerbrogade", "100", "Kbh");
//        p1.setAddress(a1);
//        em.getTransaction().begin();
//        em.persist(p1);
//        em.getTransaction().commit();
//    }

}
