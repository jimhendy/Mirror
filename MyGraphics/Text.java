package MyGraphics;

import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

class Text extends JComponent {

    private String m_text;
    private int m_x;
    private int m_y;
    private Color m_color;
    private int m_fontSize;
    private int m_screenWidth;
    private String m_justify;

    public Text( String text, int x, int y, Color color, int fontSize, String justify, int screenWidth ){
        m_text = text;
        m_x = x;
        m_y = y;
	m_color = color;
	m_fontSize = fontSize;
	m_screenWidth = screenWidth;
	m_justify = justify;
    }
    
    public void paint(Graphics g) {
	g.setFont(new Font("Times New Roman", Font.BOLD, m_fontSize ));
	g.setColor(m_color);
	FontMetrics fontMetrics = g.getFontMetrics();
	if ( m_justify == "middle" ) m_x = ( m_screenWidth / 2 ) - ( fontMetrics.stringWidth( m_text ) / 2 );
	else if ( m_justify == "right" ) m_x = (int)( m_screenWidth * 0.98 ) - fontMetrics.stringWidth( m_text ); 
	g.drawString( m_text, m_x, m_y );
    }
}
