<style>
    #toast-container > div {
        opacity: 1;
    }
</style>

<script>

    toastr.options.positionClass = 'toast-top-center';

    <#if message??>
    $(document).ready(function () {
        toastr.success("${message}");
    });
    </#if>

    <#if error??>
    $(document).ready(function () {
        toastr.error("${error}");
    });
    </#if>

</script>