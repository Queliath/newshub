package com.epam.esm.task1.service;

import com.epam.esm.task1.dao.NewsDao;
import org.springframework.dao.DataAccessException;
import com.epam.esm.task1.dto.converter.DtoConverter;
import com.epam.esm.task1.dto.validator.DtoValidator;
import com.epam.esm.task1.entity.News;
import com.epam.esm.task1.dto.NewsDto;
import com.epam.esm.task1.service.exception.EntityNotFoundException;
import com.epam.esm.task1.dto.validator.exception.DtoValidationException;
import com.epam.esm.task1.service.impl.NewsServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Tests all of the methods of the NewsService component.
 *
 * @author Uladzislau Kastsevich
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {
    private static final Long TEST_NEWS_ID = 1L;
    private static final String TEST_NEWS_TITLE = "Test title";
    private static final String TEST_NEWS_BRIEF_CONTENT = "Test brief content";
    private static final String TEST_NEWS_FULL_CONTENT = "Test full content";
    private static final News testNews = new News();
    private static final NewsDto testNewsDto = new NewsDto();

    @InjectMocks
    private NewsService newsService = new NewsServiceImpl();

    @Mock
    private NewsDao newsDaoMock;
    @Mock
    private DtoConverter<News, NewsDto> newsDtoConverterMock;
    @Mock
    private DtoValidator dtoValidatorMock;

    /**
     * Initializes test beans with the test data.
     */
    @BeforeClass
    public static void initBeforeClass() {
        testNews.setId(TEST_NEWS_ID);
        testNews.setTitle(TEST_NEWS_TITLE);
        testNews.setBriefContent(TEST_NEWS_BRIEF_CONTENT);
        testNews.setFullContent(TEST_NEWS_FULL_CONTENT);

        testNewsDto.setId(TEST_NEWS_ID);
        testNewsDto.setTitle(TEST_NEWS_TITLE);
        testNewsDto.setBriefContent(TEST_NEWS_BRIEF_CONTENT);
        testNewsDto.setFullContent(TEST_NEWS_FULL_CONTENT);
    }

    /**
     * Defines a behavior of some mocks.
     */
    @Before
    public void initBefore() {
        when(newsDtoConverterMock.getDto(testNews)).thenReturn(testNewsDto);
        when(newsDtoConverterMock.getEntity(testNewsDto)).thenReturn(testNews);
    }

    /**
     * Tests the addNews() method of the NewsService component.
     */
    @Test
    public void testAddNews() {
        when(newsDaoMock.add(testNews)).thenReturn(testNews);

        NewsDto actualNewsDto = newsService.add(testNewsDto);

        verify(newsDaoMock).add(testNews);
        assertEquals(testNewsDto, actualNewsDto);
    }

    /**
     * Tests the addNews() method of the NewsService component.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewsWithNullArg() {
        newsService.add(null);
    }

    /**
     * Tests the addNews() method of the NewsService component.
     */
    @Test(expected = DtoValidationException.class)
    public void testAddNotValidNews() {
        doThrow(DtoValidationException.class).when(dtoValidatorMock).validate(testNewsDto);

        newsService.add(testNewsDto);
    }

    /**
     * Tests the addNews() method of the NewsService component.
     */
    @Test(expected = DataAccessException.class)
    public void testAddNewsWhileDataAccessProblems() {
        when(newsDaoMock.add(testNews)).thenThrow(DataAccessException.class);

        newsService.add(testNewsDto);
    }

    /**
     * Tests the updateNews() method of the NewsService component.
     */
    @Test
    public void testUpdateNews() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(testNews);
        when(newsDaoMock.update(testNews)).thenReturn(testNews);

        NewsDto actualNewsDto = newsService.update(testNewsDto);

        verify(newsDaoMock).update(testNews);
        assertEquals(testNewsDto, actualNewsDto);
    }

    /**
     * Tests the updateNews() method of the NewsService component.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNewsWithNullArg() {
        newsService.update(null);
    }

    /**
     * Tests the updateNews() method of the NewsService component.
     */
    @Test(expected = DtoValidationException.class)
    public void testUpdateNotValidNews() {
        doThrow(DtoValidationException.class).when(dtoValidatorMock).validate(testNewsDto);

        newsService.update(testNewsDto);
    }

    /**
     * Tests the updateNews() method of the NewsService component.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testUpdateNewsWithWrongId() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(null);

        newsService.update(testNewsDto);
    }

    /**
     * Tests the updateNews() method of the NewsService component.
     */
    @Test(expected = DataAccessException.class)
    public void testUpdateNewsWhileDataAccessProblems() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(testNews);
        when(newsDaoMock.update(testNews)).thenThrow(DataAccessException.class);

        newsService.update(testNewsDto);
    }

    /**
     * Tests the deleteNews() method of the NewsService component.
     */
    @Test
    public void testDeleteNews() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(testNews);

        newsService.delete(TEST_NEWS_ID);

        verify(newsDaoMock).delete(TEST_NEWS_ID);
    }

    /**
     * Tests the deleteNews() method of the NewsService component.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNewsWithNullArg() {
        newsService.delete(null);
    }

    /**
     * Tests the deleteNews() method of the NewsService component.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNewsWithWrongId() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(null);

        newsService.delete(TEST_NEWS_ID);
    }

    /**
     * Tests the deleteNews() method of the NewsService component.
     */
    @Test(expected = DataAccessException.class)
    public void testDeleteNewsWhileDataAccessProblems() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(testNews);
        doThrow(DataAccessException.class).when(newsDaoMock).delete(TEST_NEWS_ID);

        newsService.delete(TEST_NEWS_ID);
    }

    /**
     * Tests the getNewsById() method of the NewsService component.
     */
    @Test
    public void testGetNewsById() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(testNews);

        NewsDto actualNewsDto = newsService.getById(TEST_NEWS_ID);

        verify(newsDaoMock).getById(TEST_NEWS_ID);
        assertEquals(testNewsDto, actualNewsDto);
    }

    /**
     * Tests the getNewsById() method of the NewsService component.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetNewsByIdWithNullArg() {
        newsService.getById(null);
    }

    /**
     * Tests the getNewsById() method of the NewsService component.
     */
    @Test(expected = EntityNotFoundException.class)
    public void testGetNewsByWrongId() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenReturn(null);

        newsService.getById(TEST_NEWS_ID);
    }

    /**
     * Tests the getNewsById() method of the NewsService component.
     */
    @Test(expected = DataAccessException.class)
    public void testGetNewsByIdWhileDataAccessProblems() {
        when(newsDaoMock.getById(TEST_NEWS_ID)).thenThrow(DataAccessException.class);

        newsService.getById(TEST_NEWS_ID);
    }

    /**
     * Tests the getAllNewsOrderedByCreationTime() method of the NewsService component.
     */
    @Test
    public void testGetAllNewsOrderedByCreationTime() {
        newsService.getOrderedByCreationTime(true, 0 , 20);

        verify(newsDaoMock).getOrderedByCreationTime(true, 0 , 20);
    }

    /**
     * Tests the getAllNewsOrderedByCreationTime() method of the NewsService component.
     */
    @Test(expected = DataAccessException.class)
    public void testGetAllNewsOrderedByCreationTimeWhileDataAccessProblems() {
        when(newsDaoMock.getOrderedByCreationTime(true, 0 , 20)).thenThrow(DataAccessException.class);

        newsService.getOrderedByCreationTime(true, 0, 20);
    }
}
