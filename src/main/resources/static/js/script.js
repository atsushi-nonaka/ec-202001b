$(function(){
	//郵便番号から住所を自動検索する関数
	$(function(){
		$("#searchAddress").on("click",function(){
			AjaxZip3.zip2addr('destinationZipcode','','destinationAddress','destinationAddress');
		});
	});
	
	//クレジット決済について
	$(".inputCreditInformation").hide();
	
	$("#creditCard").on("change", function(){
		$(".inputCreditInformation").show();
	});
	
	$("#cash").on("change", function(){
		$(".inputCreditInformation").hide();
	});
	
});