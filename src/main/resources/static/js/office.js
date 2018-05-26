

function assignToOfficeRequest(officeId){
    let data = {officeId:officeId};
    $.ajax({
        url: "/app1/rest/office",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(data),
        success:
            function(result)
            {
                console.log("succ"+result);
                alert("Your request has been forwarded to admin. We will send you notification when the request is approved.");
                //window.location.href = "/app1/restaurants";
            },
        error:
            function (xhr, status, error)
            {
                console.log("err"+xhr);
                console.log(xhr);
            }
    });

}

$(document).ready(function() {
    $(".office-display").click(function(event){
        assignToOfficeRequest(event.currentTarget.id);
    })
});