package com.classtable.login;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParseHtml {
	private File file;
	private Document doc;
	private Elements body;
	private Calendar calendar = Calendar.getInstance();

	public ParseHtml(String htmlFilePath) throws IOException {
		file = new File(htmlFilePath);
		doc = (Document) Jsoup.parse(file, "gb2312");
		body = doc.getElementsByTag("body");// body����
	}

	// ѧ�� ����ѧʱ �ϻ�ѧʱ
	public ArrayList<String> getSTE() {
		Elements STE = body.select("td[style=width:4%;text-align:right]");
		ArrayList<String> socre_TeachTime_ExTime = new ArrayList<String>();
		for (int i = 0; i < STE.size(); i++) {
			if(STE.get(i).text().equals("")){
				socre_TeachTime_ExTime.add(STE.get(i-3).text());
				continue;
			}
			socre_TeachTime_ExTime.add(STE.get(i).text());
		}
		return socre_TeachTime_ExTime;
	}

	// �ڿη�ʽ
	public ArrayList<String> getTeachKind() {
		Elements TeachKind = body.select("td[style=width:5%;text-align:left]");
		ArrayList<String> TK = new ArrayList<String>();
		for (int i = 0; i < TeachKind.size(); i++) {
			if(TeachKind.get(i).text().equals("")){
				TK.add(TeachKind.get(i-1).text());
				continue;
			}
			TK.add(TeachKind.get(i).text());
		}
		return TK;
	}

	// ���˷�ʽ
	public ArrayList<String> getExamKind() {
		Elements examKind = body.select("td[style=width:5%;text-align:center]");
		ArrayList<String> EK = new ArrayList<String>();
		for (int i = 3; i < examKind.size(); i++) {
			if(examKind.get(i).text().equals("")){
				EK.add(examKind.get(i-1).text());
				continue;
			}
			EK.add(examKind.get(i).text());
		}
		return EK;
	}

	// �õ�ѧ������
	public String getStuName() {
		Elements stuNameHtml = body.select("td[width=100%]");
		String textStuName = stuNameHtml.text();
		int start = textStuName.indexOf("��");
		int end = textStuName.indexOf("��");
		String strStuName = textStuName.substring(start + 2, end - 2);
		return strStuName;
	}

	// �õ��γ���
	public ArrayList<String> getClassName() {
		Elements classKind = body.select("td[style=width:21%;text-align:left]");// �γ���
		// classKindList��ſγ�����ArrayList
		ArrayList<String> classKindList = new ArrayList<String>();
		for (int i = 0; i < classKind.size(); i++) {
			if(classKind.get(i).text().equals("")){
				classKindList.add(classKind.get(i-1).text());
				continue;
			}
			classKindList.add(classKind.get(i).text());
		}
		return classKindList;
	}

	public ArrayList<String> getClassWeek() {
		Elements classTime = body.select("td[style=width:9%;text-align:left]");// �γ�ʱ��
		// ���Ͽ�ʱ�䣩�õ��Ͽ��ܴ�,�Ͽξ���ʱ��classWeek��ſγ��ܴ� classDay��ſγ�ÿ��ľ����Ͽ�ʱ��
		ArrayList<String> classWeek = new ArrayList<String>();
		for (int i = 0; i < classTime.size(); i++) {
			classWeek.add(classTime.get(i).text());
			i++;
		}
		return classWeek;
	}

	public ArrayList<String> getClassDay() {
		Elements classTime = body.select("td[style=width:9%;text-align:left]");// �γ�ʱ��
		// ���Ͽ�ʱ�䣩�õ��Ͽ��ܴ�,�Ͽξ���ʱ��classWeek��ſγ��ܴ� classDay��ſγ�ÿ��ľ����Ͽ�ʱ��
		ArrayList<String> classDay = new ArrayList<String>();
		for (int i = 0; i < classTime.size(); i++) {
			i++;
			classDay.add(classTime.get(i).text());
		}

		return classDay;
	}

	// �ο���ʦ
	public ArrayList<String> getTeacher() {
		Elements classTeacher = body
				.select("td[style=width:7%;text-align:left]");
		// classTeacherList�洢�ο���ʦ��Ϣ
		ArrayList<String> classTeacherList = new ArrayList<String>();
		for (int i = 0; i < classTeacher.size(); i++) {
			if(classTeacher.get(i).text().equals("")){
				classTeacherList.add(classTeacher.get(i-1).text());
				continue;
			}
			classTeacherList.add(classTeacher.get(i).text());
		}
		return classTeacherList;
	}

	// �Ͽεص�
	public ArrayList<String> getPlace() {
		Elements classRoom = body.select("td[style=width:13%;text-align:left]");
		// classRoomList�洢�Ͽεص���Ϣ
		ArrayList<String> classRoomList = new ArrayList<String>();
		for (int i = 0; i < classRoom.size(); i++) {
			classRoomList.add(classRoom.get(i).text());
		}
		return classRoomList;
	}

	// �γ�����
	public ArrayList<String> getClassKind() {
		Elements classWhich = body
				.select("td[style=width:10%;text-align:left]");
		// classWhichList�洢�γ�������Ϣ
		ArrayList<String> classWhichList = new ArrayList<String>();
		for (int i = 0; i < classWhich.size(); i++) {
			if(classWhich.get(i).text().equals("")){
				classWhichList.add(classWhich.get(i-1).text());
				continue;
			}
			classWhichList.add(classWhich.get(i).text());
		}
		return classWhichList;
	}

	// �õ�ѧ��
	public String getTerm() {
		Elements term = body
				.select("div[style=font-size:13px;font-weight:bold]");
		String strTerm = term.text();
		return strTerm;
	}

	// �õ���ǰ������
	public String getCurYear() {
		String yearMD;
		Date date = new Date();
		SimpleDateFormat dataformateY = new SimpleDateFormat("yyyy-MM-dd");
		yearMD = dataformateY.format(date);
		return yearMD;
	}

	// �õ���ǰ����ʱ��
	public String getCurTime() {
		String curTime;
		Date date = new Date();
		SimpleDateFormat dataformateT = new SimpleDateFormat("HH:mm:ss");
		curTime = dataformateT.format(date);
		return curTime;
	}

	// �ó���ǰ���Ƿ��ڽ����п�
	public boolean hasClassThisWeek(int curWeek, String classWeek) {
		String[] weeks = classWeek.split(",");
		int length = weeks.length;
		for (int i = 0; i < length; i++) {
			String[] sides = weeks[i].split("-");
			if (sides.length == 1)
				if (weeks[i].equals(String.valueOf(curWeek)))
					return true;
			if (sides.length > 1) {
				int smaller = Integer.parseInt(sides[0]);
//				System.out.println(smaller);
				int larger = Integer.parseInt(sides[1]);
//				System.out.println(larger);
				if (smaller <= curWeek && curWeek <= larger) {
					return true;
				}
			}
		}
		return false;
	}

	// �õ����������ڼ�
	public int getDayOfWeek() {
		int dayOfWeek = 0;
		Calendar calendar = Calendar.getInstance();
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return dayOfWeek;
	}

	public String showWeek(int dayOfWeek) {
		switch (dayOfWeek) {
		case 0:
			return "������";
		case 1:
			return "����һ";
		case 2:
			return "���ڶ�";
		case 3:
			return "������";
		case 4:
			return "������";
		case 5:
			return "������";
		case 6:
			return "������";
		default:
			return "error";
		}
	}

	// �õ������ǵڼ���
	public int getCurWeek(String timeOfTerm) {
		if (!timeOfTerm.equals("")) {
			System.out.println(timeOfTerm);
			String[] SEofT = timeOfTerm.split("��");
			String[] start = SEofT[0].split("-");
			String[] end = SEofT[1].split("-");
			// for(int i=0;i<start.length;i++){
			// System.out.println(start[i]);
			// System.out.println(end[i]);
			// }
			Calendar calendar = Calendar.getInstance();
			int dayOfYear = 0, DayOfTerm_start = 0, DayOfTerm_end = 0, curWeek = 0;
			// �õ�������һ���еĵڼ���dayOfYear
			dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
			// dayOfYear=69;
			int year_termStart = Integer.parseInt(start[0]);
			// int year_termEnd=Integer.parseInt(end[0]);
			// �õ���ѧ������һ���еĵڼ���
			DayOfTerm_start = dayInYear(year_termStart,
					Integer.parseInt(start[1]), Integer.parseInt(start[2]));
			// �õ�ѧ����ĩ������һ���еĵڼ���
			DayOfTerm_end = dayInYear(Integer.parseInt(end[0]),
					Integer.parseInt(end[1]), Integer.parseInt(end[2]));
			// �ڶ�ѧ��
			if (DayOfTerm_start < DayOfTerm_end) {
				if (dayOfYear > DayOfTerm_start && dayOfYear <= DayOfTerm_end) {
					int days = dayOfYear - DayOfTerm_start + 1;
					if (days % 7 == 0)
						curWeek = days / 7;
					else
						curWeek = days / 7 + 1;
				} else
					curWeek = 0;
			}
			// ��һѧ��
			else {
				if (dayOfYear >= DayOfTerm_start) {
					int days = dayOfYear - DayOfTerm_start + 1;
					if (days % 7 == 0)
						curWeek = days / 7;
					else
						curWeek = days / 7 + 1;
				} else if (dayOfYear < DayOfTerm_end) {
					int days_all, days_half;
					if ((year_termStart % 4 == 0 && year_termStart % 100 != 0)
							|| year_termStart % 400 == 0)
						days_half = 366 - DayOfTerm_start + 1;
					else
						days_half = 365 - DayOfTerm_start + 1;
					days_all = days_half + dayOfYear;
					if (days_all % 7 == 0)
						curWeek = days_all / 7;
					else
						curWeek = days_all / 7 + 1;
				} else
					curWeek = 0;
			}
			return curWeek;
		} else
			return 0;
	}

	public int dayInYear(int year, int month, int day) {
		int dayInYear = 0;
		for (int i = 1; i < month; i++) {
			switch (i) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				dayInYear += 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				dayInYear += 30;
				break;
			case 2:
				if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
					dayInYear += 29;
				else
					dayInYear += 28;
				break;
			}
		}
		return dayInYear + day;
	}
}
