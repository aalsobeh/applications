package com.jpa;

import java.io.Serializable;

import javax.persistence.*;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * Entity implementation class for Entity: registration
 *
 */
@Entity
public class Registration implements Serializable, XAResource {

	   
	@Id
	@GeneratedValue
	private int id;
	private int studentId;
	private int courseId;
	private static final long serialVersionUID = 1L;

	public Registration() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public int getStudentId() {
		return this.studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}   
	public int getCourseId() {
		return this.courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public void commit(Xid arg0, boolean arg1) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " + "****  commit");
	}

	public void end(Xid arg0, int arg1) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " +"****  end");
	}

	public void forget(Xid arg0) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " +"****  forget");
	}

	public int getTransactionTimeout() throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " +"****  timeout");
		return 0;
	}

	public boolean isSameRM(XAResource arg0) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " +"****  isSame");
		return false;
	}

	public int prepare(Xid arg0) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " +"****  prepare");
		return 0;
	}

	public Xid[] recover(int arg0) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " +"****  recover");
		return null;
	}

	public void rollback(Xid arg0) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " +"****  rollback");
	}

	public boolean setTransactionTimeout(int arg0) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " + "****  set timeout");
		return false;
	}

	public void start(Xid arg0, int arg1) throws XAException {
		System.out.println("Node " + "RegistrationRm" +": " + "****  start");
	}
   
}
