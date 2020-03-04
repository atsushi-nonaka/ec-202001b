$(function(){
	//郵便番号から住所を自動検索する関数
	$("#searchAddress").on("click",function(){
		$.ajax({
			url: "http://zipcoda.net/api",
			type : "POST",
			dataType : "jsonp",
			data : {
				zipcode : $("#inputZipcode").val()
			},
			async:true
		}).done(function(data){
			console.log(data);
			console.dir(JSON.stringify(data));
			$("#inputAddress").val(data.items[0].address);
		}).fail(function(XMLHttpRequest, textStatus,errorThrown){
			alert("エラーが発生しました");
			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
			console.log("textStatus : " + textStatus);
			console.log("errorThrown : " + errorThrown.message);
		})
	});
	
});