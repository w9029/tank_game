package TankGame;

import java.util.Vector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math.*;


//ը��
class Bomb
{
	//����ը������
	int x,y;
	//����ը������
	int life=15;
	boolean isLive;
	
	public Bomb(int x,int y)
	{
		this.x=x;
		this.y=y;
		this.isLive=true;
	}
	
	//��������
	public void lifeDown()
	{
		if(life>0)
		{
			life--;
		}else
		{
			this.isLive=false;
		}
	}
	
	
}

//�ӵ���
class Bullet implements Runnable
{
	public int x,y;
	public static int speed=12;
	public int direct;
	boolean isLive=false;
	
	public Bullet (int x,int y,int direct)
	{
		this.x=x;
		this.y=y;
		this.direct=direct;
		isLive=true;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}
	
	public void run() {
		
		while(true)
		{
			try {
				
				Thread.sleep(50);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			switch(direct)
			{
			case 0:
				y-=speed;break;
			case 1:
				x+=speed;break;
			case 2:
				y+=speed;break;
			case 3:
				x-=speed;break;
			}
			if(x<0||x>800||y<0||y>600)
			{
				isLive=false;
				break;
			}
		}
		
	}
	
}

class Tank
{
	int x=0,y=0;
	int direct=0;//0�� 1�� 2�� 3��
	static int speed=3;
	int color;
	boolean isLive=true;
	static boolean stop=false;
	
	//�����ӵ�����
	Vector<Bullet> bb=new Vector<Bullet>();
	
	//����ͬ���ӵ�����
	static int bsl=12;
	
	//����һ������������MyPanel�ϵ�̹��
	Vector<EnemyTank> ets=new Vector<EnemyTank>();

	public Tank(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	
	public boolean isTouchOtherTank()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);
				
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//����ж�
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//����ж�
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						}
					}
				}
			}
			break;
		case 1:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);

				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//�ϵ��ж�
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						}
						//�µ��ж�
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//�ϵ��ж�
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						}
						//�µ��ж�
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						}
					}
				}
			}
			break;
		case 2:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);

				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//����ж�
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//����ж�
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						}
					}
				}
			}
			break;
		case 3:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);
				
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//�ϵ��ж�
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						}
						//�µ��ж�
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//�ϵ��ж�
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						}
						//�µ��ж�
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						}
					}
				}
			}
			break;
		}
		
		return b;
	}
	
}

class Hero extends Tank implements Runnable
{
	Bullet b=null;
	
	boolean jsq=true;//������ �������� �������
	static int shesu=400;//�����������룩
	
	boolean walking=false;
	
	public Hero(int x,int y)
	{
		super(x,y);
	}
	
	public boolean isTouchOtherTank()
	{
		boolean b=false;
		
		switch(this.direct)
		{
		case 0:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);
				
					if(et.direct==0||et.direct==2)
					{
						//����ж�
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//����ж�
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						}
					}
				
			}
			break;
		case 1:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);

				
					if(et.direct==0||et.direct==2)
					{
						//�ϵ��ж�
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						}
						//�µ��ж�
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//�ϵ��ж�
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						}
						//�µ��ж�
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						}
					}
				
			}
			break;
		case 2:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);

				
					if(et.direct==0||et.direct==2)
					{
						//����ж�
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//����ж�
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						}
					}
				
			}
			break;
		case 3:
			//̹������
			//ȡ�����е���̹��
			for(int i=0;i<ets.size();i++)
			{
				Tank et=ets.get(i);
				
				
					if(et.direct==0||et.direct==2)
					{
						//�ϵ��ж�
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						}
						//�µ��ж�
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//�ϵ��ж�
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						}
						//�µ��ж�
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						}
					}
				
			}
			break;
		}
		
		return b;
	}
	
	
	public void shot()
	{
		if(jsq&&this.isLive&&Tank.stop==false)
		{
			jsq=false;
		switch(this.direct)
		{
		case 0:
			b=new Bullet(x,y-15,0);
			bb.add(b);
			break;
		case 1:
			b=new Bullet(x+15,y,1);
			bb.add(b);
			break;
		case 2:
			b=new Bullet(x,y+15,2);
			bb.add(b);
			break;
		case 3:
			b=new Bullet(x-15,y,3);
			bb.add(b);
			break;
		}
		Thread t=new Thread(b);
		t.start();
		}
	
	}
	
	//̹���ƶ�
	public void moveHero(int d)
	{
		
		
		
		switch(d)
		{
		case 0:
		{
			if(!isTouchOtherTank())
			y-=speed;
			if(y<15)y=15;
			
		}break;
		case 1:
		{
			if(!isTouchOtherTank())
			x+=speed;
			if(x>780)x=780;
		}break;
		case 2:
		{
			if(!isTouchOtherTank())
			y+=speed;
			if(y>531)y=531;
		}break;
		case 3:
		{
			if(!isTouchOtherTank())
			x-=speed;
			if(x<15)x=15;
		}break;
		}
	}
	
	//�߳����� ��������
	public void run() {
		// TODO Auto-generated method stub

		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(jsq==false)
			{
				try {
					Thread.sleep(shesu);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsq=true;
			}
		}
	}
	
}

class EnemyTank extends Tank implements Runnable
{
	//��������ӵ�
	//Vector<Bullet> bb=new Vector<Bullet>();
	
	Tank hero=null;
	
	static int bsl=12;//����ͬ���ӵ���
	
	int jsq=0;//���ټ����� ����Ķ�
	static int djsq=10;//������  ��λ50���� 
	
	static int gdjuli=20;
	static int sjjuli;//��������ӳ��ƶ�����
	static int sj=30;
	
	public EnemyTank(int x, int y) {
		super(x, y);
		this.isLive=true;
	}
	
	//�жϵ����Ƿ���Ҫ����ӵ�
	public void needAddBullet()
	{

		this.jsq=0;
		EnemyTank et=this;
			if(et.isLive&&stop==false)
			{
				//�����������������ӵ��ĸ���
				if(et.bb.size()<et.bsl &&(int)(Math.random()*0)==0)
				//if(et.bb.size()<200)
				{
					//����ӵ�
					Bullet b=null;
					switch(et.direct)
					{
					case 0:
						b=new Bullet(et.x,et.y-15,0);
						break;
					case 1:
						b=new Bullet(et.x+15,et.y,1);
						break;
					case 2:
						b=new Bullet(et.x,et.y+15,2);
						break;
					case 3:
						b=new Bullet(et.x-15,et.y,3);
						break;
					}
					et.bb.add(b);
					Thread t=new Thread(b);
					t.start();
				}
			}
		
	}
	
	public boolean isTouchHero(Tank hero)
	{
		boolean b=false;
		
		Tank et=null;
		et=hero;
		if(hero.isLive)
	{	switch(this.direct)
		{
		case 0:
			//̹������
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//����ж�
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y-15>=et.y-15&&this.y-15<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//����ж�
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-15&&this.x+10<=et.x+15&&this.y-15>=et.y-10&&this.y-15<=et.y+10)
						{
							return true;
						}
					}
				}
			break;
		case 1:
			//̹������
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//�ϵ��ж�
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						}
						//�µ��ж�
						if(this.x+15>=et.x-10&&this.x+15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//�ϵ��ж�
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						}
						//�µ��ж�
						if(this.x+15>=et.x-15&&this.x+15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						}
					}
				}
			break;
		case 2:
			//̹������
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//����ж�
						if(this.x-10>=et.x-10&&this.x-10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-15&&this.y+15<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//����ж�
						if(this.x-10>=et.x-15&&this.x-10<=et.x+15&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						}
						//�ҵ��ж�
						if(this.x+10>=et.x-10&&this.x+10<=et.x+10&&this.y+15>=et.y-10&&this.y+15<=et.y+10)
						{
							return true;
						}
					}
				}
			
			break;
		case 3:
			//̹������
				if(et!=this)
				{
					if(et.direct==0||et.direct==2)
					{
						//�ϵ��ж�
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y-10>=et.y-15&&this.y-10<=et.y+15)
						{
							return true;
						}
						//�µ��ж�
						if(this.x-15>=et.x-10&&this.x-15<=et.x+10&&this.y+10>=et.y-15&&this.y+10<=et.y+15)
						{
							return true;
						}
					}
					else if(et.direct==1||et.direct==3)
					{
						//�ϵ��ж�
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y-10>=et.y-10&&this.y-10<=et.y+10)
						{
							return true;
						}
						//�µ��ж�
						if(this.x-15>=et.x-15&&this.x-15<=et.x+15&&this.y+10>=et.y-10&&this.y+10<=et.y+10)
						{
							return true;
						}
					}
				}
			break;
		}
	}
		return b;
	}

	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			if(stop)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				//25%�ĸ��ʻ���µķ���
				if((int)(Math.random()*4)==0)
				{
					this.direct=(int)(Math.random()*4);
				}
				//����ӳ��ƶ�����
				sjjuli=(int)(Math.random()*sj);
				
				switch(this.direct)
				{
				case 0:
					//̹�����ڳ���
					for(int i=0;i<gdjuli+sjjuli;i++)
					{
						if(stop==true)break;
						if(y>15&&!isTouchOtherTank()&&!isTouchHero(hero))
						{
							y-=speed;
							if(y<15)y=15;
						}else if((int)(Math.random()*3)==0)break;//���������ϰ�ʱ��һ�����ʲ�������ת��
						try {
							Thread.sleep(50);
							if(jsq<djsq)jsq++;
							if(jsq>=djsq)this.needAddBullet();
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 1:
					//̹�����ڳ���
					for(int i=0;i<gdjuli+sjjuli;i++)
					{	
						if(stop==true)break;
						if(x<780&&!isTouchOtherTank()&&!isTouchHero(hero))
						{
							x+=speed;
							if(x>780)x=780;
						}else if((int)(Math.random()*3)==0)break;
						try {
							Thread.sleep(50);
							if(jsq<djsq)jsq++;
							if(jsq>=djsq)this.needAddBullet();
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 2:
					//̹�����ڳ���
					for(int i=0;i<gdjuli+sjjuli;i++)
					{
						if(stop==true)break;
						if(y<531&&!isTouchOtherTank()&&!isTouchHero(hero))
						{
							y+=speed;
							if(y>531)y=531;
						}else if((int)(Math.random()*3)==0)break;
						try {
							Thread.sleep(50);
							if(jsq<djsq)jsq++;
							if(jsq>=djsq)this.needAddBullet();
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case 3:
					//̹�����ڳ���
					for(int i=0;i<gdjuli+sjjuli;i++)
					{
						if(stop==true)break;
						if(x>15&&!isTouchOtherTank()&&!isTouchHero(hero))
						{
							x-=speed;
							if(x<15)x=15;
						}else if((int)(Math.random()*3)==0)break;
						try {
							Thread.sleep(50);
							if(jsq<djsq)jsq++;
							if(jsq>=djsq)this.needAddBullet();
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				}
				
				//�������һ���µķ���
				if(!stop)
				{
					this.direct=(int)(Math.random()*4);
				}
				

				//�жϵ����Ƿ�����
				if(this.isLive==false)
				{
					//̹���������˳��߳�
					break;
				}
				
			}
			
		}

	}

}














