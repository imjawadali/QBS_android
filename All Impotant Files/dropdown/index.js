let count = 0;
function addDiv(){
  count++;
  let header = document.createElement("h1");
  let value = document.createTextNode("Hello World");
  header.appendChild(value);
  console.log(header);
  let divcontent = document.getElementById("new");
  divcontent.appendChild(document.createTextNode("Hello World"));
  console.log(count)
}
function removeDiv(){
if(!count<=0){
  count--;
  console.log(count);
}
else{
  console.log("Your value is less than");
}
}