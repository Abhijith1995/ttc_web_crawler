package mainPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.*;

import com.mongodb.util.*;


import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.mongodb.BasicDBObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.io.* ;
import java.util.HashMap;
import java.util.LinkedList;
public class mainClass {

public static void main(String[] args) throws IOException  
	{
	
		
		// Connects to MongoDB database
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB( "battlehacktoronto" );

		
		// Connnects to given database
		DBCollection coll = db.getCollection("BusRoutes");
		
		Level1 level1 = new Level1() ;
		level1.getBusRoutes(coll);
 
		
}
		

	/*
	 * This is a hack fix to remove the words Accessible and Bicyle 
	 * 
	 */
	public static String fixData(String initial)
	{
		  StringBuilder fixed = new StringBuilder();
			
			String[] textWords = initial.split(" ");
			
			
			// Hacky fix for getting a proper name from link
			for( int i =0;i < textWords.length; i++)
			{
				
				if(
						textWords[i].equals("Bicycle")
				|| 		textWords[i].equals("Accessible")
				|| 		textWords[i].equals("Rack") 

				|| 		textWords[i].equals("The")
				|| 		textWords[i].equals("list") 
				|| 		textWords[i].equals("List") 
				|| 		textWords[i].equals("below")
				|| 		textWords[i].equals("shows") 
				|| 		textWords[i].equals("Stops")
				)
				{
						textWords[i] = "";
				}
				fixed.append(textWords[i] +" ") ; 
				

			}
			return fixed.toString().trim() ;
	}
}




	

