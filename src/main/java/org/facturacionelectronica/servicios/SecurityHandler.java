package org.facturacionelectronica.servicios;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {

	private String usuario;
	private String contrasenia;

	public SecurityHandler() {
	}

	public SecurityHandler(String usuario, String contrasenia) {
		super();
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	@Override
	public boolean handleFault(SOAPMessageContext msgCtx) {
		return true;
	}

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleMessage(SOAPMessageContext msgCtx) {
		// Indicator telling us which direction this message is going in
		final Boolean outInd = (Boolean) msgCtx.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		// Handler must only add security headers to outbound messages
		if (outInd.booleanValue()) {

			try {
				// Get the SOAP Envelope
				final SOAPEnvelope envelope = msgCtx.getMessage().getSOAPPart().getEnvelope();

				// Header may or may not exist yet
				SOAPHeader header = envelope.getHeader();
				if (header == null)
					header = envelope.addHeader();

				// Add WSS Usertoken Element Tree"DOWSA206", "Wong2018"
				final SOAPElement security = header.addChildElement("Security", "wsse",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
//				http://docs.oasisopen.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd
//				http://docs.oasisopen.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd
				final SOAPElement userToken = security.addChildElement("UsernameToken", "wsse");
//				20381847927DOWSA206 -- PASWORD :Wong2018
				userToken.addChildElement("Username", "wsse").addTextNode("20101440355L201014U");
				userToken.addChildElement("Password", "wsse").addTextNode("ortopedi");

			} catch (final Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

}
