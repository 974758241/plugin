package com.packtpub.e4.clock.ui.views;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite; // Composite 作用是容器，它可以包含其他控件，比如Button、Label、Text、Table、List、Combo、Group等。
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

import java.time.LocalTime;
import java.util.Date;

import javax.inject.Inject;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class ClockView extends ViewPart {
	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;

	@Override
	public void createPartControl(Composite parent) { // 绘制界面 Composite--

		/*
		 * viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		 * viewer.setContentProvider(ArrayContentProvider.getInstance());
		 * viewer.setInput(new String[] { "One", "Two", "Three" });
		 * viewer.setLabelProvider(new ViewLabelProvider());
		 * getSite().setSelectionProvider(viewer); makeActions(); hookContextMenu();
		 * hookDoubleClickAction(); contributeToActionBars();
		 */
		// 绘制自定义界面 绘制clock
		final Canvas clock = new Canvas(parent, SWT.NONE);// 创建一个画布 parent是父组件 Canvas的作用是绘制界面 SWT.NONE是没有任何样式
		/*
		 * clock.addPaintListener(new PaintListener() {// 绘制事件 public void
		 * paintControl(PaintEvent e) { e.gc.drawArc(e.x, e.y, e.width - 1, e.height -
		 * 1, 0, 360); } });
		 */
		clock.addPaintListener(this::drawClock);// 添加绘制事件 :: lambda表达式 this--表示 PaintListener


		Runnable redraw = () -> {// 定时器
			while (!clock.isDisposed()) {// 判断是否已经销毁 
				// clock.redraw();// 重绘
				 //在 UI 线程上运行
				clock.getDisplay().asyncExec(() -> clock.redraw());// 异步执行 clock.getDisplay()获取当前线程的Display对象 
				try {
					Thread.sleep(1000);// 休眠1秒
				} catch (InterruptedException e) {
					return;
				}
			}
		};
		new Thread(redraw, "TickTock").start();// 创建线程
		
	

		
	

	}

	private void drawClock(PaintEvent e) {
		e.gc.drawArc(e.x, e.y, e.width - 1, e.height - 1, 0, 360);// e--绘制事件 gc--绘制对象 drawArc--绘制圆弧 e.x--x坐标 e.y--y坐标
																	// e.width--宽度 e.height--高度 0--起始角度 360--结束角度
		int seconds = LocalTime.now().getSecond(); // 获取秒数
		// int arc = (15 - 0) * 6 % 360;// 获取秒针的角度 % 360--取余 arc=90
		int arc = (15 - seconds) * 6 % 360;
		Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);// 获取颜色 SWT.COLOR_BLUE--蓝色
		e.gc.setBackground(blue);// 设置背景色
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);// 绘制秒针 2--绘制的圆弧的角度
		System.out.println(arc - 1);
	}

	@Override
	public void setFocus() {
		// TODO 自动生成的方法存根

	}

}
