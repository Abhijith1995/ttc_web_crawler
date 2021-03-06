package mainPackage;

import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;

public class Level2 {

	public static void getDataFromRoute(String BusRouteLink, String busNumber ,
			String busName ,BasicDBObject level2)
	{
		
		Document doc = null ;
		LinkedList<BasicDBObject> arr = new LinkedList<BasicDBObject>() ;
		Level3 level3 = new Level3() ;


		try
		{
			//Gets the route for all of the bus links
		    doc = Jsoup.connect(BusRouteLink).get();
			Elements direction =  doc.select("#ttc_route_page_tabs li"); 
			//Get the directions for given route
			String Direction1 = "" , Direction2 ="";
			int counter = 0;
			for(Element link: direction)
			{
				if(counter==0)
				{
					//Gets the link for Northbound / Direction 1
					Direction1 = mainClass.fixData(link.select("h3").text()) ;
					level2.put("direction", Direction1) ;
					
					
				level2.put("stops", level3.getStopList(BusRouteLink)) ;
				// Pushes all acquired data into level 2 basic DB object 
				//for MongoDB database

			
						
				}
				else if (counter == 1)
				{
					//Gets the link for Southbound / Direction 2

					Direction2 = mainClass.fixData(link.select("a").text()) ;
					level2.put("direction", Direction2) ;
					
					//Gets all the link
					String WebLink = link.select("a[href]").attr("href").
							toString() ;
					WebLink = "http://ttc.ca".concat(WebLink);
					
					level2.put("stops", level3.getStopList(WebLink)) ;
					// Pushes all acquired data into level 2 basic DB object 
					//for MongoDB database
					

				}
				
				else
				{
					break;
				}
				counter++;
				
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		}
}
