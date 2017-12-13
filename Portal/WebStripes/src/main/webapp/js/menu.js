

$(document).ready(function() {
	$('.nav li ul').css({ opacity: 1 });	
	
	$('.nav li li').css({ opacity: 1 });
						   
   $('.nav').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
		 // $('.nav li ul').hide();
		  $('.nav li li').stop().show().animate({
			opacity: 1
		  }, 500, function() {
			// Animation complete.
		  });
		  $('.nav li ul').stop().animate({
			opacity: 1
		  }, 500, function() {
			// Animation complete.
		  });
	});
   
   $('.nav').mouseleave(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
		  
		  $('.nav li ul').stop().animate({
			opacity: 1
		  }, 500, function() {
			// Animation complete.
		  });
	});
  
  $('.nav li li').mouseenter(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
		  $(this).stop().animate({
			opacity: 1
		  }, 500, function() {
			// Animation complete.
		  });
	});
  
  $('.nav li li').mouseleave(function() { // \\ Change .hover to .click if you want the menu to expand on click and not hover
		  $(this).stop().animate({
			opacity: 1
		  }, 100, function() {
			// Animation complete.
		  });
	});
});