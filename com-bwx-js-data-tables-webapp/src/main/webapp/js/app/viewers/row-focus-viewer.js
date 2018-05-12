/* ===================================================
 * Data Table Focus Viewer
 *
 * @author Chris Ludka
 * ========================================================== */

/*
 * Constructor
 *
 * spec{
 *  parentElement: String
 *  rowFocusController: obj
 * }
 */
var newRowFocusViewer = function(spec) {
	// New object
	var that = {};

	/* =========================================================================
	 * Private Properties
	 * =======================================================================*/
	var panelElement = 'panel-focus';
	var panelId = '#' + panelElement;

	/* =========================================================================
	 * Private Methods
	 * =======================================================================*/

	// Focus changed
	that.synch = function() {
		// Clear
		$(panelId).children().remove();

		// Panel Heading
		var panelHeadingElement = 'panel-heading-focus';
		var panelHeadingId = '#' + panelHeadingElement;
		$(panelId).append('<div id="' + panelHeadingElement + '" class="panel-heading">Row Selected</div>');

		// Display Text
		var focus = spec.rowFocusController.getFocus();
		var displayText = '';
		if (typeof focus === 'undefined') {
			displayText = "No row is currently selected.";
		} else {
			displayText = "Row Selected: " + '<br>' +
				'Transaction: ' + focus.transactionId + '<br>' +	
				'Row Index: ' + (parseInt(focus.rowIndex) + 1) + '<br>' +
				'UUID: ' + focus.uuid;
		}

		// Panel Body
		var panelBodyElement = 'panel-body-focus';
		var panelBodyId = '#' + panelBodyElement;
		$(panelId).append('<div id="' + panelBodyElement + '" class="panel-body">' + displayText + '</div>');
	};

	/* =========================================================================
	 * Public Methods
	 * =======================================================================*/

	// Reset
	that.reset = function() {
		// Parent
		var parentId = '#' + spec.parentElement;
		$(parentId).append('<div id="' + panelElement + '" class="panel panel-default"></div>');

		// Reset
		that.synch();

		// Register Callback
		spec.rowFocusController.addCallback(that.synch);
	};

	// Result
	return that;
};