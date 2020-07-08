<style>
    #toast-container > div {
        opacity: 1;
    }
</style>

<script>

    toastr.options.positionClass = 'toast-top-center';

    <#if flashMessage??>
    $(document).ready(function () {
        toastr.success("${flashMessage}");
    });
    </#if>

    <#if flashErrorMessage??>
    $(document).ready(function () {
        toastr.error("${flashErrorMessage}");
    });
    </#if>

</script>