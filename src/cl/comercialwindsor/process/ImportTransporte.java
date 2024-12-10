package cl.comercialwindsor.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.compiere.model.X_I_Order;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import cl.comercialwindsor.model.X_I_Transporte;
import cl.comercialwindsor.model.X_M_Transporte;
import cl.comercialwindsor.model.X_M_TransporteLine;

public class ImportTransporte extends SvrProcess{

	private int m_AD_Client_ID;
	
	private int m_AD_Org_ID;
	
	private  boolean m_deleteOldImported;
	
	private String m_docAction;
	
	
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("AD_Client_ID"))
				m_AD_Client_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("AD_Org_ID"))
				m_AD_Org_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("DeleteOldImported"))
				m_deleteOldImported = "Y".equals(para[i].getParameter());
			else if (name.equals("DocAction"))
				m_docAction = (String)para[i].getParameter();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		StringBuffer sql = null;
		int no = 0;
		String clientCheck = " AND AD_Client_ID=" + m_AD_Client_ID;

		//	****	Prepare	****

		//	Delete Old Imported
		if (m_deleteOldImported)
		{
			sql = new StringBuffer ("DELETE I_Order "
				  + "WHERE I_IsImported='Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.fine("Delete Old Impored =" + no);
		}
		
		//	Set Client, Org, IsActive, Created/Updated
			sql = new StringBuffer ("UPDATE I_Transporte "
				  + "SET AD_Client_ID = COALESCE (AD_Client_ID,").append (m_AD_Client_ID).append ("),"
				  + " AD_Org_ID = COALESCE (AD_Org_ID,").append (m_AD_Org_ID).append ("),"
				  + " IsActive = COALESCE (IsActive, 'Y'),"
				  + " Created = COALESCE (Created, SysDate),"
				  + " CreatedBy = COALESCE (CreatedBy, 0),"
				  + " Updated = COALESCE (Updated, SysDate),"
				  + " UpdatedBy = COALESCE (UpdatedBy, 0),"
				  + " I_ErrorMsg = ' ',"
				  + " I_IsImported = 'N' "
				  + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.info ("Reset=" + no);
			
			// C_BPartner 
//			BP from Value
			sql = new StringBuffer ("UPDATE I_Transporte o "
				  + "SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
				  + " WHERE o.BPartner_Value=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID) "
				  + "WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL"
				  + " AND I_IsImported<>'Y'").append (clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.fine("Set BP from Value=" + no);
			
			// BP From name
			sql = new StringBuffer ("UPDATE I_Transporte o "
					  + "SET C_BPartner_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
					  + " WHERE o.BPartner_Name=bp.Name AND o.AD_Client_ID=bp.AD_Client_ID) "
					  + "WHERE C_BPartner_ID IS NULL AND BPartner_Name IS NOT NULL"
					  + " AND I_IsImported<>'Y'").append (clientCheck);
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.fine("Set BP from Name=" + no);
			
			sql = new StringBuffer ("UPDATE I_Transporte "
					  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Bussines Partner, ' "
					  + "WHERE C_BPartner_ID IS NULL"
					  + " AND I_IsImported<>'Y'").append (clientCheck);
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				if (no != 0)
				log.warning ("Invalid Bussines Partner=" + no);
			
			// M_Conductor
				
			sql = new StringBuffer ("UPDATE I_Transporte o "
					  + "SET M_Conductor_ID=(SELECT MAX(M_Conductor_ID) FROM M_Conductor i"
					  + " WHERE o.Conductor=i.Name AND o.AD_Client_ID=i.AD_Client_ID) "
					  + "WHERE M_Conductor_ID IS NULL AND Conductor IS NOT NULL"
					  + " AND I_IsImported<>'Y'").append (clientCheck);
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.fine("Set Conductor=" + no);

			sql = new StringBuffer ("UPDATE I_Transporte "
					  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Conductor, ' "
					  + "WHERE M_Conductor_ID IS NULL"
					  + " AND I_IsImported<>'Y'").append (clientCheck);
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				if (no != 0)
				log.warning ("Invalid Conductor=" + no);
			
			// M_Camion

				sql = new StringBuffer ("UPDATE I_Transporte o "
						  + "SET M_Camion_ID=(SELECT MAX(M_Camion_ID) FROM M_Camion i"
						  + " WHERE o.CamionName=i.Name AND o.AD_Client_ID=i.AD_Client_ID) "
						  + "WHERE M_Camion_ID IS NULL AND CamionName IS NOT NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					log.fine("Set Conductor=" + no);
				
				sql = new StringBuffer ("UPDATE I_Transporte "
						  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Camion, ' "
						  + "WHERE M_Camion_ID IS NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					if (no != 0)
					log.warning ("Invalid Camion=" + no);
							
			
			// C_BPartner3 
//				BP from Value
				sql = new StringBuffer ("UPDATE I_Transporte o "
					  + "SET C_BPartner3_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
					  + " WHERE o.BPartner3_Value=bp.Value AND o.AD_Client_ID=bp.AD_Client_ID) "
					  + "WHERE C_BPartner3_ID IS NULL AND BPartner3_Value IS NOT NULL"
					  + " AND I_IsImported<>'Y'").append (clientCheck);
				no = DB.executeUpdate(sql.toString(), get_TrxName());
				log.fine("Set BP from Value=" + no);
				
				// BP From name
				sql = new StringBuffer ("UPDATE I_Transporte o "
						  + "SET C_BPartner3_ID=(SELECT MAX(C_BPartner_ID) FROM C_BPartner bp"
						  + " WHERE o.BPartner3_Name=bp.Name AND o.AD_Client_ID=bp.AD_Client_ID) "
						  + "WHERE C_BPartner3_ID IS NULL AND BPartner3_Name IS NOT NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					log.fine("Set BP from Name=" + no);
				
				sql = new StringBuffer ("UPDATE I_Transporte "
						  + "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Bussines Partner3, ' "
						  + "WHERE C_BPartner3_ID IS NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					if (no != 0)
					log.warning ("Invalid Bussines Partner3=" + no);
			
			// C_Order
					
				sql = new StringBuffer ("UPDATE I_Transporte o "
						  + "SET C_Order_ID=(SELECT MAX(C_Order_ID) FROM C_Order i"
						  + " WHERE o.ORDERNO=i.DocumentNo AND o.AD_Client_ID=i.AD_Client_ID) "
						  + "WHERE C_Order_ID IS NULL AND ORDERNO IS NOT NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					log.fine("Set Order From DocNo=" + no);			
								
			
			
			// C_Invoice
					
				sql = new StringBuffer ("UPDATE I_Transporte o "
						  + "SET C_Invoice_ID=(SELECT MAX(C_Invoice_ID) FROM C_Invoice i"
						  + " WHERE o.InvoiceDocumentNo=i.DocumentNo AND o.AD_Client_ID=i.AD_Client_ID) "
						  + "WHERE C_Invoice_ID IS NULL AND InvoiceDocumentNo IS NOT NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					log.fine("Set Invoice From DocNo=" + no);		
			
			
			// M_InOut
			
				sql = new StringBuffer ("UPDATE I_Transporte o "
						  + "SET M_InOut_ID=(SELECT MAX(C_Order_ID) FROM M_InOut i"
						  + " WHERE o.INOUTNO=i.DocumentNo AND o.AD_Client_ID=i.AD_Client_ID) "
						  + "WHERE M_InOut_ID IS NULL AND INOUTNO IS NOT NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					log.fine("Set InOut From DocNo=" + no);	
					
			// M_Transporte

				sql = new StringBuffer ("UPDATE I_Transporte o "
						  + "SET M_Transporte_ID=(SELECT MAX(M_Transporte_ID) FROM M_Transporte i"
						  + " WHERE o.DocumentNo=i.DocumentNo AND o.AD_Client_ID=i.AD_Client_ID) "
						  + "WHERE M_Transporte_ID IS NULL AND DocumentNo IS NOT NULL"
						  + " AND I_IsImported<>'Y'").append (clientCheck);
					no = DB.executeUpdate(sql.toString(), get_TrxName());
					log.fine("Set Transporte From DocNo=" + no);	
			
			
			
			
//			-- New Orders -----------------------------------------------------

			int noInsert = 0;
			int noInsertLine = 0;
			StringBuilder insertedDocs = null;
			int noLine = 0;

			//	Go through Order Records w/o
			sql = new StringBuffer ("SELECT * FROM I_Transporte "
				  + "WHERE I_IsImported='N'").append (clientCheck)
				.append(" ORDER BY DateAcct, C_BPartner_ID");
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			X_M_Transporte transporte = null;
			Timestamp prev_dateAcct = new Timestamp(0);
			int prev_C_BPartner_ID = 0;
			
			try
			{
				pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
				rs = pstmt.executeQuery ();
				while (rs.next ())
				{
					X_I_Transporte imp = new X_I_Transporte (getCtx (), rs, get_TrxName());
											
					if(imp.getM_Transporte_ID()>0){
						transporte = new X_M_Transporte(getCtx(), imp.getM_Transporte_ID(), get_TrxName());
						noLine = 10;
					}
					else if(transporte == null || 
							(prev_dateAcct.compareTo(imp.getDateAcct())!=0 && prev_C_BPartner_ID!=imp.getC_BPartner_ID())){
						transporte = new X_M_Transporte(getCtx(), 0, get_TrxName());
						transporte.setC_BPartner3_ID(imp.getC_BPartner3_ID());
						transporte.setC_BPartner_ID(imp.getC_BPartner_ID());
						transporte.setM_Camion_ID(imp.getM_Camion_ID());
						transporte.setM_Conductor_ID(imp.getM_Conductor_ID());
						transporte.setDescription(imp.getDescription());
						transporte.setDETALLERETIRACLIENTE(imp.getDETALLERETIRACLIENTE());
						transporte.setDateAcct(imp.getDateAcct());
						transporte.setCANTIDADPIONETA(imp.getCANTIDADPIONETA().intValue());
						transporte.setRetiraCliente(imp.isRetiraCliente());
						transporte.setEsMaquila(imp.isEsMaquila());
						
						transporte.saveEx(get_TrxName());
						if(insertedDocs==null)
							insertedDocs = new StringBuilder (transporte.getDocumentNo());
						else
							insertedDocs.append(", "+transporte.getDocumentNo());
						
						prev_dateAcct = imp.getDateAcct();
						prev_C_BPartner_ID = imp.getC_BPartner_ID();
						noLine = 10;
						noInsert++;
					}
					
					if(imp.getC_Order_ID()>0||imp.getC_Invoice_ID()>0||imp.getM_InOut_ID()>0){
						X_M_TransporteLine line = new X_M_TransporteLine(getCtx(), 0, get_TrxName());
						line.setC_Order_ID(imp.getC_Order_ID());
						line.setC_Invoice_ID(imp.getC_Invoice_ID());
						line.setM_InOut_ID(imp.getM_InOut_ID());
						line.setCantidadBulto(imp.getCantidadBulto());
						line.setVALORVIAJE(imp.getVALORVIAJE().intValue());
						line.setDescription(imp.getDESCRIPTIONLINE());
						line.setCedible(imp.isCedible());
						line.setCOBERTURA(imp.isCOBERTURA());
						line.setFABRICS(imp.isFABRICS());
						line.setLine(noLine);
						
						line.saveEx(get_TrxName());
						noInsertLine++;
						noLine += 10;
					}
					imp.setI_IsImported(true);
					imp.setProcessed(true);
					imp.saveEx(get_TrxName());
				}
			}catch (Exception e)
			{
				log.log(Level.SEVERE, "Order - " + sql.toString(), e);
			}finally{
				DB.close(rs, pstmt);
				rs=null;
				pstmt=null;
			}
			
			if(insertedDocs==null)
				insertedDocs = new StringBuilder();
		
		return "#" + noInsert + "/" + noInsertLine+ "/n Detail:" +insertedDocs.toString();
	}

}
