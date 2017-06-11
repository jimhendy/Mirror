
package Apps;

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.regex.*;
import java.awt.Color;

class Tube{
    public String m_name;
    public String m_status;
    public Tube( String name, String status ){
        m_name = name;
        m_status = status;
    }
}

public class Tubes extends App{

    public Vector<Tube> m_tubes;

    public Tubes( int x, int y, int w, int h ) {
	m_tubes = new Vector<Tube>();
	m_left = x;
	m_top = y;
	m_width = w;
	m_height = h;
	m_name = "TubesApp";
    }

    public void Draw(){
	if ( m_tubes.size() == 0 )
	    m_frame.DrawText( "There is a good service on all lines",  m_frame.m_width - 300, 20  );
	else{
	    for ( int i = 0; i < m_tubes.size(); i++ ){
		if ( !m_tubes.elementAt(i).m_status.contains( "Good service" ) ){
		    Tube t = m_tubes.elementAt(i);
		    m_frame.DrawText( GetTubeShortName( t.m_name ) , m_frame.m_width - 200, (i+1)*20, GetTubeColor( t.m_name ) );
		    m_frame.DrawText( t.m_status , m_frame.m_width - 150, (i + 1) * 20  );
		}
	    }
	}
    }

    private String GetTubeShortName( String longName ){
	String shortName = "";
	if ( longName.contains("DLR") ) return "DLR";
	for ( String s : longName.split(" ") )
	    shortName += s.charAt(0);
	return shortName;
    }

    public void Update() throws Exception {
	String urlString = "https://tfl.gov.uk/tube-dlr-overground/status/";
	//URL websiteURL = new URL( url );
	URLConnection uc;
	URL url = new URL(urlString);
	uc = url.openConnection();
	uc.connect();

	uc = url.openConnection();
	uc.addRequestProperty("User-Agent",
			      "Mozilla/5.0 (X11; Linux armv7l) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.84 Safari/537.36");

	uc.getInputStream();
	BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
	m_tubes = new Vector<Tube>();
	StringBuilder everything = new StringBuilder();	
	int ch;
	while ((ch = in.read()) != -1) {
	    everything.append((char) ch);
	}
	/*
        BufferedReader in = new BufferedReader(
		new InputStreamReader(websiteURL.openStream()));
	String inputLine;
	while ((inputLine = in.readLine()) != null){
	        everything.append(inputLine);
	}
	*/
	String allText = everything.toString().replace("\n", "").replace("\r", "");;
	Pattern MY_PATTERN = Pattern.compile("\\<span\\>(.{0,20}?)\\<\\/span\\>\\<\\/span\\>\\<span class=\"disruption-summary \"\\>\\<span\\>(.+?)\\<br\\/\\>");

	Matcher m = MY_PATTERN.matcher(allText);
	while (m.find()) {
	    m_tubes.addElement( new Tube( m.group(1), m.group(2) ) );
	}
    }

    private Color GetTubeColor( String tubeline ){
	// http://content.tfl.gov.uk/tfl-colour-standard.pdf
	if ( tubeline.contains("Hammersmith") ) return new Color( 251, 153, 175 );
	else if ( tubeline.contains("Circle") ) return new Color(255, 206, 0);
	else if ( tubeline.contains("District") ) return new Color( 0, 114, 41 );
	else if ( tubeline.contains("Overground") ) return new Color( 232, 106, 16);
	else if ( tubeline.contains("Jubilee") ) return new Color( 134, 143, 152 );
	else if ( tubeline.contains("Piccadilly") ) return new Color( 0, 25, 168 );
	//else if ( tubeline.contains("Northern") ) return new Color( 0, 0, 0 );
	else if ( tubeline.contains("Bakerloo") ) return new Color( 137, 78, 36 );
	else if ( tubeline.contains("Central") ) return new Color( 220, 36, 31 );
	else if ( tubeline.contains("Metropolitan") ) return new Color( 117, 16, 86 );
	else if ( tubeline.contains("Victoria") ) return new Color( 0, 160, 226);
	else if ( tubeline.contains("Waterloo") ) return new Color( 118, 208, 189 );
	else if ( tubeline.contains("DLR") ) return new Color( 0, 175, 173 );
	else return Color.white;
    }
}
	    
