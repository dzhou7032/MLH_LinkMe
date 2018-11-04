import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import clarifai2.api.*;
import clarifai2.dto.*;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class MLbot {
	final static ClarifaiClient client = new ClarifaiBuilder("9209586b9f8741deacb9e64dca8daf09").buildSync();
	public static void main(String[] args) throws IOException{
		List data = getData("https://img1.svg.com/img/gallery/the-untold-truth-of-tyler1/intro-1527099715.jpg");
		

	    String key="AIzaSyDaEUkcG4E4C739pqhUdlkeX7Rempy1Rh4";
	    String qry="Tyler1";
	    URL url = new URL(
	            "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    BufferedReader br = new BufferedReader(new InputStreamReader(
	            (conn.getInputStream())));

	    String output;
	    System.out.println("Output from Server .... \n");
	    while ((output = br.readLine()) != null) {

	        if(output.contains("\"link\": \"")){                
	            String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
	            System.out.println(link);       //Will print the google search links
	        }     
	    }
	    conn.disconnect();            
	}
	
	public static List<String> getData(String url){
		Model<Concept> generalModel = client.getDefaultModels().generalModel();

		PredictRequest<Concept> request = generalModel.predict().withInputs(
		        ClarifaiInput.forImage(url)
		    );
		List<ClarifaiOutput<Concept>> result = request.executeSync().get();
		//System.out.println(result.get(0).data());
		List<String> names = new ArrayList<String>();
		for(Concept c:result.get(0).data()){
			names.add(c.name());
			System.out.println(c.name());
			System.out.println(c.value());
		}
		return names;
	}
	
	public List<String> search(String url2) throws IOException{

		List<String> store = new ArrayList<String>();

	    String key="AIzaSyDaEUkcG4E4C739pqhUdlkeX7Rempy1Rh4";
	    String qry= url2;
	    URL url = new URL(
	            "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    BufferedReader br = new BufferedReader(new InputStreamReader(
	            (conn.getInputStream())));

	    String output;
	    System.out.println("Output from Server .... \n");
	    while ((output = br.readLine()) != null) {

	        if(output.contains("\"link\": \"")){                
	            String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
	            store.add(link);
	            System.out.println(link);       //Will print the google search links
	        }     
	    }
	    conn.disconnect();        																
	    return store;
	}
	
	
	
}
