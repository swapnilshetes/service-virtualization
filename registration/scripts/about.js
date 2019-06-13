jQuery(document).ready(function ($) {

    $.scrollTo = $.fn.scrollTo = function (x, y, options) {
        if (!(this instanceof $)) return $.fn.scrollTo.apply($('html, body'), arguments);

        options = $.extend({}, {
            gap: {
                x: 0,
                y: 0
            },
            animation: {
                easing: 'swing',
                duration: 600,
                complete: $.noop,
                step: $.noop
            }
        }, options);

        return this.each(function () {
            var elem = $(this);
            elem.stop().animate({
                scrollLeft: !isNaN(Number(x)) ? x : $(y).offset().left + options.gap.x,
                scrollTop: !isNaN(Number(y)) ? y : $(y).offset().top + options.gap.y
            }, options.animation);
        });
    };


    var slider = new PageSlider($, $('#container1'));

    // Basic page routing
   // Basic page routing
    function route(event) {
        var linkwithData;
        var page,
            hash = window.location.hash;
		
		var content , mainTitle ;
				var linkwithData  = false ;
		$('.mobile-link-abt').each( function() {
			if($(this).attr("href") === hash) {
					linkwithData  = true ;
					mainTitle = $(this).parent().find('h1').html();
					content = $(this).parent().find('div').html();
			}
		})
		
		if (linkwithData) {
            page = $('#page-detail-template').html();
	    } else {
            page = $('#page-main-template').html();
        }
		
		slider.slidePage($(page));
		
		if (linkwithData) {
            
           $("#aboutus-page-heading").html(mainTitle);
		   $("#aboutus-page-content").html(content);
        } 
       

    }

    $(window).on('hashchange', route);
    route();


    /**
     * This part does the "fixed navigation after scroll" functionality
     * We use the jQuery function scroll() to recalculate our variables as the
     * page is scrolled/
     */
    $(window).scroll(function () {
        var window_top = $(window).scrollTop() + 12 // the "12" should equal the margin-top value for nav.stick
        var div_top = $('#nav-anchor').offset().top;
        if (window_top > div_top) {
            $('nav.menu-in').addClass('stick');
        } else {
            $('nav.menu-in').removeClass('stick');
        }
    });


    /**
     * This part causes smooth scrolling using scrollto.js
     * We target all a tags inside the nav, and apply the scrollto.js to it.
     */
    $("nav.menu-in a").click(function (evn) {
        evn.preventDefault();
        $('html,body').scrollTo(this.hash, this.hash);
    });


    /**
            * This part handles the highlighting functionality.
            * We use the scroll functionality again, some array creation and
            * manipulation, class adding and class removing, and conditional testing
            */
    var aChildren = $("nav.menu-in li").children(); // find the a children of the list items
    var aArray = []; // create the empty aArray
    for (var i = 0; i < aChildren.length; i++) {
        var aChild = aChildren[i];
        var ahref = $(aChild).attr('href');
        aArray.push(ahref);
    } // this for loop fill


});