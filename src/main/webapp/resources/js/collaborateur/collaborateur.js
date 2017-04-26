$(document).ready(function () {
    var requestAjax = null;
    var tableCollaborateurs = $(".table-collaborateur");
    var tableCollaborateursTbody = tableCollaborateurs.find("tbody");
    var idProjet = $(".idProjet").text();

    if ($(".search-collaborateur").length && idProjet) {
        $(".idProjet").remove();
        $(document).on("keyup", ".search-collaborateur", function () {
            if (requestAjax != null) {
                requestAjax.abort();
            }

            var query = $(this).val();
            requestAjax = $.post("/projet/" + idProjet + "/collaborateur/searchCollaborateur", {emailUser: query},
                function (data) {
                    var response = "";

                    if (data && data != "") {
                        $.each(JSON.parse(JSON.stringify(data)), function () {
                            var element = $(this)[0];
                            console.log($(this));
                            console.log(element);
                            response += "<tr>";
                            response += "<td>" + element.email + "</td>";
                            response += "<td><a href='/projet/";
                            response += idProjet;
                            response += "/collaborateur/add/";
                            response += element.id;
                            response += "' class='btn btn-primary' role='button'>Ajouter au projet</a></td>";
                            //response += "<td><a href='/projet/"+idProjet+"/collaborateur/add/"+element.id+"' class='btn btn-success' role='button'>Ajouter au projet</a></td>";
                            response += "</tr>";
                        });
                    }
                    else
                    {
                        response = "<tr><td><div class='alert alert-info'>Aucun collaborateur avec cet email</div></td></tr>";
                    }

                    tableCollaborateurs.removeClass("hidden");
                    tableCollaborateursTbody.empty();
                    tableCollaborateursTbody.html(response);
                });
        });
    }
});