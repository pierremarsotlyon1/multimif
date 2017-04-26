$(document).ready(function(){
    var myCodeMirror = CodeMirror.fromTextArea(document.getElementById("textarea-codemirror"), {
        value: "// type your code here\n",
        mode:  "text/x-java",
        theme: "dracula",
        lineNumbers: true,
        lineWrapping: true,
        styleActiveLine: true,
        extraKeys: {"Ctrl-Space": "autocomplete"},
        /*extraKeys: {
            "Ctrl-Space" : function(cm) {
                CodeMirror.commands.showHint(cm, null, {completeSingle : false});
            }
        }*/
    });
    /*myCodeMirror.on("keyup", function (cm, event) {
        if (!cm.state.completionActive &&
            event.keyCode != 13) {
            CodeMirror.commands.autocomplete(cm, null, {completeSingle: false});
        }
    });
    myCodeMirror.on("keyup",function(cm){
        CodeMirror.showHint(cm,CodeMirror.hint.deluge,{completeSingle: false});
    });*/
});