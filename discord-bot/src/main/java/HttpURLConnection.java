import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpURLConnection {
	String apiUrl = "https://cloud-api.yandex.net/v1/disk/public/resources";
	String publicKey = "public_key=https://disk.yandex.ru/d/0vmvyzN6Yfd43A";
	String limit = "limit=35";
	
	String sendGETMetaInfo() throws Exception {
		String path = "path=/Красноярский%20рабочий%20156";
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(apiUrl + "?" + publicKey + "&" + path + "&" + limit);

		// Add headers
		request.addHeader("Authorization", "OAuth AQAAAAAaCyCpAAfrEARFPPVBd0A5tIusMr8jsg4");
		request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
		
		// Console
		System.out.println(request);
		
		// Get entity
		try (CloseableHttpResponse response = httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String body = EntityUtils.toString(entity);
				return body;
			}
		}

		return null;

	}

	String sendGETDownload(String path) throws Exception {
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(apiUrl + "/download" + "?" + publicKey + "&path=" + path);
		
		// Add headers
		request.addHeader("Authorization", "OAuth AQAAAAAaCyCpAAfrEARFPPVBd0A5tIusMr8jsg4");
		request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
		
		// Console
		System.out.println(request);
		
		// Get entity
		try (CloseableHttpResponse response = httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String body = EntityUtils.toString(entity);
				return body;
			}
		}
		
		return null;
	}

	String sendGETFile(String link) throws Exception {
		final CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(link);
		
		// Add headers
		request.addHeader("Authorization", "OAuth AQAAAAAaCyCpAAfrEARFPPVBd0A5tIusMr8jsg4");
		request.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
		
		// Console
		System.out.println(request);
		
		// Get entity
		try (CloseableHttpResponse response = httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// Console
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					System.out.println(header.getName() + " : " + header.getValue());
				}
				
				// Create file in downloads
				InputStream is = entity.getContent();
				ReadableByteChannel rbc = Channels.newChannel(is);
			    FileOutputStream fos = new FileOutputStream("C:/Users/daun2146/Downloads/timetable.pdf");
			    
			    long filePosition = 0;
		        long transferedBytes = fos.getChannel().transferFrom(rbc, filePosition, Long.MAX_VALUE);

		        while(transferedBytes == Long.MAX_VALUE){
		            filePosition += transferedBytes;
		            transferedBytes = fos.getChannel().transferFrom(rbc, filePosition, Long.MAX_VALUE);
		        }
		        rbc.close();
		        fos.close();
			}
		}
		
		return null;
	}
}