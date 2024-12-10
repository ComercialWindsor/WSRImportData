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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_Transporte
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_M_Transporte extends PO implements I_M_Transporte, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220103L;

    /** Standard Constructor */
    public X_M_Transporte (Properties ctx, int M_Transporte_ID, String trxName)
    {
      super (ctx, M_Transporte_ID, trxName);
      /** if (M_Transporte_ID == 0)
        {
			setCANTIDADPIONETA (0);
// 0
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setDocumentNo (null);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_M_Transporte (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_Transporte[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BOTON.
		@param BOTON BOTON	  */
	public void setBOTON (String BOTON)
	{
		set_Value (COLUMNNAME_BOTON, BOTON);
	}

	/** Get BOTON.
		@return BOTON	  */
	public String getBOTON () 
	{
		return (String)get_Value(COLUMNNAME_BOTON);
	}

	/** Set BotonCobertura.
		@param BotonCobertura BotonCobertura	  */
	public void setBotonCobertura (String BotonCobertura)
	{
		set_Value (COLUMNNAME_BotonCobertura, BotonCobertura);
	}

	/** Get BotonCobertura.
		@return BotonCobertura	  */
	public String getBotonCobertura () 
	{
		return (String)get_Value(COLUMNNAME_BotonCobertura);
	}

	/** Set CANTIDADPIONETA.
		@param CANTIDADPIONETA CANTIDADPIONETA	  */
	public void setCANTIDADPIONETA (int CANTIDADPIONETA)
	{
		set_Value (COLUMNNAME_CANTIDADPIONETA, Integer.valueOf(CANTIDADPIONETA));
	}

	/** Get CANTIDADPIONETA.
		@return CANTIDADPIONETA	  */
	public int getCANTIDADPIONETA () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CANTIDADPIONETA);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Business Partner .
		@param C_BPartner3_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner3_ID (int C_BPartner3_ID)
	{
		if (C_BPartner3_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner3_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner3_ID, Integer.valueOf(C_BPartner3_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner3_ID);
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
		set_Value (COLUMNNAME_DateAcct, DateAcct);
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

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Shipped = SP */
	public static final String DOCSTATUS_Shipped = "SP";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set EsMaquila.
		@param EsMaquila EsMaquila	  */
	public void setEsMaquila (boolean EsMaquila)
	{
		set_Value (COLUMNNAME_EsMaquila, Boolean.valueOf(EsMaquila));
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

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (int GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, Integer.valueOf(GrandTotal));
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public int getGrandTotal () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GrandTotal);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** IMEI AD_Reference_ID=1000061 */
	public static final int IMEI_AD_Reference_ID=1000061;
	/** En reparacion = 357404050133175 */
	public static final String IMEI_EnReparacion = "357404050133175";
	/** Jeferson Amoyado = 357404050126518 */
	public static final String IMEI_JefersonAmoyado = "357404050126518";
	/** Kevin Maldonado = 357404050066342 */
	public static final String IMEI_KevinMaldonado = "357404050066342";
	/** Magaly Ramírez = 357404050197261 */
	public static final String IMEI_MagalyRamírez = "357404050197261";
	/** Alejandro Farfán = 357404050196628 */
	//public static final String IMEI_AlejandroFarfán = "357404050196628";
	/** Luis Chavez = 357404050196453 */
	public static final String IMEI_LuisChavez = "357404050196453";
	/** Kevin Maldonado Nuñez = 357404050133696 */
	//public static final String IMEI_KevinMaldonadoNuñez = "357404050133696";
	/** Cesar García = 357404050133498 */
	public static final String IMEI_CesarGarcía = "357404050133498";
	/** Jose Cepeda = 357404050132631 */
	public static final String IMEI_JoseCepeda = "357404050132631";
	/** Jefferson Amoyado = 357404050107641 */
	public static final String IMEI_JeffersonAmoyado = "357404050107641";
	/** Luis Martinez = 357404050092074 */
	public static final String IMEI_LuisMartinez = "357404050092074";
	/** Rolando Bellevue = 357404050361503 */
	public static final String IMEI_RolandoBellevue = "357404050361503";
	/** Karen Morales = 357404050363640 */
	public static final String IMEI_KarenMorales = "357404050363640";
	/** Lilian Contreras = 357404050363327 */
	public static final String IMEI_LilianContreras = "357404050363327";
	/** Nibaldo Martínez = 357404050363509 */
	public static final String IMEI_NibaldoMartínez = "357404050363509";
	/** Daniel Núñez = 357404050120248 */
	//public static final String IMEI_DanielNúñez = "357404050120248";
	/** Roberto Umaña = 868199020377595 */
	//public static final String IMEI_RobertoUmaña = "868199020377595";
	/** Michael Herrera = 868199020380193 */
	public static final String IMEI_MichaelHerrera = "868199020380193";
	/** Picking Problema = 868199020379476 */
	public static final String IMEI_PickingProblema = "868199020379476";
	/** Sergio Muñoz = 357404050581811 */
	//public static final String IMEI_SergioMuñoz = "357404050581811";
	/** Felipe Escanilla = 357404050579914 */
	//public static final String IMEI_FelipeEscanilla = "357404050579914";
	/** Armando Adriazola = 357404050581720 */
	public static final String IMEI_ArmandoAdriazola = "357404050581720";
	/** Sony Cadeus = 357404050541617 */
	public static final String IMEI_SonyCadeus = "357404050541617";
	/** Picking Completo = 357404050542516 */
	public static final String IMEI_PickingCompleto = "357404050542516";
	/** Manuel Bolívar = 357404050120347 */
	public static final String IMEI_ManuelBolívar = "357404050120347";
	/** Disponible (2) = 357404050084410 */
	public static final String IMEI_Disponible2 = "357404050084410";
	/** PDA NUEVA1 = 357404050078925 */
	public static final String IMEI_PDANUEVA1 = "357404050078925";
	/** PDA Nueva2 = 357404050033888 */
	public static final String IMEI_PDANueva2 = "357404050033888";
	/** PDA Nueva3 = 357104050119752 */
	public static final String IMEI_PDANueva3 = "357104050119752";
	/** PDA Prestada1 = 357917080121357 */
	public static final String IMEI_PDAPrestada1 = "357917080121357";
	/** Jeffrey Gonzalez = 357404050138174 */
	public static final String IMEI_JeffreyGonzalez = "357404050138174";
	/** Jesus Suarez = 357104050579914 */
	public static final String IMEI_JesusSuarez = "357104050579914";
	/** Felipe Escanilla = 357104050078925 */
	public static final String IMEI_FelipeEscanilla = "357104050078925";
	/** BARTECH = 990011940818163 */
	public static final String IMEI_BARTECH = "990011940818163";
	/** Set IMEI.
		@param IMEI IMEI	  */
	public void setIMEI (String IMEI)
	{

		set_Value (COLUMNNAME_IMEI, IMEI);
	}

	/** Get IMEI.
		@return IMEI	  */
	public String getIMEI () 
	{
		return (String)get_Value(COLUMNNAME_IMEI);
	}

	/** ISWS AD_Reference_ID=319 */
	public static final int ISWS_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISWS_Yes = "Y";
	/** No = N */
	public static final String ISWS_No = "N";
	/** Set ISWS.
		@param ISWS ISWS	  */
	public void setISWS (String ISWS)
	{

		set_Value (COLUMNNAME_ISWS, ISWS);
	}

	/** Get ISWS.
		@return ISWS	  */
	public String getISWS () 
	{
		return (String)get_Value(COLUMNNAME_ISWS);
	}

	/*public I_M_Camion getM_Camion() throws RuntimeException
    {
		return (I_M_Camion)MTable.get(getCtx(), I_M_Camion.Table_Name)
			.getPO(getM_Camion_ID(), get_TrxName());	}*/

	/** Set M_Camion_ID.
		@param M_Camion_ID M_Camion_ID	  */
	public void setM_Camion_ID (int M_Camion_ID)
	{
		if (M_Camion_ID < 1) 
			set_Value (COLUMNNAME_M_Camion_ID, null);
		else 
			set_Value (COLUMNNAME_M_Camion_ID, Integer.valueOf(M_Camion_ID));
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

	/*public I_M_CHOFER getM_CHOFER() throws RuntimeException
    {
		return (I_M_CHOFER)MTable.get(getCtx(), I_M_CHOFER.Table_Name)
			.getPO(getM_CHOFER_ID(), get_TrxName());	}*/

	/** Set M_CHOFER.
		@param M_CHOFER_ID M_CHOFER	  */
	public void setM_CHOFER_ID (int M_CHOFER_ID)
	{
		if (M_CHOFER_ID < 1) 
			set_Value (COLUMNNAME_M_CHOFER_ID, null);
		else 
			set_Value (COLUMNNAME_M_CHOFER_ID, Integer.valueOf(M_CHOFER_ID));
	}

	/** Get M_CHOFER.
		@return M_CHOFER	  */
	public int getM_CHOFER_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CHOFER_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/*public I_M_Conductor getM_Conductor() throws RuntimeException
    {
		return (I_M_Conductor)MTable.get(getCtx(), I_M_Conductor.Table_Name)
			.getPO(getM_Conductor_ID(), get_TrxName());	}*/

	/** Set M_Conductor_ID.
		@param M_Conductor_ID M_Conductor_ID	  */
	public void setM_Conductor_ID (int M_Conductor_ID)
	{
		if (M_Conductor_ID < 1) 
			set_Value (COLUMNNAME_M_Conductor_ID, null);
		else 
			set_Value (COLUMNNAME_M_Conductor_ID, Integer.valueOf(M_Conductor_ID));
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

	/** Set RechazaGT.
		@param RechazaGT RechazaGT	  */
	public void setRechazaGT (String RechazaGT)
	{
		set_Value (COLUMNNAME_RechazaGT, RechazaGT);
	}

	/** Get RechazaGT.
		@return RechazaGT	  */
	public String getRechazaGT () 
	{
		return (String)get_Value(COLUMNNAME_RechazaGT);
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
}