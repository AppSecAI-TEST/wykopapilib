package wykopapi.api.extractor;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;

public class MultipartParameterExtractorTest {
    private MultipartParameterExtractor multipartParameterExtractor;

    @Before
    public void setUp() throws Exception {
        multipartParameterExtractor = new MultipartParameterExtractor();
    }

    @Test
    public void extract() throws Exception {
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("formkey", "formvalue")
                .addFormDataPart("formkey2", "formvalue2")
                .addFormDataPart("embed", "filename.jpg", RequestBody.create(MediaType.parse("image/jpeg"), new File("dogpiano.jpg")))
                .build();

        Map<String, String> parameters = multipartParameterExtractor.extract(multipartBody);

        assertEquals(2, parameters.size());
        assertEquals("formvalue", parameters.get("formkey"));
        assertEquals("formvalue2", parameters.get("formkey2"));
    }
}