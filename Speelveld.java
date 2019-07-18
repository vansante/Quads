import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Speelveld extends JPanel implements MouseWheelListener, Serializable
{
	private Speelbord speelbord;
	private Speelvak[][] speelveld;
	private SelectSpeelstuk[] selects;
	private Speler[] spelers;
	private JPanel mainPanel;
	private int beurt = 0, speelstukGrootte, currentxPos, currentyPos;
	
	public Speelveld() {
		this.addMouseWheelListener( this );
		mainPanel = new JPanel();
		mainPanel.setBackground( Color.black );
		mainPanel.setLayout( new GridLayout( 6, 6, 1, 1 ) );
		mainPanel.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
		speelveld = new Speelvak[6][6];
		for ( int iy = 0; iy < speelveld.length; iy++ ) {
			for ( int ix = 0; ix < speelveld[iy].length; ix++ ) {
				speelveld[iy][ix] = new Speelvak( ix, iy, this );
				mainPanel.add( speelveld[iy][ix] );
			}
		}
		this.add( mainPanel );
	}
	public void setData( Speelbord speelbord, SelectSpeelstuk[] selects, Speler[] spelers ) {
		this.speelbord = speelbord;
		this.selects = selects;
		this.spelers = spelers;
		if ( beurt == 0 ) {
			spelers[0].setAanBeurt( true );
			spelers[1].setAanBeurt( false );
		}
		this.setSelect( false );
	}
	public void dumpData() {
		speelbord = null;
		selects = null;
		spelers = null;
	}
	public void setGrootte( int speelstukGrootte ) {
		this.speelstukGrootte = speelstukGrootte;
		int speelveldGrootte = ( 6 * speelstukGrootte ) + 13;
		mainPanel.setPreferredSize( new Dimension( speelveldGrootte, speelveldGrootte ) );
		mainPanel.setMaximumSize( new Dimension( speelveldGrootte, speelveldGrootte ) );
		for ( int iy = 0; iy < speelveld.length; iy++ ) {
			for ( int ix = 0; ix < speelveld[iy].length; ix++ ) {
				speelveld[iy][ix].setGrootte( speelstukGrootte );
			}
		}
		this.revalidate();
		this.repaint();
	}
	public void setSelect( boolean select ) {
		for ( int iy = 0; iy < speelveld.length; iy++ ) {
			for ( int ix = 0; ix < speelveld[iy].length; ix++ ) {
				speelveld[iy][ix].setSelect( select );
			}
		}
	}
	public void wisselBeurt() {
		spelers[beurt].setAanBeurt( false );
		if ( beurt == 0 ) {
			beurt = 1;
		} else {
			beurt = 0;
		}
		spelers[beurt].setAanBeurt( true );
	}
	public void setSpeelstuk( Speelstuk speelstuk, int xPos, int yPos ) {
		if ( speelveld[yPos][xPos].getSpeelstukData() == null ) {
			speelveld[yPos][xPos].setSpeelstuk( speelstuk );
		}
	}
	public void selecteerVak( int xPos, int yPos ) {
		if ( selects[beurt].getSelect() )
		{
			if ( this.checkZet( xPos, yPos ) ) {
				this.setSpeelstuk( selects[beurt].getSpeelstuk(), xPos, yPos );
				spelers[beurt].verwijderSpeelstuk( selects[beurt].getSpeelstukID() );
				selects[beurt].reset();
				this.setSelect( false );
				if ( this.checkVerloren( beurt ) ) {
					JOptionPane.showMessageDialog( this, spelers[beurt].getSpelerNaam() + " heeft gewonnen.", "Spel Afgelopen", JOptionPane.INFORMATION_MESSAGE );
					speelbord.sluitSpel();
					speelbord.nieuwSpel();
				} else {
					this.wisselBeurt();
				}
			} else {
				speelveld[yPos][xPos].setFouteZet();
			}
		}
	}
	public boolean checkZet( int xPos, int yPos ) {
		this.currentxPos = xPos;
		this.currentyPos = yPos;
		if ( !speelveld[yPos][xPos].getBezet() ) {
			if ( selects[beurt].getSpeelstukID() == 0 ) {
				return goedeZet( xPos, yPos, selects[beurt].getSpeelstukData(), beurt, false );
			} else {
				return goedeZet( xPos, yPos, selects[beurt].getSpeelstukData(), beurt, true );
			}
		} else {
			return false;
		}
	}
	public boolean checkVerloren( int speler ) {
		if ( speler == 0 ) {
			speler = 1;
		} else {
			speler = 0;
		}
		Speelstuk[] speelstukken = spelers[speler].getSpeelstukken();
		for ( int i = 0; i < speelstukken.length; i++ ) {
			if ( speelstukken[i] != null && speelstukken[i].getSpeelstukID() != 0 ) {
				for ( int iy = 0; iy < speelveld.length; iy++ ) {
					for ( int ix = 0; ix < speelveld[iy].length; ix++ ) {
						if ( !speelveld[iy][ix].getBezet() ) {
							for ( int u = 0; u < 4; u++ ) {
								if ( this.goedeZet( ix, iy, speelstukken[i].getSpeelstukData(), speler, true ) ) {
									return false;
								}
								speelstukken[i].draaiRechts();
							}
						}
					}
				}
			}
		}
		return true;
	}
	public boolean goedeZet( int xPos, int yPos, int[] selectData, int speler, boolean checkRaak ) {
		boolean vakTop, vakRechts, vakBeneden, vakLinks;
		int[] vakTopData, vakRechtsData, vakBenedenData, vakLinksData;
		int raakVak = 0;
		vakTop = vakBezet( xPos, yPos-1 );
		vakRechts = vakBezet( xPos+1, yPos );
		vakBeneden = vakBezet( xPos, yPos+1 );
		vakLinks = vakBezet( xPos-1, yPos );
		vakTopData = getVakData( xPos, yPos-1 );
		vakRechtsData = getVakData( xPos+1, yPos );
		vakBenedenData = getVakData( xPos, yPos+1 );
		vakLinksData = getVakData( xPos-1, yPos );
		if ( vakTop ) {
			raakVak++;
			if ( !checkAangrenzendVak( selectData[0], vakTopData[2], yPos-1, xPos, speler ) ) {
				return false;
			}
		}
		if ( vakRechts ) {
			raakVak++;
			if( !checkAangrenzendVak( selectData[1], vakRechtsData[3], yPos, xPos+1, speler ) ) {
				return false;
			}
		}
		if ( vakBeneden ) {
			raakVak++;
			if ( !checkAangrenzendVak( selectData[2], vakBenedenData[0], yPos+1, xPos, speler ) ) {
				return false;
			}
		}
		if ( vakLinks ) {
			raakVak++;
			if ( !checkAangrenzendVak( selectData[3], vakLinksData[1], yPos, xPos-1, speler ) ) {
				return false;
			}
		}
		if ( raakVak < 1 && checkRaak ) {
			return false;
		} else if ( raakVak > 0 && !checkRaak ) {
			return false;
		} else {
			return true;
		}
	}
	public boolean vakBezet( int xPos, int yPos ) {
		if ( xPos >= 0 && xPos < 6 && yPos >= 0 && yPos < 6 && speelveld[yPos][xPos].getBezet() ) {
			return true;
		} else {
			return false;
		}
	}
	public boolean checkAangrenzendVak( int selectData, int vakData, int yPos, int xPos, int speler ) {
		if ( selectData != vakData ) {
			return false;
		} else if ( selectData == 1 && speler != speelveld[yPos][xPos].getSpeler() ) {
			return false;
		} else {
			return true;
		}
	}
	public int[] getVakData( int xPos, int yPos ) {
		if ( xPos < 0 || xPos > 5 ) {
			return null;
		} else if ( yPos < 0 || yPos > 5 ) {
			return null;
		} else {
			return speelveld[yPos][xPos].getSpeelstukData();
		}
	}
	public int getZetPunten( int xPos, int yPos, int spelerID ) {
		int[] speelstukData = selects[beurt].getSpeelstukData();
		int punten = 0;
		boolean vakTop, vakRechts, vakBeneden, vakLinks;
		int[] vakTopData, vakRechtsData, vakBenedenData, vakLinksData;
		if ( spelerID == 0 ) { int spelerID2 = 1; } else { int spelerID2 = 0; }
		vakTop = vakBezet( xPos, yPos-1 );
		vakRechts = vakBezet( xPos+1, yPos );
		vakBeneden = vakBezet( xPos, yPos+1 );
		vakLinks = vakBezet( xPos-1, yPos );
		if ( speelstukData[0] == 1 ) {
			if ( xPos >= 0 && xPos < 6 && yPos-1 >= 0 && yPos-1 < 6 ) {
				if ( !speelveld[yPos-1][xPos].getBezet() ) {
					punten += this.getVakPunten( xPos, yPos-1, spelerID );
				} else {
					punten -= 1;
				}
			} else {
				punten -=  2;
			}
		}
		if ( speelstukData[1] == 1 ) {
			if ( xPos+1 >= 0 && xPos+1 < 6 && yPos >= 0 && yPos < 6 ) {
				if ( !speelveld[yPos][xPos+1].getBezet() ) {
					punten += this.getVakPunten( xPos+1, yPos, spelerID );
				} else {
					punten -= 1;
				}
			} else {
				punten -=  2;
			}
		}
		if ( speelstukData[2] == 1 ) {
			if ( xPos >= 0 && xPos < 6 && yPos+1 >= 0 && yPos+1 < 6 ) {
				if ( !speelveld[yPos+1][xPos].getBezet() ) {
					punten += this.getVakPunten( xPos, yPos+1, spelerID );
				} else {
					punten -= 1;
				}
			} else {
				punten -=  2;
			}
		}
		if ( speelstukData[3] == 1 ) {
			if ( xPos-1 >= 0 && xPos-1 < 6 && yPos >= 0 && yPos < 6 ) {
				if ( !speelveld[yPos][xPos-1].getBezet() ) {
					punten += this.getVakPunten( xPos-1, yPos, spelerID );
				} else {
					punten -= 1;
				}
			} else {
				punten -=  2;
			}
		}
		return punten;
	}
	public int getVakPunten( int xPos, int yPos, int spelerID ) {
		int eigenSpeler = 0, tegenSpeler = 0;
		if ( this.getDeelVakPunten( 2, xPos, yPos-1, spelerID ) == 0 ) {
			eigenSpeler++;
		} else if ( this.getDeelVakPunten( 2, xPos, yPos-1, spelerID ) == 1 ) {
			tegenSpeler++;
		}
		if ( this.getDeelVakPunten( 3, xPos+1, yPos, spelerID ) == 0 ) {
			eigenSpeler++;
		} else if ( this.getDeelVakPunten( 3, xPos+1, yPos, spelerID ) == 1 ) {
			tegenSpeler++;
		}
		if ( this.getDeelVakPunten( 0, xPos, yPos+1, spelerID ) == 0 ) {
			eigenSpeler++;
		} else if ( this.getDeelVakPunten( 0, xPos, yPos+1, spelerID ) == 1 ) {
			tegenSpeler++;
		}
		if ( this.getDeelVakPunten( 1, xPos-1, yPos, spelerID ) == 0 ) {
			eigenSpeler++;
		} else if ( this.getDeelVakPunten( 1, xPos-1, yPos, spelerID ) == 1 ) {
			tegenSpeler++;
		}
		if ( eigenSpeler > 0 && tegenSpeler > 0 ) {
			return 0;
		} else if ( eigenSpeler == 0 && tegenSpeler > 0 ) {
			return 2;
		} else {
			return 4;
		}
	}
	public int getDeelVakPunten( int positie, int xPos, int yPos, int spelerID ) {
		if ( this.vakBezet( xPos, yPos ) ) {
			if ( speelveld[yPos][xPos].getBezet() ) {
				if ( speelveld[yPos][xPos].getSpeelstukData()[positie] == 1 ) {
					if ( speelveld[yPos][xPos].getSpeler() == spelerID ) {
						return 0;
					} else {
						return 1;
					}
				}
			} else {
				return 2;
			}
		}
		return -1;
	}
	public void mouseWheelMoved( MouseWheelEvent e ) {
		if ( selects[beurt].getSpeelstukID() != -1 ) {
			if ( e.getWheelRotation() > 0 ) {
				selects[beurt].draaiLinks();
			} else {
				selects[beurt].draaiRechts();
			}
			if ( this.checkZet( currentxPos, currentyPos ) ) {
				speelveld[currentyPos][currentxPos].muisHover();
			} else {
				speelveld[currentyPos][currentxPos].resetHover();
			}
		}
	}
}
