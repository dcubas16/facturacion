package org.facturacionelectronica.entidades;

public class RespuestaCdr {

	private String descripcion;
	
	private String codigo;
	
	private String ticket;
	
	public RespuestaCdr() {}

	public RespuestaCdr(String descripcion, String codigo, String ticket) {
		super();
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.ticket = ticket;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
