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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for M_TransporteLineBulto
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_M_TransporteLineBulto extends PO implements I_M_TransporteLineBulto, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220103L;

    /** Standard Constructor */
    public X_M_TransporteLineBulto (Properties ctx, int M_TransporteLineBulto_ID, String trxName)
    {
      super (ctx, M_TransporteLineBulto_ID, trxName);
      /** if (M_TransporteLineBulto_ID == 0)
        {
			setC_Order_ID (0);
// @C_Order_ID@
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_M_TransporteLineBulto (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_TransporteLineBulto[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/*public I_C_Bulto getC_Bulto() throws RuntimeException
    {
		return (I_C_Bulto)MTable.get(getCtx(), I_C_Bulto.Table_Name)
			.getPO(getC_Bulto_ID(), get_TrxName());	}*/

	/** Set C_Bulto_ID.
		@param C_Bulto_ID C_Bulto_ID	  */
	public void setC_Bulto_ID (int C_Bulto_ID)
	{
		if (C_Bulto_ID < 1) 
			set_Value (COLUMNNAME_C_Bulto_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bulto_ID, Integer.valueOf(C_Bulto_ID));
	}

	/** Get C_Bulto_ID.
		@return C_Bulto_ID	  */
	public int getC_Bulto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bulto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set M_TransporteLineBulto_ID.
		@param M_TransporteLineBulto_ID M_TransporteLineBulto_ID	  */
	public void setM_TransporteLineBulto_ID (int M_TransporteLineBulto_ID)
	{
		if (M_TransporteLineBulto_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_TransporteLineBulto_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_TransporteLineBulto_ID, Integer.valueOf(M_TransporteLineBulto_ID));
	}

	/** Get M_TransporteLineBulto_ID.
		@return M_TransporteLineBulto_ID	  */
	public int getM_TransporteLineBulto_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TransporteLineBulto_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_TransporteLine getM_TransporteLine() throws RuntimeException
    {
		return (I_M_TransporteLine)MTable.get(getCtx(), I_M_TransporteLine.Table_Name)
			.getPO(getM_TransporteLine_ID(), get_TrxName());	}

	/** Set M_TransporteLine_ID.
		@param M_TransporteLine_ID M_TransporteLine_ID	  */
	public void setM_TransporteLine_ID (int M_TransporteLine_ID)
	{
		if (M_TransporteLine_ID < 1) 
			set_Value (COLUMNNAME_M_TransporteLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_TransporteLine_ID, Integer.valueOf(M_TransporteLine_ID));
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
}