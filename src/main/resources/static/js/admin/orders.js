function sendSMS(id){
    let data = {id:id};
    $.ajax({
        url: "/app1/rest/today-order/SMS",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(data),
        success:
            function(result)
            {
                console.log("succ"+result);
                alert("Success");
                location.reload();
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









function confirmOrder(id){
    let data = {id:id};
    $.ajax({
        url: "/app1/rest/today-order/confirm",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(data),
        success:
            function(result)
            {
                console.log("succ"+result);
                alert("Success");
                location.reload();
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

function orderArrived(id){
    let data = {id:id};
    $.ajax({
        url: "/app1/rest/today-order/arrived",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(data),
        success:
            function(result)
            {
                console.log("succ"+result);
                alert("Success");
                location.reload();
                //$("#"+data.id).find("#returnButton").prop("disabled",true);
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
    $(".confirmed").click(function(event){
        confirmOrder($(event.target).parent().attr("id"));
    })
    $(".arrived").click(function(event){
        orderArrived($(event.target).parent().attr("id"));
    })
    $(".SMS").click(function(event){
        sendSMS($(event.target).parent().attr("id"));
    })
});