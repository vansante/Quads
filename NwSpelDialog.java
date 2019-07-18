import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NwSpelDialog extends JDialog implements ActionListener
{
	private Speelbord speelbord;
	private JPanel[] panels;
	private JLabel[] labels;
	private JTextField[] naamVelden;
	private JComboBox[] kleurBoxen;
	private JCheckBox botSpeler;
	private JButton okKnop;
	private String[] namen, kleurenOpties;
	private Color[] kleuren;
	
	public NwSpelDialog( Speelbord speelbord ) {
		this.speelbord = speelbord;
		this.setSize( 300, 175 );
		this.setTitle( "Quads - Nieuw Spel" );
		this.setResizable( false );
		this.setLocationRelativeTo( speelbord );
		this.setLayout( new FlowLayout() );
		kleurenOpties = new String[] { "Blauw","Rood","Groen","Oranje","Rose","Grijs","Zwart" };
		kleuren = new Color[2];
		panels = new JPanel[2];
		labels = new JLabel[2];
		naamVelden = new JTextField[2];
		kleurBoxen = new JComboBox[2];
		for ( int i = 0; i < 2; i++ ) {
			panels[i] = new JPanel();
			panels[i].setLayout( new FlowLayout() );
			labels[i] = new JLabel( "Speler "+(i+1)+":" );
			naamVelden[i] = new JTextField( "Speler "+(i+1), 10 );
			naamVelden[i].addActionListener( this );
			kleurBoxen[i] = new JComboBox();
			for ( int u = 0; u < kleurenOpties.length; u++ ) {
				kleurBoxen[i].addItem( kleurenOpties[u] );
			}
			panels[i].add( labels[i] );
			panels[i].add( naamVelden[i] );
			panels[i].add( kleurBoxen[i] );
			this.add( panels[i] );
		}
		botSpeler = new JCheckBox( "Computerspeler", false );
		botSpeler.setPreferredSize( new Dimension( 280, 20 ) );
		botSpeler.addActionListener( this );
		this.add( botSpeler );
		kleurBoxen[1].setSelectedIndex( 1 );
		okKnop = new JButton( "OK" );
		okKnop.addActionListener( this );
		this.add( okKnop );
	}
	public String[] getNamen() {
		String speler1, speler2;
		speler1 = naamVelden[0].getText();
		speler2 = naamVelden[1].getText();
		if ( speler1.equalsIgnoreCase( speler2 ) ) {
			speler1 = "Speler 1";
			speler2 = "Speler 2";
		}
		if ( speler1.equalsIgnoreCase( "computer" ) && botSpeler.getSelectedObjects() != null ) {
			speler1 = "Speler 1";
		}
		return new String[] { speler1, speler2 };
	}
	public Color[] getKleuren() {
		for ( int i = 0; i < 2; i++ ) {
			if ( kleurBoxen[i].getSelectedItem() == "Blauw" ) {
				kleuren[i] = Color.blue;
			} else if ( kleurBoxen[i].getSelectedItem() == "Rood" ) {
				kleuren[i] = Color.red;
			} else if ( kleurBoxen[i].getSelectedItem() == "Groen" ) {
				kleuren[i] = Color.green;
			} else if ( kleurBoxen[i].getSelectedItem() == "Oranje" ) {
				kleuren[i] = Color.orange;
			} else if ( kleurBoxen[i].getSelectedItem() == "Rose" ) {
				kleuren[i] = Color.pink;
			} else if ( kleurBoxen[i].getSelectedItem() == "Grijs" ) {
				kleuren[i] = Color.gray;
			} else if ( kleurBoxen[i].getSelectedItem() == "Zwart" ) {
				kleuren[i] = Color.black;
			}
		}
		if ( kleuren[0] == kleuren[1] ) {
			kleuren[0] = Color.red;
			kleuren[1] = Color.blue;
		}
		return kleuren;
	}
	public boolean getBotSpeler() {
		if ( botSpeler.getSelectedObjects() == null ) {
			return false;
		} else {
			return true;
		}
	}
	public void actionPerformed( ActionEvent e ) {
		Object bron = e.getSource();
		if ( bron == okKnop || bron == naamVelden[0] || bron == naamVelden[1] ) {
			this.setVisible( false );
			speelbord.maakSpel();
		} else if ( bron == botSpeler ) {
			if ( botSpeler.getSelectedObjects() == null ) {
				naamVelden[1].setEditable( true );
				kleurBoxen[1].setEnabled( true );
				kleurBoxen[0].addItem( kleurenOpties[6] );
				kleurBoxen[1].setSelectedIndex( 1 );
				naamVelden[1].setText( "Speler 2" );
			} else {
				naamVelden[1].setEditable( false );
				kleurBoxen[1].setEnabled( false );
				kleurBoxen[0].removeItemAt( 6 );
				kleurBoxen[1].setSelectedIndex( 6 );
				naamVelden[1].setText( "Computer" );
			}
		}
	}
}
