package com.AlgoAnalysis.veiw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.AlgoAnalysis.util.AlgoModel;
import com.AlgoAnalysis.util.Config;

public class AlgorithmsChoose extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame fawindow;
	private Container c;
	private JPanel jp_C,jp_S;
	private JTable jp_C_jt;
	private AlgoModel am;
	private JButton jp_S_jb1,jp_S_jb2,jp_S_jb3;
	private Config cf;
	private JLabel jl_zy;
	
	public AlgorithmsChoose(MainFrame owner){
		fawindow=owner;
		c=this.getContentPane();
		this.setSize(480, 240);
		this.setVisible(true);
		int x=Toolkit.getDefaultToolkit().getScreenSize().width;
		int y=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(x/2-240, y/2-120);
		this.setTitle("算法选择");
		c.setLayout(new BorderLayout());
		
		am=new AlgoModel();
		jp_C=new JPanel(new BorderLayout());		
		jp_C_jt=new JTable(am);
		jp_C_jt.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		jp_C.add(jp_C_jt);
		c.add(jp_C,"Center");
		
		jp_S=new JPanel();
		jp_S_jb1=new JButton("设定算法");
		jp_S_jb1.addActionListener(this);
		
		jp_S_jb2=new JButton("打开配置文件");
		jp_S_jb2.addActionListener(this);
		jp_S_jb3=new JButton("取消");
		
		
		jl_zy = new JLabel("多选：Ctrl+左键");
		jp_S.add(jp_S_jb1);jp_S.add(jp_S_jb2);jp_S.add(jp_S_jb3);
		jp_S.add(jl_zy);
		c.add(jp_S,"South");
		
	}

	public void actionPerformed(ActionEvent e) {
		if(jp_S_jb2 == e.getSource()){
			int index=jp_C_jt.getSelectedRow();
			if(index > 0){
			String sname=(String)am.getValueAt(index, 1);
			Runtime rn=Runtime.getRuntime();
			 //Process p=null;
			 try{
				 //p=rn.exec("/benchmark/"+funcname+".properties");//输入文件的路径
				 rn.exec("C:\\WINDOWS\\system32\\notepad.exe algorithm\\"+sname+".properties");
			 }
			 catch(Exception ex){
				 ex.printStackTrace();
				 System.out.println("Wrong!!");
			 }
			}else {
				JOptionPane.showMessageDialog(this, "请选择算法！");
			}
		}else if(jp_S_jb1 == e.getSource()){
			System.out.println("设定算法");
			cf=new Config("config.properties");
			int index[]=jp_C_jt.getSelectedRows();
			for(int i=0; i<index.length; i++){
				System.out.println(index[i]);
			}
			String alname="";
			for(int i=0;i < index.length-1; i++){
				 alname=alname+(String)am.getValueAt(index[i], 1)+",";
			}
			alname+=(String)am.getValueAt(index[index.length-1], 1);
			cf.setValue("AlgorithmLib", alname);
	
			this.setVisible(false);
		
		}
	}
	
	
	
}
