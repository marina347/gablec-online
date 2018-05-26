function updateUserLunchWallet(userId, money){
    let data = {userId : userId, lunchWallet :money};
    console.log(data);
    $.ajax({
        url: "/app1/rest/wallet/request",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(data),

        success:
            function(result)
            {
                console.log("succ"+result);
                alert("Request approved");
                $("input").val("");
                location.reload();
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
        updateUserLunchWallet($(this).parent().attr("id"),$(this).siblings('input').val());
    })
});