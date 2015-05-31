package com.transactions;

import javax.transaction.SystemException;
import javax.transaction.xa.XAException;

import com.jpa.Registration;
import com.transactional.operation.RegisterationTransactionalOperation;

public class MakeRegistration implements Runnable {

	private Registration registration;
	private RegisterationTransactionalOperation regOps = new RegisterationTransactionalOperation();
	
	public MakeRegistration(int stdId, int courseId){
		registration = new Registration();
		registration.setStudentId(stdId);
		registration.setCourseId(courseId);	
	}
	
	public MakeRegistration(Registration reg){
		this.registration = reg;
	}

	@Override
	public void run() {
		 try
		 {
			 regOps.CreateRegisteration(registration);
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
