package com.AlgoAnalysis.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private Properties cfg=new Properties();
	private String filename;
	public Config(String file){
		filename=file;
		try {
			cfg.load(new FileInputStream(file));
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	  public String getString(String key){
	    return cfg.getProperty(key);
	  }
	  
	  public int getInt(String key){
	    return Integer.parseInt(cfg.getProperty(key));
	  }
	  
	  public double getDouble(String key){
	    return Double.parseDouble(getString(key));
	  }
	  
	  public void setValue(String key,String value){
		   cfg.setProperty(key, value);
		   try {
			cfg.store(new FileOutputStream(filename), "Éè¶¨µÄ"+key);
		   } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  
	  
}
