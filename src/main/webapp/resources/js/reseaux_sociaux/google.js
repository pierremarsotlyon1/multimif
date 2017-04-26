var gpclass = (function(){

    //Var globale
    var response = undefined;
    return {
        //Fonction / objets de la classe
        googleLogIn: function(response)
        {
            //Si c'est un sigin automatique, on stop
            if(response.status.method && response.status.method === "AUTO") return false;

            //On regarde si on a le tocken de l'user
            if (response['access_token']) {

                //On demande la connexion à google
                gapi.client.load('plus','v1',this.getUserInformationLogIn);

            } else if (response['error']) {
                //Si il y a une erreur
            }
        },
        getUserInformationLogIn: function(){
            var request = gapi.client.plus.people.get( {'userId' : 'me'} );
            request.execute( function(profile) {
                console.log(profile);
                var email = gpclass.getEmailFromProfile(profile);
                var password = gpclass.getPasswordFromProfile(profile);

                if(email && password)
                {
                    $(".emailLogInGoogle").val(email);
                    $(".passwordLogInGoogle").val(password);
                    $(".formLogInGoogle").submit();
                }
                else
                {

                }
            });
        },
        googleSignIn:function(response){
            //Si c'est un sigin automatique, on stop
            if(response.status.method && response.status.method === "AUTO") return false;

            //On regarde si on a le tocken de l'user
            if (response['access_token']) {

                //On demande la connexion à google
                gapi.client.load('plus','v1',this.getUserInformationSignIn);

            } else if (response['error']) {
                //Si il y a une erreur
            }
        },
        getUserInformationSignIn: function(){
            var request = gapi.client.plus.people.get( {'userId' : 'me'} );
            request.execute( function(profile) {
                console.log(profile);
                var email = gpclass.getEmailFromProfile(profile);
                var password = gpclass.getPasswordFromProfile(profile);

                if(email && password)
                {
                    $("#emailGoogle").val(email);
                    $(".passwordRegisterGoogle").val(password);
                    $(".passwordConfirmRegisterGoogle").val(password);
                    $(".formGoogle").submit();
                }
                else
                {

                }
            });
        },
        getPasswordFromProfile: function(profile)
        {
            return profile["id"];
        },
        getEmailFromProfile: function(profile)
        {
            return  profile['emails'].filter(function(v) {
                return v.type === 'account'; //On récup l'email principal
            })[0].value;
        }
    };
})();

function googleSignIn(gpSignInResponse){
    gpclass.googleSignIn(gpSignInResponse);
}

function googleLogIn(gpSignInResponse){
    gpclass.googleLogIn(gpSignInResponse);
}

$(document).ready(function(){
   var textButtonGoogle = $(".tLb");
    if(textButtonGoogle)
    {
        textButtonGoogle.html("Se connecter avec Google");
    }
});