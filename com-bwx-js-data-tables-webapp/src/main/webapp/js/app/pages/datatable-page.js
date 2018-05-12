/* ===================================================
 * Data Tables Page
 *
 * @author Chris Ludka
 * ==========================================================*/

// Entry
$(document).ready(
	function() {
		// Page
		var dataTablePage = newDataTablePage();
		dataTablePage.reset();
	});

/*
 * Constructor
 */
var newDataTablePage = function() {
	// New object
	var that = {};

	/* =========================================================================
	 * Private Properties
	 * =======================================================================*/

	// Controllers
	var transactionController;

	/* =========================================================================
	 * Public Methods
	 * =======================================================================*/

	// Reset
	that.reset = function() {
		// Controllers
		transactionController = newTransactionController();
		rowFocusController = newRowFocusController();

		// Body
		var divBodyElement = 'div-datatable-body';
		var divBodyId = '#' + divBodyElement;

		// Document Title
		document.title = "Binary Workspace JS Data Tables";

		// Clear
		$(divBodyId).children().remove();

		// Container
		var divContainerElement = 'div-container';
		var divContainerId = '#' + divContainerElement;
		$(divBodyId).append('<div id="' + divContainerElement + '" class="container-fluid"></div>');

		// Row
		var rowDataTableElement = 'row-datatable';
		var rowDataTableId = '#' + rowDataTableElement;
		$(divContainerId).append('<div id="' + rowDataTableElement + '" class="row"></div>');

		// Col Selector
		var colDataTableTransactionElement = 'col-datatable-selector';
		var colDataTableTransactionId = '#' + colDataTableTransactionElement;
		$(rowDataTableId).append('<div id="' + colDataTableTransactionElement + '" class="col-xs-4 col-sm-4 col-md-4 col-lg-4"></div>');

		// Data Table Selector Viewer
		var dataTableSelectorViewer = newDataTableTransactionViewer({
			parentElement: colDataTableTransactionElement,
			transactionController: transactionController,
			rowFocusController: rowFocusController
		});
		dataTableSelectorViewer.reset();
		
		// Data Table Focus Viewer
		var RowFocusViewer = newRowFocusViewer({
			parentElement: colDataTableTransactionElement,
			rowFocusController: rowFocusController
		});
		RowFocusViewer.reset();

		// Col Data Table
		var colDataTableElement = 'col-datatable';
		var colDataTableId = '#' + colDataTableElement;
		$(rowDataTableId).append('<div id="' + colDataTableElement + '" class="col-xs-8 col-sm-8 col-md-8 col-lg-8"></div>');

		// Data Table Viewer
		var dataTableViewer = newDataTableViewer({
			parentElement: colDataTableElement,
			transactionController: transactionController,
			rowFocusController: rowFocusController
		});

	};

	// Result
	return that;
};