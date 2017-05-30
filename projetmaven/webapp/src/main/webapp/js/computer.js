$(function() {

    $('.input-group.date.introduced').datepicker({
        format: "yyyy-mm-dd"
    });

    $('.input-group.date.discontinued').datepicker({
        format: "yyyy-mm-dd"
    });

    $("#myform").bootstrapValidator({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name_computer: {
                validators: {
                    notEmpty: {
                        message: 'The name is required and cannot be empty'
                    }
                }
            }
        }
    });

});