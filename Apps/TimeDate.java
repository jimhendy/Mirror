package Apps;

import java.util.Vector;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JComponent;

public class TimeDate extends App{

    public Date m_datetime;

    public TimeDate( int x, int y, int w, int h){
	m_left = x;
	m_top = y;
	m_width = w;
	m_height = h;
	m_name = "TimeDateApp";
	m_appComps = new Vector<JComponent>();
    }

    public void Update(){
	m_datetime = Calendar.getInstance().getTime();
    }

    public void Draw(){
	this.Clear();
	DateFormat df = new SimpleDateFormat("HH:mm");
	m_appComps.addElement( m_frame.DrawText( df.format( m_datetime ), 40, 40, "middle" ) );
	df = new SimpleDateFormat("EEEE");
	m_appComps.addElement( m_frame.DrawText( df.format(m_datetime), 80, 20, "middle" ) );
	df = new SimpleDateFormat("dd / MM / yyyy");
	m_appComps.addElement( m_frame.DrawText( df.format(m_datetime), 110, 15, "middle" ) );
    }
}
    
