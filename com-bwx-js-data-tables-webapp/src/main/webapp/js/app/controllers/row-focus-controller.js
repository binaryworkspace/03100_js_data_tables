/* ===================================================
 * Row Focus Controller.
 *
 * @author Chris Ludka
 * ========================================================== */

/*
 * Constructor
 */
var newRowFocusController = function() {
	// New object
	var that = {};

	/* =========================================================================
	 * Private Properties
	 * =======================================================================*/

	// Current focus
	var focus = undefined;

	// Callbacks
	var callbacks = [];

	/* =========================================================================
	 * Public Methods
	 * =======================================================================*/

	// Add Callback
	that.addCallback = function(callback) {
		callbacks.push(callback);
	};

	// Get Focus
	that.getFocus = function() {
		return focus;
	};

	// Remove Callback
	that.removeCallback = function(callback) {
		_.pull(callbacks, callback);
	};

	that.reset = function() {
		// Reset
		focus = undefined;
		
		// Update Callbacks
		$.each(callbacks, function(key, callback) {
			callback();
		});
	};

	// Set Focus
	that.setFocus = function(value) {
		// Upddate
		focus = value;

		// Update Callbacks
		$.each(callbacks, function(key, callback) {
			callback();
		});
	};

	// Result
	return that;
};