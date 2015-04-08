package com.AlgoAnalysis.Algorithm;

import java.util.ArrayList;

public class Function {

	//计算标准偏差,标准偏差反映数值相对于平均值 (mean) 的离散程度。
	public static double StdDev(ArrayList<Double> sol,int n)
	{
		int i;
		double sum=0,avg;
		//计算平均值
		for(i=0;i<n;i++)
			sum+=sol.get(i);
		avg=sum/n;
		sum=0;
		for(i=0;i<n;i++)
			sum+=(sol.get(i)-avg)*(sol.get(i)-avg);
		sum/=n;
		return Math.sqrt(sum);
	}
	//f0:球面模型函数，是个单峰函数, 用来区分局部优化器的优劣
	public static double Sphere(ArrayList<Double> sol,int n)
	{
		int j;
		double top=0;
		for(j=0;j<n;j++)
		{
			top+=sol.get(j)*sol.get(j);
		}
		return top;
	}
	//f1:类似f0，最小值在谷底,x,y属于[-5.12,5.12]
	public static double f1(ArrayList<Double> sol,int n)
	{
		double top=0;
		top+=sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1);	
		return top;
	}
	
	//f2:Quadric函数最小值为0，旋转超椭球体函数,连续、凹、单峰函数。Xi属于[-100,100]
	public static double Quadric(ArrayList<Double> sol,int n)
	 {
		int i,j;
		double top,t1;
		top=0;
		for(i=0;i<n;i++){
			t1=0;
			for(j=0;j<=i;j++){
				t1+=sol.get(j);
			}
			top+=t1*t1;
		}
		return top;
	 }
	//f3:Rosenbrock 函数, 也叫香蕉(Banana)函数,最优解为0，分布在[1,1,1...1]处
	//Rosenbrock函数是一个经典复杂优化问题,它的全局最优点位于一个平滑、狭长的抛物线形山谷内.由于函数仅仅为优化算法提供了
	//少量信息,使算法很难辨别搜索方向,找到全局最小点的机会微乎其微,因此Rosenbrock函数通常用来评价优化算法的执行效率
	public static double Rosenbrock(ArrayList<Double> sol,int n){
		int j;
		double top=0;
		for(j=0;j<n-1;j++)
		{
			top=top+100*Math.pow((sol.get(j+1)-Math.pow((sol.get(j)),(double)2)),(double)2)+Math.pow((sol.get(j)-1),(double)2);
		}
		return top;
	}
	
	//f4:Rastrigrin函数，多峰函数，易陷于局部最优。
	//典型的非线性多模态函数.它们具有广泛的搜索空间、大量的局部极小点和高大的障碍物,通常被认为是遗传算法很难处理的复杂多模态问题
	public static double Rastrigin(ArrayList<Double> sol,int n)
	 {
		 int j;
		 double top=0;

		 for(j=0;j<n;j++)
		 {
			 top=top+(Math.pow(sol.get(j),(double)2)-10*Math.cos(2*Math.PI*sol.get(j))+10);
		 }
		 return top;
	 }
	 //f5:Griewank函数，多峰函数，易陷于局部最优。
	public static double Griewank(ArrayList<Double> sol,int n)
	 {
		 int j;
		 double top1,top2,top;
		 top=0;
		 top1=0;
		 top2=1;
		 for(j=0;j<n;j++)
		 {
			 top1=top1+Math.pow((sol.get(j)),(double)2);
			 top2=top2*Math.cos(sol.get(j)/Math.sqrt((double)(j+1)));

		 }	
		 top=(1/(double)4000)*top1-top2+1;
		 return top;
	 }
	//f6:Schaffer 函数最小值为0，Xi属于[-10,10]，单峰函数
	 public static double Schaffer(ArrayList<Double> sol,int n)
	 {
		double top,t1,t2;
		t1=Math.pow(Math.sin(Math.sqrt(sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1))),(double)2)-0.5;
		t2=Math.pow(1+0.001*(sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1)),(double)2);
		top=0.5+t1/t2;
		return top;
	 }
	 //f7:求小值，Bohachevsky测试函数连续、凹、单峰函数，最优解为-0.1848，分布在[0,-0.23]和[0,0.23]。x,y属于[-1,1]
	 public static double Bohachevsky(ArrayList<Double> sol,int n)
	 {
		double top;
		top=sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1)-0.3*Math.cos(3*Math.PI*sol.get(0))+0.3*Math.cos(4*Math.PI*sol.get(1))+0.3;
		return top;
	 }
	//f8:Shubert有760个局部最小值，其中的18个是全局最小，其值为-186.73。x,y属于[-10,10]
	public static double Shubert(ArrayList<Double> sol,int n)
	{
		double t1=0,t2=0;
		int i;
		for(i=1;i<=5;i++){
			t1+=i*Math.cos((i+1)*sol.get(0)+i);
			t2+=i*Math.cos((i+1)*sol.get(1)+i);
		}
		return t1*t2;
	 }
	 
	//f9:最小值为0，Xi属于[-10,10]，连续、凹、单峰函数
	public static double f9(ArrayList<Double> sol,int n)
	{
		double t1=0,t2=1;
		int i;
		for(i=0;i<n;i++){
			t1+=Math.abs(sol.get(i));
			t2*=Math.abs(sol.get(i));
		}	
		return t1+t2;
	}
	
	//f10:单峰函数,最小值为0，Xi属于[-1.28,1.28]
	public static double f10(ArrayList<Double> sol,int n)
	{
		double t1=0,r;
		int i;
		for(i=0;i<n;i++){
			t1+=(i+1)*Math.pow(sol.get(i),(double)4);
		}	
		r = Math.random();
		return t1+r;
	}
	//f11:多峰函数,最小值为-2，Xi属于[0,pi]
	public static double f11(ArrayList<Double> sol,int n)
	{
		double t1=0;
		int i;
		for(i=0;i<n;i++){
			t1+=Math.sin(sol.get(i))*Math.pow(Math.sin((i+1)*sol.get(i)*sol.get(i)/Math.PI),(double)20);
		}	
		return -t1;
	}
	//f12:最小值为0，Xi属于[-5,5]，最优解在一个较大而平坦的谷底上
	public static double f12(ArrayList<Double> sol,int n)
	{
		double t1=0;
		int i;
		for(i=0;i<n;i++){
			t1+=Math.pow(sol.get(i),(double)4)-16*sol.get(i)*sol.get(i)+5*sol.get(i);
		}	
		return t1/n;
	}
	
	//f13:多峰函数,求最大值，Xi属于[-500,500],30维时f(420.97)=12569.5,100维时f(420.97)=41898.3
	public static double f13(ArrayList<Double> sol,int n)
	{
		double t1=0;
		int i;
		for(i=0;i<n;i++){
			t1+=sol.get(i)*Math.sin(Math.sqrt(Math.abs(sol.get(i))));
		}	
		return -t1;
	}
	
	//f14:Schaffer1函数,全局最优解为0，分布在(0,0)
	//x,y属于[-5.12,5.12]
	public static double Schaffer1(ArrayList<Double> sol,int n)
	{
		double top=0;
		top=20+sol.get(0)*sol.get(0)-10*Math.cos(2*Math.PI*sol.get(0))+sol.get(1)*sol.get(1)-10*Math.cos(2*Math.PI*sol.get(1));
		return top;
	}
	
	//f15:Rana函数，全局最优解约为-511.7，分布在(-512,-512)
	//x,y属于[-512,512]
	public static double Rana(ArrayList<Double> sol,int n)
	{
		double top=0;
		top=sol.get(0)*Math.sin(Math.sqrt(Math.abs(sol.get(1)+1-sol.get(0))))*Math.cos(Math.sqrt(Math.abs(sol.get(1)+1+sol.get(0))))+(sol.get(1)+1)*Math.cos(Math.sqrt(Math.abs(sol.get(1)+1-sol.get(0))))*Math.sin(Math.sqrt(Math.abs(sol.get(1)+1+sol.get(0))));
		return top;
	}
	//f16:马鞍状图形，最小值在鞍底
	//x,y属于[-5.12,5.12]
	public static double f16(ArrayList<Double> sol,int n)
	{
		double top;
		top=(4-2.1*(sol.get(0)*sol.get(0))+Math.pow((sol.get(0)*sol.get(0)*sol.get(0)*sol.get(0)),(double)1/3))*(sol.get(0)*sol.get(0))+sol.get(0)*sol.get(1)+(-4+4*sol.get(1)*sol.get(1))*(sol.get(1)*sol.get(1));
		return top;
	}
	
	//f17:单峰函数，最小值在谷底，最优值为0
	//Xi属于[-100,100]
	public static double f17(ArrayList<Double> sol,int n)
	{
		double top,t1=0;
		int i;
		top=0;
		for(i=0;i<n;i++){
			t1=(sol.get(i)+0.5);
			if(t1>=0)
				t1=(int)t1;
			else
				t1=(int)(t1-1);
			top+=t1*t1;
		}
		return top;
	}
	//f18:最大值为0，Xi属于[-10,10],连续、凸、单峰函数,最优解在一个较大而平坦的峰顶上
	public static double f18(ArrayList<Double> sol,int n)
	{
		double t1=0;
		int i;
		for(i=0;i<n-1;i++){
			t1+=Math.pow((sol.get(i)*sol.get(i)-sol.get(i+1)),(double)2)+Math.pow((1-sol.get(i)),(double)2);
		}	
		return -t1;
	}
	//f19:多峰函数，有四个全局最大值2.118，对称分布于(0.64,0.64),(-0.64,-0.64),(0.64,-0.64),(-0.64,0.64)
	//存大量局部极大值，尤其是在中间区域有一取值与全局最大值接近的局部极大值2.077凸台
	//x,y属于[-1,1]
	public static double f19(ArrayList<Double> sol,int n)
	{
		double top,t1;
		t1=1+sol.get(0)*Math.sin(4*Math.PI*sol.get(0))-sol.get(1)*Math.sin(4*Math.PI*sol.get(1)+Math.PI);
		top=Math.sin(6*Math.sqrt(sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1)))/(6*Math.sqrt(sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1)+1e-15));
		top+=t1;
		return top;
	}
	//f20:x,y属于[-1,1]
	public static double f20(ArrayList<Double> sol,int n)
	{
		double top;
		top=1+sol.get(0)*Math.sin(4*Math.PI*sol.get(0))-sol.get(1)*Math.sin(4*Math.PI*sol.get(1)+Math.PI);
		return top;
	}
	
	
	//f21:Needle-in-haystack问题求最大值 ，当a=0.05,b=3；其最优解近似为3600,分布在(0,0)；4个局部极值点为(5.12,5.12),(-5.12,-5.12),(-5.12,5.12),(5.12,-5.12)
	//x,y属于[-5.12,5.12]
	public static double Needle(ArrayList<Double> sol,int n)
	{
		double top,t1,a,b;
		a=0.05;
		b=3;
		t1=b/(a+(sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1)));
		top=t1*t1+Math.pow((sol.get(0)*sol.get(0)+sol.get(1)*sol.get(1)),(double)2);
		return top;
	}
	
	//f22:Ackley是一个具有大量局部极小点的多峰函数, 在X=( 0,0,0,...,0)时具有全局最优解为0
	public static double Ackley(ArrayList<Double> sol,int n)
	{
		int i;
		double top,t1=0,t2=0;
		for(i=0;i<n;i++){
			t1+=sol.get(i)*sol.get(i);
			t2+=Math.cos(2*Math.PI*sol.get(i));
		}
		top=-20*Math.exp(-0.2*Math.sqrt(t1/n))-Math.exp(t2/n)+20+Math.E;
		return top;
	}
}
