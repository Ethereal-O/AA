import React from 'react';
import '../css/allbooks.css';
import {withRouter} from "react-router-dom";
import { Allbookscontainer,Bcontents } from '../components/allbookscomponents';
import { getallbookdata } from '../components/connect_to_backend';

class Allbooks extends React.Component{

    //Book data
    data=[];
    constructor(){
        super();
        getallbookdata(this.getallbookdatacallback);
        this.state={
            alldata:this.data
        }
    }

    getallbookdatacallback=(data)=>
    {
        console.log(data);
        this.setState({
            alldata:data
        })
    }

    updatealldata(){
        getallbookdata(this.getallbookdatacallback);
    }

    render(){
        return(
            <div className="allbooksmain">
                <div className="header" id="header">Here are all books.</div>
                <Allbookscontainer parent={this} data={this.state.alldata}></Allbookscontainer>
                <Bcontents></Bcontents>
            </div>
        )
    }
}

export default withRouter(Allbooks);