

$(document).ready(function() {
    $("#confirm_order").click(function() {

        $.ajax({
            url: "/app1/cart/make-order",
            contentType: "application/json",
            method: "POST",
            data: null,
            success:
                function(result)
                {
                    if(result)
                    {
                        //redirect to show all orders
                        console.log("fyea");
                        window.location.href = "/app1/history";
                    }
                    else
                    {
                        alert("Not possible to confirm your order - check your wallet state or restaurant order time!");
                    }
                },
            error:
                function (xhr, status, error)
                {
                    console.log("err"+xhr);
                    console.log(xhr);
                }
        });
    });

});


