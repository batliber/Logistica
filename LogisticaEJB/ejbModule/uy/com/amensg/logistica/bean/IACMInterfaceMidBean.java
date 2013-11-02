package uy.com.amensg.logistica.bean;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.ACMInterfaceMid;

@Remote
public interface IACMInterfaceMidBean {

	public void update(ACMInterfaceMid acmInterfaceMid);
}