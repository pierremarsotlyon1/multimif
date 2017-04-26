$(document).ready(function(){
    var liFichier;
    var lastDocs;
    var idProjet = $(".idProjet").text();

    if(idProjet)
    {
        $(".idProjet").remove();
        $("#blockDocumentation").removeClass("hidden");

        $(document).on("click", "li.fichier", function()
        {
            if(liFichier)
            {
                liFichier.removeClass("active");
            }

            liFichier = $(this);
            liFichier.addClass("active");

            var idFichier = $(this).attr("data-id");
            if(idFichier)
            {
                if(lastDocs)
                {
                    lastDocs.addClass("hidden");
                }

                $(".docs"+idFichier).removeClass("hidden");
                lastDocs = $(".docs"+idFichier);
                $("#contentDocs").removeClass("hidden");
            }
        });
    }
    else
    {
        $(".alertErrorJs").removeClass("hidden");
    }
});