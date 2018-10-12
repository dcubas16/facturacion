package org.facturacionelectronica.firma;

import java.util.List;

public interface TxxxyDAO {
	public List<TxxxyBean> consultarParametroById(TxxxyBean txxxyBean);
	public List<TxxxyBean> consultarParametro(TxxxyBean txxxyBean);
	public void insertarParametro(TxxxyBean txxxyBean);
	public void actualizarParametro(TxxxyBean txxxyBean);
}
