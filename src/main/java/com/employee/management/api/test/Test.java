package com.employee.management.api.test;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class Student{
	
	int id;
	int age;
	String name;
}


public class Test {
	
	public static void main(String[] args) {
		
		Student s1 = new Student(5,12, "Shubhanshu");
		Student s2 = new Student(3,23, "Mohit");
		Student s3 = new Student(7,98, "Hillu");
		Student s4 = new Student(4,18, "Tillu");
		Student s5 = new Student(1,34, "Pillu");
		
		List<Student> students = new ArrayList<>();
		
		students.add(s1);
		students.add(s2);
		students.add(s3);
		students.add(s4);
		students.add(s5);
		
		/*
		 * Collections.sort(students, (a,b) -> { if(a.getId()==b.getId()) return 0; else
		 * if(a.getId()<b.getId()) return 1; else if(a.getId()>b.getId()) return -1;
		 * 
		 * return 0; });
		 */
		
		Collections.sort(students , (a,b) ->{
			
			return b.getName().compareTo(a.getName());
		});
		
		
		for(Student s : students)
		{
			System.out.println(s);
		}
		
	}
	
	

}
