import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {
	final JSONObject json;
	final JSONArray jsonArr;
	
	JSONParser (String jsonStr){
		this.json = new JSONObject(jsonStr);
		jsonArr = json.getJSONObject("_embedded").getJSONArray("items");
	}
	
	public ArrayList<String> getNames () {
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0; i < jsonArr.length(); i++) {
			String name = jsonArr.getJSONObject(i).getString("name");
			list.add(name);
		}
		
		return list;
	}
	
	public String getPathByName (String name) {
		for (int i = 0; i < jsonArr.length(); i++) {
			String n = jsonArr.getJSONObject(i).getString("name");
			if(n.equals(name)) {
				return jsonArr.getJSONObject(i).getString("path");
			}
		}
		
		return null;
	}
}