/*
 * In-Field Label jQuery Plugin
 * http://fuelyourcoding.com/scripts/infield.html
 *
 * Copyright (c) 2009 Doug Neiner
 * Dual licensed under the MIT and GPL licenses.
 * Uses the same license as jQuery, see:
 * http://docs.jquery.com/License
 *
 * @version 0.1
 */
(function($){
	
    $.inFieldLabelsTwo = function(label,field, options){
        // To avoid scope issues, use 'base' instead of 'this'
        // to reference this class from internal events and functions.
        var base = this;
        
        // Access to jQuery and DOM versions of each element
        base.$label = $('.sem-label');
        //base.label = label;
		//console.log(base.$label.attr('class'))

 		base.$field = $('.sem-text');
		//base.field = field;
		//console.log(base.$field.attr('class'))
        
		base.$label.data("inFieldLabelsTwo", base);
		base.showing = true;
        
        base.init = function(){
			// Merge supplied options with default options
            base.options = $.extend({},$.inFieldLabelsTwo.defaultOptions, options);
            
            base.$label.css('position','absolute');
            var fieldPosition = base.$field.position();
            base.$label.css({
                'top' : '5px'
            }).addClass(base.options.labelClass);

			// Check if the field is already filled in
			if(base.$field.val() != ""){
				base.$label.hide();
				base.showing = false;
			};
			
		

			
			base.$field.focus(function(){
				//base.fadeOnFocus();
				
				if(base.showing){
					$(this).prev().stop().animate({ opacity: 0.5 }, 300);
					base.showing = (0.5 > 0.0);
					
					// Reattach the keydown event
						$(this).bind('keydown.infieldlabel',function(e){
							//console.log('asdfasdf')
							if(
								(e.keyCode == 16) || // Skip Shift
								(e.keyCode == 9) // Skip Tab
							  ) return; 
							
							if(base.showing){
								$(this).prev().hide();
								
								base.showing = false;
							};
							
							// Remove keydown event to save on CPU processing
							base.$field.unbind('keydown.infieldlabel');
						});
					
				};
				
				
				
				//console.log('hello');
			}).blur(function(){
				if($(this).prev().val() == ""){
					
					if(!base.showing) {
						// Prepare for a animate in...
						$(this).prev().css({opacity: 0.0}).show();
						
						
						// Reattach the keydown event
						//$(this).bind('keydown.infieldlabel',function(e){
							//base.hideOnChange(e);
						//});
					};
					
					
					$(this).prev().stop().animate({ opacity: 1.0 }, 300);
					base.showing = (1.0 > 0.0);
				} else {
					$(this).prev().stop().animate({ opacity: 0.0 }, 300);
					base.showing = (0.0 > 0.0);
				};
				
				//console.log($(this).val())
				
				if($(this).val() == ""){
							$(this).prev().css({ opacity: 1.0 }).show();
							//console.log('1')
						} else {
							$(this).prev().css({ opacity: 0.0 }).hide();
							//console.log('2')
						}
				
				//console.log('goodbye');
			});
			
			//$(base.$field).bind('keydown.infieldlabel',function(e){
//				// Use of a namespace (.infieldlabel) allows us to
//				// unbind just this method later
//				//base.hideOnChange(e);
//			}).change(function(e){
//				base.checkForEmpty();
//			}).bind('onPropertyChange', function(){
//				base.checkForEmpty();
//			});
        };

		// If the label is currently showing
		// then fade it down to the amount
		// specified in the settings
		//base.fadeOnFocus = function(){
//			if(base.showing){
//				base.setOpacity(base.options.fadeOpacity);
//			};
//		};
		
		//base.setOpacity = function(opacity){
//			base.$label.stop().animate({ opacity: opacity }, base.options.fadeDuration);
//			base.showing = (opacity > 0.0);
//		};
		
		// Checks for empty as a fail safe
		// set blur to true when passing from
		// the blur event
		//base.checkForEmpty = function(blur){
//			if(base.$field.val() == ""){
//				base.prepForShow();
//				base.setOpacity( blur ? 1.0 : base.options.fadeOpacity );
//			} else {
//				base.setOpacity(0.0);
//			};
//		};
		
		//base.prepForShow = function(e){
//			if(!base.showing) {
//				// Prepare for a animate in...
//				base.$label.css({opacity: 0.0}).show();
//				
//				// Reattach the keydown event
//				base.$field.bind('keydown.infieldlabel',function(e){
//					base.hideOnChange(e);
//				});
//			};
//		};

		//base.hideOnChange = function(e){
//			if(
//				(e.keyCode == 16) || // Skip Shift
//				(e.keyCode == 9) // Skip Tab
//			  ) return; 
//			
//			if(base.showing){
//				base.$label.hide();
//				base.showing = false;
//			};
//			
//			// Remove keydown event to save on CPU processing
//			base.$field.unbind('keydown.infieldlabel');
//		};
      
		// Run the initialization method
        base.init();
    };
	
    $.inFieldLabelsTwo.defaultOptions = {
        fadeOpacity: 0.5, // Once a field has focus, how transparent should the label be
		fadeDuration: 300, // How long should it take to animate from 1.0 opacity to the fadeOpacity
		labelClass: 'infield' // Class to be applied to label when positioned over form field
    };
	

    $.fn.inFieldLabelsTwo = function(options){
        return this.each(function(){
			// Find input or textarea based on for= attribute
			// The for attribute on the label must contain the ID
			// of the input or textarea element
			var for_attr = $(this).attr('for');
			if( !for_attr ) return; // Nothing to attach, since the for field wasn't used
			
			
			// Find the referenced input or textarea element
			var $field = $(
				"input#" + for_attr + "[type='text']," + 
				"input#" + for_attr + "[type='password']," + 
				"textarea#" + for_attr
				);
				
			if( $field.length == 0) return; // Again, nothing to attach
			
			// Only create object for input[text], input[password], or textarea
            (new $.inFieldLabelsTwo(this, $field[0], options));
        });
    };
	
})(jQuery);