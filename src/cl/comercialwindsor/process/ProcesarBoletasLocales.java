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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcesarBoletasLocales extends SvrProcess 
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
			/*else if (name.equals("AD_Org_ID"))
				p_org_id  =  para[i].getParameterAsInt();*/
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
		
		StringBuilder sqlorg = new StringBuilder("SELECT org.AD_Org_ID")
		.append(" , MIN(w.M_Warehouse_ID) as M_Warehouse_ID")
		.append(" , MIN(w.C_Cash_ID) as C_Cash_ID")
		.append(" , COALESCE(MAX(ba.C_BankAccount_ID),1000009) as C_BankAccount_ID")
		.append(" FROM AD_Org org")
		.append(" LEFT JOIN M_Warehouse w ON w.IsActive='Y' AND w.AD_Org_ID=org.AD_Org_ID")
		.append(" LEFT JOIN C_BankAccount ba ON ba.IsActive='Y' AND ba.AD_Org_ID=org.AD_Org_ID")
		.append(" LEFT JOIN C_Cash ch ON ch.docstatus in ('DR','IP') AND ch.AD_Org_ID=org.AD_Org_ID")
		.append(" WHERE org.localactivo='Y'")
		.append(" GROUP BY org.AD_Org_ID");
		
		
		
		
		PreparedStatement pstmtorg = null;
		ResultSet rsorg = null;
		try
		{
			pstmtorg = DB.prepareStatement (sqlorg.toString(), get_TrxName());
			rsorg = pstmtorg.executeQuery ();
			while (rsorg.next())
			{
				p_org_id = rsorg.getInt("AD_Org_ID");
				int M_Warehouse_ID = rsorg.getInt("M_Warehouse_ID");
				int C_Cash_ID = rsorg.getInt("C_Cash_ID");
				int C_BankAccount_ID = rsorg.getInt("C_BankAccount_ID");				
				
				if(C_Cash_ID>0)
				{
					
				
					//return "Primero debe abrir el diario efectivo y/o validarlo";
				
			   StringBuilder sql = new StringBuilder("SELECT pos.VENTA_ID")
			   					.append(",pos.VENDEDOR_ID")
			   					.append(",pos.AD_ORG_ID")
			   					.append(",pos.NUM_DOCUMENTO")
			   					.append(",pos.C_BPARTNER_ID")
			   					.append(",pos.C_BPARTNER_LOCATION_ID")
			   					.append(",pos.FORMA_PAGO_ID")
			   					.append(",pos.COD_TIPOTARJETA")
			   					.append(",pos.NUM_TARJETA")
			   					.append(",pos.NUM_CHEQUE")
			   					.append(",to_char( pos.FECHA,'yyyy-MM-dd') as Fecha")
			   					.append(",pos.M_PRICELIST_ID")
			   					.append(",pos.PROCESADO")
			   					.append(",pos.EMAIL")
			   					.append(",pos.C_ORDER_ID")
			   					.append(",pos.PROCESSED")
			   					.append(",pos.C_DOCTYPE_ID")
			   					.append(",pos.NUM_CUENTA")
			   					.append(",MAX(odt.C_DocType_ID) as OrderDocType_ID")
			   					.append(",COALESE(MAX(plv.M_PRICELIST_VERSION_ID),10000000) as M_PRICELIST_VERSION_ID")
			   					.append(" FROM venta_pos pos")
			   					.append(" JOIN C_DocType odt ON odt.c_doctypeinvoice_ID=pos.C_DocType_ID AND odt.IsActive='Y'")
			   					.append(" LEFT JOIN M_PRICELIST_VERSION plv ON plv.M_PRICELIST_ID=pos.M_PRICELIST_ID")
			   					.append(" WHERE pos.processed='N' AND pos.AD_Org_ID=").append(p_org_id)
			   					.append(" GROUP BY ")
			   					;
			   			   
			   
			   try
				{
					PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
					ResultSet rs = pstmt.executeQuery ();
					while (rs.next())
					{
						log.info ("Procesando ventas");
						int OrderDocType_ID = rs.getInt("OrderDocType_ID");
						int M_PRICELIST_VERSION_ID = rs.getInt("M_PRICELIST_VERSION_ID");
						
						MOrder ord = new MOrder (getCtx() , 0, get_TrxName());
						MBPartner bp = new MBPartner (getCtx(), rs.getInt("C_BPartner_ID"), get_TrxName());
						ord.setClientOrg (getAD_Client_ID(),p_org_id);
						ord.setC_DocTypeTarget_ID(OrderDocType_ID);
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
						
						ord.setM_Warehouse_ID(M_Warehouse_ID);
						
						//Faltan firmas horas de firma y quien firmo forma de compra medio compra
						//
						
						ord.save();
						//precio lista
						
				
						//Lineas de la orden
						
						StringBuilder sqldet = new StringBuilder("SELECT det.VENTA_ID")
							.append(",det.ID_Producto")
							.append(",det.Precio")
							.append(",det.Cantidad")
							.append(",COALESCE(MAX(pp.pricelist),0) as Pricelist")
							.append(" FROM PRODUCTOS_CARRITO det")
							.append(" LEFT JOIN m_productprice pp ON pp.m_product_ID=det.ID_Producto AND pp.M_PRICELIST_VERSION_ID=").append(M_PRICELIST_VERSION_ID)
							.append(" WHERE det.ID_VENTA=").append(rs.getString("VENTA_ID"));	

						List<MOrderLine> orderLines = new ArrayList<MOrderLine>();
						int lineas = 1;
						PreparedStatement pstmtdet = null;
						ResultSet rsdet = null;
						try
						{
							pstmtdet = DB.prepareStatement (sqldet.toString(), get_TrxName());
							rsdet = pstmtdet.executeQuery();
							while (rsdet.next())
							{
								
								MOrderLine line = new MOrderLine(ord);
								//precio de precio lista

								BigDecimal Pricelist = rs.getBigDecimal("Pricelist");
								BigDecimal Precio = rsdet.getBigDecimal("Precio");
								BigDecimal Cantidad = rsdet.getBigDecimal("Cantidad");
								
								line.setM_Product_ID(rsdet.getInt("ID_Producto"));
								line.setPriceEntered(Precio);
								line.setPriceActual(Precio);
								
								line.setPriceList(Pricelist);
								line.setQtyEntered(Cantidad);
								line.setLine(lineas*10);
								line.setPrice(Precio);
								
								line.setQty(Cantidad);
								line.setC_Tax_ID(1000000);
								
									BigDecimal df = new BigDecimal (0);
									line.set_CustomColumn("Discount2", df);
									line.set_CustomColumn("Discount3", df);
									line.set_CustomColumn("Discount", df);
									line.set_CustomColumn("Discount3", df);
									line.set_CustomColumn("Discount4", df);
									line.set_CustomColumn("Discount5", df);
									line.set_CustomColumn("NotPrint", "N");
								
								line.setLineNetAmt();
								line.save();
								orderLines.add(line);
								lineas++;
							}
							
						}//try detalle de Orden de venta
						catch(Exception e)
						{
							log.log(Level.SEVERE, e.getMessage(), e);
						}finally{
							DB.close(rsdet, pstmtdet);
							rsdet = null;
							pstmtdet = null;
						}
						
						if(ord.processIt(MOrder.ACTION_Complete)){							
							ord.save();							
							DB.executeUpdate("Update VENTA_POS set Processed='Y', c_order_ID= "+ ord.getC_Order_ID()
									+ " where VENTA_ID='"+rs.getString("VENTA_ID")+"'", this.get_TrxName());						
						}else{					
							ord.save();							
							DB.executeUpdate("Update VENTA_POS set Processed='Y', c_order_ID= "+ ord.getC_Order_ID()
									+ " where VENTA_ID='"+rs.getString("VENTA_ID")+"'", this.get_TrxName());								
							continue;
						}
						
						
						//validar si es boleta o no 
						if (ord.getGrandTotal().intValue()!=0)
						{
							
						
						//factura
					
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
						for(MOrderLine orderLine : orderLines){
							MInvoiceLine il = new MInvoiceLine (inv);
							il.setOrderLine(orderLine);
							il.setQty(orderLine.getQtyEntered());
							il.setPrice(orderLine.getPriceEntered());
							il.setLineNetAmt();
							il.save();
						}
						
						orderLines.clear();
						
						if(inv.processIt (MInvoice.ACTION_Complete)){
							inv.save();
						}else{

							DB.executeUpdate("Update VENTA_POS set Processed='Y', c_order_ID= "+ ord.getC_Order_ID()
									+ " where VENTA_ID='"+rs.getString("VENTA_ID")+"'", this.get_TrxName());								
							continue;
						}
						
						
						   //}
						//Pago distinto a Cash
						if (!rs.getString("FORMA_PAGO_ID").equals("T"))
						{
							
							MPayment pay = new MPayment (getCtx(), 0, get_TrxName());
							pay.setAD_Org_ID(p_org_id);
							pay.setC_BankAccount_ID(C_BankAccount_ID);
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
							

							if(pay.processIt (MPayment.ACTION_Complete)){
								pay.save();
							}else{

								DB.executeUpdate("Update VENTA_POS set Processed='Y', c_order_ID= "+ ord.getC_Order_ID()
										+ " where VENTA_ID='"+rs.getString("VENTA_ID")+"'", this.get_TrxName());								
								continue;
							}
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
			   
			}//if cash
				
				//fin del recorrido por orgazanizion
			}
			
		}		
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}finally{
			DB.close(rsorg, pstmtorg);
			rsorg = null;
			pstmtorg = null;
		}
		
				
				
	return "Procesado, Recuerde hacer el cierre de el diario efectivo como siempre al final del dia";
	}//	doIt	
}
