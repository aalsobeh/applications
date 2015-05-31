package com.tm.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import com.RM.StudentRM;
import com.jpa.Student;

/**
 * Session Bean implementation class StudentManager
 */
@Stateless
@Named("studentManager")
@RequestScoped
public class StudentManager implements StudentManagerRemote, TransactionManager {

	@PersistenceContext(name = "my_persistence_ctx")
	EntityManager entityManager;
	
	private StudentRM studentRm; 
	
    /**
     * Default constructor. 
     */
    public StudentManager() {
    	studentRm = new StudentRM();
    }

	@Override
	public void begin() throws NotSupportedException, SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() throws RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SecurityException,
			IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStatus() throws SystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Transaction getTransaction() throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resume(Transaction arg0) throws InvalidTransactionException,
			IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback() throws IllegalStateException, SecurityException,
			SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTransactionTimeout(int arg0) throws SystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Transaction suspend() throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int createStudent(Student std) throws NamingException,
			Exception {
		System.out.println("Registration transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		studentRm.setStudenId(std.getId());
		studentRm.setStudentName(std.getName());
		studentRm.incr(1);
		entityManager.persist(std);

		return std.getId();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int deleteStudent(Student std) throws NamingException, Exception {
		System.out.println("Student transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		studentRm.incr(-1);
		entityManager.remove(std);

		return std.getId();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Student> listStudents() throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		System.out.println("Student transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		return entityManager.createQuery("select s from Student s")
				.getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Student getStudent(int stdId) throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		System.out.println("Student transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		return entityManager.find(Student.class, stdId);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getStudentCount() throws Exception {
		// TODO Auto-generated method stub
		return studentRm.get();
	}

}
