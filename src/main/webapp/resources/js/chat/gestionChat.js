$(document).ready(function(){
    var spanEmailUser = $(".emailUser");
    var spanIdProjet = $(".idProjet");
    var emailUser = spanEmailUser.text();
    var idProjet = spanIdProjet.text();

    if(emailUser && idProjet)
    {
        spanEmailUser.remove();
        spanIdProjet.remove();
        console.log("init");

        var socket = io('http://'+host + ":27017");
        console.log("fin get");
        socket.on('connect', function(){

            $(".blockAlertSocketConnexion").addClass("hidden");
            $(".blockChat").removeClass("hidden");

            console.log("connect");
            //On demande de rejoindre le chat du projet
            var obj = {
                idSalon: idProjet,
                email: emailUser
            };

            socket.emit("joinSalon", $.toJSON(obj));

            socket.on("canJoinSalon", function(){
                socket.emit("getLastMessages", $.toJSON(obj));

                socket.on("setLastMessages", function(data){
                    if(data)
                    {
                        var json;
                        try {
                            json = JSON.parse(data);
                        } catch (exception) {
                            json = null;
                            console.log("json nok");
                        }

                        if (json) {
                            $.each(json, function(){
                                var element = $(this)[0];
                                receiveMessage(element);
                            })
                        }
                    }
                });

                $(document).on("click", "#btn-chat", function(){
                    var message = $(".messageChat").val();
                    if(message)
                    {
                        obj.message = message;
                        socket.emit("sendMessage", $.toJSON(obj));
                    }
                });

                socket.on("receiveMessage", function(data){
                    var json;
                    try {
                        json = JSON.parse(data);
                    } catch (exception) {
                        json = null;
                    }

                    if (json) {
                        receiveMessage(json);
                    }
                });

                function receiveMessage(json)
                {
                    var response = "<li class='left clearfix'><span class='chat-img pull-left'>";
                    response += "<img src='http://placehold.it/50/55C1E7/fff&text=U' alt='User Avatar' class='img-circle'/>";
                    response += "</span>";
                    response += "<div class='chat-body clearfix'>";
                    response += "<div class='header'>";
                    response += "<strong class='primary-font'>"+json.email+"</strong>";
                    response += "</div>";
                    response += "<p>";
                    response += json.message;
                    response += "</p>";
                    response += "</div>";
                    response += "</li>";

                    $(".chat").append(response);
                }
            });
        });

        $.get("/chatSocket", function(){

        });
    }
});