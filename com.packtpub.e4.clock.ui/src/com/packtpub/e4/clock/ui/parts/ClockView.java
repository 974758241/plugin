package com.packtpub.e4.clock.ui.parts;

import javax.annotation.PostConstruct; // javax javax的x是extension的意思，也就是扩展包
import javax.inject.Inject; //   @Inject 可以用来指定一个类是一个依赖注入类。
import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Optional;// @Optional 可以用来指定一个参数是可选的。  @Optional("default")
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ClockView {
	private Label myLabelInView;// 定义一个Label对象，用于文本

	@PostConstruct // 在构造函数执行完成后执行
	public void createPartControl(Composite parent) {
		System.out.println("构造函数执行完成后,执行UI创建");

		myLabelInView = new Label(parent, SWT.BORDER);// 创建一个Label对象，并设置其属性 . SWT.BORDER 表示显示边框
		myLabelInView.setText("这是一个E4视图的样本");// 设置Label的文本

	}

	@Focus // 当视图获得焦点时，执行
	public void setFocus() {
		myLabelInView.setFocus();

	}

	/**
		保留这个方法是为了与E3兼容。如果你不混合使用E3和E4代码，你可以将其删除。. 
		在E4代码中，你将直接设置ESelectionService中的选择，你不会收到一个ISelection
	 * 
	 * @param s 从JFace收到的selection(E3模式)。
	 *            
	 */
	@Inject //  在构造函数执行完成后执行,在createPartControl()方法之前执行
	@Optional 
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) { // 从JFace收到的selection(E3模式)。  @Named 指定一个名称，以便在插件中使用。 
	System.out.println("收到E3d的selection"); 
		if (s==null || s.isEmpty()) // 如果selection为空，则返回。
			return;

		if (s instanceof IStructuredSelection) { // 如果selection是IStructuredSelection类型，则执行下面的代码。 IStructuredSelection是一个接口，它表示一个结构化的选择。 
			IStructuredSelection iss = (IStructuredSelection) s; // 将selection转换为IStructuredSelection类型。
			if (iss.size() == 1)// 如果selection中只有一个对象，则执行下面的代码。
				setSelection(iss.getFirstElement());// 将selection中的第一个对象设置为当前的selection。
			else
				setSelection(iss.toArray());// 将selection中的所有对象设置为当前的selection。
		}
	}

	/**
	 * 这个方法管理你当前对象的选择。
	 * 在这个例子中，我们监听的是一个单一的Object（甚至是已经在E3模式下捕获的ISelection）。<br/>
	 * 你应该改变你收到的Object的参数类型，以管理你的特定选择
	 * @param o : 当前收到的对象
	 *            
	 */
	@Inject 
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {
		System.out.println("selection对象");

		// 在纯E4模式下删除以下两行，在混合模式下保留它们
		if (o instanceof ISelection) // 已经捕获
			return;

		// 测试标签是否存在（在PostConstruct之前调用注入方法）。
		if (myLabelInView != null)
			myLabelInView.setText("目前单例模式的类 : " + o.getClass());
	}

	/**
	 * 这个方法管理你当前对象的多重选择。
	 * 你应该改变你的对象数组的参数类型来管理你的特定选择
	 * @param o :在多重选择的情况下，当前收到的对象数组
	 *            
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) { // 在多重选择的情况下，当前收到的对象数组
	System.out.println("selected数组");
		//  测试标签是否存在（在PostConstruct之前调用注入方法）。
		if (myLabelInView != null)
			myLabelInView.setText("这是一个多选的 " + selectedObjects.length + " 对象");
	}

	public ClockView() {
		System.out.println("无参构造函数执行了");
		
	}
	public ClockView(Label myLabelInView) {
		super();
		System.out.println("有参构造函数执行了");
		this.myLabelInView = myLabelInView;
	}
}
