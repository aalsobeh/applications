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

import com.RM.CourseRM;
import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.jpa.Course;
import com.tm.ejb.CourseManager;

public class CourseTransactionalOperation {
	
	private CourseManager crsTm;
	static int  i = 0;
	private String nodeIdentifier = "crsTm";
	private CourseRM crsRm = new CourseRM();

	private Xid xid;
	private Course course = new Course();
	List<Course> listCourses = new ArrayList<Course>();
	
	public CourseTransactionalOperation(){		
	}
	
	public void createCourse(Course crsResource) throws IllegalStateException, SecurityException, SystemException, XAException{		
		
		try	{
		nodeIdentifier = nodeIdentifier + (i+1); 
		arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
		crsTm  = (CourseManager) com.arjuna.ats.jta.TransactionManager
					.transactionManager();
		
		getTmInfo("Before Begin", crsTm);
		crsTm.begin();
		xid = TransactionImple.getTransaction().getTxId();
		crsTm.getTransaction().enlistResource(crsResource);
		getTmInfo("After Begin", crsTm);
		
		// TODO: Lock on the resource
		System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
		Thread.sleep(6000);
		// start the distributed transaction on the registration resource
		crsResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
		crsRm.setCourseId(crsResource.getId());
		crsRm.setCourseName(crsResource.getName());
		crsTm.createCourse(crsRm.getCourse());
		System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
		Thread.sleep(6000);
		System.out.println("Get Course Information : Id = "+ crsResource.getId() + " Course Name ="+ crsResource.getName());
		System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
		Thread.sleep(5000);
		// End the distributed transaction on the resource
		crsResource.end(xid, XAResource.TMSUCCESS);
		crsTm.commit();
		getTmInfo("After Commit", crsTm);
		}
		catch (Exception ex){
			crsResource.end(xid, XAResource.TMFAIL);
			crsTm.rollback();
		}
	}
	
	public void deleteCourse(int crsId) throws NamingException, Exception {
		Course crsResource = new Course();
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			crsTm  = (CourseManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", crsTm);
			crsTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			crsTm.getTransaction().enlistResource(crsResource);
			getTmInfo("After Begin", crsTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			crsResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			crsRm.setCourseId(crsId);
			crsResource = crsTm.getCourse(crsId);
			crsTm.deleteCourse(crsResource);
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			System.out.println("Get Course Information : Id = "+ crsResource.getId() + " Course Name ="+ crsResource.getName());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			crsResource.end(xid, XAResource.TMSUCCESS);
			crsTm.commit();
			getTmInfo("After Commit", crsTm);
			}
			catch (Exception ex){
				crsResource.end(xid, XAResource.TMFAIL);
				crsTm.rollback();
		}
	}
	
	public List<Course> listCourses() throws NamingException, XAException, IllegalStateException, SecurityException, SystemException{
		
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			crsTm  = (CourseManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", crsTm);
			crsTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			crsTm.getTransaction().enlistResource(course);
			getTmInfo("After Begin", crsTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			course.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			listCourses = crsTm.listCourses();
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			//System.out.println("Get Course Information : Id = "+ crsResource.getId() + " Course Name ="+ crsResource.getName());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			course.end(xid, XAResource.TMSUCCESS);
			crsTm.commit();
			getTmInfo("After Commit", crsTm);
			}
			catch (Exception ex){
				course.end(xid, XAResource.TMFAIL);
				crsTm.rollback();
				listCourses = null;
		}
		return  listCourses;
	}
	
	public Course getCourse(int crsId) throws XAException, IllegalStateException, SecurityException, SystemException{
		Course crsResource = new Course();
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			crsTm  = (CourseManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", crsTm);
			crsTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			crsTm.getTransaction().enlistResource(crsResource);
			getTmInfo("After Begin", crsTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			crsResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			crsRm.setCourseId(crsId);
			crsResource = crsTm.getCourse(crsId);
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			System.out.println("Get Course Information : Id = "+ crsResource.getId() + " Course Name ="+ crsResource.getName());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			crsResource.end(xid, XAResource.TMSUCCESS);
			crsTm.commit();
			getTmInfo("After Commit", crsTm);
			}
			catch (Exception ex){
				crsResource.end(xid, XAResource.TMFAIL);
				crsTm.rollback();
		}
		return crsResource;
	}
	
	public int getCourseCount() throws Exception {
		int count = 0;
		count = crsTm.getCourseCount();
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
	
	public CourseManager getRegTm() {
		return crsTm;
	}

	public void setRegTm(CourseManager crsTm) {
		this.crsTm = crsTm;
	}

	public String getNodeIdentifier() {
		return nodeIdentifier;
	}

	public void setNodeIdentifier(String nodeIdentifier) {
		this.nodeIdentifier = nodeIdentifier;
	}

	public CourseRM getCrsRm() {
		return crsRm;
	}

	public void setRegRm(CourseRM crsRm) {
		this.crsRm = crsRm;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
