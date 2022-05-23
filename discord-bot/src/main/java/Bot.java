import java.util.ArrayList;

import org.javacord.api.*;

public class Bot {
	
	public static void main(String[] args) throws Exception {
		HttpURLConnection url = new HttpURLConnection();
		String jsonMeta = url.sendGETMetaInfo();
		
		System.out.println(jsonMeta);
		
		JSONParser parser = new JSONParser(jsonMeta);
		ArrayList<String> names = parser.getNames();
		
		for (String string : names) {
			System.out.println(string);
		}
		
		System.out.println("\n" + parser.getPathByName("расписание 24.05.2022.pdf"));
		
		
		
		
		
		
		
		
		// Initialize bot
		DiscordApi api = new DiscordApiBuilder()
				.setToken("OTc3MTE2NzkwODE3Njg5NjMy.GHH4Mw.aeiV7SkJgKt27fGNVmMrLElifiE5sq1SPNkHoQ")
				.login().join();
		
		// Message listener
		api.addMessageCreateListener(message -> {
			if (message.getMessageContent().equalsIgnoreCase("!table")) {
				message.getChannel().sendMessage("https://disk.yandex.ru/d/0vmvyzN6Yfd43A/%D0%9A%D1%80%D0%B0%D1%81%D0%BD%D0%BE%D1%8F%D1%80%D1%81%D0%BA%D0%B8%D0%B9%20%D1%80%D0%B0%D0%B1%D0%BE%D1%87%D0%B8%D0%B9%20156");
			}
		});
		
		
	}
}