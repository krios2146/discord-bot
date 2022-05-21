import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpURLConnection {
	
     String sendGetMetaInfo() throws Exception {
    	final CloseableHttpClient httpClient = HttpClients.createDefault();
    	
        HttpGet request = new HttpGet("https://cloud-api.yandex.net/v1/disk/public/resources"
				+ "?public_key=https://disk.yandex.ru/d/0vmvyzN6Yfd43A"
				+ "&path=/Красноярский%20рабочий%20156");

        // add headers
        request.addHeader("Authorization", "OAuth AQAAAAAaCyCpAAfrEARFPPVBd0A5tIusMr8jsg4");
        request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
        		+ "AppleWebKit/537.36 (KHTML, like Gecko) "
        		+ "Chrome/86.0.4240.75 Safari/537.36");
        
        // get entity
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
            
        }
		return null;
        
    }
    
}