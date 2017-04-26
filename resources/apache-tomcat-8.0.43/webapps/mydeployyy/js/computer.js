$(document).ready(function () {

    $('.input-group.date.introduced').datepicker({
        format: "yyyy-mm-dd"
    });

    $('.input-group.date.discontinued').datepicker({
        format: "yyyy-mm-dd"
    });


    $('#myform').bootstrapValidator({
        framework: 'bootstrap',

        fields: {
            name_computer: {
                validators: {
                    notEmpty: {
                        message: 'A computer must have a name'
                    }
                }
            }/*,
            introduced_computer: {
                validators: {
                    date: {
                        format: 'YYYY-MM-DD',
                        message: 'The value is not a valid date'
                    }
                }
            },
            discontinued_computer: {
                validators: {
                    date: {
                        format: 'YYYY-MM-DD',
                        message: 'The value is not a valid date'
                    }
                }
            },
            company_id_computer: {

            }*/
        }
    });

});
