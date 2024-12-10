package cl.comercialwindsor.process;

import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

public class CreateInvoiceFromOrderOrInvoice extends SvrProcess {

	int m_C_Order_ID;

	int m_C_Invoice_ID;
	
	String m_DocumentNo;
	
	Timestamp m_DateDoc;

	int m_C_DocTypeTarget_ID;
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (name.equals("C_Order_ID"))
				m_C_Order_ID =para[i].getParameterAsInt();
			else if (name.equals("C_Invoice_ID"))
				m_C_Invoice_ID = para[i].getParameterAsInt();
			else if (name.equals("DocumentNo"))
				m_DocumentNo = (String)para[i].getParameter();
			else if (name.equals("DateDoc"))
				m_DateDoc = (Timestamp)para[i].getParameter();
			else if (name.equals("C_DocTypeTarget_ID"))
				m_C_DocTypeTarget_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception {
		
		if(m_C_Order_ID>0){
			test();
			//createFromOrder();
		}else if(m_C_Invoice_ID>0){
			//createFromInvoice();
		}
		
		return "OK";
	}
	
	private void test(){
		if(m_C_Order_ID==(-5)){
			throw new AdempiereException("Error de prueba");			
		}
		
		MOrder order = new MOrder(getCtx(), m_C_Order_ID, get_TrxName());
		order.setDescription(order.getDescription() + " || TEST");
		order.saveEx(get_TrxName());
	}
	
	private void createFromOrder(){
		MOrder order = new MOrder(getCtx(), m_C_Order_ID, get_TrxName());
		
		MInvoice invoice = new MInvoice(order, m_C_DocTypeTarget_ID, m_DateDoc);
		invoice.setC_Order_ID(m_C_Order_ID);
		invoice.saveEx(get_TrxName());
		
		for(MOrderLine oLine: order.getLines()){
			MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
			invoiceLine.setOrderLine(oLine);
			invoiceLine.saveEx(get_TrxName());
		}
		
		if(invoice.processIt(MInvoice.ACTION_Complete)){
			invoice.saveEx(get_TrxName());
		}else{
			throw new AdempiereException(invoice.getProcessMsg());
		}
	}

	private void createFromInvoice(){
		MInvoice invoice = new MInvoice(getCtx(), m_C_Invoice_ID, get_TrxName());

		MInvoice creditNote = new MInvoice(getCtx(), 0, get_TrxName());
		creditNote.setAD_Org_ID(invoice.getAD_Org_ID());
		creditNote.setAD_OrgTrx_ID(invoice.getAD_OrgTrx_ID());
		creditNote.setAD_User_ID(invoice.getAD_User_ID());
		creditNote.setM_PriceList_ID(invoice.getM_PriceList_ID());
		creditNote.setC_BPartner_ID(invoice.getC_BPartner_ID());
		creditNote.setC_BPartner_Location_ID(invoice.getC_BPartner_Location_ID());
		creditNote.setC_Activity_ID(invoice.getC_Activity_ID());
		creditNote.setC_ConversionType_ID(invoice.getC_ConversionType_ID());
		creditNote.setC_Currency_ID(invoice.getC_Currency_ID());
		creditNote.setC_DocTypeTarget_ID(m_C_DocTypeTarget_ID);
		creditNote.setC_DunningLevel_ID(invoice.getC_DunningLevel_ID());
		creditNote.setC_PaymentTerm_ID(invoice.getC_PaymentTerm_ID());
		creditNote.setC_Project_ID(invoice.getC_Project_ID());
		creditNote.setDateAcct(m_DateDoc);
		creditNote.setDateInvoiced(m_DateDoc);
		creditNote.setIsSOTrx(false);
		creditNote.setRef_Invoice_ID(m_C_Invoice_ID);
		creditNote.set_ValueOfColumn("C_INVOICE2_ID", m_C_Invoice_ID);
		
		for(MInvoiceLine iLine: invoice.getLines()){
			MInvoiceLine creditNoteLine = new MInvoiceLine(creditNote);
			MInvoiceLine.copyValues(iLine, creditNoteLine);
			creditNoteLine.setInvoice(creditNote);
			creditNoteLine.saveEx(get_TrxName());
		}

		if(invoice.processIt(MInvoice.ACTION_Complete)){
			invoice.saveEx(get_TrxName());
		}else{
			throw new AdempiereException(invoice.getProcessMsg());
		}
		
	}
}
