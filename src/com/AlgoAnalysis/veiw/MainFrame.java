package com.AlgoAnalysis.veiw;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartPanel;


import com.AlgoAnalysis.Algorithm.BA;
import com.AlgoAnalysis.Algorithm.DE;
import com.AlgoAnalysis.tools.ImagePanel;
import com.AlgoAnalysis.util.ChartUtil;
import com.AlgoAnalysis.util.Config;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements MouseListener,ActionListener{
	
	private JPanel jp_S,jp_jtbp,jp_N_jp1,jp_N_jp11,jp_N_jp11_jp1,jp_N_jp12,jp_N_jp2,jp_N_jp3,jp_N_jprun;
	//北部组件
	private ImagePanel jp_N;
	private JPanel jcbp,jlbp;
	private JPanel jp_N_jp21,jp_N_jp21_jp1,jp_N_jp21_jp2;
	private JPanel jp_N_jp21_eastp,jp_N_jp21_westp;
	private JLabel jp_N_jp2_jl1,jp_N_jp2_jl2,jp_N_jp2_jl3;
	

	private JLabel jp_N_jp2_jl4,jp_N_jp2_jl5,jp_N_jp2_jl6;
	private JLabel jp_N_jp21_jlz1,jp_N_jp21_jlz2;
	private JCheckBox jp_N_jp2_jcb1,jp_N_jp2_jcb2,jp_N_jp2_jcb3;
	private JPanel jp_N_jp22;
	private JLabel jp_N_jp22_jl;
	private JTabbedPane jtbp;
	private Image icon,northbg;
	private JLabel jp_N_jl,jp_N_yunxing,jp_N_chazhao,jp_N_fenxi,jp_N_qingkong,jp_N_fenge;
	private JLabel  jp_N_jp11_jl1,jp_N_jp11_jl2,jp_N_jp11_jl3;
	private JComboBox jp_N_jp11_jc1,jp_N_jp11_jc2,jp_N_jp11_jc3;
	private Vector<String> jc1Item,jc2Item,jc3Item;
	
	private JLabel jp_N_byunxing,jp_N_bchazhao,jp_N_bshuju,jp_N_bqingkong;
	private JLabel jp_N_bhuitu,jp_N_bceshi,jp_N_bkedu;
	//中间的组件
	private JSplitPane jsp;
	private JPanel jp_C_L,jp_C_R;
	private JPanel jp_C_R_jpN,jp_C_R_jpC;
	
	//中间  右部的组件
	
	//北部
	private JPanel jp_C_R_jpN_jp1,jp_C_R_jpN_jp2;
	private JPanel jp_C_R_jpN_jp1_jpl,jp_C_R_jpN_jp1_jpr;
	private JLabel jp_C_R_jpN_jl1,jp_C_R_jpN_jl2,jp_C_R_jpN_jl3,jp_C_R_jpN_jl4,jp_C_R_jpN_jl5,jp_C_R_jpN_jl6;
	private JSplitPane jp_C_R_jpC_jsp;
	//中部
	private JLabel jp_C_R_jpC_jl1,jp_C_R_jpC_jl2,jp_C_R_jpC_jl3,jp_C_R_jpC_jl4,jp_C_R_jpC_jl5,
				   jp_C_R_jpC_jl6,jp_C_R_jpC_jl7,jp_C_R_jpC_jl8,jp_C_R_jpC_jl9,jp_C_R_jpC_jl10;
	private JTextField jp_C_R_jpC_jf1,jp_C_R_jpC_jf2,jp_C_R_jpC_jf3,jp_C_R_jpC_jf4,jp_C_R_jpC_jf5,
					   jp_C_R_jpC_jf6,jp_C_R_jpC_jf7,jp_C_R_jpC_jf8,jp_C_R_jpC_jf9;
	private JPanel jp_C_R_jpC_jp1,jp_C_R_jpC_jp2,jp_C_R_jpC_jp3;
	
	//中间 左部分的组件
	private JPanel jp_C_L_jpN,jp_C_L_jpC;
	private JLabel jl_func;
	
	//右下角标签
	private JLabel jp_C_L_jpC_jl[]=new JLabel[9];
	//报表面板
	private ChartPanel chartp;
	//配置信息
	private Config cf;
	
	public static double value[][];       //最优解
	public static double fitness[][];     //最优解的适应度
	public static double avgfitness[][];	//本代平均适应度
	public static double time[][];
	
	private int m_varNum = 0;
	private double m_lb = 0;
	private double m_ub = 0;
	
	private int m_valueType  = -1;
	
	private String alg_name[];
	private int kedu,maxcycle;
	
	public MainFrame(){
		cf=new Config("config.properties");
		try {
			icon =ImageIO.read(new File("image/title.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int h=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setSize(950,600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(w/2-475, h/2-300);
		this.setLayout(new BorderLayout());
		this.setIconImage(icon);
		
		//北部分
		creatNorthPanel();
		this.add(jtbp,"North");
		//this.add(jp_N,"North");
		
		//中间部分
		creatCenterPanel();
		this.add(jsp,"Center");		
		
		//南部分
		this.validate();
	}
	
	public void creatNorthPanel(){
		try {
			northbg=ImageIO.read(new File("image/northbg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jp_N=new ImagePanel(northbg);
		jp_N.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_N.setSize(950, 90);
		
		jp_N_jp1=new JPanel();
		jp_N_jp1.setLayout(new BorderLayout());
		jp_N_jp1.setSize(375, 90);
		
		jp_N_jp11=new JPanel();
		
		jp_N_jp11.setLayout(new BorderLayout());
		jp_N_jp11.setBackground(new Color(201,217,237));
		
		jp_N_jprun=new JPanel(new GridLayout(1,4,0,0));
		jp_N_jprun.setBackground(new Color(211,227,242));
		jp_N_yunxing=new JLabel(new ImageIcon("image/yunxing.jpg"),JLabel.CENTER);
		jp_N_yunxing.addMouseListener(this);
		jp_N_chazhao=new JLabel(new ImageIcon("image/jieguo.jpg"),JLabel.CENTER);
		jp_N_chazhao.addMouseListener(this);
		jp_N_fenxi=new JLabel(new ImageIcon("image/fenxi.jpg"),JLabel.CENTER);
		jp_N_fenxi.addMouseListener(this);
		jp_N_qingkong=new JLabel(new ImageIcon("image/qingkong.gif"),JLabel.CENTER);
		jp_N_qingkong.addMouseListener(this);
		jp_N_jprun.add(jp_N_yunxing);
		jp_N_jprun.add(jp_N_chazhao);
		jp_N_jprun.add(jp_N_fenxi);
		jp_N_jprun.add(jp_N_qingkong);
		jp_N_jp11.add(jp_N_jprun,"West");
		jp_N_fenge=new JLabel(new ImageIcon("image/fenbar.jpg"),JLabel.CENTER);
		jp_N_fenge.setSize(1,64);
		jp_N_jp11.add(jp_N_fenge,"Center");
		
		jp_N_jp11_jp1=new JPanel();
		jp_N_jp11_jp1.setLayout(new BorderLayout());
		jp_N_jp11_jp1.setBackground(new Color(211,227,242));
		jp_N_jp11_jp1.setSize(50, 69);
		//标签
		jlbp=new JPanel(new GridLayout(3,1,0,0));
		jp_N_jp11_jl1=new JLabel(new ImageIcon("image/huitu.jpg"),JLabel.CENTER);
		jp_N_jp11_jl1.addMouseListener(this);
		
		jp_N_jp11_jl2=new JLabel(new ImageIcon("image/ceshi.jpg"),JLabel.CENTER);
		jp_N_jp11_jl2.addMouseListener(this);
		
		jp_N_jp11_jl3=new JLabel(new ImageIcon("image/kedu.jpg"),JLabel.CENTER);
		jp_N_jp11_jl3.addMouseListener(this);
		jlbp.add(jp_N_jp11_jl1);jlbp.add(jp_N_jp11_jl2);jlbp.add(jp_N_jp11_jl3);
		
		jcbp=new JPanel(new GridLayout(3,1,0,0));
		//下拉框
		jc1Item=new Vector<String>();
		jc1Item.add("Value/Cycle");jc1Item.add("Fitness/Cycle");
		jc1Item.add("AvgFitness/Cycle");jc1Item.add("Value/Time");
		jc1Item.add("Fitness/Time");jc1Item.add("Value/ECount");
		jp_N_jp11_jc1=new JComboBox(jc1Item);
		jp_N_jp11_jc1.setPreferredSize(new Dimension(100,20));
		jp_N_jp11_jc1.addActionListener(this);
		
		String funcname=cf.getString("BenchMarkLib");
		Config cf1=new Config("benchmark/"+funcname+".properties");
		String funclist=cf1.getString(funcname);
		
		//函数选择
		jc2Item=new Vector<String>();
		//System.out.println(funclist);
		String[] funcnamelist =funclist.split(",");
		for(int i=0; i < funcnamelist.length; i++){
			jc2Item.add(funcnamelist[i]);
		}
		jp_N_jp11_jc2=new JComboBox(jc2Item);
		jp_N_jp11_jc2.setPreferredSize(new Dimension(100,20));
		jp_N_jp11_jc2.setSelectedItem(cf.getString("szFuncName"));
		//jp_N_jp11_jc2.setBounds(25, 23, 25, 23);
		jp_N_jp11_jc2.addActionListener(this);
		
		
		//刻度选择
		jc3Item=new Vector<String>();
		jc3Item.add("10");jc3Item.add("20");
		jc3Item.add("50");jc3Item.add("100");
		jp_N_jp11_jc3=new JComboBox(jc3Item);
		jp_N_jp11_jc3.setPreferredSize(new Dimension(100,20));
		//jp_N_jp11_jc3.setBounds(25, 46, 25, 23);
		jcbp.add(jp_N_jp11_jc1);jcbp.add(jp_N_jp11_jc2);jcbp.add(jp_N_jp11_jc3);
		
		jp_N_jp11_jp1.add(jlbp,"West");jp_N_jp11_jp1.add(jcbp,"Center");
		//添加
		jp_N_jp11.add(jp_N_jp11_jp1,"East");
		
		jp_N_jp12=new JPanel();
		jp_N_jl=new JLabel("运行",JLabel.CENTER);
		jp_N_jl.setSize(375, 20);
		jp_N_jp12.setBackground(new Color(193,216,240));
		jp_N_jp12.setSize(375, 20);
		jp_N_jp12.setPreferredSize(new Dimension(375,20));
		jp_N_jp12.add(jp_N_jl);
		
		jp_N_jp1.add(jp_N_jp11,"Center");
		jp_N_jp1.add(jp_N_jp12,"South");
		jp_N_jp1.setPreferredSize(new Dimension(375,90));
		jp_N_jp1.setBorder(new LineBorder(new Color(231,239,247)));
		//第二块
		jp_N_jp2=new JPanel(new BorderLayout());
		jp_N_jp2.setSize(240, 90);
		jp_N_jp2.addMouseListener(this);
		//北部总体布局
		jp_N_jp21=new JPanel(new BorderLayout());
		jp_N_jp21.setBackground(new Color(207,223,239));

		jp_N_jp21_westp=new JPanel(new BorderLayout());
		jp_N_jp21_westp.setBackground(new Color(207,223,239));
		//西部的第一小块
		jp_N_jp21_jp1=new JPanel(new GridLayout(3,1,0,0));
		jp_N_jp21_jp1.setBackground(new Color(207,223,239));
		jp_N_jp2_jl1=new JLabel(new ImageIcon("image/hanshu.jpg"),JLabel.CENTER);
		jp_N_jp2_jl1.addMouseListener(this);
		jp_N_jp2_jl2=new JLabel(new ImageIcon("image/suanfa.jpg"),JLabel.CENTER);
		jp_N_jp2_jl2.addMouseListener(this);
		jp_N_jp2_jl3=new JLabel(new ImageIcon("image/xuanxiang.jpg"),JLabel.CENTER);
		jp_N_jp2_jl3.addMouseListener(this);
		jp_N_jp21_jp1.add(jp_N_jp2_jl1);jp_N_jp21_jp1.add(jp_N_jp2_jl2);
		jp_N_jp21_jp1.add(jp_N_jp2_jl3);
		
		//西部的第二小块
		jp_N_jp21_jlz1=new JLabel(new ImageIcon("image/fenbar.jpg"),JLabel.CENTER);
		jp_N_jp21_jlz1.setBackground(new Color(207,223,239));
		//西部的第三小块
		jp_N_jp21_jp2=new JPanel(new GridLayout(3,1,0,0));
		jp_N_jp21_jp2.setBackground(new Color(207,223,239));
		jp_N_jp2_jcb1=new JCheckBox("3D显示");
		
		//jp_N_jp2_jcb1.setPreferredSize(new Dimension())
		jp_N_jp2_jcb1.setBackground(new Color(207,223,239));
		jp_N_jp2_jcb2=new JCheckBox("显示函数",true);
		jp_N_jp2_jcb2.setBackground(new Color(207,223,239));
		jp_N_jp2_jcb2.addActionListener(this);
		
		jp_N_jp2_jcb3=new JCheckBox("批量测试");
		jp_N_jp2_jcb3.setBackground(new Color(207,223,239));
		jp_N_jp21_jp2.add(jp_N_jp2_jcb1);jp_N_jp21_jp2.add(jp_N_jp2_jcb2);
		jp_N_jp21_jp2.add(jp_N_jp2_jcb3);
		//西部组件添加
		jp_N_jp21_westp.add(jp_N_jp21_jp1,"West");
		jp_N_jp21_westp.add(jp_N_jp21_jlz1,"Center");
		jp_N_jp21_westp.add(jp_N_jp21_jp2,"East");
		
		jp_N_jp21_jlz2=new JLabel(new ImageIcon("image/fenbar.jpg"),JLabel.CENTER);
		jp_N_jp21_jlz2.setBackground(new Color(207,223,239));
		//东部
		jp_N_jp21_eastp=new JPanel(new GridLayout(3,1,0,0));
		jp_N_jp21_eastp.setBackground(new Color(207,223,239));
		jp_N_jp2_jl4=new JLabel(new ImageIcon("image/fangda.jpg"),JLabel.CENTER);
		jp_N_jp2_jl4.addMouseListener(this);
		jp_N_jp2_jl5=new JLabel(new ImageIcon("image/suoxiao.jpg"),JLabel.CENTER);
		jp_N_jp2_jl5.addMouseListener(this);
		jp_N_jp2_jl6=new JLabel(new ImageIcon("image/shuaxin.jpg"),JLabel.CENTER);
		jp_N_jp2_jl6.addMouseListener(this);
		jp_N_jp21_eastp.add(jp_N_jp2_jl4);jp_N_jp21_eastp.add(jp_N_jp2_jl5);
		jp_N_jp21_eastp.add(jp_N_jp2_jl6);
		
		//
		jp_N_jp21.add(jp_N_jp21_eastp,"East");
		jp_N_jp21.add(jp_N_jp21_jlz2,"Center");
		jp_N_jp21.add(jp_N_jp21_westp,"West");
		
		jp_N_jp2.add(jp_N_jp21,"Center");
		
		jp_N_jp22=new JPanel();
		jp_N_jp22.setSize(240, 20);
		jp_N_jp22.setBackground(new Color(193,216,240));
		jp_N_jp22_jl=new JLabel("配置",JLabel.CENTER);
		jp_N_jp22.add(jp_N_jp22_jl);
		jp_N_jp22.setPreferredSize(new Dimension(240,20));
		jp_N_jp2.add(jp_N_jp22,"South");
		jp_N_jp2.setPreferredSize(new Dimension(240,90));
		jp_N_jp2.setBorder(new LineBorder(new Color(231,239,247)));
		jp_N.add(jp_N_jp1);
		jp_N.add(jp_N_jp2);
		jtbp =new JTabbedPane();
		jtbp.add("主控",jp_N);
		jtbp.setSize(950, 90);
	}
	
	public void creatCenterPanel(){
		jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT );
		jsp.setSize(960, 400);
		jsp.setDividerLocation(690);
		jsp.setDividerSize(5);
		//jsp.setBackground(new Color(201,217,237));
		//左面板
		jp_C_L=new JPanel(new BorderLayout());
		jp_C_L.setSize(690, 400);
		jp_C_L.setBackground(Color.white);
		
		//左面板 北部分
		jp_C_L_jpN=new JPanel();
		jp_C_L_jpN.setLayout(null);
		jp_C_L_jpN.setBackground(Color.white);
		jp_C_L_jpN.setPreferredSize(new Dimension(600,125));
		
		jp_N_byunxing=new JLabel(new ImageIcon("image/byunxing.jpg"),JLabel.CENTER);
		jp_C_L_jpN.add(jp_N_byunxing);
		jp_N_byunxing.setBounds(8, 3, 220, 50);
		jp_N_byunxing.setVisible(false);
		
		jp_N_bchazhao=new JLabel(new ImageIcon("image/bchakan.jpg"),JLabel.CENTER);
		jp_C_L_jpN.add(jp_N_bchazhao);
		jp_N_bchazhao.setBounds(60, 0, 210, 40);
		jp_N_bchazhao.setVisible(false);
		
		jp_N_bshuju=new JLabel(new ImageIcon("image/bshuju.jpg"),JLabel.CENTER);
		jp_C_L_jpN.add(jp_N_bshuju);
		jp_N_bshuju.setBounds(115, 0, 210, 40);
		jp_N_bshuju.setVisible(false);
		
		jp_N_bqingkong=new JLabel(new ImageIcon("image/bqingkong.jpg"),JLabel.CENTER);
		jp_C_L_jpN.add(jp_N_bqingkong);
		jp_N_bqingkong.setBounds(165, 0, 210, 40);
		jp_N_bqingkong.setVisible(false);
		
		jp_N_bhuitu=new JLabel(new ImageIcon("image/bhuitu.jpg"),JLabel.CENTER);
		jp_C_L_jpN.add(jp_N_bhuitu);
		jp_N_bhuitu.setBounds(220, 0, 210, 40);
		jp_N_bhuitu.setVisible(false);
		
		jp_N_bceshi=new JLabel(new ImageIcon("image/bceshi.jpg"),JLabel.CENTER);
		jp_C_L_jpN.add(jp_N_bceshi);
		jp_N_bceshi.setBounds(220, 0, 210, 40);
		jp_N_bceshi.setVisible(false);
		
		jp_N_bkedu=new JLabel(new ImageIcon("image/bkedu.jpg"),JLabel.CENTER);
		jp_C_L_jpN.add(jp_N_bkedu);
		jp_N_bkedu.setBounds(220, 0, 210, 40);
		jp_N_bkedu.setVisible(false);
		
		jl_func = new JLabel(new ImageIcon("function/"+cf.getString("szFuncName")+".jpg"),JLabel.CENTER);
		System.out.println(cf.getString("szFuncName"));
		jp_C_L_jpN.add(jl_func);
		jl_func.setBounds(50, 40, 500, 55);
		jl_func.setVisible(true);
		
		
		jp_C_L.add(jp_C_L_jpN,"North");
		
		//
		jp_C_L_jpC=new JPanel();
		jp_C_L_jpC.setBackground(Color.white);
		jp_C_L.add(jp_C_L_jpC,"Center");
		//右面板
		jp_C_R=new JPanel();
		jp_C_R.setLayout(new BorderLayout());
		jp_C_R.setSize(260, 400);
		
		//右面板  南部分
		jp_C_R_jpN=new JPanel();
		jp_C_R_jpN.setSize(260, 42);
		jp_C_R_jpN.setLayout(new BorderLayout());
		
		jp_C_R_jpN_jp1=new JPanel();
		jp_C_R_jpN_jp1.setSize(260, 20);
		jp_C_R_jpN_jp1.setLayout(new GridLayout(1,2));
		jp_C_R_jpN_jp1.setBackground(new Color(184,205,232));
		
		jp_C_R_jpN_jp1_jpl=new JPanel();
		jp_C_R_jpN_jp1_jpl.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_C_R_jpN_jl1=new JLabel(new ImageIcon("image/shuxing.jpg"),JLabel.CENTER);
		jp_C_R_jpN_jp1_jpl.setBackground(new Color(184,205,232));
		jp_C_R_jpN_jp1_jpl.add(jp_C_R_jpN_jl1);

		jp_C_R_jpN_jp1_jpr=new JPanel();
		jp_C_R_jpN_jp1_jpr.setLayout(new FlowLayout(FlowLayout.RIGHT));
		jp_C_R_jpN_jp1_jpr.setBackground(new Color(184,205,232));
		jp_C_R_jpN_jl2=new JLabel(new ImageIcon("image/close.jpg"),JLabel.CENTER);
		jp_C_R_jpN_jl2.addMouseListener(this);
		jp_C_R_jpN_jp1_jpr.add(jp_C_R_jpN_jl2);
		
		jp_C_R_jpN_jp1.add(jp_C_R_jpN_jp1_jpl);
		jp_C_R_jpN_jp1.add(jp_C_R_jpN_jp1_jpr);
		
		//第二块
		jp_C_R_jpN_jp2=new JPanel();
		jp_C_R_jpN_jp2.setSize(260, 22);
		jp_C_R_jpN_jp2.setBackground(new Color(210,229,255));
		jp_C_R_jpN_jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_C_R_jpN_jl3 = new JLabel(new ImageIcon("image/jiajian.jpg"),JLabel.CENTER);
		jp_C_R_jpN_jl3.addMouseListener(this);
		jp_C_R_jpN_jl4 = new JLabel(new ImageIcon("image/ziti.jpg"),JLabel.CENTER);
		jp_C_R_jpN_jl4.addMouseListener(this);
		jp_C_R_jpN_jl5 = new JLabel(new ImageIcon("image/wen.jpg"),JLabel.CENTER);
		jp_C_R_jpN_jl5.addMouseListener(this);
		jp_C_R_jpN_jl6 = new JLabel(new ImageIcon("image/dian.jpg"),JLabel.CENTER);
		jp_C_R_jpN_jl6.addMouseListener(this);
		
		jp_C_R_jpN_jp2.add(jp_C_R_jpN_jl3);jp_C_R_jpN_jp2.add(jp_C_R_jpN_jl4);
		jp_C_R_jpN_jp2.add(jp_C_R_jpN_jl5);jp_C_R_jpN_jp2.add(jp_C_R_jpN_jl6);
		
		jp_C_R_jpN.add(jp_C_R_jpN_jp1,"North");
		jp_C_R_jpN.add(jp_C_R_jpN_jp2,"Center");
		
		jp_C_R.add(jp_C_R_jpN,"North");
		
		//右面板 中间
		jp_C_R_jpC=new JPanel(new BorderLayout());
		jp_C_R_jpC.setBackground(Color.WHITE);
		jp_C_R_jpC.setSize(260, 365);
		
		jp_C_R_jpC_jp1=new JPanel();
		jp_C_R_jpC_jp1.setSize(260, 20);
		jp_C_R_jpC_jp1.setBackground(new Color(213,228,242));
		jp_C_R_jpC_jl1=new JLabel(new ImageIcon("image/biaoti.jpg"),JLabel.CENTER);
		jp_C_R_jpC_jp1.add(jp_C_R_jpC_jl1);
		
		//
		jp_C_R_jpC_jp2=new JPanel();
		jp_C_R_jpC_jp2.setSize(260, 250);
		jp_C_R_jpC_jp2.setLayout(new GridLayout(9,2,0,0));
		jp_C_R_jpC_jp2.setBackground(Color.WHITE);
		jp_C_R_jpC_jp2.setBorder(new LineBorder(new Color(213,228,242)));
		jp_C_R_jpC_jl2=new JLabel("演化代数",JLabel.LEFT);
		jp_C_R_jpC_jl2.setPreferredSize(new Dimension(120,18));
		jp_C_R_jpC_jl2.addMouseListener(this);
		jp_C_R_jpC_jf1=new JTextField();
		jp_C_R_jpC_jf1.setText(cf.getString("nMaxCycle"));
		jp_C_R_jpC_jf1.setColumns(20);
		jp_C_R_jpC_jf1.addMouseListener(this);
		jp_C_R_jpC_jl3=new JLabel("评价次数",JLabel.LEFT);
		jp_C_R_jpC_jl3.addMouseListener(this);
		jp_C_R_jpC_jf2=new JTextField();
		jp_C_R_jpC_jf2.setText(cf.getString("nEvaCount"));
		jp_C_R_jpC_jf2.setColumns(20);
		jp_C_R_jpC_jf2.addMouseListener(this);
		jp_C_R_jpC_jl4=new JLabel("种群数量",JLabel.LEFT);
		jp_C_R_jpC_jl4.addMouseListener(this);
		jp_C_R_jpC_jf3=new JTextField();
		jp_C_R_jpC_jf3.setText(cf.getString("nPopSize"));
		jp_C_R_jpC_jf3.setColumns(20);
		jp_C_R_jpC_jf3.addMouseListener(this);
		jp_C_R_jpC_jl5=new JLabel("问题维数",JLabel.LEFT);
		jp_C_R_jpC_jl5.addMouseListener(this);
		jp_C_R_jpC_jf4=new JTextField();
		jp_C_R_jpC_jf4.setColumns(20);
		jp_C_R_jpC_jf4.setText(cf.getString("nDimension"));
		jp_C_R_jpC_jl6=new JLabel("运行次数",JLabel.LEFT);
		jp_C_R_jpC_jl6.addMouseListener(this);
		jp_C_R_jpC_jf5=new JTextField();
		jp_C_R_jpC_jf5.setColumns(20);
		jp_C_R_jpC_jf5.setText(cf.getString("nRunTimes"));
		jp_C_R_jpC_jf5.addMouseListener(this);
		jp_C_R_jpC_jl7=new JLabel("求解类型",JLabel.LEFT);
		jp_C_R_jpC_jl7.addMouseListener(this);
		jp_C_R_jpC_jf6=new JTextField();
		jp_C_R_jpC_jf6.setColumns(20);
		jp_C_R_jpC_jf6.setText("最小值");
		jp_C_R_jpC_jf6.addMouseListener(this);
		jp_C_R_jpC_jl8=new JLabel("给定最优值",JLabel.LEFT);
		jp_C_R_jpC_jl8.addMouseListener(this);
		jp_C_R_jpC_jf7=new JTextField();
		jp_C_R_jpC_jf7.setColumns(20);
		jp_C_R_jpC_jf7.setText(cf.getString("szOptimum"));
		jp_C_R_jpC_jf7.addMouseListener(this);
		jp_C_R_jpC_jl9=new JLabel("更新间隔",JLabel.LEFT);
		jp_C_R_jpC_jl9.addMouseListener(this);
		jp_C_R_jpC_jf8=new JTextField();
		jp_C_R_jpC_jf8.setColumns(20);
		jp_C_R_jpC_jf8.setText(cf.getString("nUpdateInterVal"));
		jp_C_R_jpC_jf8.addMouseListener(this);
		jp_C_R_jpC_jl10=new JLabel("更新延迟",JLabel.LEFT);
		jp_C_R_jpC_jl10.addMouseListener(this);
		jp_C_R_jpC_jf9=new JTextField();
		jp_C_R_jpC_jf9.setColumns(20);
		jp_C_R_jpC_jf9.setText(cf.getString("nUpdateDelay"));
		jp_C_R_jpC_jf9.addMouseListener(this);
		
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl2);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf1);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl3);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf2);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl4);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf3);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl5);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf4);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl6);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf5);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl7);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf6);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl8);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf7);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl9);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf8);
		jp_C_R_jpC_jp2.add(jp_C_R_jpC_jl10);jp_C_R_jpC_jp2.add(jp_C_R_jpC_jf9);
		
		jp_C_R_jpC_jp3=new JPanel();
		jp_C_R_jpC_jp3.setSize(260, 65);
		jp_C_R_jpC_jp3.setBackground(new Color(213,228,242));
		
		jp_C_R_jpC.add(jp_C_R_jpC_jp1,"North");
		//jp_C_R_jpC.add(jp_C_R_jpC_jp2,"Center");
		
		jp_C_R_jpC_jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		jp_C_R_jpC_jsp.setSize(260, 240);
		jp_C_R_jpC_jsp.setDividerLocation(190);
		jp_C_R_jpC_jsp.setDividerSize(2);
		jp_C_R_jpC_jsp.setTopComponent(jp_C_R_jpC_jp2);
		jp_C_R_jpC_jsp.setBottomComponent(jp_C_R_jpC_jp3);
		jp_C_R_jpC.add(jp_C_R_jpC_jsp,"Center");
		
		jp_C_R.add(jp_C_R_jpC,"Center");
		
		jsp.setLeftComponent(jp_C_L);
		jsp.setRightComponent(jp_C_R);
		
		this.setTitle("                                                                  AlgoAnalysis_zc");
		
	}
	
	public static void main(String[] args) {
		MainFrame mf=new MainFrame();
		mf.validate();
	}

	public void mouseClicked(MouseEvent e) {

		if(jp_N_jp2_jl1 == e.getSource())
		{
			FunctionChoose fc=new FunctionChoose(this);
			fc.validate();
		}else if(jp_N_jp2_jl2 == e.getSource()){
			AlgorithmsChoose algoc=new AlgorithmsChoose(this);
			algoc.validate();
		}else if(jp_N_jp2_jl6 == e.getSource()){
		
		}else if(jp_C_R_jpN_jl2 == e.getSource()){
			jsp.setDividerLocation(960);
		}else if(jp_C_R_jpC_jf1 == e.getSource()){
			jp_C_R_jpC_jf1.setBackground(new Color(213,228,242));
		}else if(jp_C_R_jpC_jl2 == e.getSource()) {
			jp_C_R_jpC_jl2.setBackground(new Color(213,228,242));
			jp_C_L_jpC_jl[0]=new JLabel(new ImageIcon("image/neirong0.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[0]);
	
		}else if(jp_N_yunxing == e.getSource()){  //运行
			//获得参数
			int evolutionCount = Integer.parseInt(jp_C_R_jpC_jf1.getText().trim());
			int popSize = Integer.parseInt(jp_C_R_jpC_jf3.getText().trim());
			int xnum = Integer.parseInt(jp_C_R_jpC_jf4.getText().trim());
			maxcycle = Integer.parseInt(jp_C_R_jpC_jf1.getText().trim());
			int run_time = Integer.parseInt(jp_C_R_jpC_jf5.getText().trim());
			String s = jp_C_R_jpC_jf6.getText().trim();
			
			int p_type = 0;
			//0最小值   1最大值
			if(s.equals("最小值")){
				p_type = 0;
			}else{
				p_type = 1;
			}
			
			Config cf2 = new Config("config.properties");
			String Algorithmname = cf2.getString("AlgorithmLib");
			alg_name= Algorithmname.split(",");
			String func = jp_N_jp11_jc2.getSelectedItem().toString();
			int index = jp_N_jp11_jc2.getSelectedIndex();
			this.initcontrolprama(index);
			if(m_valueType != -1){
				p_type = m_valueType;
			}
			
			if(m_varNum != 0 && xnum != m_varNum ){
				xnum = m_varNum;
			}
			
			
			this.kedu = Integer.parseInt(jp_N_jp11_jc3.getSelectedItem().toString());
			
			value = new double[alg_name.length][maxcycle];
			fitness = new double[alg_name.length][maxcycle];
			avgfitness = new double[alg_name.length][maxcycle];
			time = new double[alg_name.length][maxcycle];
			
			
			
			for(int i=0; i < alg_name.length; i++){
				System.out.println(popSize+" "+xnum+" "+maxcycle+" "+p_type+" "+func+" "+m_lb+" "+m_ub+" "+i);
				if(alg_name[i].equals("DE")){
					DE dealg=new DE(popSize,xnum,maxcycle,run_time,p_type,func,m_lb,m_ub,i);
					dealg.run();
				}else if(alg_name[i].equals("BA")){
					BA ba = new BA(popSize,xnum,maxcycle,run_time,p_type,func,m_lb,m_ub,i);
					ba.run();
				}
			}
			
			if(chartp != null){
				jp_C_L_jpC.remove(chartp);
			}
			
			if(jp_N_jp11_jc1.getSelectedIndex() == 0){ 
				chartp=new ChartPanel(ChartUtil.createChart(false,alg_name,kedu,maxcycle,value,jp_N_jp11_jc1.getSelectedIndex()));
			}else if(jp_N_jp11_jc1.getSelectedIndex() == 1){
				chartp=new ChartPanel(ChartUtil.createChart(false,alg_name,kedu,maxcycle,fitness,jp_N_jp11_jc1.getSelectedIndex()));
			}else if(jp_N_jp11_jc1.getSelectedIndex() == 2){
				chartp=new ChartPanel(ChartUtil.createChart(false,alg_name,kedu,maxcycle,avgfitness,jp_N_jp11_jc1.getSelectedIndex()));
			}
			
			chartp.setPreferredSize(new Dimension(600, 250));
			jp_C_L_jpC.add(chartp);
			this.validate();
		}else if(e.getSource() == jp_C_R_jpC_jl3){
			System.out.println("激活");
			jp_C_L_jpC_jl[1]=new JLabel(new ImageIcon("image/neirong1.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[1]);
		}else if(e.getSource() == jp_C_R_jpC_jl4){
			jp_C_L_jpC_jl[2]=new JLabel(new ImageIcon("image/neirong2.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[2]);
		}else if(e.getSource() == jp_C_R_jpC_jl5){
			jp_C_L_jpC_jl[3]=new JLabel(new ImageIcon("image/neirong3.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[3]);
		}else if(e.getSource() == jp_C_R_jpC_jl6){
			jp_C_L_jpC_jl[4]=new JLabel(new ImageIcon("image/neirong4.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[4]);
		}else if(e.getSource() == jp_C_R_jpC_jl7){
			/*jp_C_L_jpC_jl[5]=new JLabel(new ImageIcon("image/neirong6.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[5]);*/
		}else if(e.getSource() == jp_C_R_jpC_jl8){
			jp_C_L_jpC_jl[5]=new JLabel(new ImageIcon("image/neirong6.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[5]);
		}else if(e.getSource() == jp_C_R_jpC_jl9){
			jp_C_L_jpC_jl[6]=new JLabel(new ImageIcon("image/neirong7.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[6]);
		}else if(e.getSource() == jp_C_R_jpC_jl10){
			jp_C_L_jpC_jl[7]=new JLabel(new ImageIcon("image/neirong8.jpg"),JLabel.CENTER); 
			jp_C_R_jpC_jp3.removeAll();
			jp_C_R_jpC_jp3.add(jp_C_L_jpC_jl[7]);
		}else if(e.getSource() == jp_N_chazhao){
			//System.out.println("打开文件");
			try {
				Runtime.getRuntime().exec("Explorer.exe "+"result");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource() == jp_N_qingkong){
			File file=new File("result");
			File filelist[]=file.listFiles();
			for(int i=0; i<filelist.length; i++){
				filelist[i].delete();
			}
		}
		this.validate();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.jp_N_jp11_jc2){
			String fun=jp_N_jp11_jc2.getSelectedItem().toString();
			this.jl_func.setIcon(new ImageIcon("function/"+fun+".jpg"));
		}else if(e.getSource() == this.jp_N_jp2_jcb2){
			if(jp_N_jp2_jcb2.isSelected()){
				jl_func.setVisible(true);
			}else{
				jl_func.setVisible(false);
			}
		}else if(e.getSource() == this.jp_N_jp11_jc1){
			if(chartp != null){
				jp_C_L_jpC.remove(chartp);
			}
			if(jp_N_jp11_jc1.getSelectedIndex() == 0){ 
				chartp=new ChartPanel(ChartUtil.createChart(false,alg_name,kedu,maxcycle,value,jp_N_jp11_jc1.getSelectedIndex()));
			}else if(jp_N_jp11_jc1.getSelectedIndex() == 1){
				chartp=new ChartPanel(ChartUtil.createChart(false,alg_name,kedu,maxcycle,fitness,jp_N_jp11_jc1.getSelectedIndex()));
			}else if(jp_N_jp11_jc1.getSelectedIndex() == 2){
				chartp=new ChartPanel(ChartUtil.createChart(false,alg_name,kedu,maxcycle,avgfitness,jp_N_jp11_jc1.getSelectedIndex()));
			}
			chartp.setPreferredSize(new Dimension(600, 250));
			jp_C_L_jpC.add(chartp);
			jp_C_L_jpC.validate();
		}
	}
	
	public void initcontrolprama(int func){
		if(cf.getString("BenchMarkLib").equals("BenchMarkFunc")){
			switch(func)
			{
			case 0:    //sphere
				m_varNum = 50;
				m_lb = -100;
				m_ub = 100;	
				break;
			case 1:      //Quadric
				m_varNum = 50;
				m_lb = -100;
				m_ub = 100;		
				break;
			case 2:	     //Rosenbrock
				m_varNum = 50;
				m_lb = -5.12;
				m_ub = 5.12;
				break;
			case 3:      //,Rastrigin
				m_varNum = 50;
				m_lb = -5.12;
				m_ub = 5.12;	
				break;
			case 4:     //Griewank   
				m_varNum = 50;
				m_lb = -600;
				m_ub = 600;		
				break;
			case 5:    //Schaffer 
				m_varNum = 2;
				m_lb = -10;
				m_ub = 10;		
				break;
			case 6:   //Bohachevsky 
				m_varNum = 2;
				m_lb = -1;
				m_ub = 1;		
				break;
			case 7:            //Shubert 
				m_varNum = 2;
				m_lb = -10;
				m_ub = 10;		
				break;
			case 8:           //Schaffer1 
				m_varNum = 2;
				m_lb = -5.12;
				m_ub = 5.12;		
				break;
			case 9:           //Rana 
				m_varNum = 2;
				m_lb = -512;
				m_ub = 512;		
				break;
			case 10:             //Needle
				m_valueType=1; 
				m_varNum = 2;
				m_lb = -5.12;
				m_ub = 5.12;		
				break;
			case 11:             //Ackley
				m_valueType=0;
				m_varNum = 50;
				m_lb = -10;
				m_ub = 10;		
				break;
			}
		}else if(cf.getString("BenchMarkLib").equals("CEC2005")){
			switch(func){
			case 0:      //f1
				m_varNum = 2;
				m_lb = -5.12;
				m_ub = 5.12;		
				break;
			case 1:             // f9 
				m_varNum = 50;
				m_lb = -10;
				m_ub = 10;		
				break;
			case 2:           //f10 
				m_varNum = 50;
				m_lb = -1.28;
				m_ub = 1.28;		
				break;
			case 3:           //f11 
				m_varNum = 50;
				m_lb = 0;
				m_ub = 3.1415926;		
				break;
			case 4:          //f12 
				m_varNum = 50;
				m_lb = -5;
				m_ub = 5;		
				break;
			case 5:         //f13 
				m_varNum = 50;
				m_lb = -500;
				m_ub = 500;		
				break;
			case 6:            //f16 
				m_varNum = 2;
				m_lb = -5.12;
				m_ub = 5.12;		
				break;
			case 7:		   //f17 
				m_varNum = 50;
				m_lb = -100;
				m_ub = 100;		
				break;
			case 8:           //f18 
				m_valueType=1;
				m_varNum = 50;
				m_lb = -10;
				m_ub = 10;		
				break;
			case 9:        //f19 
				m_valueType=1;
				m_varNum = 2;
				m_lb = -1;
				m_ub = 1;		
				break;
			case 10:            //f20 
				m_valueType=1;
				m_varNum = 2;
				m_lb = -1;
				m_ub = 1;		
				break;
			}
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {

		if(jp_N_yunxing == e.getSource()){
			
			jp_N_yunxing.setIcon(new ImageIcon("image/yunxing2.jpg"));
			jp_N_byunxing.setVisible(true);
			
		}else if(jp_N_chazhao == e.getSource()){
			jp_N_chazhao.setIcon(new ImageIcon("image/jieguo2.jpg"));
			jp_N_bchazhao.setVisible(true);
			
		}else if(jp_N_fenxi == e.getSource()){
			jp_N_fenxi.setIcon(new ImageIcon("image/fenxi2.jpg"));
			jp_N_bshuju.setVisible(true);
			
		}else if(jp_N_qingkong == e.getSource()){
			jp_N_qingkong.setIcon(new ImageIcon("image/qingkong2.jpg"));
			jp_N_bqingkong.setVisible(true);
			
		}else if(jp_N_jp2 == e.getSource()){
			
			jp_N_jp2.setBackground(new Color(214,233,239));
		}else if(jp_N_jp2_jl1 == e.getSource()){
			jp_N_jp2_jl1.setIcon(new ImageIcon("image/hanshu2.jpg"));
		}else if(jp_N_jp2_jl2 == e.getSource()){
			jp_N_jp2_jl2.setIcon(new ImageIcon("image/suanfa2.jpg"));
		}else if(jp_N_jp2_jl3 == e.getSource()){
			jp_N_jp2_jl3.setIcon(new ImageIcon("image/xuanxiang2.jpg"));
		}else if(jp_N_jp2_jl4 == e.getSource()){
			jp_N_jp2_jl4.setIcon(new ImageIcon("image/fangda2.jpg"));
		}else if(jp_N_jp2_jl5 == e.getSource()){
			jp_N_jp2_jl5.setIcon(new ImageIcon("image/suoxiao2.jpg"));
		}else if(jp_N_jp2_jl6 == e.getSource()){
			jp_N_jp2_jl6.setIcon(new ImageIcon("image/shuaxin2.jpg"));
		}else if(jp_C_R_jpN_jl2 == e.getSource()){
			jp_C_R_jpN_jl2.setIcon(new ImageIcon("image/close2.jpg"));
		}else if(jp_C_R_jpN_jl3 == e.getSource()){
			jp_C_R_jpN_jl3.setIcon(new ImageIcon("image/jiajian2.jpg"));
		}else if(jp_C_R_jpN_jl4 == e.getSource()){
			jp_C_R_jpN_jl4.setIcon(new ImageIcon("image/ziti2.jpg"));
		}else if(jp_C_R_jpN_jl5 == e.getSource()){
			jp_C_R_jpN_jl5.setIcon(new ImageIcon("image/wen2.jpg"));
		}else if(jp_C_R_jpN_jl6 == e.getSource()){
			jp_C_R_jpN_jl6.setIcon(new ImageIcon("image/dian2.jpg"));
		}else if(jp_N_jp11_jl1 == e.getSource()){
			jp_N_bhuitu.setVisible(true);
		}else if(jp_N_jp11_jl2 == e.getSource()){
			jp_N_bceshi.setVisible(true);
		}else if(jp_N_jp11_jl3 == e.getSource()){
			jp_N_bkedu.setVisible(true);
		}
	}

	public void mouseExited(MouseEvent e) {
		
		if(jp_N_yunxing == e.getSource()){
			jp_N_yunxing.setIcon(new ImageIcon("image/yunxing.jpg"));
			jp_N_byunxing.setVisible(false);
		}else if(jp_N_chazhao == e.getSource()){
			jp_N_chazhao.setIcon(new ImageIcon("image/jieguo.jpg"));
			jp_N_bchazhao.setVisible(false);
		}else if(jp_N_fenxi == e.getSource()){
			jp_N_fenxi.setIcon(new ImageIcon("image/fenxi.jpg"));
			jp_N_bshuju.setVisible(false);
		}else if(jp_N_qingkong == e.getSource()){
			jp_N_qingkong.setIcon(new ImageIcon("image/qingkong.gif"));
			jp_N_bqingkong.setVisible(false);
		}else if(jp_N_jp2 == e.getSource()){
			jp_N_jp2.setBackground(new Color(207,223,239));
		}else if(jp_N_jp2_jl1 == e.getSource()){
			jp_N_jp2_jl1.setIcon(new ImageIcon("image/hanshu.jpg"));
		}else if(jp_N_jp2_jl2 == e.getSource()){
			jp_N_jp2_jl2.setIcon(new ImageIcon("image/suanfa.jpg"));
		}else if(jp_N_jp2_jl3 == e.getSource()){
			jp_N_jp2_jl3.setIcon(new ImageIcon("image/xuanxiang.jpg"));
		}else if(jp_N_jp2_jl4 == e.getSource()){
			jp_N_jp2_jl4.setIcon(new ImageIcon("image/fangda.jpg"));
		}else if(jp_N_jp2_jl5 == e.getSource()){
			jp_N_jp2_jl5.setIcon(new ImageIcon("image/suoxiao.jpg"));
		}else if(jp_N_jp2_jl6 == e.getSource()){
			jp_N_jp2_jl6.setIcon(new ImageIcon("image/shuaxin.jpg"));
		}else if(jp_C_R_jpN_jl2 == e.getSource()){
			jp_C_R_jpN_jl2.setIcon(new ImageIcon("image/close.jpg"));
		}else if(jp_C_R_jpN_jl3 == e.getSource()){
			jp_C_R_jpN_jl3.setIcon(new ImageIcon("image/jiajian.jpg"));
		}else if(jp_C_R_jpN_jl4 == e.getSource()){
			jp_C_R_jpN_jl4.setIcon(new ImageIcon("image/ziti.jpg"));
		}else if(jp_C_R_jpN_jl5 == e.getSource()){
			jp_C_R_jpN_jl5.setIcon(new ImageIcon("image/wen.jpg"));
		}else if(jp_C_R_jpN_jl6 == e.getSource()){
			jp_C_R_jpN_jl6.setIcon(new ImageIcon("image/dian.jpg"));
		}else if(jp_N_jp11_jl1 == e.getSource()){
			jp_N_bhuitu.setVisible(false);
		}else if(jp_N_jp11_jl2 == e.getSource()){
			jp_N_bceshi.setVisible(false);
		}else if(jp_N_jp11_jl3 == e.getSource()){
			jp_N_bkedu.setVisible(false);
		}
	}
	
	public void updatefunction(){
		cf=new Config("config.properties");
		String funcname=cf.getString("BenchMarkLib");
		Config cf1=new Config("benchmark/"+funcname+".properties");
		String funclist=cf1.getString(funcname);
		
		jc2Item.removeAllElements();
		//System.out.println(funclist);
		String[] funcnamelist =funclist.split(",");
		for(int i=0; i < funcnamelist.length; i++){
			jc2Item.add(funcnamelist[i]);
		}
		String index=cf.getString("szFuncName");
		jp_N_jp11_jc2.setSelectedItem(index);
		
		jp_C_R_jpC_jf4.setText(cf.getString("nDimension"));
		
		jp_N_jp11_jc2.validate();
		//System.out.println("刷新"+index);
	}
	
}
