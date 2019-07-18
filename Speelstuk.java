import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Speelstuk extends JPanel implements MouseListener
{
	private int spelerID, deelTop, deelRechts, deelBeneden, deelLinks;
	private int hoogte, breedte, speelstukID;
	private boolean select = false, hoverStatus = false;
	private Polygon polyTop, polyRechts, polyBeneden, polyLinks;
	private Speler speler;
	private Color kleurSpeler;
	
	public Speelstuk( int spelerID, Color kleurSpeler, int speelstukID, int deelTop,
	int deelRechts, int deelBeneden, int deelLinks ) {
		this.spelerID = spelerID;
		this.kleurSpeler = kleurSpeler;
		this.speelstukID = speelstukID;
		this.deelTop = deelTop;
		this.deelRechts = deelRechts;
		this.deelBeneden = deelBeneden;
		this.deelLinks = deelLinks;
		this.maakPolygons();
	}
	public Speelstuk( Speler speler, int spelerID, Color kleurSpeler, int speelstukID,
	int deelTop, int deelRechts, int deelBeneden, int deelLinks ) {
		this.speler = speler;
		this.spelerID = spelerID;
		this.kleurSpeler = kleurSpeler;
		this.speelstukID = speelstukID;
		this.deelTop = deelTop;
		this.deelRechts = deelRechts;
		this.deelBeneden = deelBeneden;
		this.deelLinks = deelLinks;
		this.addMouseListener( this );
		this.maakPolygons();
	}
	public void paintComponent( Graphics g ) {
		boolean streep = false, overHelft = false;
		int streepLengte, inspring = 0;
		if ( hoverStatus ) {
			g.setColor( Color.yellow );
			g.fillRect( 0, 0, breedte, hoogte );
		} else {
			g.setColor( Color.white );
			g.fillRect( 0, 0, breedte, hoogte );
		}
		g.setColor( Color.black );
		// TOP
		if ( deelTop == 2 ) {
			streep = false;
			overHelft = false;
			streepLengte = hoogte;
			inspring = 0;
			for ( int i = 0; i <= hoogte/2; i += 2 ) {
				if ( streep ) {
					g.fillRect( inspring, i, streepLengte, 2 );
				}
				streepLengte -= 4;
				inspring += 2;
				streep = !streep;
			}
		} else if ( deelTop == 3 ) {
			streep = false;
			overHelft = false;
			streepLengte = 0;
			for ( int i = 0; i < breedte; i += 2 ) {
				if ( streep ) {
					g.fillRect( i, 0, 2, streepLengte );
				}
				if ( streepLengte < hoogte/2 && !overHelft )
				{
					streepLengte += 2;
				} else {
					overHelft = true;
					streepLengte -= 2;
				}
				streep = !streep;
			}
		}
		// RECHTS
		if ( deelRechts == 2 ) {
			streep = false;
			overHelft = false;
			streepLengte = 0;
			inspring = 0;
			for ( int i = 0; i < hoogte; i += 2 ) {
				if ( streep ) {
					g.fillRect( breedte-inspring, i, streepLengte, 2 );
				}
				if ( streepLengte < breedte/2 && !overHelft )
				{
					streepLengte += 2;
					inspring += 2;
				} else {
					overHelft = true;
					streepLengte -= 2;
					inspring -= 2;
				}
				streep = !streep;
			}
		} else if ( deelRechts == 3 ) {
			streep = true;
			overHelft = false;
			streepLengte = -4;
			inspring = hoogte/2+2;
			for ( int i = -2; i <= breedte/2; i += 2 ) {
				if ( streep ) {
					g.fillRect( breedte/2+i, inspring, 2, streepLengte );
				}
				streepLengte += 4;
				inspring -= 2;
				streep = !streep;
			}
		}
		// BENEDEN
		if ( deelBeneden == 2 ) {
			streep = true;
			overHelft = false;
			streepLengte = -2;
			inspring = breedte/2+2;
			for ( int i = -2; i <= hoogte/2; i += 2 ) {
				if ( streep ) {
					g.fillRect( inspring, hoogte/2+i, streepLengte, 2 );
				}
				streepLengte += 4;
				inspring -= 2;
				streep = !streep;
			}
		} else if ( deelBeneden == 3 ) {
			streep = false;
			overHelft = false;
			streepLengte = 0;
			inspring = 0;
			for ( int i = 0; i <= breedte; i += 2 ) {
				if ( streep ) {
					g.fillRect( i, hoogte-inspring, 2, streepLengte );
				}
				if ( streepLengte < hoogte/2 && !overHelft )
				{
					streepLengte += 2;
					inspring += 2;
				} else {
					overHelft = true;
					streepLengte -= 2;
					inspring -= 2;
				}
				streep = !streep;
			}
		}
		// LINKS
		if ( deelLinks == 2 ) {
			streep = false;
			overHelft = false;
			streepLengte = 2;
			for ( int i = 0; i <= hoogte; i += 2 ) {
				if ( streep ) {
					g.fillRect( 0, i, streepLengte, 2 );
				}
				if ( streepLengte <= breedte/2 && !overHelft )
				{
					streepLengte += 2;
				} else {
					overHelft = true;
					streepLengte -= 2;
				}
				streep = !streep;
			}
		} else if ( deelLinks == 3 ) {
			streep = false;
			overHelft = false;
			streepLengte = hoogte;
			inspring = 0;
			for ( int i = 0; i <= breedte/2; i += 2 ) {
				if ( streep ) {
					g.fillRect( i, inspring, 2, streepLengte );
				}
				streepLengte -= 4;
				inspring += 2;
				streep = !streep;
			}
		}
		g.setColor( kleurSpeler );
		if ( deelTop == 1 ) {
			g.fillPolygon( polyTop );
		}
		if ( deelRechts == 1 ) {
			g.fillPolygon( polyRechts );
		}
		if ( deelBeneden == 1 ) {
			g.fillPolygon( polyBeneden );
		}
		if ( deelLinks == 1 ) {
			g.fillPolygon( polyLinks );
		}
	}
	public void maakPolygons() {
		polyTop = new Polygon();
		polyTop.addPoint( 0, 0 );
		polyTop.addPoint( breedte, 0 );
		polyTop.addPoint( breedte/2, hoogte/2 );
		polyRechts = new Polygon();
		polyRechts.addPoint( breedte, 0 );
		polyRechts.addPoint( breedte, hoogte );
		polyRechts.addPoint( breedte/2, hoogte/2 );
		polyBeneden = new Polygon();
		polyBeneden.addPoint( 0, hoogte );
		polyBeneden.addPoint( breedte, hoogte );
		polyBeneden.addPoint( breedte/2, hoogte/2 );
		polyLinks = new Polygon();
		polyLinks.addPoint( 0, 0 );
		polyLinks.addPoint( 0, hoogte );
		polyLinks.addPoint( breedte/2, hoogte/2 );
	}
	public void draaiLinks() {
		if ( deelTop == 3 ) { deelTop = 2; }
		else if ( deelTop == 2 ) { deelTop = 3; }
		if ( deelRechts == 3 ) { deelRechts = 2; }
		else if ( deelRechts == 2 ) { deelRechts = 3; }
		if ( deelBeneden == 3 ) { deelBeneden = 2; }
		else if ( deelBeneden == 2 ) { deelBeneden = 3; }
		if ( deelLinks == 3 ) { deelLinks = 2; }
		else if ( deelLinks == 2 ) { deelLinks = 3; }
		int temp = deelTop;
		deelTop = deelRechts;
		deelRechts = deelBeneden;
		deelBeneden = deelLinks;
		deelLinks = temp;
		this.repaint();
	}
	public void draaiRechts() {
		if ( deelTop == 3 ) { deelTop = 2; }
		else if ( deelTop == 2 ) { deelTop = 3; }
		if ( deelRechts == 3 ) { deelRechts = 2; }
		else if ( deelRechts == 2 ) { deelRechts = 3; }
		if ( deelBeneden == 3 ) { deelBeneden = 2; }
		else if ( deelBeneden == 2 ) { deelBeneden = 3; }
		if ( deelLinks == 3 ) { deelLinks = 2; }
		else if ( deelLinks == 2 ) { deelLinks = 3; }
		int temp = deelLinks;
		deelLinks = deelBeneden;
		deelBeneden = deelRechts;
		deelRechts = deelTop;
		deelTop = temp;
		this.repaint();
	}
	public int getSpeler() {
		return spelerID;
	}
	public int[] getSpeelstukData() {
		int[] info;
		info = new int[] { deelTop, deelRechts, deelBeneden, deelLinks };
		return info;
	}
	public int getSpeelstukID() {
		return speelstukID;
	}
	public void setGrootte( int breedte, int hoogte ) {
		this.breedte = breedte;
		this.hoogte = hoogte;
		this.setSize( breedte, hoogte );
		this.maakPolygons();
	}
	public void setSelect( boolean select ) {
		this.select = select;
	}
	public Color getKleur() {
		return kleurSpeler;
	}
	public void mouseClicked( MouseEvent e ) {
		if ( select ) {
			speler.selectSpeelstuk( speelstukID );
		}
	}
	public void mousePressed( MouseEvent e ) {}
	public void mouseEntered( MouseEvent e ) {
		if ( select ) {
			hoverStatus = true;
			this.repaint();
		}
	}
	public void mouseExited( MouseEvent e ) {
		if ( select ) {
			hoverStatus = false;
			this.repaint();
		}
	}
	public void mouseReleased( MouseEvent e ) {}
}
