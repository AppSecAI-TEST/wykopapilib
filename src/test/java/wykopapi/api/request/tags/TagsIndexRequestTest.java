package wykopapi.api.request.tags;

import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import wykopapi.api.dto.Tag;
import wykopapi.api.extractor.Extractor;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TagsIndexRequestTest {
    private Extractor extractor;
    private TagsIndexRequest request;

    @Before
    public void setUp() throws Exception {
        extractor = new Extractor();
        request = TagsIndexRequest.builder().build();
    }

    @Test
    public void getRequest() throws Exception {
        Map<String, String> parameters = extractor.extract(request.getRequest().body());

        assertEquals("https://a.wykop.pl/tags/index", request.getRequest().url().toString());
        assertEquals(0, parameters.size());
    }

    @Test
    public void getReturnType() throws Exception {
        assertEquals(new TypeToken<List<Tag>>(){}.getType(), request.getReturnType());
    }

}