package MyGraphics;

import java.awt.Graphics;
import javax.swing.JComponent;

class Rect extends JComponent {

    private int m_left;
    private int m_top;
    private int m_width;
    private int m_height;

    public Rect( int x, int y, int w, int h ){
        m_left = x;
        m_top = y;
        m_width = w;
        m_height = h;
    }

    public void paint(Graphics g){
        g.drawRoundRect( m_left, m_top, m_width, m_height, 1, 10 );
    }
}
