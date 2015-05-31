package com.jpa;

import java.io.Serializable;

import javax.persistence.*;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * Entity implementation class for Entity: Course
 *
 */
@Entity
public class Course  implements Serializable, XAResource {
	
	@Id
	private int courseId;

	@Column(unique = true, nullable = false)
	private String courseName;

	public Course(){
		super();
	}
	
	public int getId() {
		return courseId;
	}

	public void setId(int id) {
		this.courseId = id;
	}

	public String getName() {
		return courseName;
	}

	public void setName(String name) {
		this.courseName = name;
	}

	public void commit(Xid arg0, boolean arg1) throws XAException {
		System.out.println("Node " + "courseRm" +": " + "****  commit");
	}

	public void end(Xid arg0, int arg1) throws XAException {
		System.out.println("Node " + "courseRm" +": " +"****  end");
	}

	public void forget(Xid arg0) throws XAException {
		System.out.println("Node " + "courseRm" +": " +"****  forget");
	}

	public int getTransactionTimeout() throws XAException {
		System.out.println("Node " + "courseRm" +": " +"****  timeout");
		return 0;
	}

	public boolean isSameRM(XAResource arg0) throws XAException {
		System.out.println("Node " + "courseRm" +": " +"****  isSame");
		return false;
	}

	public int prepare(Xid arg0) throws XAException {
		System.out.println("Node " + "courseRm" +": " +"****  prepare");
		return 0;
	}

	public Xid[] recover(int arg0) throws XAException {
		System.out.println("Node " + "courseRm" +": " +"****  recover");
		return null;
	}

	public void rollback(Xid arg0) throws XAException {
		System.out.println("Node " + "courseRm" +": " +"****  rollback");
	}

	public boolean setTransactionTimeout(int arg0) throws XAException {
		System.out.println("Node " + "courseRm" +": " + "****  set timeout");
		return false;
	}

	public void start(Xid arg0, int arg1) throws XAException {
		System.out.println("Node " + "courseRm" +": " + "****  start");
	}
}
