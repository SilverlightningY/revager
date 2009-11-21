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
package neos.resi.gui.aspects_manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import neos.resi.app.model.Data;
import neos.resi.app.model.appdata.AppAspect;
import neos.resi.app.model.appdata.AppCatalog;
import neos.resi.gui.UI;

/**
 * The Class CheckRenderer.
 */
@SuppressWarnings("serial")
public class CheckRenderer implements TreeCellRenderer {

	final ImageIcon ASP_ICON = Data.getInstance().getIcon("aspect_15x15.png");
	final ImageIcon CATE_ICON = Data.getInstance()
			.getIcon("category_15x15.png");
	final ImageIcon CATA_ICON = Data.getInstance().getIcon("catalog_20x20.png");

	private JPanel rendererPanel;

	protected JCheckBox check;

	protected TreeLabel label;

	/**
	 * Instantiates a new check renderer.
	 */
	public CheckRenderer() {
		rendererPanel = new JPanel(new BorderLayout());
		rendererPanel.setBackground(null);
		rendererPanel.setLayout(new BorderLayout());
		check = new JCheckBox();
		rendererPanel.add(check, BorderLayout.WEST);
		label = new TreeLabel();
		rendererPanel.add(label, BorderLayout.CENTER);
		check.setBackground(UIManager.getColor("Tree.textBackground"));
		label.setForeground(UIManager.getColor("Tree.textForeground"));// Tree.selectionForeground
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.
	 * swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, isSelected,
				expanded, leaf, row, hasFocus);
		rendererPanel.setEnabled(tree.isEnabled());
		check.setSelected(((CheckNode) value).isSelected());
		label.setFont(tree.getFont());
		label.setText(stringValue);
		label.setSelected(isSelected);
		label.setFocus(hasFocus);
		DefaultMutableTreeNode currentTreeNode = (DefaultMutableTreeNode) value;
		Object userObject = (Object) currentTreeNode.getUserObject();

		if (userObject instanceof AppAspect) {
			label.setIcon(ASP_ICON);
		} else if (userObject instanceof AppCatalog) {
			label.setIcon(CATA_ICON);
			label.setFont(UI.LARGE_FONT.deriveFont(Font.BOLD));
		} else if (currentTreeNode.getLevel() == 0) {
			label.setIcon(null);
			label.setFont(UI.LARGE_FONT);
		} else {
			label.setIcon(CATE_ICON);
		}

		return rendererPanel;
	}

	/**
	 * Gets the preferred size.
	 * 
	 * @return the preferred size
	 */
	public Dimension getPreferredSize() {
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		return new Dimension(d_check.width + d_label.width,
				(d_check.height < d_label.height ? d_label.height + 2
						: d_check.height + 2));
	}

	/**
	 * Do layout.
	 */
	public void doLayout() {
		Dimension d_check = check.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		int y_check = 0;
		int y_label = 0;
		if (d_check.height < d_label.height) {
			y_check = (d_label.height - d_check.height) / 2;
		} else {
			y_label = (d_check.height - d_label.height) / 2;
		}
		check.setLocation(0, y_check);
		check.setBounds(0, y_check, d_check.width, d_check.height);
		label.setLocation(d_check.width, y_label);
		label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
	}

	/**
	 * Sets the background.
	 * 
	 * @param color
	 *            the new background
	 */
	public void setBackground(Color color) {
		if (color instanceof ColorUIResource)
			color = null;
		rendererPanel.setBackground(color);
	}

	/**
	 * The Class TreeLabel.
	 */
	public class TreeLabel extends JLabel {
		boolean isSelected;

		boolean hasFocus;

		/**
		 * Instantiates a new tree label.
		 */
		public TreeLabel() {
			setIconTextGap(6);
			setBorder(new EmptyBorder(3, 0, 3, 3));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#setBackground(java.awt.Color)
		 */
		public void setBackground(Color color) {
			if (color instanceof ColorUIResource) {
				color = null;
			}

			super.setBackground(color);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paint(java.awt.Graphics)
		 */
		public void paint(Graphics g) {
			String str;
			if ((str = getText()) != null) {
				if (0 < str.length()) {
					if (isSelected) {
						g.setColor(UIManager
								.getColor("Tree.selectionBackground"));
					} else {
						g.setColor(UIManager.getColor("Tree.textBackground"));
					}

					Dimension d = getPreferredSize();

					int imageOffset = 0;

					Icon currentI = getIcon();

					if (currentI != null) {
						imageOffset = currentI.getIconWidth()
								+ Math.max(0, getIconTextGap() - 3);
					}

					g.fillRect(imageOffset, 2, d.width - 1 - imageOffset, 21);

					if (hasFocus) {
						g.setColor(UIManager
								.getColor("Tree.selectionBorderColor"));
						g.drawRect(imageOffset, 2, d.width - 1 - imageOffset,
								20);
					}
				}
			}

			super.paint(g);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#getPreferredSize()
		 */
		public Dimension getPreferredSize() {
			Dimension retDimension = super.getPreferredSize();
			if (retDimension != null) {
				retDimension = new Dimension(retDimension.width,
						retDimension.height);
			}
			return retDimension;
		}

		/**
		 * Sets the selected.
		 * 
		 * @param isSelected
		 *            the new selected
		 */
		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		/**
		 * Sets the focus.
		 * 
		 * @param hasFocus
		 *            the new focus
		 */
		public void setFocus(boolean hasFocus) {
			this.hasFocus = hasFocus;
		}
	}
}