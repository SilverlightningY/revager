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
package neos.resi.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import neos.resi.app.Application;
import neos.resi.app.ResiFileFilter;
import neos.resi.app.ReviewManagement;
import neos.resi.app.model.Data;
import neos.resi.gui.UI;
import neos.resi.gui.helpers.FileChooser;
import neos.resi.gui.workers.SaveReviewWorker;
import neos.resi.tools.GUITools;

/**
 * The Class SaveReviewAsAction.
 */
@SuppressWarnings("serial")
public class SaveReviewAsAction extends AbstractAction {

	private static final String ENDING_XML = "."
			+ Data.getInstance().getResource("fileEndingReviewXML")
					.toLowerCase();

	private boolean exitApplication = false;

	/**
	 * Instantiates a new save review as action.
	 */
	public SaveReviewAsAction() {
		super();

		putValue(SMALL_ICON, Data.getInstance().getIcon("menuSaveAs_16x16.png"));
		putValue(NAME, Data.getInstance()
				.getLocaleStr("menu.file.saveReviewAs"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ReviewManagement revMgmt = Application.getInstance().getReviewMgmt();

		boolean exitApp = exitApplication;
		this.exitApplication = false;

		if (!Application.getInstance().getApplicationCtl().isReviewStorable()) {
			String messageText = Data.getInstance().getLocaleStr(
					"message.notStorable")
					+ "\n\n"
					+ Application.getInstance().getApplicationCtl()
							.getReasonForRevNotStorable();

			JOptionPane.showMessageDialog(UI.getInstance().getMainFrame(),
					GUITools.getMessagePane(messageText), Data.getInstance()
							.getLocaleStr("info"),
					JOptionPane.INFORMATION_MESSAGE);

			return;
		}

		FileChooser fileChooser = UI.getInstance().getFileChooser();

		if (Data.getInstance().getResiData().getReviewPath() != null) {
			fileChooser.setFile(new File(Data.getInstance().getResiData()
					.getReviewPath()));
		} else {
			fileChooser.setFile(new File(Application.getInstance()
					.getApplicationCtl().getReviewNameSuggestion()));
		}

		if (fileChooser.showDialog(UI.getInstance().getMainFrame(),
				FileChooser.MODE_SAVE_FILE, ResiFileFilter.TYPE_REVIEW) == FileChooser.SELECTED_APPROVE) {
			String reviewPath = fileChooser.getFile().getAbsolutePath();

			Object[] options = {
					Data.getInstance().getLocaleStr("button.ignore"),
					Data.getInstance().getLocaleStr("button.correct") };

			if (revMgmt.hasExtRefs()
					&& (reviewPath.trim().toLowerCase().endsWith(ENDING_XML) || reviewPath
							.trim().toLowerCase().endsWith(".xml"))
					&& JOptionPane.showOptionDialog(UI.getInstance()
							.getMainFrame(), GUITools.getMessagePane(Data
							.getInstance().getLocaleStr(
									"message.hasExtRefsXMLWarn")), Data
							.getInstance().getLocaleStr("question"),
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]) == JOptionPane.NO_OPTION) {
				this.setExitApplication(exitApp);
				this.actionPerformed(e);

				return;
			} else {
				new SaveReviewWorker(reviewPath, exitApp).execute();
			}
		}
	}

	/**
	 * Sets the property wether the application should be closed or not.
	 * 
	 * @param exitApplication
	 *            true if the application should be closed
	 */
	public void setExitApplication(boolean exitApplication) {
		this.exitApplication = exitApplication;
	}

}
