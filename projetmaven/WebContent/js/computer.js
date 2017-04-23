
(function() {
    $('#introduced').datepicker();
    $('#discontinued').datepicker();
    console.log("lol zdzok");



    $('.input-group.date.introduced').datepicker({
        format: "yyyy-mm-dd"
    });

    $('.input-group.date.discontinued').datepicker({
        format: "yyyy-mm-dd"
    });

})();
