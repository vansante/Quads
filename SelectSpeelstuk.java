import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectSpeelstuk extends JPanel implements ActionListener, MouseWheelListener
{
	private Speelstuk speelstuk;
	private Speelveld speelveld;
	private JButton draaiLinks, draaiRechts;
	private JPanel knopPanel;
	private int speelstukID, speelstukGrootte;
	
	public SelectSpeelstuk() {
		this.setBackground( Color.white );
		this.addMouseWheelListener( this );
		this.setLayout( new FlowLayout() );
		this.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
		knopPanel = new JPanel( new FlowLayout() );
		knopPanel.setBackground( Color.white );
		draaiLinks = new JButton( "<<<" );
		draaiLinks.addActionListener( this );
		draaiLinks.setBackground( Color.white );
		draaiLinks.setForeground( Color.black );
		draaiRechts = new JButton( ">>>" );
		draaiRechts.addActionListener( this );
		draaiRechts.setBackground( Color.white );
		draaiRechts.setForeground( Color.black );
		knopPanel.add( draaiLinks );
		knopPanel.add( draaiRechts );
	}
	public void setGrootte( int speelstukGrootte ) {
		this.speelstukGrootte = speelstukGrootte;
		int selectHoogte = speelstukGrootte + 50;
		int selectBreedte = speelstukGrootte + 100;
		this.setPreferredSize( new Dimension( selectBreedte, selectHoogte ) );
		this.setMaximumSize( new Dimension( selectBreedte, selectHoogte ) );
		if ( speelstuk != null ) {
			speelstuk.setGrootte( speelstukGrootte, speelstukGrootte );
			speelstuk.setMinimumSize( new Dimension( speelstukGrootte, speelstukGrootte ) );
			speelstuk.setPreferredSize( new Dimension( speelstukGrootte, speelstukGrootte ) );
			speelstuk.setMaximumSize( new Dimension( speelstukGrootte, speelstukGrootte ) );
		}
	}
	public void setData( Speelveld speelveld ) {
		this.speelveld = speelveld;
	}
	public Speelstuk getSpeelstuk() {
		return speelstuk;
	}
	public int[] getSpeelstukData() {
		return speelstuk.getSpeelstukData();
	}
	public int getSpeler() {
		if ( speelstuk != null ) {
			return speelstuk.getSpeler();
		} else {
			return -1;
		}
	}
	public void setSpeelstukID( int speelstukID ) {
		this.speelstukID = speelstukID;
	}
	public int getSpeelstukID() {
		if ( speelstuk != null ) {
			return speelstukID;
		} else {
			return -1;
		}
	}
	public void setSpeelstuk( Speelstuk speelstuk ) {
		if ( this.speelstuk == null ) {
			this.speelstuk = speelstuk;
			this.add( speelstuk );
			this.add( knopPanel );
			speelveld.setSelect( true );
			this.setGrootte( speelstukGrootte );
			this.repaint();
			this.validate();
		} else {
			this.reset();
			this.setSpeelstuk( speelstuk );
		}
	}
	public void reset() {
		this.remove( speelstuk );
		this.remove( knopPanel );
		speelstuk = null;
		this.repaint();
	}
	public boolean getSelect() {
		if ( speelstuk != null) {
			return true;
		} else {
			return false;
		}
	}
	public void draaiLinks() {
		if ( speelstuk != null ) {
			speelstuk.draaiLinks();
			this.repaint();
		}
	}
	public void draaiRechts() {
		if ( speelstuk != null ) {
			speelstuk.draaiRechts();
			this.repaint();
		}
	}
	public void actionPerformed( ActionEvent e ) {
		Object event = e.getSource();
		if ( event == draaiLinks ) {
			this.draaiLinks();
		} else if ( event == draaiRechts ) {
			this.draaiRechts();
		}
	}
	public void mouseWheelMoved( MouseWheelEvent e ) {
		if ( speelstuk != null ) {
			if ( e.getWheelRotation() > 0 ) {
				this.draaiLinks();
			} else {
				this.draaiRechts();
			}
		}
	}
}
