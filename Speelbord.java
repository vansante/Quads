import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import javax.swing.*;

public class Speelbord extends JPanel implements ComponentListener
{
	private Speler[] spelers;
	private Speelveld speelveld;
	private SelectSpeelstuk[] selects;
	private JPanel midPanel;
	private NwSpelDialog nwSpel;
	private HelpDialog helpDialog;
	private OverDialog overDialog;
	private JTextField[] naamVelden;
	private boolean spelActief = false, applicatie;
	
	public Speelbord( boolean applicatie ) {
		this.applicatie = applicatie;
		this.addComponentListener( this );
	}
	public void showHelp() {
		helpDialog = new HelpDialog( this );
		helpDialog.setVisible( true );
	}
	public void showOver() {
		overDialog = new OverDialog( this );
		overDialog.setVisible( true );
	}
	public void addComponenten() {
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		midPanel = new JPanel();
		midPanel.setLayout( new BoxLayout( midPanel, BoxLayout.X_AXIS ) );
		midPanel.add( Box.createHorizontalGlue() );
		midPanel.add( Box.createHorizontalGlue() );
		midPanel.add( selects[0] );
		midPanel.add( Box.createHorizontalGlue() );
		midPanel.add( speelveld );
		midPanel.add( Box.createHorizontalGlue() );
		midPanel.add( selects[1] );
		midPanel.add( Box.createHorizontalGlue() );
		midPanel.add( Box.createHorizontalGlue() );
		this.add( Box.createVerticalGlue() );
		this.add( Box.createVerticalGlue() );
		this.add( spelers[0] );
		this.add( Box.createVerticalGlue() );
		this.add( midPanel );
		this.add( Box.createVerticalGlue() );
		this.add( spelers[1] );
		this.add( Box.createVerticalGlue() );
		this.add( Box.createVerticalGlue() );
		this.setGrootten();
		midPanel.repaint();
		this.repaint();
	}
	public void setData() {
		spelers[0].setData( speelveld, selects[0] );
		spelers[1].setData( speelveld, selects[1] );
		speelveld.setData( this, selects, spelers );
		selects[0].setData( speelveld );
		selects[1].setData( speelveld );
	}
	public boolean spelActief() {
		return spelActief;
	}
	public void sluitSpel() {
		if ( spelActief ) {
			midPanel.removeAll();
			this.removeAll();
			selects = null;
			spelers = null;
			speelveld = null;
			midPanel = null;
			this.revalidate();
			this.repaint();
			spelActief = false;
		}
	}
	public void nieuwSpel() {
		if ( !spelActief ) {
			if ( applicatie ) {
				nwSpel = new NwSpelDialog( this );
				nwSpel.setVisible( true );
			} else { 
				this.maakSpel();
			}
		}
	}
	public void maakSpel() {
		if ( !spelActief ) {
			selects = new SelectSpeelstuk[2];
			selects[0] = new SelectSpeelstuk();
			selects[1] = new SelectSpeelstuk();
			spelers = new Speler[2];
			if ( applicatie ) {
				spelers[0] = new Speler( false, 0, nwSpel.getKleuren()[0], nwSpel.getNamen()[0] );
				spelers[1] = new Speler( nwSpel.getBotSpeler(), 1, nwSpel.getKleuren()[1], nwSpel.getNamen()[1] );
			} else {
				spelers[0] = new Speler( false, 0, Color.red, "Speler 1" );
				spelers[1] = new Speler( true, 1, Color.blue, "Computer" );
			}
			speelveld = new Speelveld();
			nwSpel = null;
			this.setData();
			spelActief = true;
			this.addComponenten();
		}
	}
	public void saveSpel( String mapnaam, String bestandsnaam ) {
		if ( spelActief && applicatie ) {
			speelveld.dumpData();
			spelers[0].dumpData();
			spelers[1].dumpData();
			try {
				ObjectOutputStream uit = new ObjectOutputStream( new FileOutputStream( mapnaam + bestandsnaam ) );
				uit.writeObject( speelveld );
				uit.writeObject( spelers );
				uit.close();
			} catch ( IOException error ) {
				JOptionPane.showMessageDialog( this, "IO Exceptie.\nKon het bestand niet wegschrijven.", "Fout", JOptionPane.ERROR_MESSAGE );
				//error.printStackTrace();
			}
			this.setData();
		}
	}
	public void laadSpel( String mapnaam, String bestandsnaam ) {
		if ( !spelActief && applicatie ) {
			boolean noError;
			try {
				ObjectInputStream in = new ObjectInputStream( new FileInputStream( mapnaam + bestandsnaam ) );
				try {
					speelveld = (Speelveld) in.readObject();
					spelers = (Speler[]) in.readObject();
					noError = true;
				}
				catch ( ClassNotFoundException c )
				{
					JOptionPane.showMessageDialog( this, "Onbekend bestandsformaat.\nKon het bestand niet laden.", "Fout", JOptionPane.ERROR_MESSAGE );
					noError = false;
				}
				in.close();
			}
			catch ( IOException error )
			{
				JOptionPane.showMessageDialog( this, "IO Exceptie.\nKon het bestand niet laden.", "Fout", JOptionPane.ERROR_MESSAGE );
				//error.printStackTrace();
				noError = false;
			}
			if ( noError ) {
				selects = new SelectSpeelstuk[2];
				selects[0] = new SelectSpeelstuk();
				selects[1] = new SelectSpeelstuk();
				this.setData();
				spelActief = true;
				this.addComponenten();
			} else {
				speelveld = null;
				spelers = null;
			}
		}
	}
	public void setGrootten() {
		if ( spelActief ) {
			double hoogte = this.getHeight();
			int speelstukGrootte = (int) Math.floor( hoogte / 96 ) * 8;
			speelveld.setGrootte( speelstukGrootte );
			spelers[0].setGrootte( speelstukGrootte );
			spelers[1].setGrootte( speelstukGrootte );
			selects[0].setGrootte( speelstukGrootte );
			selects[1].setGrootte( speelstukGrootte );
		}
	}
	public void componentHidden( ComponentEvent e ) {}
	public void componentMoved( ComponentEvent e ) {}
	public void componentResized( ComponentEvent e ) {
		this.setGrootten();
	}
	public void componentShown( ComponentEvent e ) {}
}
