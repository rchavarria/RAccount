package es.rchavarria.raccount.frontend.expensesReport.gui;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.rchavarria.raccount.frontend.expensesReport.data.ResultFormData;
import es.rchavarria.raccount.frontend.expensesReport.models.ExpensesReportResultTableModel;
import es.rchavarria.raccount.frontend.gui.view.GuiView;

public class ExpensesReportResultController {

    private final static Logger log = LoggerFactory.getLogger(ExpensesReportResultController.class);

    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
	private ExpensesReportResultView view;

	public ExpensesReportResultController() {
		view = new ExpensesReportResultView();
	}

	public GuiView getView() {
		return view;
	}

	public void load(ResultFormData data) {
	    log.info("loading {} expenses", data.expenses.size());
	    
	    view.setAccountName(data.account.getName());
	    view.setDateFromName(sdf.format(data.dateFrom));
	    view.setDateToName(sdf.format(data.dateTo));
		view.setTableModel(new ExpensesReportResultTableModel(data.expenses));
	}
}
