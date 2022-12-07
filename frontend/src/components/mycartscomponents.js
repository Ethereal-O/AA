import React from 'react';
import '../css/mycarts.css';
import { Details } from './detailscomponents';
import { NameList } from './namelistcomponents';
import { userdeletecartdata, checklogin, usercleancart, usercleancartWebSocket } from './connect_to_backend';
// import { createkafkausercleancart } from './connect_to_kafka';
export class Mycartscontainer extends React.Component {

    data = [];
    constructor(props) {
        super(props);
        this.state = {
            nowdata: props.data,
            nowselecteddata: [-1, "", "Bookname", "BookAuther", "BookPrice", "https://i-1-lanrentuku.52tup.com/2020/12/29/f3eab929-d990-42d9-b858-0270ae7528b6.jpg?imageView2/2/w/1024/", "Click button left to select a book.", 0, 0],
        }
    }
    componentWillReceiveProps(nextProps) {
        this.setState({
            nowdata: nextProps.data,
            nowselecteddata: [-1, "", "Bookname", "BookAuther", "BookPrice", "https://i-1-lanrentuku.52tup.com/2020/12/29/f3eab929-d990-42d9-b858-0270ae7528b6.jpg?imageView2/2/w/1024/", "Click button left to select a book.", 0, 0]
        })
    }
    Showdetails(e) {
        var namelist = document.getElementsByClassName("nameandpriceelement");
        for (let i = 0; i < namelist.length; i++) {
            namelist[i].style.background = "rgba(255,255,255,0)";
        }
        document.getElementById(e.currentTarget.id).style.background = "rgba(255,255,255,0.3)";
        var bookid = e.currentTarget.id.substring(3);
        for (let i = 0; i < this.state.nowdata.length; i++) {
            if (this.state.nowdata[i][0] === bookid) {
                this.setState({
                    nowselecteddata: this.state.nowdata[i]
                })
            }
        }
    }
    deleteit = (e) => {
        var bookid = this.state.nowselecteddata[0];
        let userdeletecartdatacallback = (susflag) => {
            if (susflag > 0) {
                alert("Delete Success!");
                this.props.parent.updatealldata();
            } else {
                alert("Delete false!");
            }
        }
        userdeletecartdata(bookid, localStorage.getItem("username"), userdeletecartdatacallback)
    }

    submit = (totprice) => {
        var b = document.getElementById('buycontent');
        b.children[0].children[0].innerHTML = "Total price: " + totprice + " dollars";
        if (b.style.visibility === 'hidden') {
            b.style.visibility = 'visible';
        } else {
            b.style.visibility = 'hidden';
        }
    }
    updatealldata() {
        this.props.parent.updatealldata();
    }

    render() {
        let totprice = 0;
        for (let i = 0; i < this.state.nowdata.length; i++) {
            totprice = totprice + parseFloat(this.state.nowdata[i][4]);
        }
        return (
            <div className="mycartscontainer">
                <div className="card">
                    <NameList ele={this.state.nowdata} parent={this} options="carts"></NameList>
                    <Details nowselecteddata={this.state.nowselecteddata} parent={this} options={"mycharts"}></Details>
                </div>
                <div className='totbar'>
                    <div>Total price:{totprice.toFixed(2)} dollars</div>
                    <button className="buybutton" onClick={(e) => this.submit(totprice)}>Submit</button>
                </div>
            </div>
        )
    }
}

export class Bcontents extends React.Component {
    constructor() {
        super();
        this.state = {
            password: "",
        }
    }

    changepassword = (e) => {
        this.setState({
            password: e.target.value
        })
    }

    trybuy = () => {
        let checklogincallback = (susflag) => {
            let usercleancartcallback = (cleansusflag) => {
                if (cleansusflag > 0) {
                    alert("Success!");
                    document.getElementById('BScontents').style.visibility = "visible";
                    document.getElementById('BScontentsTime').innerHTML = "Time:" + new Date();
                    // this.props.parent.updatealldata();
                    ws.close();
                } else {
                    alert("Something wrong!");
                }
            }
            if (susflag > 0) {
                var ws = usercleancartWebSocket(localStorage.getItem("username"), usercleancartcallback);
                alert("Request success! Please wait!");
                document.getElementById('buycontent').style.visibility = 'hidden';
                // var ws = new WebSocket('ws://localhost:8080/websocket/'+localStorage.getItem("username")+"usercleancartservice");
                // ws.onmessage = usercleancartcallback;
                // ws.onopen = function () {
                //     console.log('ws onopen');
                //     ws.send("confirm");
                // };
                // usercleancart(localStorage.getItem("username"),usercleancartcallback);
            } else {
                alert("Wrong password!");
            }
        }
        if (this.state.password === "") {
            alert("blank password detected!");
            return;
        }
        checklogin(localStorage.getItem("username"), this.state.password, checklogincallback);
        document.getElementById("mycartcpassword").value = "";
        this.setState({
            password: ""
        })
    }

    render() {
        return (
            <div className="buycontent" id="buycontent">
                <form className="word" name="input" action="" method="get">
                    <p>Total price:</p>
                    <br />
                    <div className="inputbox">
                        <div className="display">Password:</div>
                        <input onChange={(e) => this.changepassword(e)} className="typein" type="password" name="cpassword" id="mycartcpassword" />
                    </div>
                    <br />
                    <input onClick={() => this.trybuy()} className="buybutton" type="button" value="Submit" />
                </form>
            </div>
        )
    }
}

export class BScontents extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data: props.data
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({
            data: nextProps.data
        })
    }
    confirm() {
        document.getElementById("BScontents").style.visibility = "hidden";
        this.props.parent.updatealldata();
    }

    render() {
        return (
            <div id="BScontents" className='BScontents'>
                <div className='BScontentstitle' id="BScontentsTime">Time:</div>
                <div className='BScontentstitle'>You have buy:</div>
                <div className="BScontentsname" id="namelist">
                    <div className="BScontentsorderbooks" id="orderbookstitle">
                        <h2>Bookname</h2><h2>Price</h2>
                    </div>
                    {
                        this.state.data.map(item => (
                            <div className="BScontentsorderbooks">
                                <p>{item[2]}</p>
                                <p>{item[4]} dollars</p>
                            </div>
                        ))
                    }
                </div>
                <button onClick={() => this.confirm()}>OK</button>
            </div>
        )
    }

}

