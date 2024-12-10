/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package cl.comercialwindsor.apps.form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.IStatusBar;
import org.compiere.apps.form.GenForm;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPInstance;
import org.compiere.model.MRole;
//import org.compiere.model.MUser;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.ofb.model.OFBForward;

import cl.comercialwindsor.model.MXMLInvoice;

/**
 * Generate Invoice (manual) controller class
 * 
 */
public class GenerateDocsFromOCv3 extends GenForm
{
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(GenerateDocsFromOCv3.class);
	//
	
	public Object 			m_AD_Org_ID = null;
	public Object 			m_C_BPartner_ID = null;
	public Object 			m_DateFrom = null;
	public Object 			m_DateTo = null;
	public Object  			m_User_ID = null; 
	//public Object  m_POReference = null;
	public Object  			m_BPLocation_ID= null;
	public Object  			m_IsTxtGen= null;
	
	public void dynInit() throws Exception
	{
		setTitle("InvGenerateInfo");
		setReportEngineType(ReportEngine.INVOICE);
		setAskPrintMsg("PrintInvoices");
	}
	
	public void configureMiniTable(IMiniTable miniTable)
	{
		//  create Columns
		miniTable.addColumn("C_Order_ID");
		miniTable.addColumn("AD_Org_ID");
		miniTable.addColumn("C_DocType_ID");
		miniTable.addColumn("DocumentNo");
		miniTable.addColumn("Description");
		miniTable.addColumn("C_BPartner_ID");
		miniTable.addColumn("DateOrdered");
		miniTable.addColumn("TotalLines");
		//
		miniTable.setMultiSelection(true);
	   //  set details
		miniTable.setColumnClass(0, IDColumn.class, false, " ");
		miniTable.setColumnClass(1, String.class, true, Msg.translate(Env.getCtx(), "AD_Org_ID"));
		miniTable.setColumnClass(2, String.class, true, Msg.translate(Env.getCtx(), "C_DocType_ID"));
		miniTable.setColumnClass(3, String.class, true, Msg.translate(Env.getCtx(), "DocumentNo"));
		miniTable.setColumnClass(4, String.class, true, Msg.translate(Env.getCtx(), "Description"));
		miniTable.setColumnClass(5, String.class, true, Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		miniTable.setColumnClass(6, Timestamp.class, true, Msg.translate(Env.getCtx(), "DateAcct"));
		miniTable.setColumnClass(7, BigDecimal.class, true, Msg.translate(Env.getCtx(), "TotalLines"));
		miniTable.autoSize();
	}
	
	/**
	 * Get SQL for Orders that needs to be shipped
	 * @return sql
	 */
	private String getOrderSQL(int ID_Type)
	{
	    StringBuffer sql = new StringBuffer(
	            "SELECT ic.C_order_ID, o.Name, dt.Name, ic.DocumentNo, bp.Name, ic.DateAcct, ic.TotalLines "
	            + "FROM C_ORDERTODOCTXT ic, AD_Org o, C_BPartner bp, C_DocType dt "
	            + "WHERE ic.AD_Org_ID=o.AD_Org_ID"
	            + " AND ic.C_BPartner_ID=bp.C_BPartner_ID"
	            + " AND ic.C_DocType_ID=dt.C_DocType_ID"
	            + " AND ic.AD_Client_ID=?");

        if (m_AD_Org_ID != null)
            sql.append(" AND ic.AD_Org_ID=").append(m_AD_Org_ID);
        //ininoles se agrega validacion para que no muestre las facturas de todas la ORG
        else//codigo de validaciones para organizaciones        	
        {
        	MRole role = new MRole(Env.getCtx(), Env.getAD_Role_ID(Env.getCtx()),null);
        	if(!role.isAccessAllOrgs())//sin acceso a todas las organizaciones
        	{
            	String sqlValidOrg = "";
        		if(role.isUseUserOrgAccess())//usuario usa acceso a organizaciones
        			sqlValidOrg = "SELECT AD_Org_ID FROM AD_User_OrgAccess WHERE IsActive = 'Y' AND AD_User_ID = "+Env.getAD_User_ID(Env.getCtx());
        		else //rol usa acceso a organizacion 
        			sqlValidOrg = "SELECT AD_Org_ID FROM AD_Role_OrgAccess WHERE IsActive = 'Y' AND AD_Role_ID = "+role.get_ID();        		
        		sql.append(" AND ic.AD_Org_ID IN ("+sqlValidOrg+")");
        	}        	
        }//ininoles end	
        if (m_C_BPartner_ID != null)
            sql.append(" AND ic.C_BPartner_ID=").append(m_C_BPartner_ID);
        
        if (m_DateFrom != null || m_DateTo != null)
		{
			Timestamp from = (Timestamp) m_DateFrom;
			Timestamp to = (Timestamp) m_DateTo;
			if (from == null && to != null)
				sql.append(" AND TRUNC(ic.DateAcct) <= ?");
			else if (from != null && to == null)
				sql.append(" AND TRUNC(ic.DateAcct) >= ?");
			else if (from != null && to != null)
				sql.append(" AND TRUNC(ic.DateAcct) BETWEEN ? AND ?");
		}
        
        
       if(m_User_ID!=null)
    	   sql.append(" AND ic.AD_User_ID=?");
       if(m_BPLocation_ID!=null)
    	   sql.append(" AND ic.C_BPartner_Location_ID=?");
       if(ID_Type == 1)
   		   sql.append(" AND ic.IsTextGen = 'Y' ");
   	   else
   		   sql.append(" AND (ic.IsTextGen <> 'Y' OR ic.IsTextGen IS NULL)");

       
       sql.append(" ORDER BY o.Name,bp.Name,ic.DateAcct");
        
       return sql.toString();
	}
	
	/**
	 * Get SQL for Customer RMA that need to be invoiced
	 * @return sql
	 */
	
	public void executeQuery(KeyNamePair IsTxtGenKNPair, IMiniTable miniTable)
	{
		log.info("");
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		//  Create SQL
		
		String sql = getOrderSQL(IsTxtGenKNPair.getKey());
       
		//  reset table
		int row = 0;
		miniTable.setRowCount(row);
		//  Execute
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, AD_Client_ID);
			int i=2;
			 if (m_DateFrom != null || m_DateTo != null)
				{
				 Timestamp from = (Timestamp) m_DateFrom;
					Timestamp to = (Timestamp) m_DateTo;
					log.fine("Date From=" + from + ", To=" + to);
					if (from == null && to != null)
						pstmt.setTimestamp(i++, to);
					else if (from != null && to == null)
						pstmt.setTimestamp(i++, from);
					else if (from != null && to != null)
					{
						pstmt.setTimestamp(i++, from);
						pstmt.setTimestamp(i++, to);
					}
				}
			 if(m_User_ID!=null)
				 pstmt.setInt(i++, (Integer)m_User_ID);
		     if(m_BPLocation_ID!=null)
		    	 pstmt.setInt(i++, (Integer)m_BPLocation_ID);
		     //if(m_POReference!=null && ((String)m_POReference).length()>0)
		    //	 pstmt.setString(i++, (String)m_POReference);
			rs = pstmt.executeQuery();
			//
			while (rs.next())
			{
				//  extend table
				miniTable.setRowCount(row+1);
				//  set values
				miniTable.setValueAt(new IDColumn(rs.getInt(1)), row, 0);   //  C_Order_ID
				miniTable.setValueAt(rs.getString(2), row, 1);              //  Org
				miniTable.setValueAt(rs.getString(3), row, 2);              //  DocType
				miniTable.setValueAt(rs.getString(4), row, 3);              //  Doc No
				if(OFBForward.NewDescriptionInvoiceGenPA())
				{
					String newDescription = DB.getSQLValueString(null, "SELECT string_agg(documentno, ', ' order by documentno)" +
							" FROM M_InOut WHERE C_Order_ID = "+rs.getInt(1)); 
					newDescription = newDescription +"-"+new MOrder(Env.getCtx(),rs.getInt(1),null).getSalesRep().getName();
					miniTable.setValueAt(newDescription, row, 4);              //  Description faaguuilar Added
				}
				else
					miniTable.setValueAt(new MOrder(Env.getCtx(),rs.getInt(1),null).getDescription(), row, 4);              //  Description 
				miniTable.setValueAt(rs.getString(5), row, 5);              //  BPartner
				miniTable.setValueAt(rs.getTimestamp(6), row, 6);           //  DateOrdered
				miniTable.setValueAt(rs.getBigDecimal(7), row, 7);          //  TotalLines
				//  prepare next
				row++;
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally{
			DB.close(rs, pstmt);
		}
		//
		miniTable.autoSize();
	//	statusBar.setStatusDB(String.valueOf(miniTable.getRowCount()));
	}   //  executeQuery
	
	/**
	 *	Save Selection & return selecion Query or ""
	 *  @return where clause like C_Order_ID IN (...)
	 */
	public void saveSelection(IMiniTable miniTable)
	{
		log.info("");
		//  Array of Integers
		ArrayList<Integer> results = new ArrayList<Integer>();
		setSelection(null);

		//	Get selected entries
		int rows = miniTable.getRowCount();
		for (int i = 0; i < rows; i++)
		{
			IDColumn id = (IDColumn)miniTable.getValueAt(i, 0);     //  ID in column 0
		//	log.fine( "Row=" + i + " - " + id);
			if (id != null && id.isSelected())
				results.add(id.getRecord_ID());
		}

		if (results.size() == 0)
			return;
		log.config("Selected #" + results.size());
		setSelection(results);
	}	//	saveSelection

	
	/**************************************************************************
	 *	Generate document
	 * Parametro detTypeKNPair = Cantiadad de veces que desea generar el txt
	 */
	public String generate(IStatusBar statusBar, KeyNamePair detTypeKNPair,KeyNamePair pcTypeKNPair)
	{
		String info = "";
		String trxName = Trx.createTrxName("IVG");
		Trx trx = Trx.get(trxName, true);	//trx needs to be committed too
		
		setSelectionActive(false);  //  prevents from being called twice
		statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "OrdGenerateGen"));
		statusBar.setStatusDB(String.valueOf(getSelection().size()));		
		
		int AD_Process_ID = DB.getSQLValue(trxName, "SELECT AD_Process_ID FROM AD_Process WHERE value like 'TestGenTxt'");;
		MPInstance instance = new MPInstance(Env.getCtx(), AD_Process_ID, 0);
		if (!instance.save())
		{
			info = Msg.getMsg(Env.getCtx(), "ProcessNoInstance");
			return info;
		}
		ProcessInfo pi = new ProcessInfo ("", AD_Process_ID);
		pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
		//pi.addLog();
		
		/*
		 * Generar documentos asociados
		 * 
		 */
		
		/*
		 * Fin Generar documentos
		 * 
		 * 
		 */
		
		//insert selection
		for(Integer selectedId : getSelection())
		{
			//se generan archivos de txt en base a cada factura
			MOrder oc = new MOrder(Env.getCtx(), selectedId, null);
			//MUser user = new MUser(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()), null);
			
			/*
			 * 
			 * Generar recibo despacho y boleta factura
			 * 
			 */
			
			MOrder order = new MOrder (Env.getCtx() , oc.get_ValueAsInt("C_OrderVenta_ID"), null);
			int cant=0;
			int Locator_ID=0;
			String RM = "";
			String EM = "";
			//valida cantidades de lineas
			String coc="Select count(ol.c_orderline_ID) OC, sum(qtyentered)qtyoc from C_orderline ol "+
				 	//" inner join c_order o on (ol.c_ordeR_ID=o.c_order_ID) "+
		 		 " where ol.QTYDELIVERED<ol.QTYENTERED and	 ol.c_order_ID=?"+
		 		 " and not exists (select * from m_inoutline iol"
		 		 + "				inner join m_inout io on (iol.m_inout_ID=io.m_inout_ID)"
		 		 + "	 			where ol.c_orderline_ID=iol.c_orderline_ID and io.docstatus in ('DR','CO','IN','IP'))";
			
			String cov="Select count(ol.c_orderline_ID) OV, sum(qtyentered)qtyov  from C_orderline ol inner join m_product p on (ol.m_product_Id=p.m_product_ID) "+
				 	//" inner join c_order o on (ol.c_ordeR_ID=o.c_order_ID) "+
		 		 " where ol.QTYDELIVERED<ol.QTYENTERED and	 ol.c_order_ID=?"+
		 		 " and p.producttype='I' "+
		 		 " and not exists (select * from m_inoutline iol"
		 		 + "				inner join m_inout io on (iol.m_inout_ID=io.m_inout_ID)"
		 		 + "	 			where ol.c_orderline_ID=iol.c_orderline_ID and io.docstatus in ('DR','CO','IN','IP'))";
			int uoc=0;
			int uov=0;
			int qoc=0;
			int qov=0;
			PreparedStatement pstmt = null;
			ResultSet rs=null;
			try
			{
				
				pstmt = DB.prepareStatement(coc, null);
				pstmt.setInt(1, selectedId);
				rs = pstmt.executeQuery();
				
				while (rs.next())
				{
					
					
					uoc=rs.getInt("OC");
					qoc=rs.getInt("qtyoc");
				}
			
						
			}
			catch (Exception e)
			{
				
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			finally{
				DB.close(rs, pstmt);
			}
			pstmt = null;
			rs=null;
			
			try
			{
				
				pstmt = DB.prepareStatement(cov, null);
				pstmt.setInt(1, oc.get_ValueAsInt("C_OrderVenta_ID"));
				rs = pstmt.executeQuery();
				
				while (rs.next())
				{
					uov=rs.getInt("OV");
					qov=rs.getInt("qtyov");
					
				}
			}
			catch (Exception e)
			{
				
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			finally{
				DB.close(rs, pstmt);
			}
			
			int compareocov=0;
			if(uoc!=uov || qoc!=qov)
			{
				pi.addLog(0, oc.getDateAcct(), null, "Validar cantidad de productos de la OC contra la OV original, o que ya no este procesada: " + oc.getDocumentNo());
				compareocov++;
			}
				
			
			//fin valida cantidad de productos
			
			if(compareocov==0)
				{
					
				
				 String sql ="Select ol.c_orderline_ID from C_orderline ol inner join m_product p on (ol.m_product_Id=p.m_product_ID) "+
						 	//" inner join c_order o on (ol.c_ordeR_ID=o.c_order_ID) "+
				 		 " where ol.QTYDELIVERED<ol.QTYENTERED and	 ol.c_order_ID=?"+
				 		  " and p.producttype='I' "+
				 		 " and not exists (select * from m_inoutline iol"+
				 		
				 		 "				inner join m_inout io on (iol.m_inout_ID=io.m_inout_ID)"
				 		 + "	 			where ol.c_orderline_ID=iol.c_orderline_ID and io.docstatus in ('DR','CO','IN','IP'))";
				
				pstmt = null;
				rs=null;
				try
				{ //try RM
					
					pstmt = DB.prepareStatement(sql, null);
					pstmt.setInt(1,oc.getC_Order_ID() );
					rs = pstmt.executeQuery();
					
					
					MInOut rec= new MInOut  ( oc,0, oc.getDateAcct());
					rec.save();
					Locator_ID = DB.getSQLValue(null,"Select max(l.m_locator_ID) m_locator_ID"+
						 	" from m_locator l "+
				 		 " where l.isactive='Y' and l.m_warehouse_ID=?",
				 		rec.getM_Warehouse_ID());
					
					while (rs.next())
						{
							cant=cant+10;	
							MOrderLine line = new MOrderLine (Env.getCtx() , rs.getInt("C_OrderLine_ID"),null);
							MInOutLine reline = new MInOutLine (rec);
							reline.setM_InOut_ID(rec.getM_InOut_ID());
							reline.setOrderLine(line, Locator_ID, line.getQtyReserved());
							reline.setQty(line.getQtyReserved());
							reline.setMovementQty(line.getQtyReserved());
							reline.setQtyEntered(line.getQtyReserved());
							//lineio.setC_OrderLine_ID (rs.getInt("C_OrderLine_ID"));
							//lineio.setLine(cant);
							//lineio.setM_Product_ID(rs.getInt("M_Product_ID"));
							//lineio.setC_(rs.getInt("M_Product_ID"));
							
							reline.save();	
													
							
								
								
								
							
						}
					Locator_ID=0;
					
					rec.set_CustomColumn("NOFACTURA", oc.getPOReference().replace("Fact Windsor:", " "));
					rec.processIt(MInOut.DOCACTION_Complete);
					rec.completeIt();
					rec.setDocAction(MInOut.DOCACTION_Close);
					//rec.processIt ("CO");
					rec.save();
					RM="Se Genera RM Nº: "+rec.getDocumentNo();
					
					
				}//try RM
				catch (Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				finally{
					DB.close(rs, pstmt);
				}
			
				 cant=0;
				   sql ="Select ol.c_orderline_ID from C_orderline ol inner join m_product p on (ol.m_product_Id=p.m_product_ID) "+
						 	//" inner join c_order o on (ol.c_ordeR_ID=o.c_order_ID) "+
				 		 " where ol.QTYDELIVERED<ol.QTYENTERED and	 ol.c_order_ID=?"+
				 		  " and p.producttype='I' "+
				 		 " and not exists (select * from m_inoutline iol"+
				 		
				 		 "				inner join m_inout io on (iol.m_inout_ID=io.m_inout_ID)"
				 		 + "	 			where ol.c_orderline_ID=iol.c_orderline_ID and io.docstatus in ('DR','CO','IN','IP'))";
				  pstmt = null;
				  rs= null;
				   try
					{ 		//Try Despacho 
						
						pstmt = DB.prepareStatement(sql, null);
						pstmt.setInt(1, order.getC_Order_ID());
						rs = pstmt.executeQuery();
					
					
					MInOut rec= new MInOut  ( order,0, order.getDateAcct());
					rec.save();
					Locator_ID = DB.getSQLValue(null,"Select max(l.m_locator_ID) m_locator_ID"+
						 	" from m_locator l "+
				 		 " where l.isactive='Y' and l.m_warehouse_ID=?",
				 		rec.getM_Warehouse_ID());
					
					while (rs.next())
						{
							cant=cant+10;	
							MOrderLine line = new MOrderLine (Env.getCtx() , rs.getInt("C_OrderLine_ID"),null);
							MInOutLine reline = new MInOutLine (rec);
							reline.setM_InOut_ID(rec.getM_InOut_ID());
							reline.setOrderLine(line, Locator_ID, line.getQtyReserved());
							reline.setQty(line.getQtyReserved());
							reline.setMovementQty(line.getQtyReserved());
							reline.setQtyEntered(line.getQtyReserved());
							reline.save();	
						}
					Locator_ID=0;
					
					
					rec.processIt(MInOut.DOCACTION_Complete);
					rec.completeIt();
					rec.setDocAction(MInOut.DOCACTION_Close);
					//rec.processIt ("CO");
					rec.save();
					EM="Se Genera Despacho Nº: "+rec.getDocumentNo();
					
				}//try RM
				catch (Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				finally
				{
					DB.close(rs, pstmt);
				}		
						
				//Factura-Boleta
				
				
				MInvoice inv = null; 
				
				
				String sqlol = "Select * from c_orderline ol where ol.c_order_ID=? "+
							    " and not exists (Select * from c_invoiceline il inner join c_invoice i on (il.c_invoice_ID=i.c_invoice_ID) " +
							    " where il.c_orderline_ID=ol.c_orderline_ID and i.docstatus in ('CO','DR','IN','IP','CL'))";
				PreparedStatement pstmtol = null;
				ResultSet rsol=null; 	
				try
				{
					pstmtol = DB.prepareStatement (sqlol, null);
					pstmtol.setInt(1, order.getC_Order_ID());
					rsol = pstmtol.executeQuery ();
				
					while (rsol.next())
					{
						if (inv==null)
						{
							Date date = new Date();
							Timestamp ahora = new Timestamp(date.getTime());
							inv=new MInvoice (order,-1, ahora );
							inv.save();
						}
						MOrderLine ol = new MOrderLine  (Env.getCtx(), rsol.getInt("C_OrderLine_ID"), null);
						MInvoiceLine il = new MInvoiceLine (inv);
						il.setOrderLine(ol);
						il.setQty(ol.getQtyEntered());
						il.setPrice(ol.getPriceEntered());
						il.setLineNetAmt();
						il.save();
					}
					
	
					
				}//try doctype Orden de venta
				catch(Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				finally{
					DB.close(rsol, pstmtol);
				}
				
				String IN ="";
				int existeinv=0;
				if(inv==null)
				{
					IN = "Ya tiene una boleta/Factura asociada, Revisar ";
					pi.addLog(0, order.getDateAcct(), null, "Ya tiene una boleta/Factura asociada, Revisar: " + order.getDocumentNo());
					existeinv++;
				}
				//int grandtotal=inv.getGrandTotal().intValue();
				//if (inv.getGrandTotal().intValue()>0)
				  // {
				else{
					
					inv.processIt(MInvoice.DOCACTION_Complete);
					inv.completeIt();
				inv.setDocAction(MInvoice.DOCACTION_Close);
				
				//inv.processIt ("CO");
				inv.save();
				inv.setProcessed(true);
				inv.save();
				IN = "Bolete/Factura Generada: " + inv.getDocumentNo();
				
				}
				String RMsep =" ";
				if (!RM.isEmpty())
					RMsep =" - ";
			
				String INsep = " ";
				if (!IN.isEmpty())
					INsep = " - ";
				
				log.info(RMsep + " / " + INsep + " / " +EM);
				
				/**
				 * 
				 * Generacion del XML (Contaline)
				 * 
				 * **/
				
				if(existeinv==0)
				{

					try {
						MXMLInvoice XMLinv = new MXMLInvoice(inv.getCtx(), inv.getC_Invoice_ID(), null);
						XMLinv.generateXML();
					} catch (Exception e) {
						log.severe(e.toString());
					    throw new AdempiereException("Error al generar el XML");
					}				
				}
				/*
				 * 
				 * fin recibo despacho y boleta factura
				 * 
				 */
				/*
				 * Reemplazar Archivo
				 * 
				 */
				/* comentar la creacion del TXT Antiguo
				if(pcTypeKNPair.getKey() == 1)
				{
						try
						{
						
							File origen = new File("C:\\Enternet\\EnternetAPPFull\\Agente\\impresion\\entsend.xml");
				            File destino = new File("C:\\Enternet\\EnternetAPPFull\\Agente\\entsend.xml");
				            
							
							
				            InputStream in = new FileInputStream(origen);
				            OutputStream out = new FileOutputStream(destino);
		
				            byte[] buf = new byte[1024];
				            int len;
		
				            while ((len = in.read(buf)) > 0) {
				                    out.write(buf, 0, len);
				            }
		
				            in.close();
				            out.close();
						
					
						
						}
						catch (Exception e)
						{
						
							log.log(Level.SEVERE, e.getMessage(), e);
						}
				}
				if(pcTypeKNPair.getKey() == 2)
				{
						try
						{
						
							File origen = new File("C:\\Enternet\\EnternetAPPFull\\Agente\\solopdf\\entsend.xml");
				            File destino = new File("C:\\Enternet\\EnternetAPPFull\\Agente\\entsend.xml");
				            
							
							
				            InputStream in = new FileInputStream(origen);
				            OutputStream out = new FileOutputStream(destino);
		
				            byte[] buf = new byte[1024];
				            int len;
		
				            while ((len = in.read(buf)) > 0) {
				                    out.write(buf, 0, len);
				            }
		
				            in.close();
				            out.close();
						
					
						
						}
						catch (Exception e)
						{
						
							log.log(Level.SEVERE, e.getMessage(), e);
						}
				}
					/*
					 * Fin Reemplazar Archivo
					 * 
					 */	
				/* comentar la creacion del TXT Antiguo
				if(existeinv==0)
				{
					try 
					{
						int veces=1;
						while (veces<=detTypeKNPair.getKey())
						{
							
						
			            String ruta = user.get_ValueAsString("InvoicePathFile");
			            
			            ruta=ruta+inv.getDocumentNo()+"-"+String.valueOf( veces)+".txt";
			            String ln = System.getProperty("line.separator");
						StringBuilder str = new StringBuilder();
						//se agrega cabecera de archivo
						String sqlCab = "select LINEA01,LINEA02,LINEA03,LINEA04,LINEA05,LINEA06,LINEA07," +
								" LINEA08,LINEA09,LINEA10,LINEA11,LINEA12,LINEA13,LINEA14,LINEA15," +
								" LINEA16,LINEA17,LINEA18,LINEA19,LINEA20,LINEA21,LINEA22,LINEA23," +
								" LINEA24,LINEA25,LINEA26,LINEA27,LINEA28,LINEA29,LINEA31," +
								" LINEA32,LINEA33,LINEA34,LINEA30,LINEA35 FROM RVIM_CABECERABELECTRONICAAUTO" +
								" WHERE c_invoice_ID="+inv.get_ID();
						log.config("sql cabecera:"+sqlCab);
						PreparedStatement pstmt = DB.prepareStatement(sqlCab, trxName);					
						ResultSet rs = pstmt.executeQuery();
						if(rs.next())
						{
							for(int a=1;a<=34;a++)
							{
								if(a != 30 && a!=31)
									
								{
									str.append(rs.getString(a<10?"LINEA0"+a:"LINEA"+a));
									str.append(ln);
								}
								if(a == 30)
								{
									str.append(rs.getString("LINEA"+a));
									str.append(ln);
								}
								if (a==31)
								{
									if(pcTypeKNPair.getKey() == 1)
										str.append(rs.getString("LINEA"+a));
									if(pcTypeKNPair.getKey() == 2)
										str.append(rs.getString("LINEA"+35));	
									str.append(ln);
								}
									
							}
						}
						//se agrega linea cabecera detalle
						str.append("2:|ITEM|CODIGO |NOMBRE |CANTIDAD |PRECIO |DESCUENTO MONTO|TOTAL");
						
						//se agrega detalle a archivo
						String sqlDet = "";
						if(detTypeKNPair.getKey() == 1)
							sqlDet= "SELECT LINEA24 as DETALLE FROM RVIM_BOLELECTRONICA2_LINE_V WHERE c_invoice_ID=?";
						else if (detTypeKNPair.getKey() == 2)
							sqlDet= "SELECT LINEA24 as DETALLE FROM RVIM_BOLELECTRONICA2_LINE_V WHERE c_invoice_ID=?";
						else if (detTypeKNPair.getKey() == 3)
							sqlDet= "SELECT LINEA24 as DETALLE FROM RVIM_BOLELECTRONICA2_LINE_V WHERE c_invoice_ID=?";
							
						log.config("sql detalle:"+sqlDet);
						PreparedStatement pstmtDet = DB.prepareStatement(sqlDet, trxName);	
						pstmtDet.setInt(1, inv.get_ID());
						ResultSet rsDet = pstmtDet.executeQuery();
						while(rsDet.next())
						{
							str.append(ln);
							str.append(rsDet.getString("DETALLE"));					
						}
						
			            File file = new File(ruta);
			            // Si el archivo no existe es creado
			            if (!file.exists()) {
			                file.createNewFile();
			            }
			            FileWriter fw = new FileWriter(file);
			            BufferedWriter bw = new BufferedWriter(fw);
			            bw.write(str.toString());
			            bw.close();
			            if(veces==1)
			            	pi.addLog(0, inv.getDateInvoiced(), null, "Factura o Boleta Generada: " + inv.getDocumentNo());
			            veces++;
			            
			            
						} 
			            //ininoles se actualiza campo indicativo
			            DB.executeUpdate("UPDATE C_Order SET IsTextGen = 'Y' WHERE C_Invoice_ID = "+oc.get_ID(), trxName);
			          
								
						
					} catch (Exception e) {
			            e.printStackTrace();
			        }
				
				}*/// if existeinv==0
	
			}	//if compareocov	
			
		} //for inselect
		
		setProcessInfo(pi);
		setTrx(trx); //esto es como un commit a todo lo que se hizo.
		/*
		 * Volver al xml original
		 * 
		 */
		/* comentar la creacion del TXT Antiguo
		try
		{
		
			File origen = new File("C:\\Enternet\\EnternetAPPFull\\Agente\\impresion\\entsend.xml");
            File destino = new File("C:\\Enternet\\EnternetAPPFull\\Agente\\entsend.xml");
            
			
			
            InputStream in = new FileInputStream(origen);
            OutputStream out = new FileOutputStream(destino);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
            }

            in.close();
            out.close();
		
	
		
		}
		catch (Exception e)
		{
		
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		/*
		 * Volver al xml original
		 * 
		 */
		return info;
	}	//	generateInvoices
}