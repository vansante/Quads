import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Quads extends JFrame implements ActionListener, WindowListener, ComponentListener
{
	private JMenuBar menu;
	private JMenu menuBestand, menuSpelerKleur, menuHelp, menuOver;
	private JMenuItem mItemAfsluiten, mItemOpslaan, mItemLaden, mItemSluiten, mItemNieuw, mItemHelp, mItemOver;
	private Speelbord speelbord;
	private JFileChooser opslaanFS, ladenFS;
	private String helptekst;
	
	public Quads() {
		this.setSize( 850, 700 );
		this.setTitle( "Quads v1.00" );
		this.setResizable( true );
		this.addWindowListener( this );
		this.addComponentListener( this );
		this.setIconImage( Toolkit.getDefaultToolkit().getImage("./Qicon.gif") );
		this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		menu = new JMenuBar();
			menuBestand = new JMenu( "Bestand" );
			menuBestand.setMnemonic( KeyEvent.VK_B );
				mItemNieuw = new JMenuItem( "Nieuw Spel", KeyEvent.VK_N );
				mItemNieuw.addActionListener( this );
				mItemNieuw.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK) );
				mItemOpslaan = new JMenuItem( "Spel Opslaan", KeyEvent.VK_S );
				mItemOpslaan.addActionListener( this );
				mItemOpslaan.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK) );
				mItemLaden = new JMenuItem( "Spel Laden", KeyEvent.VK_L );
				mItemLaden.addActionListener( this );
				mItemLaden.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK) );
				mItemSluiten = new JMenuItem( "Spel Sluiten", KeyEvent.VK_C );
				mItemSluiten.addActionListener( this );
				mItemSluiten.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK) );
				mItemAfsluiten = new JMenuItem( "Afsluiten", KeyEvent.VK_Q );
				mItemAfsluiten.addActionListener( this );
				mItemAfsluiten.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK) );
			menuBestand.add( mItemNieuw );
			menuBestand.add( mItemOpslaan );
			menuBestand.add( mItemLaden );
			menuBestand.add( mItemSluiten );
			menuBestand.addSeparator();
			menuBestand.add( mItemAfsluiten );
			menuHelp = new JMenu( "Help" );
			menuHelp.setMnemonic( KeyEvent.VK_H );
				mItemHelp = new JMenuItem( "Help" );
				mItemHelp.addActionListener( this );
				mItemHelp.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK) );
			menuHelp.add( mItemHelp );
			menuOver = new JMenu( "Over" );
				mItemOver = new JMenuItem( "Over" );
				mItemOver.addActionListener( this );
				mItemOver.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK) );
			menuOver.setMnemonic( KeyEvent.VK_O );
			menuOver.addActionListener( this );
			menuOver.add( mItemOver );
		menu.add( menuBestand );
		menu.add( menuHelp );
		menu.add( menuOver );
		
		opslaanFS = new JFileChooser();
		opslaanFS.setDialogTitle( "Quads Spel Opslaan" );
		opslaanFS.addChoosableFileFilter( new BestandFilter() );
		ladenFS = new JFileChooser();
		ladenFS.setDialogTitle( "Quads Spel Laden" );
		ladenFS.addChoosableFileFilter( new BestandFilter() );
		speelbord = new Speelbord( true );
		this.getContentPane().add( speelbord );
		this.setJMenuBar( menu );
		this.centerScherm();
		this.setVisible( true );
	}
	public static void main( String[] args ) {
		Quads Quads = new Quads();
	}
	public void saveSpel() {
		if ( speelbord.spelActief() ) {
			if ( opslaanFS.showSaveDialog( this ) == JFileChooser.APPROVE_OPTION ) {
				String bestandsnaam = opslaanFS.getSelectedFile().getName() + ".qsf";
				String mapnaam = opslaanFS.getCurrentDirectory().getAbsolutePath() + "/";
				speelbord.saveSpel( mapnaam, bestandsnaam );
			}
		}
	}
	public void laadSpel() {
		if ( !speelbord.spelActief() ) {
			if ( ladenFS.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
				String bestandsnaam = ladenFS.getSelectedFile().getName();
				String mapnaam = ladenFS.getCurrentDirectory().getAbsolutePath() + "/";
				speelbord.laadSpel( mapnaam, bestandsnaam );
			}
		}
	}
	public void applicatieAfsluiten() {
		if ( speelbord.spelActief() ) {
			int resultaat = JOptionPane.showConfirmDialog( this, "Er is een spel bezig, wilt u deze opslaan?", "Spel bezig", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE );
			if ( resultaat == JOptionPane.YES_OPTION ) {
				this.saveSpel();
				speelbord.sluitSpel();
				this.dispose();
				System.exit( 0 );
			} else if ( resultaat == JOptionPane.NO_OPTION ) {
				speelbord.sluitSpel();
				this.dispose();
				System.exit( 0 );
			}
		} else {
			this.dispose();
			System.exit( 0 );
		}
	}
	public void actionPerformed( ActionEvent e ) {
		Object event = e.getSource();
		if ( event == mItemAfsluiten ) {
			this.applicatieAfsluiten();
		} else if ( event == mItemNieuw ) {
			if ( speelbord.spelActief() ) {
				int resultaat = JOptionPane.showConfirmDialog( this, "Er is een spel bezig, wilt u deze opslaan?", "Spel bezig", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE );
				if ( resultaat == JOptionPane.YES_OPTION ) {
					this.saveSpel();
					speelbord.sluitSpel();
					speelbord.nieuwSpel();
				} else if ( resultaat == JOptionPane.NO_OPTION ) {
					speelbord.sluitSpel();
					speelbord.nieuwSpel();
				}
			} else {
				speelbord.nieuwSpel();
			}
		} else if ( event == mItemOpslaan ) {
			this.saveSpel();
		} else if ( event == mItemLaden ) {
			if ( speelbord.spelActief() ) {
				int resultaat = JOptionPane.showConfirmDialog( this, "Er is een spel bezig, wilt u deze opslaan?", "Spel bezig", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE );
				if ( resultaat == JOptionPane.YES_OPTION ) {
					this.saveSpel();
					speelbord.sluitSpel();
					this.laadSpel();
				} else if ( resultaat == JOptionPane.NO_OPTION ) {
					speelbord.sluitSpel();
					this.laadSpel();
				}
			} else {
				this.laadSpel();
			}
		} else if ( event == mItemSluiten ) {
			if ( speelbord.spelActief() ) {
				int resultaat = JOptionPane.showConfirmDialog( this, "Er is een spel bezig, wilt u deze opslaan?", "Spel bezig", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE );
				if ( resultaat == JOptionPane.YES_OPTION ) {
					this.saveSpel();
					speelbord.sluitSpel();
				} else if ( resultaat == JOptionPane.NO_OPTION ) {
					speelbord.sluitSpel();
				}
			}
		} else if ( event == mItemHelp ) {
			speelbord.showHelp();
		} else if ( event == mItemOver ) {
			speelbord.showOver();
		}
	}
	public void centerScherm() {
		Dimension scherm = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation( ( scherm.width - getSize().width ) / 2, ( scherm.height - getSize().height ) / 2 );
	}
	public void windowClosing( WindowEvent e ) {
		this.applicatieAfsluiten();
	}
	public void windowIconified( WindowEvent e ) {}
	public void windowOpened( WindowEvent e ) {}
	public void windowClosed( WindowEvent e ) {}
	public void windowDeiconified( WindowEvent e ) {}
	public void windowActivated( WindowEvent e ) {}
	public void windowDeactivated( WindowEvent e ) {}
	public void componentHidden( ComponentEvent e ) {}
	public void componentMoved( ComponentEvent e ) {}
	public void componentResized( ComponentEvent e ) {
		this.setSize( Math.max( 500, this.getWidth() ), Math.max( 460, this.getHeight() ) );
	}
	public void componentShown( ComponentEvent e ) {}
	public class BestandFilter extends javax.swing.filechooser.FileFilter {
		public boolean accept( File bestand ) {
			String filename = bestand.getName();
			return filename.endsWith(".qsf");
		}
		public String getDescription() {
			return "*.qsf (Quads Save File)";
		}
	}
}
