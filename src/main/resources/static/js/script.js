$(function(){
	//郵便番号から住所を自動検索する関数
	$(function(){
		$("#searchDestinationAddress").on("click",function(){
			AjaxZip3.zip2addr('destinationZipcode','','destinationAddress','destinationAddress');
		});
	});
	
	$(function(){
		$("#searchAddress").on("click",function(){
			AjaxZip3.zip2addr('zipcode','','address','address');
		});
	});
	
	//カレンダー機能
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional["ja"]);
		$("#deliveryDate").datepicker({
			dateFormat: 'yy-mm-dd',
			minDate: new Date(),
			maxDate: '+6M'
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