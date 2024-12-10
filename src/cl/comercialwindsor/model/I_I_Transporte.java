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

/** Generated Interface for I_Transporte
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_I_Transporte 
{

    /** TableName=I_Transporte */
    public static final String Table_Name = "I_Transporte";

    /** AD_Table_ID=1000734 */
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

    /** Column name BPartner_Name */
    public static final String COLUMNNAME_BPartner_Name = "BPartner_Name";

	/** Set Business Partner Name.
	  * Business Partner Name
	  */
	public void setBPartner_Name (String BPartner_Name);

	/** Get Business Partner Name.
	  * Business Partner Name
	  */
	public String getBPartner_Name();

    /** Column name BPartner_Value */
    public static final String COLUMNNAME_BPartner_Value = "BPartner_Value";

	/** Set Business Partner Key.
	  * The Key of the Business Partner
	  */
	public void setBPartner_Value (String BPartner_Value);

	/** Get Business Partner Key.
	  * The Key of the Business Partner
	  */
	public String getBPartner_Value();

    /** Column name BPartner3_Name */
    public static final String COLUMNNAME_BPartner3_Name = "BPartner3_Name";

	/** Set Business Partner Name 3.
	  * Business Partner Name 3
	  */
	public void setBPartner3_Name (String BPartner3_Name);

	/** Get Business Partner Name 3.
	  * Business Partner Name 3
	  */
	public String getBPartner3_Name();

    /** Column name BPartner3_Value */
    public static final String COLUMNNAME_BPartner3_Value = "BPartner3_Value";

	/** Set Business Partner Key 3.
	  * The Key of the Business Partner 3
	  */
	public void setBPartner3_Value (String BPartner3_Value);

	/** Get Business Partner Key 3.
	  * The Key of the Business Partner 3
	  */
	public String getBPartner3_Value();

    /** Column name CamionName */
    public static final String COLUMNNAME_CamionName = "CamionName";

	/** Set Camion Name	  */
	public void setCamionName (String CamionName);

	/** Get Camion Name	  */
	public String getCamionName();

    /** Column name CantidadBulto */
    public static final String COLUMNNAME_CantidadBulto = "CantidadBulto";

	/** Set CantidadBulto	  */
	public void setCantidadBulto (BigDecimal CantidadBulto);

	/** Get CantidadBulto	  */
	public BigDecimal getCantidadBulto();

    /** Column name CANTIDADPIONETA */
    public static final String COLUMNNAME_CANTIDADPIONETA = "CANTIDADPIONETA";

	/** Set CANTIDADPIONETA	  */
	public void setCANTIDADPIONETA (BigDecimal CANTIDADPIONETA);

	/** Get CANTIDADPIONETA	  */
	public BigDecimal getCANTIDADPIONETA();

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

	/** Set Business Partner 3.
	  * Identifies a Business Partner 3
	  */
	public void setC_BPartner3_ID (int C_BPartner3_ID);

	/** Get Business Partner 3.
	  * Identifies a Business Partner 3
	  */
	public int getC_BPartner3_ID();

	public I_C_BPartner getC_BPartner3() throws RuntimeException;

    /** Column name Cedible */
    public static final String COLUMNNAME_Cedible = "Cedible";

	/** Set Cedible	  */
	public void setCedible (boolean Cedible);

	/** Get Cedible	  */
	public boolean isCedible();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name COBERTURA */
    public static final String COLUMNNAME_COBERTURA = "COBERTURA";

	/** Set COBERTURA	  */
	public void setCOBERTURA (boolean COBERTURA);

	/** Get COBERTURA	  */
	public boolean isCOBERTURA();

    /** Column name Conductor */
    public static final String COLUMNNAME_Conductor = "Conductor";

	/** Set Conductor	  */
	public void setConductor (String Conductor);

	/** Get Conductor	  */
	public String getConductor();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name DESCRIPTIONLINE */
    public static final String COLUMNNAME_DESCRIPTIONLINE = "DESCRIPTIONLINE";

	/** Set DESCRIPTIONLINE	  */
	public void setDESCRIPTIONLINE (String DESCRIPTIONLINE);

	/** Get DESCRIPTIONLINE	  */
	public String getDESCRIPTIONLINE();

    /** Column name DETALLERETIRACLIENTE */
    public static final String COLUMNNAME_DETALLERETIRACLIENTE = "DETALLERETIRACLIENTE";

	/** Set DETALLERETIRACLIENTE	  */
	public void setDETALLERETIRACLIENTE (String DETALLERETIRACLIENTE);

	/** Get DETALLERETIRACLIENTE	  */
	public String getDETALLERETIRACLIENTE();

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

    /** Column name FABRICS */
    public static final String COLUMNNAME_FABRICS = "FABRICS";

	/** Set FABRICS	  */
	public void setFABRICS (boolean FABRICS);

	/** Get FABRICS	  */
	public boolean isFABRICS();

    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/** Set Import Error Message.
	  * Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg);

	/** Get Import Error Message.
	  * Messages generated from import process
	  */
	public String getI_ErrorMsg();

    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/** Set Imported.
	  * Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported);

	/** Get Imported.
	  * Has this import been processed
	  */
	public boolean isI_IsImported();

    /** Column name INOUTNO */
    public static final String COLUMNNAME_INOUTNO = "INOUTNO";

	/** Set INOUTNO	  */
	public void setINOUTNO (String INOUTNO);

	/** Get INOUTNO	  */
	public String getINOUTNO();

    /** Column name InvoiceDocumentNo */
    public static final String COLUMNNAME_InvoiceDocumentNo = "InvoiceDocumentNo";

	/** Set Invoice Document No.
	  * Document Number of the Invoice
	  */
	public void setInvoiceDocumentNo (String InvoiceDocumentNo);

	/** Get Invoice Document No.
	  * Document Number of the Invoice
	  */
	public String getInvoiceDocumentNo();

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

    /** Column name I_Transporte_ID */
    public static final String COLUMNNAME_I_Transporte_ID = "I_Transporte_ID";

	/** Set Import Transporte	  */
	public void setI_Transporte_ID (int I_Transporte_ID);

	/** Get Import Transporte	  */
	public int getI_Transporte_ID();

    /** Column name M_Camion_ID */
    public static final String COLUMNNAME_M_Camion_ID = "M_Camion_ID";

	/** Set M_Camion_ID	  */
	public void setM_Camion_ID (int M_Camion_ID);

	/** Get M_Camion_ID	  */
	public int getM_Camion_ID();

	//public cl.comercialwindsor.model.I_M_Camion getM_Camion() throws RuntimeException;

    /** Column name M_Conductor_ID */
    public static final String COLUMNNAME_M_Conductor_ID = "M_Conductor_ID";

	/** Set M_Conductor_ID	  */
	public void setM_Conductor_ID (int M_Conductor_ID);

	/** Get M_Conductor_ID	  */
	public int getM_Conductor_ID();

	//public cl.comercialwindsor.model.I_M_Conductor getM_Conductor() throws RuntimeException;

    /** Column name M_InOut_ID */
    public static final String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/** Set Shipment/Receipt.
	  * Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID);

	/** Get Shipment/Receipt.
	  * Material Shipment Document
	  */
	public int getM_InOut_ID();

	public I_M_InOut getM_InOut() throws RuntimeException;

    /** Column name M_Transporte_ID */
    public static final String COLUMNNAME_M_Transporte_ID = "M_Transporte_ID";

	/** Set M_Transporte_ID	  */
	public void setM_Transporte_ID (int M_Transporte_ID);

	/** Get M_Transporte_ID	  */
	public int getM_Transporte_ID();

	//public cl.comercialwindsor.model.I_M_Transporte getM_Transporte() throws RuntimeException;

    /** Column name M_TransporteLine_ID */
    public static final String COLUMNNAME_M_TransporteLine_ID = "M_TransporteLine_ID";

	/** Set M_TransporteLine_ID	  */
	public void setM_TransporteLine_ID (int M_TransporteLine_ID);

	/** Get M_TransporteLine_ID	  */
	public int getM_TransporteLine_ID();

	//public cl.comercialwindsor.model.I_M_TransporteLine getM_TransporteLine() throws RuntimeException;

    /** Column name ORDERNO */
    public static final String COLUMNNAME_ORDERNO = "ORDERNO";

	/** Set ORDERNO	  */
	public void setORDERNO (String ORDERNO);

	/** Get ORDERNO	  */
	public String getORDERNO();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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

    /** Column name VALORVIAJE */
    public static final String COLUMNNAME_VALORVIAJE = "VALORVIAJE";

	/** Set VALORVIAJE	  */
	public void setVALORVIAJE (BigDecimal VALORVIAJE);

	/** Get VALORVIAJE	  */
	public BigDecimal getVALORVIAJE();
}
