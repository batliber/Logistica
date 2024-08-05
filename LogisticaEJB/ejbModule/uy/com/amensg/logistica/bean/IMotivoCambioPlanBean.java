package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.MotivoCambioPlan;

@Remote
public interface IMotivoCambioPlanBean {

	public Collection<MotivoCambioPlan> list();
}