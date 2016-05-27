package json;

import java.io.File;
import java.io.IOException;

import dictionary.Dictionary;
import word.Word;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.KeyDeserializer;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonSerialization extends KeyDeserializer{
	
	public void writeJSON(Dictionary dictionary) throws IOException{
	     ObjectMapper mapper = new ObjectMapper();	
	     mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
	     mapper.writeValue(new File("dictionary.json"), dictionary);
	  }

	public Dictionary readJSON() throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		//mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Dictionary dictionary = mapper.readValue(new File("dictionary.json"), Dictionary.class);
	    return dictionary;
	}

	@Override
	public Object deserializeKey(String name, DeserializationContext arg1) throws IOException, JsonProcessingException {
		
		System.out.println(name);
		
		return new Word(name);
	}
}
