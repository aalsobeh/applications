package com.tm.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.jpa.Student;

@Remote
public interface StudentManagerRemote {

	public int createStudent(Student student) throws NamingException, Exception;
	public int deleteStudent(Student student) throws NamingException, Exception;
	
	public List<Student> listStudents() throws NamingException,
	NotSupportedException, SystemException, SecurityException,
	IllegalStateException, RollbackException, HeuristicMixedException,
	HeuristicRollbackException;
	
	public int getStudentCount() throws Exception;
	public Student getStudent(int stdId) throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException;

}
