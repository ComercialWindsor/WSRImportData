/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package cl.comercialwindsor.process;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.Properties;
import java.util.logging.Level;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.soap.SOAPBinding;

import org.compiere.util.*;
import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.ofb.model.OFBForward;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.InputStream;
 








import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.codec.binary.Base64; 

/**
 *	Generate XML Consolidated from Invoice (Generic) 
 *	
 *  @author Italo Niï¿½oles ininoles
 *  @version $Id: ExportDTEInvoiceCG.java,v 1.2 19/05/2011 $
 */
public class ExportDTEInOutFOLOrg extends SvrProcess
{	
	/** Properties						*/
	private Properties 		m_ctx;	
	private int p_M_InOut_ID = 0;
	public String urlPdf = "";
	private String mylog;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		p_M_InOut_ID=getRecord_ID();
		m_ctx = Env.getCtx();
	}	//	prepare

	
	/**
	 * 	Create Shipment
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		MInOut inout=new MInOut(m_ctx,p_M_InOut_ID,get_TrxName());
		String msg=CreateXMLCG(inout);
		
		return msg;
	}	//	doIt
	
	
	
	public String CreateXMLCG(MInOut inout) throws SQLException
    {
		
		
		String wsRespuesta = "";
        MDocType doc = new MDocType(inout.getCtx(), inout.getC_DocType_ID(), inout.get_TrxName());
        //mediante este check se cambiaran los valores de los tag o se obviaran algunos valores
        MOrg company = MOrg.get(inout.getCtx(), inout.getAD_Org_ID());
        if(doc.get_Value("CreateXML") == null)
            return "";
        if(!((Boolean)doc.get_Value("CreateXML")).booleanValue())
            return "";
        int typeDoc = Integer.parseInt(doc.get_Value("DocumentNo")!=null?(String)doc.get_Value("DocumentNo"):"0");
        if(typeDoc == 0)
            return "";
        mylog = new String();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, "integracion", null);//implementation.createDocument(null, "DTE", null);//builder.newDocument();//
            //document.setXmlVersion("1.0");            
            document.setTextContent("text/xml");
            /*Attr atr = document.createAttribute("xmlns");
            atr.setValue("http://www.sii.cl/SiiDte");*/
            Element DTE = document.createElement("DTE");
            document.getDocumentElement().appendChild(DTE);
            DTE.setAttribute("version", "1.0");
            
    		 Element Documento = document.createElement("Documento");
             DTE.appendChild(Documento);//document.getDocumentElement().appendChild(Documento);
             Documento.setAttribute("ID", (new StringBuilder()).append("F").append(inout.getDocumentNo()).append("T").append((String)doc.get_Value("DocumentNo")).toString());
            
            
            document = llenarEncabezado(document, Documento, inout, company, doc, typeDoc);

            document = llenarDetalle(document, Documento, inout);
            
            document = llenarReferencias(document, Documento, inout, company);
            
            
            /*
            */
            mylog = "archivo";
            
            
	          String ExportDir = (String)company.get_Value("ExportDir");
	          try
	          {
	        	  File theDir = new File(ExportDir);
	        	  if (!theDir.exists())
	        		  ExportDir = (String)company.get_Value("ExportDir2"); 
	          }
	          catch(Exception e)
	          {
	        	  throw new AdempiereException("no existe directorio");
	          }
	          
	          ExportDir = ExportDir.replace("\\", "/");
	          javax.xml.transform.Source source = new DOMSource(document);
	          javax.xml.transform.Result result = new StreamResult(new File(ExportDir, (new StringBuilder()).append(inout.getDocumentNo()).append(".xml").toString()));
	          //javax.xml.transform.Result console = new StreamResult(System.out);
	          Transformer transformer = TransformerFactory.newInstance().newTransformer();
	          transformer.setOutputProperty("indent", "yes");
	          transformer.setOutputProperty("encoding", "ISO-8859-1");
	          transformer.transform(source, result);
	          
	          commitEx();
	          
	        
    	}
        catch (ParserConfigurationException pce) 
        {
    	      pce.printStackTrace();
              return (new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(pce.getMessage()).toString();
        }
        catch (TransformerException tfe) 
        {
          tfe.printStackTrace();
          return (new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(tfe.getMessage()).toString();
        }
        catch(Exception e)
        {
            log.severe((new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString());
            log.severe(e.toString());
            return (new StringBuilder()).append("CreateXML: ").append(mylog).append("--").append(e.getMessage()).toString();
        }           
        commitEx();
        return "XML CG Generated "+ wsRespuesta;
    }


	
    
	private String reemplazarCaracteresEspeciales(String palabra) {
        palabra = palabra.replace("ñ", "n");palabra = palabra.replace("Ñ", "N");palabra = palabra.replace("|", "");
        palabra = palabra.replace("à", "a"); palabra = palabra.replace("á", "a"); palabra = palabra.replace("À", "A"); palabra = palabra.replace("Á", "A");
        palabra = palabra.replace("è", "e"); palabra = palabra.replace("é", "e"); palabra = palabra.replace("È", "E"); palabra = palabra.replace("É", "E");
        palabra = palabra.replace("ì", "i"); palabra = palabra.replace("í", "i"); palabra = palabra.replace("Ì", "I"); palabra = palabra.replace("Í", "I");
        palabra = palabra.replace("ò", "o"); palabra = palabra.replace("ó", "o"); palabra = palabra.replace("Ò", "O"); palabra = palabra.replace("Ó", "O");
        palabra = palabra.replace("ù", "u"); palabra = palabra.replace("ú", "u"); palabra = palabra.replace("Ù", "U"); palabra = palabra.replace("Ú", "U");
        palabra = palabra.replace("\b", ""); palabra = palabra.replace("/", ""); palabra = palabra.replace(":", ""); palabra = palabra.replace("<", "");
        palabra = palabra.replace("*", ""); palabra = palabra.replace("?", ""); palabra = palabra.replace("'", ""); palabra = palabra.replace(">", "");

        return palabra;

    }
	
	private String truncate(String text, int maxSize) {
		
		if(text==null)
			return "";
		if(maxSize<=0){
			return text;
		}else{
			//delete all the accents and ñ from the string
			//text = reemplazarCaracteresEspeciales(text);//Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");//.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			return reemplazarCaracteresEspeciales(text.substring(0, Math.min(text.length(), maxSize)));
		}
	}
	
	public Document llenarEncabezado(Document document, Element Documento, MInOut inout, MOrg company, MDocType doc,int typeDoc){
		

        
		 Element Encabezado = document.createElement("Encabezado");
         Documento.appendChild(Encabezado);
         Element IdDoc = document.createElement("IdDoc");
         Encabezado.appendChild(IdDoc);
         mylog = "IdDoc";
         Element TipoDTE = document.createElement("TipoDTE");
         org.w3c.dom.Text text = document.createTextNode(truncate(Integer.toString(typeDoc),3));
         TipoDTE.setTextContent(text.getTextContent());//TipoDTE.appendChild(text);
         IdDoc.appendChild(TipoDTE);
         Element Folio = document.createElement("Folio");
         org.w3c.dom.Text fo = document.createTextNode(truncate(inout.getDocumentNo(),10));
         Folio.appendChild(fo);
         IdDoc.appendChild(Folio);
         Element FchEmis = document.createElement("FchEmis");
         org.w3c.dom.Text emis = document.createTextNode(inout.getMovementDate().toString().substring(0, 10));
         FchEmis.appendChild(emis);
         IdDoc.appendChild(FchEmis);
         
         
         // Tipo Despacho
         
         //IndTraslado
         
         //TpoTranVenta
         
         //FmaPago
         
         
         Element FchVenc = document.createElement("FchVenc");
         org.w3c.dom.Text venc = document.createTextNode(inout.getMovementDate().toString().substring(0, 10));
         FchVenc.appendChild(venc);
         IdDoc.appendChild(FchVenc);
         
         
         
         //--------------------------------------------------------EMISOR---------------------------------------------------------------//
         Element Emisor = document.createElement("Emisor");
         Encabezado.appendChild(Emisor);
         mylog = "Emisor";
         Element Rut = document.createElement("RUTEmisor");
         org.w3c.dom.Text rut = document.createTextNode(truncate((String)company.get_Value("Rut"),10));
         Rut.appendChild(rut);
         Emisor.appendChild(Rut);
         //ininoles validacion nuevo nombre razon social
         String nameRzn = company.getDescription();
         if (nameRzn == null)
         {
         	nameRzn = " ";
         }
         nameRzn = nameRzn.trim();
         if (nameRzn.length() < 2)
         	nameRzn = company.getName();
         //ininoles end            
         nameRzn = nameRzn.replace("'", "");
         nameRzn = nameRzn.replace("\"", "");
         Element RznSoc = document.createElement("RznSoc");//RznSocEmisor
         org.w3c.dom.Text rzn = document.createTextNode(truncate(nameRzn,100));
         RznSoc.appendChild(rzn);
         Emisor.appendChild(RznSoc);            
         String giroEmisStr = (String)company.get_Value("Giro");
         giroEmisStr = giroEmisStr.replace("'", "");
         giroEmisStr = giroEmisStr.replace("\"", "");
         Element GiroEmis = document.createElement("GiroEmis");
         org.w3c.dom.Text gi = document.createTextNode(truncate(giroEmisStr,10));
         GiroEmis.appendChild(gi);
         Emisor.appendChild(GiroEmis);
         
         Element Acteco = document.createElement("Acteco");
	            org.w3c.dom.Text teco = document.createTextNode(truncate((String)company.get_Value("Acteco"),6));
	            Acteco.appendChild(teco);
	            Emisor.appendChild(Acteco);	        	
	        
         Element DirOrigen = document.createElement("DirOrigen");
         org.w3c.dom.Text dir = document.createTextNode(truncate((String)company.get_Value("Address1"),60));
         DirOrigen.appendChild(dir);
         Emisor.appendChild(DirOrigen);
         
         Element CmnaOrigen = document.createElement("CmnaOrigen");
         org.w3c.dom.Text com = document.createTextNode(truncate((String)company.get_Value("Comuna"),20));
         CmnaOrigen.appendChild(com);
         Emisor.appendChild(CmnaOrigen);
         Element CiudadOrigen = document.createElement("CiudadOrigen");
         org.w3c.dom.Text city = document.createTextNode(truncate((String)company.get_Value("City"),20));
         CiudadOrigen.appendChild(city);
         Emisor.appendChild(CiudadOrigen);
         
         

         //--------------------------------------------------------Receptor---------------------------------------------------------------//
         mylog = "receptor";
         MBPartner BP = new MBPartner(inout.getCtx(), inout.getC_BPartner_ID(), inout.get_TrxName());
         MBPartnerLocation bloc = new MBPartnerLocation(inout.getCtx(), inout.getC_BPartner_Location_ID(), inout.get_TrxName());
         Element Receptor = document.createElement("Receptor");
         Encabezado.appendChild(Receptor);
         Element RUTRecep = document.createElement("RUTRecep");
         String vRut = (new StringBuilder()).append(BP.getValue()).append("-").append(BP.get_ValueAsString("Digito")).toString();
         if(BP.get_ID()==1000003)
         	vRut="66666666-6";
         org.w3c.dom.Text rutc = document.createTextNode(truncate(vRut,10));
         RUTRecep.appendChild(rutc);
         Receptor.appendChild(RUTRecep);
         
         String RznSocRecepStr = BP.getName();
         RznSocRecepStr = RznSocRecepStr.replace("'", "");
         RznSocRecepStr = RznSocRecepStr.replace("\"", "");
         Element RznSocRecep = document.createElement("RznSocRecep");
         org.w3c.dom.Text RznSocR = document.createTextNode(truncate(RznSocRecepStr,40));
         RznSocRecep.appendChild(RznSocR);
         Receptor.appendChild(RznSocRecep);
         
         
         Element GiroRecep = document.createElement("GiroRecep");
         org.w3c.dom.Text giro = document.createTextNode(truncate(BP.get_Value("Name2")!=null?(String)BP.get_Value("Name2"):"",40));
         GiroRecep.appendChild(giro);
         Receptor.appendChild(GiroRecep);	
         
         
         String dirRecepStr = bloc.getLocation(true).getAddress1();
         if(dirRecepStr.isEmpty())
        	 throw new AdempiereException("direccion del cliente vacia");
         dirRecepStr = dirRecepStr.replace("'", "");
         dirRecepStr = dirRecepStr.replace("\"", "");
         Element DirRecep = document.createElement("DirRecep");
         org.w3c.dom.Text dirr = document.createTextNode(truncate(dirRecepStr,70));
         DirRecep.appendChild(dirr);
         Receptor.appendChild(DirRecep);
         
         if (bloc.getLocation(true).getC_City_ID()>0)
         {
	            Element CmnaRecep = document.createElement("CmnaRecep");
	            org.w3c.dom.Text Cmna = document.createTextNode(truncate(MCity.get(inout.getCtx(), bloc.getLocation(true).getC_City_ID()).getName(),20));
	            CmnaRecep.appendChild(Cmna);
	            Receptor.appendChild(CmnaRecep);
         }
         else
         {
         	Element CmnaRecep = document.createElement("CmnaRecep");
	            org.w3c.dom.Text Cmna = document.createTextNode(truncate(bloc.getLocation(true).getAddress2(),20));
	            CmnaRecep.appendChild(Cmna);
	            Receptor.appendChild(CmnaRecep);
         }            
         //if(bloc.getLocation(true).getAddress2()!=null && bloc.getLocation(true).getAddress2().length()>0 ){
	          //  Element CmnaRecep = document.createElement("CmnaRecep");
	          //  org.w3c.dom.Text Cmna = document.createTextNode(bloc.getLocation(true).getAddress2());
	          //  CmnaRecep.appendChild(Cmna);
	          //  Receptor.appendChild(CmnaRecep);
         //}
         
         Element CiudadRecep = document.createElement("CiudadRecep");
         org.w3c.dom.Text reg = document.createTextNode(truncate(bloc.getLocation(true).getC_City_ID()>0?MCity.get(inout.getCtx(), bloc.getLocation(true).getC_City_ID()).getC_Region().getName():"Metropolitana",20));
         CiudadRecep.appendChild(reg);
         Receptor.appendChild(CiudadRecep);
         

         //--------------------------------------------------------Totales---------------------------------------------------------------//
         
         mylog = "Totales";
         Element Totales = document.createElement("Totales");
         Encabezado.appendChild(Totales);
         BigDecimal amountex = DB.getSQLValueBD(inout.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.taxbaseamt),0),0) from C_OrderTax il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='Y' and il.C_Order_ID=").append(inout.getC_Order_ID()).toString());
         BigDecimal amountNeto = DB.getSQLValueBD(inout.get_TrxName(), (new StringBuilder()).append("select Round(COALESCE(SUM(il.taxbaseamt),0),0) from C_OrderTax il  inner join C_Tax t on (il.C_Tax_ID=t.C_Tax_ID) and t.istaxexempt='N' and il.C_Order_ID=").append(inout.getC_Order_ID()).toString());
         
         
	        Element MntNeto = document.createElement("MntNeto");
	        org.w3c.dom.Text neto = document.createTextNode(amountNeto!=null?amountNeto.toString():"0");
	        MntNeto.appendChild(neto);
	        Totales.appendChild(MntNeto);
         
         
         Element MntExe = document.createElement("MntExe");
         org.w3c.dom.Text exe = document.createTextNode(amountex != null ? amountex.toString() : "0");
         MntExe.appendChild(exe);
         Totales.appendChild(MntExe);
         
         
         Element MntTotal = document.createElement("MntTotal");
         org.w3c.dom.Text total = document.createTextNode(inout.getC_Order().getGrandTotal().setScale(0, 4).toString()); //inout.getGrandTotal().setScale(0, 4).toString()
         MntTotal.appendChild(total);
         Totales.appendChild(MntTotal);
		
		return document;
	}

	private Document llenarDetalle(Document document, Element Documento, MInOut inout) {
		
		mylog = "detalle";
        MInOutLine iLines[] = inout.getLines(false);
        int lineInvoice = 0;
        for(int i = 0; i < iLines.length; i++)
        {	
        	MInOutLine iLine = iLines[i];
        	if((iLine.getM_Product_ID()==0 && iLine.getC_Charge_ID()==0))
        		continue;

             //logica de los descuentos en la factura
            if(iLine.getMovementQty().compareTo(BigDecimal.ZERO)<=0) {
            	continue;
            }
            Element Detalle = document.createElement("Detalle");
            Documento.appendChild(Detalle);
            
            //MTax tax=new MTax(invoice.getCtx() ,iLine.getC_Tax_ID(),invoice.get_TrxName() );
            // ininoles se saca indice exento
            //if(tax.isTaxExempt()){
            	// Element IndEx = document.createElement("IndExe");
                // org.w3c.dom.Text lineE = document.createTextNode("1");
                // IndEx.appendChild(lineE);
                // Detalle.appendChild(IndEx);	
            //}
            //
            lineInvoice = lineInvoice+1;              
            Element NroLinDet = document.createElement("NroLinDet");
            org.w3c.dom.Text line = document.createTextNode(Integer.toString(lineInvoice));
            NroLinDet.appendChild(line);
            Detalle.appendChild(NroLinDet);
            String detailValue = "";
            String pname="";
            if(iLine.getProduct()!=null ){
            	pname=iLine.getProduct().getName();
            	detailValue = iLine.getProduct().getValue();
            }
            else{
            	pname=iLine.getC_Charge().getName();
            	detailValue = iLine.getC_Charge().getName();
            }
            
            Element CdgItem = document.createElement("CdgItem");
            Element TpoCodigo = document.createElement("TpoCodigo");
            Element VlrCodigo = document.createElement("VlrCodigo");   
            org.w3c.dom.Text tpoCodigo = document.createTextNode("INT");
            TpoCodigo.appendChild(tpoCodigo);
            org.w3c.dom.Text vlrCodigo = document.createTextNode(truncate(detailValue,10));
            VlrCodigo.appendChild(vlrCodigo);                
            CdgItem.appendChild(TpoCodigo);
            CdgItem.appendChild(VlrCodigo);                
            Detalle.appendChild(CdgItem);
            
            Element NmbItem = document.createElement("NmbItem");
            
            
            pname = pname.replace("'", "");
            pname = pname.replace("\"", "");
            org.w3c.dom.Text Item = document.createTextNode(truncate(pname,80));
            NmbItem.appendChild(Item);
            Detalle.appendChild(NmbItem);
            
            if (iLine.getDescription() != null && iLine.getDescription() != "")
            {
                Element DscItem = document.createElement("DscItem");
                org.w3c.dom.Text desc = document.createTextNode(iLine.getDescription()==null?" ":truncate(iLine.getDescription(),1000));
                DscItem.appendChild(desc);
                Detalle.appendChild(DscItem);
            }
            Element QtyItem = document.createElement("QtyItem");
            org.w3c.dom.Text qt = document.createTextNode(iLine.getMovementQty().toString());
            QtyItem.appendChild(qt);
            Detalle.appendChild(QtyItem);
                            
            String unmdItemStr = "";
            if(iLine.getM_Product_ID() > 0)
            	unmdItemStr = iLine.getM_Product().getC_UOM().getUOMSymbol();
            else
            	unmdItemStr = "UN";                
            if (unmdItemStr == null)
            	unmdItemStr = "UN";                
            Element UnmdItem = document.createElement("UnmdItem");
            org.w3c.dom.Text UM = document.createTextNode(unmdItemStr);
            UnmdItem.appendChild(UM);
            Detalle.appendChild(UnmdItem);
            
           
            	Element PrcItem = document.createElement("PrcItem");
            	// if (iLine.getPriceActual().equals(equals(BigDecimal.ZERO)))
            	if (iLine.getC_OrderLine().getPriceActual().compareTo(new BigDecimal("0")) == 0)
                 {
                org.w3c.dom.Text pa = document.createTextNode("1");
                PrcItem.appendChild(pa);
                 }
                
            	 else
                 {
                org.w3c.dom.Text pa = document.createTextNode(iLine.getC_OrderLine().getPriceActual().setScale(0, 4).toString());
                //org.w3c.dom.Text pa = document.createTextNode("1");
                PrcItem.appendChild(pa);
                 }
                Detalle.appendChild(PrcItem);          
           
            
            Element MontoItem = document.createElement("MontoItem");
            BigDecimal montoItem =iLine.getC_OrderLine().getLineNetAmt();           
            org.w3c.dom.Text tl = document.createTextNode(montoItem.toString());
            MontoItem.appendChild(tl);
            Detalle.appendChild(MontoItem);
        }
		return document;
	}	
	
	
	private Document llenarReferencias(Document document, Element Documento,
			MInOut inout, MOrg company) {
		mylog = "referencia";
        String tiporeferencia = new String();
        String folioreferencia  = new String();
        String fechareferencia = new String();
        String razonRef= new String();
        int tipo_Ref =0;
        
        if(inout.getC_Order_ID() >0 )//referencia orden
        {
        	 mylog = "referencia:order";
        	 MOrder refdoc = new MOrder(inout.getCtx(), inout.getC_Order_ID(), inout.get_TrxName()); 
        	 tiporeferencia = "801";
             folioreferencia = refdoc.getDocumentNo();
             fechareferencia = refdoc.getDateOrdered().toString().substring(0, 10);
        	 tipo_Ref = 2; //Orden
        	 
        	 String sql = "SELECT C_Invoice_ID FROM C_Invoice WHERE DocStatus ='CO' AND C_Order_ID="+inout.getC_Order_ID();
        	 int C_Invoice_ID = DB.getSQLValue(get_TrxName(), sql);
        	 
        	 if(C_Invoice_ID>0)//referencia factura C_RefDoc_ID
             {
                 mylog = "referencia:invoice";
                 MInvoice refinv = new MInvoice(inout.getCtx(), C_Invoice_ID, inout.get_TrxName());
                 MDocType Refdoctype = new MDocType(inout.getCtx(), refinv.getC_DocType_ID(), inout.get_TrxName());
                 tiporeferencia = (String) Refdoctype.get_Value("DocumentNo");
                 folioreferencia = (String) refinv.getDocumentNo();
                 fechareferencia = refinv.getDateInvoiced().toString().substring(0, 10);
                 //razonRef = truncate(inout.get_ValueAsString("MotivoNC"), 90);
                 tipo_Ref = 1; //factura
             } 
        }
        
       /* if(invoice.get_Value("C_RefInOut_ID") != null && ((Integer)invoice.get_Value("C_RefInOut_ID")).intValue() > 0)//referencia despacho
        {
        	 mylog = "referencia:despacho";
        	 MInOut refdoc = new MInOut(invoice.getCtx(), ((Integer)invoice.get_Value("C_RefInOut_ID")).intValue(), invoice.get_TrxName()); 
        	 tiporeferencia = "52";
             folioreferencia = (String) refdoc.getDocumentNo();
             fechareferencia = refdoc.getMovementDate().toString().substring(0, 10);
        	 tipo_Ref = 3; //despacho
        }*/
        
        if(inout.getC_Invoice_ID()>0)//referencia factura C_RefDoc_ID
        {
            mylog = "referencia:invoice";
            MInvoice refdoc = new MInvoice(inout.getCtx(), inout.getC_Invoice_ID(), inout.get_TrxName());
            MDocType Refdoctype = new MDocType(inout.getCtx(), refdoc.getC_DocType_ID(), inout.get_TrxName());
            tiporeferencia = (String) Refdoctype.get_Value("DocumentNo");
            folioreferencia = (String) refdoc.getDocumentNo();
            fechareferencia = refdoc.getDateInvoiced().toString().substring(0, 10);
            //razonRef = truncate(inout.get_ValueAsString("MotivoNC"), 90);
            tipo_Ref = 1; //factura
        } 
        
       
        int indice = 0;
        if(tipo_Ref>0)
        {
            Element Referencia = document.createElement("Referencia");
            Documento.appendChild(Referencia);
            Element NroLinRef = document.createElement("NroLinRef");
            org.w3c.dom.Text Nro = document.createTextNode("1");
            NroLinRef.appendChild(Nro);
            Referencia.appendChild(NroLinRef);
            Element TpoDocRef = document.createElement("TpoDocRef");
            org.w3c.dom.Text tpo = document.createTextNode(truncate(tiporeferencia, 3));
            TpoDocRef.appendChild(tpo);
            Referencia.appendChild(TpoDocRef);
            Element FolioRef = document.createElement("FolioRef");
            org.w3c.dom.Text ref = document.createTextNode(truncate(folioreferencia,18));
            FolioRef.appendChild(ref);
            Referencia.appendChild(FolioRef);
            
                Element FchRef = document.createElement("FchRef");
                org.w3c.dom.Text fchref = document.createTextNode(fechareferencia);
                FchRef.appendChild(fchref);
                Referencia.appendChild(FchRef);
            
            
            Element CodRef = document.createElement("CodRef");
            org.w3c.dom.Text codref = document.createTextNode(Integer.toString(tipo_Ref));
            CodRef.appendChild(codref);
            Referencia.appendChild(CodRef);   
            
            Element RazonRef = document.createElement("RazonRef");
            org.w3c.dom.Text razonref = document.createTextNode(razonRef);
            RazonRef.appendChild(razonref);
            Referencia.appendChild(RazonRef);  
            indice = indice+1;
        }else{
        	
        }
      //ininoles nueva referencia de guia de despacho
       
        /*int ID_Ship = 0;
        try            	
        {
        	if(invoice.getC_Order_ID() > 0)
        	{
        		ID_Ship = DB.getSQLValue(invoice.get_TrxName(), "SELECT COALESCE((MAX(M_InOut_ID)),0) " +
        				"FROM M_InOut mi INNER JOIN C_Order co ON (mi.C_Order_ID = co.C_Order_ID) " +
        				"WHERE mi.docstatus IN ('CO','CL','VO') AND mi.C_Order_ID = "+invoice.getC_Order_ID());
        	}
        }catch (Exception e)
		{
        	ID_Ship = 0;
			log.log(Level.SEVERE, e.getMessage(), e);
		}
        if(ID_Ship>0)
        {
        	indice = indice+1;
        	String  docRef = "52";            	
        	MInOut inOutref = new MInOut(invoice.getCtx(), ID_Ship, invoice.get_TrxName());
        	MDocType docTShip = new MDocType(invoice.getCtx(), inOutref.getC_DocType_ID(), invoice.get_TrxName());
        	if(docTShip.get_ValueAsBoolean("CreateXML"))
        		docRef = "52";
        	else
        		docRef = "50";            		
        	Element Referencia = document.createElement("Referencia");
            Documento.appendChild(Referencia);
            Element NroLinRef = document.createElement("NroLinRef");
            org.w3c.dom.Text Nro = document.createTextNode(Integer.toString(indice));
            NroLinRef.appendChild(Nro);
            Referencia.appendChild(NroLinRef);
            Element TpoDocRef = document.createElement("TpoDocRef");
            org.w3c.dom.Text tpo = document.createTextNode(truncate(docRef,3));
            TpoDocRef.appendChild(tpo);
            Referencia.appendChild(TpoDocRef);
            Element FolioRef = document.createElement("FolioRef");
            org.w3c.dom.Text ref = document.createTextNode(truncate(inOutref.getDocumentNo(),18));
            FolioRef.appendChild(ref);
            Referencia.appendChild(FolioRef);
                Element FchRef = document.createElement("FchRef");
                org.w3c.dom.Text fchref = document.createTextNode(inOutref.getMovementDate().toString().substring(0, 10));
                FchRef.appendChild(fchref);
                Referencia.appendChild(FchRef);    
            
        }*/
        
        //fin referencia
        
        
        //fin referencia
        
        mylog = "Adicional";
        Element Adicional = document.createElement("Adicional");
        document.getDocumentElement().appendChild(Adicional);//Documento.appendChild(Adicional);
        if (inout.getDescription() != null && inout.getDescription() != "" && inout.getDescription() != " ")
        {            
           
            /*Element NodosA = document.createElement("NodosA");
            Adicional.appendChild(NodosA);*/
            Element Siete = document.createElement("Siete");
            org.w3c.dom.Text a6Text = document.createTextNode(truncate(inout.getDescription(),250));
            Siete.appendChild(a6Text);
            Adicional.appendChild(Siete);
        }
        Element Ocho = document.createElement("Ocho");
        MOrgInfo companyInfo = company.getInfo();
        String a8TextString = company.getName()+" - "+(companyInfo.getC_Location()!=null?companyInfo.getC_Location().getAddress1():"");
        org.w3c.dom.Text a8Text = document.createTextNode(truncate(a8TextString,250));
        Ocho.appendChild(a8Text);
        Adicional.appendChild(Ocho);
        
        /*if(invoice.getPOReference()!=null){
        	if(!invoice.getPOReference().equals("")){
                Element Nueve = document.createElement("Nueve");
                org.w3c.dom.Text a9Text = document.createTextNode("Pedido: "+invoice.getPOReference());
                Nueve.appendChild(a9Text);
                Adicional.appendChild(Nueve);            		
        	}
        }*/
        
        MBPartnerLocation bloc = new MBPartnerLocation(inout.getCtx(), inout.getC_BPartner_Location_ID(), inout.get_TrxName());
        if(bloc.get_ValueAsString("Email")!=null){
        	if(!bloc.get_ValueAsString("Email").equals("")){
        		Element Contabilidad = document.createElement("Contabilidad");
        		Element aEncabezado = document.createElement("Encabezado");
                Element Destinatarios = document.createElement("destinatarios");
                Adicional.appendChild(Contabilidad);
                Contabilidad.appendChild(aEncabezado);
                aEncabezado.appendChild(Destinatarios);
                mylog = "Destinatarios";
                /*Element ccTag = document.createElement("cc");
                org.w3c.dom.Text cc = document.createTextNode("contacto@mashini.cl");
                ccTag.appendChild(cc);
                Destinatarios.appendChild(ccTag);*/
                Element Correo = document.createElement("correo");
                org.w3c.dom.Text correo = document.createTextNode(truncate(bloc.get_ValueAsString("Email"),150));
                Correo.appendChild(correo);
                Destinatarios.appendChild(Correo);
        	}
        }
		return document;
	}
	
}	//	InvoiceCreateInOut
