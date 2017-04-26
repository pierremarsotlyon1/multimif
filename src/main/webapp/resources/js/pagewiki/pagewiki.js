$(document).ready(function(){
   $("#summernote").summernote({
       height: 300,                 // set editor height
       minHeight: null,             // set minimum height of editor
       maxHeight: null,             // set maximum height of editor
   });

    $(document).on("click", ".btnSubmitPage", function(e){
        e.preventDefault();

       $("input#description").val($("#summernote").summernote('code'));

        $(".formSendPage").submit();
    });
});