package com.transactions;

import javax.transaction.SystemException;
import javax.transaction.xa.XAException;

import com.jpa.Student;
import com.transactional.operation.StudentTransactionalOperation;

public class AddNewStudentTransaction implements Runnable{

	private Student student;
	private StudentTransactionalOperation stdOps = new StudentTransactionalOperation();
	
	public AddNewStudentTransaction(int stdId, String stdName){
		student = new Student();
		student.setId(stdId);
		student.setName(stdName);	
	}
	
	public AddNewStudentTransaction(Student std){
		this.student = std;
	}

	@Override
	public void run() {
		 try
		 {
			stdOps.createStudent(student);
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