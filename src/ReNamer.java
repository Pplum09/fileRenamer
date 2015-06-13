/**
 * Name: Isara Ritthaworn
 * Date: 1/28/2015
 * 
 * Program Description: This program is used to rename file names to a user selected name
 * 
 * Potential Bug List:
 * 
 * 		- what if someone presses the run button multiple times without
 * 		  things being set
 * 		- while the program is running it's name change, don't let any of
 * 		  the other buttons work
 * 
 * Version History:
 *
 * Version 0.15 1/28/2015
 *  - 2 JScrollPane used to display filenames was replaced with 1
 *    JXTable
 *  - fixed check box and text field alignment in panel 2
 *  - removed messages box, will probably add back in 
 *  - added preview button
 *  
 * Version 0.1 1/13/2015
 *  - basic UI done
 *  - buttons added
 *  - check boxes added
 *  - text boxes added
 *  - 2 JScrollPane used to display filenames
 */
//import org.jdesktop.swingx.JXTable; // used for jxtable

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel; // used for table

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Files; // these 3 used for file renaming methods only
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReNamer extends JFrame {

	// SYSTEM COMPONENTS
	private File inputDirectory;
	
	private static JFileChooser fileChooser;
	
	// GUI COMPONENTS (visible interface)
	
	// check boxes
	private static JCheckBox RecordGroupNum;
	private static JCheckBox SeriesDesignator;
	private static JCheckBox ReplaceSpecific;
	private static JCheckBox ReplaceSpaces;
	private static JCheckBox InsertFolderName;
	
	// buttons
	private static JButton Browse;
	private static JButton Run;
	private static JButton Cancel;
	private static JButton Preview;
	
	// text box
	private static JTextArea JTABrowseBox;
	private static JTextArea RecordGroupNumText;
	private static JTextArea SeriesDesignatorText;
	private static JTextArea ReplaceSpecificText;
	private static JTextArea JTAMessageWindow; // DELETE MIGHT GO UN USED
	private static JTextArea NamePreview;
	
	// jxtable
	//private static JXTable JXTFileTable;
	
	// Default Table Model
	private DefaultTableModel FileSummary;
	
	// ReNamer default constructor
	public ReNamer()
	{
		initSystemComponents();
		initGUIComponents();
	}
	
	private void initSystemComponents()
	{
		fileChooser = null; // why do this before you create the object
		
		// settings for fileChooser
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
	}
	private void initGUIComponents()
	{
		// check boxes
		RecordGroupNum = new JCheckBox();
		SeriesDesignator = new JCheckBox();
		ReplaceSpecific = new JCheckBox();
		ReplaceSpaces = new JCheckBox();
		InsertFolderName = new JCheckBox();
		
		// buttons
		Browse = new JButton();
		Run = new JButton();
		Cancel = new JButton();
		Preview = new JButton();
		
		// text box
		JTABrowseBox = new JTextArea();
		RecordGroupNumText = new JTextArea();
		SeriesDesignatorText = new JTextArea();
		ReplaceSpecificText = new JTextArea();
		JTAMessageWindow = new JTextArea();
		NamePreview = new JTextArea();
		
		// default table model used to label jxtable
		FileSummary = new DefaultTableModel();
		FileSummary.addColumn("Current Filename");
		FileSummary.addColumn("New FileName");
		FileSummary.addColumn("Results");
		
		// jxtable
		//JXTFileTable = new JXTable(FileSummary) {
		//	public boolean isCellEditable(int row, int column) {
		//		return false;
		//	}
		//};
		
		RecordGroupNum.setText(" Record Group Number: ");
		
		SeriesDesignator.setText(" Series Designator:         ");
		SeriesDesignator.setToolTipText("Enter series designator");
		
		ReplaceSpecific.setText(" Replace Specific:           ");
		ReplaceSpecific.setToolTipText("Enter character to replace");
		
		ReplaceSpaces.setText(" Replace Spaces ");
		ReplaceSpaces.setToolTipText("Replaces \"  \" with \"_\"");
		
		InsertFolderName.setText(" Insert Folder Name ");
		InsertFolderName.setToolTipText("Prepends current folder name to the beginning");
		
		Browse.setText("Browse");
		Browse.setToolTipText("Select folder containing files");
		
		Run.setText("  Run  ");
		Run.setToolTipText("Click to change file names");
		
		Cancel.setText("Cancel");
		Cancel.setToolTipText("Click to cancel operation");
		
		Preview.setText("Preview");
		Preview.setToolTipText("Click to preview file name");
		
		JTABrowseBox.setEditable(false);
		JTABrowseBox.setMargin(new Insets(5, 5, 5, 5));
		JTABrowseBox.setToolTipText("Folder path");
		
		RecordGroupNumText.setEditable(true);
		RecordGroupNumText.setMargin(new Insets(5, 5, 5, 5));
		RecordGroupNumText.setToolTipText("Enter record group number");
		
		SeriesDesignatorText.setEditable(true);
		SeriesDesignatorText.setMargin(new Insets(5, 5, 5, 5));
		SeriesDesignatorText.setToolTipText("Enter series designator number");
		
		ReplaceSpecificText.setEditable(true);
		ReplaceSpecificText.setMargin(new Insets(5, 5, 5, 5));
		ReplaceSpecificText.setToolTipText("Enter character to replace");
		
		JTAMessageWindow.setEditable(false);
		JTAMessageWindow.setToolTipText("Messages display here");
		
		NamePreview.setEditable(false);
		NamePreview.setMargin(new Insets(5, 5, 5, 5));
		NamePreview.setToolTipText("Preview of name");
		
		// setting for JXTable
		//JXTFileTable.setToolTipText("Displays current filename and new filename");
		//JXTFileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//JXTFileTable.setVisibleRowCount(30);
		//JXTFileTable.setVisibleColumnCount(8);
		//JXTFileTable.setHorizontalScrollEnabled(true);
		//JXTFileTable.setColumnControlVisible(true);
		
		
		// PANELS
		
		//############################## ROW 1 PANELS ###########################################################//
		JPanel JPRow1Sub1SourceFolderBrowse = new JPanel();
		JPRow1Sub1SourceFolderBrowse.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPRow1Sub1SourceFolderBrowse.setLayout(new BoxLayout(JPRow1Sub1SourceFolderBrowse, BoxLayout.LINE_AXIS));
		JPRow1Sub1SourceFolderBrowse.setPreferredSize(new Dimension(90, 0));
		JPRow1Sub1SourceFolderBrowse.add(Browse);
		
		JPanel JPRow1Sub1SourceFolderText = new JPanel();
		JPRow1Sub1SourceFolderText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow1Sub1SourceFolderText.setLayout(new BoxLayout(JPRow1Sub1SourceFolderText, BoxLayout.PAGE_AXIS));
		JPRow1Sub1SourceFolderText.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow1Sub1SourceFolderText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow1Sub1SourceFolderText.add(JTABrowseBox);
		
		JPanel JPRow1Sub1SourceFolder = new JPanel();
		JPRow1Sub1SourceFolder.setBorder(BorderFactory.createTitledBorder(" Source Folder "));
		JPRow1Sub1SourceFolder.setLayout(new BoxLayout(JPRow1Sub1SourceFolder, BoxLayout.LINE_AXIS));
		JPRow1Sub1SourceFolder.add(JPRow1Sub1SourceFolderBrowse);
		JPRow1Sub1SourceFolder.add(JPRow1Sub1SourceFolderText);
		
		JPanel JPRow1 = new JPanel();
		JPRow1.setLayout(new BoxLayout(JPRow1, BoxLayout.LINE_AXIS));
		JPRow1.add(JPRow1Sub1SourceFolder);
		
		//############################## ROW 2 PANELS ###########################################################//
		// used only for formatting
		JPanel JPROW2InsertFolderNameRigid = new JPanel();
		JPROW2InsertFolderNameRigid.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPROW2InsertFolderNameRigid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		
		JPanel JPRow2InsertFolderName = new JPanel();
		JPRow2InsertFolderName.setLayout(new BoxLayout(JPRow2InsertFolderName, BoxLayout.LINE_AXIS));
		JPRow2InsertFolderName.add(InsertFolderName);
		JPRow2InsertFolderName.add(JPROW2InsertFolderNameRigid);
		
		// used only for formatting
		JPanel JPROW2ReplaceSpacesRigid = new JPanel();
		JPROW2ReplaceSpacesRigid.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPROW2ReplaceSpacesRigid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		
		JPanel JPRow2ReplaceSpaces = new JPanel();
		JPRow2ReplaceSpaces.setLayout(new BoxLayout(JPRow2ReplaceSpaces, BoxLayout.LINE_AXIS));
		JPRow2ReplaceSpaces.add(ReplaceSpaces);
		JPRow2ReplaceSpaces.add(JPROW2ReplaceSpacesRigid);
		
		JPanel JPRow2ReplaceSpecificText = new JPanel();
		JPRow2ReplaceSpecificText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow2ReplaceSpecificText.setLayout(new BoxLayout(JPRow2ReplaceSpecificText, BoxLayout.PAGE_AXIS));
		JPRow2ReplaceSpecificText.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow2ReplaceSpecificText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow2ReplaceSpecificText.add(ReplaceSpecificText);
		
		JPanel JPRow2ReplaceSpecific = new JPanel();
		JPRow2ReplaceSpecific.setLayout(new BoxLayout(JPRow2ReplaceSpecific, BoxLayout.LINE_AXIS));
		JPRow2ReplaceSpecific.add(ReplaceSpecific);
		JPRow2ReplaceSpecific.add(JPRow2ReplaceSpecificText);
		
		JPanel JPRow2RecordGroupNumText = new JPanel();
		JPRow2RecordGroupNumText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow2RecordGroupNumText.setLayout(new BoxLayout(JPRow2RecordGroupNumText, BoxLayout.PAGE_AXIS));
		JPRow2RecordGroupNumText.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow2RecordGroupNumText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow2RecordGroupNumText.add(RecordGroupNumText);
		
		JPanel JPRow2RecordGroupNum = new JPanel();
		JPRow2RecordGroupNum.setLayout(new BoxLayout(JPRow2RecordGroupNum, BoxLayout.LINE_AXIS));
		JPRow2RecordGroupNum.add(RecordGroupNum);
		JPRow2RecordGroupNum.add(JPRow2RecordGroupNumText);
		
		JPanel JPRow2SeriesDesigText = new JPanel();
		JPRow2SeriesDesigText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow2SeriesDesigText.setLayout(new BoxLayout(JPRow2SeriesDesigText, BoxLayout.PAGE_AXIS));
		JPRow2SeriesDesigText.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow2SeriesDesigText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow2SeriesDesigText.add(SeriesDesignatorText);
		
		JPanel JPRow2SeriesDesig = new JPanel();
		JPRow2SeriesDesig.setLayout(new BoxLayout(JPRow2SeriesDesig, BoxLayout.LINE_AXIS));
		JPRow2SeriesDesig.add(SeriesDesignator);
		JPRow2SeriesDesig.add(JPRow2SeriesDesigText);
		
		JPanel JPRow2NamePreviewText = new JPanel();
		JPRow2NamePreviewText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow2NamePreviewText.setLayout(new BoxLayout(JPRow2NamePreviewText, BoxLayout.PAGE_AXIS));
		JPRow2NamePreviewText.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow2NamePreviewText.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow2NamePreviewText.add(NamePreview);
		
		JPanel JPRow2NamePreview = new JPanel();
		JPRow2NamePreview.setBorder(BorderFactory.createTitledBorder(" Filename Preview "));
		JPRow2NamePreview.setLayout(new BoxLayout(JPRow2NamePreview, BoxLayout.LINE_AXIS));
		JPRow2NamePreview.add(JPRow2NamePreviewText);
		
		JPanel JPRow2Filename = new JPanel();
		JPRow2Filename.setBorder(BorderFactory.createTitledBorder(" Filename "));
		JPRow2Filename.setLayout(new BoxLayout(JPRow2Filename, BoxLayout.PAGE_AXIS));
		JPRow2Filename.add(JPRow2RecordGroupNum);
		JPRow2Filename.add(Box.createRigidArea(new Dimension(0, 7)));
		JPRow2Filename.add(JPRow2SeriesDesig);
		JPRow2Filename.add(Box.createRigidArea(new Dimension(0, 7)));
		JPRow2Filename.add(JPRow2ReplaceSpecific);
		JPRow2Filename.add(Box.createRigidArea(new Dimension(0, 7)));
		JPRow2Filename.add(JPRow2ReplaceSpaces);
		JPRow2Filename.add(Box.createRigidArea(new Dimension(0, 7)));
		JPRow2Filename.add(JPRow2InsertFolderName);
		JPRow2Filename.add(Box.createRigidArea(new Dimension(0, 7)));
		//JPRow2Filename.add(JPRow2NamePreview); // I might implement this later, just an idea for now
		
		JPanel JPRow2 = new JPanel();
		JPRow2.setLayout(new BoxLayout(JPRow2, BoxLayout.LINE_AXIS));
		JPRow2.add(JPRow2Filename);
		
		//############################## ROW 3 PANELS ###########################################################//
		//JScrollPane JSPFileTable = new JScrollPane(JXTFileTable);
		//JSPFileTable.setViewportView(JXTFileTable);
		
		JPanel JPRow3FileDisplay = new JPanel();
		JPRow3FileDisplay.setLayout(new BoxLayout(JPRow3FileDisplay, BoxLayout.LINE_AXIS));
		//JPRow3FileDisplay.add(JSPFileTable);
		
		JPanel JPRow3 = new JPanel();
		JPRow3.setLayout(new BoxLayout(JPRow3, BoxLayout.LINE_AXIS));
		JPRow3.add(JPRow3FileDisplay);
		
		//############################## ROW 4 PANELS ###########################################################//
		JScrollPane JPRow4Message = new JScrollPane(JTAMessageWindow);
		JPRow4Message.setBorder(BorderFactory.createTitledBorder(" Messages "));
		
		JPanel JPRow4 = new JPanel();
		JPRow4.setLayout(new BoxLayout(JPRow4, BoxLayout.LINE_AXIS));
		JPRow4.add(JPRow4Message);
		
		//############################## ROW 5 PANELS ###########################################################//
		JPanel JPRow5Commands = new JPanel();
		JPRow5Commands.setLayout(new BoxLayout(JPRow5Commands, BoxLayout.LINE_AXIS));
		JPRow5Commands.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow5Commands.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		//JPRow5Commands.add(Box.createRigidArea(new Dimension(10, 0)));
		JPRow5Commands.add(Preview);
		JPRow5Commands.add(Box.createRigidArea(new Dimension(10, 0)));
		JPRow5Commands.add(Run);
		JPRow5Commands.add(Box.createRigidArea(new Dimension(10, 0)));
		JPRow5Commands.add(Cancel);
		
		JPanel JPRow5 = new JPanel();
		JPRow5.setLayout(new BoxLayout(JPRow5, BoxLayout.LINE_AXIS));
		JPRow5.add(JPRow5Commands);
		
		//############################## MAIN PANEL #############################################################//
		// setting for main panel
		JPanel JPMain = new JPanel();
		JPMain.setLayout(new BoxLayout(JPMain, BoxLayout.PAGE_AXIS));;
		JPMain.add(JPRow1);
		JPMain.add(JPRow2);
		JPMain.add(JPRow3);
		//JPMain.add(JPRow4);
		JPMain.add(JPRow5);
		
		// setting for main frame
		this.setTitle("ReNamer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setPreferredSize(new Dimension(900,700));
		this.setContentPane(JPMain);
			
		// action listeners
		Browse.addActionListener(new MyIOListener());
		Run.addActionListener(new MyIOListener());
		pack(); // pack is added when the window size is not set manually
	}
	
	//######################################## GUI ACTION LISTENERS SECTION ##########################################//
	/**
	 * This internal class listens for user's input
	 * 
	 */
	private class MyIOListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
			// INPUT BUTTON
			if (event.getSource() == Browse)
			{
				// open browse directory
				int userRespond = fileChooser.showOpenDialog(ReNamer.this);
			
				// user select a directory
				if (userRespond == JFileChooser.APPROVE_OPTION)
				{
					// set input directory
					// every time a new directory is selected, the 
					// inputDirectory variable is changed
					inputDirectory = fileChooser.getSelectedFile();
					
					String msg = inputDirectory + "\n";
					printDir(msg); //prints selected dir in text area
					System.out.println("directory selected");
				}
			}
			
			// RUN BUTTON
			else if (event.getSource() == Run) {
				System.out.println("run started");
				System.out.println(RecordGroupNumText.getText());
				printFiles();
				
			}
			
		}
 }
	
	//######################################## HELPER METHODS SECTION ################################################//
	// this method prints the directory in the textbox
	private void printDir(String msg)
	{
		JTABrowseBox.setText(msg.trim());
	}
	
	// returns array of all filenames in a directory
	private void printFiles() {
		System.out.println("start print");
		File[] listOfFiles = inputDirectory.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File: " + listOfFiles[i].getName());
			}
			else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory: " + listOfFiles[i].getName());
			}
		}
		
		// reName here
		for (int i = 0; i < listOfFiles.length; i++) {
				File newFile = new File(listOfFiles[i].getName() + "I DID IT!");
				listOfFiles[i].renameTo(newFile);
		}
		
		System.out.println("rename done");
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File: " + listOfFiles[i].getName());
			}
			else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory: " + listOfFiles[i].getName());
			}
		}
		
	}

	
	//######################################## MAIN FUNCTION SECTION ################################################//	
 
 public static void main(String args[]) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				new ReNamer().setVisible(true);
			}
			
		});
	}
}

/**
 * programmer notes:
 * 
 * LINE_AXIS vs PAGE_AXIS
 */