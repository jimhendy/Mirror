package Apps;

import java.io.*;
import java.util.*;
import javax.swing.JComponent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


class Note{

    public String m_author;
    public String m_note;
    public Date m_date;

    public Note( String author, String note, Date date ){
	m_author = author;
	if ( m_author.equalsIgnoreCase("jimhendy88@gmail.com") ) m_author = "Jim";
	else if ( m_author.equalsIgnoreCase("alisonbutler87@googlemail.com") ) m_author = "Ali";
	else if ( m_author.equalsIgnoreCase("mirrorcontroljimali@gmail.com") ) m_author = "Mirror";
	m_note = note;
	m_date = date;
    }
}
    

public class Messages extends App{

    public Vector<Note> m_notes;

    public Messages( int x, int y, int w, int h ){
	m_left = x;
	m_top = y;
	m_width = w;
	m_height = h;
	m_name = "MessageApp";
	m_notes = new Vector<Note>();
	m_appComps = new Vector<JComponent>();
    }

    public void Draw(){
	this.Clear();
	int y = m_frame.m_height - ( 30 * m_notes.size() );
	for ( Note n : m_notes ){
	    JComponent c = m_frame.DrawText( n.m_author + " - " + n.m_note , y, 15, "right" );
	    m_appComps.addElement( c );
	    y += 20 ;
	}
    }

    public void Update() throws Exception {
	try{
	    Process p = Runtime.getRuntime().exec("/home/pi/bin/GetMail.sh");
	    p.waitFor();
	}
	catch(Exception e){ System.out.println(e); }
	m_notes.clear();
	String mailDir = "/home/pi/mail/new/";
	File folder = new File( mailDir );
	File[] listOfFiles = folder.listFiles();
	DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
	for (int i = 0; i < listOfFiles.length; i++) {
	    if (listOfFiles[i].isFile()) {
		String author = "";
		String note = "";
		Date date = null;
		try (BufferedReader br = new BufferedReader(new FileReader( mailDir + listOfFiles[i].getName() ))) {
			String line;
			while ((line = br.readLine()) != null) {
			    if ( line.contains("From:") ){ author = (line.split("<")[1]).split(">")[0].trim(); }
			    else if ( line.contains("Subject:") ){ note = (line.split("Subject:")[1]).trim(); }
			    else if ( line.contains("Received: by " ) && line.contains("HTTP") ){ date = df.parse((line.split("HTTP;")[1]).split("-")[0].trim() ) ; }
			}
			m_notes.addElement( new Note( author, note, date ) );
		    }
		catch(Exception e){ System.out.println(e); }
	    }
	}
	Collections.sort( m_notes, new Comparator<Note>() {
		public int compare(Note n1, Note n2) {
		    if (n1.m_date.after( n2.m_date ) ) return 1 ;
		    return -1;
		}
	    } );
    }
	    
}
