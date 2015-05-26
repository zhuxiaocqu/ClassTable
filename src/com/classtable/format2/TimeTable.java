package com.classtable.format2;

import java.util.Collection;
import java.util.TreeSet;

import java.util.Iterator;

public class TimeTable {
	//��CourseObjectTreeSet�н��α�����ת�뵽courseArr��
	TreeSet<CourseObject> CourseObjectTreeSet=new TreeSet<CourseObject>();
	public Course[][] courseArr=new Course[7][5];   //��һ�����죬ÿ�����ڿα�ʾ��һ����ά����
	public TimeTable(TreeSet<CourseObject> CourseObjectTreeSet){
		this.CourseObjectTreeSet=CourseObjectTreeSet;
		for(int i=0;i<7;i++){
			for(int j=0;j<5;j++){
				courseArr[i][j]=new Course();
			}
		}
		fillcourseArr();
	}
	//�������ڽ�TreeSet�пα����ݷ���CourseInfo������
	public void fillcourseArr(){
		char day;    //��ʾ�ܼ�
		char time;   //��ʾ��һ���еĵڼ��ڿ�
		char time1;
		int row=0,col=0;  //ͨ���ж����������ַ�����ȷ���ÿγ���courseArr�е�λ��
		for(CourseObject co:CourseObjectTreeSet){
			day=co.getClasses().charAt(0);
			time=co.getClasses().charAt(2);
			time1=co.getClasses().charAt(3);
			//�����ܼ��жϺ�����
			switch(day){
			case 'һ':
				row=0;
				break;
			case '��':
				row=1;
				break;
			case '��':
				row=2;
				break;
			case '��':
				row=3;
				break;
			case '��':
				row=4;
				break;
			case '��':
				row=5;
				break;
			case '��':
				row=6;
				break;
			}
			//���ݵڼ��ڿ��ж�������
			switch(time){
			case '1':{
				if(time1=='-'){                //��Щ�γ���11--12С��,Ҳ���ڵ�����
					col=0;
					break;
				}
				else if(time1=='0'||time1=='1'){
					col=4;
					break;
				}
			}			
			case '3':
				col=1;
				break;
			case '5':
				col=2;
				break;
			case '7':
				col=3;
				break;
			case '9':
				col=4;
				break;
			}
			courseArr[row][col].addCourseInfo(new CourseInfo(co.getId(),co.getName(),co.getWeeks(),co.getClassroom()));
		}		
	}
	//�������ڼ��α��Ƿ��и���
	public int[] checkTableData(Course[][] courseArrNew,Course[][] courseArrOld){		
		int[] IdArr=new int[100];      //��int�������洢�¿α��б䶯����Id���
		int index=0;                   //�����±�
		Collection<CourseInfo> newSet=new TreeSet<CourseInfo>();   //��������TreeSet���ڱȽ�ʱ�Ļ���
		Collection<CourseInfo> oldSet=new TreeSet<CourseInfo>();
		
		for(int i=0;i<7;i++){
			for(int j=0;j<5;j++){
				newSet.clear();
				oldSet.clear();
				newSet.addAll(courseArrNew[i][j].CourseSet);
				oldSet.addAll(courseArrOld[i][j].CourseSet);			
				Iterator<CourseInfo> itNew= newSet.iterator();		
//				System.out.println("����"+(i+1)+", ��"+(j+1)+"��ڿΡ�");				
				//ʹ��whileѭ�����б������ڶ��߳�ͬʱ���ʸ���Դʱ�׳����������ڲ��޸ļ���
				while(itNew.hasNext()){
					CourseInfo courseNew=itNew.next();
					Iterator<CourseInfo> itOld= oldSet.iterator();
//					System.out.println("New: "+courseNew.Id+"  "+courseNew.CourseName+"  "+courseNew.Weeks);
					while(itOld.hasNext()){
						CourseInfo courseOld=itOld.next();
//						System.out.println("Old: "+courseOld.Id+"  "+courseOld.CourseName+"  "+courseOld.Weeks);
						if(courseNew.CourseName.equals(courseOld.CourseName)){				
							if((!courseNew.Weeks.equals(courseOld.Weeks))||(!courseNew.Classroom.equals(courseOld.Classroom))){
								IdArr[index]=courseNew.Id;
								index++;
							}
							itNew.remove();
							itOld.remove();
						}
					}
				}		
//				Iterator<CourseInfo> itLeft=((TreeSet<CourseInfo>) itNew).iterator();		
				itNew=newSet.iterator();   //����������ʼ�����±�ص���ǰ��
				while(itNew.hasNext()){
					CourseInfo courseLeft=itNew.next();
//					System.out.println("left:"+courseLeft.Id+"  "+courseLeft.CourseName);
					IdArr[index]=courseLeft.Id;
					index++;
				}				
				//ʹ��foreach���������ڷ��ʼ���Ԫ��ʱ���ü��ϲ��ܱ��ı䣬���������쳣
//				for(CourseInfo courseNew:newSet){
//					System.out.println("New"+courseNew.CourseName);				
//					for(CourseInfo courseOld:oldSet){
////						System.out.println("Old"+courseOld.CourseName);
//						if(courseNew.CourseName.equals(courseOld.CourseName)){				
//							if((!courseNew.Weeks.equals(courseOld.Weeks))||(!courseNew.Classroom.equals(courseOld.Classroom))){
//								IdArr[index]=courseNew.Id;
//								index++;
//							}
//							newSet.remove(courseNew);
//							for(CourseInfo courseNew1:newSet)
//								System.out.println("left----"+courseNew1.CourseName);
//							oldSet.remove(courseOld);
//						}
//					}
//				}
//				for(CourseInfo courseLeft:newSet){
//					IdArr[index]=courseLeft.Id;
//					index++;
//				}			
			}
		}	
		return IdArr;
		
	}
}
