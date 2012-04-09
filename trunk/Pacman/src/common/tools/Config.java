package common.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

public class Config{
	private HashMap<String,String> config = new HashMap<String,String>();
	
	
	
	
	public Config(InputStream file){
		try {	
			BufferedReader reader = new BufferedReader(new InputStreamReader(file));
			String buffer = null;
			
			while( (buffer = reader.readLine()) != null){
				if(!buffer.startsWith("#") && !buffer.isEmpty()){
					String key = buffer.substring(0, buffer.indexOf('='));
					String value = buffer.substring(buffer.indexOf('=')+1);
					config.put(key, value);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String get(String key){
		return config.get(key);
	}
	
	public Boolean getBoolean(String key){
		return new Boolean(config.get(key));
	}
	
	public Float getFloat(String key){
		String val = config.get(key).replace(",", ".");
		return new Float(val);
	}
	
	public int getInteger(String key){
		return new Integer(config.get(key));
	}
	
	public boolean containsKey(String key){
		return config.containsKey(key);
	}
	
	public Set<String> getKeySet(){
		return config.keySet();
	}
	
	public void put(String key , String value){
		config.put(key, value);
	}

}
