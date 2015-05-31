package com.RM;

import java.io.IOException;
import java.rmi.ConnectIOException;
import java.sql.Connection;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.arjuna.ats.arjuna.state.InputObjectState;
import com.arjuna.ats.arjuna.state.OutputObjectState;
import com.arjuna.ats.txoj.Lock;
import com.arjuna.ats.txoj.LockManager;
import com.arjuna.ats.txoj.LockMode;
import com.arjuna.ats.txoj.LockResult;
import com.jpa.Student;

public class StudentRM extends LockManager{

	private int state= 0;
	
	//@Inject
	//EntityManager entityManager;
	
	@Inject
	private Student student;
	
	public StudentRM(){
		super();
	}
	
	public void setStudenId(int sid) throws Exception{
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			student.setId(sid);
		} else {
			throw new Exception("Error - could not set write lock.");
		}
		
	}
	
	public int getStudentId() throws Exception{
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return student.getId();
		} else {
			throw new Exception("Error - could not set read lock.");
		}
	}
	
	public void setStudentName(String sname)throws Exception{
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			student.setName(sname);
		} else {
			throw new Exception("Error - could not set write lock.");
		}
	}
	
	public String getStudentName() throws Exception{
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return student.getName();
		} else {
			throw new Exception("Error - could not set read lock.");
		}
	}
	
	/*
	public void addStudent(Student s)throws Exception{
		Lock lock = new Lock(LockMode.WRITE);
		getLockInfo(lock, "Before Set Lock");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After Set Lock");
			entityManager.persist(s);
		} else {
			throw new Exception("Error - could not set write lock.");
		}
	}
	
	public void deleteStudent(Student s) throws Exception{
			Lock lock = new Lock(LockMode.WRITE);
			getLockInfo(lock, "Before Set Lock");
			if (setlock(lock, 0) == LockResult.GRANTED) {
				getLockInfo(lock, "After Set Lock");
				entityManager.remove(s);
			} else {
				throw new Exception("Error - could not set write lock.");
			}
	}
	*/
	
	public Student getStudent() throws Exception {
		Lock lock = new Lock(LockMode.READ);
		getLockInfo(lock, "before Get");
		if (setlock(lock, 0) == LockResult.GRANTED) {
			getLockInfo(lock, "After set Lock");
			return student;
		} else {
			throw new Exception("Error - could not set read lock.");
		}
		
	}

	public void setStudent(Student student) throws Exception{
			Lock lock = new Lock(LockMode.WRITE);
			getLockInfo(lock, "Before Set Lock");
			if (setlock(lock, 0) == LockResult.GRANTED) {
				getLockInfo(lock, "After Set Lock");
				this.student = student;
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
