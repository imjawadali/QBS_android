document.onkeydown = function(e) {
    if(event.keyCode == 123) {
       return false;
    }
    if(e.ctrlKey && e.shiftKey && e.keyCode == 'I'.charCodeAt(0)) {
       return false;
    }
    if(e.ctrlKey && e.shiftKey && e.keyCode == 'C'.charCodeAt(0)) {
       return false;
    }
    if(e.ctrlKey && e.shiftKey && e.keyCode == 'J'.charCodeAt(0)) {
       return false;
    }
    if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)) {
       return false;
    }
  }




  fetch('/Retrieve-Assigned-Tasks')
  .then(response => response.json())
  .then(res => {
	  var assignedCount = res[0].count;
	  fetch('/Retrieve-Completed-Tasks')
	  .then(response => response.json())
	  .then(res => {
		  var completedCount = res[0].count;
		  var data = {
			  labels: [
				  "Assigned",
				  "Completed"
			  ],
			  datasets: [{
				//   data: [assignedCount-completedCount, completedCount],
				  data: [assignedCount, completedCount],
				  backgroundColor: [
					  "#26EAC0",
					  "#96FFE7"
					  
				  ],
				  hoverBackgroundColor: [
					  "#26EAC0",
					  "#96FFE7"
				  ],
				  borderColor: [
				  '#26EAC0',
				  '#96FFE7'
				  ],
				  borderWidth: 1
			  }]
		  };
		  var promisedDeliveryChart = new Chart(document.getElementById('myChart2'), {
			  type: 'doughnut',
			  data: data,
			  options: {
				  responsive: false,
				  legend: {
						  position: 'top',
						  labels: {
							fontSize: 10,
						}
					  },
					  cutoutPercentage: 70,
					  title: {
						  display: false,
						  text: 'Tasks Assigned'
					  },
				  animation: {
					  animateScale: true,
					  animateRotate: true
				  }
			  },
			  plugins: [{
				  id: 'text',
				  beforeDraw: function(chart, a, b) {
					  var width = chart.width,
						  height = chart.height,
						  ctx = chart.ctx;
							  
					  ctx.restore();
					  var fontSize = (height / 350).toFixed(2);
					  ctx.font = "bold "+fontSize + "em sans-serif";
					  ctx.textBaseline = "center";


			
					var text1 = "You have",
					text2 = "completed",
					text3 = completedCount,
					text4= "tasks";

					textX = Math.round((width ) / 2),
					textY1 = height/2.2,
					textY2 = height/1.85;
					textY3 = height/1.45;
					textY4 = height/1.25;


					ctx.textAlign = "center";

					ctx.font = "10px Arial";
					ctx.fillText(text1, textX, textY1);

					ctx.font = "10px Arial";
					ctx.fillText(text2, textX, textY2);

					ctx.font = "bold 20px Arial";
					ctx.fillText(text3, textX, textY3);

					ctx.font = "10px Arial";
					ctx.fillText(text4, textX, textY4);

						ctx.save();

					}
				}]
			});
		})
		.catch(error => console.error(error));
	})
	.catch(error => console.error(error));












	
//   var text1 = "You have",
					//   text2 = assignedCount,
					//   text3 = "assigned and",
					//   text4 = completedCount,
					//   text5 = "completed tasks",
					//   textX = Math.round((width ) / 2),
					//   textY1 = height/2.2,
					//   textY2 = height/1.8;
					//   textY3 = height/1.55;
					//   textY4 = height/1.35;
					//   textY5 = height/1.2;
					//   ctx.textAlign = "center";
					//   ctx.font = "10px Arial";
					//   ctx.fillText(text1, textX, textY1);
					//   ctx.font = "bold 20px Arial";
					//   ctx.fillText(text2, textX, textY2);
					//   ctx.font = "10px Arial";
					//   ctx.fillText(text3, textX, textY3);
					//   ctx.font = "bold 20px Arial";
					//   ctx.fillText(text4, textX, textY4);
					//   ctx.font = "10px Arial";
					//   ctx.fillText(text5, textX, textY5);
					//   ctx.save

















// var data = {
// 			labels: [
// 				"To Do",
// 				"Completed"
// 			],
// 			datasets: [{
// 				data: [3,2],
// 				backgroundColor: [
// 					"#26EAC0",
// 					"#96FFE7"
					
// 				],
// 				hoverBackgroundColor: [
// 					"#26EAC0",
// 					"#96FFE7"
// 				],
//                 borderColor: [
// 				'#26EAC0',
// 				'#96FFE7'
// 				],
// 				borderWidth: 1
// 			}]
// 		};

// 		var promisedDeliveryChart = new Chart(document.getElementById('myChart2'), {
// 			type: 'doughnut',
// 			data: data,
// 			options: {
// 				responsive: false,
//                 legend: {
// 						position: 'top'
// 					},
// 					cutoutPercentage: 70,
// 					// title: {
// 					// 	display: true,
// 					// 	text: 'Tasks Assigned'
// 					// },
// 				animation: {
// 					animateScale: true,
// 					animateRotate: true
// 				}
// 			},
// 			plugins: [{
// 				id: 'text',
// 				beforeDraw: function(chart, a, b) {
// 					var width = chart.width,
// 						height = chart.height,
// 						ctx = chart.ctx;
							
// 					ctx.restore();
//                     // console.log(height);
// 					var fontSize = (height / 350).toFixed(2);
//                     console.log(fontSize);
// 					ctx.font = "bold "+fontSize + "em sans-serif";
// 					ctx.textBaseline = "center";

// 					var text1 = "You have",
// 					text2 = "completed",
// 					text3 = "02",
// 					text4= "tasks";

// 					textX = Math.round((width ) / 2),
// 					textY1 = height/2.2,
// 					textY2 = height/1.85;
// 					textY3 = height/1.45;
// 					textY4 = height/1.25;


// 					ctx.textAlign = "center";

// 					ctx.font = "10px Arial";
// 					ctx.fillText(text1, textX, textY1);

// 					ctx.font = "10px Arial";
// 					ctx.fillText(text2, textX, textY2);

// 					ctx.font = "bold 20px Arial";
// 					ctx.fillText(text3, textX, textY3);

// 					ctx.font = "10px Arial";
// 					ctx.fillText(text4, textX, textY4);

// 						ctx.save();
// 				}
// 			}]
// 		});
