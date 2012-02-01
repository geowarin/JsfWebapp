
window['scrolledToEnd']  = false;

jQuery(document).ready(function() {
	
	jQuery('#scrollContainer').bind('scroll', scrolled);
});

function scrolled(e) {
	var evt = e || window.event;

	var totalScrollHeight = jQuery('#scrollContainer').attr("scrollHeight")
			- jQuery('#scrollContainer').attr("offsetHeight");
	// console.debug(evt.target.scrollTop + "/" + totalScrollHeight);
	if (!window['scrolledToEnd'] && (totalScrollHeight - evt.target.scrollTop < 40)) {
		window['scrolledToEnd'] = true;
		//console.debug('scrolled');
		scrolledToEndHandler(e);
	}
}

function scrolledToEndHandler(e) {
	
	addUrlToPanel("/contents/contents2.xhtml");
}

