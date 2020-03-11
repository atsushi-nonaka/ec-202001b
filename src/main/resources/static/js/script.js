$(function(){
	//郵便番号から住所を自動検索する関数
	$("#searchDestinationAddress").on("click",function(){
		AjaxZip3.zip2addr('destinationZipcode','','destinationAddress','destinationAddress');
	});
	
	$("#searchAddress").on("click",function(){
		AjaxZip3.zip2addr('zipcode','','address','address');
	});
	
	//カレンダー機能
	$.datepicker.setDefaults($.datepicker.regional["ja"]);
	$("#deliveryDate").datepicker({
		dateFormat: 'yy-mm-dd',
		minDate: new Date(),
		maxDate: '+5d'
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