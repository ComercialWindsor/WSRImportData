package cl.comercialwindsor.process;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Properties;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.*;

import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
//import java.math.BigDecimal;
//import org.compiere.model.MOrder;
//import org.compiere.model.X_C_OrderLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
//import org.compiere.model.X_C_Invoice;
import org.compiere.process.*;
import org.compiere.util.AdempiereSystemError;
//import org.compiere.util.DB;
//import org.compiere.util.Env;
import org.compiere.util.DB;







import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcesarBoletasLocal extends SvrProcess 
{
	//private Properties 		m_ctx;	
	
	private int p_org_id = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_org_id  =  para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	//	m_ctx = Env.getCtx();
		
		//int cliente = Env.getAD_User_ID(getCtx());
	}	//	prepare

	/**
	 *  Perrform process.
	 *  @return Message
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws AdempiereSystemError
	{
	
		Boolean cash = false;
		//int cash_id =0;
		String sqlc = "Select count(1) encontrado "+
						" From c_cash "+
						" where docstatus in ('DR','IP') "+
						" and ad_org_ID="+p_org_id;
		try
		{
			PreparedStatement pstmtc = DB.prepareStatement (sqlc, get_TrxName());
			ResultSet rsc = pstmtc.executeQuery ();
		
			if (rsc.next())
			{
				if (rsc.getInt("encontrado")==1)
				{
					cash=true;
					/*String sqlcid= "Select max(c_cash_ID) c_cash_ID "+
							" From c_cash "+
							" where docstatus in ('DR','IP') "+
							" and ad_org_ID="+p_org_id;
					try
					{
						PreparedStatement pstmtcid = DB.prepareStatement (sqlcid, get_TrxName());
						ResultSet rscid = pstmtcid.executeQuery ();
					
						while (rscid.next())
						{
							
							cash_id=rscid.getInt("c_cash_ID");
						}
						
						rscid.close();
						pstmtcid.close();
					}//try doctype Orden de venta
					catch(Exception e)
					{
						
						log.log(Level.SEVERE, e.getMessage(), e);
					}*/
				}
			}
			
			rsc.close();
			pstmtc.close();
		}//try caja chica
		catch(Exception e)
		{
			
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		
		if(!cash)
			return "Primero debe abrir el diario efectivo y/o validarlo";
		
	   String sql = "Select "+
					"   VENTA_ID, " +
					"   VENDEDOR_ID, "+
					"   AD_ORG_ID, " +
					"   NUM_DOCUMENTO, "+
					"   C_BPARTNER_ID, "+
					"   C_BPARTNER_LOCATION_ID, "+
					"   FORMA_PAGO_ID, "+
					"   COD_TIPOTARJETA, "+
					"   NUM_TARJETA, "+
					"   NUM_CHEQUE, "+
					"   to_char( FECHA,'yyyy-MM-dd') Fecha, "+
					"   M_PRICELIST_ID, "+
					"   PROCESADO, "+
					"   EMAIL, "+
					"   C_ORDER_ID, "+
					"   PROCESSED, "+
					"   C_DOCTYPE_ID, "+
					"   NUM_CUENTA "+
			   		" from venta_pos where processed='N' and AD_Org_ID="+p_org_id;
	   try
		{
			PreparedStatement pstmt = DB.prepareStatement (sql, get_TrxName());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next())
			{
				log.info ("Procesando ventas");
				int doctyorder_id=0;
				String sqldocOrder= "Select max(dt.c_doctype_ID)c_doctype_ID " +
									" from c_doctype dt " +
									" where dt.isactive='Y' and dt.c_doctypeinvoice_ID="+rs.getInt("C_DocType_ID");
				try
				{
					PreparedStatement pstmtdt = DB.prepareStatement (sqldocOrder, get_TrxName());
					ResultSet rsdt = pstmtdt.executeQuery ();
				
					while (rsdt.next())
					{
						log.info ("Tipo Doc");
						doctyorder_id=rsdt.getInt("c_doctype_ID");
					}
					if(doctyorder_id==0)
						return "no existe documento orden base";
					rsdt.close();
					pstmtdt.close();
				}//try doctype Orden de venta
				catch(Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				
				MOrder ord = new MOrder (getCtx() , 0, get_TrxName());
				MBPartner bp = new MBPartner (getCtx(), rs.getInt("C_BPartner_ID"), get_TrxName());
				ord.setClientOrg (1000000,p_org_id);
				ord.setC_DocTypeTarget_ID(doctyorder_id);
				ord.setIsSOTrx(true);
				ord.setDeliveryRule("F");
				ord.setC_BPartner_ID(bp.getC_BPartner_ID());
				ord.setC_BPartner_Location_ID(rs.getInt("C_BPartner_Location_ID"));
				ord.setDescription(rs.getString("VENTA_ID"));
				ord.setAD_User_ID(rs.getInt("VENDEDOR_ID"));
				//	Bill Partner
				ord.setBill_BPartner_ID(bp.getC_BPartner_ID());
				ord.setBill_Location_ID(rs.getInt("C_BPartner_Location_ID"));
				//
				ord.setC_PaymentTerm_ID(1000001);
				ord.setM_PriceList_ID(rs.getInt("M_PriceList_ID"));
				
				
				
				ord.setSalesRep_ID(rs.getInt("VENDEDOR_ID"));

				try {
				      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				       // you can change format of date
				      Date date = formatter.parse(rs.getString("Fecha")  );
				      Timestamp timeStampDate = new Timestamp(date.getTime());
				      log.info ("Fechas");
				      ord.setDateOrdered( timeStampDate);
				      ord.setDateAcct( timeStampDate);
				    } catch (ParseException e) {
				      System.out.println("Exception :" + e);
				      return null;
				    }
				//
				
				
				ord.setInvoiceRule("D");
				
				String sqlw = "Select min(w.m_warehouse_ID) m_warehouse_ID"+
			   		 			" from m_warehouse w " +
			   		 			" where w.isactive='Y' and w.ad_org_ID="+p_org_id;
				try
				{
					PreparedStatement pstmtw = DB.prepareStatement (sqlw, get_TrxName());
					ResultSet rsw = pstmtw.executeQuery ();
				
					while (rsw.next())
					{
						ord.setM_Warehouse_ID(rsw.getInt("m_warehouse_ID"));
					}
					
					rsw.close();
					pstmtw.close();
				}//try doctype Orden de venta
				catch(Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				
				
				
				//Faltan firmas horas de firma y quien firmo forma de compra medio compra
				//
				
				ord.save();
				
				
				/*
				 * Ver si es un local mayorista 
				 */
				
				String sqlorgtype = " Select ot.name, coalesce(oi.ov_monto_mayorista,0) monto from ad_orgtype ot " +
													 " inner join ad_orginfo oi on (ot.ad_orgtype_ID=oi.ad_orgtype_ID)" +
													 " where oi.ad_org_ID="+p_org_id;
				Boolean mayorista = false;
				String may = "";
				int monto= 0;
				try
				{
					PreparedStatement pstmtt = DB.prepareStatement (sqlorgtype, get_TrxName());
					ResultSet rspt = pstmtt.executeQuery ();
				
					while (rspt.next())
					{
						may=rspt.getString("name");
						monto=rspt.getInt("monto");
					}
					
					rspt.close();
					pstmtt.close();
				}
				catch(Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				
				if(may.equals("Sucursal mayorista"))
					mayorista=true;
			
				
				String sqlsuma = "Select sum(total_may) as total  from PRODUCTOS_CARRITO where ID_VENTA='"+rs.getString("VENTA_ID")+"'";
				Boolean total= false;
				int sumatoria = 0;
				try
				{
					PreparedStatement pstmts = DB.prepareStatement (sqlsuma, get_TrxName());
					ResultSet rss = pstmts.executeQuery ();
				
					while (rss.next())
					{
						
						sumatoria=rss.getInt("total");
					}
					
					rss.close();
					pstmts.close();
				}
				catch(Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				
				if(sumatoria>=monto)
					total = true;
				/*
				 * fin ver si es un local mayorista
				 * */
				//Lineas de la orden
				
				String sqldet = "Select * from PRODUCTOS_CARRITO where ID_VENTA='"+rs.getString("VENTA_ID")+"'";
				try
				{
					PreparedStatement pstmtdet = DB.prepareStatement (sqldet, get_TrxName());
					ResultSet rsdet = pstmtdet.executeQuery ();
				int lineas = 1;
					while (rsdet.next())
					{
						
						MOrderLine line = new MOrderLine(ord);
					
					// 	line.setC_Order_ID(order.getC_Order_ID());
						line.setM_Product_ID(rsdet.getInt("ID_Producto"));
						if(mayorista && ord.getC_BPartner_ID()!=1000003 && total )
						{
							line.setPriceEntered( new BigDecimal  (rsdet.getInt("Precio_MAY")));
							line.setPriceActual(new BigDecimal  (rsdet.getInt("Precio_May")));
						}
						else
						{
							line.setPriceEntered( new BigDecimal  (rsdet.getInt("Precio")));
							line.setPriceActual(new BigDecimal  (rsdet.getInt("Precio")));
						}
						line.setPriceList(new BigDecimal  (rsdet.getInt("Precio")));
						line.setQtyEntered( new BigDecimal (rsdet.getInt("Cantidad")));
						line.setLine(lineas*10);
						if(mayorista && ord.getC_BPartner_ID()!=1000003 && total)
						{
						
							line.setPrice(new BigDecimal  (rsdet.getInt("Precio_May")));
						}
						else
						{
							line.setPrice(new BigDecimal  (rsdet.getInt("Precio")));
						}
						
						line.setQty(new BigDecimal (rsdet.getInt("Cantidad")));
						line.setC_Tax_ID(1000000);
						
					//	line.setLineNetAmt(rs.getBigDecimal("LineNetAmt"));
						//int la = rs.getBigDecimal("PriceEntered").intValue() * rs.getBigDecimal("QtyEntered").intValue();
						//BigDecimal lab = new BigDecimal (la);
						
						
							
							//line.set_CustomColumn("Discount2", rs.getBigDecimal("Discount2"));
							//line.set_CustomColumn("Discount3", rs.getBigDecimal("Discount3"));
							BigDecimal df = new BigDecimal (0);
							line.set_CustomColumn("Discount2", df);
							//BigDecimal df = new BigDecimal (0);
							line.set_CustomColumn("Discount3", df);
							//BigDecimal df = new BigDecimal (0);
							line.set_CustomColumn("Discount", df);
							line.set_CustomColumn("Discount3", df);
							line.set_CustomColumn("Discount4", df);
							line.set_CustomColumn("Discount5", df);
							line.set_CustomColumn("NotPrint", "N");
						
						
						line.setLineNetAmt();
						line.save();
						//line.set_ValueOfColumn("TEMPLINE_ID", rs.getInt("M_INVENTORYLINETEMP_ID"));
						
						
						
						
						
						lineas++;
					}
					
					rsdet.close();
					pstmtdet.close();
				}//try detalle de Orden de venta
				catch(Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				ord.setDocAction("CO");
				ord.processIt ("CO");
				ord.save();
				
				DB.executeUpdate("Update VENTA_POS set Processed='Y', c_order_ID= "+ ord.getC_Order_ID()
						+ " where VENTA_ID='"+rs.getString("VENTA_ID")+"'", this.get_TrxName());
			
				//factura
				//validar si es boleta o no 
				if (ord.getGrandTotal().intValue()!=0)
				{
				MInvoice inv = new MInvoice (ord, rs.getInt("C_DocType_ID"), ord.getDateAcct());
				inv.setDocumentNo(rs.getString("NUM_DOCUMENTO"));
				if (rs.getString("FORMA_PAGO_ID").equals("T")) 
					inv.setPaymentRule("B");
				else if (rs.getString("FORMA_PAGO_ID").equals("K"))
					inv.setPaymentRule("S");
				else if (rs.getString("FORMA_PAGO_ID").equals("C"))
					inv.setPaymentRule("K");
				else if (rs.getString("FORMA_PAGO_ID").equals("P"))
					inv.setPaymentRule("T");
				else if (rs.getString("FORMA_PAGO_ID").equals("M") || rs.getString("FORMA_PAGO_ID").equals("N") || rs.getString("FORMA_PAGO_ID").equals("O"))
					inv.setPaymentRule("E");
				else if (rs.getString("FORMA_PAGO_ID").equals("T") )
					inv.setPaymentRule("E");
				else
				inv.setPaymentRule(rs.getString("FORMA_PAGO_ID"));
				inv.save();
				
				//detalle de factura
				String sqlol = "Select * from c_orderline where c_order_ID="+ord.getC_Order_ID();
	 			
				try
				{
					PreparedStatement pstmtol = DB.prepareStatement (sqlol, get_TrxName());
					ResultSet rsol = pstmtol.executeQuery ();
				
					while (rsol.next())
					{
						MOrderLine ol = new MOrderLine  (getCtx(), rsol.getInt("C_OrderLine_ID"), get_TrxName());
						MInvoiceLine il = new MInvoiceLine (inv);
						il.setOrderLine(ol);
						il.setQty(ol.getQtyEntered());
						il.setPrice(ol.getPriceEntered());
						il.setLineNetAmt();
						il.save();
					}
					
					rsol.close();
					pstmtol.close();
				}//try doctype Orden de venta
				catch(Exception e)
				{
					
					log.log(Level.SEVERE, e.getMessage(), e);
				}
				//int grandtotal=inv.getGrandTotal().intValue();
				//if (inv.getGrandTotal().intValue()>0)
				  // {
				inv.setDocAction("CO");
				inv.processIt ("CO");
				inv.save();
				inv.setProcessed(true);
				inv.save();
				
				
				   //}
				//Pago distinto a Cash
				if (!rs.getString("FORMA_PAGO_ID").equals("T"))
				{
						
					
					
						
					String sqla = " select max (C_BankAccount_ID) C_BankAccount_ID " +
									" from c_bankAccount "+
									" where isactive='Y' and Ad_org_ID="+p_org_id;
					int acc_id =1000009;
					try
					{
						PreparedStatement pstmta = DB.prepareStatement (sqla, get_TrxName());
						ResultSet rsa = pstmta.executeQuery ();
					
						if (rsa.next())
						{
							acc_id= rsa.getInt("C_BankAccount_ID");
						}
						
						rsa.close();
						pstmta.close();
					}//try doctype Orden de venta
					catch(Exception e)
					{
						
						log.log(Level.SEVERE, e.getMessage(), e);
					}
					
					MPayment pay = new MPayment (getCtx(), 0, get_TrxName());
					pay.setAD_Org_ID(p_org_id);
					pay.setC_BankAccount_ID(acc_id);
					pay.setC_DocType_ID(true);
					pay.setC_DocType_ID(1000008);
					pay.setC_BPartner_ID(rs.getInt("C_BPartner_ID"));
					pay.setTenderType(rs.getString("FORMA_PAGO_ID"));
					pay.setC_Invoice_ID(inv.getC_Invoice_ID());
					pay.setPayAmt(inv.getGrandTotal() );
					pay.setDateTrx(inv.getDateAcct());
					pay.setDateAcct(inv.getDateAcct());
					pay.setC_Currency_ID(228);
					if(rs.getString("FORMA_PAGO_ID").equals("K"))
					{
						pay.setCheckNo("'"+rs.getInt("NUM_CHEQUE")+"'" );
						pay.setAccountNo("'"+rs.getInt("NUM_CUENTA")+"'" );
						pay.save();
					}
					if(rs.getString("FORMA_PAGO_ID").equals("C"))
					{
						pay.setCreditCardType(rs.getString("COD_TIPOTARJETA"));
						pay.setCreditCardNumber("'"+rs.getInt("NUM_TARJETA")+"'");
						pay.save();
					}
					pay.setDocAction("CO");
					pay.processIt ("CO");
					
					pay.save();
					
					pay.setProcessed(true);
					pay.save();	
						
				}
			}//grand total !0	
			else
			{
				ord.setDescription(ord.getDescription() + " - Cambio de Producto");
				ord.save();
			}
			}
			
			
			
			
			
			
			rs.close();
			pstmt.close();
		}
		
		
		catch(Exception e)
		{
			
			log.log(Level.SEVERE, e.getMessage(), e);
		}
				
				
	return "Procesado, Recuerde hacer el cierre de el diario efectivo como siempre al final del dia";
	}//	doIt	
}
