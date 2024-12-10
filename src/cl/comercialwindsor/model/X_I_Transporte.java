/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package cl.comercialwindsor.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for I_Transporte
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_I_Transporte extends PO implements I_I_Transporte, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220103L;

    /** Standard Constructor */
    public X_I_Transporte (Properties ctx, int I_Transporte_ID, String trxName)
    {
      super (ctx, I_Transporte_ID, trxName);
      /** if (I_Transporte_ID == 0)
        {
			setI_IsImported (false);
// N
			setI_Transporte_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_Transporte (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_I_Transporte[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Business Partner Name.
		@param BPartner_Name 
		Business Partner Name
	  */
	public void setBPartner_Name (String BPartner_Name)
	{
		set_Value (COLUMNNAME_BPartner_Name, BPartner_Name);
	}

	/** Get Business Partner Name.
		@return Business Partner Name
	  */
	public String getBPartner_Name () 
	{
		return (String)get_Value(COLUMNNAME_BPartner_Name);
	}

	/** Set Business Partner Key.
		@param BPartner_Value 
		The Key of the Business Partner
	  */
	public void setBPartner_Value (String BPartner_Value)
	{
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	/** Get Business Partner Key.
		@return The Key of the Business Partner
	  */
	public String getBPartner_Value () 
	{
		return (String)get_Value(COLUMNNAME_BPartner_Value);
	}

	/** Set Business Partner Name 3.
		@param BPartner3_Name 
		Business Partner Name 3
	  */
	public void setBPartner3_Name (String BPartner3_Name)
	{
		set_Value (COLUMNNAME_BPartner3_Name, BPartner3_Name);
	}

	/** Get Business Partner Name 3.
		@return Business Partner Name 3
	  */
	public String getBPartner3_Name () 
	{
		return (String)get_Value(COLUMNNAME_BPartner3_Name);
	}

	/** Set Business Partner Key 3.
		@param BPartner3_Value 
		The Key of the Business Partner 3
	  */
	public void setBPartner3_Value (String BPartner3_Value)
	{
		set_Value (COLUMNNAME_BPartner3_Value, BPartner3_Value);
	}

	/** Get Business Partner Key 3.
		@return The Key of the Business Partner 3
	  */
	public String getBPartner3_Value () 
	{
		return (String)get_Value(COLUMNNAME_BPartner3_Value);
	}

	/** Set Camion Name.
		@param CamionName Camion Name	  */
	public void setCamionName (String CamionName)
	{
		set_Value (COLUMNNAME_CamionName, CamionName);
	}

	/** Get Camion Name.
		@return Camion Name	  */
	public String getCamionName () 
	{
		return (String)get_Value(COLUMNNAME_CamionName);
	}

	/** Set CantidadBulto.
		@param CantidadBulto CantidadBulto	  */
	public void setCantidadBulto (BigDecimal CantidadBulto)
	{
		set_ValueNoCheck (COLUMNNAME_CantidadBulto, CantidadBulto);
	}

	/** Get CantidadBulto.
		@return CantidadBulto	  */
	public BigDecimal getCantidadBulto () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CantidadBulto);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CANTIDADPIONETA.
		@param CANTIDADPIONETA CANTIDADPIONETA	  */
	public void setCANTIDADPIONETA (BigDecimal CANTIDADPIONETA)
	{
		set_ValueNoCheck (COLUMNNAME_CANTIDADPIONETA, CANTIDADPIONETA);
	}

	/** Get CANTIDADPIONETA.
		@return CANTIDADPIONETA	  */
	public BigDecimal getCANTIDADPIONETA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CANTIDADPIONETA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner getC_BPartner3() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner3_ID(), get_TrxName());	}

	/** Set Business Partner 3.
		@param C_BPartner3_ID 
		Identifies a Business Partner 3
	  */
	public void setC_BPartner3_ID (int C_BPartner3_ID)
	{
		if (C_BPartner3_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner3_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner3_ID, Integer.valueOf(C_BPartner3_ID));
	}

	/** Get Business Partner 3.
		@return Identifies a Business Partner 3
	  */
	public int getC_BPartner3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cedible.
		@param Cedible Cedible	  */
	public void setCedible (boolean Cedible)
	{
		set_ValueNoCheck (COLUMNNAME_Cedible, Boolean.valueOf(Cedible));
	}

	/** Get Cedible.
		@return Cedible	  */
	public boolean isCedible () 
	{
		Object oo = get_Value(COLUMNNAME_Cedible);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set COBERTURA.
		@param COBERTURA COBERTURA	  */
	public void setCOBERTURA (boolean COBERTURA)
	{
		set_ValueNoCheck (COLUMNNAME_COBERTURA, Boolean.valueOf(COBERTURA));
	}

	/** Get COBERTURA.
		@return COBERTURA	  */
	public boolean isCOBERTURA () 
	{
		Object oo = get_Value(COLUMNNAME_COBERTURA);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Conductor.
		@param Conductor Conductor	  */
	public void setConductor (String Conductor)
	{
		set_Value (COLUMNNAME_Conductor, Conductor);
	}

	/** Get Conductor.
		@return Conductor	  */
	public String getConductor () 
	{
		return (String)get_Value(COLUMNNAME_Conductor);
	}

	public I_C_Order getC_Order() throws RuntimeException
    {
		return (I_C_Order)MTable.get(getCtx(), I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set DESCRIPTIONLINE.
		@param DESCRIPTIONLINE DESCRIPTIONLINE	  */
	public void setDESCRIPTIONLINE (String DESCRIPTIONLINE)
	{
		set_Value (COLUMNNAME_DESCRIPTIONLINE, DESCRIPTIONLINE);
	}

	/** Get DESCRIPTIONLINE.
		@return DESCRIPTIONLINE	  */
	public String getDESCRIPTIONLINE () 
	{
		return (String)get_Value(COLUMNNAME_DESCRIPTIONLINE);
	}

	/** Set DETALLERETIRACLIENTE.
		@param DETALLERETIRACLIENTE DETALLERETIRACLIENTE	  */
	public void setDETALLERETIRACLIENTE (String DETALLERETIRACLIENTE)
	{
		set_Value (COLUMNNAME_DETALLERETIRACLIENTE, DETALLERETIRACLIENTE);
	}

	/** Get DETALLERETIRACLIENTE.
		@return DETALLERETIRACLIENTE	  */
	public String getDETALLERETIRACLIENTE () 
	{
		return (String)get_Value(COLUMNNAME_DETALLERETIRACLIENTE);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set EsMaquila.
		@param EsMaquila EsMaquila	  */
	public void setEsMaquila (boolean EsMaquila)
	{
		set_ValueNoCheck (COLUMNNAME_EsMaquila, Boolean.valueOf(EsMaquila));
	}

	/** Get EsMaquila.
		@return EsMaquila	  */
	public boolean isEsMaquila () 
	{
		Object oo = get_Value(COLUMNNAME_EsMaquila);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set FABRICS.
		@param FABRICS FABRICS	  */
	public void setFABRICS (boolean FABRICS)
	{
		set_ValueNoCheck (COLUMNNAME_FABRICS, Boolean.valueOf(FABRICS));
	}

	/** Get FABRICS.
		@return FABRICS	  */
	public boolean isFABRICS () 
	{
		Object oo = get_Value(COLUMNNAME_FABRICS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Import Error Message.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import process
	  */
	public String getI_ErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set INOUTNO.
		@param INOUTNO INOUTNO	  */
	public void setINOUTNO (String INOUTNO)
	{
		set_Value (COLUMNNAME_INOUTNO, INOUTNO);
	}

	/** Get INOUTNO.
		@return INOUTNO	  */
	public String getINOUTNO () 
	{
		return (String)get_Value(COLUMNNAME_INOUTNO);
	}

	/** Set Invoice Document No.
		@param InvoiceDocumentNo 
		Document Number of the Invoice
	  */
	public void setInvoiceDocumentNo (String InvoiceDocumentNo)
	{
		set_Value (COLUMNNAME_InvoiceDocumentNo, InvoiceDocumentNo);
	}

	/** Get Invoice Document No.
		@return Document Number of the Invoice
	  */
	public String getInvoiceDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceDocumentNo);
	}

	/** Set Import Transporte.
		@param I_Transporte_ID Import Transporte	  */
	public void setI_Transporte_ID (int I_Transporte_ID)
	{
		if (I_Transporte_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Transporte_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Transporte_ID, Integer.valueOf(I_Transporte_ID));
	}

	/** Get Import Transporte.
		@return Import Transporte	  */
	public int getI_Transporte_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Transporte_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public cl.comercialwindsor.model.I_M_Camion getM_Camion() throws RuntimeException
    {
		return (cl.comercialwindsor.model.I_M_Camion)MTable.get(getCtx(), cl.comercialwindsor.model.I_M_Camion.Table_Name)
			.getPO(getM_Camion_ID(), get_TrxName());	}*/

	/** Set M_Camion_ID.
		@param M_Camion_ID M_Camion_ID	  */
	public void setM_Camion_ID (int M_Camion_ID)
	{
		if (M_Camion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Camion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Camion_ID, Integer.valueOf(M_Camion_ID));
	}

	/** Get M_Camion_ID.
		@return M_Camion_ID	  */
	public int getM_Camion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Camion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public cl.comercialwindsor.model.I_M_Conductor getM_Conductor() throws RuntimeException
    {
		return (cl.comercialwindsor.model.I_M_Conductor)MTable.get(getCtx(), cl.comercialwindsor.model.I_M_Conductor.Table_Name)
			.getPO(getM_Conductor_ID(), get_TrxName());	}*/

	/** Set M_Conductor_ID.
		@param M_Conductor_ID M_Conductor_ID	  */
	public void setM_Conductor_ID (int M_Conductor_ID)
	{
		if (M_Conductor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Conductor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Conductor_ID, Integer.valueOf(M_Conductor_ID));
	}

	/** Get M_Conductor_ID.
		@return M_Conductor_ID	  */
	public int getM_Conductor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Conductor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_InOut getM_InOut() throws RuntimeException
    {
		return (I_M_InOut)MTable.get(getCtx(), I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public cl.comercialwindsor.model.I_M_Transporte getM_Transporte() throws RuntimeException
    {
		return (cl.comercialwindsor.model.I_M_Transporte)MTable.get(getCtx(), cl.comercialwindsor.model.I_M_Transporte.Table_Name)
			.getPO(getM_Transporte_ID(), get_TrxName());	}*/

	/** Set M_Transporte_ID.
		@param M_Transporte_ID M_Transporte_ID	  */
	public void setM_Transporte_ID (int M_Transporte_ID)
	{
		if (M_Transporte_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Transporte_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Transporte_ID, Integer.valueOf(M_Transporte_ID));
	}

	/** Get M_Transporte_ID.
		@return M_Transporte_ID	  */
	public int getM_Transporte_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Transporte_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public cl.comercialwindsor.model.I_M_TransporteLine getM_TransporteLine() throws RuntimeException
    {
		return (cl.comercialwindsor.model.I_M_TransporteLine)MTable.get(getCtx(), cl.comercialwindsor.model.I_M_TransporteLine.Table_Name)
			.getPO(getM_TransporteLine_ID(), get_TrxName());	}*/

	/** Set M_TransporteLine_ID.
		@param M_TransporteLine_ID M_TransporteLine_ID	  */
	public void setM_TransporteLine_ID (int M_TransporteLine_ID)
	{
		if (M_TransporteLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_TransporteLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_TransporteLine_ID, Integer.valueOf(M_TransporteLine_ID));
	}

	/** Get M_TransporteLine_ID.
		@return M_TransporteLine_ID	  */
	public int getM_TransporteLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TransporteLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ORDERNO.
		@param ORDERNO ORDERNO	  */
	public void setORDERNO (String ORDERNO)
	{
		set_Value (COLUMNNAME_ORDERNO, ORDERNO);
	}

	/** Get ORDERNO.
		@return ORDERNO	  */
	public String getORDERNO () 
	{
		return (String)get_Value(COLUMNNAME_ORDERNO);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set RetiraCliente.
		@param RetiraCliente RetiraCliente	  */
	public void setRetiraCliente (boolean RetiraCliente)
	{
		set_Value (COLUMNNAME_RetiraCliente, Boolean.valueOf(RetiraCliente));
	}

	/** Get RetiraCliente.
		@return RetiraCliente	  */
	public boolean isRetiraCliente () 
	{
		Object oo = get_Value(COLUMNNAME_RetiraCliente);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set VALORVIAJE.
		@param VALORVIAJE VALORVIAJE	  */
	public void setVALORVIAJE (BigDecimal VALORVIAJE)
	{
		set_ValueNoCheck (COLUMNNAME_VALORVIAJE, VALORVIAJE);
	}

	/** Get VALORVIAJE.
		@return VALORVIAJE	  */
	public BigDecimal getVALORVIAJE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_VALORVIAJE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}