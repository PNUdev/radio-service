<#include "../include/header.ftl">

<div class="container d-flex">
    <form method="get" class="w-100 justify-content-center align-items-center">
        <div class="input-group mt-3 mb-1 row justify-content-center">
            <span class="input-group-text ">Посилання на YouTube відео</span>
        </div>
        <div class="row w-100 justify-content-center">
            <input type="url" id="linkInput" class="form-control w-50" name="link" <#if link??>value="${link}"</#if>
                   required>
        </div>
        <div class="row m-3 justify-content-center align-items-center">
            <button type="button" onclick="findVideo()" class="btn btn-primary row">Знайти відео</button>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>

<div class="container bg-light justify-content-center text-center" style="visibility: hidden" id="errorMsg">
    <h2>Відео за цим посиланням не знайдено</h2>
</div>

<div class="container bg-light" style="visibility: hidden" id="resultDiv">
    <div class="row justify-content-center text-center">
        <iframe class="mt-3" width="420" height="315" id="youtubePlayer">
        </iframe>
    </div>
    <div class="row justify-content-center text-center">
        <form method="post">
            <input id="postLink" type="hidden" name="link" value="">
            <button class="btn btn-primary row m-4">Додати це відео</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>



<script>

    const youtubeRegex = /http(?:s?):\/\/(?:www\.)?youtu(?:be\.com\/watch\?v=|\.be\/)([\w\-\_]*)(&(amp;)?‌​[\w\?‌​=]*)?/;
    let resultDiv = $("#resultDiv");
    let errorDiv = $("#errorMsg");
    let youtubePlayer = $("#youtubePlayer");
    let postLink = $("#postLink");
    const notFoundImgWidth = 120;

    function findVideo() {

        hideResultAndError();

        let link = $("#linkInput").val();

        let found = link.match(youtubeRegex);

        if (found == null) {
            showError();
            return;
        }

        let id = found[1];
        let img = new Image();
        img.src = "http://img.youtube.com/vi/" + id + "/mqdefault.jpg";

        img.onload = function () {
            if (this.width === notFoundImgWidth) {
                showError();
            } else {
                let playerScr = "https://www.youtube.com/embed/" + id;
                youtubePlayer.attr("src", playerScr);
                postLink.val(link);
                showResult();
            }
        }
    }

    function showResult() {
        errorDiv.css("visibility", "hidden");
        resultDiv.css("visibility", "visible");
    }

    function showError() {
        errorDiv.css("visibility", "visible");
        resultDiv.css("visibility", "hidden");
    }

    function hideResultAndError() {
        errorDiv.css("visibility", "hidden");
        resultDiv.css("visibility", "hidden");
    }


</script>


<#include "../include/footer.ftl">