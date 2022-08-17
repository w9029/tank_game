package TankGame;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.lang.Math.*;

public class TankGame1 extends JFrame implements ActionListener,KeyListener{

	static MyPanel mp=null;
	
	//����һ����ʼ���
	static MyStage1Panel mstgp1=null;
	
	//�˵�ѡ��
	JMenuBar jmb=null;
	
	JMenu jm1=null;//��ʼ��Ϸ
	JMenuItem jmi1=null;
	JMenuItem jmi2=null;
	JMenuItem jmi3=null;
	JMenuItem jmi4=null;
	JMenuItem jmi5=null;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			TankGame1 a=new TankGame1();
	}
	
	public TankGame1()
	{
		
		//�����˵����˵�ѡ��
		jmb=new JMenuBar();
		jm1=new JMenu("��Ϸ(G)");
		jm1.setMnemonic('G');
		jmi1=new JMenuItem("��ʼ����Ϸ(N)");
		jmi1.setMnemonic('N');
		jmi2=new JMenuItem("�˳���Ϸ(E)");
		jmi2.setMnemonic('E');
		jmi3=new JMenuItem("���沢�˳�(C)");
		jmi3.setMnemonic('C');
		jmi4=new JMenuItem("��ȡ����(L)");
		jmi4.setMnemonic('L');
		jmi5=new JMenuItem("����ģʽ(D)");
		jmi5.setMnemonic('D');
		
		//ע�����
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newgame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExit");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Load");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("Diyu");
		
		
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		jm1.add(jmi5);
		jmb.add(jm1);
		this.setJMenuBar(jmb);
		
		mstgp1=new MyStage1Panel();
		this.add(mstgp1);
		
		this.addKeyListener(this);
		
		Thread t=new Thread(mstgp1);
		t.start();
		
		this.setSize(800,600);
		this.setLocationRelativeTo(null);//���ô��ھ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("TankGame");
		//this.setIconImage(new ImageIcon("/image/dai.jpg").getImage());	
		this.setResizable(false);//��ֹ���Ĵ��ڴ�С
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//���û������ѡ����������
		if(e.getActionCommand().equals("newgame"))//&&mp.isLive==false
		{
			if(mp.isLive||Tank.stop)
			{
				for(int i=mp.ets.size()-1;i>=0;i--)
				{
					mp.ets.remove(i);
					
				}
				this.remove(mp);
			
			}
			mp=null;
			mp=new MyPanel(true);
			
			Tank.speed=mp.ts;
			Bullet.speed=mp.ds;
			Tank.stop=false;
			mp.stop=false;
			mp.isLive=true;
			
			mp.enSize=18;//��������
			mp.tptank=12;
			
			while(mp.ets.size()>11)
			{
				int s=mp.ets.size()-1;
				mp.ets.remove(s);
			}
			
			//���ڻ�ɱʱ��ͼƬ
			mp.aaa=(int)(Math.random()*3);
			
			
			this.add(mp);
			
			//�����߳�
			Thread t=new Thread(mp);
			t.start();
			mstgp1.isLive=false;
			this.remove(mstgp1);
			
			this.remove(mstgp1);
			
			//ע�����
			this.addKeyListener(mp);
			this.setVisible(true);
			
		}
		else if(e.getActionCommand().equals("exit"))
		{
			//�û�������˳�ϵͳ�Ĳ˵�
			//������Ϸ����
			Recorder.keepRecording();
			
			//�˳�
			System.exit(0);
		}
		else if(e.getActionCommand().equals("saveExit")&&mp.ets.size()>0)
		{
			//������ٵ��˵������͵��˵�����
			Recorder.setEts(mp.ets);
			Recorder.setHero(mp.hero);
			
				
			
			Recorder.keepEnTankInfo();
			//�˳�
			System.exit(0);
		}
		else if(e.getActionCommand().equals("Load")&&mstgp1.isLive&&Recorder.Load())
		{
			
				mp=new MyPanel(false);
				
				mp.isLive=true;
			
				mp.ets=Recorder.ets;
				
				mp.hero.x=Recorder.herox;
				mp.hero.y=Recorder.heroy;
				mp.hero.direct=Recorder.herodir;
				mp.hero.ets=Recorder.getEts();
			
				this.add(mp);
			
				Thread t2 =new Thread(mp.hero);
				t2.start();
			
			//���Ƶ�������
				for(int i=0;i<mp.ets.size();i++)
				{
					EnemyTank et=null;
					et=mp.ets.get(i);
					et.ets=mp.ets;
					et.hero=mp.hero;
					Thread tt=new Thread(et);
					tt.start();
				}
			
			//�����߳�
				Thread t=new Thread(mp);
				t.start();
				this.remove(mstgp1);
			
			//ע�����
				this.addKeyListener(mp);
				this.setVisible(true);
			
			
			

		}else if(e.getActionCommand().equals("Diyu")&& mp.isLive==true&&mp.ets.size()>0)
		{
			
			
			//����̹���ƶ��ٶ�
			Tank.speed=6;
			//�ӵ��ƶ��ٶ�
			Bullet.speed=25;
			//��ҵ�������
			Tank.bsl=12;
			//���˵�������
			EnemyTank.bsl=100;
			//��������������룩��+50ms��
			Hero.shesu=400;
			//����������(50����)
			EnemyTank.djsq=0;
			//����ת��Ƶ��
			mp.enSize=18;//��������
			mp.tptank=15;
			mp.tkn=mp.tptank;
			while(mp.ets.size()<mp.tptank)
			{
				mp.addEnemy();
			}

			
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER&&mp.isLive==false)
		{
			if(mp.isLive||Tank.stop)
			{
				for(int i=mp.ets.size()-1;i>=0;i--)
				{
					mp.ets.remove(i);
					
				}
				this.remove(mp);
			
			}
			
			mp=null;
			mp=new MyPanel(true);
			
			Tank.speed=mp.ts;
			Bullet.speed=mp.ds;
			Tank.stop=false;
			mp.stop=false;
			mp.isLive=true;
			
			mp.enSize=18;//��������
			mp.tptank=12;
			
			while(mp.ets.size()>11)
			{
				int s=mp.ets.size()-1;
				mp.ets.remove(s);
			}
			
			mp.aaa=(int)(Math.random()*3);
			
			
			this.add(mp);
			
			//�����߳�
			Thread t=new Thread(mp);
			t.start();
			mstgp1.isLive=false;
			this.remove(mstgp1);
			
			//ע�����
			this.addKeyListener(mp);
			this.setVisible(true);
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}

//��ʼ����
class MyStartPanel extends JPanel implements Runnable
{
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 800, 600);
		
		
		
		g.setColor(Color.yellow);
		Font myFont=new Font("������κ",Font.BOLD,30);
		g.setFont(myFont);
		g.drawString("stage:1", 350, 280);
		
			
		//image001=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/666.gif"));
			
		//g.drawImage(image001, 380, 280, 50, 50,this);
			
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
		while(true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			this.repaint();
		}
		
	}
	
	
}


//��һ����ʾ
class MyStage1Panel extends JPanel implements Runnable
{
	int t=0;
	boolean isLive;
	
	Image image001=null;//666
	Image image002=null;//stage1-1
	Image image003=null;//stage1-2
	Image image004=null;//huoyuanjia
	Image image005=null;//shuoming
	Image image006=null;//9029
	
	public MyStage1Panel()
	{
		image002=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/stage1-1.png"));
		image003=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/stage1-2.png"));
		image004=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/huoyuanjia.jpg"));
		image005=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/shuoming.png"));
		image006=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/9029.png"));
		
		isLive=true;
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		
		g.drawImage(image004, 335, 150, 120, 120,this);
		
		g.setColor(Color.WHITE);
		Font myFont=new Font("���Ŀ���",Font.BOLD,17);
		g.setFont(myFont);
		g.drawString("���س�����ʼ��Ϸ", 325, 470);
		g.drawImage(image005, 0, 10, 250, 160,this);
		g.drawImage(image006, 715, 515, 70, 30,this);
		
		
		
		if(t%2==0)
		{

			g.drawImage(image002, 250, 270, 300, 100,this);
			
			//image001=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/666.gif"));
			//g.drawImage(image001, 380, 280, 50, 50,this);
			
		}else
		{
			g.drawImage(image003, 250, 270, 300, 100,this);
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		
		while(true)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			t++;
			this.repaint();
		}
	}
	
}

class MyPanel extends JPanel implements KeyListener,Runnable
{
	static boolean isLive=false;
	
	boolean Diyujz=false;
	
	Hero hero=null;//�����ҵ�̹��
	
	
	//�������
	public static Vector<EnemyTank> ets=new Vector<EnemyTank>();
	static int enSize=18;//��������
	
	//���ڻ�ɱʱ��ͼƬ
	static int tptank=12;//ͬ��������
	static int tkn=tptank;
	int tknjsq=0;
	boolean tknbiaoji=false;
	int aaa=(int)(Math.random()*3);
	
	int ts=3,ds=12;
	
	static boolean stop=false;
	
	int biaoji[]=new int[4];//����ƶ�����
	
	boolean walking=false;
	
	//Walk walk=null;
	
	//����ը������
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	
	//��������ͼƬ ���һ��ը��
	Image image1=null;
	Image image2=null;
	Image image3=null;
	//�����
	Image image4=null;//woc
	Image image5=null;//jing
	Image image6=null;//rebuqi
	Image image7=null;//zhishang
	Image image8=null;//die
	Image image9=null;//jiamian
	Image image10=null;//Diyujz
	Image image11=null;//puchi
	Image image12=null;//heng
	Image image13=null;//666
	Image image14=null;//9029
	Image image15=null;//shuoming
	
	public MyPanel(boolean isnew)
	{
	//�ָ���¼
		//Recorder.getRecording();
		
	//��Ϸ�趨
		//����̹���ƶ��ٶ�
		Tank.speed=3;
		//�ӵ��ƶ��ٶ�
		Bullet.speed=12;
		//��ҵ�������
		Tank.bsl=12;
		//���˵�������
		EnemyTank.bsl=12;
		//��������������룩��+50ms��
		Hero.shesu=400;
		//����������(50����)
		EnemyTank.djsq=12;
		//����ת��Ƶ��
		EnemyTank.gdjuli=30;
		EnemyTank.sj=30;
		
		
	//��ҳ�ʼλ��
		hero=new Hero(400,300);
		Thread h=new Thread(hero);
		h.start();
		hero.ets=ets;
		
		if(isnew==true){
		//��ʼ������̹��
		for(int i=0;i<tptank;i++)
		{
			//����һ������
			addEnemy();
		}
		}
		
		
		//��ʼ��ͼƬ
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/1.png"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/2.png"));
		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/3.png"));
		image4=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/woc.jpg"));
		image5=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/jing.jpg"));
		image6=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/caozuo.png"));
		image7=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/zhishang.png"));
		image8=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/die.jpg"));
		image9=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/jiamian.jpg"));
		image10=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/Diyujz.png"));
		image11=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/puchi.jpg"));
		image12=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/heng.png"));
		image13=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/666.gif"));
		image14=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/9029.png"));
		image15=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/image/shuoming.png"));
		
		//��ʼը��
		Bomb bomb=new Bomb(-100,-100);
		bombs.add(bomb);
		
	}
	
	public void paint(Graphics g)
	{
		
		super.paint(g);
		g.fillRect(0, 0, 800, 600);
		
	//������ʾ��Ϣ
		this.showInfo(g);
		
	//���Լ���̹��
		if(hero.isLive)
		{
			this.drawTank(hero.getX(),hero.getY(), g, hero.getDirect(),1);
		}
		
	//���Լ����ӵ�
		for(int i=0;i<hero.bb.size();i++)
		{
			Bullet myb=hero.bb.get(i);
			if(myb!=null&&myb.isLive==true)
			{
				g.fillRect(myb.x, myb.y, 2, 2);
			}
			if(myb.isLive==false)
			{
				hero.bb.remove(myb);
			}
			
		}
		
	//����ը��
		for(int i=0;i<bombs.size();i++)
		{
			Bomb bomb=bombs.get(i);
			
			if(bomb.life>10)			{
				g.drawImage(image1, bomb.x, bomb.y, 30, 30,this);
			}else if(bomb.life>5)
			{
				g.drawImage(image2, bomb.x, bomb.y, 30, 30,this);
			}else{
				g.drawImage(image3, bomb.x, bomb.y, 30, 30,this);
			}
			bomb.lifeDown();
			//ը������
			if(bomb.life==0)
			{
				bombs.remove(bomb);
			}
		}
		
	//������̹��
		for(int i=0;i<ets.size();i++)
		{
			EnemyTank et=ets.get(i);
			
			if(et.isLive)
			{	
				this.drawTank(et.getX(), et.getY(), g, et.getDirect(), 0);
				//���������ӵ�
				for(int j=0;j<et.bb.size();j++)
				{
					//ȡ���ӵ�
					Bullet emb=et.bb.get(j);
					if(emb.isLive)
					{
						g.fillRect(emb.x, emb.y, 2, 2);
					}else
					{
						//��������ӵ����� ɾ���ӵ�
						et.bb.remove(emb);
					}
				}
				
			}else ets.remove(et);
		}
		
		
	//��ɱʱ��ͼ 
		if(this.ets.size()<tkn)
		{
			enSize--;
			if(ets.size()<enSize)
			{
				this.addEnemy();
			}
			tkn=ets.size();
			this.tknbiaoji=true;
			aaa=(int)(Math.random()*3);
			
		}
		if(tknbiaoji && aaa%3==0)
		{
			g.drawImage(image4, 1, 465, 80,80,this);
		}else if(tknbiaoji && aaa%3==1){
			g.drawImage(image5, 1, 465, 80,80,this);
		}else if(tknbiaoji && aaa%3==2){
			g.drawImage(image6, 1, 465, 110,80,this);
		}
		
	//hero����
		if(hero.isLive==false)
		{
			
			Tank.speed=0;
			Bullet.speed=0;
			Tank.stop=true;
			this.isLive=false;
			
			g.drawImage(image8, 0, -50, 800,600,this);
			if(aaa%3==0)
			{
				g.drawImage(image7, 316, 50, 150,150,this);
			}
			else if(aaa%3==1)
			{
				g.drawImage(image11, 316, 50, 150,150,this);
			}
			else
			{
				g.drawImage(image12, 316, 50, 150,150,this);
			}
			g.setColor(Color.WHITE);
			Font myFont=new Font("���Ŀ���",Font.BOLD,17);
			g.setFont(myFont);
			g.drawString("���س������¿�ʼ", 325, 470);
			g.drawImage(image14, 715, 515, 70, 30,this);
			g.drawImage(image15, 0, 10, 250, 160,this);
		}
		
	//����ȫ������
		if(ets.size()==0)
		{
			
			
			Tank.speed=0;
			Bullet.speed=0;
			Tank.stop=true;
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			if(EnemyTank.bsl==12)
			{
				g.drawImage(image9, 340, 120, 110,140,this);;
				g.setColor(Color.WHITE);
				Font myFont=new Font("���Ŀ���",Font.BOLD,17);
				g.setFont(myFont);
				g.drawString("����Ϊ�����ͽ����ˣ�", 315, 320);
				g.drawString("����Ͳ¶���", 345, 345);
				g.drawString("�������������Ѿ���������", 297, 370);
				g.drawString("��������Ե����¿�ʼ", 310, 425);
				g.drawString("Ȼ����һ��", 320, 445);
				g.setColor(Color.ORANGE);
				g.drawString("����ģʽ", 412, 445);
				g.drawImage(image14, 715, 515, 70, 30,this);
				
				
				
			}else
			{
				g.drawImage(image5, 336, 150, 120,120,this);
				g.setColor(Color.WHITE);
				Font myFont=new Font("���Ŀ���",Font.BOLD,17);
				g.setFont(myFont);
				g.drawString("woc ���㶼��Ӯ", 335, 320);
				g.drawString("���Լ���ûӮ�����ģʽ", 300, 345);
				g.drawString("���ǲ��Ƿ���ʲôbug�ˡ���", 295, 370);
				g.drawImage(image13, 200, 230, 60,60,this);
				g.drawImage(image13, 530, 230, 60,60,this);
				g.drawImage(image14, 715, 515, 70, 30,this);
			}
			
			
		}
		
	//����ͣ

		if(stop==true)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			g.setColor(Color.yellow);
			Font myFont=new Font("����",Font.BOLD,30);
			g.setFont(myFont);
			g.drawString("Pause", 356, 310);
			g.drawImage(image4, 356, 190, 80,80,this);
			g.drawImage(image14, 715, 515, 70, 30,this);
			g.drawImage(image15, 0, 10, 250, 160,this);

		}
		
	}
	
	//������ʾ��Ϣ
	public void showInfo(Graphics g)
	{
		//����̹�˵���Ϣ
		this.drawTank(50,640,g,0,0);
		g.setColor(Color.black);
		g.drawString(Recorder.getEnNum()+"", 65, 645);
				
		this.drawTank(110,640,g,0,1);
		g.setColor(Color.black);
		g.drawString(Recorder.getMyLife()+"", 125, 645);
		
		//������ҵ��ܳɼ�
		g.setColor(Color.black);
		Font f=new Font("����",Font.BOLD,20);
		g.setFont(f);
		g.drawString("������ʷ�ܳɼ�",825,35);
		
		this.drawTank(840, 70, g, 0, 0);
		g.setColor(Color.black);
		g.drawString(Recorder.getKilledEnNum()+"", 865, 77);
		
		
	}
	
	public void addEnemy()
	{
		
			//����һ������
			EnemyTank et=new EnemyTank((int)(Math.random()*755)+20,(int)(Math.random()*515+20));
			et.setColor(0);
			et.setDirect((int)(Math.random()*4));
			et.hero=hero;
			
			//��MyPanel�ĵ���̹�����������õ���
			et.ets=ets;
			
			
			//���������߳�
			Thread t=new Thread(et);
			t.start();
		
			//����
			ets.add(et);
		
	}
	
	//��������̹�˺�����ӵ�
	public void hitEnemyTank()
		{
			//�жϵ����Ƿ񱻻���
			for(int i=0;i<hero.bb.size();i++)
			{
				//ȡ���ӵ�
				Bullet myb=hero.bb.get(i);
				//�ж��ӵ��Ƿ���Ч
				if(myb.isLive)
				{
					//ȡ��̹��
					for(int j=0;j<ets.size();j++)
					{
						EnemyTank et=ets.get(j);
						if(et.isLive)
						{
							this.hitTank(myb, et);
							if(et.isLive==false)
							{
								Recorder.reduceEnNum();//���ٵ���������¼
								Recorder.addEnNumRec();//����ɱ����
							}
						}
					}
				}
			}
		}
		
		//hero�Ƿ񱻻���
		public void hitMe()
		{
			//ȡ��ÿһ�����˵�̹��
			for(int i=0;i<this.ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				
				//ȡ�������ӵ�
				for(int j=0;j<et.bb.size();j++)
				{
					Bullet emb=et.bb.get(j);
					
					if(hero.isLive)
					this.hitTank(emb,hero);
				}
			}
		}
		
		//�ж�̹���Ƿ񱻻���
		public void hitTank(Bullet b, Tank et)
		{
			//�ж�̹�˷���
			switch(et.direct)
			{
			//����Ϊ����
			case 0:
			case 2:
				if(b.x>=et.x-10&&b.x<=et.x+10&&b.y>=et.y-15&&b.y<=et.y+15)
				{
					//���� �ӵ����� ��������
					b.isLive=false;
					et.isLive=false;

					//����ը�� ����Vector
					Bomb bomb=new Bomb(et.x-15,et.y-15);
					bombs.add(bomb);
				}break;
			//����Ϊ����
			case 1:
			case 3:
				if(b.x>=et.x-15&&b.x<=et.x+15&&b.y>=et.y-10&&b.y<=et.y+10)
				{
					//���� �ӵ����� ��������
					b.isLive=false;
					et.isLive=false;

					//����ը�� ����Vector
					Bomb bomb=new Bomb(et.x-15,et.y-15);
					bombs.add(bomb);	
				}break;
			}
			
		}
	
	//��̹��
	public void drawTank(int x,int y,Graphics g,int direct,int type)
	{
		//�����ͼ�ο�������
		int a=x,b=y;
		switch(type)
		{
			case 0:
				g.setColor(Color.GREEN);
				break;
			case 1:
				g.setColor(Color.YELLOW);
				break;
		}
		switch(direct)
		{
			//����
			case 0:
				a=x-10;
				b=y-15;
				//��߾���
				g.fill3DRect(a,b, 5, 30,true);
				//�ұ߾���
				g.fill3DRect(a+15,b, 5, 30,true);
				//�м����
				g.draw3DRect(a+5,b+5, 10, 20, true);
				//��Բ
				g.fillOval(a+5, b+10, 10, 10);
				//����
				g.drawLine(a+10, b+15, a+10,b);
				break;
			//����
			case 1:
				a=x-15;
				b=y-10;
				//�ϱ߾���
				g.fill3DRect(a,b, 30, 5,true);
				//�±߾���
				g.fill3DRect(a,b+15, 30, 5,true);
				//�м����
				g.draw3DRect(a+5,b+5, 20, 10, true);
				//��Բ
				g.fillOval(a+10, b+5, 10, 10);
				//����
				g.drawLine(a+15, b+10, a+30,b+10);
				break;
			//����
			case 2:
				a=x-10;
				b=y-15;
				//��߾���
				g.fill3DRect(a,b, 5, 30,true);
				//�ұ߾���
				g.fill3DRect(a+15,b, 5, 30,true);
				//�м����
				g.draw3DRect(a+5,b+5, 10, 20, true);
				//��Բ
				g.fillOval(a+5, b+10, 10, 10);
				//����
				g.drawLine(a+10, b+15, a+10,b+30);
				break;
			//����
			case 3:
				a=x-15;
				b=y-10;
				//�ϱ߾���
				g.fill3DRect(a,b, 30, 5,true);
				//�±߾���
				g.fill3DRect(a,b+15, 30, 5,true);
				//�м����
				g.draw3DRect(a+5,b+5, 20, 10, true);
				//��Բ
				g.fillOval(a+10, b+5, 10, 10);
				//����
				g.drawLine(a+15, b+10, a,b+10);
				break;
		}
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode()==KeyEvent.VK_W&&!Tank.stop)
		{
			biaoji[0]=1;//��ǰ��������
			this.hero.setDirect(0);
			this.walking=true;
			
		}else if(e.getKeyCode()==KeyEvent.VK_D&&!Tank.stop)
		{
			biaoji[1]=1;//��ǰ��������
			this.hero.setDirect(1);
			this.walking=true;
			
		}else if(e.getKeyCode()==KeyEvent.VK_S&&!Tank.stop)
		{
			biaoji[2]=1;//��ǰ��������
			this.hero.setDirect(2);
			this.walking=true;
			
		}else if(e.getKeyCode()==KeyEvent.VK_A&&!Tank.stop)
		{
			biaoji[3]=1;
			this.hero.setDirect(3);
			this.walking=true;
			
		}
		
		//�Ƿ���J
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			if(this.hero.bb.size()<this.hero.bsl)
			this.hero.shot();
		}
		
		//�Ƿ��¿ո�
		if(e.getKeyCode()==KeyEvent.VK_SPACE&&this.hero.isLive)
		{
			if(Tank.speed!=0)
			{
				Tank.speed=0;
				Bullet.speed=0;
				Tank.stop=true;
				stop=true;
			}
			else
			{
				Tank.speed=ts;
				Bullet.speed=ds;
				Tank.stop=false;
				stop=false;
			}
			
		}

		//���»���
		this.repaint();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W&&biaoji[0]==1)
		{
			int flag=0;
			biaoji[0]=0;
			for(int i=0;i<4;i++)
			{
				if(biaoji[i]==1){
					flag=1;
					this.hero.direct=i;
				}
				
			}
			if(flag==0)
			this.walking=false;
		}else if(e.getKeyCode()==KeyEvent.VK_D&&biaoji[1]==1)
		{
			int flag=0;
			biaoji[1]=0;
			for(int i=0;i<4;i++)
			{
				if(biaoji[i]==1){
					flag=1;
					this.hero.direct=i;
				}
			}
			if(flag==0)
			this.walking=false;
		}else if(e.getKeyCode()==KeyEvent.VK_S&&biaoji[2]==1)
		{
			int flag=0;
			biaoji[2]=0;
			for(int i=0;i<4;i++)
			{
				if(biaoji[i]==1){
					flag=1;
					this.hero.direct=i;
				}
			}
			if(flag==0)
			this.walking=false;
		}else if(e.getKeyCode()==KeyEvent.VK_A&&biaoji[3]==1)
		{
			int flag=0;
			biaoji[3]=0;
			for(int i=0;i<4;i++)
			{
				if(biaoji[i]==1){
					flag=1;
					this.hero.direct=i;
				}
			}
			if(flag==0)
			this.walking=false;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//ÿ��50�����ػ�
		while(true)
		{
			if(this.tknbiaoji==false)tknjsq=0;
			try {
				Thread.sleep(50);tknjsq++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(tknjsq==30)
			{
				this.tknbiaoji=false;
				this.stop=false;
			}
			
			
			//�ж�hero�Ƿ�Ҫ�ƶ�
			if(walking)
			{
				hero.moveHero(hero.direct);
			}
			
			//�жϵ����Ƿ񱻻���
			this.hitEnemyTank();
			
			//�ж����Ƿ񱻻���
			this.hitMe();
			
			
			//�ػ�
			this.repaint();
			if(tknjsq>100)tknjsq=0;
		}
	}
}






