const tableBody = document.querySelector('#myTable tbody');
fetch('https://jsonplaceholder.typicode.com/posts')
  .then(response => response.json())
  .then(data => {

        const post = data[1]; // get the first post object
        console.log(post.id);

        const h1 = document.createElement('h1');
        h1.appendChild(document.createTextNode(`Hello ${post.id}`));
        h1.appendChild(document.createTextNode(` ${post.title}`));
        tableBody.appendChild(h1);

  })
  .catch(error => {
    console.error(error);
  });


//   const tableBody = document.querySelector('#myTable tbody');
//   fetch('https://jsonplaceholder.typicode.com/posts')
//     .then(response => response.json())
//     .then(data => {
  
//           const post = data[0]; // get the first post object
//           console.log(post.id);
  
//           const h1 = document.createElement('h1');
//           h1.appendChild(document.createTextNode(`Hello ${post.id}`));
//           tableBody.appendChild(h1);
  
//     })
//     .catch(error => {
//       console.error(error);
//     });
  