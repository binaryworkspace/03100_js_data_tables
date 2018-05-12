/* ===================================================
 * Data Table Example Viewer
 *
 * @author Chris Ludka
 * ========================================================== */

/*
 * Constructor
 *
 * spec{
 *  parentElement: String
 *  transactionController: obj
 *  rowFocusController: obj
 * }
 */
var newDataTableViewer = function(spec) { // New object
	var that = {};

	/* =========================================================================
	 * Private Methods
	 * =======================================================================*/
	var parentId = '#' + spec.parentElement;
	var tableElement = 'datatable';
	var tableId = '#' + tableElement;
	var tableStructure = "";

	/*
	 * Flatten DataTables params.
	 *
	 * There are two fields in data tables that have three dimensions ([][][]) that can
	 * be merged into two dimensions ([][]) to match the rest of the dimensional space.
	 *
	 * @see http://stackoverflow.com/questions/29889909/handle-data-table-request-parameters-in-spring-request
	 *
	 */
	var planifyDataTables = function(data) {
		for (var i = 0; i < data.columns.length; i++) {
			var column = data.columns[i];
			column.searchRegex = column.search.regex;
			column.searchValue = column.search.value;
			delete(column.search);
		}
	};

	// Reset the table
	var reset = function() {
		// Ensure table has been cleared
		if ($(tableId).DataTable()) {
			$(tableId).DataTable().destroy();
		}

		// Clear panel elements
		$(parentId).children().remove();

		// Panel
		var panelElement = 'panel-' + tableElement;
		var panelId = '#' + panelElement;
		$(parentId).append('<div id="' + panelElement + '" class="panel panel-default"></div>');

		// Panel Heading
		var panelHeadingElement = 'panel-heading-' + tableElement;
		var panelHeadingId = '#' + panelHeadingElement;
		$(panelId).append('<div id="' + panelHeadingElement + '" class="panel-heading"></div>');
		$(panelHeadingId).text(title);

		// Check for an empty table
		if (columns.length === 0) {
			return;
		}

		// Panel Body
		var panelBodyElement = 'panel-body-' + tableElement;
		var panelBodyId = '#' + panelBodyElement;
		$(panelId).append('<div id="' + panelBodyElement + '" class="panel-body"></div>');

		// Table
		$(panelBodyId).append('<table id="' + tableElement + '" class="table table-condensed table-hover table-striped table-bordered" /table>');

		// Header
		var headerTr$ = $('<tr style="font-size:10px;" />');
		$(tableId).append($('<thead/>'));
		for (var i = 0; i < columns.length; i++) {
			headerTr$.append($('<th/>').html(columns[i].name));
		}
		$(tableId).children('thead').append(headerTr$);

		// Build Table
		$(tableId).DataTable({
			ajax: {
				url: "services/datatable-3100/table-body",
				type: 'POST',
				contentType: "application/json; charset=utf-8",
				dataType: 'json',
				data: function(data) {
					/*
					 * Data Table Body Request:
					 * data{
					 *  columns: []
					 *  draw: int
					 *  length: int
					 *  order: [{
					 *  	column: int
					 *  	dir: String ('asc', 'desc')
					 *  	}]
					 *  search: {
					 *  	value: String
					 *  	regex: boolean
					 *  	}
					 *  start: int
					 * }
					 */

					// Transaction ID
					data.transactionId = spec.transactionController.getTransactionId();

					planifyDataTables(data);
					return JSON.stringify(data);
				}
			},
			columns: columns,
			columnDefs: [{
				targets: '_all',
				createdCell: function(td, cellData, rowData, row, col) {
					$(td).attr('style', 'font-size:10px;');
				}
			}],
			lengthMenu: [5, 10, 25, 50, 100],
			order: [
				[0, 'asc']
			],
			processing: true,
			renderer: {
				header: 'bootstrap',
				pageButton: 'bootstrap'
			},
			scrollX: true,
			select: {
				style: 'single'
			},
			serverSide: true
		});

		// Add handler for row click event
		spec.rowFocusController.reset();
		$(tableId).DataTable().on('select', function(e, dt, type, indexes) {
			if (type === 'row') {
				// Get the HTML TR element id from the row.
				var rowElement = dt.row(indexes).node();
				var rowId = $(rowElement).attr('id');

				// Reply back to the sever with the row id
				$.ajax({
					url: "services/datatable-3100/row/" + rowId,
					type: 'PUT',
					contentType: "application/json; charset=utf-8",
					dataType: 'json',
					data: rowId,
					success: function(data, status, jqXHR) {
						spec.rowFocusController.setFocus(data);
					},
					error: function(xhr) {
						throw new Error(xhr.responseText);
					}
				});
			}
		});

		// Add handler for search highlight
		$(tableId).DataTable().on('draw', function() {
			//console.log("Call Highlight for: " + $(tableId).DataTable().search());
			var body = $($(tableId).DataTable().table().body());
			body.unhighlight();

			// Search rules
			var strSearch = $(tableId).DataTable().search();
			if (spec.isSearchByPhraseEnabled) {
				body.highlight(strSearch);
			} else {
				// Get all candidate words separated by spaces
				var strSplit = strSearch.split(" ");
				for (var i = 0; i < strSplit.length; i++) {
					// Highlight
					body.highlight(strSplit);
				}
			}
		});
	};

	// Synch Columns and Title
	var synchStructure = function() {
		// Get the table title and header data, then reset the table
		return $.ajax({
			url: "services/datatable-3100/table-structure/" + spec.transactionController.getTransactionId(),
			dataType: 'json',
			success: function(data) {
				// Table structure
				var response = _.attempt(JSON.parse.bind(null, JSON.stringify(data)));
				tableStructure = response;
				columns = tableStructure.columns;
				title = tableStructure.title;
			},
			error: function(xhr) {
				throw new Error(xhr.responseText);
			}
		});
	};

	// Forces a reload of the table
	var reload = function() {
		$(tableId).DataTable().ajax.reload(null, false);
	};

	/* =========================================================================
	 * Public Methods
	 * =======================================================================*/

	// Transaction Id changed
	that.synch = function() {
		// Check if the table has been initialized
		if ($(parentId).children().length === 0 || $(tableId).DataTable().context.length === 0) {
			$.when(synchStructure()).done(function(a1) {
				reset();
			});
			return;
		}

		// If the columns have changed then the table must be reset
		$.when(synchStructure()).done(function(a1) {
			// Server side columns
			var serverColumns = [];
			for (var i = 0; i < columns.length; i++) {
				serverColumns.push(columns[i].name);
			}

			// Loop through existing table columns
			var clientColumns = [];
			$(tableId + " th").each(function() {
				clientColumns.push($(this).find("div").html());
			});

			// Check if the columns changed
			if (_.isEqual(serverColumns, clientColumns)) {
				// The headers did not change so check for title change
				var clientTitle = $(tableId + " caption").html();
				if (!(_.isEqual(clientTitle, tableStructure.title))) {
					$(tableId + " caption").text(tableStructure.title);
				}

				/* The headers did not change so check for table data changes
				 * using reload().
				 */
				reload();
			} else {
				// Server does not match client
				reset();
			}
		});
	};

	// Register Callback
	spec.transactionController.addCallback(that.synch);

	// Result
	return that;
};