package cl.comercialwindsor.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class UpdateBPValueAndDigito extends SvrProcess {

	int m_C_BPartner_ID;
	String m_Value = null;
	String m_Digito  = null;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("Value"))
				m_Value =(String)para[i].getParameter();
			else if (name.equals("DIGITO"))
				m_Digito = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_C_BPartner_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {

		if((m_Value==null || m_Value.isEmpty()) && (m_Digito==null || m_Digito.isEmpty()))
			return "OK";
		
		validateNewRut();
		
		Object[] params = null;
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE C_BPartner SET updated=SYSDATE, updatedBy=").append(getAD_User_ID());		 
		
		if(m_Value !=null && !m_Value.isEmpty()){
			sql.append(", Value=?");
			params = new Object[]{m_Value};
		}				
		if(m_Digito!=null && !m_Digito.isEmpty()){
			sql.append(", Digito=?");
			params = (params == null ? new Object[]{m_Digito} : new Object[]{m_Value, m_Digito});
		}		
		sql.append(" WHERE C_BPartner_ID=").append(m_C_BPartner_ID);		
		
		DB.executeUpdateEx(sql.toString(), params, get_TrxName());
		
		return "OK";
	}

	private void validateNewRut() {
		StringBuilder sql = new StringBuilder();
		MBPartner partner = new MBPartner(getCtx(), m_C_BPartner_ID, get_TrxName());
		sql.append("SELECT C_BPartner_ID FROM C_BPartner WHERE COALESCE(?,?)= Value AND COALESCE(?,?)=Digito AND C_BPartner_ID<>").append(m_C_BPartner_ID);	
		int C_BPartner_ID = DB.getSQLValueEx(get_TrxName(), sql.toString()
				,new Object[]{m_Value,partner.getValue(), m_Digito, partner.get_ValueAsString("Digito")}
				);
		if(C_BPartner_ID>0)
			throw new AdempiereException("Ya existe un socio con el rut ingresado");
	}

}
