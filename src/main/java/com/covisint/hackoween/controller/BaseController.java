/* Copyright (C) 2015 Covisint.
        All Rights Reserved. */

        package com.covisint.hackoween.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.covisint.hackoween.Engine;
import com.covisint.hackoween.model.Person;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpResponse;


        /**
 *
 */
@Controller
public class BaseController {
    
    private static int counter = 0;
    private static final String VIEW_INDEX = "index";
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);
    private final static Engine engine = new Engine();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {

        model.addAttribute("message", "Welcome Home");
        model.addAttribute("counter", ++counter);
       

        // Spring uses InternalResourceViewResolver and return back index.jsp
        return VIEW_INDEX;

    }
    
    @RequestMapping(value = "/federated", method = RequestMethod.GET)
    public String remoteUserLoad(ModelMap model,@RequestHeader("CT_REMOTE_USER") String ctUser) {

        model.addAttribute("message", "Welcome Federated User");
        model.addAttribute("ctUser", ctUser);
        String token = "";
        String name  = "";
        
        try {
             token = getToken();
             name =  getUser(ctUser, token);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
                    e.printStackTrace();
                
        } catch (IOException e) {
            // TODO Auto-generated catch block
                    e.printStackTrace();
                
        }
       logger.debug(" name "+name);
       model.addAttribute("userName", name);
        // Spring uses InternalResourceViewResolver and return back index.jsp
        return VIEW_INDEX;

    }

    /*@RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute("message", "Welcome " + name);
        model.addAttribute("counter", ++counter);
        logger.debug("[welcomeName] counter : {}", counter);
        return VIEW_INDEX;

    }*/
    
    @RequestMapping(value = "/moveperson", method = RequestMethod.POST)
    public String movePerson(ModelMap model,@RequestHeader("CT_REMOTE_USER") String ctUser, @RequestParam String attr1) {

        Person person = null;
        String token = "";
		try {
			person = engine.movePerson(ctUser, attr1);
			token = getToken();
            sendCommand( token, "eyJjb21tYW5kIjoiUExBWSIsImRhdGEiOiJmZW1hbGVTY3JlYW0xLm1wMyJ9Cg==" );
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        logger.debug(" attr1 ", attr1);
        logger.debug("[welcomeName] counter : {}", counter);
        model.addAttribute("userName", person.getFirstName());
        model.addAttribute("roomId", person.getCurrentRoom().getRefId());
        Gson gson = new Gson();
        String personString = gson.toJson(person);
        logger.debug("person json = " + personString);
        return personString;

    }
    
    @RequestMapping(value = "/voice", method = RequestMethod.POST)
    public String welcomeName(ModelMap model,@RequestParam String attr1) {

      String token = "";
        
        try {
             token = getToken();
             sendCommand( token, "eyJjb21tYW5kIjoiUExBWSIsImRhdGEiOiJmZW1hbGVTY3JlYW0xLm1wMyJ9Cg==" );
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
                    e.printStackTrace();
                
        } catch (IOException e) {
            // TODO Auto-generated catch block
                    e.printStackTrace();
                
        }

        logger.debug(" attr1 ", attr1);
        logger.debug("[welcomeName] counter : {}", counter);
        return VIEW_INDEX;

    }

  public  static String getToken() throws ClientProtocolException, IOException {
    String url = "https://api.covapp.io/oauth/v3/token";

    HttpClient client      = HttpClientBuilder.create().build();
    HttpPost   post        = new HttpPost( url );
    String     auth        = "rLfICu1QQGhQrelDTJQEV5zJ8mGodcVB" + ":" + "lat0eNA8k1Dul6pm";
    byte[]     encodedAuth = Base64.getEncoder()
                           .encode( auth.getBytes( Charset.forName( "ISO-8859-1" )));
    String     authHeader  = "Basic " + new String( encodedAuth );
    
    post.setHeader( HttpHeaders.AUTHORIZATION, authHeader );
    List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
    urlParameters.add( new BasicNameValuePair( "grant_type", "client_credentials" ));
    urlParameters.add( new BasicNameValuePair( "scope", "all" ));

    post.setEntity( new UrlEncodedFormEntity( urlParameters ));

    HttpResponse response = client.execute( post );
    System.out.println("Response Code : "  + response.getStatusLine().getStatusCode());
    
    BufferedReader rd     = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
    StringBuffer   result = new StringBuffer();
    String         line   = "";
    while(( line = rd.readLine()) != null ) {
      result.append( line );
    }
    
    String payload = new String( result.toString() );
    JsonParser parser = new JsonParser();
    JsonObject jo = ( JsonObject ) parser.parse( payload );
    String accessToken = jo.get( "access_token" ).getAsString();
    //System.out.println( "accessToken " + accessToken );
    return accessToken;
  }
  
  public static void sendCommand( String token, String command ) throws ClientProtocolException, IOException {
    String url = "https://api.covapp.io//sendcommand/v1/message/command/7d4a78f9-89aa-464f-9664-8fd07cca28bf";

    HttpClient client      = HttpClientBuilder.create().build();
    HttpPost   post        = new HttpPost( url );
    post.addHeader( "Accept",        "application/vnd.com.covisint.platform.messaging.sendcommand.v1+json" );
    post.addHeader( "Content-Type",  "application/vnd.com.covisint.platform.messaging.sendcommand.v1+json" );
    post.addHeader( "x-requestor",   "GBAWARI" );
    post.addHeader( "x-requestor-app",   "postman" );
    post.addHeader( "x-realm",       "HAL06-HACK" );
    post.addHeader( "Authorization", "Bearer " + token );
    
    StringBuilder builder = new StringBuilder( "{ \"messageId\":\"CommandobDojSequenceNum1158df1\", " )
                           .append( "\"deviceId\":\"2857437f-b793-4a98-a4c1-29e8ecf497bb\", " )
                           .append( "\"commandId\":\"9bea93da-1965-441f-a069-11860b8358f4\", " )
                           .append( "\"message\":\"" ).append( command ).append( "\", " )
                           .append("\"encodingType\":\"base64\" }" )
                           ;
    
    System.out.println( builder.toString() );
   
    post.setEntity( new StringEntity( builder.toString() ));

    HttpResponse response = client.execute( post );
    //System.out.println( "Response Code : "  + response.getStatusLine().getStatusCode());
  }
  
  public static String getUser( String ctUser, String token ) throws ClientProtocolException, IOException {
      String url = "https://api.us1.covisint.com/person/v1/persons/"+ctUser;

      HttpClient client      = HttpClientBuilder.create().build();
      HttpGet   post        = new HttpGet( url );
      post.addHeader( "Accept",        "application/vnd.com.covisint.platform.person.v1+json" );
      post.addHeader( "Content-Type",  "application/vnd.com.covisint.platform.person.v1+json" );
      post.addHeader( "x-requestor",   "GBAWARI" );
      post.addHeader( "x-requestor-app",   "postman" );
      post.addHeader( "x-realm",       "HAL06-HACK" );
      post.addHeader( "Authorization", "Bearer " + token );
      

      HttpResponse response = client.execute( post );
      
      BufferedReader rd     = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
      StringBuffer   result = new StringBuffer();
      String         line   = "";
      while(( line = rd.readLine()) != null ) {
        result.append( line );
      }
      
      String payload = new String( result.toString() );
      JsonParser parser = new JsonParser();
      JsonObject jo = ( JsonObject ) parser.parse( payload );
      String email = jo.get( "email" ).getAsString();
      logger.debug( "email  " + email );
      JsonObject name = jo.get( "name" ).getAsJsonObject();
      String nameGiven = name.get( "given" ).getAsString();
      logger.debug( "namegiven  " + nameGiven );
      String surName = name.get( "surname" ).getAsString();
      logger.debug( "surName  " + surName );
      return nameGiven + " "+surName;
      //System.out.println( "Response Code : "  + response.getStatusLine().getStatusCode());
    }

}

    
