import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OverDialog extends JDialog implements ActionListener
{
	private Speelbord speelbord;
	private JPanel tekstPanel, okPanel;
	private JLabel afbLabel;
	private JLabel[] tekstLabels;
	private JButton okKnop;
	
	public OverDialog( Speelbord speelbord ) {
		this.speelbord = speelbord;
		this.setSize( 300, 200 );
		this.setTitle( "Over Quads" );
		this.setResizable( false );
		this.setLocationRelativeTo( speelbord );
		this.setLayout( new BorderLayout() );
		tekstPanel = new JPanel();
		tekstPanel.setLayout( new BoxLayout( tekstPanel, BoxLayout.Y_AXIS ) );
		okPanel = new JPanel();
		afbLabel = new JLabel( new ImageIcon( "Qicon.gif" ) );
		afbLabel.setPreferredSize( new Dimension( 120, 200 ) );
		tekstLabels = new JLabel[6];
		tekstLabels[0] = new JLabel( "Quads v1.00" );
		tekstLabels[0].setFont( new Font( "Serif", Font.BOLD, 18 ) );
		tekstLabels[1] = new JLabel( "Gemaakt door:" );
		tekstLabels[1].setFont( new Font( "Serif", Font.BOLD, 16 ) );
		tekstLabels[2] = new JLabel( "Paul van Santen" );
		tekstLabels[3] = new JLabel( "Koen van Haasteren" );
		tekstLabels[4] = new JLabel( "Leon Bizo" );
		tekstLabels[5] = new JLabel( "Thijs van den Nouland" );
		okKnop = new JButton( "OK" );
		okKnop.addActionListener( this );
		for ( int i = 0; i < tekstLabels.length; i++ ) {
			tekstPanel.add( tekstLabels[i] );
			if ( i == 0 || i == 1 || i == 5 ) {
				tekstPanel.add( Box.createVerticalGlue() );
			}
		}
		okPanel.add( okKnop );
		this.add( afbLabel, BorderLayout.WEST );
		this.add( tekstPanel, BorderLayout.CENTER );
		this.add( okPanel, BorderLayout.SOUTH );
	}
	public void actionPerformed( ActionEvent e ) {
		this.setVisible( false );
	}
}
