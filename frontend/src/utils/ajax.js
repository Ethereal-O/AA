export function postrequest(url,data,callback){
    document.cookie="name=ethereal;secure";
    let opts={
        method:"POST",
        body:data,
        credentials:"include"
    }
    fetch(url,opts).then((response)=>{return response.json()})
        .then((data)=>{callback(data)})
        .catch((error)=>{
            // console.log(error);
            alert("Permission denied!")
            window.location.href="/";
        })
        ;
}

export function websocketrequest(url,callback){
    var ws = new WebSocket(url);
    ws.onmessage = function (e) {
        callback(e.data);
    };
    ws.onopen = function () {
        console.log('ws onopen');
        ws.send("confirm");
    };
    return ws;
}