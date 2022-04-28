
package com.packtpub.e4.clock.ui;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class ClockWidget extends Canvas {
	private final Color color; // swt color object
	private ZoneId zone = ZoneId.systemDefault();// 方法获取系统默认时区

	public ClockWidget(Composite parent, int style, RGB rgb) {
		super(parent, style);
		this.color = new Color(parent.getDisplay(), rgb);// 得到颜色对象
		addDisposeListener(e -> color.dispose()); // 当窗口关闭时，释放颜色对象
		addPaintListener(this::drawClock);// 添加绘制事件
		Runnable redraw = () -> { // 创建线程
			while (!this.isDisposed()) {
				this.getDisplay().asyncExec(() -> this.redraw());// 异步执行绘制
				try {
					Thread.sleep(1000);// 线程休眠1秒
				} catch (InterruptedException e) { // 异常处理
					return;
				}
			}
		};
		new Thread(redraw, "TickTock").start();// 创建线程并启动
	}

	private void drawClock(PaintEvent e) {
		e.gc.drawArc(e.x, e.y, e.width - 1, e.height - 1, 0, 360);
		ZonedDateTime now = ZonedDateTime.now(zone);// 得到当前时间
		int seconds = now.getSecond();// 得到秒
		int arc = (15 - seconds) * 6 % 360;
		if (handColor == null) {// 判断颜色是否为空
			e.gc.setBackground(color);// 设置背景颜色
		} else {
			e.gc.setBackground(handColor);
		}
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 1, 2);// 绘制秒针弧度
		e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_BLACK));// 设置背景颜色 SWT.COLOR_BLACK为黑色
		int hours = now.getHour();// 得到小时
		arc = (3 - hours) * 30 % 360;// 计算小时的角度
		e.gc.fillArc(e.x, e.y, e.width - 1, e.height - 1, arc - 5, 10); // 绘制小时的圆弧
	}

	public Point computeSize(int w, int h, boolean changed) {// 计算尺寸
		int size;
		if (w == SWT.DEFAULT) {
			size = h;
		} else if (h == SWT.DEFAULT) {
			size = w;
		} else {
			size = Math.min(w, h);
		}
		if (size == SWT.DEFAULT) {
			size = 50;
		}
		return new Point(size, size);
	}

	public void setZone(ZoneId zone) {
		this.zone = zone;
	}

	// This does not work - add a dispose listener instead
	// @Override
	// public void dispose() {
	// if (color != null && !color.isDisposed())
	// color.dispose();
	// super.dispose();
	// }

	private Color handColor; 

	public Color getHandColor() {// 获取颜色
		if (handColor == null) {
			return color;
		} else {
			return handColor;
		}
	}

	public void setHandColor(Color color) {// 设置颜色
		handColor = color;
	}
}
