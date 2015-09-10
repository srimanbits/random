package com.ggk.hrms.review.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.EmpSelfNotes;
import com.ggk.hrms.review.dao.EmpSelfNotesDao;

@Repository("notesDao")
public class EmpSelfNotesDaoImpl implements EmpSelfNotesDao {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public EmpSelfNotes getEmpSelfNotes(int empId) {
		Query query= entityManager.createQuery("from EmpSelfNotes notes where notes.employee.empId=:empId");
		query.setParameter("empId", empId);
		try{
			return (EmpSelfNotes) query.getSingleResult();
		}catch(javax.persistence.NoResultException e){
			
			return null;
		}
		
	}

	@Override
	@Transactional
	public void saveEmpSelfNotes(EmpSelfNotes notes) {
		entityManager.merge(notes);
		
	}

}
