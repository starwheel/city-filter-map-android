package com.backbase.cityfiltermap;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.backbase.cityfiltermap.data.CityStorage;
import com.backbase.cityfiltermap.data.LocalCityStorage;
import com.backbase.cityfiltermap.domain.model.SearchEntity;
import com.backbase.cityfiltermap.domain.usecase.SearchUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SearchUseCaseTest {

    private SearchUseCase useCase;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TreeMap<Character, List<SearchEntity>> map;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Context context = mock(Context.class);

        CurrentThreadExecutor currentThreadExecutor = new CurrentThreadExecutor();

        CityStorage storage = new LocalCityStorage(context);

        useCase = new SearchUseCase(currentThreadExecutor, storage);

        map = new TreeMap<>();

        List<SearchEntity> searchEntities = new ArrayList<>();
        searchEntities.add(new SearchEntity("US", "Alabama"));
        searchEntities.add(new SearchEntity("US", "Albuquerque"));
        searchEntities.add(new SearchEntity("US", "Anaheim"));
        searchEntities.add(new SearchEntity("US", "Arizona"));
        map.put('a', searchEntities);

        List<SearchEntity> searchEntities2 = new ArrayList<>();
        searchEntities2.add(new SearchEntity("AU", "Sydney"));
        map.put('s', searchEntities2);

        storage.setCache(map);
    }


    @Test
    public void searchSuccess() throws InterruptedException {
        assertEquals(1, LiveDataTestUtil.getValue(useCase.getSearchLiveData("s")).size());
        assertEquals("Sydney", LiveDataTestUtil.getValue(useCase.getSearchLiveData("s")).get(0).getName());
        assertEquals(4, LiveDataTestUtil.getValue(useCase.getSearchLiveData("a")).size());
    }

    @Test
    public void searchFail() throws InterruptedException {
        assertEquals(0, LiveDataTestUtil.getValue(useCase.getSearchLiveData("b")).size());    }

    @Test
    public void searchEmptyReturnsNothing() throws InterruptedException {
        assertEquals(0, LiveDataTestUtil.getValue(useCase.getSearchLiveData("")).size());
    }

    @Test
    public void searchNullReturnsNothing() throws InterruptedException {
        assertEquals(0, LiveDataTestUtil.getValue(useCase.getSearchLiveData(null)).size());
    }
}
