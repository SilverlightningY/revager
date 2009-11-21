/* 
 * Copyright 2009 Davide Casciato, Sandra Reich, Johannes Wettinger
 * 
 * This file is part of Resi.
 *
 * Resi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Resi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resi. If not, see <http://www.gnu.org/licenses/>.
 */
package neos.resi.gui.dialogs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import neos.resi.app.model.ApplicationData;
import neos.resi.app.model.Data;
import neos.resi.app.model.DataException;
import neos.resi.app.model.appdata.AppCSVColumnName;
import neos.resi.app.model.appdata.AppCSVProfile;
import neos.resi.gui.AbstractDialog;
import neos.resi.gui.TextPopupWindow;
import neos.resi.gui.UI;
import neos.resi.gui.TextPopupWindow.ButtonClicked;
import neos.resi.gui.models.CSVColumnsComboBoxModel;
import neos.resi.gui.workers.CSVProfilesWorker;
import neos.resi.tools.GUITools;

/**
 * The Class CSVProfilesDialog.
 */
@SuppressWarnings("serial")
public class CSVProfilesDialog extends AbstractDialog {

	private ApplicationData appData = Data.getInstance().getAppData();

	private AppCSVProfile currentProfile = null;

	private boolean dialogContentCreated = false;

	private boolean updatingColOrder = false;
	private boolean updatingDialog = false;
	private boolean updatingAppData = false;

	private JPanel panelContent = null;

	private JList listProfiles;

	private JButton buttonAdd;
	private JButton buttonRemove;

	private JTextField textProfileName;

	private JComboBox boxColumn1;
	private JComboBox boxColumn2;
	private JComboBox boxColumn3;
	private JComboBox boxColumn4;

	private CSVColumnsComboBoxModel modelColumn1;
	private CSVColumnsComboBoxModel modelColumn2;
	private CSVColumnsComboBoxModel modelColumn3;
	private CSVColumnsComboBoxModel modelColumn4;

	private JTextField textColumn1;
	private JTextField textColumn2;
	private JTextField textColumn3;
	private JTextField textColumn4;

	private JCheckBox boxColsInFirstLine;
	private JCheckBox boxEncapsContent;

	private List<JTextField> validSevMaps;

	/**
	 * Instantiates a new cSV profiles dialog.
	 * 
	 * @param parent
	 *            the parent
	 */
	public CSVProfilesDialog(Frame parent) {
		super(parent);

		setTitle(Data.getInstance().getLocaleStr("csvProfDialog.title"));
		setIcon(Data.getInstance().getIcon("CSVProfiles_64x64.png"));
		setDescription(Data.getInstance().getLocaleStr(
				"csvProfDialog.description"));

		setHelpChapter("csv_profiles", "1");
		/*
		 * Close button
		 */
		JButton buttonClose = new JButton(Data.getInstance().getLocaleStr(
				"close"), Data.getInstance().getIcon("buttonClose_16x16.png"));
		buttonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CSVProfilesWorker(listProfiles.getSelectedIndex())
						.execute();
				setVisible(false);
			}
		});

		addButton(buttonClose);

		buttonClose.requestFocus();

		/*
		 * Set window properties
		 */
		setMinimumSize(new Dimension(750, 650));
		setPreferredSize(new Dimension(750, 650));

		setLocationToCenter();

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				new CSVProfilesWorker().execute();
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}
		});

		pack();
	}

	/**
	 * Creates the dialog content.
	 */
	private void createDialogContent() {
		GridBagLayout gblBase = new GridBagLayout();
		getContentPane().setLayout(gblBase);

		GridBagLayout gblContent = new GridBagLayout();
		panelContent = new JPanel(gblContent);

		/*
		 * List of profiles
		 */
		listProfiles = new JList(getCSVProfiles());
		listProfiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listProfiles.setSelectedIndex(0);
		listProfiles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (listProfiles.getSelectedValue() != null) {
						updateAppData(null, -1);

						updateDialog(appData
								.getCSVProfile((String) listProfiles
										.getSelectedValue()));

						for (Component c : panelContent.getComponents()) {
							c.setEnabled(true);
						}

						buttonRemove.setEnabled(true);
					} else {
						for (Component c : panelContent.getComponents()) {
							c.setEnabled(false);
						}

						buttonRemove.setEnabled(false);
					}
				} catch (DataException exc) {
					JOptionPane.showMessageDialog(UI.getInstance()
							.getCSVProfilesDialog(), GUITools
							.getMessagePane(exc.getMessage()), Data
							.getInstance().getLocaleStr("error"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		int gblRow = 0;

		/*
		 * Profile name
		 */
		textProfileName = new JTextField("");
		textProfileName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				new CSVProfilesWorker(textProfileName.getText()).execute();
			}
		});

		GUITools.addComponent(panelContent, gblContent, new JLabel(Data
				.getInstance().getLocaleStr("csvProfDialog.profName")), 0,
				gblRow, 2, 1, 1.0, 0.0, 0, 5, 0, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		GUITools.addComponent(panelContent, gblContent, textProfileName, 0,
				gblRow, 2, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		/*
		 * Column order
		 */
		modelColumn1 = new CSVColumnsComboBoxModel();
		modelColumn2 = new CSVColumnsComboBoxModel();
		modelColumn3 = new CSVColumnsComboBoxModel();
		modelColumn4 = new CSVColumnsComboBoxModel();

		boxColumn1 = new JComboBox(modelColumn1);
		boxColumn1.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				updateColumnOrder(boxColumn1, textColumn1);
				new CSVProfilesWorker().execute();
			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});

		boxColumn2 = new JComboBox(modelColumn2);
		boxColumn2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateColumnOrder(boxColumn2, textColumn2);
				new CSVProfilesWorker().execute();
			}
		});

		boxColumn3 = new JComboBox(modelColumn3);
		boxColumn3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateColumnOrder(boxColumn3, textColumn3);
				new CSVProfilesWorker().execute();
			}
		});

		boxColumn4 = new JComboBox(modelColumn4);
		boxColumn4.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateColumnOrder(boxColumn4, textColumn4);
				new CSVProfilesWorker().execute();
			}
		});

		GUITools.addComponent(panelContent, gblContent, new JLabel(Data
				.getInstance().getLocaleStr("csvProfDialog.columnsTitle")), 0,
				gblRow, 4, 1, 1.0, 0.0, 15, 5, 0, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		GUITools.addComponent(panelContent, gblContent, boxColumn1, 0, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, boxColumn2, 1, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, boxColumn3, 2, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, boxColumn4, 3, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		/*
		 * Column mappings
		 */
		FocusListener textColFocusListener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				new CSVProfilesWorker().execute();
			}
		};

		textColumn1 = new JTextField();
		textColumn1.addFocusListener(textColFocusListener);

		textColumn2 = new JTextField();
		textColumn2.addFocusListener(textColFocusListener);

		textColumn3 = new JTextField();
		textColumn3.addFocusListener(textColFocusListener);

		textColumn4 = new JTextField();
		textColumn4.addFocusListener(textColFocusListener);

		GUITools.addComponent(panelContent, gblContent, new JLabel(Data
				.getInstance().getLocaleStr("csvProfDialog.columnsMappings")),
				0, gblRow, 4, 1, 1.0, 0.0, 15, 5, 0, 5,
				GridBagConstraints.BOTH, GridBagConstraints.WEST);

		gblRow++;

		GUITools.addComponent(panelContent, gblContent, textColumn1, 0, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, textColumn2, 1, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, textColumn3, 2, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, textColumn4, 3, gblRow,
				1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		/*
		 * Checkboxes for options of the CSV profile
		 */
		ChangeListener boxChangeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				new CSVProfilesWorker().execute();
			}
		};

		boxColsInFirstLine = new JCheckBox(Data.getInstance().getLocaleStr(
				"csvProfDialog.boxColsInFirstLine"));
		boxColsInFirstLine.addChangeListener(boxChangeListener);

		boxEncapsContent = new JCheckBox(Data.getInstance().getLocaleStr(
				"csvProfDialog.boxEncapsContent"));
		boxEncapsContent.addChangeListener(boxChangeListener);

		GUITools.addComponent(panelContent, gblContent, boxColsInFirstLine, 0,
				gblRow, 4, 1, 1.0, 0.0, 25, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		GUITools.addComponent(panelContent, gblContent, boxEncapsContent, 0,
				gblRow, 4, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		/*
		 * Valid severity mappings
		 */
		validSevMaps = new ArrayList<JTextField>();
		for (int i = 0; i < 6; i++) {
			JTextField text = new JTextField("");
			text.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
					updateSeverityMappings();
					updateSeverityMappings();
				}

				@Override
				public void keyTyped(KeyEvent e) {
				}
			});
			text.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					new CSVProfilesWorker().execute();
				}
			});

			validSevMaps.add(text);
		}

		GUITools.addComponent(panelContent, gblContent, new JLabel(Data
				.getInstance().getLocaleStr("csvProfDialog.validSevMaps")), 0,
				gblRow, 4, 1, 1.0, 0.0, 25, 5, 0, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		GUITools.addComponent(panelContent, gblContent, validSevMaps.get(0), 0,
				gblRow, 1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, validSevMaps.get(1), 1,
				gblRow, 1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		GUITools.addComponent(panelContent, gblContent, validSevMaps.get(2), 0,
				gblRow, 1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, validSevMaps.get(3), 1,
				gblRow, 1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		gblRow++;

		GUITools.addComponent(panelContent, gblContent, validSevMaps.get(4), 0,
				gblRow, 1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);
		GUITools.addComponent(panelContent, gblContent, validSevMaps.get(5), 1,
				gblRow, 1, 1, 1.0, 0.0, 5, 5, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		/*
		 * Build content pane with vertical separator
		 */
		GUITools.addComponent(getContentPane(), gblBase, new JLabel(Data
				.getInstance().getLocaleStr("csvProfDialog.listTitle")), 0, 0,
				2, 1, 1.0, 0.0, 0, 0, 5, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		JScrollPane scrollList = new JScrollPane(listProfiles,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollList.setMinimumSize(new Dimension(70, 200));
		scrollList.setPreferredSize(new Dimension(70, 200));

		GUITools.addComponent(getContentPane(), gblBase, scrollList, 0, 1, 2,
				1, 0.0, 1.0, 0, 0, 10, 0, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		/*
		 * Add and remove button
		 */
		buttonAdd = GUITools
				.newImageButton(Data.getInstance().getIcon("add_25x25_0.png"),
						Data.getInstance().getIcon("add_25x25.png"));
		buttonAdd.setToolTipText(Data.getInstance().getLocaleStr(
				"csvProfDialog.add"));
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TextPopupWindow popup = new TextPopupWindow(UI.getInstance()
						.getCSVProfilesDialog(), Data.getInstance()
						.getLocaleStr("csvProfDialog.popupNew"), null, false);

				popup.setVisible(true);

				if (popup.getButtonClicked() == ButtonClicked.OK) {
					try {
						appData.newCSVProfile(popup.getInput());

						new CSVProfilesWorker(popup.getInput()).execute();
					} catch (DataException exc) {
						JOptionPane.showMessageDialog(UI.getInstance()
								.getCSVProfilesDialog(), GUITools
								.getMessagePane(exc.getMessage()), Data
								.getInstance().getLocaleStr("error"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		buttonRemove = GUITools.newImageButton(Data.getInstance().getIcon(
				"remove_25x25_0.png"), Data.getInstance().getIcon(
				"remove_25x25.png"));
		buttonRemove.setToolTipText(Data.getInstance().getLocaleStr(
				"csvProfDialog.remove"));
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {
						Data.getInstance().getLocaleStr("button.remove"),
						Data.getInstance().getLocaleStr("button.abort") };

				String profName = (String) listProfiles.getSelectedValue();

				try {
					if (appData.getNumberOfCSVProfiles() > 1
							&& JOptionPane
									.showOptionDialog(
											UI.getInstance()
													.getCSVProfilesDialog(),
											GUITools
													.getMessagePane(Data
															.getInstance()
															.getLocaleStr(
																	"message.confirmRemoveCSVProf")
															+ "\n\n" + profName),
											Data.getInstance().getLocaleStr(
													"question"),
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null,
											options, options[0]) == JOptionPane.YES_OPTION) {

						appData.removeCSVProfile(profName);

						new CSVProfilesWorker(0).execute();
					}
				} catch (DataException exc) {
					JOptionPane.showMessageDialog(UI.getInstance()
							.getCSVProfilesDialog(), GUITools
							.getMessagePane(exc.getMessage()), Data
							.getInstance().getLocaleStr("error"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		try {
			if (appData.getNumberOfCSVProfiles() <= 1) {
				buttonRemove.setEnabled(false);
			}
		} catch (DataException exc) {
			/*
			 * do nothing
			 */
		}

		GUITools.addComponent(getContentPane(), gblBase, buttonAdd, 0, 2, 1, 1,
				1.0, 0.0, 0, 0, 0, 0, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		GUITools.addComponent(getContentPane(), gblBase, buttonRemove, 1, 2, 1,
				1, 1.0, 0.0, 0, 0, 0, 0, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		JPanel panelSep = new JPanel();
		panelSep.setBorder(new MatteBorder(0, 1, 0, 0, UI.SEPARATOR_COLOR));

		GUITools.addComponent(getContentPane(), gblBase, panelSep, 2, 0, 1, 3,
				0.0, 1.0, 0, 20, 0, 5, GridBagConstraints.BOTH,
				GridBagConstraints.WEST);

		GUITools.addComponent(getContentPane(), gblBase, panelContent, 3, 0, 1,
				3, 1.0, 1.0, 0, 0, 0, 0, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.NORTHWEST);
	}

	/**
	 * Update column order.
	 * 
	 * @param box
	 *            the box
	 * @param text
	 *            the text
	 */
	private void updateColumnOrder(JComboBox box, JTextField text) {
		if (!updatingColOrder) {
			updatingColOrder = true;

			/*
			 * Determine free column
			 */
			List<String> columns = new ArrayList<String>();
			columns.add(Integer.toString(0));
			columns.add(Integer.toString(1));
			columns.add(Integer.toString(2));
			columns.add(Integer.toString(3));

			columns.remove(Integer.toString(boxColumn1.getSelectedIndex()));
			columns.remove(Integer.toString(boxColumn2.getSelectedIndex()));
			columns.remove(Integer.toString(boxColumn3.getSelectedIndex()));
			columns.remove(Integer.toString(boxColumn4.getSelectedIndex()));

			if (columns.size() > 0) {
				int remainIdx = Integer.parseInt(columns.get(0));

				String colMap;

				if (boxColumn1 != box
						&& box.getSelectedIndex() == boxColumn1
								.getSelectedIndex()) {
					boxColumn1.setSelectedIndex(remainIdx);
					boxColumn1.repaint();

					colMap = textColumn1.getText();
					textColumn1.setText(text.getText());
					text.setText(colMap);
				} else if (boxColumn2 != box
						&& box.getSelectedIndex() == boxColumn2
								.getSelectedIndex()) {
					boxColumn2.setSelectedIndex(remainIdx);
					boxColumn2.repaint();

					colMap = textColumn2.getText();
					textColumn2.setText(text.getText());
					text.setText(colMap);
				} else if (boxColumn3 != box
						&& box.getSelectedIndex() == boxColumn3
								.getSelectedIndex()) {
					boxColumn3.setSelectedIndex(remainIdx);
					boxColumn3.repaint();

					colMap = textColumn3.getText();
					textColumn3.setText(text.getText());
					text.setText(colMap);
				} else if (boxColumn4 != box
						&& box.getSelectedIndex() == boxColumn4
								.getSelectedIndex()) {
					boxColumn4.setSelectedIndex(remainIdx);
					boxColumn4.repaint();

					colMap = textColumn4.getText();
					textColumn4.setText(text.getText());
					text.setText(colMap);
				}
			}

			updatingColOrder = false;
		}
	}

	/**
	 * Update severity mappings.
	 */
	private void updateSeverityMappings() {
		int lastFilledField = -1;

		for (int i = 0; validSevMaps.size() > i; i++) {
			if (!validSevMaps.get(i).getText().equals("")) {
				if (lastFilledField == i - 1) {
					lastFilledField = i;

					validSevMaps.get(i).setVisible(true);
				} else {
					lastFilledField += 1;

					validSevMaps.get(lastFilledField).setText(
							validSevMaps.get(i).getText());

					validSevMaps.get(i).setText("");
					validSevMaps.get(i).setVisible(false);
				}
			} else {
				validSevMaps.get(i).setVisible(false);
			}
		}

		for (int i = 0; validSevMaps.size() > i; i++) {
			if (!validSevMaps.get(i).getText().equals("")) {
				lastFilledField = i;
			}
		}

		if (lastFilledField < validSevMaps.size() - 1) {
			validSevMaps.get(lastFilledField + 1).setVisible(true);
			validSevMaps.get(lastFilledField + 1).setText("");
		}

		panelContent.revalidate();
	}

	/**
	 * Update dialog.
	 * 
	 * @param csvProfile
	 *            the csv profile
	 */
	private void updateDialog(AppCSVProfile csvProfile) {
		updatingDialog = true;

		currentProfile = csvProfile;

		AppCSVColumnName col = null;

		try {
			/*
			 * Column order and mappings
			 */
			Iterator<AppCSVColumnName> iter = currentProfile.getColumnOrder()
					.iterator();

			col = iter.next();
			modelColumn1.setSelectedColumn(col);
			textColumn1.setText(currentProfile.getColumnMapping(col));

			col = iter.next();
			modelColumn2.setSelectedColumn(col);
			textColumn2.setText(currentProfile.getColumnMapping(col));

			col = iter.next();
			modelColumn3.setSelectedColumn(col);
			textColumn3.setText(currentProfile.getColumnMapping(col));

			col = iter.next();
			modelColumn4.setSelectedColumn(col);
			textColumn4.setText(currentProfile.getColumnMapping(col));

			/*
			 * Profile name
			 */
			textProfileName.setText(currentProfile.getName());

			/*
			 * Options
			 */
			boxColsInFirstLine.setSelected(currentProfile.isColsInFirstLine());

			boxEncapsContent.setSelected(currentProfile.isEncapsulateContent());

			/*
			 * Valid severity mappings
			 */
			List<String> mappings = currentProfile.getValidSeverityMappings();

			int i = 0;

			while (i < mappings.size() && i < validSevMaps.size()) {
				validSevMaps.get(i).setText(mappings.get(i));
				validSevMaps.get(i).setVisible(true);

				i++;
			}

			if (i < validSevMaps.size() - 1) {
				validSevMaps.get(i).setText("");
				validSevMaps.get(i).setVisible(true);

				i++;

				while (i < validSevMaps.size()) {
					validSevMaps.get(i).setText("");
					validSevMaps.get(i).setVisible(false);

					i++;
				}
			}

		} catch (Exception e) {
			for (Component c : panelContent.getComponents()) {
				c.setVisible(false);
			}
		}

		panelContent.revalidate();

		updatingDialog = false;
	}

	/**
	 * Update app data.
	 * 
	 * @param selectedItem
	 *            the selected item
	 * @param selectedIndex
	 *            the selected index
	 */
	public void updateAppData(String selectedItem, int selectedIndex) {
		if (!updatingAppData) {
			updatingAppData = true;

			try {
				if (currentProfile != null && currentProfile.exists()
						&& !textProfileName.getText().equals("")
						&& !updatingDialog) {
					/*
					 * profile name
					 */
					currentProfile.setName(textProfileName.getText());

					/*
					 * Checkbox options
					 */
					currentProfile.setColsInFirstLine(boxColsInFirstLine
							.isSelected());

					currentProfile.setEncapsulateContent(boxEncapsContent
							.isSelected());

					/*
					 * Column order
					 */
					List<AppCSVColumnName> colList = new ArrayList<AppCSVColumnName>();
					colList.add(modelColumn1.getSelectedColumn());
					colList.add(modelColumn2.getSelectedColumn());
					colList.add(modelColumn3.getSelectedColumn());
					colList.add(modelColumn4.getSelectedColumn());

					currentProfile.setColumnOrder(colList);

					/*
					 * Column mappings
					 */
					currentProfile.setColumnMapping(modelColumn1
							.getSelectedColumn(), textColumn1.getText());
					currentProfile.setColumnMapping(modelColumn2
							.getSelectedColumn(), textColumn2.getText());
					currentProfile.setColumnMapping(modelColumn3
							.getSelectedColumn(), textColumn3.getText());
					currentProfile.setColumnMapping(modelColumn4
							.getSelectedColumn(), textColumn4.getText());

					/*
					 * Severity mappings
					 */
					List<String> mappings = new ArrayList<String>();

					for (JTextField tf : validSevMaps) {
						if (!tf.getText().trim().equals("")) {
							mappings.add(tf.getText());
						}
					}

					if (mappings.isEmpty()) {
						/*
						 * Load standard severities as mappings
						 */
						for (String sev : Data.getInstance().getLocaleStr(
								"standardSeverities").split(",")) {
							mappings.add(sev);
						}
					}

					currentProfile.setValidSeverityMappings(mappings);
				}

				/*
				 * Set selected profile
				 */
				if (selectedIndex != -1) {
					listProfiles.setListData(getCSVProfiles());
					listProfiles.setSelectedIndex(selectedIndex);
				}

				if (selectedItem != null
						&& !selectedItem.equals((String) listProfiles
								.getSelectedValue())) {
					listProfiles.setListData(getCSVProfiles());
					listProfiles.setSelectedValue(selectedItem, true);
				}

				/*
				 * Set state of remove button
				 */
				if (appData.getNumberOfCSVProfiles() > 1) {
					buttonRemove.setEnabled(true);
				} else {
					buttonRemove.setEnabled(false);
				}
			} catch (DataException e) {
				JOptionPane.showMessageDialog(UI.getInstance()
						.getCSVProfilesDialog(), GUITools.getMessagePane(e
						.getMessage()), Data.getInstance()
						.getLocaleStr("error"), JOptionPane.ERROR_MESSAGE);
			}

			updatingAppData = false;
		}
	}

	/**
	 * Gets the cSV profiles.
	 * 
	 * @return the cSV profiles
	 */
	private Vector<String> getCSVProfiles() {
		Vector<String> vecProfiles = new Vector<String>();

		try {
			for (AppCSVProfile prof : Data.getInstance().getAppData()
					.getCSVProfiles()) {
				vecProfiles.add(prof.getName());
			}
		} catch (DataException exc) {
			JOptionPane.showMessageDialog(UI.getInstance()
					.getCSVProfilesDialog(), GUITools.getMessagePane(exc
					.getMessage()), Data.getInstance().getLocaleStr("error"),
					JOptionPane.ERROR_MESSAGE);
		}

		return vecProfiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see neos.resi.gui.AbstractDialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean vis) {
		if (vis) {
			setLocationToCenter();
		}

		if (!dialogContentCreated && vis) {
			createDialogContent();

			try {
				updateDialog(appData.getCSVProfiles().get(0));
			} catch (DataException exc) {
				JOptionPane.showMessageDialog(UI.getInstance()
						.getCSVProfilesDialog(), GUITools.getMessagePane(exc
						.getMessage()), Data.getInstance()
						.getLocaleStr("error"), JOptionPane.ERROR_MESSAGE);
			}

			dialogContentCreated = true;
		}

		super.setVisible(vis);
	}

}
