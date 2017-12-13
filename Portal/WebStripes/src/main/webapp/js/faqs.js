

$(document).ready(function() {
	
	if ($('.faqs').length ) {
		
		$(".deswrap").each(function(){
			 $('.deswrap').children().addClass('sr-hidden');
		});
    
		$('.faqs').mouseenter(function() { 
		  $(this).addClass('faqHover');
		});
	   
	   $('.faqs').mouseleave(function() { 
			  
			  $(this).removeClass('faqHover');
		});
	   
	   
		//$('.faqs').click(function() { 
//		 
//		  
//		  if($(this).hasClass('faqActive')){ // if it is being closed
//			  $(this).removeClass('faqActive');
//			 // $('.deswrap').children().hide();
//		  } else { // if it is being opened
//			   $('.faqs').removeClass('faqActive');
//			  $(this).addClass('faqActive');
//			 
//			  //$('.deswrap').children().hide();
//			 // $('.deswrap').children().hide(500, function() {
//				// $(this).children('.deswrap').show();
//			 // });
//
//
//			 
//		  }
//		  
//		});
		
		
		$('.title').click(function() { 
		 
		  var thisparentfaq = $(this).parent().parent();
		  var thisfaq = $(this).parent().next().children();
		  
		  $('.deswrap').children().addClass('sr-hidden'); // hide all at first then show the one clicked
		  
		  if($(thisfaq).hasClass('sr-hidden')){ 
			$(thisfaq).removeClass('sr-hidden');
			$(thisfaq).show('fast');
		  } else {
			$(thisfaq).addClass('sr-hidden'); 
		  }
		  
		  if($(thisparentfaq).hasClass('faqActive')){ // if it is being closed
			  $(thisparentfaq).removeClass('faqActive');
			 $('.deswrap').children().addClass('sr-hidden');
		  } else { // if it is being opened
			   $('.faqs').removeClass('faqActive');
			  $(thisparentfaq).addClass('faqActive');
			 
			  
			 //$('.deswrap').children().hide(500, function() {
			//	 $(this).children('.deswrap').show();
			// });


			 
		  }
		  
		  
		});
  
	
	}
	
	
	
	
	 $('.faqs').each(function(index) {  // validate select boxes
												
				if (jQuery.browser.msie) {
						$(this).css({'margin-top':'0'})
						$(this).css({'margin-bottom':'0'})
					} 
				
				if( ($(this).next()).hasClass('faqs') ) {
											
				} else {
					
					if (jQuery.browser.msie) {
						$(this).css({'margin-bottom':'15px'})
					} else {
						$(this).css({'margin-bottom':'35px'})
					}
					
					
					
						
					
				}
				
				if( ($(this).prev()).hasClass('faqs') ) {
											
				} else {
					$(this).addClass('faqsBorderTop')
				}
				
				
				
		});
	 
	 if (jQuery.browser.msie) {
		$('.csfaqs').not(':first').not(':last').css({'margin-top':'-20px','margin-bottom':'0'})
		$('.csfaqs:first').css({'margin-bottom':'0'})
	} else {
		$('.csfaqs').not(':last').css({'margin-bottom':'0'})
	}


	
});