 <!-- Copyright:   ${HRY_COMPANY} -->
 <!-- ${HRY_MODEL!}Add.html     -->
 <!-- @author:      ${codeVersion!}  -->
 <!-- @version:     V1.0             -->
 <!-- @Date:        ${codeDate!}     -->

${include_base!}
 <div class="centerRowBg centerRowBg_admin">
<div class="row">
    <div class="col-md-12">
        <h3 class="page-header">SysCustomer--Add  <button type="button"  class="btn btn-info-blue pull-right"  onclick="loadUrl('${ctx}/v.do?u=${getPath!}${HRY_MODEL_LC!}list')" > <i class="fa fa-mail-forward"></i>返回</button></h3>
    </div>
</div>


<form id="form" >

<div class="row">
	<div class="col-md-4 column">
		<#if columns ?? >
		<#list columns as c > 
		<#if c.key!="PRI">
		<div class="form-group">
			 <label for="${(c.column)!}">${(c.comments.name)!(c.column)!}</label>
			 <input type="text" class="form-control" name="${(c.column)!}" id="${(c.column)!}" />
		</div>
		</#if>
		</#list>
		</#if>
	</div>
</div>

<div class="row">
	<div class="col-md-2 column">
		<button type="button" id="addSubmit" class="btn btn-info-blue btn-block" >提交</button>
	</div>
</div>

</form>

</div>
<script type="text/javascript">
seajs.use("js${getPath!}${HRY_MODEL!}",function(o){
	o.add();
});
</script>




