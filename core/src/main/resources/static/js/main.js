$(document).ready(function () {

    $("#search-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/nroad/request-details?requestIds="+$("#requestId").val(),
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Document Details Json</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#status').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Final Json</h4><pre>"
                + e.responseText + "</pre>";
            $('#status').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}