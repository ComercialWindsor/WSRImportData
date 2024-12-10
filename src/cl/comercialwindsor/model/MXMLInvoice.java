package cl.comercialwindsor.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCity;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MXMLInvoice extends MInvoice {

	public MXMLInvoice(Properties ctx, int C_Invoice_ID, String trxName) {
		super(ctx, C_Invoice_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public MXMLInvoice(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3619208264473186266L;

	private String reemplazarCaracteresEspeciales(String palabra) {
		palabra = palabra.replace("ñ", "n");
		palabra = palabra.replace("Ñ", "N");
		palabra = palabra.replace("|", "");
		// palabra = palabra.replace(" ", "");
		palabra = palabra.replace("à", "a");
		palabra = palabra.replace("á", "a");
		palabra = palabra.replace("À", "A");
		palabra = palabra.replace("Á", "A");
		palabra = palabra.replace("è", "e");
		palabra = palabra.replace("é", "e");
		palabra = palabra.replace("È", "E");
		palabra = palabra.replace("É", "E");
		palabra = palabra.replace("ì", "i");
		palabra = palabra.replace("í", "i");
		palabra = palabra.replace("Ì", "I");
		palabra = palabra.replace("Í", "I");
		palabra = palabra.replace("ò", "o");
		palabra = palabra.replace("ó", "o");
		palabra = palabra.replace("Ò", "O");
		palabra = palabra.replace("Ó", "O");
		palabra = palabra.replace("ù", "u");
		palabra = palabra.replace("ú", "u");
		palabra = palabra.replace("Ù", "U");
		palabra = palabra.replace("Ú", "U");
		palabra = palabra.replace("\b", "");
		palabra = palabra.replace("/", "");
		palabra = palabra.replace(":", "");
		palabra = palabra.replace("<", "");
		palabra = palabra.replace("*", "");
		palabra = palabra.replace("?", "");
		palabra = palabra.replace("'", "");
		palabra = palabra.replace(">", "");
		return palabra;

	}

	private String truncate(String text, int maxSize) {

		if (text == null)
			return "";
		if (maxSize <= 0) {
			return text;
		} else {
			// delete all the accents and ñ from the string
			// text =
			// reemplazarCaracteresEspeciales(text);//Normalizer.normalize(text,
			// Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]",
			// "");//.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			return reemplazarCaracteresEspeciales(text.substring(0,
					Math.min(text.length(), maxSize)));
		}
	}

	public String generateXML() {
		String wsRespuesta = "";
		MDocType doc = MDocType.get(getCtx(), getC_DocTypeTarget_ID());
		// mediante este check se cambiaran los valores de los tag o se obviaran
		// algunos valores
		boolean isBoleta = doc.get_ValueAsBoolean("BoletaElectronica");
		MOrg company = MOrg.get(getCtx(), getAD_Org_ID());
		if (doc.get_Value("CreateXML") == null)
			return "";
		if (((Boolean) doc.get_Value("CreateXML")).booleanValue())
			return "";
		int typeDoc = Integer.parseInt((String) doc.get_Value("DocumentNo"));
		if (typeDoc == 0)
			return "";
		String mylog = new String();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null,
					"integracion", null);// implementation.createDocument(null,
											// "DTE",
											// null);//builder.newDocument();//
			// document.setXmlVersion("1.0");
			document.setTextContent("text/xml");
			/*
			 * Attr atr = document.createAttribute("xmlns");
			 * atr.setValue("http://www.sii.cl/SiiDte");
			 */
			Element DTE = document.createElement("DTE");
			document.getDocumentElement().appendChild(DTE);
			DTE.setAttribute("version", "1.0");

			Element Documento = document.createElement("Documento");
			DTE.appendChild(Documento);// document.getDocumentElement().appendChild(Documento);
			Documento.setAttribute(
					"ID",
					(new StringBuilder()).append("F").append(getDocumentNo())
							.append("T")
							.append((String) doc.get_Value("DocumentNo"))
							.toString());
			Element Encabezado = document.createElement("Encabezado");
			Documento.appendChild(Encabezado);
			Element IdDoc = document.createElement("IdDoc");
			Encabezado.appendChild(IdDoc);
			mylog = "IdDoc";
			Element TipoDTE = document.createElement("TipoDTE");
			org.w3c.dom.Text text = document.createTextNode(truncate(
					Integer.toString(typeDoc), 3));
			TipoDTE.setTextContent(text.getTextContent());// TipoDTE.appendChild(text);
			IdDoc.appendChild(TipoDTE);
			Element Folio = document.createElement("Folio");
			org.w3c.dom.Text fo = document.createTextNode(truncate(
					getDocumentNo(), 10));
			Folio.appendChild(fo);
			IdDoc.appendChild(Folio);
			Element FchEmis = document.createElement("FchEmis");
			org.w3c.dom.Text emis = document.createTextNode(getDateInvoiced()
					.toString().substring(0, 10));
			FchEmis.appendChild(emis);
			IdDoc.appendChild(FchEmis);
			// ininoles nuevos campos de termino de pago
			if (!isBoleta) {
				Element MntPagos = document.createElement("MntPagos");
				IdDoc.appendChild(MntPagos);
				mylog = "Termino de Pago";
				Element FchPago = document.createElement("FchPago");
				org.w3c.dom.Text fPago = document
						.createTextNode(getDateInvoiced().toString().substring(
								0, 10));
				FchPago.appendChild(fPago);
				MntPagos.appendChild(FchPago);
				Element MntPago = document.createElement("MntPago");
				org.w3c.dom.Text mPago = document
						.createTextNode(getGrandTotal().setScale(0, 4)
								.toString());
				MntPago.appendChild(mPago);
				MntPagos.appendChild(MntPago);
				Element GlosaPagos = document.createElement("GlosaPagos");
				org.w3c.dom.Text gPagos = document.createTextNode(truncate(
						getC_PaymentTerm().getName(), 40));
				GlosaPagos.appendChild(gPagos);
				MntPagos.appendChild(GlosaPagos);
			} else {
				Element IndServicio = document.createElement("IndServicio");
				org.w3c.dom.Text serv = document.createTextNode("3");
				IndServicio.appendChild(serv);
				IdDoc.appendChild(IndServicio);
			}
			// ininoles end

			Element FchVenc = document.createElement("FchVenc");
			org.w3c.dom.Text venc = document.createTextNode(getDateInvoiced()
					.toString().substring(0, 10));
			FchVenc.appendChild(venc);
			IdDoc.appendChild(FchVenc);
			Element Emisor = document.createElement("Emisor");
			Encabezado.appendChild(Emisor);
			mylog = "Emisor";
			Element Rut = document.createElement("RUTEmisor");
			org.w3c.dom.Text rut = document.createTextNode(truncate(
					(String) company.get_Value("Rut"), 10));
			Rut.appendChild(rut);
			Emisor.appendChild(Rut);
			// ininoles validacion nuevo nombre razon social
			String nameRzn = company.getDescription();
			if (nameRzn == null) {
				nameRzn = " ";
			}
			nameRzn = nameRzn.trim();
			if (nameRzn.length() < 2)
				nameRzn = company.getName();
			// razon social hardcodeado
			nameRzn = "INVERSIONES MURO LTDA";
			// ininoles end
			nameRzn = nameRzn.replace("'", "");
			nameRzn = nameRzn.replace("\"", "");
			Element RznSoc = document.createElement(isBoleta ? "RznSocEmisor"
					: "RznSoc");// RznSocEmisor
			org.w3c.dom.Text rzn = document.createTextNode(truncate(nameRzn,
					100));
			RznSoc.appendChild(rzn);
			Emisor.appendChild(RznSoc);
			String giroEmisStr = (String) company.get_Value("Giro");
			giroEmisStr = giroEmisStr.replace("'", "");
			giroEmisStr = giroEmisStr.replace("\"", "");
			Element GiroEmis = document.createElement(isBoleta ? "GiroEmisor"
					: "GiroEmis");
			org.w3c.dom.Text gi = document.createTextNode(truncate(giroEmisStr,
					10));
			GiroEmis.appendChild(gi);
			Emisor.appendChild(GiroEmis);
			if (!isBoleta) {
				Element Acteco = document.createElement("Acteco");
				org.w3c.dom.Text teco = document.createTextNode(truncate(
						(String) company.get_Value("Acteco"), 6));
				Acteco.appendChild(teco);
				Emisor.appendChild(Acteco);
			}
			Element DirOrigen = document.createElement("DirOrigen");
			org.w3c.dom.Text dir = document.createTextNode(truncate(
					(String) company.get_Value("Address1"), 60));
			DirOrigen.appendChild(dir);
			Emisor.appendChild(DirOrigen);

			Element CmnaOrigen = document.createElement("CmnaOrigen");
			org.w3c.dom.Text com = document.createTextNode(truncate(
					(String) company.get_Value("Comuna"), 20));
			CmnaOrigen.appendChild(com);
			Emisor.appendChild(CmnaOrigen);
			Element CiudadOrigen = document.createElement("CiudadOrigen");
			org.w3c.dom.Text city = document.createTextNode(truncate(
					(String) company.get_Value("City"), 20));
			CiudadOrigen.appendChild(city);
			Emisor.appendChild(CiudadOrigen);
			// vendedor
			if (getSalesRep_ID() > 0 && !isBoleta) {
				Element SalesRep = document.createElement("CdgVendedor");
				org.w3c.dom.Text sales = document.createTextNode(truncate(
						getSalesRep().getName(), 60));
				SalesRep.appendChild(sales);
				Emisor.appendChild(SalesRep);
			}

			mylog = "receptor";
			MBPartner BP = new MBPartner(getCtx(), getC_BPartner_ID(),
					get_TrxName());
			MBPartnerLocation bloc = new MBPartnerLocation(getCtx(),
					getC_BPartner_Location_ID(), get_TrxName());
			Element Receptor = document.createElement("Receptor");
			Encabezado.appendChild(Receptor);
			Element RUTRecep = document.createElement("RUTRecep");
			String vRut = (new StringBuilder()).append(BP.getValue())
					.append("-").append(BP.get_ValueAsString("Digito"))
					.toString();
			if (BP.get_ID() == 1000003 && (isBoleta || isCreditMemo()))
				vRut = "66666666-6";
			org.w3c.dom.Text rutc = document.createTextNode(truncate(vRut, 10));
			RUTRecep.appendChild(rutc);
			Receptor.appendChild(RUTRecep);

			String RznSocRecepStr = BP.getName();
			RznSocRecepStr = RznSocRecepStr.replace("'", "");
			RznSocRecepStr = RznSocRecepStr.replace("\"", "");
			Element RznSocRecep = document.createElement("RznSocRecep");
			org.w3c.dom.Text RznSocR = document.createTextNode(truncate(
					RznSocRecepStr, (isBoleta ? 40 : 100)));
			RznSocRecep.appendChild(RznSocR);
			Receptor.appendChild(RznSocRecep);

			if (!isBoleta) {
				Element GiroRecep = document.createElement("GiroRecep");
				org.w3c.dom.Text giro = document.createTextNode(truncate(
						(String) BP.get_Value("Name2"), 40));
				GiroRecep.appendChild(giro);
				Receptor.appendChild(GiroRecep);
			}

			String dirRecepStr = bloc.getLocation(false).getAddress1() == null ? ""
					: bloc.getLocation(false).getAddress1();
			dirRecepStr = dirRecepStr.replace("'", "");
			dirRecepStr = dirRecepStr.replace("\"", "");
			Element DirRecep = document.createElement("DirRecep");
			org.w3c.dom.Text dirr = document.createTextNode(truncate(
					dirRecepStr, 70));
			DirRecep.appendChild(dirr);
			Receptor.appendChild(DirRecep);

			if (bloc.getLocation(false).getC_City_ID() > 0) {
				Element CmnaRecep = document.createElement("CmnaRecep");
				org.w3c.dom.Text Cmna = document.createTextNode(truncate(MCity
						.get(getCtx(), bloc.getLocation(false).getC_City_ID())
						.getName(), 20));
				CmnaRecep.appendChild(Cmna);
				Receptor.appendChild(CmnaRecep);
			} else {
				Element CmnaRecep = document.createElement("CmnaRecep");
				org.w3c.dom.Text Cmna = document.createTextNode(truncate(bloc
						.getLocation(false).getAddress2(), 20));
				CmnaRecep.appendChild(Cmna);
				Receptor.appendChild(CmnaRecep);
			}
			// if(bloc.getLocation(true).getAddress2()!=null &&
			// bloc.getLocation(true).getAddress2().length()>0 ){
			// Element CmnaRecep = document.createElement("CmnaRecep");
			// org.w3c.dom.Text Cmna =
			// document.createTextNode(bloc.getLocation(true).getAddress2());
			// CmnaRecep.appendChild(Cmna);
			// Receptor.appendChild(CmnaRecep);
			// }

			Element CiudadRecep = document.createElement("CiudadRecep");
			org.w3c.dom.Text reg = document.createTextNode(truncate(
					bloc.getLocation(false).getC_City_ID() > 0 ? MCity
							.get(getCtx(),
									bloc.getLocation(false).getC_City_ID())
							.getC_Region().getName() : "Metropolitana", 20));
			CiudadRecep.appendChild(reg);
			Receptor.appendChild(CiudadRecep);

			mylog = "Totales";
			Element Totales = document.createElement("Totales");
			Encabezado.appendChild(Totales);

			// getting totals
			StringBuilder sqlTotales = new StringBuilder(
					"SELECT Round(ABS(COALESCE(SUM(il.LineNetAmt),0)),0) as amount, t.istaxexempt")
					.append(" FROM C_InvoiceLine il ")
					.append(" JOIN C_Tax t ON (il.C_Tax_ID=t.C_Tax_ID)")
					.append(" WHERE  il.LineNetAmt>0 AND il.C_Invoice_ID=")
					.append(getC_Invoice_ID())
					.append(" GROUP BY  t.istaxexempt");

			PreparedStatement st = null;
			ResultSet rs = null;
			BigDecimal amountex = BigDecimal.ZERO;
			BigDecimal amountNeto = BigDecimal.ZERO;
			try {
				st = DB.prepareStatement(sqlTotales.toString(), get_TrxName());
				rs = st.executeQuery();
				while (rs.next()) {
					if (rs.getString("istaxexempt").equals("Y")) {
						amountex = rs.getBigDecimal("amount");
					} else {
						amountNeto = rs.getBigDecimal("amount");
					}
				}
			} catch (Exception e) {
				throw new AdempiereException(e);
			} finally {
				DB.close(rs, st);
				st = null;
				rs = null;
			}

			Element MntNeto = document.createElement("MntNeto");
			org.w3c.dom.Text neto = document
					.createTextNode(amountNeto != null ? amountNeto.toString()
							: "0");
			MntNeto.appendChild(neto);
			Totales.appendChild(MntNeto);

			Element MntExe = document.createElement("MntExe");
			org.w3c.dom.Text exe = document
					.createTextNode(amountex != null ? amountex.toString()
							: "0");
			MntExe.appendChild(exe);
			Totales.appendChild(MntExe);

			if (amountNeto.signum() > 0 && !isBoleta) {
				Element TasaIVA = document.createElement("TasaIVA");
				org.w3c.dom.Text tiva = document.createTextNode("19");
				TasaIVA.appendChild(tiva);
				Totales.appendChild(TasaIVA);
			}

			Element IVA = document.createElement("IVA");
			BigDecimal ivaamt = Env.ZERO;
			if (amountex.intValue() != getGrandTotal().intValue())
				ivaamt = getGrandTotal().subtract(getTotalLines()).setScale(0,
						4);
			if (getGrandTotal().compareTo(BigDecimal.ONE) >= 0
					&& getGrandTotal().compareTo(new BigDecimal(6)) <= 0)
				ivaamt = Env.ONE;
			org.w3c.dom.Text iva = document.createTextNode(ivaamt.toString());
			IVA.appendChild(iva);
			Totales.appendChild(IVA);

			Element MntTotal = document.createElement("MntTotal");
			org.w3c.dom.Text total = document.createTextNode(getGrandTotal()
					.setScale(0, 4).toString());
			MntTotal.appendChild(total);
			Totales.appendChild(MntTotal);

			mylog = "detalle";
			MInvoiceLine iLines[] = getLines(false);
			int lineInvoice = 0;
			boolean hasDiscount = false;
			BigDecimal discountAmt = BigDecimal.ZERO;
			for (int i = 0; i < iLines.length; i++) {
				MInvoiceLine iLine = iLines[i];
				if ((iLine.getM_Product_ID() == 0 && iLine.getC_Charge_ID() == 0))
					continue;

				if (iLine.getQtyInvoiced().compareTo(BigDecimal.ZERO) <= 0
						|| iLine.getLineTotalAmt().compareTo(BigDecimal.ZERO) <= 0) {
					hasDiscount = true;
					discountAmt = discountAmt
							.add(iLine.getLineTotalAmt().abs()).setScale(2,
									RoundingMode.HALF_UP);
					continue;
				}
				Element Detalle = document.createElement("Detalle");
				Documento.appendChild(Detalle);

				// MTax tax=new MTax(invoice.getCtx()
				// ,iLine.getC_Tax_ID(),invoice.get_TrxName() );
				// ininoles se saca indice exento
				// if(tax.isTaxExempt()){
				// Element IndEx = document.createElement("IndExe");
				// org.w3c.dom.Text lineE = document.createTextNode("1");
				// IndEx.appendChild(lineE);
				// Detalle.appendChild(IndEx);
				// }
				//
				lineInvoice = lineInvoice + 1;
				Element NroLinDet = document.createElement("NroLinDet");
				org.w3c.dom.Text line = document.createTextNode(Integer
						.toString(lineInvoice));
				NroLinDet.appendChild(line);
				Detalle.appendChild(NroLinDet);
				String detailValue = "";
				String pname = "";
				if (iLine.getProduct() != null) {
					pname = iLine.getProduct().getName();
					detailValue = iLine.getProduct().getValue();
				} else {
					pname = iLine.getC_Charge().getName();
					detailValue = iLine.getC_Charge().getName();
				}

				Element CdgItem = document.createElement("CdgItem");
				Element TpoCodigo = document.createElement("TpoCodigo");
				Element VlrCodigo = document.createElement("VlrCodigo");
				org.w3c.dom.Text tpoCodigo = document.createTextNode("INT");
				TpoCodigo.appendChild(tpoCodigo);
				org.w3c.dom.Text vlrCodigo = document.createTextNode(truncate(
						detailValue, 10));
				VlrCodigo.appendChild(vlrCodigo);
				CdgItem.appendChild(TpoCodigo);
				CdgItem.appendChild(VlrCodigo);
				Detalle.appendChild(CdgItem);

				Element NmbItem = document.createElement("NmbItem");

				pname = pname.replace("'", "");
				pname = pname.replace("\"", "");
				org.w3c.dom.Text Item = document.createTextNode(truncate(pname,
						80));
				NmbItem.appendChild(Item);
				Detalle.appendChild(NmbItem);

				if (iLine.getDescription() != null
						&& iLine.getDescription() != "") {
					Element DscItem = document.createElement("DscItem");
					org.w3c.dom.Text desc = document.createTextNode(iLine
							.getDescription() == null ? " " : truncate(
							iLine.getDescription(), 1000));
					DscItem.appendChild(desc);
					Detalle.appendChild(DscItem);
				}
				Element QtyItem = document.createElement("QtyItem");
				org.w3c.dom.Text qt = document.createTextNode(iLine
						.getQtyInvoiced().toString());
				QtyItem.appendChild(qt);
				Detalle.appendChild(QtyItem);

				String unmdItemStr = "";
				if (iLine.getM_Product_ID() > 0)
					unmdItemStr = iLine.getM_Product().getC_UOM()
							.getUOMSymbol();
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
				if (iLine.getPriceActual().compareTo(new BigDecimal("0")) == 0) {
					org.w3c.dom.Text pa = document.createTextNode("1");
					PrcItem.appendChild(pa);
				}

				else {
					org.w3c.dom.Text pa = document.createTextNode(iLine
							.getPriceActual().setScale(0, 4).toString());
					// org.w3c.dom.Text pa = document.createTextNode("1");
					PrcItem.appendChild(pa);
				}
				Detalle.appendChild(PrcItem);

				Element MontoItem = document.createElement("MontoItem");
				BigDecimal montoItem = BigDecimal.ZERO;
				if (isBoleta)
					montoItem = iLine.getLineTotalAmt().setScale(0, 4);
				else
					montoItem = iLine.getLineNetAmt().setScale(0, 4);

				org.w3c.dom.Text tl = document.createTextNode(montoItem
						.toString());
				MontoItem.appendChild(tl);
				Detalle.appendChild(MontoItem);
			}

			if (hasDiscount
					&& discountAmt.abs().compareTo(BigDecimal.ZERO) != 0) {
				mylog = "descuentos";

				Element Descuentos = document.createElement("DscRcgGlobal");// Descuentos
				Documento.appendChild(Descuentos);
				Element NroLinDR = document.createElement("NroLinDR");
				org.w3c.dom.Text Nro = document.createTextNode("1");
				NroLinDR.appendChild(Nro);
				Descuentos.appendChild(NroLinDR);
				Element TpoMov = document.createElement("TpoMov");
				org.w3c.dom.Text tpo = document.createTextNode("D");
				TpoMov.appendChild(tpo);
				Descuentos.appendChild(TpoMov);
				Element TpoValor = document.createElement("TpoValor");
				org.w3c.dom.Text tpoVal = document.createTextNode("$");
				TpoValor.appendChild(tpoVal);
				Descuentos.appendChild(TpoValor);
				Element ValorDR = document.createElement("ValorDR");
				org.w3c.dom.Text valorDR = document.createTextNode(discountAmt
						.toString());
				ValorDR.appendChild(valorDR);
				Descuentos.appendChild(ValorDR);
			}

			mylog = "referencia";
			String tiporeferencia = new String();
			String folioreferencia = new String();
			String fechareferencia = new String();
			String razonRef = new String();
			int tipo_Ref = 0;

			if (getPOReference() != null && getPOReference().length() > 0)// referencia
																			// orden
			{
				mylog = "referencia:order";
				// MOrder refdoc = new MOrder(invoice.getCtx(),
				// ((Integer)get_Value("C_RefOrder_ID")).intValue(),
				// invoice.get_TrxName());
				tiporeferencia = "801";
				folioreferencia = getPOReference();
				fechareferencia = getDateOrdered().toString().substring(0, 10);
				tipo_Ref = 2; // Orden
			}

			if (get_Value("C_RefInOut_ID") != null
					&& ((Integer) get_Value("C_RefInOut_ID")).intValue() > 0)// referencia
																				// despacho
			{
				mylog = "referencia:despacho";
				MInOut refdoc = new MInOut(getCtx(),
						((Integer) get_Value("C_RefInOut_ID")).intValue(),
						get_TrxName());
				tiporeferencia = "52";
				folioreferencia = (String) refdoc.getDocumentNo();
				fechareferencia = refdoc.getMovementDate().toString()
						.substring(0, 10);
				tipo_Ref = 3; // despacho
			}

			if (get_Value("C_INVOICE2_ID") != null
					&& ((Integer) get_Value("C_INVOICE2_ID")).intValue() > 0
					&& isCreditMemo())// referencia factura C_RefDoc_ID
			{
				mylog = "referencia:invoice";
				MInvoice refdoc = new MInvoice(getCtx(),
						((Integer) get_Value("C_INVOICE2_ID")).intValue(),
						get_TrxName());
				MDocType Refdoctype = MDocType.get(getCtx(),
						refdoc.getC_DocType_ID());
				tiporeferencia = (String) Refdoctype.get_Value("DocumentNo");
				folioreferencia = (String) refdoc.getDocumentNo();
				fechareferencia = refdoc.getDateInvoiced().toString()
						.substring(0, 10);
				razonRef = truncate(get_ValueAsString("MotivoNC"), 90);
				tipo_Ref = 1; // factura
			}

			int indice = 0;
			if (tipo_Ref > 0) {
				Element Referencia = document.createElement("Referencia");
				Documento.appendChild(Referencia);
				Element NroLinRef = document.createElement("NroLinRef");
				org.w3c.dom.Text Nro = document.createTextNode("1");
				NroLinRef.appendChild(Nro);
				Referencia.appendChild(NroLinRef);
				Element TpoDocRef = document.createElement("TpoDocRef");
				org.w3c.dom.Text tpo = document.createTextNode(truncate(
						tiporeferencia, 3));
				TpoDocRef.appendChild(tpo);
				Referencia.appendChild(TpoDocRef);
				Element FolioRef = document.createElement("FolioRef");
				org.w3c.dom.Text ref = document.createTextNode(truncate(
						folioreferencia, 18));
				FolioRef.appendChild(ref);
				Referencia.appendChild(FolioRef);
				if (!isBoleta) {
					Element FchRef = document.createElement("FchRef");
					org.w3c.dom.Text fchref = document
							.createTextNode(fechareferencia);
					FchRef.appendChild(fchref);
					Referencia.appendChild(FchRef);
				}

				Element CodRef = document.createElement("CodRef");
				org.w3c.dom.Text codref = document.createTextNode(Integer
						.toString(tipo_Ref));
				CodRef.appendChild(codref);
				Referencia.appendChild(CodRef);

				Element RazonRef = document.createElement("RazonRef");
				org.w3c.dom.Text razonref = document.createTextNode(razonRef);
				RazonRef.appendChild(razonref);
				Referencia.appendChild(RazonRef);
				indice = indice + 1;
			} else {
				if (isCreditMemo()) {
					throw new AdempiereException(
							"La nota de crédito debe tener un documento de referencia");
				}
			}
			// ininoles nueva referencia de guia de despacho
			int ID_Ship = 0;
			try {
				if (getC_Order_ID() > 0) {
					ID_Ship = DB
							.getSQLValue(
									get_TrxName(),
									"SELECT COALESCE((MAX(M_InOut_ID)),0) "
											+ "FROM M_InOut mi INNER JOIN C_Order co ON (mi.C_Order_ID = co.C_Order_ID) "
											+ "WHERE mi.docstatus IN ('CO','CL','VO') AND mi.C_Order_ID = "
											+ getC_Order_ID());
				}
			} catch (Exception e) {
				ID_Ship = 0;
				log.log(Level.SEVERE, e.getMessage(), e);
			}
			if (ID_Ship > 0) {
				String docRef = "52";
				MInOut inOutref = new MInOut(getCtx(), ID_Ship, get_TrxName());

				if (!inOutref.getDocumentNo().isEmpty()
						&& !inOutref.getDocumentNo().equals("0")) {
					indice = indice + 1;
					MDocType docTShip = new MDocType(getCtx(),
							inOutref.getC_DocType_ID(), get_TrxName());
					if (docTShip.get_ValueAsBoolean("CreateXML"))
						docRef = "52";
					else
						docRef = "50";
					Element Referencia = document.createElement("Referencia");
					Documento.appendChild(Referencia);
					Element NroLinRef = document.createElement("NroLinRef");
					org.w3c.dom.Text Nro = document.createTextNode(Integer
							.toString(indice));
					NroLinRef.appendChild(Nro);
					Referencia.appendChild(NroLinRef);
					Element TpoDocRef = document.createElement("TpoDocRef");
					org.w3c.dom.Text tpo = document.createTextNode(truncate(
							docRef, 3));
					TpoDocRef.appendChild(tpo);
					Referencia.appendChild(TpoDocRef);
					Element FolioRef = document.createElement("FolioRef");
					org.w3c.dom.Text ref = document.createTextNode(truncate(
							inOutref.getDocumentNo(), 18));
					FolioRef.appendChild(ref);
					Referencia.appendChild(FolioRef);
					if (!isBoleta) {
						Element FchRef = document.createElement("FchRef");
						org.w3c.dom.Text fchref = document
								.createTextNode(inOutref.getMovementDate()
										.toString().substring(0, 10));
						FchRef.appendChild(fchref);
						Referencia.appendChild(FchRef);
					}
				}
			}

			// fin referencia

			// fin referencia

			mylog = "Adicional";
			Element Adicional = document.createElement("Adicional");
			document.getDocumentElement().appendChild(Adicional);// Documento.appendChild(Adicional);
			if (getDescription() != null && getDescription() != ""
					&& getDescription() != " ") {

				/*
				 * Element NodosA = document.createElement("NodosA");
				 * Adicional.appendChild(NodosA);
				 */
				Element Siete = document.createElement("Siete");
				org.w3c.dom.Text a6Text = document.createTextNode(truncate(
						getDescription(), 250));
				Siete.appendChild(a6Text);
				Adicional.appendChild(Siete);
			}
			Element Ocho = document.createElement("Ocho");
			MOrgInfo companyInfo = company.getInfo();
			String a8TextString = company.getName()
					+ " - "
					+ (companyInfo.getC_Location() != null ? companyInfo
							.getC_Location().getAddress1() : "");
			org.w3c.dom.Text a8Text = document.createTextNode(truncate(
					a8TextString, 250));
			Ocho.appendChild(a8Text);
			Adicional.appendChild(Ocho);

			if (getPOReference() != null) {
				if (!getPOReference().equals("")) {
					Element Nueve = document.createElement("Nueve");
					org.w3c.dom.Text a9Text = document
							.createTextNode("Pedido: " + getPOReference());
					Nueve.appendChild(a9Text);
					Adicional.appendChild(Nueve);
				}
			}

			if (bloc.get_ValueAsString("Email") != null) {
				if (!bloc.get_ValueAsString("Email").equals("")) {
					Element Contabilidad = document
							.createElement("Contabilidad");
					Element aEncabezado = document.createElement("Encabezado");
					Element Destinatarios = document
							.createElement("destinatarios");
					Adicional.appendChild(Contabilidad);
					Contabilidad.appendChild(aEncabezado);
					aEncabezado.appendChild(Destinatarios);
					mylog = "Destinatarios";
					/*
					 * Element ccTag = document.createElement("cc");
					 * org.w3c.dom.Text cc =
					 * document.createTextNode("contacto@mashini.cl");
					 * ccTag.appendChild(cc); Destinatarios.appendChild(ccTag);
					 */
					Element Correo = document.createElement("correo");
					org.w3c.dom.Text correo = document.createTextNode(truncate(
							bloc.get_ValueAsString("Email"), 150));
					Correo.appendChild(correo);
					Destinatarios.appendChild(Correo);
				}
			}

			/*
            */
			mylog = "archivo";

			String ExportDir = (String) company.get_Value("ExportDir");
			try {
				File theDir = new File(ExportDir);
				if (!theDir.exists())
					ExportDir = (String) company.get_Value("ExportDir2");
			} catch (Exception e) {
				throw new AdempiereException("no existe directorio");
			}

			ExportDir = ExportDir.replace("\\", "/");
			javax.xml.transform.Source source = new DOMSource(document);
			javax.xml.transform.Result result = new StreamResult(new File(
					ExportDir, (new StringBuilder()).append(getDocumentNo())
							.append(".xml").toString()));
			// javax.xml.transform.Result console = new
			// StreamResult(System.out);
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.setOutputProperty("encoding", "ISO-8859-1");
			transformer.transform(source, result);

			set_ValueOfColumn("IsTxtGen", "Y");
			saveEx(get_TrxName());
			// commitEx();
		} catch (ParserConfigurationException pce) {
			log.severe(pce.toString());
			return (new StringBuilder()).append("CreateXML: ").append(mylog)
					.append("--").append(pce.getMessage()).toString();
		} catch (TransformerException tfe) {
			log.severe(tfe.toString());
			tfe.printStackTrace();
			return (new StringBuilder()).append("CreateXML: ").append(mylog)
					.append("--").append(tfe.getMessage()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.severe(e.toString());
			log.severe(e.getMessage());
			return (new StringBuilder()).append("CreateXML: ").append(mylog)
					.append("--").append(e.getMessage()).toString();
		}
		// commitEx();
		return "XML CG Generated " + wsRespuesta;

	}

}
