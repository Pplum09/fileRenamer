/**
 * Name: Isara Ritthaworn
 * Date: 1/12/2015
 * 
 * Program Description: This program is used to rename file names to a user selected name
 * 
 */
import javax.swing.*;
import javax.swing.event.*;

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

public class ReNamer extends JFrame {

	
	
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
	
	// text box
	private static JTextArea JTABrowseBox;
	private static JTextArea RecordGroupNumText;
	private static JTextArea SeriesDesignatorText;
	private static JTextArea ReplaceSpecificText;
	private static JTextArea JTASourceFileName;
	private static JTextArea JTANewFileName;
	private static JTextArea JTAMessageWindow;
	
	// ReNamer default constructor
	public ReNamer()
	{
		initGUIComponents();
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
		
		// text box
		JTABrowseBox = new JTextArea();
		RecordGroupNumText = new JTextArea();
		SeriesDesignatorText = new JTextArea();
		ReplaceSpecificText = new JTextArea();
		JTASourceFileName = new JTextArea();
		JTANewFileName = new JTextArea();
		JTAMessageWindow = new JTextArea();
		
		// setting for row 1 panel
		RecordGroupNum.setText(" Record Group Number: ");
		
		SeriesDesignator.setText(" Series Designator: ");
		SeriesDesignator.setToolTipText("Enter series designator");
		
		ReplaceSpecific.setText(" Replace Specific: ");
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
		
		JTASourceFileName.setEditable(false);
		JTASourceFileName.setToolTipText("Old file names");
		
		JTANewFileName.setEditable(false);
		JTANewFileName.setToolTipText("New file names");
		
		JTAMessageWindow.setEditable(false);
		JTAMessageWindow.setToolTipText("Messages display here");
		
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
		JPanel JPRow2FilenameCheckBox = new JPanel();
		JPRow2FilenameCheckBox.setLayout(new BoxLayout(JPRow2FilenameCheckBox, BoxLayout.PAGE_AXIS));
		JPRow2FilenameCheckBox.add(Box.createRigidArea(new Dimension(0, 5)));
		JPRow2FilenameCheckBox.add(RecordGroupNum);
		JPRow2FilenameCheckBox.add(Box.createRigidArea(new Dimension(0, 11)));
		JPRow2FilenameCheckBox.add(SeriesDesignator);
		JPRow2FilenameCheckBox.add(Box.createRigidArea(new Dimension(0, 13)));
		JPRow2FilenameCheckBox.add(ReplaceSpecific);
		JPRow2FilenameCheckBox.add(Box.createRigidArea(new Dimension(0, 12)));
		JPRow2FilenameCheckBox.add(ReplaceSpaces);
		JPRow2FilenameCheckBox.add(Box.createRigidArea(new Dimension(0, 10)));
		JPRow2FilenameCheckBox.add(InsertFolderName);
		
		JPanel JPRow2FilenameTextBox1 = new JPanel();
		JPRow2FilenameTextBox1.setLayout(new BoxLayout(JPRow2FilenameTextBox1, BoxLayout.PAGE_AXIS));
		JPRow2FilenameTextBox1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow2FilenameTextBox1.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow2FilenameTextBox1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow2FilenameTextBox1.add(RecordGroupNumText);
		
		JPanel JPRow2FilenameTextBox2 = new JPanel();
		JPRow2FilenameTextBox2.setLayout(new BoxLayout(JPRow2FilenameTextBox2, BoxLayout.PAGE_AXIS));
		JPRow2FilenameTextBox2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow2FilenameTextBox2.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow2FilenameTextBox2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow2FilenameTextBox2.add(SeriesDesignatorText);
		
		JPanel JPRow2FilenameTextBox3 = new JPanel();
		JPRow2FilenameTextBox3.setLayout(new BoxLayout(JPRow2FilenameTextBox3, BoxLayout.PAGE_AXIS));
		JPRow2FilenameTextBox3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPRow2FilenameTextBox3.setMinimumSize(new Dimension(Integer.MAX_VALUE, 15));
		JPRow2FilenameTextBox3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JPRow2FilenameTextBox3.add(ReplaceSpecificText);
		
		JPanel JPRow2FilenameTextBoxMain = new JPanel();
		JPRow2FilenameTextBoxMain.setLayout(new BoxLayout(JPRow2FilenameTextBoxMain, BoxLayout.PAGE_AXIS));
		JPRow2FilenameTextBoxMain.add(JPRow2FilenameTextBox1);
		JPRow2FilenameTextBoxMain.add(Box.createRigidArea(new Dimension(0, 5)));
		JPRow2FilenameTextBoxMain.add(JPRow2FilenameTextBox2);
		JPRow2FilenameTextBoxMain.add(Box.createRigidArea(new Dimension(0, 5)));
		JPRow2FilenameTextBoxMain.add(JPRow2FilenameTextBox3);
		JPRow2FilenameTextBoxMain.add(Box.createRigidArea(new Dimension(0, 45)));
		
		JPanel JPRow2Filename = new JPanel();
		JPRow2Filename.setBorder(BorderFactory.createTitledBorder(" Filename "));
		JPRow2Filename.setLayout(new BoxLayout(JPRow2Filename, BoxLayout.LINE_AXIS));
		JPRow2Filename.add(JPRow2FilenameCheckBox);
		JPRow2Filename.add(JPRow2FilenameTextBoxMain);
		
		
		JPanel JPRow2 = new JPanel();
		JPRow2.setLayout(new BoxLayout(JPRow2, BoxLayout.LINE_AXIS));
		JPRow2.add(JPRow2Filename);
		
		//############################## ROW 3 PANELS ###########################################################//
		JScrollPane JPRow3SourceName = new JScrollPane(JTASourceFileName);
		JPRow3SourceName.setBorder(BorderFactory.createTitledBorder(" Source file name "));
		
		JScrollPane JPRow3NewName = new JScrollPane(JTANewFileName);
		JPRow3NewName.setBorder(BorderFactory.createTitledBorder(" New file name "));
		
		JPanel JPRow3FileDisplay = new JPanel();
		JPRow3FileDisplay.setLayout(new BoxLayout(JPRow3FileDisplay, BoxLayout.LINE_AXIS));
		JPRow3FileDisplay.add(JPRow3SourceName);
		JPRow3FileDisplay.add(JPRow3NewName);
		
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
		JPMain.add(JPRow4);
		JPMain.add(JPRow5);
		
		// setting for main frame
		this.setTitle("ReNamer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setPreferredSize(new Dimension(900,700));
		this.setContentPane(JPMain);
			
		
		pack(); // pack is added when the window size is not set manually
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