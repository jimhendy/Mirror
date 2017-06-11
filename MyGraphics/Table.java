package MyGraphics;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;

class Table extends JComponent {

    private String[] m_columns;
    private Object[][] m_data;
    private int m_x;
    private int m_y;
    public JTable m_table;
    public JScrollPane m_scrollPane;
    

    public Table( String[] columns, Object[][] data, int x, int y ){
        m_columns = columns;
	m_data = data;
	m_x = x;
        m_y = y;
	m_table = new JTable( data, columns );
	m_scrollPane = new JScrollPane(m_table);
	//m_table.setFillsViewportHeight(true);
    }
    /*
    public void paint(Graphics g) {
	g.setFont(new Font("TimesRoman", Font.PLAIN, 20 ));
    }
    */
}
