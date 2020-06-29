<#include "../include/header.ftl">
<div class="w-100">
    <div class="col-md-5 jumbotron mx-auto mt-5">
        <div class="h3">Ви впевнені, що справді хочете видалити програму <b>${program.title}</b>
            (буде також видалено всі записи з розкладу, пов'язані з цією програмою)?
        </div>
        <div class="row d-flex justify-content-end mt-5">
            <a href="/admin/programs/edit/${program.id}">
                <div class="btn btn-outline-primary mx-4">Ні, відмінити дію</div>
            </a>
            <form method="POST">
                <button class="btn btn-outline-danger mx-4">Так, видалити!</button>
            </form>
        </div>
    </div>
</div>
<#include "../include/footer.ftl">