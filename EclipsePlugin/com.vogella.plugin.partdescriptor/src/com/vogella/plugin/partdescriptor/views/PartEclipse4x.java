package com.vogella.plugin.partdescriptor.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;



/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class PartEclipse4x extends ViewPart {
	 private TableViewer viewer;
	 



	@Override
	public void createPartControl(Composite parent) {
		   viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
	                | SWT.V_SCROLL);
	        viewer.setContentProvider(ArrayContentProvider.getInstance());
	        viewer.setLabelProvider(new LabelProvider());
	        viewer.setInput(new String[] {"One", "Two", "Three"});
	}



	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
