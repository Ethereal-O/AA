import React, {useEffect, useRef} from 'react';
import VanillaTilt from 'vanilla-tilt';
import { trylogin,checklogin,trylogout,register } from './connect_to_backend';

function Tilt(props) {
    const { options, ...rest } = props;
    const tilt = useRef(null);

    useEffect(() => {
        VanillaTilt.init(tilt.current, options);
    }, [options]);

    return <div ref={tilt} {...rest} />;
}

export class Homecontainer extends React.Component{
    constructor(props){
        super(props);
        this.state={
            isloged:this.props.isloged,
            userstate:this.props.userstate
        }
    }
    componentWillReceiveProps(nextProps){
        this.setState({
            isloged:nextProps.isloged,
            userstate:nextProps.userstate
        })
    }
    notloged=()=>{
        alert("Please login first!");
    }
    render(){
        if(this.state.userstate===2||this.state.userstate==='2')
        {
            return(
                <div className="homecontainer">
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>01</h2>
                            <h3>Manage Users</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to manage all users.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            <a href="/Allusers">Manage all users</a>
                        </div>
                    </Tilt>
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>02</h2>
                            <h3>Manage Books</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to show all books.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            <a href="/Allbooks">Manage all books</a>
                        </div>
                    </Tilt>
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>03</h2>
                            <h3>Manage orders</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to manage all orders.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            <a href="/Allorders">Manage all orders</a>
                        </div>
                    </Tilt>
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>04</h2>
                            <h3>Statistics</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to show statistics.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            <a href="/Statistics">Show statistics</a>
                        </div>
                    </Tilt>
                </div>
            )
        }
        else
        {
            return(
                <div className="homecontainer">
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>01</h2>
                            <h3>My Shelf</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to open my shelf.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            { this.state.isloged?
                                <a href="/Mybooks">Open my shelf</a>:<button onClick={()=>this.notloged()}>Open my shelf</button>
                            }
                        </div>
                    </Tilt>
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>02</h2>
                            <h3>View Books</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to show all books.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            <a href="/Allbooks">Show all books</a>
                        </div>
                    </Tilt>
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>03</h2>
                            <h3>My Carts</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to view my carts.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            { this.state.isloged?
                                <a href="/Mycarts">View my carts</a>:<button onClick={()=>this.notloged()}>View my carts</button>
                            }
                        </div>
                    </Tilt>
                    <Tilt className="card" options={this.options}>
                        <div className="skin">
                            <h2>04</h2>
                            <h3>Statistics</h3>
                            <p>Put here to show details.</p>
                        </div>
                        <div className="content">
                            <h2>Details</h2>
                            <p>Tap the button below to show statistics.</p>
                            {/* 根据用户名跳转到相应页面 */}
                            { this.state.isloged?
                                <a href="/UserStatistics">Show statistics</a>:<button onClick={()=>this.notloged()}>Show statistics</button>
                            }
                        </div>
                    </Tilt>
                </div>
            )
        }
        
    }
}

export class Footer extends React.Component{
    constructor(props){
        super(props);
        this.state={
            isloged:this.props.isloged
        }
    }
    componentWillReceiveProps(nextProps){
        this.setState({
            isloged:nextProps.isloged
        })
    }
    Lclick(){
        var l=document.getElementById('Lcontent');
        if (l.style.visibility==='hidden'){
            l.style.visibility='visible';
        }else{
            l.style.visibility='hidden';
        }
    }
    Rclick(){
        var r=document.getElementById('Rcontent');
        if (r.style.visibility==='hidden'){
            r.style.visibility='visible';
        }else{
            r.style.visibility='hidden';
        }
    }
    Oclick(){
        let logoutcallback=(susflag)=>{
            localStorage.setItem("isloged", false);
            this.props.parent.haslogout();
            if (susflag){
                alert("Logout success! "+"It has been "+susflag+"ms since you logged in!");
            }else{
                alert("Logout false!");
            }
        }
        trylogout(logoutcallback);
    }
    render(){
        if(this.state.isloged){
            let logedusername=localStorage.getItem("username");
            return(
                <div className='footer'>
                    <div >Welcome, {logedusername}.</div>
                    <div className="log">
                        <button className="LorRbutton" onClick={() => this.Oclick()}>Log out</button>
                    </div>
                </div>
            )
        }else{
            return(
                <div className="footer">
                    <div className="log">
                        <button className="LorRbutton" onClick={() => this.Lclick()}>Log in</button>
                    </div>
                    <div className="log">
                        <button className="LorRbutton" onClick={() => this.Rclick()}>Register</button>
                    </div>
                </div>
            )
        }
    }
}

export class LorRcontents extends React.Component{
    constructor (props){
        super(props);
        this.state={
            LorR:this.props.options,
            username:"",
            password:"",
            repeatpassword:"",
            email:""
        };
    }
    changedata=(e)=>
    {
        let buttonname=e.target.name.substring(1);
        this.setState({[buttonname]:e.target.value});
    }
    
    getinfo=()=>
    {
        let trylogincallback = (susflag) => {
            if (susflag > 0) {
                var lorc = document.getElementsByClassName("LorRcontent");
                for (let i = 0; i < lorc.length; i++) {
                    lorc[i].style.visibility = 'hidden';
                }
                alert("Login success!");
                localStorage.setItem("isloged", true);
                localStorage.setItem("username", this.state.username);
                localStorage.setItem("userstate", susflag);
                this.props.parent.haslogin(this.state.username, susflag);
            }
            if (susflag === 0) {
                alert("Wrong password!");
            }
            if (susflag === -1) {
                alert("No this user!")
            }
            if (susflag === -2) {
                alert("Your account has been disabled!")
            }
            this.setState({
                username:"",
                password:""
            })
        }

        let checklogincallback = (susflag) => {
            if (susflag > 0) {
                var lorc = document.getElementsByClassName("LorRcontent");
                for (let i = 0; i < lorc.length; i++) {
                    lorc[i].style.visibility = 'hidden';
                }
                alert("Login success!");
                localStorage.setItem("isloged", true);
                localStorage.setItem("username", this.state.username);
                localStorage.setItem("userstate", susflag);
                this.props.parent.haslogin(this.state.username, susflag);
            }
            if (susflag === 0) {
                alert("Wrong password!");
            }
            if (susflag === -1) {
                alert("No this user!")
            }
            if (susflag === -2) {
                alert("Your account has been disabled!")
            }
            this.setState({
                username:"",
                password:""
            })
        }

        let registercallback=(susflag)=>
        {
            if (susflag>0) {
                var lorc = document.getElementsByClassName("LorRcontent");
                for (let i = 0; i < lorc.length; i++) {
                    lorc[i].style.visibility = 'hidden';
                }
                alert("Register success!");
            }
            if (susflag===0)
            {
                alert("Duplicated username!");
            }
            if (susflag===-1)
            {
                alert("Register false!")
            }
            this.setState({
                username:"",
                password:"",
                repeatpassword:"",
                email:""
            })
        }
        if (this.state.LorR==="L")
        {
            if (this.state.username === "" || this.state.password === "") {
                alert("Blank username or password detected!");
                document.getElementById(this.state.LorR + "username").value="";
                document.getElementById(this.state.LorR + "password").value="";
                document.getElementById("Rrepeatpassword").value="";
                document.getElementById("Remail").value="";
                this.setState({
                    username: "",
                    password: "",
                    repeatpassword: "",
                    email: ""
                })
                return;
            }
        }else{
            if (this.state.username === "" || this.state.password === ""||this.state.repeatpassword===""||this.state.email==="") {
                alert("Blank username password or email detected!");
                document.getElementById(this.state.LorR + "username").value="";
                document.getElementById(this.state.LorR + "password").value="";
                document.getElementById("Rrepeatpassword").value="";
                document.getElementById("Remail").value="";
                this.setState({
                    username: "",
                    password: "",
                    repeatpassword: "",
                    email: ""
                })
                return;
            }

            if (this.state.password!==this.state.repeatpassword)
            {
                alert("The two passwords you entered were inconsistent!");
                document.getElementById(this.state.LorR + "username").value="";
                document.getElementById(this.state.LorR + "password").value="";
                document.getElementById("Rrepeatpassword").value="";
                document.getElementById("Remail").value="";
                this.setState({
                    username: "",
                    password: "",
                    repeatpassword: "",
                    email: ""
                })
                return;
            }

            if (this.state.email.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) === -1)
            {
                alert("Wrong email format!");
                document.getElementById(this.state.LorR + "username").value="";
                document.getElementById(this.state.LorR + "password").value="";
                document.getElementById("Rrepeatpassword").value="";
                document.getElementById("Remail").value="";
                this.setState({
                    username: "",
                    password: "",
                    repeatpassword: "",
                    email: ""
                })
                return;
            }
        }
        if (this.state.LorR==='L')
        {
            trylogin(this.state.username,this.state.password,trylogincallback);
            // checklogin(this.state.username,this.state.password,checklogincallback);
        }else
        {
            register(this.state.username,this.state.password,this.state.email,registercallback);
        }
        document.getElementById(this.state.LorR + "username").value="";
        document.getElementById(this.state.LorR + "password").value="";
        document.getElementById("Rrepeatpassword").value="";
        document.getElementById("Remail").value="";
    }
    render(){
        if (this.state.LorR==="L")
        {
            return(
                <div className="LorRcontent" id={this.state.LorR + "content"}>
                    <form className="word" ref={this.ref} id={this.state.LorR+"form"} action="" method="get">
                        <h3>{this.props.options==='L'?'Login':'Register'}</h3>
                        <div className="inputbox">
                            <div className="display">Username:</div>
                            <input onChange={(e)=>this.changedata(e)} className="typein" type="text" name={this.state.LorR + "username"} id={this.state.LorR + "username"}/>
                        </div>
                        <br />
                        <div className="inputbox">
                            <div className="display">Password:</div>
                            <input onChange={(e)=>this.changedata(e)} className="typein" type="password" name={this.state.LorR + "password"} id={this.state.LorR + "password"}/>
                        </div>
                        <br />
                        <input className="LorRbutton" type="button" value="Submit" onClick={()=>this.getinfo()} />
                    </form>
                </div>
            )
        }
        else
        {
            return(
                <div className="LorRcontent" id={this.state.LorR + "content"}>
                    <form className="word" ref={this.ref} id={this.state.LorR+"form"} action="" method="get">
                        <h3>{this.props.options==='L'?'Login':'Register'}</h3>
                        <div className="inputbox">
                            <div className="display">Username:</div>
                            <input onChange={(e)=>this.changedata(e)} className="typein" type="text" name={this.state.LorR + "username"} id={this.state.LorR + "username"}/>
                        </div>
                        <br />
                        <div className="inputbox">
                            <div className="display">Password:</div>
                            <input onChange={(e)=>this.changedata(e)} className="typein" type="password" name={this.state.LorR + "password"} id={this.state.LorR + "password"}/>
                        </div>
                        <br />
                        <div className="inputbox">
                            <div className="display">Repeat:</div>
                            <input onChange={(e)=>this.changedata(e)} className="typein" type="password" name={this.state.LorR + "repeatpassword"} id={this.state.LorR + "repeatpassword"}/>
                        </div>
                        <br />
                        <div className="inputbox">
                            <div className="display">Email:</div>
                            <input onChange={(e)=>this.changedata(e)} className="typein" type="text" name={this.state.LorR + "email"} id={this.state.LorR + "email"}/>
                        </div>
                        <br />
                        <input className="LorRbutton" type="button" value="Submit" onClick={()=>this.getinfo()} />
                    </form>
                </div>
            )
        }
    }
}
