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
package neos.resi.gui.actions.severities;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import neos.resi.app.Application;
import neos.resi.gui.UI;

/**
 * The Class PushSeverityTopAction.
 */
@SuppressWarnings("serial")
public class PushSeverityTopAction extends AbstractAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JTable sevTbl = UI.getInstance().getManageSeveritiesDialog()
				.getSeverityTbl();

		int selectedRow = sevTbl.getSelectedRow();

		if (selectedRow != -1) {
			String localSev = Application.getInstance().getSeverityMgmt()
					.getSeverities().get(selectedRow);

			Application.getInstance().getSeverityMgmt().pushTopSeverity(
					localSev);

			UI.getInstance().getManageSeveritiesDialog().getStm()
					.fireTableDataChanged();

			sevTbl.scrollRectToVisible(sevTbl.getCellRect(0, 0,
					false));

			sevTbl.setRowSelectionInterval(0, 0);
		}

		UI.getInstance().getManageSeveritiesDialog().updateButtons();
	}

}
