let count = 0;
function adddiv(){
    count++;
    let data = document.getElementById("dynamic");

    let mydiv = document.createElement("div"); 
    mydiv.classList.add('childcontent');
    
    let input = document.createElement("input");

    let icon = document.createElement('i');    
    icon.classList.add('fa', 'fa-times');
    icon.setAttribute('aria-hidden', 'true');

    icon.addEventListener('click', function() {
        mydiv.remove();
        count--;
    });

    input.placeholder = `Option ${count}`;
    input.id = 'text2';

    data.appendChild(mydiv);
    mydiv.appendChild(input);
    mydiv.appendChild(icon);
}
