package com.workers;

import com.arjuna.ats.arjuna.common.CoreEnvironmentBeanException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.jpa.Course;
import com.jpa.Registration;
import com.jpa.Student;
import com.tm.ejb.CourseManager;
import com.tm.ejb.RegistrationManager;
import com.tm.ejb.StudentManager;
import com.transactional.operation.CourseTransactionalOperation;
import com.transactional.operation.RegisterationTransactionalOperation;
import com.transactional.operation.StudentTransactionalOperation;
import com.transactions.AddNewCourseTransaction;
import com.transactions.AddNewStudentTransaction;
import com.transactions.MakeRegistration;


public class Concurrent_Worker 
{
	
	public void RegisterTransaction() throws IllegalStateException, SecurityException, SystemException
	{
		Student stdResource = new Student();
		stdResource.setId(1);
		stdResource.setName("std1");
		
		Course crsResource = new Course();
		crsResource.setId(1);
		crsResource.setName("course1");
		
                arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier("mainTm");
                TransactionManager manager  = com.arjuna.ats.jta.TransactionManager
                        .transactionManager();
                try
                {
                    manager.begin();
                    ExecutorService executor = Executors.newFixedThreadPool(3);
                    AddNewStudentTransaction  studentWorker = new AddNewStudentTransaction(stdResource);
                    AddNewCourseTransaction courseWorker = new AddNewCourseTransaction(crsResource);
                    MakeRegistration registrtionWorker = new MakeRegistration(stdResource.getId(),crsResource.getId());
                    executor.execute(studentWorker);
                    Thread.sleep(6000);
                    executor.execute(courseWorker);
                    Thread.sleep(6000);
                    executor.execute(registrtionWorker);
                    Thread.sleep(6000);
                    manager.commit();
                    executor.shutdown();
                    // Wait until all threads are finish
                    while (!executor.isTerminated()) {
                    }
                    System.out.println("\nFinished Transaction threads");
                    
                } catch (Exception e) {
                    manager.rollback();
                    e.printStackTrace();
			}
	}
}
