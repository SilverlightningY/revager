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
package neos.resi.export;

import neos.resi.app.model.ApplicationData;
import neos.resi.app.model.Data;
import neos.resi.app.model.DataException;
import neos.resi.app.model.appdata.AppSettingKey;
import neos.resi.app.model.schema.Meeting;
import neos.resi.app.model.schema.Review;

/**
 * This class implements the functionality to export a protocol for a certain
 * meeting.
 */
public class MeetingProtocolPDFExporter extends ProtocolPDFExporter {

	/**
	 * Reference to application data.
	 */
	private static ApplicationData appData = Data.getInstance().getAppData();

	/**
	 * Reference to the current review.
	 */
	private static Review review = Data.getInstance().getResiData().getReview();

	/**
	 * The meeting.
	 */
	private Meeting meeting = null;

	/**
	 * True, if the signature fields should be part of the protocol.
	 */
	private boolean showSignFields = false;

	/**
	 * True, if the external product references should be part of the protocol.
	 */
	private boolean attachProdExtRefs = true;

	/**
	 * True, if the external references of the findings should be part of the
	 * protocol.
	 */
	private boolean attachFindExtRefs = true;

	/**
	 * Instantiates a new meeting protocol PDF exporter.
	 * 
	 * @param filePath
	 *            the file path
	 * @param meeting
	 *            the meeting
	 * @param showSignFields
	 *            true, if the signature fields should be part of the protocol
	 * @param attachProdExtRefs
	 *            true, if the external product references should be part of the
	 *            protocol
	 * @param attachFindExtRefs
	 *            true, if the external references of the findings should be
	 *            part of the protocol
	 * 
	 * @throws ExportException
	 *             If an error occurs while instantiating the exporter
	 * @throws DataException
	 *             If an error occurs while getting the data for the export
	 *             process
	 */
	public MeetingProtocolPDFExporter(String filePath, Meeting meeting,
			boolean showSignFields, boolean attachProdExtRefs,
			boolean attachFindExtRefs) throws ExportException, DataException {
		super(filePath, Data.getInstance().getLocaleStr(
				"export.reviewProtocolTitle")
				+ " · " + review.getName(), appData
				.getSetting(AppSettingKey.PDF_PROTOCOL_LOGO), appData
				.getSetting(AppSettingKey.PDF_PROTOCOL_FOOT_TEXT));

		this.meeting = meeting;
		this.showSignFields = showSignFields;
		this.attachProdExtRefs = attachProdExtRefs;
		this.attachFindExtRefs = attachFindExtRefs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see neos.resi.export.PDFExporter#writeContent()
	 */
	@Override
	protected void writeContent() throws ExportException {
		try {
			/*
			 * Write the title page of the protocol
			 */
			writeTitlePage(meeting, attachProdExtRefs);

			pdfDoc.newPage();

			/*
			 * Write the meeting
			 */
			writeMeeting(meeting, attachFindExtRefs, showSignFields);
		} catch (Exception e) {
			/*
			 * Not part of unit testing because this exception is only thrown if
			 * an internal error occurs.
			 */
			throw new ExportException(Data.getInstance().getLocaleStr(
					"message.pdfWriteProtocolFailed"));
		}
	}

}
