var the_div;
var clickCount = 0;
var selected = [];


function show(input,text){
	the_div=input;
	clickCount++;
    if (clickCount === 1) {
        singleClickTimer = setTimeout(function() {
        clickCount = 0;
        singleClick(text);
        }, 400);
    } else if (clickCount === 2) {
        clearTimeout(singleClickTimer);
        clickCount = 0;
        doubleClick(text);
    }
}
function setList(){
	document.getElementById("selectedPics").value=selected;
	//alert(document.getElementById("selectedPics").value);
}
function singleClick(text) {
	if(the_div.style.borderColor=="darkblue"){
		var index = selected.indexOf(text);
		selected.splice(index,1);
		the_div.style.border = "3px solid ghostwhite";
	}else{
		the_div.style.border = "3px solid darkblue";
		selected.push(text);
	}
}

function doubleClick(text) {
	document.getElementById("selectedPic").value=text;
    document.getElementById("selectedPics").value=text;
    goToFullScreen();
}
function showHiddenValue() { 
    document.getElementById('hiddenField').value="hello";
 }

