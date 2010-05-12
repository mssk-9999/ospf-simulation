package ospf.simulate.ui;
import java.io.*;
import java.lang.*;
import java.util.Vector;
import java.awt.*;

import javax.swing.JFrame;

import javax.swing.JPanel;

import ospf.simulate.Simulator;
import ospf.simulate.router.Interface;
import ospf.simulate.router.Link;
import ospf.simulate.router.Router;

@SuppressWarnings("serial")
/*public class TopologyPanel extends JPanel {

	
	/*
	 * Update the topology diagram of the area
	 */
	/*public static void updateDiagram() {
		
	}
}*/

class RouterUI{
	
	int ID;
	private int x, y;	//路由的坐标
	static int SIZE;
	private String name;
	static int ROUND = 100;
	
	public RouterUI(int iniID, int inix, int iniy, String iniName){
		SIZE = 400;
		ID = iniID;
		x = inix;
		y = iniy;
		name = iniName;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public String getString(){
		return name;
	}
	
	//画圆函数
	public void paint(Graphics gc){
		gc.drawString(name, x - 20, y);
		gc.drawOval(x - 50, y - 50, ROUND, ROUND);
	}
}

class Line{
	
	int ID1;
	int ID2;
	String s1, s2;
	static int x[] = {250, 550, 100, 700, 400};//五个路由的圆心坐标
	static int y[] = {100, 100, 300, 300, 450};//
	static int dele = 5;
	static int deles = 30;
	
	//public Line(){}
	public Line(int iniID1, int iniID2, String iniS1, String iniS2){
		ID1 = iniID1;
		ID2 = iniID2;
		s1 = iniS1;
		s2 = iniS2;
	}
	
	//划线函数
	public void paint(Graphics gc){
		gc.setColor(Color.red);
		gc.drawString(s1 , x[ID1] - (x[ID1] - x[ID2])/dele - deles, 
							y[ID1] - (y[ID1] - y[ID2])/dele);
		gc.drawString(s2 , x[ID2] - (x[ID2] - x[ID1])/dele - deles, 
							y[ID2] - (y[ID2] - y[ID1])/dele);
		gc.drawLine(x[ID1], y[ID1], x[ID2], y[ID2]);
	}
}


@SuppressWarnings("serial")
public class TopologyPanel  extends Panel{
	
	static RouterUI[] router = new RouterUI[5];//定义routerui数组，最大为5
	static Line[] line = new Line[20];//定义line数组，最大为20
	
	static int maxRouter = 0;//当前router数组中所存路由数量
	static int maxLine = 0;//当前line数组中所存数量
	
	static int ROUND = 100;//所画路由半径
	
	static int x[] = {250, 550, 100, 700, 400};//五个路由的圆心坐标
	static int y[] = {100, 100, 300, 300, 450};
	
	
	public TopologyPanel() 
	{
		setBounds(0,0,500,400); 
		setVisible(true); 
	} 

	public void paint(Graphics g) 
	{

		
		Vector<Router> d = Simulator.getRouters();
		maxRouter = 0;
		maxLine = 0;
		
		//初始化router数组
		for (int i = 0; i < d.size(); i++){
			
			router[i] = new RouterUI(i, x[i], y[i], d.get(i).getName());
			maxRouter++;
		}
		
		
		//初始化line数组
		for (int i = 0; i < d.size(); i++){
			
			//保存当前路由器的interface
			Vector<Interface> inf = d.get(i).getInterfaces();
			
			for (int m = 0; m < inf.size(); m++)
				//link非null
				if (inf.get(m).getLink() != null){
					
					Link tempLink = inf.get(m).getLink();	//临时link
					Interface tempInf = tempLink.getOtherSide();//临时interface
					
					for (int j = i + 1; j < d.size(); j++)
						//遍历vector，找到临时interface所属路由
						if (i < j && tempInf.getRouter().getName() == d.get(j).getName())
						{
							System.out.println(j);
							//赋予line值
							line[maxLine] = new Line(i ,j, tempLink.getOneSide().getInterfaceInfo(),
									tempLink.getOtherSide().getInterfaceInfo());
							maxLine++;
					}
				}
		}
		//super.paint(g);
		//路由器拓扑
		g.drawString("路由器拓扑", 350, 50);
		g.setColor(Color.black);//画红圆 
		
		//画路由
		for (int i = 0; i < maxRouter; i++)
			router[i].paint(g);
		
		//划线
		for (int i = 0; i < maxLine; i++)
			line[i].paint(g);
		
	} 
	
	
	public static void update(){
//		public static void main(String args[]){
		
		//初始化router数组
		
		Vector<Router> d = Simulator.getRouters();
		
		/*for (int i = 0; i < d.size(); i++){
			router[i] = new RouterUI(i, x[i], y[i], d.get(i).getName());
			maxRouter++;
		}*/
		
		
		//初始化line数组
		//router能不能直接相等
		/*for (int i = 0; i < d.size(); i++){
			
			Vector<Interface> inf = d.get(i).getInterfaces();
			
			for (int m = 0; m < inf.size(); m++)
				if (inf.get(m).getLink() != null){
					Link tempLink = inf.get(m).getLink();
					Interface tempInf = tempLink.getOtherSide();
					
					for (int j = 0; i < d.size(); j++)
						if (i != j && tempInf.getRouter().getName() == d.get(i).getName())
						{
							line[maxLine] = new Line(i ,j, tempLink.getOneSide().getInterfaceInfo(),
									tempLink.getOtherSide().getInterfaceInfo());
							maxLine++;
					}
				}
		}
		
		MainFrame.getMainFrame().validate();
		
		{
			line[maxLine] = new Line(0 ,1, "1", "2");
			maxLine++;
			line[maxLine] = new Line(1 ,2, "2", "3");
			maxLine++;
			line[maxLine] = new Line(2 ,3, "3", "5");
			maxLine++;
			line[maxLine] = new Line(3 ,4, "3", "5");
			maxLine++;
			line[maxLine] = new Line(0 ,2, "3", "5");
			maxLine++;
			line[maxLine] = new Line(0 ,3, "3", "5");
			maxLine++;
			line[maxLine] = new Line(0 ,4, "3", "5");
			maxLine++;
			line[maxLine] = new Line(1 ,3, "3", "5");
			maxLine++;
			line[maxLine] = new Line(1 ,4, "3", "5");
			maxLine++;
			line[maxLine] = new Line(2 ,4, "3", "5");
			maxLine++;
			
		}*/
//		
//		TopologyPanel dof = new TopologyPanel();
//		dof.setBackground(Color.pink);
//		JFrame f = new JFrame();
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setBounds(200, 50, 800, 600);
//		f.add(dof);
//		f.setVisible(true);
	}
}