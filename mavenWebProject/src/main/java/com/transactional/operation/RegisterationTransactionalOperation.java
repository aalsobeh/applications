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

import com.RM.RegistrationRM;
import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.arjuna.ats.internal.jta.transaction.arjunacore.BaseTransaction;
import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionImple;
import com.jpa.Registration;
import com.jpa.Student;
import com.tm.ejb.RegistrationManager;
import com.tm.ejb.StudentManager;

public class RegisterationTransactionalOperation
{
	private RegistrationManager regTm;
	static int i = 0;
	private String nodeIdentifier = "regTm1";
	private RegistrationRM regRm = new RegistrationRM();

	private Xid xid;
	private Registration registration = new Registration();
	private List<Registration> listRegistration = new ArrayList<Registration>();
	
	
	public RegisterationTransactionalOperation(){		
	}
	
	public void CreateRegisteration(Registration regResource) throws IllegalStateException, SecurityException, SystemException, XAException{		
		
		try	{
			nodeIdentifier = nodeIdentifier + (i+1);
		arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
		regTm  = (RegistrationManager) com.arjuna.ats.jta.TransactionManager
					.transactionManager();
		
		getTmInfo("Before Begin", regTm);
		regTm.begin();
		xid = TransactionImple.getTransaction().getTxId();
		regTm.getTransaction().enlistResource(regResource);
		getTmInfo("After Begin", regTm);
		
		// TODO: Lock on the resource
		System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
		Thread.sleep(6000);
		// start the distributed transaction on the registration resource
		regResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
		regRm.setStudentId(regResource.getStudentId());
		regRm.setCourseId(regResource.getCourseId());
		regTm.createRegistration(regResource);
		System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
		Thread.sleep(6000);
		System.out.println("Get Registration Information : Id = "+ regResource.getId() + " Student Id ="+ regResource.getStudentId() + "Course Id ="+ regResource.getCourseId());
		System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
		Thread.sleep(5000);
		// End the distributed transaction on the resource
		regResource.end(xid, XAResource.TMSUCCESS);
		regTm.commit();
		getTmInfo("After Commit", regTm);
		}
		catch (Exception ex){
			regResource.end(xid, XAResource.TMFAIL);
			regTm.rollback();
		}
	}
	
	public void deleteRegistration(int regId) throws NamingException, Exception {
		Registration regResource = new Registration();
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			regTm  = (RegistrationManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", regTm);
			regTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			regTm.getTransaction().enlistResource(regResource);
			getTmInfo("After Begin", regTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			regResource.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			regRm.setRegistrationId(regId);
			regResource = regTm.getRegistration(regId);
			regTm.deleteRegistration(regResource);
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			System.out.println("Get Registration Information : Id = "+ regResource.getId() + " Student Id ="+ regResource.getStudentId());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			regResource.end(xid, XAResource.TMSUCCESS);
			regTm.commit();
			getTmInfo("After Commit", regTm);
			}
			catch (Exception ex){
				regResource.end(xid, XAResource.TMFAIL);
				regTm.rollback();
				
		}
	}
	
	public List<Registration> listRegistrations() throws NamingException, XAException, IllegalStateException, SecurityException, SystemException{
		
		try {
			nodeIdentifier = nodeIdentifier + (i+1); 
			arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(nodeIdentifier);
			regTm  = (RegistrationManager) com.arjuna.ats.jta.TransactionManager
						.transactionManager();
			
			getTmInfo("Before Begin", regTm);
			regTm.begin();
			xid = TransactionImple.getTransaction().getTxId();
			regTm.getTransaction().enlistResource(registration);
			getTmInfo("After Begin", regTm);
			
			// TODO: Lock on the resource
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 4 seconds");
			Thread.sleep(6000);
			// start the distributed transaction on the registration resource
			registration.start(xid, javax.transaction.xa.XAResource.TMNOFLAGS);
			listRegistration = regTm.listRegistrations();
			System.out.println("+++Node " + nodeIdentifier+ ": " + "try to get the lock - Timeout 6 seconds");
			Thread.sleep(6000);
			//System.out.println("Get Student Information : Id = "+ crsResource.getId() + " Course Name ="+ crsResource.getName());
			System.out.println("+++Node " + nodeIdentifier+ ": " + "Current Thread will go to sleep for 5 seconds");
			Thread.sleep(5000);
			// End the distributed transaction on the resource
			registration.end(xid, XAResource.TMSUCCESS);
			regTm.commit();
			getTmInfo("After Commit", regTm);
			}
			catch (Exception ex){
				registration.end(xid, XAResource.TMFAIL);
				regTm.rollback();
				listRegistration = null;
		}
		return  listRegistration;
	}

	
	public Registration getRegistration(int id){
		
		Registration reg = new Registration();
		try {
			reg= regTm.getRegistration(id);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reg;
	}
	
	public int getRegistrationCount() throws Exception {
		int count = 0;
		count = regTm.getRegistrationCount();
		return count;
	}
	
	public int getStudentRegistrationCount(int studentId) throws Exception {
		int count = 0;
		count = regTm.getStudentRegistrationCount(studentId);
		return count;
	}
	
	public int getCourseRegistrationCount(int courseId) throws Exception {
		int count = 0;
		count = regTm.getCourseRegistrationCount(courseId);
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
	
	public RegistrationManager getRegTm() {
		return regTm;
	}

	public void setRegTm(RegistrationManager regTm) {
		this.regTm = regTm;
	}

	public String getNodeIdentifier() {
		return nodeIdentifier;
	}

	public void setNodeIdentifier(String nodeIdentifier) {
		this.nodeIdentifier = nodeIdentifier;
	}

	public RegistrationRM getRegRm() {
		return regRm;
	}

	public void setRegRm(RegistrationRM regRm) {
		this.regRm = regRm;
	}

	public Xid getXid() {
		return xid;
	}

	public void setXid(Xid xid) {
		this.xid = xid;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public List<Registration> getListRegistration() {
		return listRegistration;
	}

	public void setListRegistration(List<Registration> listRegistration) {
		this.listRegistration = listRegistration;
	}

}
