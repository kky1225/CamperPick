package kr.spring.iamport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Iamport {
	public String getToken() throws ParseException {
		
		RestTemplate restTemplate = new RestTemplate();
	
		//서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	    
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("imp_key", "0875331357109726");
		map.put("imp_secret", "63db5cbb2ce0ee5a66b6e4480cfb34a2487487b3a529322e7d9d8fa484907301a269eaacdb631b23");
	    
		Gson var = new Gson();
		String json=var.toJson(map);
		//서버로 요청할 Body
	   
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		
		String token = restTemplate.postForObject("https://api.iamport.kr/users/getToken", entity, String.class);
		
		token = token.substring(token.indexOf("response") + 10);
		token = token.substring(0, token.length() - 1);
		
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject)parser.parse(token);
		
		token = (String) obj.get("access_token");
		
		System.out.println(token);
		
		return token;
		
		
	}
	
	public int cancelPayment(String token, String merchant_uid) {
		
		HttpClient client = HttpClientBuilder.create().build(); 
		HttpPost post = new HttpPost("https://api.iamport.kr/payments/cancel");
		Map<String, String> map = new HashMap<String, String>(); 
		post.setHeader("Authorization", token); 
		map.put("merchant_uid", merchant_uid);
		String asd = ""; 
		try { 
			post.setEntity(new UrlEncodedFormEntity(convertParameter(map))); 
			HttpResponse res = client.execute(post); 
			ObjectMapper mapper = new ObjectMapper(); 
			String enty = EntityUtils.toString(res.getEntity()); 
			
			JsonNode rootNode = mapper.readTree(enty); 
			asd = rootNode.get("response").asText(); 
			
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		
		if (asd == null) { 
			System.err.println("환불실패"); 
			return -1; 
		} else { 
			System.err.println("환불성공"); 
			return 1; 
		} 
		
	}

	private List<? extends NameValuePair> convertParameter(Map<String, String> paramMap) {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>(); 
		Set<Entry<String,String>> entries = paramMap.entrySet(); 
		
		for(Entry<String,String> entry : entries) {
			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		} 
		
		return paramList;
	}

}
