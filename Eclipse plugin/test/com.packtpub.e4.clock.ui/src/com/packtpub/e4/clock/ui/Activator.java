package com.packtpub.e4.clock.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {//  implements BundleActivator   

	private static BundleContext context;

	static BundleContext getContext() {// 
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception { //
		Activator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
