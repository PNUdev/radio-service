<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>

<style>
    #toast-container > div {
        opacity: 1;
    }
</style>

<script>

    toastr.options.positionClass = 'toast-top-center';

    <#if flashMessageSuccess??>
    $(document).ready(function () {
        toastr.success("${flashMessageSuccess}");
    });
    </#if>

    <#if flashMessageError??>
    $(document).ready(function () {
        toastr.error("${flashMessageError}");
    });
    </#if>

</script>