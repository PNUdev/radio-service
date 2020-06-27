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
    </form>
</div>

<div class="container bg-light" style="visibility: hidden" id="resultDiv">
    <div class="row justify-content-center text-center">
        <iframe width="420" height="315" id="youtubePlayer">
        </iframe>
    </div>
    <div class="row justify-content-center text-center">
        <form method="post" action="/admin/videos/add">
            <input id="postLink" type="hidden" name="link" value="">
            <button class="btn btn-primary row m-4">Додати це відео</button>
        </form>
    </div>
</div>

<div class="container d-flex bg-light justify-content-center text-center" style="visibility: hidden" id="errorMsg">
    <h2>Відео за цим посиланням не знайдено</h2>
</div>

<script>

    const youtubeRegex = /http(?:s?):\/\/(?:www\.)?youtu(?:be\.com\/watch\?v=|\.be\/)([\w\-\_]*)(&(amp;)?‌​[\w\?‌​=]*)?/;
    const resultDiv = document.getElementById("resultDiv");
    const errorDiv = document.getElementById("errorMsg");
    const youtubePlayer = document.getElementById("youtubePlayer");
    const postLink = document.getElementById("postLink");

    function findVideo() {
        let link = document.getElementById("linkInput").value;
        let found = link.match(youtubeRegex);

        if (found == null) {
            resultDiv.style.visibility = "hidden"
            errorDiv.style.visibility = "visible"
        } else {
            let id = found[1];
            let playerScr = "https://www.youtube.com/embed/" + id;
            youtubePlayer.setAttribute("src", playerScr)
            console.log(youtubePlayer.getAttribute("src"));
            postLink.value = link;
            errorDiv.style.visibility = "hidden"
            resultDiv.style.visibility = "visible"
        }
    }

</script>


<#include "../include/footer.ftl">