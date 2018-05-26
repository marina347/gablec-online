
$(document).ready(function() {

    $('[id^=detail-]').hide();
    $('.toggle').click(function() {

        $input = $( this );
        var len = $input.closest("restaurant-offer-container").find(".article-chosen");
        if(len.length == 0)
        {
            $target = $('#'+$input.attr('data-toggle'));
            $target.slideToggle();
            if($input.hasClass("restaurant-heading"))
            {
                $input.toggleClass("restaurant-card-expanded");
            }
        }
        $('[data-toggle="tooltip"]').tooltip()

    });
    $(".category-articles").each(function () {
        if ( $(this).children().length == 0 ) {
            $(this).parent().remove();
        }
    })

    $(".category-article").click(function() {

        $input = $(this);
        $input.toggleClass("article-chosen");
        if($input.hasClass("article-chosen"))
        {
            addArticleToSummary($input);
        }
        else
        {
            removeArticleFromSummary($input);
        }
        $('[data-toggle="tooltip"]').tooltip()
    });

    $(".button-quantity").click(function(e)
    {
        $input = $(this);
        quantityClick($input);
        if (e.stopPropagation)
            e.stopPropagation();
    });

    $(".proposition").click(function(e)
    {
        $input = $(this);
        let id = parseInt($input.attr("id").split("_")[1]);
        if($("#"+id).hasClass("article-chosen"))
            quantityClick($("#"+id).find(".button-quantity-plus"));
        else{
            $("#"+id).toggleClass("article-chosen");
            addArticleToSummary($("#"+id));
            $('[data-toggle="tooltip"]').tooltip();
        }

        if (e.stopPropagation)
            e.stopPropagation();
    });

    $("#filter_restaurants").keyup(function()
    {
        if(event.target.value.length>=1)
        {
            filterRestaurants(event.target.value);
        }
        else{
            showAllRestaurants();
        }
    });

    setUpPage(cartFoodList);


});

function quantityClick($input)
{
    var val = 0;
    let foodId = $input.parent().parent().attr("id");
    if($input.hasClass("button-quantity-minus"))
    {

        val = parseInt($input.next().text());
        if(val!==1){
            val--;
            decItemCount(foodId);
        }

        $input.next().html(val+"");
    }
    else
    {
        val = parseInt($input.prev().text());
        val++;
        incItemCount(foodId);
        $input.prev().html(val+"");
    }
    handleValueChange($input.parent().parent());
    $('[data-toggle="tooltip"]').tooltip()
}

function setUpPage(cartFoodList)
{
    console.log(cartFoodList);
    let arrayLength = cartFoodList.length;
    for (let i = 0; i < arrayLength; i++) {
        console.log(cartFoodList[i]);
        let input = $("#"+cartFoodList[i].food.id);
        input.find(".article-quantity").text(cartFoodList[i].count);
        input.toggleClass("article-chosen");
        addArticleToSummary(input, false);
    }

    $('[data-toggle="tooltip"]').tooltip()

}

function showAllRestaurants()
{
    $(".restaurant-offer-container").css("display", "");
}

function hideAllRestaurants()
{
    $(".restaurant-offer-container").css("display", "none");
}

function showSelectedRestaurants(input)
{
    let toBeShown = $("[data-restaurant-name*='"+input+"' i] ");
    toBeShown.css("display", "");
}

function filterRestaurants(input)
{
    hideAllRestaurants();
    showSelectedRestaurants(input);
}

function calculateTotal()
{

    let total = 0.0;
    $(".article-chosen").each(function () {
            let articlePrice = parseFloat($(this).find(".article-price").attr("data-price"));
            let quantity = parseFloat($(this).find(".article-quantity").text());
            total += articlePrice * quantity;

    })
    return total;
}

function getTotalFormated()
{
    return  calculateTotal().toFixed(2).toString().replace(".",",");
}

function getSummaryContentTemplate(articleName, count, price, currency, restaurantName)
{
    return `
        <span data-toggle="tooltip" title="${restaurantName}"  class="article-data-summary bolded">${articleName} x ${count} </span><br/>
        <span class="dots"></span>
        <span class="article-price-summary bolded">${parseFloat((count*price)).toFixed(2).toString().replace(".",",")}</span>
        <span class="bolded">${currency}</span>`;
}

function getArticleSummaryTemplate(articleId, articleName, count, price, currency, restaurantName)
{
    console.log(count*price);
    console.log(price);
    console.log(count);
    return `<div id="summary_${articleId}" class="article-item-summary">
                ${getSummaryContentTemplate(articleName,count,price,currency,restaurantName)}
            </div>`;
}

function handleValueChange(parentNode)
{
    $("#sum_value").html(getTotalFormated());
    let restaurantName = parentNode.closest(".restaurant-offer-container").attr("data-restaurant-name");
    console.log(restaurantName);
    $("#summary-content").find("#summary_"+parentNode.attr("id")).html(getSummaryContentTemplate(
        parentNode.find(".article-data").text(),
        parseFloat(parentNode.find(".article-quantity").text()),
        parseFloat(parentNode.find(".article-price").attr("data-price")),
        " HRK",
        restaurantName));
}

function addArticleToSummary(input, doApi = true)
{
    $("#sum_value").html(getTotalFormated());
    let restaurantName = input.closest(".restaurant-offer-container").attr("data-restaurant-name");
    $("#summary-content").append(getArticleSummaryTemplate(
        input.attr("id"),
        input.find(".article-data").text(),
        parseFloat(input.find(".article-quantity").text()),
        parseFloat(input.find(".article-price").attr("data-price")),
        " HRK",
        restaurantName))
    let foodId = parseInt(input.attr("id"));
    let count = parseInt(input.find(".article-quantity").text());
    if(doApi)
        putItemToCartApi(foodId,count);
}

function removeArticleFromSummary(input, doApi = true) {
    $("#sum_value").html(getTotalFormated());
    $("#summary-content").find("#summary_"+$input.attr("id")).remove();
    let foodId = parseInt(input.attr("id"));
    if(doApi)
        removeItemFromCart(foodId);
}


function checkout()
{                 window.location.href = "/app1/checkout";
/*
    let data = [];
    $(".article-chosen").each(function () {
        let item = {};
        item.foodId = parseInt($(this).attr("id"));
        item.count = parseInt($(this).find(".article-quantity").text());
        data.push(item);
    })
    console.log(data);

     $.ajax({
         url: "/app1/cart",
         contentType: "application/json",
         method: "POST",
         data: JSON.stringify(data),
         success:
             function(result)
             {
                console.log("succ"+result);
                 window.location.href = "/app1/checkout";
             },
         error:
             function (xhr, status, error)
             {
                console.log("err"+xhr);
                console.log(xhr);
             }
     });
*/

}

function putItemToCartApi(foodId, count)
{

    let data = {foodId:foodId, count:count};
    console.log(data);

    $.ajax({
        url: "/app1/cart/put-item",
        contentType: "application/x-www-form-urlencoded",
        method: "POST",
        data: data,
        success:
            function(result)
            {
                console.log("succ"+result);

            },
        error:
            function (xhr, status, error)
            {
                console.log("err"+xhr);
                console.log(xhr);
            }
    });
}

function incItemCount(foodId)
{

    let data = {foodId:foodId};
    console.log(data);

    $.ajax({
        url: "/app1/cart/increase-count-item",
        contentType: "application/x-www-form-urlencoded",
        method: "POST",
        data: data,
        success:
            function(result)
            {
                console.log("succ"+result);

            },
        error:
            function (xhr, status, error)
            {
                console.log("err"+xhr);
                console.log(xhr);
            }
    });
}

function decItemCount(foodId)
{

    let data = {foodId:foodId};
    console.log(data);

    $.ajax({
        url: "/app1/cart/decrease-count-item",
        contentType: "application/x-www-form-urlencoded",
        method: "POST",
        data: data,
        success:
            function(result)
            {
                console.log("succ"+result);

            },
        error:
            function (xhr, status, error)
            {
                console.log("err"+xhr);
                console.log(xhr);
            }
    });
}

function removeItemFromCart(foodId)
{

    let data = {foodId:foodId};
    console.log(data);

    $.ajax({
        url: "/app1/cart/remove-item",
        contentType: "application/x-www-form-urlencoded",
        method: "POST",
        data: data,
        success:
            function(result)
            {
                console.log("succ"+result);

            },
        error:
            function (xhr, status, error)
            {
                console.log("err"+xhr);
                console.log(xhr);
            }
    });
}



