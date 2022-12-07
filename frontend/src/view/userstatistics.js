import React from 'react';
import '../css/userstatistics.css';
import {withRouter} from "react-router-dom";
import { UserStatisticscontainer} from '../components/userstatisticscomponents';
import { getusershelfdata } from '../components/connect_to_backend';

class UserStatistics extends React.Component{
     //Book data
    //data=[["Abook","This is Abook.","A"],["Bbook","This is Bbook.","A"],["Cbook","This is Cbook.","B"]];
    //data=[["HP and PS","J.K.RowLing","100 dollars","../pics/hp_and_ps.ipg","Harry Potter and the Philosopher Stone Sorcerer's Stone.","HP"],["HP and GF","J.K.RowLing","100 dollars","../pics/hp_and_gf.ipg","Harry Potter and the Goblet of Fire.","HP"],["CSAPP","Randal E.Bryant / David O'Hallaron","200 dollars","../pics/csapp.ipg","Computer Systems: A Programmer's Perspective.","CS"]];
    data=[];

    constructor(){
        super();
        getusershelfdata(localStorage.getItem("username"),this.getusershelfdatacallback);
        this.state={
            alldata:this.data
        }
    }

    getusershelfdatacallback=(data)=>
    {
        this.setState({
            alldata:data
        })
    }

    updatealldata(){
        getusershelfdata(localStorage.getItem("username"),this.getusershelfdatacallback);
    }

    render() {
        return(
            <div className="userstatisticsmain">
                <div className="header">Here are Statistics.</div>
                <UserStatisticscontainer parent={this} data={this.state.alldata}></UserStatisticscontainer>
            </div>
        )
    }
}

export default withRouter(UserStatistics);