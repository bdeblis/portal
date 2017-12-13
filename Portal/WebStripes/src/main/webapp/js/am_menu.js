// \\ General Accordion Events
	$(document).ready(function() {initMenu();});
	
	
	function initMenu() {
		$('#am_menu ul').hide();
		($('#am_menu .nav-selected').next()).show();
		($('#am_menu .nav-selected').parent()).show();
		//($('#am_menu .nav-selected').parent().parent().sibling()).addClass('nav-selected');
		
		$('#col1').mouseleave(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
			$('#am_menu ul').hide();
			($('#am_menu .nav-selected').next()).show();
			 ($('#am_menu .nav-selected').parent()).show();
		
			
		}); 
		
		$('#col2').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
			$('#am_menu ul').hide();
			($('#am_menu .nav-selected').next()).show();
			 ($('#am_menu .nav-selected').parent()).show();
			
			
		}); 
		
		$('#all').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
			$('#am_menu ul').hide();
			($('#am_menu .nav-selected').next()).show();
			 ($('#am_menu .nav-selected').parent()).show();
			
			
		}); 
		
		$('body').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
			$('#am_menu ul').hide();
			($('#am_menu .nav-selected').next()).show();
			 ($('#am_menu .nav-selected').parent()).show();
			
			
		}); 
		
		
		
		var zi = 1000;
		$("#am_menu>li").each(function() {
		
			$(this).css({'z-index':zi--,'position':'relative'})
	
		});
		
		
		
		
		//$('#am_menu li a').click(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
//			$('#am_menu li ul li ul').show().addClass('am_deep_menu');
//			var checkElement = $(this).next();
//			if((checkElement.is('#am_menu ul')) && (checkElement.is(':visible'))) {
//				return false;
//			}
//			if((checkElement.is('#am_menu ul')) && (!checkElement.is(':visible'))) {
//				$('#am_menu ul:visible').slideUp('fast');
//				checkElement.slideDown('fast');
//				return false;
//			}
//	});
		
		$('#am_menu>li>a').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
			$('#am_menu li ul li ul').show().addClass('am_deep_menu');
			var checkElement = $(this).next();
			
			
			
			if( $(this).is('.nav-path-selected') ) {
				
			} else if (checkElement.is('#am_menu ul')) {
				checkElement.addClass('hover');
			} else {
				$(this).addClass('noChild');
			}
			
			
			
			if((checkElement.is('#am_menu ul')) && (checkElement.is(':visible'))) {
				return false;
			}
			if( (checkElement.is('#am_menu ul.hover')) && (!checkElement.is(':visible')) || $(this).hasClass('noChild') ) {
				//$('#am_menu ul:visible').slideUp('fast');
				//checkElement.slideDown('fast');
				
				$('#am_menu ul.hover').fadeOut(250);
				//$('#am_menu ul:visible').fadeOut(250);
			
				//checkElement.fadeIn(250);
				
				  checkElement.css({'opacity':'0','display':'block'}).stop().animate({
					opacity: 0.95
				  }, 250, function() {
					// Animation complete.
				  });

				
				return false;
			}
	});
		
		$('#am_menu>li>a.nav-path-selected').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
			
			$('#am_menu ul').each(function(index) {
					
					if( $(this).siblings().is('.nav-path-selected') ) {
				
					} else {
						$(this).fadeOut(250);
					}
					
				});
		});
		
		$('#am_menu>li.nav-path-selected>ul').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
			
			$('#am_menu ul').each(function(index) {
					
					if( $(this).siblings().is('.nav-path-selected') ) {
				
					} else {
						$(this).fadeOut(250);
					}
					
				});
		});
		
		
		//$('li.nav-path-selected').prev().children().css('border-bottom','none');
		
		
		
		

// \\ Add ID to the first li of the menu
	$('#am_menu li a:first').attr('id', 'am_menu_black_first');
	
}