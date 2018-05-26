

function assignToOfficeRequest(userId){
    let data = {userId:userId};
    $.ajax({
        url: "/app1/rest/office/request",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(data),
        success:
            function(result)
            {
                console.log("succ"+result);
                alert("Request approved");
                $("#"+data.userId).find("button").prop("disabled",true);
                $("#"+data.userId).find("button").text("Approved");
                //window.location.href = "/app1/restaurants";
            },
        error:
            function (xhr, status, error)
            {
                alert("Backend problems.Try later.");
                console.log("err"+xhr);
                console.log(xhr);
            }
    });

}

$(document).ready(function() {
    $("button").click(function(event){
        assignToOfficeRequest($(event.target).parent().attr("id"));
    })
});