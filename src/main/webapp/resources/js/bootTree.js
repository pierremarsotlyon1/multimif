/**
 *  BootTree Treeview plugin for Bootstrap.
 *
 *  Based on BootSnipp TreeView Example by Sean Wessell
 *  URL:	http://bootsnipp.com/snippets/featured/bootstrap-30-treeview
 *
 *	Revised code by Leo "LeoV117" Myers
 *
 */
$.fn.extend({
    treeview:	function() {
        return this.each(function() {
            // Initialize the top levels;
            var tree = $(this);
            tree.addClass('treeview-tree');
            tree.find('li').each(function() {
                var stick = $(this);
            });
            tree.find('li').has("ul").each(function () {
                var branch = $(this); //li with children ul

                branch.prepend("<i class='fa fa-angle-down' aria-hidden='true'></i> ");
                branch.addClass('tree-branch');
                branch.on('click', function (e) {
                    if (this == e.target) {
                        var icon = $(this).children('i:first');
                        icon.toggleClass("fa-angle-down fa-angle-up");
                        $(this).children().children().toggle();
                        $(this).find(".addElementProjet").show();
                        $(this).children().show();
                    }
                });
                branch.children().children().toggle();

                /**
                 *	The following snippet of code enables the treeview to
                 *	function when a button, indicator or anchor is clicked.
                 *
                 *	It also prevents the default function of an anchor and
                 *	a button from firing.
                 */
                branch.children('.tree-indicator, button, span').click(function(e) {
                    branch.click();
                    //e.preventDefault();
                });
            });
        });
    }
});

/**
 *	The following snippet of code automatically converst
 *	any '.treeview' DOM elements into a treeview component.
 */