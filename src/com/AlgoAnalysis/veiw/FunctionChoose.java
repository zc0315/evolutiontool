package com.AlgoAnalysis.veiw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.AlgoAnalysis.util.Config;

public class FunctionChoose extends JDialog implements ActionListener{
	private MainFrame fawindow;
	private JPanel jp=new JPanel();
	private JPanel jp_N=new JPanel(),jp_C=new JPanel(),jp_C_jp1=new JPanel(),jp_C_jp2=new JPanel(),jp_C_jp3=new JPanel();
	private FlowLayout jp_N_fl=new FlowLayout(FlowLayout.LEFT);
	private BorderLayout jp_bl=new BorderLayout();
	private JLabel jp_N_jl,jp_C_jp3_jl;
	private JButton jp_N_jb,jp_C_jp2_jb1,jp_C_jp2_jb2;
	private JComboBox jp_N_jcb;
	private Vector<String> jp_N_vec,jp_C_jp1_vec;
	private JList jp_C_jp1_list;
	private Container c;
	private JScrollPane jslp;
	private Config fc;
	
	public FunctionChoose(MainFrame owner){
		fawindow=owner;
		fc=new Config("config.properties");
		this.setTitle("选择测试函数库");
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(320, 320);
		int x=Toolkit.getDefaultToolkit().getScreenSize().width;
		int y=Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(x/2-165, y/2-160);
		this.setVisible(true);
		c=this.getContentPane();
		
		jp.setLayout(jp_bl);
		jp.setSize(320, 320);
		
		jp_N.setLayout(jp_N_fl);
		jp_N.setSize(320,40);
		jp_N_jl=new JLabel("选择函数库");
		
		String defualtfunclib=fc.getString("BenchMarkLib");
		
		jp_N_vec=new Vector<String>();
		File file = new File("benchmark");
		String[] files = file.list();
		for (int i = 0; i < files.length; i++) {
		  if(files[i].endsWith(".properties")){
			  //System.out.println(files[i]);
			  int d=files[i].indexOf('.');
			  String s=files[i].substring(0,d);
			  jp_N_vec.add(s);
		  }
		}
		jp_N_jcb=new JComboBox(jp_N_vec);
		jp_N_jcb.setSelectedItem(defualtfunclib);
		jp_N_jcb.addActionListener(this);

		jp_N_jb=new JButton("设定函数");
		jp_N_jb.addActionListener(this);
		
		jp_N.add(jp_N_jl);
		jp_N.add(jp_N_jcb);
		jp_N.add(jp_N_jb);
		
		jp_C.setLayout(new BorderLayout());
		jp_C.setSize(320, 280);
		
		jp_C_jp1.setSize(200, 205);
		
		jp_C_jp1_vec=new Vector<String>();

		fc=new Config("benchmark/"+defualtfunclib+".properties");
		String funclist=fc.getString(defualtfunclib);
		
		String[] funcname =funclist.split(",");
		for(int i=0; i < funcname.length; i++){
			jp_C_jp1_vec.add(funcname[i]);
		}
		jp_C_jp1_list=new JList(jp_C_jp1_vec);
		jp_C_jp1_list.setPreferredSize(new Dimension(150, 500));
		jslp=new JScrollPane(jp_C_jp1_list);
		jp_C_jp1.add(jslp);
		
		jp_C_jp2.setSize(100, 205);
		jp_C_jp2.setLayout(new GridLayout(7,1));
		jp_C_jp2_jb1=new JButton("打开配置文件");
		jp_C_jp2_jb1.addActionListener(this);
		jp_C_jp2_jb2=new JButton("取消");
		jp_C_jp2.add(jp_C_jp2_jb1);
		jp_C_jp2.add(jp_C_jp2_jb2);
		
		jp_C_jp3_jl=new JLabel(new ImageIcon("image/di.jpg"),JLabel.CENTER);
		jp_C_jp3.add(jp_C_jp3_jl);
		
		jp_C.add(jp_C_jp1,"West");jp_C.add(jp_C_jp2,"East");
		jp_C.add(jp_C_jp3,"South");
		
		jp.add(jp_N,"North");
		jp.add(jp_C,"Center");
		
		c.add(jp);
		//this.setModalityType(Dialog.ModalityType.MODELESS);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == jp_N_jb){
			fc=new Config("config.properties");
			String s=(String)jp_N_jcb.getSelectedItem();
			System.out.println("设定函数"+s);
			fc.setValue("BenchMarkLib", s);
			String funname=(String)jp_C_jp1_list.getSelectedValue();
			int index = jp_C_jp1_list.getSelectedIndex();
			if(funname != null){
				System.out.println("选择的函数"+funname);
				fc.setValue("szFuncName",funname);
			}	
			this.setVisible(false);
			fawindow.initcontrolprama(index);
			fawindow.updatefunction();
			
		}else if(jp_N_jcb == e.getSource()){
			int index=jp_N_jcb.getSelectedIndex();
			String funcname=(String)jp_N_jcb.getItemAt(index);
			//System.out.println(funcname);
			jp_C_jp1_vec=new Vector<String>();

			fc=new Config("benchmark/"+funcname+".properties");
			String funclist=fc.getString(funcname);
		
			String[] funcnamelist =funclist.split(",");
			for(int i=0; i < funcnamelist.length; i++){
				jp_C_jp1_vec.add(funcnamelist[i]);
			}
			jp_C_jp1_list.setListData(jp_C_jp1_vec);
			
			//this.validate();
		}else if(jp_C_jp2_jb1 == e.getSource()){
			int index=jp_N_jcb.getSelectedIndex();
			String funcname=(String)jp_N_jcb.getItemAt(index);
			Runtime rn=Runtime.getRuntime();
			 //Process p=null;
			 try{
				 //p=rn.exec("/benchmark/"+funcname+".properties");//输入文件的路径
				 rn.exec("C:\\WINDOWS\\system32\\notepad.exe /benchmark/"+funcname+".properties");
			 }
			 catch(Exception ex){
				 ex.printStackTrace();
				 System.out.println("Wrong!!");
			 }
			 System.out.println(funcname);
		}
	}
	
}
