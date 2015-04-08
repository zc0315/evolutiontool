package com.AlgoAnalysis.util;

import java.io.File;
import java.util.Vector;


import javax.swing.table.AbstractTableModel;

public class AlgoModel extends AbstractTableModel{
	private Vector<Object> headname;
	private Vector<Vector<Object>> datavalue;
	private Config cf;
	
	public AlgoModel(){
		
		headname=new Vector<Object>();
		datavalue=new Vector<Vector<Object>>();
		headname.add("算法文件");headname.add("算法名字");
		headname.add("算法描述");
		datavalue.add(headname);
		File file = new File("algorithm");
		String[] files = file.list();
		for (int i = 0; i < files.length; i++) {
		  if(files[i].endsWith(".properties")){
			  //System.out.println(files[i]);
			  Vector<Object> temp=new Vector<Object>();
			  cf=new Config("algorithm/"+files[i]);
			  int d=files[i].indexOf('.');
			  String s=files[i].substring(0,d);
			  temp.add(s+".jar");temp.add(s);
			  temp.add(cf.getString("Description"));
			  datavalue.add(temp);
		  }
		}
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headname.size();
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return datavalue.size();
	}

	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return datavalue.get(arg0).get(arg1);
	}
	
	
}
