package com.epam.esm.task1.controller;

import com.epam.esm.task1.dto.NewsDto;
import com.epam.esm.task1.service.NewsService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Tests all the methods of the NewsController.
 *
 * @author Uladzislau Kastsevich
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsControllerTest {
    private static final Long TEST_NEWS_ID = 1L;
    private static final String TEST_NEWS_TITLE = "Test title";
    private static final String TEST_NEWS_BRIEF_CONTENT = "Test brief content";
    private static final String TEST_NEWS_FULL_CONTENT = "Test full content";
    private static final NewsDto testNewsDto = new NewsDto();

    @InjectMocks
    private NewsController newsController = new NewsController();

    @Mock
    private NewsService newsServiceMock;

    /**
     * Initializes test beans with the test data.
     */
    @BeforeClass
    public static void initTestBeans() {
        testNewsDto.setId(TEST_NEWS_ID);
        testNewsDto.setTitle(TEST_NEWS_TITLE);
        testNewsDto.setBriefContent(TEST_NEWS_BRIEF_CONTENT);
        testNewsDto.setFullContent(TEST_NEWS_FULL_CONTENT);
    }

    /**
     * Tests the addNews() method of the NewsController.
     */
    @Test
    public void testAddNews() {
        when(newsServiceMock.add(testNewsDto)).thenReturn(testNewsDto);

        ResponseEntity<NewsDto> responseEntity = newsController.addNews(testNewsDto, new MockHttpServletRequest());

        verify(newsServiceMock).add(testNewsDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    /**
     * Tests the updateNews() method of the NewsController.
     */
    @Test
    public void testUpdateNews() {
        ResponseEntity<NewsDto> responseEntity = newsController.updateNews(TEST_NEWS_ID, testNewsDto);

        verify(newsServiceMock).update(testNewsDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * Tests the deleteNews() method of the NewsController.
     */
    @Test
    public void testDeleteNews() {
        ResponseEntity<Void> responseEntity = newsController.deleteNews(TEST_NEWS_ID);

        verify(newsServiceMock).delete(TEST_NEWS_ID);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    /**
     * Tests the getNewsById() method of the NewsController.
     */
    @Test
    public void testGetNewsById() {
        when(newsServiceMock.getById(TEST_NEWS_ID)).thenReturn(testNewsDto);

        ResponseEntity<NewsDto> actualNewsDto = newsController.getNewsById(TEST_NEWS_ID);

        verify(newsServiceMock).getById(TEST_NEWS_ID);
        assertEquals(testNewsDto, actualNewsDto);
    }

    /**
     * Tests the getNews() method of the NewsController.
     */
    @Test
    public void testGetAllNewsOrderedByCreationTime() {
        newsController.getNews(0, 20, null, null);

        verify(newsServiceMock).getOrderedByCreationTime(true, 0 , 20);
    }
}
