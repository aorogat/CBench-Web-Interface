/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemstesting;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import qa.dataStructures.Question;

/**
 *
 * @author aorogat
 */
public class TEQUILA {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    //Question has array of answers. each has vars(keywords)
    public static void main(String[] args) throws IOException, JSONException, Exception {
        TEQUILA obj = new TEQUILA();
        //obj.sendPost();

        String format = "%-80s%-70s%n";
        System.out.format(format, "Question\t", "Answer\t");
        System.out.format(format, "======\t", "========\t");

        String question = "?";
        String result="";
        JSONObject json;
     
         
        
        try{

            
            File reader = new File("./data/TempQuestions/TempQuestions.json");
            BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(reader)));
                    
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            result = response.toString();
            json = new JSONObject(result);
            
            String qanswer = "";
            JSONArray questions = json.getJSONArray("questions");
            for (Object ques : questions) {
                JSONObject a = (JSONObject) ques;
                answer(a.getString("Question"));
            }

        } catch (Exception e) {
            System.out.format("inputfile Error");
        }

        
    }

    static void answer(String question) throws IOException, JSONException {
        String ks, as, qs;
        String format = "%-80s%-70s%n";
        JSONObject json;

        String url = "https://gate.d5.mpi-inf.mpg.de/tequila/getAnswers";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        //httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        
        
        String urlParameters = "question=" + question;

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        String result = "";
        int responseCode = httpClient.getResponseCode();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            result = response.toString();
            json = new JSONObject(result);
            String qanswer = "";
            JSONArray answers = json.getJSONArray("tempo_answer");
            for (Object answer : answers) {
                JSONObject a = (JSONObject) answer;
                qanswer += a.getString("name") + ",";
            }
            System.out.format(format, question + "\t", qanswer + "\t");

        } catch (Exception e) {
            System.out.format(format, question + "\t", "-------" + "\t");
        }
    }

}
