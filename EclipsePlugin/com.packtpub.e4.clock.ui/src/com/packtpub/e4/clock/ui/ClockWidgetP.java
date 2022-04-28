package com.packtpub.e4.clock.ui;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ClockWidgetP extends Canvas {
	private final Color color;
	private ZoneId zone = ZoneId.systemDefault();
	

	public void setZone(ZoneId zone) {
		this.zone = zone;
	}

	public ZoneId getZone() {
		return zone;
	}

	public ClockWidgetP(Composite parent, int style, RGB rgb) {
		super(parent, style);
		this.color = new Color(parent.getDisplay(), rgb);
		addPaintListener(this::drawClock);
		addDisposeListener(e -> color.dispose());// 当窗口关闭时，释放颜色对象
		Runnable redraw = () -> {
			while (!this.isDisposed()) {
				this.getDisplay().asyncExec(() -> this.redraw());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		};
		new Thread(redraw, "TickTock").start();
	}

	private void drawClock(PaintEvent e) {
		e.gc.drawArc(e.x, e.y, e.width - 1, e.height - 1, 0, 360);// e--绘制事件 gc--绘制对象 drawArc--绘制圆弧 e.x--x坐标 e.y--y坐标
		ZonedDateTime now = ZonedDateTime.now(zone);													// e.width--宽度 e.height--高度 0--起始角度 360--结束角度
		int seconds = now.getSecond(); // 获取秒数
		// int arc = (15 - 0) * 6 % 360;// 获取秒针的角度 % 360--取余 arc=90
		int arc = (15 - seconds) * 6 % 360;
		Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);// 获取颜色 SWT.COLOR_BLUE--蓝色
		e.gc.setBackground(this.color);// 设置背景色
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);// 绘制秒针 2--绘制的圆弧的角度

//绘制分针
		int minutes = now.getMinute();
		arc = (15 - minutes) * 6 % 360;
		Color green = e.display.getSystemColor(SWT.COLOR_GREEN);
		e.gc.setBackground(green);
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);

		int hours = now.getHour();
		arc = (15 - hours) * 30 % 360; // 每小时30度
		Color red = e.display.getSystemColor(SWT.COLOR_RED);
		e.gc.setBackground(red);
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);

	}

	public Point computeSize(int w, int h, boolean changed) {
		int size;
		if (w == SWT.DEFAULT) {// 如果宽度为默认值
			size = h;
		} else if (h == SWT.DEFAULT) {
			size = w;
		} else {
			size = Math.min(w, h);
		}
		if (size == SWT.DEFAULT) {
			size = 50;
		}
		return new Point(size, size);//  返回组件的大小
	}

	@Override
	public void dispose() {// 释放资源
		color.dispose();
		super.dispose();
	}
}
