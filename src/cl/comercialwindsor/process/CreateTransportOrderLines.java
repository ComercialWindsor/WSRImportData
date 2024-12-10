package cl.comercialwindsor.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import cl.comercialwindsor.model.X_M_Transporte;
import cl.comercialwindsor.model.X_M_TransporteLine;

public class CreateTransportOrderLines extends SvrProcess{
	

	
	int m_C_BPartner_ID;
	String m_TipoEmpresa;
	String m_IsInvoiced;
	String m_IsShip;
	Timestamp m_DateFrom;
	Timestamp m_DateTo;
	int m_M_Transporte_ID;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("C_BPartner_ID"))
				m_C_BPartner_ID = para[i].getParameterAsInt();
			else if(name.equals("DateAcct")){
				m_DateFrom = (Timestamp)para[i].getParameter();
				m_DateTo = (Timestamp)para[i].getParameter_To();
			}
			else if (name.equals("TipoEmpresa"))
				m_TipoEmpresa = (String)para[i].getParameter();
			else if (name.equals("IsInvoiced"))
				m_IsInvoiced = (String)para[i].getParameter();
			else if (name.equals("IsShip"))
				m_IsShip = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_M_Transporte_ID = getRecord_ID();
		
		
	}

	@Override
	protected String doIt() throws Exception {

		if(m_M_Transporte_ID<=0)
			return "Orden de Transporte no Valida";
		
		X_M_Transporte parent = new X_M_Transporte(getCtx(), m_M_Transporte_ID, get_TrxName());

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o.C_Order_ID, inv.C_Invoice_ID, ino.M_InOut_ID ")
		.append(" FROM C_Order o")
		.append(" JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID")
		.append(" LEFT JOIN C_Invoice inv ON o.c_order_id = inv.c_order_id AND inv.docstatus = 'CO'")
		.append(" LEFT JOIN M_Inout ino ON (o.c_order_id = ino.c_order_id or o.m_inout_id=ino.m_inout_id) AND ino.docstatus = 'CO'")
		.append(" LEFT JOIN M_TransporteLine trpl on o.c_order_id=trpl.c_order_id")
		.append(" LEFT JOIN M_Transporte trp on trpl.m_transporte_id = trp.m_transporte_id and trp.docstatus in ('DR','CO','IP')")
		.append(" WHERE o.AD_Client_ID="+getAD_Client_ID())
		.append(" AND trp.m_transporte_id IS NULL")
		.append(" AND o.DateAcct BETWEEN ? AND ? ")
		;

		if(m_C_BPartner_ID>0)
			sql.append(" AND o.C_BPartner_ID ="+m_C_BPartner_ID);
		
		if(m_TipoEmpresa!=null){
			if(!m_TipoEmpresa.equals(""))
				sql.append(" AND bp.TipoEmpresa ='"+m_TipoEmpresa+"'");
		}
		
		if(m_IsInvoiced!=null){
			if(m_IsInvoiced.equals("Y"))
				sql.append(" AND inv.C_Invoice_ID IS NOT NULL");
			else if(m_IsInvoiced.equals("N"))
				sql.append(" AND inv.C_Invoice_ID IS NULL");
		}
			
		if(m_IsShip!=null){
			if(m_IsShip.equals("Y"))
				sql.append(" AND ino.M_Inout_ID IS NOT NULL");
			else if(m_IsShip.equals("N"))
				sql.append(" AND ino.M_Inout_ID IS NULL");
		}

		sql.append(" ORDER BY o.DateAcct");
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int lineNo = 10;
		int created = 0;
		
		try{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			pstmt.setTimestamp(1, m_DateFrom);
			pstmt.setTimestamp(2, m_DateTo);
			rs = pstmt.executeQuery();
			
			while (rs.next ()){
				int C_Order_ID = rs.getInt("C_Order_ID");
				int C_Invoice_ID = rs.getInt("C_Invoice_ID");
				int M_InOut_ID = rs.getInt("M_InOut_ID");
				X_M_TransporteLine line = new X_M_TransporteLine(getCtx(), 0, get_TrxName());
				line.setAD_Org_ID(parent.getAD_Org_ID());
				line.setM_Transporte_ID(m_M_Transporte_ID);
				line.setC_Order_ID(C_Order_ID);
				line.setC_Invoice_ID(C_Invoice_ID);
				line.setM_InOut_ID(M_InOut_ID);
				line.setCantidadBulto(BigDecimal.ZERO);
				line.setVALORVIAJE(0);
				line.setLine(lineNo);
				line.setProcessed(false);
				line.setCedible(false);
				line.setCOBERTURA(false);
				line.setFABRICS(false);
				line.saveEx(get_TrxName());
				lineNo += 10;	
				created++;
			}
		}catch (Exception e){
			log.log(Level.SEVERE, "Transport Order - " + sql.toString(), e);
			new AdempiereException(e);
		}finally{
			DB.close(rs, pstmt);
			rs=null;
			pstmt=null;
		}
		
		
		return "@Created@:"+created;
	}

}
