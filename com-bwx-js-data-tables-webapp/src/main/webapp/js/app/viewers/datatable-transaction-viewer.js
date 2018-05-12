/* ===================================================
 * Data Table Transaction Viewer
 *
 * @author Chris Ludka
 * ========================================================== */

/*
 * Constructor
 *
 * spec{
 *  parentElement: String
 *  transactionController: obj
 * }
 */
var newDataTableTransactionViewer = function(spec) {
	// New object
	var that = {};

	/* =========================================================================
	 * Private Properties
	 * =======================================================================*/
	var panelBodyElement = 'panel-body-datatable-selector';
	var panelBodyId = '#' + panelBodyElement;
	var divRadioButtonsElement = 'div-body-datatable-selector-radio-buttons';
	var txList;

	/* =========================================================================
	 * Private Methods
	 * =======================================================================*/

	/*
	 * Reset Buttons: Called when the transaction Id changes.
	 */
	resetRadioButtons = function() {
		// Clear
		$(panelBodyId).children().remove();

		for (var txIndex = 0; txIndex < txList.length; txIndex++) {
			// Transaction List
			var tx = txList[txIndex];

			// Radio Button
			var radioButtonElement = divRadioButtonsElement + '-' + tx.id;
			var radioButtonId = '#' + radioButtonElement;
			if (txIndex == spec.transactionController.getTransactionId()) {
				$(panelBodyId).append('<div><button id="' + radioButtonElement + '" class="btn btn-primary" data-tx-id="' + tx.id + '">' + tx.description + '</button></div>');
			} else {
				$(panelBodyId).append('<div><button id="' + radioButtonElement + '" class="btn btn-default" data-tx-id="' + tx.id + '">' + tx.description + '</button></div>');
			}

			// Radio Button Selector Event
			$(radioButtonId).click(function(e) {
				e.preventDefault();

				// Selection
				var txId = $(e.currentTarget).attr("data-tx-id");

				// Simulate a 'poll' where the transaction id changed
				spec.transactionController.setTransactionId(txId);
			});
		}
	};

	/* =========================================================================
	 * Public Methods
	 * =======================================================================*/

	// Reset
	that.reset = function() {
		// Parent
		var parentId = '#' + spec.parentElement;
		var panelElement = 'panel-datatable-selector';
		var panelId = '#' + panelElement;
		$(parentId).append('<div id="' + panelElement + '" class="panel panel-default"></div>');

		// Panel Heading
		var panelHeadingElement = 'panel-heading-datatable-selector';
		var panelHeadingId = '#' + panelHeadingElement;
		$(panelId).append('<div id="' + panelHeadingElement + '" class="panel-heading">Example Table Transaction States</div>');

		// Panel Body
		$(panelId).append('<div id="' + panelBodyElement + '" class="panel-body"></div>');

		// Get Table Transactions from Server
		$.ajax({
			url: "services/datatable-3100/table-transactions",
			dataType: "json",
			success: function(data) {
				// Table Transaction List
				txList = _.attempt(JSON.parse.bind(null, JSON.stringify(data)));

				// Default to the first (zero indexed) button being selected
				spec.transactionController.addCallback(resetRadioButtons);
				spec.transactionController.setTransactionId(0);
			},
			error: function(xhr) {
				throw new Error(xhr.responseText);
			}
		});
	};

	// Result
	return that;
};