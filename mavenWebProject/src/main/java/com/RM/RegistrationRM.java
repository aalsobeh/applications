package com.RM;

import java.io.IOException;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import com.arjuna.ats.txoj.Lock;
import com.arjuna.ats.txoj.LockManager;
import com.arjuna.ats.txoj.LockMode;
import com.arjuna.ats.txoj.LockResult;
import com.jpa.Registration;

public class RegistrationRM extends LockManager{
	
	private int state= 0;
	
	//@Inject
	//EntityManager entityManager;
	
	@Inject
	private Registration registration;
	
	public RegistrationRM(){
		super();
		registration = new Registration();
	}
	
	public void setRegistrationId(int rid) throws Exception{
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			registration.setId(rid);
		} else {
			throw new Exception("Error - could not set write lock.");
		}		
	}
	
	public int getRegistrationId() throws Exception{
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return registration.getId();
		} else {
			throw new Exception("Error - could not set read lock.");
		}
	}
	
	public void setStudentId(int sid)throws Exception{
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			registration.setStudentId(sid);
		} else {
			throw new Exception("Error - could not set write lock.");
		}
	}
	
	public int getStudentId() throws Exception{
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return registration.getStudentId();
		} else {
			throw new Exception("Error - could not set read lock.");
		}
	}
	
	public void setCourseId(int cid)throws Exception{
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			registration.setCourseId(cid);
		} else {
			throw new Exception("Error - could not set write lock.");
		}
	}
	
	public int getCourseId() throws Exception{
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return registration.getCourseId();
		} else {
			throw new Exception("Error - could not set read lock.");
		}
	}
	
	/*
	public void addRegistration(Registration r)throws Exception{
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			entityManager.persist(r);
		} else {
			throw new Exception("Error - could not set write lock.");
		}
	}
	
	public void deleteRegistration(Registration r) throws Exception{
			Lock lock = new Lock(LockMode.WRITE);
			getLockInfo(lock, "Before Set Lock");
			if (setlock(lock, 0) == LockResult.GRANTED) {
				getLockInfo(lock, "After Set Lock");
				entityManager.remove(r);
			} else {
				throw new Exception("Error - could not set write lock.");
			}
	}
	*/
	
	public Registration getRegistration() throws Exception {
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return registration;
		} else {
			throw new Exception("Error - could not set read lock.");
		}
		
	}

	public void setRegistration(Registration Registration) throws Exception{
			Lock lock = new Lock(LockMode.WRITE);
			getLockInfo(lock, "Before Set Lock");
			if (setlock(lock, 0) == LockResult.GRANTED) {
				getLockInfo(lock, "After Set Lock");
				this.registration = Registration;
			} else {
				throw new Exception("Error - could not set write lock.");
			}
	}
	
	
	public void incr(int value) throws Exception {
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			state += value;
		} else {
			throw new Exception("Error - could not set write lock.");
		}
	}
	
	public int get() throws Exception {
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return state;
		} else {
			throw new Exception("Error - could not set read lock.");
		}
	}
	
	/**
	 * TXOJ classes must override save_state in order to save their state.
	 */
	public boolean save_state(OutputObjectState os, int ot) {
		boolean result = super.save_state(os, ot);

		if (!result)
			return false;

		try {
			os.packInt(state);
		} catch (IOException e) {
			result = false;
		}

		return result;
	}

	/**
	 * TXOJ classes must override restore_state in order to have their state
	 * recovered by transactions.
	 */
	public boolean restore_state(InputObjectState os, int ot) {
		boolean result = super.restore_state(os, ot);

		if (!result)
			return false;

		try {
			state = os.unpackInt();
		} catch (IOException e) {
			result = false;
		}

		return result;
	}

	/**
	 * TXOJ classes must override the type method to partition them from other
	 * classes of <code>LockManager</code>.
	 */
	public String type() {
		return super.type() + this.getClass().getName();
	}
	
	private void getLockInfo(Lock lock, String location)
	{
		System.out.println("_________________________ "+ location +" ____________________________________\n");
		System.out.println("Lock Manager id : " + lock.get_uid());
		System.out.println("Lock Current Owner : " + lock.getCurrentOwner());
		System.out.println("Lock Mode" + lock.getLockMode());
		System.out.println("Lock Status : " + lock.getCurrentStatus());
		System.out.println("Lock Store Root  : " + lock.getStoreRoot());
		System.out.println("Lock Store  : " + lock.getStore());
		System.out.println("Depth of Owners Hirerachy: " + lock.getAllOwners().depth());
		System.out.println("______________________________________________________________________________\n");
	}
}
