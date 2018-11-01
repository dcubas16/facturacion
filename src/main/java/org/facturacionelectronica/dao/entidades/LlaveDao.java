package org.facturacionelectronica.dao.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "LLAVE", uniqueConstraints = {
        @UniqueConstraint(columnNames = "RUC")})
public class LlaveDao {

	
	@Id
	@Column(name = "RUC", unique = true, nullable=false)
	private String ruc;
	
	@Column(name = "ALIAS", nullable=false)
	private String alias;
	
	@Column(name = "PIN", nullable=false)
	private String pin;
	
	public LlaveDao() {}
	
	public LlaveDao(String ruc, String alias, String pin) {
		super();
		this.ruc = ruc;
		this.alias = alias;
		this.pin = pin;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
}
