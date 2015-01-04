package org.oham.learn.ssh.services;

import java.util.List;

import org.oham.learn.ssh.beans.TGroup;
import org.oham.learn.ssh.beans.TPerson;

public interface TestHibernateService {

	public List<TPerson> getTpersonList();
	
	public List<TGroup> getTGroupList();
	
}