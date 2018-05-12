/* ===================================================
 * Transaction Controller.
 *
 * @author Chris Ludka
 * ========================================================== */

/*
 * Constructor
 */
var newTransactionController = function() {
	// New object
	var that = {};

	/* =========================================================================
	 * Private Properties
	 * =======================================================================*/

	// Current Transaction ID
	var transactionId = -1;

	// Callbacks
	var callbacks = [];

	/* =========================================================================
	 * Public Methods
	 * =======================================================================*/

	// Add Callback
	that.addCallback = function(callback) {
		callbacks.push(callback);
	};

	// Transaction ID
	that.getTransactionId = function() {
		return transactionId;
	};

	// Remove Callback
	that.removeCallback = function(callback) {
		_.pull(callbacks, callback);
	};

	that.reset = function() {
		transactionId = -1;
	};

	/*
	 * Simulate a Transaction ID change the server.  Normally, this would be
	 * call back to the server triggered through polling process.
	 */
	that.setTransactionId = function(value) {
		if ((typeof value === 'undefined') || (!(_.isEqual(transactionId, value)))) {
			// Update
			transactionId = value;

			// Update Callbacks
			$.each(callbacks, function(key, callback) {
				callback();
			});
		}
	};

	// Result
	return that;
};