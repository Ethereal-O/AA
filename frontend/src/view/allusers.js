import React from 'react';
import '../css/allusers.css';
import {withRouter} from "react-router-dom";
import { Alluserscontainer} from '../components/alluserscomponents';
import { getalluserdata } from '../components/connect_to_backend';

class Allusers extends React.Component{

    //User data
    data=[];
    constructor(){
        super();
        getalluserdata(this.getalluserdatacallback);
        this.state={
            alldata:this.data
        }
    }

    getalluserdatacallback=(data)=>
    {
        console.log(data);
        for (let i=0;i<data.length;i++)
        {
            switch(data[i][3]){
                case '-2':
                    data[i][3]='禁用';
                    break;
                case '2':
                    data[i][3]='管理员';
                    break;
                case '1':
                    data[i][3]='顾客';
                    break;
                default:
                    // alert("Please input\"管理员\" or\"普通用户\" or\"禁用\"!")
                    return;
            }
        }
        this.setState({
            alldata:data
        })
    }

    updatealldata(){
        getalluserdata(this.getalluserdatacallback);
    }

    render(){
        return(
            <div className="allusersmain">
                <div className="header">Here are all users.</div>
                <Alluserscontainer parent={this} data={this.state.alldata}></Alluserscontainer>
            </div>
        )
    }
}

export default withRouter(Allusers);