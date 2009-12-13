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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import neos.resi.app.Application;
import neos.resi.app.model.Data;
import neos.resi.app.model.DataException;
import neos.resi.app.model.appdata.AppAttendee;
import neos.resi.app.model.schema.Attendee;
import neos.resi.app.model.schema.Role;
import neos.resi.gui.UI;
import neos.resi.gui.dialogs.AttendeeDialog;
import neos.resi.tools.GUITools;

/**
 * The Class ConfirmAttendeeAction.
 */
@SuppressWarnings("serial")
public class ConfirmAttendeeAction extends AbstractAction {

	private Role[] roles = Role.values();
	private String attContact;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		AttendeeDialog attDialog = UI.getInstance().getAttendeeDialog();

		JTextField nameTxtFld = attDialog.getNameTxtFld();
		JScrollPane contactScrllPn = attDialog.getContactScrllPn();

		nameTxtFld.setBorder(UI.STANDARD_BORDER_INLINE);
		contactScrllPn.setBorder(UI.STANDARD_BORDER);

		String attName = nameTxtFld.getText();
		if(attDialog.getContactTxtArea().getText()!=null)
			attContact = attDialog.getContactTxtArea().getText();
		else
			attContact = "";
			
		Role attRole = roles[attDialog.getRoleBox().getSelectedIndex()];

		boolean nameMissing = false;

		String message = "";

		if (attName.trim().equals("")) {
			nameMissing = true;
		}


		if (nameMissing) {
			message = Data.getInstance().getLocaleStr(
					"attendeeDialog.message.noName");

			attDialog.setMessage(message);
			nameTxtFld.setBorder(UI.MARKED_BORDER_INLINE);
		} else {
			AppAttendee currAppAtt = attDialog.getCurrentAppAttendee();
			Attendee currAtt = attDialog.getCurrentAttendee();

			/*
			 * Update the app attendee in the database
			 */
			try {
				if (currAppAtt == null) {
					currAppAtt = Data.getInstance().getAppData().getAttendee(
							attName, attContact);

					if (currAppAtt == null) {
						currAppAtt = Data.getInstance().getAppData()
								.newAttendee(attName, attContact);
					}
				} else {
					currAppAtt.setNameAndContact(attName, attContact);
				}

				for (String str : currAppAtt.getStrengths()) {
					currAppAtt.removeStrength(str);
				}

				for (String str : attDialog.getStrengthList()) {
					currAppAtt.addStrength(str);
				}
			} catch (DataException e) {
				JOptionPane.showMessageDialog(attDialog, GUITools
						.getMessagePane(e.getMessage()), Data.getInstance()
						.getLocaleStr("error"), JOptionPane.ERROR_MESSAGE);
			}

			/*
			 * update the review attendee
			 */
			Attendee newAtt = new Attendee();

			newAtt.setName(attName);
			newAtt.setContact(attContact);
			newAtt.setRole(attRole);

			if (currAtt == null) {
				if (!Application.getInstance().getAttendeeMgmt().isAttendee(
						newAtt)) {
					Application.getInstance().getAttendeeMgmt().addAttendee(
							attName, attContact, attRole, null);
				} else {
					attDialog.setMessage(Data.getInstance().getLocaleStr(
							"attendeeDialog.message.attendeeDupl"));

					return;
				}
			} else {
				newAtt.setAspects(currAtt.getAspects());

				if (Application.getInstance().getAttendeeComp().compare(
						currAtt, newAtt) != 0) {
					if (!Application.getInstance().getAttendeeMgmt()
							.editAttendee(currAtt, newAtt)) {
						attDialog.setMessage(Data.getInstance().getLocaleStr(
								"attendeeDialog.message.attendeeDupl"));

						return;
					}
				}
			}

			attDialog.setVisible(false);

			UI.getInstance().getMainFrame().updateAttendeesTable(false);
			UI.getInstance().getMainFrame().updateButtons();

			UI.getInstance().getAspectsManagerFrame().updateViews();
		}
	}
	
}
