import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;

public class QuadsApplet extends Applet implements ActionListener
{
	private Speelbord speelbord;
	private JPanel knopPanel;
	private JButton nieuwKnop, helpKnop, overKnop;
	private String helpTekst;
	
	public void init() {
		speelbord = new Speelbord( false );
		speelbord.setMinimumSize( new Dimension( 700, 600 ) );
		speelbord.setPreferredSize( new Dimension( 700, 600 ) );
		speelbord.nieuwSpel();
		speelbord.setGrootten();
		speelbord.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
		knopPanel = new JPanel( new FlowLayout() );
		knopPanel.setPreferredSize( new Dimension( 700, 38 ) );
		knopPanel.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
		nieuwKnop = new JButton( "Nieuw Spel" );
		nieuwKnop.addActionListener( this );
		nieuwKnop.setBackground( Color.white );
		nieuwKnop.setForeground( Color.black );
		helpKnop = new JButton( "Help" );
		helpKnop.addActionListener( this );
		helpKnop.setBackground( Color.white );
		helpKnop.setForeground( Color.black );
		overKnop = new JButton( "Over" );
		overKnop.addActionListener( this );
		overKnop.setBackground( Color.white );
		overKnop.setForeground( Color.black );
		knopPanel.add( nieuwKnop );
		knopPanel.add( helpKnop );
		knopPanel.add( overKnop );
		this.add( knopPanel );
		this.add( speelbord );
		helpTekst = 	"Beide spelers beschikken over een set van 17 stukken. " +
				"Elke set is in de eerste plaats drager van een kleur " +
				"\n(rood of blauw), en in de tweede plaats van 17 verschillende " +
				"combinaties van die kleur met horizontale en/of verticale " +
				"lijnen.\nDaarnaast hebben de spelers ook elk één neutraal stuk. " +
				"\n\nOm het spel te beginnen, plaatsen de spelers eerst hun neutraal" +
				"stuk willekeurig op het bord. Vervolgens moeten zij om de beurt" +
				"\neen stuk aanleggen. \n\nDe voorwaarden zijn beproefd: elk stuk moet " +
				"minstens één ander stuk raken en de elkaar rakende zijden moeten " +
				"qua kleur of\nqua lijn bij elkaar passen. \n\nDe bedoeling is om velden " +
				"voor de tegenspeler te blokkeren op plaatsen waar je zelf nog kunt " +
				"spelen. \n\nMet andere woorden, enerzijds moet je trachten velden voor " +
				"jezelf te reserveren, anderzijds moet je de juiste stukken achter " +
				"\nde hand houden om op het einde van het spel nog aan te kunnen " +
				"leggen op plaatsen waar je tegenspeler dat niet meer kan. " +
				"\n\nDe speler die op het bord een situatie weet te creëren die de " +
				"tegenspeler volledig blokkeert, is de winnaar.\n\n";
	}
	public void actionPerformed( ActionEvent e ) {
		if ( e.getSource() == nieuwKnop ) {
			speelbord.sluitSpel();
			speelbord.nieuwSpel();
			this.validate();
		} else if ( e.getSource() == helpKnop ) {
			JOptionPane.showMessageDialog( speelbord, helpTekst, "Help", JOptionPane.INFORMATION_MESSAGE );
		} else if ( e.getSource() == overKnop ) {
			JOptionPane.showMessageDialog( this, "Quads v1.00\n---\nGemaakt Door:\nProjectgroep 1G\nHogeschool Leiden", "Over Quads", JOptionPane.INFORMATION_MESSAGE );
		}
	}
}
