package com.packtpub.e4.clock.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {// Eclipse 插件激活器

	private static BundleContext context;
	private TrayItem trayItem;
	private Image image;

	static BundleContext getContext() {//
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception { // 插件开始执行
		Activator.context = bundleContext;
		// 创建系统托盘
		final Display display = Display.getDefault(); // 获取默认的Display对象
		display.asyncExec(() -> {// 异步执行
			image = new Image(display, Activator.class.getResourceAsStream("/icons/sample.gif"));
			Tray tray = display.getSystemTray();// 获取系统托盘
			if (tray != null && image != null) {// 判断系统托盘是否存在 image--图片 tray--托盘
				trayItem = new TrayItem(tray, SWT.NONE);// 创建托盘项
				trayItem.setToolTipText("Hello World");// 设置托盘项的提示信息
				trayItem.setVisible(true);// 设置托盘项是否可见
				trayItem.setText("Hello World");// 设置托盘项的文本
				trayItem.setImage(image);// 设置托盘项的图标
			}
		});

	}
		
	public void stop(BundleContext context) throws Exception { //  插件停止了
		Activator.context = null;
	
		if (trayItem != null) {
			Display.getDefault().asyncExec(trayItem::dispose);// 异步执行 trayItem资源释放
		}
		if (image != null) {
			Display.getDefault().asyncExec(image::dispose);// 异步执行 image资源释放
		}
	}

}
