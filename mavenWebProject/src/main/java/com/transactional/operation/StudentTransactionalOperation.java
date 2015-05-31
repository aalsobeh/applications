package com.transactional.operation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import com.RM.StudentRM;
import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.jpa.Course;
import com.jpa.Student;
import com.tm.ejb.CourseManager;
import com.tm.ejb.StudentManager;

public class StudentTransactionalOperation {
	
	private StudentManager stdTm;
	static int i=0;
	private String nodeIdentifier = "stdTm1";
	private StudentRM stdRm = new StudentRM();

	private Xid xid;
	private Student student = new Student();
	private List<Student> listStudents = new ArrayList<Student>();
	
	
	public StudentTransactionalOperation(){		
	}
	
	public void createStudent(Student stdResource) throws IllegalStateException, SecurityException, SystemException, XAException{		
		
		try	{
		nodeIdentifier = nodeIdentifier + (i+1);
		arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
		stdTm  = (StudentManager) com.arjuna.ats.jta.TransactionManager
					.transactionManager();
		
		getTmInfo("Before Begin", stdTm);
		stdTm.begin();
		xid = TransactionImple.getTransaction().getTxId();
		stdTm.getTransaction().enlistResource(stdResource);
		getTmInfo("After Begin", stdTm);
		
		// TODO: Lock on the resource
		System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
		Thread.sleep(6000);
		// start the distributed transaction on the registration resource
		stdResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
		stdRm.setStudenId(stdResource.getId());
		stdRm.setStudentName(stdResource.getName());
		stdTm.createStudent(stdRm.getStudent());
		System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
		Thread.sleep(6000);
		System.out.println("Get Student Information : Id = "+ stdResource.getId() + " Student Id ="+ stdResource.getName());
		System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
		Thread.sleep(5000);
		// End the distributed transaction on the resource
		stdResource.end(xid, XAResource.TMSUCCESS);
		stdTm.commit();
		getTmInfo("After Commit", stdTm);
		}
		catch (Exception ex){
			stdResource.end(xid, XAResource.TMFAIL);
			stdTm.rollback();
		}
	}
	
	public void deleteStudent(int stdId) throws NamingException, Exception {
		Student stdResource = new Student();
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			stdTm  = (StudentManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", stdTm);
			stdTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			stdTm.getTransaction().enlistResource(stdResource);
			getTmInfo("After Begin", stdTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			stdResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			stdRm.setStudenId(stdId);
			stdResource = stdTm.getStudent(stdId);
			stdTm.deleteStudent(stdResource);
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			System.out.println("Get Student Information : Id = "+ stdResource.getId() + " Student Name ="+ stdResource.getName());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			stdResource.end(xid, XAResource.TMSUCCESS);
			stdTm.commit();
			getTmInfo("After Commit", stdTm);
			}
			catch (Exception ex){
				stdResource.end(xid, XAResource.TMFAIL);
				stdTm.rollback();
		}
	}
	
	public List<Student> listStudents() throws NamingException, XAException, IllegalStateException, SecurityException, SystemException{
		
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			stdTm  = (StudentManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", stdTm);
			stdTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			stdTm.getTransaction().enlistResource(student);
			getTmInfo("After Begin", stdTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			student.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			listStudents = stdTm.listStudents();
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			//System.out.println("Get Student Information : Id = "+ crsResource.getId() + " Course Name ="+ crsResource.getName());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			student.end(xid, XAResource.TMSUCCESS);
			stdTm.commit();
			getTmInfo("After Commit", stdTm);
			}
			catch (Exception ex){
				student.end(xid, XAResource.TMFAIL);
				stdTm.rollback();
				listStudents = null;
		}
		return  listStudents;
	}

	public Student getStudent(int stdId) throws XAException, IllegalStateException, SecurityException, SystemException{
		Student stdResource = new Student();
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			stdTm  = (StudentManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", stdTm);
			stdTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			stdTm.getTransaction().enlistResource(stdResource);
			getTmInfo("After Begin", stdTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			stdResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			stdRm.setStudenId(stdId);
			stdResource = stdTm.getStudent(stdId);
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			System.out.println("Get Student Information : Id = "+ stdResource.getId() + " Student Name ="+ stdResource.getName());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			stdResource.end(xid, XAResource.TMSUCCESS);
			stdTm.commit();
			getTmInfo("After Commit", stdTm);
			}
			catch (Exception ex){
				stdResource.end(xid, XAResource.TMFAIL);
				stdTm.rollback();
				stdResource =null;
		}
		return stdResource;
	}
	
	public int getStudentCount() throws Exception {
		int count = 0;
		count = stdTm.getStudentCount();
		return count;
	}
		
	private void getTmInfo(String comment ,TransactionManager _manager) throws SystemException {
		try
		{
			System.out.println(comment+"-->Node =" + nodeIdentifier + ": " + "TM = " + _manager.toString());
			if(_manager != null)
			{
				System.out.println("\t\t"+ comment+"-->Node =" + nodeIdentifier + ": " + "Transaction = " + _manager.getTransaction());
				System.out.println("\t\t"+comment+"-->Node =" + nodeIdentifier + ": " + "Tm.Status = " + _manager.getStatus());
				
				if(_manager.getTransaction() !=null && _manager !=null)
				{
					System.out.println("\t\t\t"+comment+"-->Node =" + nodeIdentifier + ": " + "Transaction Manager : " + _manager.toString());
					try {
						System.out.println("\t\t\t"+ comment+"-->Node =" + nodeIdentifier + ": " + "TM Status       : " + _manager.getStatus());
						System.out.println("\t\t\t"+ comment+"-->Node =" + nodeIdentifier + ": " + "TM.Transaction     : " + _manager.getTransaction());
						System.out.println("\t\t\t"+ comment+"-->Node =" + nodeIdentifier + ": " +"TM.TransactionStatus : " + _manager.getTransaction().getStatus());
						
						if(TransactionImple.getTransaction() != null)
						{
							System.out.println("\t\t\t\t"+ comment+"-->Node =" + nodeIdentifier + ": " + "TransactionImple : " + TransactionImple.getTransaction());
							System.out.println("\t\t\t\t"+ comment+"-->Node =" + nodeIdentifier + ": " + "TransactionImple Id : " + TransactionImple.getTransaction().getTxId());
							System.out.println("\t\t\t\t"+ comment+"-->Node =" + nodeIdentifier + ": " + "TransactionImple Uid : " + TransactionImple.getTransaction().get_uid());
							System.out.println("\t\t\t\t"+comment+"-->Node =" + nodeIdentifier + ": " + "TransactionImple Status: " + TransactionImple.getTransaction().getStatus());
							System.out.println("\t\t\t\t"+comment+"-->Node =" + nodeIdentifier + ": " + "# of Enlisted Resources : " + TransactionImple.getTransaction().getResources().size());
							System.out.println("\t\t\t\t"+comment+"-->Node =" + nodeIdentifier + ": " + "Transaction Remaining Timeout : " + (TransactionImple.getTransaction().getRemainingTimeoutMills())/1000 + " sec");	
						}
						
						if(TransactionImple.getTransaction().getResources() !=null)
						{
							Map resources = TransactionImple.getTransaction().getResources();
							Set entrys = resources.entrySet() ;
					        Iterator iter = entrys.iterator() ;
					        while(iter.hasNext()) 
					        {
					            Map.Entry me = (Map.Entry)iter.next();
					            System.out.println("\t\t\t"+comment+"-->Node =" + nodeIdentifier + ": " + "Resource : "+ me.getKey() + " = " + me.getValue()+ " Status : " + TransactionImple.getTransaction().getXAResourceState((XAResource) me.getKey()));
					            System.out.println("\t\t\t"+comment+"-->Node =" + nodeIdentifier + ": " + "Local Resource for Transaction : "+ TransactionImple.getTransaction().getTxLocalResource(me.getKey()));
					        }
						}				
					} catch (SystemException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}catch(SystemException ex){
			System.out.println(ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}
	
	public StudentManager getRegTm() {
		return stdTm;
	}

	public void setRegTm(StudentManager stdTm) {
		this.stdTm = stdTm;
	}

	public String getNodeIdentifier() {
		return nodeIdentifier;
	}

	public void setNodeIdentifier(String nodeIdentifier) {
		this.nodeIdentifier = nodeIdentifier;
	}

	public StudentRM getStdRm() {
		return stdRm;
	}

	public void setRegRm(StudentRM stdRm) {
		this.stdRm = stdRm;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}
}
