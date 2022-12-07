import React from 'react';
import '../css/allusers.css';
import { Searchbar } from './searchbarcomponents';
import { NameList } from './namelistcomponents';
import { adminchangeuserdata,admindeleteuserdata } from "./connect_to_backend";

export class Alluserscontainer extends React.Component{

    data=[];
    constructor(props){
        super(props);
        // this.data=props.data;
        this.state={
            alldata:props.data,
            nowdata:props.data,
            sortedby:"Null",
            isdecending:false
        }
    }
    componentWillReceiveProps(nextProps){
        this.setState({
            alldata:nextProps.data,
            nowdata:nextProps.data,
            sortedby:"Null",
            isdecending:false
        })
    }
    datasort(e){
        let clickname=e.currentTarget.innerHTML;
        let index=-1;
        if(clickname[0]==="I") index=0;
        if(clickname[0]==="U") index=1;
        if(clickname[0]==="P") index=2;
        if(clickname[0]==="T") index=3;
        let opt=0;
        if(this.state.sortedby!==clickname[0]) opt=1;
        if(this.state.sortedby===clickname[0]&&this.state.isdecending===false) opt=2;
        if(this.state.sortedby===clickname[0]&&this.state.isdecending===true) opt=3;

        if (opt===1)
        {
            e.currentTarget.innerHTML += "\u2193";
            var newdata = this.state.nowdata.slice();
            newdata.sort(function (a, b) {
                return b[index] > a[index] ? -1 : 1;
            });
            this.setState({
                nowdata: newdata,
                sortedby: clickname[0]
            })
        }
        if (opt===2)
        {
            e.currentTarget.innerHTML=clickname.substring(0,clickname.length-1);
            e.currentTarget.innerHTML += "\u2191";
            var newdata = this.state.nowdata.slice();
            newdata.sort(function (a, b) {
                return a[index] > b[index] ? -1 : 1;
            });
            this.setState({
                isdecending:true,
                nowdata: newdata,
            })
        }
        if (opt===3)
        {
            e.currentTarget.innerHTML=clickname.substring(0,clickname.length-1);
            var newdata = this.state.nowdata.slice();
            newdata.sort(function (a, b) {
                return a[index] > b[index] ? -1 : 1;
            });
            this.setState({
                isdecending:false,
                sortedby:"Null",
            })
        }
    }
    searchdata=()=>{
        var searchwhat=document.getElementById("searchdata").value;
        var newdata=[];
        for (let i=0;i<this.state.alldata.length;i++){
            if(this.state.alldata[i][1].search(searchwhat)!==-1){
                newdata.push(this.state.alldata[i]);
            }
        }
        this.setState({
            nowdata:newdata
        })
    }
    updatealldata(){
        this.props.parent.updatealldata();
    }

    deleteuserdata=(id)=>{
        let admindeleteuserdatacallback=(susflag)=>
        {
            if(susflag)
            {
                alert("Delete user Success!");
                this.updatealldata();
            }else{
                alert("Detele user false!");
            }
        }
        admindeleteuserdata(id,admindeleteuserdatacallback);
    }


    changeuserdata(e,id,index){
        let adminchangeuserdatacallback=(susflag)=>
        {
            if(susflag)
            {
                alert("Change data Success!");
                this.updatealldata();
            }else{
                alert("Change data false!");
            }
        }
        if (e.keyCode===13){
            let content = e.currentTarget.value;
            if (index===3){
                switch(content){
                    case '禁用':
                        content=-2;
                        break;
                    case '管理员':
                        content=2;
                        break;
                    case '顾客':
                        content=1;
                        break;
                    default:
                        alert("Please input\"管理员\" or\"顾客\" or\"禁用\"!")
                        return;
                }
            }
            
            adminchangeuserdata(id,index, content,adminchangeuserdatacallback);
        }
    }

    render(){
        return(
            <div className="alluserscontainer">
                <div className="card">
                    <NameList ele={this.state.nowdata} parent={this} options="users"></NameList>
                </div>
                <Searchbar parent={this}></Searchbar>
            </div>
        )
    }
}