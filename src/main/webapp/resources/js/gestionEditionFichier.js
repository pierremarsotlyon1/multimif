$(document).ready(function(){
    var idFichier;
    var message = $(".messages");
    var inputToSent = $(".valueToSent");
    var btnSend = $(".btnSend");
    var emptyMainFile = $(".emptyMainFile");
    var formCompilation = $(".formCompilation");
    var pathfichier;
    var myCodeMirror = null;


    var hiddenCodeMirror = true;
    var lastFichierClicked;
    var idProjet = $(".spanIdProjet").text();
    if(document.getElementById("textarea-codemirror")!=null) {
        myCodeMirror = CodeMirror.fromTextArea(document.getElementById("textarea-codemirror"), {
            value: "// Type your code here\n",
            mode: "text/x-java",
            theme: "dracula",
            /*lineNumbers: true,
            lineWrapping: true,
            styleActiveLine: true,
            autofocus: true,
            indentWithTabs: true,*/
            extraKeys: {
                "F10": function (cm) {
                    cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                },
                "Esc": function (cm) {
                    if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
                },
                "Ctrl-Space": "autocomplete"
            }
        });
    }else{
        myCodeMirror = CodeMirror.fromTextArea(document.getElementById("textarea-codemirror-read-only"), {
            value: "// Type your code here\n",
            mode:  "text/x-java",
            theme: "dracula",
            /*lineNumbers: true,
            lineWrapping: true,
            styleActiveLine: true,*/
            readOnly: true,
            extraKeys: {
                "F10": function(cm) {
                    cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                },
                "Esc": function(cm) {
                    if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
                }
            }
        });
    }

    $.get("/editionProjet", function(){
        var socket = io('http://'+host+':27018');
        socket.on('connect', function(){
            $(".blockAlertSocketConnexion").addClass("hidden");
            $(".blockResume").removeClass("hidden");

            myCodeMirror.setOption("value","// Type your code here\n")
            myCodeMirror.setOption("extraKeys", {
                "Ctrl-S": function (cm) {
                    socket.emit('update', idFichier)
                },
                "F10": function (cm) {
                    cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                },
                "Esc": function (cm) {
                    if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
                },
                "Ctrl-Space": "autocomplete"
            });
            //On demande l'arborescence
            $.post("/projet/"+idProjet+"/getArborescence", function(data){
                $(".newtreeview").html(data);
                $('.newtreeview').each(function () {
                    var tree = $(this);
                    tree.treeview();
                });

                $(".newtreeview li.tree-branch").each(function(){
                    $("<i class='fa fa-plus addElementProjet' aria-hidden='true'></i>").insertAfter($(this).find("span").first());
                    $(".addElementProjet").css("padding-left","3px");
                    $(".addElementProjet").css("color","green");
                });
            });

            socket.on("canprogress", function(data)
            {
                var json = jQuery.parseJSON(data);
                idFichier = json.idFichier;

                myCodeMirror.setValue(json.contentFichier);
            });

            socket.on('disconnect', function(){

            });

            socket.on('updateuser',function(data) {
                $(".blockUserEditionFichier").removeClass("hidden");
                if (data > "1") {
                    $(".nbUserEditionFichier").html(data + " utilisateurs qui éditent ce fichier");
                }
                else {
                    $(".nbUserEditionFichier").html(data + " utilisateur qui édite ce fichier");
                }
            });

            $(document).on("click", ".btnSend", function(){
                var obj = {
                    idFichier : idFichier,
                    positionX : 0,
                    positionY : 0,
                    text : 'c'
                };
                socket.emit('message', $.toJSON(obj));
            });

            $(document).on("click",".fichier",function(){
                pathfichier = $(this).attr("data-path");
                var extension = $(this).text().split(".")[1];
                switch(extension){
                    case("java"):
                        myCodeMirror.setOption("mode","text/x-java");
                        break;
                    case("py"):
                        myCodeMirror.setOption("mode","text/x-python");
                        break;
                    case("xml"):
                        myCodeMirror.setOption("mode","application/xml");
                        break;
                    case("html"):
                        myCodeMirror.setOption("mode","text/html");
                        break;

                    case("h"):
                        //Fall-through
                    case("c"):
                        myCodeMirror.setOption("mode","text/x-csrc");
                        break;

                    case("hpp"):
                    //Fall-through
                    case("cpp"):
                        myCodeMirror.setOption("mode","text/x-c++src");
                        break;
                }
                var obj = {
                    oldId : idFichier,
                    idFichier : $(this).attr("data-id"),
                    pathFichier : pathfichier
                };

                if(socket)
                {
                    socket.emit('setupIdFile',$.toJSON(obj));
                    if(hiddenCodeMirror)
                    {
                        $(".blockCodeMirror").removeClass("hidden");
                        $(".saveicon").removeClass("hidden");
                        hiddenCodeMirror = false;
                    }

                    //On regarde si un autre fichier était en édition avant celui la
                    if(lastFichierClicked)
                    {
                        lastFichierClicked.removeClass("text-navy");
                    }

                    //On met le nom du fichier en vert
                    $(this).addClass("text-navy");
                    lastFichierClicked = $(this);
                }
            });

            socket.on('change',function(data){
                var cursor = myCodeMirror.doc.getCursor();
                var pos = myCodeMirror.getScrollInfo();
                var json = jQuery.parseJSON(data.replace("\\",""));
                myCodeMirror.doc.replaceRange(json.text,json.from,json.to,"setValue");
                myCodeMirror.doc.setCursor(cursor);
                myCodeMirror.scrollTo(pos.left,pos.top);
            });

            myCodeMirror.on('change', function(cm,change){
                if(change.origin != "setValue"){
                    var obj = {
                        idFichier : idFichier,
                        fromLine : change.from.line,
                        toLine : change.to.line,
                        fromCh : change.from.ch,
                        toCh : change.to.ch,
                        text : change.text,
                        removed : change.removed,
                        json : $.toJSON(change)
                    };
                    socket.emit('change', $.toJSON(obj));
                }
            });
            $(document).on("click", ".saveicon", function() {
                socket.emit('update', idFichier);
            });
            $(document).on("click", ".btn-compil", function(){
                socket.emit('update',idFichier);
                var idProjet = $(".spanIdProjet").text();
                $.post("/projet/"+ idProjet + "/compilation", {}, function(data){
                    $("#execArea").html(data);
                    $("#resultModal").modal("show");
                    //textarea change
                });
            });
        });
    });

    $(document).on("change",".selectTypeElement",function(){
        var val = $(this).find("option:selected").val();
        switch(val){
            case("1"):
                $(".checkboxline").removeClass("hidden");
                break;
            case("2"):
                $("#execcheckbox").prop("checked", false);
                $(".checkboxline").addClass("hidden");
                break;
        }
    });

    $('.btn-launch').on('click',function () {
        var idProjet = $(".spanIdProjet").text();
        var mainF = $("#mainFile option:selected").text();
        $.post("/projet/"+ idProjet + "/execution", {mainFile:mainF}, function(data){
            $("#execArea").html(data);
            $("#resultModal").modal("show");
            //textarea change
        });
    });
    $('.btn-maven').on('click',function () {
        var idProjet = $(".spanIdProjet").text();
        var commands = $("#mavenCommands").val();
        $.post("/projet/"+ idProjet + "/maven", {commands:commands}, function(data){
            $("#execArea").html(data);
            $("#resultModal").modal("show");
            //textarea change
        });
    });


    $('#execModal').on('show.bs.modal', function(){
        var idProjet = $(".spanIdProjet").text();

        $.post("/projet/"+ idProjet + "/execlist", {}, function(data){

            var obj = jQuery.parseJSON(data);

            //Si on trouve aucun fichier main, on affiche l'erreur
            if(obj == null || obj.length == 0)
            {
                emptyMainFile.removeClass("hidden");
            }
            //Sinon on génére la liste et on affiche le formulaire
            else
            {
                var str = "<select id='mainFile' class='form-control'>";

                $.each(obj, function(index,value){
                    str += "<option value='"+value+"'>"+value+"</option>";
                });

                str += "</select>";

                $(".execSelect").html(str);
                formCompilation.removeClass("hidden");
            }
        });
    });

    $('#execModal').on('hidden.bs.modal', function(){
        if(!emptyMainFile.hasClass("hidden"))
        {
            emptyMainFile.addClass("hidden");
        }
        if(!formCompilation.hasClass("hidden"))
        {
            formCompilation.addClass("hidden");
        }
    });
});