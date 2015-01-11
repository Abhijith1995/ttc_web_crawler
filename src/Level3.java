package mainPackage;

import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;

public class Level3 {

	public  LinkedList<BasicDBObject> getStopList(String webLink) 
	{
	LinkedList<BasicDBObject> level3Array = new LinkedList<BasicDBObject>();
	Document doc=null;
	
				try {
					doc = Jsoup.connect(webLink).get(); // Goes to stop list
				} catch (IOException ex) {
					ex.printStackTrace();
				}
	 
			
		    Elements links = doc.select("#timed-stops-1 a[href]");
		    // Gets all the timed stops for given stop
			
			for (Element link : links) 
			{
				if(!link.text().equals(""))
				{
					BasicDBObject level3 = new BasicDBObject() ;
					String fixedLinkText = mainClass.fixData(link.text());
					
					
					level3.put("stopName", fixedLinkText) ;
					
					
					String stopLink = "http://ttc.ca".concat(link.attr("href"));
					String timesWeekday[] , timesSaturday[] , timesSunday[] ;
					
					
					timesWeekday = this.getDataFromStop(0, stopLink);
					
					timesSaturday = this.getDataFromStop(1, stopLink);
					
					timesSunday = this.getDataFromStop(2, stopLink);
					
					level3.put("timesWeekday", timesWeekday) ;
					level3.put("timesSaturday", timesSaturday) ;
					level3.put("timesSunday", timesSunday) ;
					
					level3Array.add(level3);
					// Gets the level 3 array with all the required timings
					
					
					
					
				}
			
			}
			
			String sendStops = "";
			String sendWeekdays = "", sendSat="",sendSun="";
			
			
			System.out.println(sendStops);
			System.out.println(sendWeekdays);
			System.out.println(sendSat);
			System.out.println(sendSun);
			
			return level3Array ;

		
			
		}
	// Gets all the data from a given stop, given day of the week
	public  String[] getDataFromStop(int type, String StopLink)
	{
		Document doc = null ;
		String connector = "";
		
		if(type==0)
		{
			connector = "&Day=Monday_To_Friday" ;
		}
		else if ( type ==1)
		{
			connector = "&Day=Saturday" ;
		}
		else if ( type == 2)
		{
			connector = "&Day=Sunday" ;
		}
		
	    try {
				doc = Jsoup.connect(StopLink.concat(connector)).get();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	    
	    // Gets all the links/stop timings
		 Elements times = doc.select(".schedule-time-top-border .sched-time");
			int counterForTimes = 0;
			int totalTimes = times.size();
			String[] busTimes = new String[totalTimes] ;
			for (Element time : times) 
			{
			
				
				String timeString = time.text();
				String t1 = timeString.substring(0,timeString.indexOf(':'));
				String t2 = t1.concat("-");
				t2 = t2.concat(timeString.substring(timeString.indexOf(':')+1));
				
				
				
				busTimes[counterForTimes] = t2 ;
				counterForTimes ++;
			}
			return busTimes ; // Returns a string of all the bus times 
			//for that given bus route for that given stop on that 
			//given day
	}
	
}
