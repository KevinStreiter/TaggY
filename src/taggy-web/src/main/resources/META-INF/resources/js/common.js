var the_div;
var clickCount = 0;
var selected = [];

function show(input,text){
	the_div=input;
	clickCount++;
    if (clickCount === 1) {
        singleClickTimer = setTimeout(function() {
            clickCount = 0;
            singleClick();
            selected.push(text);
        }, 400);
    } else if (clickCount === 2) {
        clearTimeout(singleClickTimer);
        clickCount = 0;
        doubleClick();
    }
}
function setList(){
	
	document.getElementById("selectedPics").value=selected;
	alert(document.getElementById("selectedPics").value);
}
function singleClick() {
	if(the_div.style.borderStyle=="solid"){
		the_div.style.border = "thin none green";
		
	}else{
		
		the_div.style.border = "thin solid green";
	}
}

function doubleClick() {
    alert("double")
}
function showHiddenValue() { 
    alert(document.getElementById('hiddenField').value);	
    document.getElementById('hiddenField').value="hello";
 }
