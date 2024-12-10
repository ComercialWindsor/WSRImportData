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
package cl.comercialwindsor.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for M_Transporte
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_M_Transporte 
{

    /** TableName=M_Transporte */
    public static final String Table_Name = "M_Transporte";

    /** AD_Table_ID=1000488 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name BOTON */
    public static final String COLUMNNAME_BOTON = "BOTON";

	/** Set BOTON	  */
	public void setBOTON (String BOTON);

	/** Get BOTON	  */
	public String getBOTON();

    /** Column name BotonCobertura */
    public static final String COLUMNNAME_BotonCobertura = "BotonCobertura";

	/** Set BotonCobertura	  */
	public void setBotonCobertura (String BotonCobertura);

	/** Get BotonCobertura	  */
	public String getBotonCobertura();

    /** Column name CANTIDADPIONETA */
    public static final String COLUMNNAME_CANTIDADPIONETA = "CANTIDADPIONETA";

	/** Set CANTIDADPIONETA	  */
	public void setCANTIDADPIONETA (int CANTIDADPIONETA);

	/** Get CANTIDADPIONETA	  */
	public int getCANTIDADPIONETA();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner3_ID */
    public static final String COLUMNNAME_C_BPartner3_ID = "C_BPartner3_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner3_ID (int C_BPartner3_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner3_ID();

	public I_C_BPartner getC_BPartner3() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DETALLERETIRACLIENTE */
    public static final String COLUMNNAME_DETALLERETIRACLIENTE = "DETALLERETIRACLIENTE";

	/** Set DETALLERETIRACLIENTE	  */
	public void setDETALLERETIRACLIENTE (String DETALLERETIRACLIENTE);

	/** Get DETALLERETIRACLIENTE	  */
	public String getDETALLERETIRACLIENTE();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name EsMaquila */
    public static final String COLUMNNAME_EsMaquila = "EsMaquila";

	/** Set EsMaquila	  */
	public void setEsMaquila (boolean EsMaquila);

	/** Get EsMaquila	  */
	public boolean isEsMaquila();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (int GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public int getGrandTotal();

    /** Column name IMEI */
    public static final String COLUMNNAME_IMEI = "IMEI";

	/** Set IMEI	  */
	public void setIMEI (String IMEI);

	/** Get IMEI	  */
	public String getIMEI();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name ISWS */
    public static final String COLUMNNAME_ISWS = "ISWS";

	/** Set ISWS	  */
	public void setISWS (String ISWS);

	/** Get ISWS	  */
	public String getISWS();

    /** Column name M_Camion_ID */
    public static final String COLUMNNAME_M_Camion_ID = "M_Camion_ID";

	/** Set M_Camion_ID	  */
	public void setM_Camion_ID (int M_Camion_ID);

	/** Get M_Camion_ID	  */
	public int getM_Camion_ID();

	//public I_M_Camion getM_Camion() throws RuntimeException;

    /** Column name M_CHOFER_ID */
    public static final String COLUMNNAME_M_CHOFER_ID = "M_CHOFER_ID";

	/** Set M_CHOFER	  */
	public void setM_CHOFER_ID (int M_CHOFER_ID);

	/** Get M_CHOFER	  */
	public int getM_CHOFER_ID();

	//public I_M_CHOFER getM_CHOFER() throws RuntimeException;

    /** Column name M_Conductor_ID */
    public static final String COLUMNNAME_M_Conductor_ID = "M_Conductor_ID";

	/** Set M_Conductor_ID	  */
	public void setM_Conductor_ID (int M_Conductor_ID);

	/** Get M_Conductor_ID	  */
	public int getM_Conductor_ID();

	//public I_M_Conductor getM_Conductor() throws RuntimeException;

    /** Column name M_Transporte_ID */
    public static final String COLUMNNAME_M_Transporte_ID = "M_Transporte_ID";

	/** Set M_Transporte_ID	  */
	public void setM_Transporte_ID (int M_Transporte_ID);

	/** Get M_Transporte_ID	  */
	public int getM_Transporte_ID();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name RechazaGT */
    public static final String COLUMNNAME_RechazaGT = "RechazaGT";

	/** Set RechazaGT	  */
	public void setRechazaGT (String RechazaGT);

	/** Get RechazaGT	  */
	public String getRechazaGT();

    /** Column name RetiraCliente */
    public static final String COLUMNNAME_RetiraCliente = "RetiraCliente";

	/** Set RetiraCliente	  */
	public void setRetiraCliente (boolean RetiraCliente);

	/** Get RetiraCliente	  */
	public boolean isRetiraCliente();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
