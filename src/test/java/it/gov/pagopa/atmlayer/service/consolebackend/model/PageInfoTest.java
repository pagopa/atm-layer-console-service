package it.gov.pagopa.atmlayer.service.consolebackend.model;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class PageInfoTest {

    @Test
    void testConstructorAndGetters() {
        Integer page = 1;
        Integer limit = 10;
        Integer itemsFound = 100;
        Integer totalPages = 10;
        List<String> results = Arrays.asList("result1", "result2", "result3");

        PageInfo<String> pageInfo = new PageInfo<>(page, limit, itemsFound, totalPages, results);

        assertEquals(page, pageInfo.getPage());
        assertEquals(limit, pageInfo.getLimit());
        assertEquals(itemsFound, pageInfo.getItemsFound());
        assertEquals(totalPages, pageInfo.getTotalPages());
        assertEquals(results, pageInfo.getResults());
    }

    @Test
    void testConstructorWithNullResults() {
        Integer page = 1;
        Integer limit = 10;
        Integer itemsFound = 0;
        Integer totalPages = 0;

        PageInfo<String> pageInfo = new PageInfo<>(page, limit, itemsFound, totalPages, null);

        assertEquals(page, pageInfo.getPage());
        assertEquals(limit, pageInfo.getLimit());
        assertEquals(itemsFound, pageInfo.getItemsFound());
        assertEquals(totalPages, pageInfo.getTotalPages());
        assertNull(pageInfo.getResults());
    }
}
