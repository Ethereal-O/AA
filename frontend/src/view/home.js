import React from 'react';
import '../css/home.css';
import {withRouter} from "react-router-dom";
import {Homecontainer,LorRcontents,Footer} from '../components/homecomponents';

class Home extends React.Component{

    //data here
    options={
        max:15,
        speed:400,
        glare:true,
        "max-glare":1
    };

    constructor(){
        super();
        if (localStorage.getItem("isloged")==="true")
        {
            this.state={
                isloged:true,
                username:localStorage.getItem("username"),
                userstate:localStorage.getItem("userstate")
            }
        } else {
            localStorage.setItem("isloged", false);
            this.state = {
                isloged: false,
                username: null,
                userstate: 0
            }
        }
    }
    haslogin=(username,userstate)=>{
        this.setState({
            isloged:true,
            username:username,
            userstate:userstate
        })
    }
    haslogout=()=>{
        this.setState({
            isloged:false,
            username:null,
            userstate:0
        })
    }
    render(){
        return(
            <div className="homemain">
                <div className="header">Welcome to Dream Bookstore.</div>
                <Homecontainer isloged={this.state.isloged} userstate={this.state.userstate}></Homecontainer>
                <Footer isloged={this.state.isloged} parent={this}></Footer>
                <LorRcontents options={"L"} parent={this}></LorRcontents>
                <LorRcontents options={"R"} parent={this}></LorRcontents>
            </div>)
    }
}

export default withRouter(Home);