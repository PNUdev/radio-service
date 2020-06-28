<#include "../include/header.ftl">

<table class="table my-5">
    <thead class="thead-dark">
    <tr>
        <th scope="col" colspan="4" class="text-center h3">${dailySchedule.dayOfWeekName}</th>
        <th>
            <div class="btn btn-lg btn-primary float-right mr-3">Додати</div>
        </th>
    </tr>
    </thead>
    <thead class="thead-light">
    <tr>
        <th scope="col">Назва програми</th>
        <th scope="col">Коментар</th>
        <th scope="col">Початок</th>
        <th scope="col">Кінець</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>

    <#list dailySchedule.scheduleItems as program>
        <tr>
            <th scope="row">${program.programName}</th>
            <td>
                <textarea class="form-control" rows="6" cols="60">${program.comment}</textarea>
            </td>
            <td>
                <input class="form-control" type="time" value="${program.time.startTime}">
            </td>
            <td>
                <input class="form-control" type="time" value="${program.time.endTime}">
            </td>
            <td>
                <div class="btn btn-primary m-2">Зберегти</div>
                <div class="btn btn-danger m-2">Видалити</div>
            </td>
        </tr>
    </#list>
    </tbody>
</table>


<#include "../include/footer.ftl">