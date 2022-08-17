package TankGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

//��¼�࣬�������ݺ��趨
class Recorder
{
	private static int enNum=20;//��������
	private static int myLife=3;//�������
	private static int killedEnNum=0;//ɱ���ĵ�����
	

	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static File f=new File("d:/TankGameSavedata");
	private static FileReader fr=null;
	private static BufferedReader br=null;
	static Vector<EnemyTank> ets=new Vector<EnemyTank>();
	static Hero hero=null;
	static int herox=0;
	static int heroy=0;
	static int herodir=0;
	
	

	//��ȡ����λ�ú��ӵ�λ��
	public static boolean Load()
	{
		
		
			if(f.isDirectory())//�ж��ļ����Ƿ����
			{

		
			try {
				fr=new FileReader("d:/TankGameSavedata/myRecording.txt");
				br=new BufferedReader(fr);
				String n="";
		//��ȡɱ����
				n=br.readLine();
				killedEnNum=Integer.parseInt(n);
			
		//��ȡhero��Ϣ
				n=br.readLine();
				String xyz[]=n.split(" ");
			
				Recorder.herox=Integer.parseInt(xyz[0]);
				Recorder.heroy=Integer.parseInt(xyz[1]);
				Recorder.herodir=Integer.parseInt(xyz[2]);
			
		//��ȡ������Ϣ
				while((n=br.readLine())!=null)
				{
					String xyz1[]=n.split(" ");
					EnemyTank et=new EnemyTank(Integer.parseInt(xyz1[0]),Integer.parseInt(xyz1[1]));
					et.direct=Integer.parseInt(xyz1[2]);
					Recorder.ets.add(et);
					
				}
				
			
				
				} catch ( Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}finally{
				try {
						br.close();
						fr.close();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}
				return true;
		
			}else{
				return false;
			}
		
	}

	//����̹�˵����꣬�����ӵ�
	public static void keepEnTankInfo()
	{
		try {
		//ֹͣ�ƶ�
			Tank.speed=0;
			Bullet.speed=0;
			Tank.stop=true;
			MyPanel.stop=true;
		
		//�����ļ���
			if(f.isDirectory())//�ж��ļ����Ƿ����
			{
				f.mkdir();//�ļ��Ѵ��� ����
			}else{
				f.mkdir();
			}
				
			fw=new FileWriter("d:/TankGameSavedata/myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(killedEnNum+"\r\n");
			
		//����hero��Ϣ
			String record=hero.x+" "+hero.y+" "+hero.direct;
			//д��
			bw.write(record+"\r\n");
			
		//���浱ǰ���˵�����ͷ���
			for(int i=0;i<ets.size();i++)
			{
				EnemyTank et=ets.get(i);
				
				if(et.isLive)
				{
					String record2=et.x+" "+et.y+" "+et.direct;
					
					//д��
					bw.write(record2+"\r\n");
					
					
				}
				
			}
		//�����ӵ�
//			Vector<Bullet> bb=null;
//			for(int i=0;i<ets.size();i++)
//			{
//				EnemyTank et=ets.get(i);
//				if(ets.get(i).bb.get(0).isLive)
//				System.out.println(ets.get(i).bb.get(0).x);
//				
//				
				
//				if(bb.size()>=1)
//				for(int j=0;j<et.bb.size();i++)
//					{
//						if(bb.get(i).isLive)
//						{
//						System.out.println(bb.get(i).x);
//						String record1="-10 "+et.bb.get(i).x+" "+et.bb.get(i).y+" "+et.bb.get(i).direct;
//						bw.write(record1+"\r\n");
//						}
//					}
//				System.out.println("���");
				
//			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//��ȡ�ļ��б����ɱ����
	public static void getRecording()
	{
		try {
			fr=new FileReader("d:/TankGameSavedata/myRecording.txt");
			br=new BufferedReader(fr);
			String n;
			n=br.readLine();
			killedEnNum=Integer.parseInt(n);
		
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//��¼ɱ����
	public static void keepRecording()
	{
		try {
			if(f.isDirectory())//�ж��ļ����Ƿ����
			{
				f.mkdir();
				System.out.println("�Ѵ����ļ��У�������Ϸ����");
			}else{
				f.mkdir();
			}
				
			fw=new FileWriter("d:/TankGameSavedata/myRecording.txt");
			bw=new BufferedWriter(fw);
			
			bw.write(killedEnNum+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//���ٵ���
	public static void reduceEnNum()
	{
		enNum--;
	}
	
	//ɱ��������
	public static void addEnNumRec()
	{
		killedEnNum++;
	}
	
	
	public static int getKilledEnNum() {
		return killedEnNum;
	}

	public static void setKilledEnNum(int allEnNum) {
		Recorder.killedEnNum = allEnNum;
	}
	
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	
	public static Vector<EnemyTank> getEts() {
		return ets;
	}

	public static void setEts(Vector<EnemyTank> ets) {
		Recorder.ets = ets;
	}
	

	public static Hero getHero() {
		return hero;
	}

	public static void setHero(Hero hero) {
		Recorder.hero = hero;
	}
}









