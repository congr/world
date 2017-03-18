process.stdin.resume();
process.stdin.setEncoding('ascii');

var input_stdin = "";
var input_stdin_array = "";
var input_currentline = 0;

process.stdin.on('data', function (data) {
  input_stdin += data;
});

process.stdin.on('end', function () {
  input_stdin_array = input_stdin.split("\n");
  main();
});

function readLine() {
  return input_stdin_array[input_currentline++];
}

/////////////// ignore above this line ////////////////////

function main() {
  var n = parseInt(readLine());
  let arr = readLine().trim().split(' ');
  for(let i in arr)
    console.log(arr[i])
  arr = arr.map(Number);


  let re = [0, 0, 0]
  for (let i of arr) {
    if (i > 0)
      re[0]++;
    else if (i < 0)
      re[1]++;
    else // == 0
      re[2]++;
  }
  console.log((re[0] / n).toFixed(6));
  console.log((re[1] / n).toFixed(6));
  console.log((re[2] / n).toFixed(6));

}
