package Apps;

import MyGraphics.Frame;
import javax.swing.JComponent;
import java.util.Vector;

public class App{
    
    public static Frame m_frame;
    
    public String m_name;
    public int m_left;
    public int m_width;
    public int m_top;
    public int m_height;
    public Vector<JComponent> m_appComps;

    public void Clear() {
	for ( int i = 0; i < m_appComps.size(); i++ ){
	    try{ m_frame.Clear( m_appComps.elementAt(i) ); }
	    catch(Exception e){ System.out.println(e); }
	}
	m_appComps.clear();
    }
    
    public void Update() throws Exception {
	System.out.println( "Cannot update from App superclass" );
    }
    
    public void Draw(){
	System.out.println( "Cannot draw from App superclass" );
    }
    
    public void DrawBound( int x, int y, int width, int height ){
	m_frame.DrawBound( x, y, width, height );
    }

}
	
    
