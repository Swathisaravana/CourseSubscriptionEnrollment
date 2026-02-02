package com.course.app;

import java.util.Date;
import java.util.Scanner;

import com.course.service.EnrollmentService;

public class CourseMain {
	private static EnrollmentService enrollmentService; 
	public static void main(String[] args) { 
	enrollmentService = new EnrollmentService(); 
	Scanner sc = new Scanner(System.in); 
	System.out.println("--- Online Course Enrollment Console ---");
	try { 
		
	boolean r = enrollmentService.enrollStudent("CRS2001","STU5004","Kavya Rao",25000.00, new Date()); 
	System.out.println(r ? "ENROLLED" : "FAILED"); 
	} catch(Exception e) { System.out.println(e); } 
	// TEST 2: Successful paid enrollment 
	try { 
		//Date enrollmentDate = new Date(System.currentTimeMillis());
	boolean r = enrollmentService.enrollStudent("CRS1001","STU5005","Rohan Bhat",19999.00,new Date()); 
	System.out.println(r ? "ENROLLED" : "FAILED"); 
	} catch(Exception e) { System.out.println(e); } 
	// TEST 3: Cancel enrollment with refund 
	try { 
	boolean r = enrollmentService.cancelEnrollment(150001,true); 
	System.out.println(r ? "CANCELLED/REFUNDED" : "FAILED"); 
	} catch(Exception e) 
	{ System.out.println(e); } 
	sc.close(); 
	} 
} 
