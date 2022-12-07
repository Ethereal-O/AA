import React from 'react';
import '../css/allorders.css';
import {withRouter} from "react-router-dom";
import { Allorderscontainer,Ocontents,OFooter } from '../components/allorderscomponents';
import { getallorderdata } from '../components/connect_to_backend';

class Allorders extends React.Component{
     //Book data
    //data=[["Abook","This is Abook.","A"],["Bbook","This is Bbook.","A"],["Cbook","This is Cbook.","B"]];
    //data=[["HP and PS","J.K.RowLing","100 dollars","../pics/hp_and_ps.ipg","Harry Potter and the Philosopher Stone Sorcerer's Stone.","HP"],["HP and GF","J.K.RowLing","100 dollars","../pics/hp_and_gf.ipg","Harry Potter and the Goblet of Fire.","HP"],["CSAPP","Randal E.Bryant / David O'Hallaron","200 dollars","../pics/csapp.ipg","Computer Systems: A Programmer's Perspective.","CS"]];
    data=[];

    constructor(){
        super();
        getallorderdata(this.getallorderdatacallback);
        this.state={
            alldata:this.data
        }
    }

    getallorderdatacallback=(data)=>
    {
        console.log(data);
        this.setState({
            alldata:data
        })
    }

    updatealldata(){
        getallorderdata(this.getallorderdatacallback);
    }

    render() {
        return(
            <div className="allordersmain">
                <div className="header">Here are all orders.</div>
                <Allorderscontainer parent={this} data={this.state.alldata}></Allorderscontainer>
                {/* <OFooter></OFooter> */}
                <Ocontents parent={this} options="orderadd"></Ocontents>
                <Ocontents parent={this} options="orderdelete"></Ocontents>
            </div>
        )
    }
}

export default withRouter(Allorders);