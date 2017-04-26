$(document).ready(function () {
    var idProjet = $(".spanIdProjet").text();

    $('.treeview').each(function () {
        var tree = $(this);
        tree.treeview();
    });

    $("li.tree-branch").each(function () {
        var balise = $(this).find("span").first();
        if (balise) {
            balise.append("<i class='fa fa-plus addElementProjet' aria-hidden='true'></i>");
        }
    });

    $(document).on("click", ".addElementProjet", function (e) {
        e.preventDefault();

        var baliseA = $(this).prev();
        if (!baliseA) return false;

        var idDossier = baliseA.attr("data-id");
        var dossierRoot = baliseA.attr("data-root");

        //On dit si le dossier select est le dossier root ou non
        var inputDossierRoot = $("input[name='dossierRoot']");
        inputDossierRoot.val(dossierRoot ? "1" : "0");

        //On donne l'id du dossier select
        $("input[name='idDossier']").val(idDossier);

        $("#myModal").modal();
    });

    $(document).on("keydown", ".nameElementProjet", function () {
        //On récup le nom de fichier entré
        var filename = $(this).val();

        //On récup l'extension
        var extension = filename.replace(/^.*\./, '');

        //On récup le button submit du form
        var btnSubmitAddElementProjet = $(".btnSubmitAddElementProjet");
        if (extension && extension.length > 0) {
            btnSubmitAddElementProjet.prop("disabled", false);
        }
        else {
            btnSubmitAddElementProjet.prop("disabled", true);
        }
    });

    /* Demande de l'arborescence */
    $.post("/projet/" + idProjet + "/getArborescence", function (data) {
        if (data) {
            $(".newtreeview").html(data);
            $('.newtreeview').each(function () {
                var tree = $(this);
                tree.treeview();
            });

            $(".newtreeview li.tree-branch").each(function () {
                $("<i class='fa fa-plus addElementProjet' aria-hidden='true'></i>").insertAfter($(this).find("span").first());
                $(".addElementProjet").css("padding-left", "3px");
                $(".addElementProjet").css("color", "green");
            });
        }
    });

    $(".addElementProjet").css("padding-left", "3px");
    $(".addElementProjet").css("color", "green");

    $(document).on("click", ".deleteFileProjet", function () {
        var idFichier = $(this).attr("data-id");
        var domFichier = $(this);
        if (idFichier) {
            $.post("/projet/" + idProjet + "/deleteFile/" + idFichier, function (data) {
                if (data) {
                    if (data.success) {
                        toastr.success(data.message);
                        var li = domFichier.closest("li");
                        if(li)
                        {
                            li.remove();
                        }
                    }
                    else {
                        toastr.error(data.message);
                    }

                }
            });
        }
    });

    $(document).on("click", ".setFichierUnMain", function(){
        var idFichier = $(this).attr("data-id");
        var domFichier = $(this);
        if (idFichier) {
            $.post("/projet/" + idProjet + "/setUnMain/" + idFichier, function (data) {
                if (data) {
                    if (data.success) {
                        toastr.success(data.message);
                        domFichier.removeClass("fa-stop").addClass("fa-play");
                        domFichier.removeClass("setFichierUnMain").addClass("setFichierMain");
                    }
                    else {
                        toastr.error(data.message);
                    }

                }
            });
        }
    });

    $(document).on("click", ".setFichierMain", function(){
        var idFichier = $(this).attr("data-id");
        var domFichier = $(this);
        if (idFichier) {
            $.post("/projet/" + idProjet + "/setMain/" + idFichier, function (data) {
                if (data) {
                    if (data.success) {
                        toastr.success(data.message);
                        domFichier.removeClass("fa-play").addClass("fa-stop");
                        domFichier.removeClass("setFichierMain").addClass("setFichierUnMain");
                    }
                    else {
                        toastr.error(data.message);
                    }

                }
            });
        }
    });

    $('[data-toggle="tooltip"]').tooltip();
});