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

import com.RM.CourseRM;
import com.jpa.Course;

/**
 * Session Bean implementation class CourseManager
 */
@Stateless
@Named("courseManager")
@RequestScoped
public class CourseManager implements CourseManagerRemote, TransactionManager {

	@PersistenceContext(name = "my_persistence_ctx")
	EntityManager entityManager;
	
	private CourseRM courseRm;
    /**
     * Default constructor. 
     */
    public CourseManager() {
        courseRm = new CourseRM();
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
	public int createCourse(Course course) throws NamingException,
			Exception {
		System.out.println("Course transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		courseRm.setCourseId(course.getId());
		courseRm.setCourseName(course.getName());
		courseRm.incr(1);
		entityManager.persist(courseRm.getCourse());

		return course.getId();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int deleteCourse(Course course) throws NamingException, Exception {
		System.out.println("Course transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		courseRm.incr(-1);
		entityManager.remove(course);

		return course.getId();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Course> listCourses() throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		
		System.out.println("Course transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		return entityManager.createQuery("select c from Course c")
				.getResultList();

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Course getCourse(int cid) throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		
		System.out.println("Course transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		return entityManager.find(Course.class, cid);

	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getCourseCount() throws Exception {
		return courseRm.get();
	}

}
