import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.*;
import javax.swing.*;

public class Speler extends JPanel implements Serializable {
	private Speelstuk[] speelstukken;
	private Speelveld speelveld;
	private SelectSpeelstuk select;
	private JPanel mainPanel;
	private JPanel[] speelstukvakken;
	private JLabel label;
	private int spelerID, speelstukGrootte;
	private boolean bot;
	private Color spelerKleur;
	private String spelerNaam;
	
	public Speler( boolean bot, int spelerID, Color spelerKleur, String spelerNaam ) {
		this.bot = bot;
		this.spelerID = spelerID;
		this.spelerKleur = spelerKleur;
		this.spelerNaam = spelerNaam;
		mainPanel = new JPanel();
		mainPanel.setBackground( Color.black );
		mainPanel.setLayout( new GridLayout( 2, 9, 1, 1 ) );
		mainPanel.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
		label = new JLabel( spelerNaam );
		label.setForeground( Color.black );
		speelstukvakken = new JPanel[18];
		speelstukken = new Speelstuk[18];
		speelstukken[0] = new Speelstuk( this, spelerID, spelerKleur, 0, 3, 3, 3, 3 );
		speelstukken[1] = new Speelstuk( this, spelerID, spelerKleur, 1, 3, 1, 2, 1 );
		speelstukken[2] = new Speelstuk( this, spelerID, spelerKleur, 2, 2, 1, 2, 3 );
		speelstukken[3] = new Speelstuk( this, spelerID, spelerKleur, 3, 2, 2, 1, 3 );
		speelstukken[4] = new Speelstuk( this, spelerID, spelerKleur, 4, 1, 3, 3, 3 );
		speelstukken[5] = new Speelstuk( this, spelerID, spelerKleur, 5, 2, 2, 1, 2 );
		speelstukken[6] = new Speelstuk( this, spelerID, spelerKleur, 6, 1, 2, 2, 3 );
		speelstukken[7] = new Speelstuk( this, spelerID, spelerKleur, 7, 1, 3, 1, 1 );
		speelstukken[8] = new Speelstuk( this, spelerID, spelerKleur, 8, 1, 1, 2, 3 );
		speelstukken[9] = new Speelstuk( this, spelerID, spelerKleur, 9, 1, 2, 3, 1 );
		speelstukken[10] = new Speelstuk( this, spelerID, spelerKleur, 10, 1, 3, 3, 1 );
		speelstukken[11] = new Speelstuk( this, spelerID, spelerKleur, 11, 1, 1, 3, 3 );
		speelstukken[12] = new Speelstuk( this, spelerID, spelerKleur, 12, 1, 3, 1, 3 );
		speelstukken[13] = new Speelstuk( this, spelerID, spelerKleur, 13, 3, 1, 3, 1 );
		speelstukken[14] = new Speelstuk( this, spelerID, spelerKleur, 14, 2, 2, 3, 1 );
		speelstukken[15] = new Speelstuk( this, spelerID, spelerKleur, 15, 2, 1, 2, 3 );
		speelstukken[16] = new Speelstuk( this, spelerID, spelerKleur, 16, 1, 1, 1, 2 );
		speelstukken[17] = new Speelstuk( this, spelerID, spelerKleur, 17, 3, 2, 2, 1 );
		for ( int i = 0; i < speelstukken.length; i++ ) {
			speelstukvakken[i] = new JPanel( null );
			speelstukvakken[i].setBackground( Color.white );
			speelstukvakken[i].add( speelstukken[i] );
			speelstukken[i].setLocation( 1, 1 );
			mainPanel.add( speelstukvakken[i] );
		}
		if ( spelerID == 0 ) {
			label.setHorizontalAlignment( JLabel.LEFT );
			this.add( mainPanel );
			this.add( label );
		} else {
			label.setHorizontalAlignment( JLabel.RIGHT );
			this.add( label );
			this.add( mainPanel );
		}
	}
	public void setGrootte( int speelstukGrootte ) {
		this.speelstukGrootte = speelstukGrootte;
		int spelerHoogte = ( 2 * speelstukGrootte ) + 3;
		int spelerBreedte = ( 9 * speelstukGrootte ) + 10;
		this.setMinimumSize( new Dimension( spelerBreedte, spelerHoogte + 15 + speelstukGrootte / 3 ) );
		this.setPreferredSize( new Dimension( spelerBreedte, spelerHoogte + 15 + speelstukGrootte / 3 ) );
		this.setMaximumSize( new Dimension( spelerBreedte, spelerHoogte + 15 + speelstukGrootte / 3 ) );
		mainPanel.setPreferredSize( new Dimension( spelerBreedte, spelerHoogte ) );
		mainPanel.setMaximumSize( new Dimension( spelerBreedte, spelerHoogte ) );
		label.setPreferredSize( new Dimension( spelerBreedte, speelstukGrootte/3+5 ) );
		label.setFont( new Font( "Serif", Font.BOLD, speelstukGrootte/3 ) );
		for ( int i = 0; i < speelstukken.length; i++ ) {
			if ( speelstukken[i] != null ) {
				speelstukken[i].setGrootte( speelstukGrootte, speelstukGrootte );
				speelstukvakken[i].setSize( speelstukGrootte+2, speelstukGrootte+2 );
			}
		}
		this.revalidate();
		this.repaint();
	}
	public void setData( Speelveld speelveld, SelectSpeelstuk select ) {
		this.speelveld = speelveld;
		this.select = select;
	}
	public void dumpData() {
		speelveld = null;
		select = null;
	}
	public void setAanBeurt( boolean aanBeurt ) {
		if ( aanBeurt ) {
			if ( spelerID == 0 ) {
				label.setText( spelerNaam + " <<<" );
			} else {
				label.setText( ">>> " + spelerNaam );
			}
		} else {
			label.setText( spelerNaam );
		}
		if ( !bot ) {
			for ( int i = 0; i < speelstukken.length; i++ ) {
				if ( speelstukken[i] != null ) {
					speelstukken[i].setSelect( aanBeurt );
				}
			}
		} else if ( aanBeurt ) {
			this.botDoeZet();
		}
	}
	public void selectSpeelstuk( int speelstukID ) {
		if ( this.checkEersteSteen() || speelstukID == 0 ) {
			int[] speelstukdata = speelstukken[speelstukID].getSpeelstukData();
			select.setSpeelstuk( new Speelstuk( speelstukken[speelstukID].getSpeler(), speelstukken[speelstukID].getKleur(), speelstukID, speelstukdata[0], speelstukdata[1], speelstukdata[2], speelstukdata[3] ) );
			select.setSpeelstukID( speelstukID );
		} else {
			JOptionPane.showMessageDialog( speelveld, "U moet eerst de neutrale beginsteen plaatsen.", "Waarschuwing", JOptionPane.WARNING_MESSAGE );
		}
	}
	public boolean checkEersteSteen() {
		for ( int i = 0; i < speelstukken.length; i++ ) {
			if ( speelstukken[i] != null ) {
				if ( speelstukken[i].getSpeelstukID() == 0 ) {
					return false;
				}
			}
		}
		return true;
	}
	public void verwijderSpeelstuk( int speelstukID ) {
		speelstukvakken[speelstukID].remove( speelstukken[speelstukID] );
		speelstukken[speelstukID] = null;
		mainPanel.doLayout();
		mainPanel.repaint();
	}
	public Speelstuk[] getSpeelstukken() {
		Speelstuk[] temp = new Speelstuk[speelstukken.length];
		for ( int i = 0; i < speelstukken.length; i++ ) {
			if ( speelstukken[i] != null ) {
				int[] data = speelstukken[i].getSpeelstukData();
				temp[i] = new Speelstuk( speelstukken[i].getSpeler(), speelstukken[i].getKleur(), speelstukken[i].getSpeelstukID(), data[0], data[1], data[2], data[3] );
			}
		}
		return temp;
	}
	public String getSpelerNaam() {
		return spelerNaam;
	}
	public void botDoeZet() {
		ArrayList puntenLijst = new ArrayList();
		ArrayList zettenLijst = new ArrayList();
		if ( checkEersteSteen() ) {
			for ( int i = 0; i < speelstukken.length; i++ ) {
				if ( speelstukken[i] != null ) {
					this.selectSpeelstuk( i );
					for ( int iy = 0; iy < 6; iy++ ) {
						for ( int ix = 0; ix < 6; ix++ ) {
							for ( int u = 0; u < 4; u++ ) {
								select.draaiRechts();
								if ( speelveld.checkZet( ix, iy ) ) {
									int[] temp = { i, ix, iy, u };
									zettenLijst.add( temp );
									puntenLijst.add( new Integer( speelveld.getZetPunten( ix, iy, spelerID ) ) );
								}
							}
						}
					}
				}
			}
			int hoogst = -10;
			for ( int i = 0; i < (Integer) puntenLijst.size(); i++ ) {
				if ( (Integer) puntenLijst.get( i ) > hoogst ) {
					hoogst = (Integer) puntenLijst.get( i );
				}
			}
			for ( int i = 0; i < (Integer) puntenLijst.size(); i++ ) {
				if ( (Integer) puntenLijst.get( i ) == hoogst ) {
					int[] temp = (int[]) zettenLijst.get( i );
					this.selectSpeelstuk( temp[0] );
					for ( int u = 0; u < (temp[3]+1); u++ ) {
						select.draaiRechts();
					}
					speelveld.selecteerVak( temp[1], temp[2] );
					hoogst = -10;
				}
			}
		} else {
			this.botZetEersteSteen();
		}
	}
	public void botZetEersteSteen() {
		this.selectSpeelstuk( 0 );
		int xPos = (int) ( Math.random() * 6 );
		int yPos = (int) ( Math.random() * 6 );
		if ( speelveld.checkZet( xPos, yPos ) ) {
			speelveld.selecteerVak( xPos, yPos );
		} else {
			this.botZetEersteSteen();
		}
	}
}
