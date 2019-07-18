import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Speelvak extends JPanel implements MouseListener
{
	private Speelstuk speelstuk;
	private Speelveld speelveld;
	private boolean bezet = false, select = true;
	private int breedte, hoogte, xPos, yPos;
	
	public Speelvak( int xPos, int yPos, Speelveld speelveld ) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.speelveld = speelveld;
		this.setLayout( null );
		this.setBackground( Color.white );
		this.setSize( breedte, hoogte );
		this.addMouseListener( this );
	}
	public int getSpeler() {
		return speelstuk.getSpeler();
	}
	public int[] getSpeelstukData() {
		if ( speelstuk != null ) {
			return speelstuk.getSpeelstukData();
		} else {
			return null;
		}
	}
	public int getSpeelstukID() {
		if ( speelstuk != null ) {
			return speelstuk.getSpeelstukID();
		} else {
			return -1;
		}
	}
	public void setSpeelstuk( Speelstuk speelstuk ) {
		this.speelstuk = speelstuk;
		bezet = true;
		speelstuk.setLocation( 0, 0 );
		add( speelstuk );
		this.setBackground( Color.white );
	}
	public void setSelect( boolean select ) {
		this.select = select;
	}
	public void setFouteZet() {
		this.setBackground( Color.red );
	}
	public boolean getBezet() {
		return bezet;
	}
	public void setGrootte( int speelstukGrootte ) {
		this.breedte = speelstukGrootte;
		this.hoogte = speelstukGrootte;
		this.setSize( breedte, hoogte );
		if ( speelstuk != null ) {
			speelstuk.setGrootte( speelstukGrootte, speelstukGrootte );
		}
	}
	public void muisHover() {
		if ( !bezet && select && speelveld.checkZet( xPos, yPos ) ) {
			this.setBackground( Color.yellow );
		}
	}
	public void resetHover() {
		this.setBackground( Color.white );
	}
	public void mouseClicked( MouseEvent e ) {
		if ( !bezet && select ) {
			speelveld.selecteerVak( xPos, yPos );
		}
	}
	public void mousePressed( MouseEvent e ) {}
	public void mouseEntered( MouseEvent e ) {
		this.muisHover();
	}
	public void mouseExited( MouseEvent e ) {
		this.resetHover();
	}
	public void mouseReleased( MouseEvent e ) {}
}
