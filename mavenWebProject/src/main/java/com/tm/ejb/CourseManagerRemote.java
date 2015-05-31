package com.tm.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.jpa.Course;

@Remote
public interface CourseManagerRemote {

	public int createCourse(Course course) throws NamingException, Exception;
	public int deleteCourse(Course course) throws NamingException, Exception;
	
	public List<Course> listCourses() throws NamingException,
	NotSupportedException, SystemException, SecurityException,
	IllegalStateException, RollbackException, HeuristicMixedException,
	HeuristicRollbackException;
	
	public int getCourseCount() throws Exception;
	Course getCourse(int cid) throws NamingException, NotSupportedException,
			SystemException, SecurityException, IllegalStateException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException;
}
