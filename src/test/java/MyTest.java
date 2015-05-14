import de.stevenschwenke.java.persistenceExperiments.dao.PersonDAO;
import de.stevenschwenke.java.persistenceExperiments.dao.PersonDAOImpl;
import de.stevenschwenke.java.persistenceExperiments.model.Name;
import de.stevenschwenke.java.persistenceExperiments.model.Person;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@Transactional
public class MyTest {

    private Logger logger = LoggerFactory.getLogger(MyTest.class);

    private final int datasetCount = 10000;

    @Autowired
    private PersonDAO personDao;

    @Test
    public void compositionTest() {
        Person person = new Person("Name", "Surname");
        personDao.save(person);

        Person loadedPerson = personDao.list().get(0);
        assertEquals("Name", loadedPerson.getName().getString());
        assertEquals("Surname", loadedPerson.getSurname().getString());
    }

    @Test
    public void performanceTest() {

        fillDatabase();

        PersonDAOImpl impl = (PersonDAOImpl) this.personDao;
        Session session = impl.getSessionFactory().getCurrentSession();

        Instant begin2 = Instant.now();
        List personsWithCriteria = session.createCriteria(Person.class).list();
        assertEquals(datasetCount, personsWithCriteria.size());
        Instant end2 = Instant.now();

        logger.info("Load with plain criteria in " + Duration.between(begin2, end2).getSeconds() + "s");

//        session.createCriteria(Person.class).setFetchMode("teilprojekte", FetchMode.SELECT).setFetchMode("kunden", FetchMode.SELECT).list();
    }

    private void fillDatabase() {
        Instant begin2 = Instant.now();

        for (int counter = 0; counter < datasetCount; counter++) {
            Person p = new Person("Name", "Surname");
            personDao.save(p);
        }
        Instant end2 = Instant.now();
        logger.info("Insert data in " + Duration.between(begin2, end2).getSeconds() + "s");
    }
}
