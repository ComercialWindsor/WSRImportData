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
import org.compiere.util.KeyNamePair;

/** Generated Model for M_TransporteLine
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_M_TransporteLine extends PO implements I_M_TransporteLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220103L;

    /** Standard Constructor */
    public X_M_TransporteLine (Properties ctx, int M_TransporteLine_ID, String trxName)
    {
      super (ctx, M_TransporteLine_ID, trxName);
      /** if (M_TransporteLine_ID == 0)
        {
			setProcessed (false);
			setVALORVIAJE (0);
        } */
    }

    /** Load Constructor */
    public X_M_TransporteLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_TransporteLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CantidadBulto.
		@param CantidadBulto CantidadBulto	  */
	public void setCantidadBulto (BigDecimal CantidadBulto)
	{
		set_Value (COLUMNNAME_CantidadBulto, CantidadBulto);
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

	public I_C_City getC_City() throws RuntimeException
    {
		return (I_C_City)MTable.get(getCtx(), I_C_City.Table_Name)
			.getPO(getC_City_ID(), get_TrxName());	}

	/** Set City.
		@param C_City_ID 
		City
	  */
	public void setC_City_ID (int C_City_ID)
	{
		if (C_City_ID < 1) 
			set_Value (COLUMNNAME_C_City_ID, null);
		else 
			set_Value (COLUMNNAME_C_City_ID, Integer.valueOf(C_City_ID));
	}

	/** Get City.
		@return City
	  */
	public int getC_City_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cedible.
		@param Cedible Cedible	  */
	public void setCedible (boolean Cedible)
	{
		set_Value (COLUMNNAME_Cedible, Boolean.valueOf(Cedible));
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
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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
		set_Value (COLUMNNAME_COBERTURA, Boolean.valueOf(COBERTURA));
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
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
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

	/** Set DETALLEFABRICS.
		@param DETALLEFABRICS DETALLEFABRICS	  */
	public void setDETALLEFABRICS (String DETALLEFABRICS)
	{
		set_Value (COLUMNNAME_DETALLEFABRICS, DETALLEFABRICS);
	}

	/** Get DETALLEFABRICS.
		@return DETALLEFABRICS	  */
	public String getDETALLEFABRICS () 
	{
		return (String)get_Value(COLUMNNAME_DETALLEFABRICS);
	}

	/** Set FABRICS.
		@param FABRICS FABRICS	  */
	public void setFABRICS (boolean FABRICS)
	{
		set_Value (COLUMNNAME_FABRICS, Boolean.valueOf(FABRICS));
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

	/** Set ISWS.
		@param ISWS ISWS	  */
	public void setISWS (boolean ISWS)
	{
		set_Value (COLUMNNAME_ISWS, Boolean.valueOf(ISWS));
	}

	/** Get ISWS.
		@return ISWS	  */
	public boolean isWS () 
	{
		Object oo = get_Value(COLUMNNAME_ISWS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
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
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
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

	/** MotivoDevolucion AD_Reference_ID=1000049 */
	public static final int MOTIVODEVOLUCION_AD_Reference_ID=1000049;
	/** Retraso Transportista = Retraso Transportista */
	public static final String MOTIVODEVOLUCION_RetrasoTransportista = "Retraso Transportista";
	/** Cajas Mal Estado = Cajas Mal Estado */
	public static final String MOTIVODEVOLUCION_CajasMalEstado = "Cajas Mal Estado";
	/** Set MotivoDevolucion.
		@param MotivoDevolucion MotivoDevolucion	  */
	public void setMotivoDevolucion (String MotivoDevolucion)
	{

		set_Value (COLUMNNAME_MotivoDevolucion, MotivoDevolucion);
	}

	/** Get MotivoDevolucion.
		@return MotivoDevolucion	  */
	public String getMotivoDevolucion () 
	{
		return (String)get_Value(COLUMNNAME_MotivoDevolucion);
	}

	/** Set Movement Date.
		@param MovementDate 
		Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Movement Date.
		@return Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_MovementDate);
	}

	/*public I_M_ServicioTransporte getM_ServicioTransporte() throws RuntimeException
    {
		return (I_M_ServicioTransporte)MTable.get(getCtx(), I_M_ServicioTransporte.Table_Name)
			.getPO(getM_ServicioTransporte_ID(), get_TrxName());	}*/

	/** Set M_ServicioTransporte_ID.
		@param M_ServicioTransporte_ID M_ServicioTransporte_ID	  */
	public void setM_ServicioTransporte_ID (int M_ServicioTransporte_ID)
	{
		if (M_ServicioTransporte_ID < 1) 
			set_Value (COLUMNNAME_M_ServicioTransporte_ID, null);
		else 
			set_Value (COLUMNNAME_M_ServicioTransporte_ID, Integer.valueOf(M_ServicioTransporte_ID));
	}

	/** Get M_ServicioTransporte_ID.
		@return M_ServicioTransporte_ID	  */
	public int getM_ServicioTransporte_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ServicioTransporte_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Transporte getM_Transporte() throws RuntimeException
    {
		return (I_M_Transporte)MTable.get(getCtx(), I_M_Transporte.Table_Name)
			.getPO(getM_Transporte_ID(), get_TrxName());	}

	/** Set M_Transporte_ID.
		@param M_Transporte_ID M_Transporte_ID	  */
	public void setM_Transporte_ID (int M_Transporte_ID)
	{
		if (M_Transporte_ID < 1) 
			set_Value (COLUMNNAME_M_Transporte_ID, null);
		else 
			set_Value (COLUMNNAME_M_Transporte_ID, Integer.valueOf(M_Transporte_ID));
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_Transporte_ID()));
    }

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

	/** Set NOBOLETAT.
		@param NOBOLETAT NOBOLETAT	  */
	public void setNOBOLETAT (String NOBOLETAT)
	{
		set_Value (COLUMNNAME_NOBOLETAT, NOBOLETAT);
	}

	/** Get NOBOLETAT.
		@return NOBOLETAT	  */
	public String getNOBOLETAT () 
	{
		return (String)get_Value(COLUMNNAME_NOBOLETAT);
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

	/** Set PRODUCTVALUES.
		@param PRODUCTVALUES PRODUCTVALUES	  */
	public void setPRODUCTVALUES (String PRODUCTVALUES)
	{
		set_Value (COLUMNNAME_PRODUCTVALUES, PRODUCTVALUES);
	}

	/** Get PRODUCTVALUES.
		@return PRODUCTVALUES	  */
	public String getPRODUCTVALUES () 
	{
		return (String)get_Value(COLUMNNAME_PRODUCTVALUES);
	}

	/** ResultadoTransporte AD_Reference_ID=1000048 */
	public static final int RESULTADOTRANSPORTE_AD_Reference_ID=1000048;
	/** Exitoso = E */
	public static final String RESULTADOTRANSPORTE_Exitoso = "E";
	/** Devolucion Total = D */
	public static final String RESULTADOTRANSPORTE_DevolucionTotal = "D";
	/** Devolucion Parcial = P */
	public static final String RESULTADOTRANSPORTE_DevolucionParcial = "P";
	/** Retira Cliente = R */
	public static final String RESULTADOTRANSPORTE_RetiraCliente = "R";
	/** Anulacion = A */
	public static final String RESULTADOTRANSPORTE_Anulacion = "A";
	/** Set ResultadoTransporte.
		@param ResultadoTransporte ResultadoTransporte	  */
	public void setResultadoTransporte (String ResultadoTransporte)
	{

		set_Value (COLUMNNAME_ResultadoTransporte, ResultadoTransporte);
	}

	/** Get ResultadoTransporte.
		@return ResultadoTransporte	  */
	public String getResultadoTransporte () 
	{
		return (String)get_Value(COLUMNNAME_ResultadoTransporte);
	}

	/** Set Retira.
		@param Retira Retira	  */
	public void setRetira (boolean Retira)
	{
		set_Value (COLUMNNAME_Retira, Boolean.valueOf(Retira));
	}

	/** Get Retira.
		@return Retira	  */
	public boolean isRetira () 
	{
		Object oo = get_Value(COLUMNNAME_Retira);
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
	public void setVALORVIAJE (int VALORVIAJE)
	{
		set_Value (COLUMNNAME_VALORVIAJE, Integer.valueOf(VALORVIAJE));
	}

	/** Get VALORVIAJE.
		@return VALORVIAJE	  */
	public int getVALORVIAJE () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VALORVIAJE);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}