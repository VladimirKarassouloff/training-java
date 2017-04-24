
(function() {

    $('.input-group.date.introduced').datepicker({
        format: "yyyy-mm-dd"
    });

    $('.input-group.date.discontinued').datepicker({
        format: "yyyy-mm-dd"
    });

    var errorDom = $("#error");
    var nameForm = $("#computerName");

    $("#myform").submit( function(event) {
        if(nameForm.val() === "" ) {
            errorDom.html("Name should not be empty");
            event.preventDefault();
        }

    });

})();
