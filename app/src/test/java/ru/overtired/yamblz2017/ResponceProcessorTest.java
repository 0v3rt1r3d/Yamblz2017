package ru.overtired.yamblz2017;

import org.junit.Test;

import ru.overtired.yamblz2017.data.AutoCompleteFetcher;
import ru.overtired.yamblz2017.data.ResponseProcesser;
import ru.overtired.yamblz2017.data.Weather;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

public class ResponceProcessorTest {

    public static final String DEFAULT_CITY = "Moscow";

    @Test
    public void weatherResponseNotNull() {
        Weather w = ResponseProcesser.requestWeather(anyString(), DEFAULT_CITY);
        assertNotNull(w);
    }

    @Test
    public void autoCompleteResponceNotNull() {
        AutoCompleteFetcher autoCompleteFetcher = ResponseProcesser.requestAutoComplete();
        assertNotNull(autoCompleteFetcher);
    }
}
