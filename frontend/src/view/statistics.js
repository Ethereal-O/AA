import React from 'react';
import '../css/statistics.css';
import {withRouter} from "react-router-dom";
import { Statisticscontainer} from '../components/statisticscomponents';
import { getallorderdata } from '../components/connect_to_backend';

class Statistics extends React.Component{
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
            <div className="statisticsmain">
                <div className="header">Here are Statistics.</div>
                <Statisticscontainer parent={this} data={this.state.alldata}></Statisticscontainer>
            </div>
        )
    }
}

export default withRouter(Statistics);