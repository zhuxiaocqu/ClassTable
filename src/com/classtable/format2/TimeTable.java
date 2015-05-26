package com.classtable.format2;

import java.util.Collection;
import java.util.TreeSet;

import java.util.Iterator;

public class TimeTable {
	//从CourseObjectTreeSet中将课表数据转入到courseArr中
	TreeSet<CourseObject> CourseObjectTreeSet=new TreeSet<CourseObject>();
	public Course[][] courseArr=new Course[7][5];   //将一周七天，每天五大节课表示成一个二维数组
	public TimeTable(TreeSet<CourseObject> CourseObjectTreeSet){
		this.CourseObjectTreeSet=CourseObjectTreeSet;
		for(int i=0;i<7;i++){
			for(int j=0;j<5;j++){
				courseArr[i][j]=new Course();
			}
		}
		fillcourseArr();
	}
	//函数用于将TreeSet中课表数据放入CourseInfo数组中
	public void fillcourseArr(){
		char day;    //表示周几
		char time;   //表示是一天中的第几节课
		char time1;
		int row=0,col=0;  //通过判断上面两个字符型来确定该课程在courseArr中的位置
		for(CourseObject co:CourseObjectTreeSet){
			day=co.getClasses().charAt(0);
			time=co.getClasses().charAt(2);
			time1=co.getClasses().charAt(3);
			//根据周几判断横坐标
			switch(day){
			case '一':
				row=0;
				break;
			case '二':
				row=1;
				break;
			case '三':
				row=2;
				break;
			case '四':
				row=3;
				break;
			case '五':
				row=4;
				break;
			case '六':
				row=5;
				break;
			case '日':
				row=6;
				break;
			}
			//根据第几节课判断纵坐标
			switch(time){
			case '1':{
				if(time1=='-'){                //有些课程是11--12小节,也算在第五大节
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
	//函数用于检查课表是否有更新
	public int[] checkTableData(Course[][] courseArrNew,Course[][] courseArrOld){		
		int[] IdArr=new int[100];      //用int数组来存储新课表有变动出的Id序号
		int index=0;                   //数组下标
		Collection<CourseInfo> newSet=new TreeSet<CourseInfo>();   //定义两个TreeSet用于比较时的缓存
		Collection<CourseInfo> oldSet=new TreeSet<CourseInfo>();
		
		for(int i=0;i<7;i++){
			for(int j=0;j<5;j++){
				newSet.clear();
				oldSet.clear();
				newSet.addAll(courseArrNew[i][j].CourseSet);
				oldSet.addAll(courseArrOld[i][j].CourseSet);			
				Iterator<CourseInfo> itNew= newSet.iterator();		
//				System.out.println("星期"+(i+1)+", 第"+(j+1)+"大节课。");				
				//使用while循环进行遍历，在多线程同时访问该资源时易出错，但能再内部修改集合
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
				itNew=newSet.iterator();   //将迭代器初始化，下标回到最前面
				while(itNew.hasNext()){
					CourseInfo courseLeft=itNew.next();
//					System.out.println("left:"+courseLeft.Id+"  "+courseLeft.CourseName);
					IdArr[index]=courseLeft.Id;
					index++;
				}				
				//使用foreach来遍历，在访问集合元素时，该集合不能被改变，否则将引发异常
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
