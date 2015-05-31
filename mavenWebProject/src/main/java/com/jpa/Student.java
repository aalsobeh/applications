package com.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

@Entity
public class Student implements Serializable, XAResource{
	
	@Id
	private int id;

	@Column(unique = true, nullable = false)
	private String name;

	public Student(){
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void commit(Xid arg0, boolean arg1) throws XAException {
		System.out.println("Node " + "studentRm" +": " + "****  commit");
	}

	public void end(Xid arg0, int arg1) throws XAException {
		System.out.println("Node " + "studentRm" +": " +"****  end");
	}

	public void forget(Xid arg0) throws XAException {
		System.out.println("Node " + "studentRm" +": " +"****  forget");
	}

	public int getTransactionTimeout() throws XAException {
		System.out.println("Node " + "studentRm" +": " +"****  timeout");
		return 0;
	}

	public boolean isSameRM(XAResource arg0) throws XAException {
		System.out.println("Node " + "studentRm" +": " +"****  isSame");
		return false;
	}

	public int prepare(Xid arg0) throws XAException {
		System.out.println("Node " + "studentRm" +": " +"****  prepare");
		return 0;
	}

	public Xid[] recover(int arg0) throws XAException {
		System.out.println("Node " + "studentRm" +": " +"****  recover");
		return null;
	}

	public void rollback(Xid arg0) throws XAException {
		System.out.println("Node " + "studentRm" +": " +"****  rollback");
	}

	public boolean setTransactionTimeout(int arg0) throws XAException {
		System.out.println("Node " + "studentRm" +": " + "****  set timeout");
		return false;
	}

	public void start(Xid arg0, int arg1) throws XAException {
		System.out.println("Node " + "studentRm" +": " + "****  start");
	}
}
