package org.oham.learn.ssh.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.oham.learn.ssh.beans.TGroup;
import org.oham.learn.ssh.beans.TPerson;
import org.oham.learn.ssh.services.TestHibernateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value="TestHibernate")
public class TestHibernateServiceImpl implements TestHibernateService {

	@Resource(name="sessionFactory")
	private SessionFactory factory;
	
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public List<TPerson> getTpersonList() {
		Session session = factory.getCurrentSession();
		Criteria criteria = session.createCriteria(TPerson.class);
		List<TPerson> list = criteria.list(); 
		return list;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	public List<TGroup> getTGroupList() {
		Session session = factory.getCurrentSession();
		Criteria criteria = session.createCriteria(TGroup.class);
		List<TGroup> list = criteria.list();
		return list;
	}
}
