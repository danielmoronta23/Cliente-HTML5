
(function($) {
    "use strict";

    // Add active state to sidbar nav links
    var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
        $("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function() {
            if (this.href === path) {
                $(this).addClass("active");
            }
        });

    // Toggle the side navigation
    $("#sidebarToggle").on("click", function(e) {
        e.preventDefault();
        $("body").toggleClass("sb-sidenav-toggled");
    });
})(jQuery);

(function() {
    //Check to see if localStorage is supported
    if(window.localStorage)
        console.log("Local Storage Supported")
    else
        console.log("No support for Local Storage")

    var almacenamiento = localStorage.getItem("BackUp_fecha");

    if(almacenamiento == null){
        var pag = document.body;
        var tmp = new Date().toUTCString();
        localStorage.setItem("BackUp_fecha", tmp);
        localStorage.setItem("pagina", pag);

        //Almacenando información en el ambiente de sesión.
        sessionStorage.setItem("BackUp_fecha", new Date().toUTCString())

    }

})();





