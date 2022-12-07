import React from 'react';
import '../css/mycarts.css';
import {withRouter} from "react-router-dom";
import { Mycartscontainer,Bcontents, BScontents } from '../components/mycartscomponents';
import { getusercartdata } from '../components/connect_to_backend';

class Mycarts extends React.Component{
     //Book data
    //data=[["Abook","This is Abook.","A"],["Bbook","This is Bbook.","A"],["Cbook","This is Cbook.","B"]];
    //data=[["HP and PS","J.K.RowLing","100 dollars","../pics/hp_and_ps.ipg","Harry Potter and the Philosopher Stone Sorcerer's Stone.","HP"],["HP and GF","J.K.RowLing","100 dollars","../pics/hp_and_gf.ipg","Harry Potter and the Goblet of Fire.","HP"],["CSAPP","Randal E.Bryant / David O'Hallaron","200 dollars","../pics/csapp.ipg","Computer Systems: A Programmer's Perspective.","CS"]];
    data=[];

    constructor(){
        super();
        getusercartdata(localStorage.getItem("username"),this.getusercartdatacallback);
        this.state={
            alldata:this.data
        }
    }

    getusercartdatacallback=(data)=>
    {
        this.setState({
            alldata:data
        })
    }

    updatealldata(){
        getusercartdata(localStorage.getItem("username"),this.getusercartdatacallback);
    }

    render() {
        return(
            <div className="mycartsmain">
                <div className="header">Here are my carts.</div>
                <Mycartscontainer parent={this} data={this.state.alldata}></Mycartscontainer>
                <Bcontents parent={this}></Bcontents>
                <BScontents parent={this} data={this.state.alldata}></BScontents>
            </div>
        )
    }
}

export default withRouter(Mycarts);