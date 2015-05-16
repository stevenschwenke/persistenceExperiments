import de.stevenschwenke.java.persistenceExperiments.dao.PersonDAO;
import de.stevenschwenke.java.persistenceExperiments.dao.PersonDAOImpl;
import de.stevenschwenke.java.persistenceExperiments.model.City;
import de.stevenschwenke.java.persistenceExperiments.model.Person;
import org.hibernate.FetchMode;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@Transactional
public class MyTest {

    private Logger logger = LoggerFactory.getLogger(MyTest.class);

    private final int datasetCount = 90000;

    @Autowired
    private PersonDAO personDao;

    /**
     * This test makes sure one instance of a {@link Person} can be saved and retrieved correctly.
     */
    @Test
    public void compositionTest() {
        Person person = new Person("Name", "Surname");
        person.setLocation(new City("Berlin"));
        personDao.save(person);

        Person loadedPerson = personDao.list().get(0);
        assertEquals("Name", loadedPerson.getName().getString());
        assertEquals("Surname", loadedPerson.getSurname().getString());
        assertEquals("Berlin", loadedPerson.getLocation().getName().getString());
    }

    /**
     * Compare different ways of loading data from the database.
     */
    @Test
    public void performanceTest() {

        fillDatabase();

        PersonDAOImpl impl = (PersonDAOImpl) this.personDao;
        Session session = impl.getSessionFactory().getCurrentSession();

        ////////////////////////////////////////////////////////////////////////////
        // !!! Just uncomment the one method under test because of caching !!!
        ////////////////////////////////////////////////////////////////////////////

        // 1. Plain criteria
        // Using this method, the Hibernate configuration is used to determine what gets retrieved from the database.
//        plainCriteria(session); // 5s with 9.000.000 records

        // 2. Criteria with explicit eager-fetching
        // The Hibernate configuration is used and enhanced by statements what should be loaded eager. This method
        // is not refactor-safe because a newly added property would have to be added in each and every query.
//        criteriaWithExplicitFetch(session); // 5s with 9.000.000 recordsC

        // 3. HQL
//        hql(session); // 5s with 9.000.000 records

        // 4. getByID
        singleRequestsWithGet(session); // 15s with 9.000.000 records
    }

    private void plainCriteria(Session session) {
        Instant begin1 = Instant.now();
        List<Person> personsWithCriteria = session.createCriteria(Person.class).list();
        assertEquals(datasetCount, personsWithCriteria.size());
        analyze(personsWithCriteria);
        Instant end1 = Instant.now();
        logger.info("Loaded " + personsWithCriteria.size() + " records with plain criteria in " + Duration.between(begin1, end1).getSeconds() + "s");
    }

    private void criteriaWithExplicitFetch(Session session) {
        Instant begin = Instant.now();
        List<Person> personsWithCriteriaWithExplicitFetch = session.createCriteria(Person.class).setFetchMode("city", FetchMode.SELECT).list();
        analyze(personsWithCriteriaWithExplicitFetch);
        Instant end = Instant.now();
        logger.info("Loaded " + personsWithCriteriaWithExplicitFetch.size() + " records with criteria with explicit Fetch in " + Duration.between(begin, end).getSeconds() + "s");
    }

    private void hql(Session session) {
        Instant begin = Instant.now();
        List<Person> personsWithHQL = session.createQuery("from Person").list();
        analyze(personsWithHQL);
        Instant end = Instant.now();
        logger.info("Loaded " + personsWithHQL.size() + " records with HQL in " + Duration.between(begin, end).getSeconds() + "s");
    }

    private void singleRequestsWithGet(Session session) {
        Instant begin = Instant.now();
        for(int i =1; i< datasetCount+1; i++) {
        Person loadedPerson = (Person) session.get(Person.class, i);
        analyze(loadedPerson);
        }
        Instant end = Instant.now();
        logger.info("Loaded " + datasetCount + " records with single requests in " + Duration.between(begin, end).getSeconds() + "s");
    }

    /**
     * Have a deep look into a List of {@link Person}s to make sure everything about them gets loaded.
     *
     * @param personsWithCriteria
     */
    private void analyze(List<Person> personsWithCriteria) {
        for (Person p : personsWithCriteria) {
            analyze(p);
        }
    }

    private void analyze(Person p) {
        assertNotNull(p.getName());
        assertNotNull(p.getSurname());
        assertNotNull(p.getLocation().getName());
    }

    private void fillDatabase() {
        Instant begin2 = Instant.now();

        for (int counter = 0; counter < datasetCount; counter++) {
            Person p = new Person("Name", "Surname");
            p.setLocation(new City("Berlin"));
            personDao.save(p);
        }
        Instant end2 = Instant.now();
        logger.info("Insert data in " + Duration.between(begin2, end2).getSeconds() + "s");
    }
}
