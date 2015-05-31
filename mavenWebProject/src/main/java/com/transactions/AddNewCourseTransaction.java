package com.transactions;

import javax.transaction.SystemException;
import javax.transaction.xa.XAException;

import com.jpa.Course;
import com.transactional.operation.CourseTransactionalOperation;

public class AddNewCourseTransaction implements Runnable{

	private Course course;
	private CourseTransactionalOperation courseOps = new CourseTransactionalOperation();
	
	public AddNewCourseTransaction(int crsId, String crsName){
		course = new Course();
		course.setId(crsId);
		course.setName(crsName);	
	}
	
	public AddNewCourseTransaction(Course crs){
		this.course = crs;
	}

	@Override
	public void run() {
		 try
		 {
			courseOps.createCourse(course);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}