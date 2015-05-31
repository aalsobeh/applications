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

import com.RM.RegistrationRM;
import com.jpa.Course;
import com.jpa.Registration;

/**
 * Session Bean implementation class RegistrationManager
 */
@Stateless
@Named("registrationManager")
@RequestScoped
public class RegistrationManager implements RegistrationManagerRemote, TransactionManager {

	@PersistenceContext(name = "my_persistence_ctx")
	EntityManager entityManager;

	private RegistrationRM registrationRm;
	
    /**
     * Default constructor. 
     */
    public RegistrationManager() {
        // TODO Auto-generated constructor stub
    	registrationRm = new RegistrationRM();
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int createRegistration(Registration regResource) throws NamingException, Exception {
		
		System.out.println("Registration transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		Registration reg = new Registration();
		registrationRm.setStudentId(regResource.getStudentId());
		registrationRm.setCourseId(regResource.getCourseId());
		reg = registrationRm.getRegistration();
		registrationRm.incr(1);
		entityManager.persist(reg);

		return reg.getId();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getRegistrationCount() throws Exception {
		return registrationRm.get();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Registration> listRegistrations() throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		
		System.out.println("Registration transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		return entityManager.createQuery("select r from Registration r")
				.getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getStudentRegistrationCount(int studentId) throws Exception {
		List<Registration> listReg =  
				entityManager.createQuery("select r from Registration r where studentId="+ "'"+ studentId +"'")
				.getResultList();
		return listReg.size();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getCourseRegistrationCount(int courseId) throws Exception {
		List<Registration> listReg =  
				entityManager.createQuery("select r from Registration r where courseId="+ "'"+ courseId +"'")
				.getResultList();
		return listReg.size();
	}
	
	@Override
	public int deleteRegistration(Registration reg) throws NamingException,
			Exception {
		
		System.out.println("Registration transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
	
		registrationRm.incr(-1);
		entityManager.remove(reg);

		return reg.getId();
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Registration getRegistration(int rid) throws NamingException,
			NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		
		System.out.println("Registration transaction is identified as: "
				+ new InitialContext().lookup("java:comp/UserTransaction")
						.toString());
		
		return entityManager.find(Registration.class, rid);

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
}
