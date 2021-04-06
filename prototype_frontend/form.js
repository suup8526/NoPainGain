function onSubmit(){
    let name = document.getElementById('fullName').value;
    console.log(name);
    const data = JSON.stringify({"name": name});
    const request = new XMLHttpRequest();
    request.open('POST', 'https://boiling-spire-96508.herokuapp.com/https://prototype-server-backend.herokuapp.com/greeting_message');
    request.setRequestHeader("Content-Type", "application/json");
    console.log(data);
    request.send(data);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            const json = request.response;
            const obj = JSON.parse(json);
            document.getElementById('result').innerText = obj.resp_message;//"Hi " + name + ", Greetings from Team No Pain Gain";
        }
    };
    document.getElementById('fullName').value = "";
};