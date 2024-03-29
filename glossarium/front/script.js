var json_data;


async function fetchDataAndExecuteD3() {
    var url = 'http://localhost:8080/words/tree';
    try {
        var response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        var data = await response.json();
        json_data = data;
        console.log('JSON data:', json_data);
       
        var m = [20, 120, 20, 20],
        w = 1280 - m[1] - m[3],
        h = 800 - m[0] - m[2],
        i = 0,
        root;
    
    var tree = d3.layout.tree()
        .size([h, w]);
    
    var diagonal = d3.svg.diagonal()
        .projection(function(d) { return [d.y, d.x]; });
    
    var vis = d3.select("#body").append("svg:svg")
        .attr("width", w + m[1] + m[3])
        .attr("height", h + m[0] + m[2])
      .append("svg:g")
        .attr("transform", "translate(" + m[3] + "," + m[0] + ")");
    
    root = json_data;
    root.x0 = h / 2;
    root.y0 = 0;
    
    function toggleAll(d) {
      if (d.children) {
        d.children.forEach(toggleAll);
        toggle(d);
      }
    }
    
     update(root);
    
    
    function update(source) {
      var duration = d3.event && d3.event.altKey ? 5000 : 500;
    
      // Compute the new tree layout.
      var nodes = tree.nodes(root).reverse();
    
      // Normalize for fixed-depth.
      nodes.forEach(function(d) { d.y = d.depth * 180; });
    
      // Update the nodes…
      var node = vis.selectAll("g.node")
          .data(nodes, function(d) { return d.id || (d.id = ++i); });
    
      // Enter any new nodes at the parent's previous position.
      var nodeEnter = node.enter().append("svg:g")
          .attr("class", "node")
          .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
          .on("click", function(d) { toggle(d); update(d); });
    
      nodeEnter.append("svg:circle")
          .attr("r", 1e-6)
          .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });
    
      nodeEnter.append('a')
          .attr('xlink:href', function(d) {
            return d.url;
          })
          .append("svg:text")
          .attr("x", function(d) { return d.children || d._children ? -10 : 10; })
          .attr("dy", ".35em")
          .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
          .text(function(d) { return d.name; })
          .style('fill', function(d) {
            return d.free ? 'black' : '#999';
          })
          .style("fill-opacity", 1e-6);
    
      nodeEnter.append("svg:title")
        .text(function(d) {
          return d.description;
        });
    
      // Transition nodes to their new position.
      var nodeUpdate = node.transition()
          .duration(duration)
          .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });
    
      nodeUpdate.select("circle")
          .attr("r", 6)
          .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });
    
      nodeUpdate.select("text")
          .style("fill-opacity", 1);
    
      // Transition exiting nodes to the parent's new position.
      var nodeExit = node.exit().transition()
          .duration(duration)
          .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
          .remove();
    
      nodeExit.select("circle")
          .attr("r", 1e-6);
    
      nodeExit.select("text")
          .style("fill-opacity", 1e-6);
    
      // Update the links…
      var link = vis.selectAll("path.link")
          .data(tree.links(nodes), function(d) { return d.target.id; });
    
      // Enter any new links at the parent's previous position.
      link.enter().insert("svg:path", "g")
          .attr("class", "link")
          .attr("d", function(d) {
            var o = {x: source.x0, y: source.y0};
            return diagonal({source: o, target: o});
          })
        .transition()
          .duration(duration)
          .attr("d", diagonal);
    
      // Transition links to their new position.
      link.transition()
          .duration(duration)
          .attr("d", diagonal);
    
      // Transition exiting nodes to the parent's new position.
      link.exit().transition()
          .duration(duration)
          .attr("d", function(d) {
            var o = {x: source.x, y: source.y};
            return diagonal({source: o, target: o});
          })
          .remove();
    
      // Stash the old positions for transition.
      nodes.forEach(function(d) {
        d.x0 = d.x;
        d.y0 = d.y;
      });
    }
    
    // Toggle children.
    function toggle(d) {
      if (d.children) {
        d._children = d.children;
        d.children = null;
      } else {
        d.children = d._children;
        d._children = null;
      }
    }

    } catch (error) {

        console.error('Error during fetch operation:', error);
    }
}
window.onload = fetchDataAndExecuteD3;

   //search
   function searchWords() {
    var userInput = document.getElementById('searchInput').value;
    var url = 'http://localhost:8080/words/search?text=' + encodeURIComponent(userInput);

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); 
        })
        .then(dataArray => {
            var resultContainer = document.getElementById('resultContainer');
            resultContainer.innerHTML = '<h2>Слово встречается в следующих терминах:</h2>';
            
            if (Array.isArray(dataArray) && dataArray.length > 0) {
                dataArray.forEach(data => {
                    if (data && data.word && data.description) {
                        resultContainer.innerHTML += '<p><b>Термин</b>: ' + data.word + '</p>';
                        resultContainer.innerHTML += '<p><b>Определение</b>: ' + data.description + '</p>';
                    }
                });
            } else {
                resultContainer.innerHTML += '<p>No valid data received from the server.</p>';
            }
        })
        .catch(error => {
            console.error('Error during fetch operation:', error);
        });
}