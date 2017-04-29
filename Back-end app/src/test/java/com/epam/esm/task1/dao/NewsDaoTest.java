package com.epam.esm.task1.dao;

import com.epam.esm.task1.entity.News;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

/**
 * Test all of the methods of the NewsDao component.
 *
 * @author Uladzislau Kastsevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-dao-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/datasets/NewsDaoTest.xml")
public class NewsDaoTest {
    private static final Long NON_EXISTING_NEWS_ID = 100L;
    private static final Long TEST_NEWS_ID = 1L;
    private static final String TEST_NEWS_TITLE = "Test title";
    private static final String TEST_NEWS_BRIEF_CONTENT = "Test brief content";
    private static final String TEST_NEWS_FULL_CONTENT = "Test full content";

    @Autowired
    private NewsDao newsDao;

    /**
     * Test the addNews() method of the NewsDao component.
     */
    @Test
    @ExpectedDatabase(value = "/datasets/NewsDaoTest.testAddNews-result.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testAddNews() {
        News testNews = getTestNews();

        newsDao.add(testNews);
    }

    /**
     * Test the updateNews() method of the NewsDao component.
     */
    @Test
    @ExpectedDatabase(value = "/datasets/NewsDaoTest.testUpdateNews-result.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testUpdateNews() {
        News testNews = getTestNews();

        newsDao.update(testNews);
    }

    /**
     * Test the updateNews() method of the NewsDao component.
     */
    @Test
    @ExpectedDatabase(value = "/datasets/NewsDaoTest.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testUpdateNewsWithWrongId() {
        News testNews = getTestNews();
        testNews.setId(NON_EXISTING_NEWS_ID);

        newsDao.update(testNews);
    }

    /**
     * Test the deleteNews() method of the NewsDao component.
     */
    @Test
    @ExpectedDatabase(value = "/datasets/NewsDaoTest.testDeleteNews-result.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testDeleteNews() {
        newsDao.delete(TEST_NEWS_ID);
    }

    /**
     * Test the deleteNews() method of the NewsDao component.
     */
    @Test
    @ExpectedDatabase(value = "/datasets/NewsDaoTest.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testDeleteNewsWithWrongId() {
        newsDao.delete(NON_EXISTING_NEWS_ID);
    }

    /**
     * Test the getNewsById() method of the NewsDao component.
     */
    @Test
    public void testGetNewsById() {
        News expectedNews = new News();
        expectedNews.setId(1L);
        expectedNews.setTitle("ultrices");
        expectedNews.setBriefContent("In hac habitasse platea dictumst. Maecenas ut massa quis augue luctus tincidunt. Nulla mollis molestie lorem. Quisque ut erat. Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.");
        expectedNews.setFullContent("Phasellus sit amet erat. Nulla tempus. Vivamus in felis eu sapien cursus vestibulum. Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum.");
        expectedNews.setCreationTime(LocalDateTime.of(2017, 2, 8, 0, 0, 0));

        News actualNews = newsDao.getById(1L);

        assertEquals(expectedNews, actualNews);
    }

    /**
     * Test the getNewsById() method of the NewsDao component.
     */
    @Test
    public void testGetNewsByWrongId() {
        News actualNews = newsDao.getById(NON_EXISTING_NEWS_ID);

        assertNull(actualNews);
    }

    /**
     * Test the getAllNewsOrderedByCreationTime() method of the NewsDao component.
     */
    @Test
    public void testGetAllNewsOrderedByCreationTime() {
        News firstNews;
        News secondNews;
        ListIterator<News> firstNewsIterator;
        ListIterator<News> secondNewsIterator;

        List<News> newsList = newsDao.getOrderedByCreationTime(true, 0, 20);

        assertEquals(20, newsList.size());

        firstNewsIterator = newsList.listIterator();
        secondNewsIterator = newsList.listIterator(1);
        do {
            firstNews = firstNewsIterator.next();
            secondNews = secondNewsIterator.next();
            assertTrue(firstNews.getCreationTime().compareTo(secondNews.getCreationTime()) >= 0);
        } while (secondNewsIterator.hasNext());
    }

    private News getTestNews() {
        News testNews = new News();
        testNews.setId(TEST_NEWS_ID);
        testNews.setTitle(TEST_NEWS_TITLE);
        testNews.setBriefContent(TEST_NEWS_BRIEF_CONTENT);
        testNews.setFullContent(TEST_NEWS_FULL_CONTENT);
        return testNews;
    }
}
