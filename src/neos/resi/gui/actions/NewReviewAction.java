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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import neos.resi.app.model.Data;
import neos.resi.gui.UI;
import neos.resi.gui.workers.NewReviewWorker;
import neos.resi.tools.GUITools;

/**
 * The Class NewReviewAction.
 */
@SuppressWarnings("serial")
public class NewReviewAction extends AbstractAction {

	/**
	 * Instantiates a new new review action.
	 */
	public NewReviewAction() {
		super();

		putValue(SMALL_ICON, Data.getInstance().getIcon("menuNew_16x16.png"));
		putValue(NAME, Data.getInstance().getLocaleStr("menu.file.newReview"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (UI.getInstance().getStatus() == UI.Status.UNSAVED_CHANGES) {
			int option = JOptionPane.showConfirmDialog(neos.resi.gui.UI
					.getInstance().getMainFrame(), GUITools.getMessagePane(Data
					.getInstance().getLocaleStr("message.unsavedChanges")),
					Data.getInstance().getLocaleStr("question"),
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {
				ActionRegistry.getInstance().get(
						SaveReviewAction.class.getName()).actionPerformed(null);

				if (UI.getInstance().getStatus() == UI.Status.DATA_SAVED) {
					new NewReviewWorker().execute();
				}
			} else if (option == JOptionPane.NO_OPTION) {
				new NewReviewWorker().execute();
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		} else {
			new NewReviewWorker().execute();
		}
	}

}