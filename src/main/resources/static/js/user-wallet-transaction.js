
function borrowMoneyRequest(from,to,amount){
    let data = {userFrom:from,userTo:to,amount:amount};
    $.ajax({
        url: "/app1/rest/borrow/request",
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

function returnMoneyRequest(id){
    let data = {id:id};
    $.ajax({
        url: "/app1/rest/borrow/return-request",
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
    $("#mybutton").click(function(event){
        borrowMoneyRequest($(this).parent().attr("id"),$('#myselect option:selected').val(),$(this).siblings('input').val());
    })
    $(".forReturn").click(function(event){
        returnMoneyRequest($(event.target).parent().attr("id"));
    })
});