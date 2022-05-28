import java.util.GregorianCalendar;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.javacord.api.*;
import org.javacord.api.entity.message.MessageBuilder;
import org.json.JSONObject;

public class Bot {
	
	public static void main(String[] args) {   
		// Initialize bot
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
				
				MessageBuilder mb = new MessageBuilder();
				mb.append(messageText)
			    	.addAttachment(new File("C:/Users/daun2146/Downloads/timetable.pdf"))
			    	.send(message.getChannel());
			}
		});
	}
	
	private static String getSchedule() throws Exception {
		// GET meta information request
		HttpURLConnection url = new HttpURLConnection();
		String jsonMeta = url.sendGETMetaInfo();
		
		JSONParser metaParser = new JSONParser(jsonMeta);
		
		// Get tomorrow date
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}

		SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy");
	    String tomorrow = formatForDate.format(calendar.getTime());
		
	    // Find path for tomorrow timetable
	    String path = metaParser.getPathByName(tomorrow);
	    path = path.replaceAll("\\s", "%20");
	    
	    // GET download request
	    String downloadJsonStr = url.sendGETDownload(path);    
	    JSONObject downloadJson = new JSONObject(downloadJsonStr);
	    String downloadLink = downloadJson.getString("href");
	    
	    url.sendGETFile(downloadLink);
	    
	    return "Timetable for " + tomorrow;
	}
}