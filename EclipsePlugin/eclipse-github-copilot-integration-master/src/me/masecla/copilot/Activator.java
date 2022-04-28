package me.masecla.copilot;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 *激活器类控制插件的生命周期
 */
public class Activator extends AbstractUIPlugin {

	// 插件的ID
	public static final String PLUGIN_ID = "me.masecla.copilot"; //$NON-NLS-1$

	// 共享实例
	private static Activator plugin;
	
	/**
	 * 构造函数
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {// 启动插件
		super.start(context); //   调用父类的方法
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception { // 停止插件
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}