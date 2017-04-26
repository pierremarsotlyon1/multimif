$(document).ready(function () {
    var error = $(".message-error");
    var success = $(".message-success");

    if (error) {
        var message = error.html();
        if (message) {
            toastr.error(message);
            /*var alert = generateAlert(false, false, true, message);
            if (alert) {
                $("body").prepend(alert);
            }*/
        }

        error.remove();
    }

    if (success) {
        var message = success.html();
        if (message) {
            toastr.success(message);
            /* var alert = generateAlert(true, false, false, message);
             if (alert) {
                 $("body").prepend(alert);
             }*/
        }

        success.remove();
    }

    function generateAlert(success, warning, danger, message) {
        var classe = success == true ? "success" : warning ? "warning" : danger ? "danger" : "success";

        return "<div class='container'>" +
            "<div class='row text-center'>" +
            "<div class='col-md-12'></div>" +
            "<div class='alert alert-" + classe + " alert-dismissible fade in' " +
            "role='alert'> " +
            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>×</span>" +
            "</button>" + message + "</div></div></div></div>";
    }
});