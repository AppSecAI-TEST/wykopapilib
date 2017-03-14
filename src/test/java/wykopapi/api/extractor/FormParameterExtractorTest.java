package wykopapi.api.extractor;

import okhttp3.FormBody;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class FormParameterExtractorTest {
    private FormParameterExtractor parameterExtractor;

    @Before
    public void setUp() throws Exception {
        parameterExtractor = new FormParameterExtractor();
    }

    @Test
    public void extract() throws Exception {
        FormBody formBody = new FormBody.Builder()
                .add("key1", "value1")
                .add("key2", "value2")
                .build();

        Map<String, String> parameters = parameterExtractor.extract(formBody);
        assertEquals(2, parameters.size());
        assertEquals("value1", parameters.get("key1"));
        assertEquals("value2", parameters.get("key2"));
    }

    @Test
    public void extractEmpty() throws Exception {
        FormBody formBody = new FormBody.Builder().build();
        Map<String, String> parameters = parameterExtractor.extract(formBody);
        assertEquals(0, parameters.size());
    }

}