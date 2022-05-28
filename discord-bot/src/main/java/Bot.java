import java.util.GregorianCalendar;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.javacord.api.*;
import org.javacord.api.entity.message.MessageBuilder;
import org.json.JSONObject;

public class Bot {
	
	public static void main(String[] args) {   
		// Initialize Bot
		DiscordApi api = new DiscordApiBuilder()
				.setToken("OTc3MTE2NzkwODE3Njg5NjMy.GHH4Mw.aeiV7SkJgKt27fGNVmMrLElifiE5sq1SPNkHoQ")
				.login().join();
		
		// Message listener
		api.addMessageCreateListener(message -> {
			if (message.getMessageContent().equalsIgnoreCase("!table")) {
				String messageText = "";
				try {
					messageText = getSchedule();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				File timetableFile = new File("C:/Users/daun2146/Downloads/timetable.pdf");
				
				MessageBuilder mb = new MessageBuilder();
				mb.append(messageText);
				if (timetableFile.exists()) {
					mb.addAttachment(timetableFile);
				} else {
					mb.append("File wasn't downloaded");
				}
				mb.send(message.getChannel());
			}
		});
	}
	
	private static String getSchedule() throws Exception {
		// GET Request - Meta Information about public resource
		HttpURLConnection url = new HttpURLConnection();
		String jsonMeta = url.sendGETMetaInfo();
		
		if (jsonMeta == null) {
			return "Something went wrong [Meta Request]\n";
		}
		
		JSONParser metaParser = new JSONParser(jsonMeta);
		
		// Find which day we need a timetable
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}

		SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy");
	    String tomorrow = formatForDate.format(calendar.getTime());
		
	    // Get and format path for request
	    String path = metaParser.getPathByName(tomorrow);
	    
		if (path == null) {
			return "Something went wrong [Find Name]\n";
		}
		
	    path = path.replaceAll("\\s", "%20");
	    
	    // GET Request - Ask for download
	    String downloadJsonStr = url.sendGETDownload(path);
	    
		if (downloadJsonStr == null) {
			return "Something went wrong [Download Request]\n";
		}
		
	    JSONObject downloadJson = new JSONObject(downloadJsonStr);
	    String downloadLink = downloadJson.getString("href");
	    
	    // GET Request - Download file
	    url.sendGETFile(downloadLink);
	    
	    return "Timetable for " + tomorrow;
	}
}