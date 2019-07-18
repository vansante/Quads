import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class HelpDialog extends JDialog implements ActionListener
{
	private Speelbord speelbord;
	private JPanel panels;
	private JTextArea helpVak;
	private JScrollPane scroll;
	private JButton okKnop;
	private String helpTekst;
	
	public HelpDialog( Speelbord speelbord ) {
		this.speelbord = speelbord;
		this.setSize( 600, 500 );
		this.setTitle( "Quads - Help" );
		this.setResizable( false );
		this.setLocationRelativeTo( speelbord );
		this.setLayout( new FlowLayout() );
		helpTekst = "";
		try {
			BufferedReader helpin = new BufferedReader( new FileReader( "./help.txt" ) );
			String regel;
			while ( ( regel = helpin.readLine() ) != null )
			{
				helpTekst += regel + "\n";
			}
			helpin.close();
		}
		catch ( Exception error ) {}
		helpVak = new JTextArea( helpTekst );
		helpVak.setEditable( false );
		helpVak.setLineWrap( true );
		helpVak.setWrapStyleWord( true );
		helpVak.setColumns( 50 );
		helpVak.setRows( 22 );
		helpVak.setFont( new Font( "Serif", Font.PLAIN, 14 ) );
		scroll = new JScrollPane(helpVak);
		scroll.setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
		this.add( scroll );
		okKnop = new JButton( "OK" );
		okKnop.addActionListener( this );
		this.add( okKnop );
	}
	public void actionPerformed( ActionEvent e ) {
		this.setVisible( false );
	}
}
