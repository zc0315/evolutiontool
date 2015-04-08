package com.AlgoAnalysis.Algorithm;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.AlgoAnalysis.veiw.MainFrame;


public class DE implements Algorithm{
	ArrayList<Individual> population=new ArrayList<Individual>();
	ArrayList<Individual> newpopulation=new ArrayList<Individual>();
	int[][] G;
	int generation;            /*number of generation*/
	int best_index;            /*index of best individual*/
	int worst_index;
	Individual bestindividual;
	Individual currentbest;
	
	private int xnum;
	private int run_time;
	private int p_type;
	private String func;
	private double lb;
	private double ub;
	private int index;
	int PopSize=30;                     /*population size*/
	int MaxGeneration=200;              /*max.number of generation*/
	double Pc=0.3;                      /*probability of crossover*/
	double Fc=0.5;                    /*probability of mutation*/
	
	DecimalFormat df=new DecimalFormat("0.000");
	
	//算法求得的最优解
	double m_global;
	//算法求得的最优解的适应度
	double m_gfitness;
	//最优解的各参数值
	ArrayList<Double> m_globalParams;
	
	//每代个体的平均适应度值
	double m_avgfitness;
	
	private Class<?> c = null;
	private Method method;
	private Function fun = new Function();

	public DE(int _popsize,int _xnum,int maxcycle,int run_time,int p_type,String func,double lb,double ub,int index){
		PopSize = _popsize;
		xnum = _xnum;
		this.MaxGeneration = maxcycle;
		this.run_time = run_time;
		this.p_type = p_type;
		this.func = func;
		this.lb = lb;
		this.ub = ub;
		this.index = index;
		try {
			c = Class.forName("com.AlgoAnalysis.Algorithm.Function");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			method = c.getMethod(func,ArrayList.class,int.class);
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
	
	//产生种群
	public void GenerateInitialPopulation(){
		for(int i=0; i<this.PopSize; i++){
			Individual ind = new Individual();
			for(int j=0; j<this.xnum; j++){
				 ind.chorm.add(Math.random()*(ub-lb)+lb);
			}
			try {
				ind.fvalue = (Double)method.invoke(fun, ind.chorm, xnum);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			ind.fitness = CalculateFitness(ind.fvalue);
			this.population.add(ind);
		}
	}
	
	public double CalculateFitness(double fun){
		//计算解的适应度
		double result=0;
		if(p_type == 0){//求最小值
			if(fun>=0)
			{
				result=-fun;
			}
			else
			{
				result=1+Math.abs(fun);
			}
		}else{
			if(fun>=0)
			{
				result=fun;
			}
			else
			{
				result=fun;
			}
		}
		return result;
	}
	
	public void print(){
		for(int i=0; i<PopSize; i++){
			for(int j=0; j<xnum; j++){
				System.out.print(population.get(i).chorm.get(j)+" ");
			}
			System.out.println();
		}
	}
	
	/*public void CalculatenewObjectValue(){
		for(int i=0; i<PopSize; i++){
			ArrayList<Double> temp=newpopulation.get(i).chorm;
			newpopulation.get(i).path.add(0, 1);
			for(int j=1; j<Individual.x; j++){
				int count=0;
				double xi=temp.get(j);
				int k=0;
				while(k<Individual.x){
					if(xi >= temp.get(k)){
						count++;
						//System.out.print("count"+k+"="+count+" ");
					}
					k++;
				}
				newpopulation.get(i).path.add(j,count);
			}
			//System.out.println();
		}
		
		for(int i=0; i<PopSize; i++){
			ArrayList<Integer> patht=newpopulation.get(i).path;
			for(int j=1; j<Individual.x; j++){
				int s=patht.get(j-1);
				int e=patht.get(j);
				if(G[s-1][e-1] == 0){
					newpopulation.get(i).value=2147483647;
					break;
				}else{
					newpopulation.get(i).value+=G[s-1][e-1];
				}
			}
		}
	}*/
	
	public void SelectSite(){
		int i,max;
		max=0;
		for(i=1;i<this.PopSize;i++){
			if(this.population.get(i).fitness > this.population.get(max).fitness)
				max=i;
		}
		m_global=this.population.get(max).fvalue;		
		m_gfitness=this.population.get(max).fitness;		

		for(i=0;i < xnum;i++)
			m_globalParams.set(i,this.population.get(max).chorm.get(i));
	}
	
	public void DEOperate(){
		int a,b,c,d;
		for(int i=0; i<PopSize; i++){
			a=(int)(Math.random()*this.PopSize);
			while(a - i == 0){
				a=(int)(Math.random()*this.PopSize);
			}
			b=(int)(Math.random()*this.PopSize);
			while((b == i) || (b == a)){
				b=(int)(Math.random()*this.PopSize);
			}
			c=(int)(Math.random()*this.PopSize);
			while((c == i) || (c == a) || (c == b)){
				c=(int)(Math.random()*this.PopSize);
			}
			d=(int)(Math.random()*this.PopSize);
			while((d == i) || (d == a) || (d == b) || (d == c)){
				d=(int)(Math.random()*this.PopSize);
			}
			
			Individual vi=new Individual();
			Individual va=population.get(a);
			Individual vb=population.get(b);
			Individual vc=population.get(c);
			Individual vd=population.get(d);
			for(int j=0; j<xnum; j++){
				double ni=this.m_globalParams.get(j)+Fc*(va.chorm.get(j)-vb.chorm.get(j)+vc.chorm.get(j)-vd.chorm.get(j));  
				if(ni <= lb || ni >= ub){//编码形式 大于1和小于0的重新生成编码
				    ni=Math.random()*(ub - lb)+lb;
				}
				vi.chorm.add(ni);
			}
			newpopulation.add(i,vi);
		}
		
	}
	public void printnew(){
		for(int i=0; i<PopSize; i++){
			System.out.println("\n旧种群：");
			for(int j=0; j < xnum; j++){
				System.out.print(newpopulation.get(i).chorm.get(j)+" ");
			}
			System.out.println("\n新种群");
			for(int k=0; k<xnum; k++){
				System.out.print(population.get(i).chorm.get(k)+" ");
			}
		}
	}
	public void Crossover(){
		double rand;
		int jrand;
		for(int i=0; i < PopSize; i++){
			jrand=(int)(Math.random()*xnum);
			for(int j=0; j < xnum; j++){
				rand=Math.random();
				if(rand <= Pc || jrand != j){
					double tm = population.get(i).chorm.get(j); //检查是否有相同的基因
					newpopulation.get(i).chorm.set(j,tm);
				}
			}
		}
	}
	
	public void SelectionOperator(){
		for(int i=0; i<PopSize; i++){
			if(population.get(i).fitness < newpopulation.get(i).fitness){
				population.add(i,newpopulation.get(i));
			}
			
		}
		
	}
	
	public void GenerateNextPopulation(){
		DEOperate();
		Crossover();
		//计算新种群的值和适应度
		for(int i=0; i<this.PopSize; i++){
			try {
				this.newpopulation.get(i).fvalue= (Double)method.invoke(fun, this.newpopulation.get(i).chorm, xnum);
				this.newpopulation.get(i).fitness = CalculateFitness(this.newpopulation.get(i).fvalue);
				
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
	
	public void run(){
		  
			int run=0;
			//把最终运算结果写入文件
			/*File filename=new File("result");
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				FileWriter fw=new FileWriter(filename,true);
				fw.write(String.format("DE算法第%d次运行\r\n",run+1));
				fw.write("代数\t时间\t最优值\t\t\t最佳适应度\r\n");
				long m_start = System.currentTimeMillis();
				generation=0;
				GenerateInitialPopulation();
				this.SelectSite();
				for (generation=1; generation < this.MaxGeneration; generation++)
				{
					GenerateNextPopulation();
					
					SelectionOperator();
					this.SelectSite();
					CalcAvgFitness();
					long n= System.currentTimeMillis() - m_start;
					MainFrame.value[index][generation]=this.m_global;
					MainFrame.fitness[index][generation]=this.m_gfitness;
					MainFrame.avgfitness[index][generation]=m_avgfitness;
					MainFrame.time[index][generation]=(double)n/1000.0;
					fw.write(String.format("%d\t%.3e\t%.15e\t%.15e\r\n",generation+1,(double)n/1000.0,m_global,this.m_gfitness));
				}
				fw.write("DE算法最优值参数信息：\r\n");
				for(int i=0;i<xnum; i++){
					fw.write(String.format("%d\t%.15e\r\n",i+1,this.m_globalParams.get(i)));
				}
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void CalcAvgFitness(){
		int i;
		double sum=0;
		for(i=0;i<this.PopSize;i++){
			sum+=this.population.get(i).fitness;
		}
		m_avgfitness=sum/this.PopSize;
	}
	
	public static void main(String args[]){
		/*DE de=new DE();
		de.run();*/
	} 
}

class Individual{
	public ArrayList<Double> chorm=new ArrayList<Double>();
	public double fvalue;			/*object value of this individual*/
	public double fitness;
}