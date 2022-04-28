package com.packtpub.e4.clock.ui;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;// 一个Bundle在框架中的执行上下文。该上下文被用来授权访问其他方法，以便该bundle能够与Framework交互。

public class Activator implements BundleActivator { // BundleActivator是一个接口，它的实现类可以在插件启动时调用，在插件停止时调用。

	private static BundleContext context;// BundleContext是一个接口，它的实现类可以获取插件的上下文信息。

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {// 在插件启动时调用
		Activator.context = bundleContext;// 将 BundleContext 对象赋值给静态变量context, BundleContext 对象可以在插件中获取到插件的相关信息。
	}

	public void stop(BundleContext bundleContext) throws Exception {// 在插件停止时调用
		Activator.context = null;
	}

}
