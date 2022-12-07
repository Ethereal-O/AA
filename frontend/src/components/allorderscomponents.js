import React from 'react';
import '../css/allorders.css';
import { NameList } from './namelistcomponents';
import { adminaddorder,admindeleteorder } from './connect_to_backend';
import { Searchbar, Timeseacherbar } from './searchbarcomponents';

export class Allorderscontainer extends React.Component{

    data=[];
    constructor(props){
        super(props);
        let newuserdata=this.preset(this.props.data);
        this.state={
            alldata:props.data,
            nowdata:props.data,
            usersdata:newuserdata,
            ordersdata:[],
            booksdata:[]
        }
    }
    componentWillReceiveProps(nextProps){
        let newuserdata=this.preset(nextProps.data);
        this.setState({
            alldata:nextProps.data,
            nowdata:nextProps.data,
            usersdata:newuserdata,
            ordersdata:[],
            booksdata:[]
        })
    }

    preset(data)
    {
        let newuserdata=[];
        for (let i=0;i<data.length;i++)
        {
            let nowusername=data[i][0];
            let existed=false;
            for (let j=0;j<newuserdata.length;j++)
            {
                if (nowusername===newuserdata[j])
                {
                    existed=true;
                    break;
                }
            }
            if (!existed) newuserdata.push(nowusername);
        }
        return newuserdata;
    }

    searchdata=()=>{
        var searchwhat=document.getElementById("searchdata").value;
        console.log(searchwhat);
        console.log(this.state.alldata);
        var newdata=[];
        for (let i=0;i<this.state.alldata.length;i++){
            if(this.state.alldata[i][2].search(searchwhat)!==-1){
                newdata.push(this.state.alldata[i]);
            }
        }
        let newuserdata=this.preset(newdata);
        this.setState({
            nowdata:newdata,
            usersdata:newuserdata,
            ordersdata:[],
            booksdata:[]
        })
    }

    timesearchdata=(starttime,endtime)=>{
        var newdata=[];
        for (let i=0;i<this.state.alldata.length;i++){
            if(this.state.alldata[i][3]>=starttime&&this.state.alldata[i][3]<=endtime){
                newdata.push(this.state.alldata[i]);
            }
        }
        let newuserdata=this.preset(newdata);
        this.setState({
            nowdata:newdata,
            usersdata:newuserdata,
            ordersdata:[],
            booksdata:[]
        })

    }


    selectuser=(e,item)=>
    {
        var namelist=document.getElementsByClassName("orderselement");
        for (let i=0;i<namelist.length;i++){
            namelist[i].style.background="rgba(255,255,255,0)";
        }
        let neworderdata=[];
        for (let i=0;i<this.state.nowdata.length;i++)
        {
            let noworderid=this.state.nowdata[i][1];
            let existed=false;
            for (let j=0;j<neworderdata.length;j++)
            {
                if (noworderid===neworderdata[j])
                {
                    existed=true;
                    break;
                }
            }
            if (!existed&&this.state.nowdata[i][0]===item) neworderdata.push(noworderid);
        }
        document.getElementById(e.currentTarget.id).style.background="rgba(255,255,255,0.3)";
        this.setState({
            ordersdata:neworderdata
        })
    }

    selectorder=(e,item)=>
    {
        var namelist=document.getElementsByClassName("orderselement");
        for (let i=0;i<namelist.length;i++){
            namelist[i].style.background="rgba(255,255,255,0)";
        }
        let newbookdata=[];
        for (let i=0;i<this.state.nowdata.length;i++)
        {
            let nowbookname=this.state.nowdata[i][2];
            if (this.state.nowdata[i][1]===item) newbookdata.push(nowbookname);
        }
        document.getElementById(e.currentTarget.id).style.background="rgba(255,255,255,0.3)";
        this.setState({
            booksdata:newbookdata
        })
        
    }

    updatealldata(){
        this.props.parent.updatealldata();
    }

    render(){
        return(
            <div className="allorderscontainer">
                <div className="card">
                    <NameList ele={this.state.usersdata} parent={this} options="orders_users"></NameList>
                    <NameList ele={this.state.ordersdata} parent={this} options="orders_orders"></NameList>
                    <NameList ele={this.state.booksdata} parent={this} options="orders_books"></NameList>
                </div>
                <Searchbar parent={this}></Searchbar>
                <Timeseacherbar parent={this}></Timeseacherbar>
            </div>
        )
    }
}

export class OFooter extends React.Component{

    Addorderclick(){
        var l=document.getElementById('orderaddcontent');
        if (l.style.visibility==='hidden'){
            l.style.visibility='visible';
        }else{
            l.style.visibility='hidden';
        }
    }
    Deleteorderclick(){
        var r=document.getElementById('orderdeletecontent');
        if (r.style.visibility==='hidden'){
            r.style.visibility='visible';
        }else{
            r.style.visibility='hidden';
        }
    }

    render(){
        return(
            <div className="orderfooter">
                <div className="orderchange">
                    <button className="orderchangebutton" onClick={() => this.Addorderclick()}>Add</button>
                </div>
                <div className="orderchange">
                    <button className="orderchangebutton" onClick={() => this.Deleteorderclick()}>Delete</button>
                </div>
            </div>
        )
    }
}

export class Ocontents extends React.Component{
    constructor(props)
    {
        super(props);
        this.state={
            options:this.props.options,
            username:"",
            orderid:"",
            bookid:""
        }
    }

    changeusername=(e)=>
    {
        this.setState({
            username:e.target.value
        })
    }
    changeorderid=(e)=>
    {
        this.setState({
            orderid:e.target.value
        })
    }
    changebookid=(e)=>
    {
        this.setState({
            bookid:e.target.value
        })
    }

    tryaddorder=()=>
    {
        let adminaddordercallback=(susflag)=>
        {
            if(susflag>0)
            {
                let ocontents=document.getElementsByClassName("orderchangecontent");
                for (let i = 0; i < ocontents.length; i++) {
                    ocontents[i].style.visibility = 'hidden';
                }
                alert("Add order Success!");
                this.props.parent.updatealldata();
            }else{
                alert("Add order false!");
            }
        }
        if(this.state.username===""||this.state.orderid===""||this.state.bookid==="")
        {
            alert("Blank username, orderid or bookid detected!");
            this.setState({
                username:"",
                orderid:"",
                bookid:""
            })
            return;
        }
        adminaddorder(this.state.username,this.state.orderid,this.state.bookid,adminaddordercallback);
    }

    trydeleteorder=()=>
    {
        let admindeleteordercallback=(susflag)=>
        {
            if(susflag>0)
            {
                let ocontents=document.getElementsByClassName("orderchangecontent");
                for (let i = 0; i < ocontents.length; i++) {
                    ocontents[i].style.visibility = 'hidden';
                }
                alert("Delete order Success!");
                this.props.parent.updatealldata();
            }else{
                alert("Delete order false!");
            }
        }
        if(this.state.orderid===""||this.state.bookid==="")
        {
            alert("Blank orderid or bookid detected!");
            this.setState({
                orderid:"",
                bookid:""
            })
            return;
        }
        admindeleteorder(this.state.orderid,this.state.bookid,admindeleteordercallback);
    }

    render(){
        if(this.state.options==="orderadd")
        {
            return(
                <div className="orderchangecontent" id="orderaddcontent">
                    <form className="word" name="input" action="" method="get">
                        <p>Please enter the username, orderid and bookid to add order:</p>
                        <br />
                        <div className="inputbox">
                            <div className="display">Username:</div>
                            <input onChange={(e)=>this.changeusername(e)} className="typein" type="text"/>
                        </div>
                        <br />
                        <div className="inputbox">
                            <div className="display">Orderid:</div>
                            <input onChange={(e)=>this.changeorderid(e)} className="typein" type="text"/>
                        </div>
                        <br />
                        <div className="inputbox">
                            <div className="display">Bookid:</div>
                            <input onChange={(e)=>this.changebookid(e)} className="typein" type="text"/>
                        </div>
                        <br />
                        <input onClick={()=>this.tryaddorder()} className="orderchangebutton" type="button" value="Submit" />
                    </form>
                </div>
            )
        }
        else
        {
            return(
                <div className="orderchangecontent" id="orderdeletecontent">
                    <form className="word" name="input" action="" method="get">
                        <p>Please enter the orderid and bookid to delete order:</p>
                        <br />
                        <div className="inputbox">
                            <div className="display">Orderid:</div>
                            <input onChange={(e)=>this.changeorderid(e)} className="typein" type="text"/>
                        </div>
                        <br />
                        <div className="inputbox">
                            <div className="display">Bookid:</div>
                            <input onChange={(e)=>this.changebookid(e)} className="typein" type="text"/>
                        </div>
                        <br />
                        <input onClick={()=>this.trydeleteorder()} className="orderchangebutton" type="button" value="Submit" />
                    </form>
                </div>
            )
        }
        
    }
}