package model;

import exception.DataServerConnectionException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.BasicConfigurator;
import org.junit.*;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


/**
 * @author ArtSCactus
 * @version 1.0
 */
public class ModelTest extends Mockito {
    private static final String DATA_SERVER_HOST = "http://localhost:8080";
    private static final String CITIES_LIST_JSON = "[{" +
            "\"name\":Minsk," +
            "\"description\":Description}," +
            "{" +
            "\"name\":Vitebsk," +
            "\"description\":Description" +
            "}]";
    private static final String TEST_CITY_DESCRIPTION = "Description";
    private CloseableHttpClient client;
    private Model model;

    @BeforeClass
    public static void globalInit() {
        BasicConfigurator.configure();
    }

    @Before
    public void init() {
        client = mock(CloseableHttpClient.class);
        model = new Model(DATA_SERVER_HOST, client);
    }
    @After
    public void closeClient() throws IOException {
        client.close();
        model.close();
    }


    @Test
    public void should_return_json_from_http_response_if_everything_ok() throws IOException {

        HttpGet get = mock(HttpGet.class);
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        InputStream stream = new ByteArrayInputStream(CITIES_LIST_JSON.getBytes());
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(stream);
        stream.close();
        response.setEntity(entity);

        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(get.getURI()).thenReturn(URI.create(DATA_SERVER_HOST + "/city/all"));
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(get)).thenReturn(response);

        String json = model.executeRequestAndExcludeJson(get);
        Assert.assertEquals(CITIES_LIST_JSON, json);
    }

    @Test(expected = DataServerConnectionException.class)
    public void should_throw_exception_if_data_server_is_not_respond() throws IOException {
        HttpGet get = mock(HttpGet.class);
        when(client.execute(get)).thenThrow(IOException.class);
        model.executeRequestAndExcludeJson(get);

    }

    @Test
    public void should_return_correct_city_description() throws IOException {
        HttpGet get = mock(HttpGet.class);
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        InputStream stream = new ByteArrayInputStream(TEST_CITY_DESCRIPTION.getBytes());
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(stream);
        stream.close();
        response.setStatusLine(statusLine);
        response.setEntity(entity);

        when(client.execute(get)).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);

        String methodResult = model.requestCityDescription(get);
        Assert.assertEquals(TEST_CITY_DESCRIPTION, methodResult);

    }

}
