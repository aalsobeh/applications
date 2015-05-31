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
import com.jpa.Registration;
import com.jpa.Student;

@Remote
public interface RegistrationManagerRemote {
	
	public int createRegistration(Registration regResource) throws NamingException, Exception;
	public int deleteRegistration(Registration regResource) throws NamingException, Exception;
	
	public List<Registration> listRegistrations() throws NamingException,
	NotSupportedException, SystemException, SecurityException,
	IllegalStateException, RollbackException, HeuristicMixedException,
	HeuristicRollbackException;
	
	public int getRegistrationCount() throws Exception;
	public int getStudentRegistrationCount(int studentId) throws Exception;
	public int getCourseRegistrationCount(int courseId) throws Exception;
	Registration getRegistration(int rid) throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException;
	
}
