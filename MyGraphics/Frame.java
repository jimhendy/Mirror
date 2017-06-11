package MyGraphics;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.util.*;

public class Frame extends JComponent {
    
    private JFrame m_frame;
    public Vector<JComponent> m_comps;
    public int m_width;
    public int m_height;
    
    public void Clear( JComponent c ){
	m_frame.remove( c );
	m_comps.remove( c );
	m_frame.revalidate();
        m_frame.repaint();
    }
    
    public void Clear(){
	for ( int i = 0; i < m_comps.size(); i++ )
	    m_frame.remove( m_comps.elementAt(i) );
	m_comps.clear();
	m_frame.revalidate();
        m_frame.repaint();
    }

    private void AddC( JComponent c ){
	m_frame.getContentPane().add( c );
	m_comps.addElement( c );
        m_frame.validate();
        m_frame.repaint();
    }


    public JComponent DrawText( String text, int x, int y ){
	return DrawText( text, x, y, Color.white , 15, "" );
    }
    public JComponent DrawText( String text, int x, int y, Color color ){
	return DrawText( text, x, y, color, 15, "" );
    }
    public JComponent DrawText( String text, int x, int y, int fontSize ){
	return DrawText( text, x, y, Color.white, fontSize, "" );
    }
    public JComponent DrawText( String text, int x, int y, Color color, int fontSize ){
	return DrawText( text, x, y, color, fontSize, "" );
    }
    public JComponent DrawText( String text, int y, int fontSize, String justify ){
	return DrawText( text, 0, y, Color.white, fontSize, justify );
    }
    public JComponent DrawText( String text, int x, int y, Color color, int fontSize, String justify ){
	JComponent c = new Text( text, x, y, color, fontSize, justify , this.m_width );
        AddC( c );
	return c;
    }


    public void DrawBound( int x, int y, int w, int h ){
	JComponent c = new Rect( x, y, w, h );
	AddC( c );
    }

    public Frame () {
	m_frame = new JFrame();
	m_comps = new Vector<JComponent>();
	m_frame.getContentPane().setBackground(Color.black);
	m_frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
	m_width = ( Toolkit.getDefaultToolkit().getScreenSize().width );
	m_height = ( Toolkit.getDefaultToolkit().getScreenSize().height );
	// Uncomment to remove close button at top
	m_frame.setUndecorated(true);
	m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	m_frame.setVisible(true);
    }
}
