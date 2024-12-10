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

/** Generated Interface for M_TransporteLine
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_M_TransporteLine 
{

    /** TableName=M_TransporteLine */
    public static final String Table_Name = "M_TransporteLine";

    /** AD_Table_ID=1000489 */
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

    /** Column name CantidadBulto */
    public static final String COLUMNNAME_CantidadBulto = "CantidadBulto";

	/** Set CantidadBulto	  */
	public void setCantidadBulto (BigDecimal CantidadBulto);

	/** Get CantidadBulto	  */
	public BigDecimal getCantidadBulto();

    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/** Set City.
	  * City
	  */
	public void setC_City_ID (int C_City_ID);

	/** Get City.
	  * City
	  */
	public int getC_City_ID();

	public I_C_City getC_City() throws RuntimeException;

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

    /** Column name DETALLEFABRICS */
    public static final String COLUMNNAME_DETALLEFABRICS = "DETALLEFABRICS";

	/** Set DETALLEFABRICS	  */
	public void setDETALLEFABRICS (String DETALLEFABRICS);

	/** Get DETALLEFABRICS	  */
	public String getDETALLEFABRICS();

    /** Column name FABRICS */
    public static final String COLUMNNAME_FABRICS = "FABRICS";

	/** Set FABRICS	  */
	public void setFABRICS (boolean FABRICS);

	/** Get FABRICS	  */
	public boolean isFABRICS();

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
	public void setISWS (boolean ISWS);

	/** Get ISWS	  */
	public boolean isWS();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

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

    /** Column name MotivoDevolucion */
    public static final String COLUMNNAME_MotivoDevolucion = "MotivoDevolucion";

	/** Set MotivoDevolucion	  */
	public void setMotivoDevolucion (String MotivoDevolucion);

	/** Get MotivoDevolucion	  */
	public String getMotivoDevolucion();

    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/** Set Movement Date.
	  * Date a product was moved in or out of inventory
	  */
	public void setMovementDate (Timestamp MovementDate);

	/** Get Movement Date.
	  * Date a product was moved in or out of inventory
	  */
	public Timestamp getMovementDate();

    /** Column name M_ServicioTransporte_ID */
    public static final String COLUMNNAME_M_ServicioTransporte_ID = "M_ServicioTransporte_ID";

	/** Set M_ServicioTransporte_ID	  */
	public void setM_ServicioTransporte_ID (int M_ServicioTransporte_ID);

	/** Get M_ServicioTransporte_ID	  */
	public int getM_ServicioTransporte_ID();

	//public I_M_ServicioTransporte getM_ServicioTransporte() throws RuntimeException;

    /** Column name M_Transporte_ID */
    public static final String COLUMNNAME_M_Transporte_ID = "M_Transporte_ID";

	/** Set M_Transporte_ID	  */
	public void setM_Transporte_ID (int M_Transporte_ID);

	/** Get M_Transporte_ID	  */
	public int getM_Transporte_ID();

	public I_M_Transporte getM_Transporte() throws RuntimeException;

    /** Column name M_TransporteLine_ID */
    public static final String COLUMNNAME_M_TransporteLine_ID = "M_TransporteLine_ID";

	/** Set M_TransporteLine_ID	  */
	public void setM_TransporteLine_ID (int M_TransporteLine_ID);

	/** Get M_TransporteLine_ID	  */
	public int getM_TransporteLine_ID();

    /** Column name NOBOLETAT */
    public static final String COLUMNNAME_NOBOLETAT = "NOBOLETAT";

	/** Set NOBOLETAT	  */
	public void setNOBOLETAT (String NOBOLETAT);

	/** Get NOBOLETAT	  */
	public String getNOBOLETAT();

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

    /** Column name PRODUCTVALUES */
    public static final String COLUMNNAME_PRODUCTVALUES = "PRODUCTVALUES";

	/** Set PRODUCTVALUES	  */
	public void setPRODUCTVALUES (String PRODUCTVALUES);

	/** Get PRODUCTVALUES	  */
	public String getPRODUCTVALUES();

    /** Column name ResultadoTransporte */
    public static final String COLUMNNAME_ResultadoTransporte = "ResultadoTransporte";

	/** Set ResultadoTransporte	  */
	public void setResultadoTransporte (String ResultadoTransporte);

	/** Get ResultadoTransporte	  */
	public String getResultadoTransporte();

    /** Column name Retira */
    public static final String COLUMNNAME_Retira = "Retira";

	/** Set Retira	  */
	public void setRetira (boolean Retira);

	/** Get Retira	  */
	public boolean isRetira();

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
	public void setVALORVIAJE (int VALORVIAJE);

	/** Get VALORVIAJE	  */
	public int getVALORVIAJE();
}
