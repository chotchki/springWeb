"use strict";
$.fn.masonryImagesReveal = function( $items ) {
	  var msnry = this.data('masonry');
	  var itemSelector = msnry.options.itemSelector;
	  // hide by default
	  $items.hide();
	  // append to container
	  this.append( $items );
	  $items.imagesLoaded().progress( function( imgLoad, image ) {
	    // get item
	    // image is imagesLoaded class, not <img>, <img> is image.img
	    var $item = $( image.img ).parents( itemSelector );
	    // un-hide item
	    $item.show();
	    // masonry does its thing
	    msnry.appended( $item );
	  });
	  
	  return this;
};

(function(){
	var loadImgs = function(index, element){
		var parent = this;
		var e = element;
		var url = $(this).data('photos');
		$.getJSON(url)
			.fail(function() {
				$(parent).find('h1').html('<i class="fa fa-3x fa-alert"></i> Had an error loading images!');
			})
			.done(function(json) {
				$(parent).empty();
				
				var $container = $(e).masonry({
					itemSelector: '.item',
					columnWidth: 275,
					isFitWidth: true
				});
				
				var images = '';
				for(var i = 0; i < json.length; i++){
					images += '<div class="item">';
					images += '<a href="/photos/items/' + json[i].id + '">';
					images += '<img src="/photos/items/' + json[i].id + '/view/275" /></a></div>';
				}
				$container.masonryImagesReveal($(images));
				$(parent).removeClass('photos').addClass('photos-loaded');
			});
	};
	
	$('.photos').each(loadImgs);

	$('#albums').infinitescroll({
		debug        : true, 
	    navSelector  : ".pagination",            
	                   // selector for the paged navigation (it will be hidden)
	    nextSelector : ".pagination li:last a",    
	                   // selector for the NEXT link (to page 2)
	    itemSelector : "#albums .row",          
	                   // selector for all items you'll retrieve
		},function( newElements ) {
	    	$('.photos').each(loadImgs);
	    }
	 );
})();


	

