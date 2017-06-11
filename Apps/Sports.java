package Apps;

import java.util.*;
import java.util.regex.*;
import java.net.*;
import java.io.*;


class Game{

    public String m_team;
    public String m_opponent;
    public String m_date;
    public String m_kickOff;
    public String m_channel;

    public Game( String team, String opponent, String date, String kickOff, String channel ){
	m_team = team;
	m_opponent = opponent;
	m_date = date;
	m_kickOff = kickOff;
	m_channel = channel;	
    }

}

public class Sports extends App{
    
    public Vector<Game> m_games;
    private Vector<String> m_rugbyTeams;
    private Vector<String> m_footballTeams;

    public Sports( int x, int y, int w, int h ){
	m_games = new Vector<Game>();
	m_name = "SportsApp";
	m_top = y;
	m_left = x;
	m_width = w;
	m_height = h;
	m_rugbyTeams = new Vector<String>();
	m_footballTeams = new Vector<String>();
	m_rugbyTeams.addElement("Glasgow");
	m_rugbyTeams.addElement("Munster");
	m_rugbyTeams.addElement("Scotland");
	m_rugbyTeams.addElement("Ireland");
	m_rugbyTeams.addElement("British and Irish Lions");
	m_footballTeams.addElement("Everton");
	
	
    }

    public void Draw(){
	int y = m_frame.m_height - (m_games.size() * 25);
	for ( Game g : m_games ){
	    m_frame.DrawText( g.m_team + " v " + g.m_opponent + "\t | \t" + g.m_date + "\t | \t" + g.m_kickOff + "\t | \t" + g.m_channel, m_left, y, 10 );
	    y += 20;
	}
    }
	    
    private void GetSport( String url, String sportId, Vector<String> myTeams ) throws Exception {
	url += sportId;
	URL websiteURL = new URL( url );
        BufferedReader in = new BufferedReader(
                                               new InputStreamReader(websiteURL.openStream()));
	String inputLine;
	StringBuilder everything = new StringBuilder();
	while ((inputLine = in.readLine()) != null){
	    everything.append(inputLine);
	}
	String allText = everything.toString();
	Pattern MY_PATTERN = Pattern.compile("\\<em class=\"\"\\>(.{0,30}?)\\<\\/em\\>.?\\<em class=\"\"\\>v\\<\\/em\\>.+?\\<em class=\"\"\\>(.{0,30}?)\\<\\/em\\>.+?class=\"time-channel \"\\>(.+?) at (.+?) on (.+?)\\<\\/span\\>\\<\\/span\\>");

	Matcher m = MY_PATTERN.matcher(allText);
	while (m.find()) {
	    for ( String t: myTeams ){
		if ( m.group(1).contains(t) || m.group(2).contains(t) )
		    m_games.addElement( new Game( m.group(1), m.group(2), m.group(3), m.group(4), m.group(5) ) );
	    }
	}
    }
	

    public void Update() throws Exception {
	Calendar nowDate = Calendar.getInstance();
	nowDate.setTime( new Date() );
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTime( new Date() );
        nextDate.add(Calendar.DAY_OF_YEAR, 7);

        String url = "http://www.wheresthematch.com/tv/home.asp?showdatestart=";
        url += String.valueOf(nowDate.get(Calendar.YEAR)) +
            String.format("%02d", nowDate.get(Calendar.MONTH) + 1) +
            String.format("%02d", nowDate.get(Calendar.DAY_OF_MONTH) );
        url += "&showdateend=";
        url += String.valueOf( nextDate.get(Calendar.YEAR)) +
            String.format("%02d", nextDate.get(Calendar.MONTH) + 1 ) +
            String.format("%02d", nextDate.get(Calendar.DAY_OF_MONTH ));
	url += "&sportid=";
	m_games.clear();
	GetSport( url, "3", m_rugbyTeams );
	GetSport( url, "1", m_footballTeams );
    }


}
