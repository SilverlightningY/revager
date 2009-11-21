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
package neos.resi.gui.actions.attendee;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import neos.resi.app.Application;
import neos.resi.app.model.schema.Attendee;
import neos.resi.app.model.schema.Protocol;
import neos.resi.gui.UI;

/**
 * The Class RemAttFromProtAction.
 */
@SuppressWarnings("serial")
public class RemAttFromProtAction extends AbstractAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Protocol prot = UI.getInstance().getProtocolFrame().getCurrentProt();
		int selRow = UI.getInstance().getProtocolFrame().getPresentAttTable()
				.getSelectedRow();
		if (selRow != -1) {
			Attendee selAtt = Application.getInstance().getProtocolMgmt()
					.getAttendees(prot).get(selRow);
			Application.getInstance().getProtocolMgmt().removeAttendee(selAtt,
					prot);
			UI.getInstance().getProtocolFrame().getPatm().setProtocol(prot);
			UI.getInstance().getProtocolFrame().getPatm()
					.fireTableDataChanged();
			UI.getInstance().getProtocolFrame().updateAttButtons();
		}
	}

}
