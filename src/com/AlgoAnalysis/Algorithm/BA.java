package com.AlgoAnalysis.Algorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.Collections;


import com.AlgoAnalysis.veiw.MainFrame;


public class BA implements Algorithm{
	private int popsize;
	private int xnum;
	private int maxcycle;
	private int run_time;
	private int p_type;
	private String func;
	private double lb;
	private double ub;
	private int m;
	private int e;
	
	private int NEP = 7;
	private int NSP = 4;
	
	//邻域搜索用的块大小
	double m_ngh;
	private ArrayList<Individual> pop;
	
	//算法求得的最优解
	double m_global;
	//算法求得的最优解的适应度
	double m_gfitness;
	//每代个体的平均适应度值
	double m_avgfitness;
	//最优解的各参数值
	ArrayList<Double> m_globalParams;
	
	private Class<?> c = null;
	private Method method;
	private Function fun = new Function();
	
	private int index;
	
	public BA(int _popsize,int _xnum,int maxcycle,int run_time,int p_type,String func,double lb,double ub,int index){
		popsize = _popsize;
		xnum = _xnum;
		this.maxcycle = maxcycle;
		this.run_time = run_time;
		this.p_type = p_type;
		this.func = func;
		this.lb = lb;
		this.ub = ub;
		m = 5;
		e = 3;
		this.index = index;
		pop = new ArrayList<Individual>();
		
		try {
			c = Class.forName("com.AlgoAnalysis.Algorithm.Function");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			method = c.getMethod(func, ArrayList.class,int.class);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		m_globalParams = new ArrayList<Double>();
		for(int i=0; i < xnum; i++){
			m_globalParams.add(0.0);
		}
	}
	
	public void run(){
		/*if(this.popsize<5){
			AfxMessageBox("初始种群个数不能小于5.");
			return;
		}*/
		int iter,run=0;
		//把最终运算结果写入文件
		
	/*	File filename=new File("result");
		String[] files=filename.list();
		for(int i=0; i<files.length; i++){
			if(files[i].equals(func+".txt")){
				File tfile=new File("result/"+func+".txt");
				tfile.delete();
			}
		}*/
		File filename=new File("result/"+func+".txt");
		if(!filename.exists()){
			try {
				filename.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fw=new FileWriter(filename,true);
			fw.write(String.format("BA算法第%d次运行\r\n",run+1));
			fw.write("代数\t时间\t最优值\t\t\t最佳适应度\r\n");
			long m_start = System.currentTimeMillis();
			Initialize();
			for (iter=0; iter < this.maxcycle; iter++)
			{
				SelectBestSite();
				NeighborhoodSearch();
				ReInitNotSelectedSite();
				SelectSite();
				CalcAvgFitness();
				long n= System.currentTimeMillis() - m_start;
				MainFrame.value[index][iter]=m_global;
				MainFrame.fitness[index][iter]=m_gfitness;
				MainFrame.avgfitness[index][iter]=m_avgfitness;
				MainFrame.time[index][iter]=(double)n/1000.0;
				fw.write(String.format("%d\t%.3e\t%.15e\t%.15e\r\n",iter+1,(double)n/1000.0,m_global,m_gfitness));
			}
			fw.write("BA算法最优值参数信息：\r\n");
			for(int i=0;i<xnum; i++){
				fw.write(String.format("%d\t%.15e\r\n",i+1,m_globalParams.get(i)));
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	//初始化site
	void Initialize(){
		for(int i=0; i<popsize; i++){
			Individual ind = new Individual();
			for(int j=0; j<xnum; j++){
				 ind.chromosome.add(Math.random()*(ub-lb)+lb);
			}
			try {
				ind.fvalue = (Double)method.invoke(fun, ind.chromosome, xnum);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ind.fitness = CalculateFitness(ind.fvalue);
			ind.trial = 0;
			pop.add(ind);
		}

	}
	//计算解的适应度
	public double CalculateFitness(double fun){
		double result=0;
		if(p_type == 0){//求最小值
			if(fun>=0)
			{
				//result=1/(fun+1);
				result=-fun;
			}
			else
			{
				result=1+Math.abs(fun);

			}
		}
		else{
			if(fun>=0)
			{
				result=fun;
			}
			else
			{
				//result=1/(1+fabs(fun));
				result=fun;
			}
		}
		return result;
	}
	
	public void SelectBestSite(){
		Collections.sort(this.pop);
	}
	
	public void ReInitNotSelectedSite(){
		int i,j,param2change,neighbour;
		double ObjValSol; /*Objective function value of new solution*/
		double FitnessSol; /*Fitness value of new solution*/
		double r;
		
		double m_solution[]= new double[xnum];
		for(i=m;i<popsize;i++)
		{
			//改变哪一维
			r =Math.random();//[0,1)
			param2change=(int)(r*xnum);
					
			do{
				r =Math.random();
				neighbour=m+(int)(r*(popsize - m));
			}while(i==neighbour);
			m_ngh=this.pop.get(i).chromosome.get(param2change)-this.pop.get(i).chromosome.get(param2change);

			for(j=0;j<xnum;j++)
				m_solution[j]=this.pop.get(i).chromosome.get(j);

			r =Math.random(); //r value in [0,1)
	        m_solution[param2change]=this.pop.get(i).chromosome.get(param2change)+m_ngh*((r-0.5)*2);
			if(m_solution[param2change]<lb)
				m_solution[param2change]=lb;
			if(m_solution[param2change]>ub)
				m_solution[param2change]=ub;
			
			ArrayList<Double> p = new ArrayList<Double>();
			
			for(int k=0; k<xnum; k++){
				p.add(m_solution[k]);
			}
			
			try {
				ObjValSol = (Double)method.invoke(fun,p,xnum);
				FitnessSol=CalculateFitness(ObjValSol);
				if (FitnessSol>this.pop.get(i).fitness)
		        {
					this.pop.get(i).trial=0;
					for(j=0;j<xnum;j++)
						this.pop.get(i).chromosome.set(j, m_solution[j]);
					this.pop.get(i).fvalue=ObjValSol;
					this.pop.get(i).fitness=FitnessSol;
		        }
		        else
		        {   /*if the solution i can not be improved, increase its trial counter*/
		        	this.pop.get(i).trial++;
		        }
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	public void NeighborhoodSearch(){
		int i,j,neighbour,param2change;
		double r;
		double ObjValSol; /*Objective function value of new solution*/
		double FitnessSol; /*Fitness value of new solution*/
		double m_solution[] = new double[xnum];
		for(i=0;i<e;i++){
			for(j=1;j<NEP;j++){
				//选要改变的参数
				r =Math.random();
				param2change=(int)(r*xnum);
				
				do{
					r =Math.random();
					neighbour=(int)(r*xnum);
				}while(neighbour==i);
				m_ngh=this.pop.get(i).chromosome.get(param2change)-this.pop.get(neighbour).chromosome.get(param2change);

				//把当前要进行邻域搜索site的参数复制到m_solution中
				for(int k=0;k<xnum;k++)
					m_solution[k]=this.pop.get(i).chromosome.get(k);

				//改变参数的值
				r =Math.random(); //r value in [0,1)
				m_solution[param2change]=this.pop.get(i).chromosome.get(param2change)+m_ngh*((r-0.5)*2);
				//参数进行越界修正
				if(m_solution[param2change]<lb)
					m_solution[param2change]=lb;
				if(m_solution[param2change]>ub)
					m_solution[param2change]=ub;
				ArrayList<Double> p = new ArrayList<Double>();
				
				for(int k=0; k<xnum; k++){
					p.add(m_solution[k]);
				}
				
				try {
					ObjValSol = (Double)method.invoke(fun,p,xnum);
					FitnessSol=CalculateFitness(ObjValSol);
					
					//site得到优化，记录下新site
					//由于fitnesssol是个double，它的最大精度是16位，当FitnessSol非常接近1时就不能表示的更精确了
					//所以这时求出的更好的值也就没有意义了，也就是求出的最理想值在1e-16附近。不能比这更小了，
					//要想求得更小的值，须改变适应度函数中计算适应度的方法。
					if (FitnessSol > this.pop.get(i).fitness)
					{
						this.pop.get(i).trial=0;
						for(int k=0;k<xnum;k++)
							this.pop.get(i).chromosome.set(k, m_solution[k]);
						this.pop.get(i).fvalue=ObjValSol;
						this.pop.get(i).fitness=FitnessSol;
					}
					else
					{   /*if the solution i can not be improved, increase its trial counter*/
						this.pop.get(i).trial++;
					}
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			}		
		}
		

		for(i=this.e; i<m; i++){
			for(j=1;j<NSP;j++){
				//选要改变的参数
				r =Math.random();
				param2change=(int)(r*xnum);
							
				do{
					r =Math.random();
					neighbour=e+(int)(r*(m - e));
				}while(neighbour==i);
				m_ngh=this.pop.get(i).chromosome.get(param2change)-this.pop.get(neighbour).chromosome.get(param2change);

				//把当前要进行邻域搜索site的参数复制到m_solution中
				for(int k=0;k<xnum;k++)
					m_solution[k]=this.pop.get(i).chromosome.get(k);

				//改变参数的值
				r =Math.random();
				m_solution[param2change]=this.pop.get(i).chromosome.get(param2change)+m_ngh*((r-0.5)*2);
				//参数进行越界修正
				if(m_solution[param2change]<lb)
					m_solution[param2change]=lb;
				if(m_solution[param2change]>ub)
					m_solution[param2change]=ub;
				
				ArrayList<Double> p = new ArrayList<Double>();
				for(int k=0; k<xnum; k++){
					p.add(m_solution[k]);
				}
				try {
					ObjValSol = (Double)method.invoke(fun,p,xnum);
					FitnessSol=CalculateFitness(ObjValSol);
					//site得到优化，记录下新site
					if (FitnessSol>this.pop.get(i).fitness)
					{
						this.pop.get(i).trial=0;
						for(int k=0;k<xnum;k++)
							this.pop.get(i).chromosome.set(k, m_solution[k]);
						this.pop.get(i).fvalue=ObjValSol;
						this.pop.get(i).fitness=FitnessSol;
					}
					else
					{   /*if the solution i can not be improved, increase its trial counter*/
						this.pop.get(i).trial++;
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}		
		}
	}
	public void SelectSite(){
		int i,max;
		max=0;
		for(i=1;i<xnum;i++){
			if(this.pop.get(i).fitness>this.pop.get(max).fitness)
				max=i;
		}
		m_global=this.pop.get(max).fvalue;		
		m_gfitness=this.pop.get(max).fitness;		

		for(i=0;i < xnum;i++)
			m_globalParams.set(i, this.pop.get(max).chromosome.get(i));
	}
	
	public void CalcAvgFitness(){
		int i;
		double sum=0;
		for(i=0;i<popsize;i++){
			sum+=this.pop.get(i).fitness;
		}
		m_avgfitness=sum/popsize;
	}
	
	
	class Individual implements Comparable<Individual>{
		private ArrayList<Double> chromosome  = null;
		double fvalue;
		double fitness;
		int trial;
		public Individual(){
			chromosome = new ArrayList<Double>();
		}
		
		
		public ArrayList<Double> getChromosome() {
			return chromosome;
		}
		public void setChromosome(ArrayList<Double> chromosome) {
			this.chromosome = chromosome;
		}
		public double getFvalue() {
			return fvalue;
		}
		public void setFvalue(double fvalue) {
			this.fvalue = fvalue;
		}
		public double getFitness() {
			return fitness;
		}
		public void setFitness(double fitness) {
			this.fitness = fitness;
		}
		public int getTrial() {
			return trial;
		}
		public void setTrial(int trial) {
			this.trial = trial;
		}
		
		public int compareTo(com.AlgoAnalysis.Algorithm.BA.Individual o) {
			if(this.fitness - o.fitness >= 0){
				return 0;
			}else{
				return 1;
			}
		}
		
		
		
	} 
	
}
